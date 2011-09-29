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
	public long insert(int amount, String date_pay, int pay, String reason, String comment) {
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
	 * Return a Cursor over the list of all Spending in the database
	 * 
	 * @return Cursor over all notes
	 */
	public Cursor fetchAll() {
		return database.query(clsContant.TBL_SPENDING, new String[] { clsContant.KEY_ROWID, clsContant.KEY_AMOUNT,
				clsContant.KEY_DATE_PAY, clsContant.KEY_PAY, clsContant.KEY_REASON, clsContant.KEY_COMMENT }, null,
				null, null, null, null);
	}

	/**
	 * Return a Cursor positioned at the defined Spending
	 */
	public Cursor fetchData(long rowId) throws SQLException {
		Cursor mCursor = database.query(true, clsContant.TBL_SPENDING, new String[] { clsContant.KEY_ROWID,
				clsContant.KEY_AMOUNT, clsContant.KEY_DATE_PAY, clsContant.KEY_PAY, clsContant.KEY_REASON,
				clsContant.KEY_COMMENT }, clsContant.KEY_ROWID + "=" + rowId, null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	private ContentValues createContentValues(int amount, String date_pay, int pay, String reason, String comment) {
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
					clsContant.KEY_COMMENT }, null, null, null, null, clsContant.KEY_ROWID);

			while (cursor.moveToNext()) {
				data = new clsData(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
				arrList.add(data);
			}
			// if (cursor.moveToFirst()) {
			// do {
			// data = new clsData(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
			// arrList.add(data);
			// } while (cursor.moveToNext());
			// if (cursor != null && !cursor.isClosed()) {
			// cursor.close();
			// }
			// }
			return arrList;
		} catch (Exception ex) {
			Log.i(TAG, "***** SelectAll() Error: " + ex.getMessage());
			return null;
		}
	}

	public ArrayList<clsData> SelectData(String cond) {

		String[] column_name;
		clsData data;
		ArrayList<clsData> arrList = new ArrayList<clsData>();
		try {
			column_name = new String[] { clsContant.KEY_PAY, clsContant.KEY_AMOUNT, clsContant.KEY_DATE_PAY,
					clsContant.KEY_REASON };

			// selection = clsContant.KEY_AMOUNT + "=? AND " + clsContant.KEY_PAY + "=?";
			// query (table_name, String[] columns, selection, String[] selectionArgs, groupBy, having, orderBy)
			Cursor cursor = database.query(clsContant.TBL_SPENDING, column_name, cond, null, null, null,
					clsContant.KEY_DATE_PAY);

			while (cursor.moveToNext()) {
				data = new clsData(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
				arrList.add(data);
			}
			// if (cursor.moveToFirst()) {
			// do {
			// data = new clsData(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
			// arrList.add(data);
			// } while (cursor.moveToNext());
			// if (cursor != null && !cursor.isClosed()) {
			// cursor.close();
			// }
			// }
			return arrList;
		} catch (Exception ex) {
			Log.i(TAG, "***** SelectData() Error: " + ex.getMessage());
			return null;
		}
	}

	// public ArrayList<String[]> SelectData(String cond) {
	// String[] column_name;
	// ArrayList<String[]> arrList = new ArrayList<String[]>();
	// try{
	// column_name = new String[] { clsContant.KEY_AMOUNT, clsContant.KEY_DATE_PAY, clsContant.KEY_REASON,
	// clsContant.KEY_OTHER };
	//
	// //selection = clsContant.KEY_AMOUNT + "=? AND " + clsContant.KEY_PAY + "=?";
	// // query (table_name, String[] columns, selection, String[] selectionArgs, groupBy, having, orderBy)
	// Cursor cursor = database.query(clsContant.TBL_SPENDING, column_name, cond, null, null, null,
	// clsContant.KEY_DATE_PAY);
	//
	// if (cursor.moveToFirst()) {
	// do {
	// // list.add(new String[]{cursor.getString(0), cursor.getString(1), cursor.getString(2)});
	// arrList.add(new String[] { cursor.getString(0), cursor.getString(1), cursor.getString(2) });
	// } while (cursor.moveToNext());
	// if (cursor != null && !cursor.isClosed()) {
	// cursor.close();
	// }
	// }
	// return arrList;
	// }catch(Exception ex){
	// Log.i(TAG, "***** SelectData() Error: " + ex.getMessage());
	// return null;
	// }
	// }

}