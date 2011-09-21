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
		database = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		dbHelper.close();
	}

	/**
	 * Create a new Spending If the Spending is successfully created return the new rowId for that note, otherwise
	 * return a -1 to indicate failure.
	 */
	public long insert(int amount, String date_pay, int pay, String reason, String other, String comment) {
		ContentValues initialValues = createContentValues(amount, date_pay, pay, reason, other, comment);
		return database.insert(clsContant.DATABASE_TABLE, null, initialValues);
	}

	/**
	 * Update the Spending
	 */
	public boolean update(long rowId, int amount, String date_pay, int pay, String reason, String other, String comment) {
		ContentValues updateValues = createContentValues(amount, date_pay, pay, reason, other, comment);
		return database.update(clsContant.DATABASE_TABLE, updateValues, clsContant.KEY_ROWID + "=" + rowId, null) > 0;
	}

	/**
	 * Deletes
	 */
	public boolean delete(long rowId) {
		return database.delete(clsContant.DATABASE_TABLE, clsContant.KEY_ROWID + "=" + rowId, null) > 0;
	}

	/**
	 * Return a Cursor over the list of all Spending in the database
	 * 
	 * @return Cursor over all notes
	 */
	public Cursor fetchAll() {
		return database.query(clsContant.DATABASE_TABLE, new String[] { clsContant.KEY_ROWID, clsContant.KEY_AMOUNT,
				clsContant.KEY_DATE_PAY, clsContant.KEY_PAY, clsContant.KEY_REASON, clsContant.KEY_OTHER,
				clsContant.KEY_COMMENT }, null, null, null, null, null);
	}

	/**
	 * Return a Cursor positioned at the defined Spending
	 */
	public Cursor fetchData(long rowId) throws SQLException {
		Cursor mCursor = database.query(true, clsContant.DATABASE_TABLE, new String[] { clsContant.KEY_ROWID,
				clsContant.KEY_AMOUNT, clsContant.KEY_DATE_PAY, clsContant.KEY_PAY, clsContant.KEY_REASON,
				clsContant.KEY_OTHER, clsContant.KEY_COMMENT }, clsContant.KEY_ROWID + "=" + rowId, null, null, null,
				null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	private ContentValues createContentValues(int amount, String date_pay, int pay, String reason, String other,
			String comment) {
		ContentValues values = new ContentValues();
		values.put(clsContant.KEY_AMOUNT, amount);
		values.put(clsContant.KEY_DATE_PAY, date_pay);
		values.put(clsContant.KEY_PAY, pay);
		values.put(clsContant.KEY_REASON, reason);
		values.put(clsContant.KEY_OTHER, other);
		values.put(clsContant.KEY_COMMENT, comment);
		return values;
	}

	public ArrayList<String[]> SelectAll() {
		ArrayList<String[]> arrList = new ArrayList<String[]>();
		try{
		// query (table, String[] columns, selection, String[] selectionArgs, groupBy, having, orderBy)
			Cursor cursor = database.query(clsContant.TBL_SPENDING, new String[] { clsContant.KEY_ROWID,
					clsContant.KEY_AMOUNT, clsContant.KEY_DATE_PAY, clsContant.KEY_PAY, clsContant.KEY_REASON,
					clsContant.KEY_OTHER, clsContant.KEY_COMMENT }, null, null, null, null, clsContant.KEY_ROWID);
			if (cursor.moveToFirst()) {
				do {
					arrList.add(new String[] { cursor.getString(0), cursor.getString(1), cursor.getString(2) });
				} while (cursor.moveToNext());
				if (cursor != null && !cursor.isClosed()) {
					cursor.close();
				}
			}
			return arrList;
		}catch(Exception ex){
			Log.i(TAG, "***** SelectAll() Error: " + ex.getMessage());
			return null;
		}
	}

	public ArrayList<String[]> SelectData(String[] selectionArgs) {
		String selection;
		String[] column_name;
		ArrayList<String[]> arrList = new ArrayList<String[]>();
		try{
			column_name = new String[] { clsContant.KEY_AMOUNT, clsContant.KEY_DATE_PAY, clsContant.KEY_REASON,
					clsContant.KEY_OTHER };

			selection = clsContant.KEY_AMOUNT + "=? AND " + clsContant.KEY_PAY + "=?";
			// query (table_name, String[] columns, selection, String[] selectionArgs, groupBy, having, orderBy)
			Cursor cursor = database.query(clsContant.TBL_SPENDING, column_name, selection, selectionArgs, null, null,
					clsContant.KEY_DATE_PAY);

			if (cursor.moveToFirst()) {
				do {
					// list.add(new String[]{cursor.getString(0), cursor.getString(1), cursor.getString(2)});
					arrList.add(new String[] { cursor.getString(0), cursor.getString(1), cursor.getString(2) });
				} while (cursor.moveToNext());
				if (cursor != null && !cursor.isClosed()) {
					cursor.close();
				}
			}
			return arrList;
		}catch(Exception ex){
			Log.i(TAG, "***** SelectData() Error: " + ex.getMessage());
			return null;
		}
	}
}