package spending.com;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SpendingDbAdapter {

	private static final String TAG = SpendingDbAdapter.class.getSimpleName();
	private Context context;
	private SQLiteDatabase database;
	private SpendingDatabaseHelper dbHelper;

	public SpendingDbAdapter(Context context) {
		this.context = context;
	}

	public SpendingDbAdapter open() throws SQLException {
		dbHelper = new SpendingDatabaseHelper(context);
		// database = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		dbHelper.close();
	}

	/**
	 * Create a new Spending If the Spending is successfully created return the new rowId for that note, otherwise
	 * return a -1 to indicate failure.
	 */
	public long insert(float amount, String date_pay, int pay, String reason, String comment) {
		database = dbHelper.getWritableDatabase();
		ContentValues initialValues = createContentValues(amount, date_pay, pay, reason, comment);
		return database.insert(clsContant.TBL_SPENDING, null, initialValues);
	}

	/**
	 * Update the Spending
	 */
	public boolean update(long rowId, int amount, String date_pay, int pay, String reason, String other, String comment) {
		ContentValues updateValues = createContentValues(amount, date_pay, pay, reason, comment);
		return database.update(clsContant.TBL_SPENDING, updateValues, clsContant.KEY_ROWID + "=" + rowId, null) > 0;
	}

	/**
	 * Deletes
	 */
	public boolean delete(long rowId) {
		return database.delete(clsContant.TBL_SPENDING, clsContant.KEY_ROWID + "=" + rowId, null) > 0;
	}

	/**
	 * Deletes
	 */
	public boolean deleteData(String dateFrom, String dateTo) {
		String cond = " 1=1 ";
		int del;
		try {
			cond += getConditionData(dateFrom, dateTo, clsContant.KEY_DATE_PAY);
			database = dbHelper.getReadableDatabase();
			del = database.delete(clsContant.TBL_SPENDING, cond, null);
			if(del >=  0)
				return true;
			else 
				return false;
		} catch (Exception ex) {
			Log.i(TAG, "***** deleteData() Error: " + ex.getMessage());
			return false;
		}
	}

	private ContentValues createContentValues(float amount, String date_pay, int pay, String reason, String comment) {
		ContentValues values = new ContentValues();
		values.put(clsContant.KEY_AMOUNT, amount);
		values.put(clsContant.KEY_DATE_PAY, date_pay);
		values.put(clsContant.KEY_PAY, pay);
		values.put(clsContant.KEY_REASON, reason);
		values.put(clsContant.KEY_COMMENT, comment);
		return values;
	}

	public ArrayList<clsData> SelectAll() {
		ArrayList<clsData> arrList = new ArrayList<clsData>();
		clsData data;
		try {
			database = dbHelper.getReadableDatabase();
			// query (table, String[] columns, selection, String[] selectionArgs, groupBy, having, orderBy)
			Cursor cursor = database.query(clsContant.TBL_SPENDING, new String[] { clsContant.KEY_ROWID,
					clsContant.KEY_AMOUNT, clsContant.KEY_DATE_PAY, clsContant.KEY_PAY, clsContant.KEY_REASON,
					clsContant.KEY_COMMENT }, null, null, null, null, clsContant.KEY_DATE_PAY + " DESC");

			while (cursor.moveToNext()) {
				data = new clsData(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3),
						cursor.getString(4));
				arrList.add(data);
			}
			if (cursor != null && !cursor.isClosed()) {
				cursor.close();
			}
			return arrList;
		} catch (Exception ex) {
			Log.i(TAG, "***** SelectAll() Error: " + ex.getMessage());
			return null;
		}
	}

	public ArrayList<clsData> SelectData(String dateFrom, String dateTo, String amountFrom, String amountTo,
			String reason, int pay) {
		String cond;
		String[] column_name;
		clsData data;
		ArrayList<clsData> arrList = new ArrayList<clsData>();
		try {
			// cond = "SELECT * FROM " + clsContant.TBL_SPENDING + " WHERE 1=1 ";
			cond = " 1=1 ";
			cond += getCondition(dateFrom, dateTo, amountFrom, amountTo, reason, pay);
			column_name = new String[] { clsContant.KEY_ROWID, clsContant.KEY_AMOUNT, clsContant.KEY_DATE_PAY,
					clsContant.KEY_PAY, clsContant.KEY_REASON };
			database = dbHelper.getReadableDatabase();
			// query (table_name, String[] columns, selection, String[] selectionArgs, groupBy, having, orderBy)
			Cursor cursor = database.query(clsContant.TBL_SPENDING, column_name, cond, null, null, null,
					clsContant.KEY_DATE_PAY + " DESC");
			while (cursor.moveToNext()) {
				// (int rowid, String amount, String datePay, int pay, String reason){
				data = new clsData(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3),
						cursor.getString(4));
				arrList.add(data);
			}
			if (cursor != null && !cursor.isClosed()) {
				cursor.close();
			}
			return arrList;
		} catch (Exception ex) {
			Log.i(TAG, "***** SelectData() Error: " + ex.getMessage());
			return null;
		}
	}
	

	/**
	 * Return condition
	 */
	private String getConditionData(String dateFrom, String dateTo, String keySearch) {
		String cond = " ";
		if (dateFrom.length() != 0 && dateTo.length() != 0) {
			cond += " AND " + keySearch + " >= '" + dateFrom + "' AND " + keySearch + " <= '" + dateTo + "'";
		} else if (dateFrom.length() != 0 && dateTo.length() == 0) {
			cond += " AND " + keySearch + " >= '" + dateFrom + "'";
		} else if (dateFrom.length() == 0 && dateTo.length() != 0) {
			cond += " AND " + keySearch + "<='" + dateTo + "'";
		}
		return cond;
	}

	/**
	 * Return condition
	 */
	private String getCondition(String dateFrom, String dateTo, String amountFrom, String amountTo, String reason,
			int pay) {
		String cond = " ";

		cond += getConditionData(dateFrom, dateTo, clsContant.KEY_DATE_PAY);
		cond += getConditionData(amountFrom, amountTo, clsContant.KEY_AMOUNT);

		if (reason.length() != 0) {
			cond += " AND " + clsContant.KEY_REASON + " like '%" + reason + "%'";
		}
		if (pay == 1) {
			cond += " AND " + clsContant.KEY_PAY + "=1 ";
		} else if (pay == 2) {
			cond += " AND " + clsContant.KEY_PAY + "=2";
		}
		return cond;
	}
}