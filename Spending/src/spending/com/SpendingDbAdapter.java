package spending.com;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class SpendingDbAdapter {

	//Database name
	private static final String DATABASE_TABLE = "DB_SPENDING";
	// Database fields
	public static final String KEY_ROWID = "_id";
	public static final String KEY_AMOUNT = "amount";
	public static final String KEY_DATE_PAY = "date_pay";
	public static final String KEY_PAY = "pay";
	public static final String KEY_REASON = "reason";
	public static final String KEY_OTHER = "other";
	public static final String KEY_COMMENT = "comment";
	private String [] column_name = new String[]{KEY_ROWID, KEY_AMOUNT, KEY_DATE_PAY, KEY_PAY, KEY_REASON, KEY_OTHER, KEY_COMMENT};
	
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
	 * Create a new Spending If the Spending is successfully created return the new
	 * rowId for that note, otherwise return a -1 to indicate failure.
	 */
	public long insert(int amount, String date_pay, int pay, String reason, String other, String comment) {
		ContentValues initialValues = createContentValues(amount, date_pay, pay, reason, other, comment);
		return database.insert(DATABASE_TABLE, null, initialValues);
	}

	/**
	 * Update the Spending
	 */
	public boolean update(long rowId, int amount, String date_pay, int pay, String reason, String other, String comment) {
		ContentValues updateValues = createContentValues(amount, date_pay, pay, reason, other, comment);
		return database.update(DATABASE_TABLE, updateValues, KEY_ROWID + "=" + rowId, null) > 0;
	}

	/**
	 * Deletes 
	 */
	public boolean delete(long rowId) {
		return database.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
	}

	/**
	 * Return a Cursor over the list of all Spending in the database
	 * 
	 * @return Cursor over all notes
	 */
	public Cursor fetchAll() {
		return database.query(DATABASE_TABLE, new String[] { KEY_ROWID,
				KEY_AMOUNT, KEY_DATE_PAY, KEY_PAY, KEY_REASON, KEY_OTHER, KEY_COMMENT }, null, null, null, null, null);
	}

	/**
	 * Return a Cursor positioned at the defined Spending
	 */
	public Cursor fetchData(long rowId) throws SQLException {
		Cursor mCursor = database.query(true, DATABASE_TABLE, new String[] {
				KEY_ROWID, KEY_AMOUNT, KEY_DATE_PAY, KEY_PAY, KEY_REASON, KEY_OTHER, KEY_COMMENT },
				KEY_ROWID + "=" + rowId, null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	private ContentValues createContentValues(int amount, String date_pay, int pay, String reason, String other, String comment) {
		ContentValues values = new ContentValues();
		values.put(KEY_AMOUNT, amount);
		values.put(KEY_DATE_PAY, date_pay);
		values.put(KEY_PAY, pay);
		values.put(KEY_REASON, reason);
		values.put(KEY_OTHER, other);
		values.put(KEY_COMMENT, comment);		
		return values;
	}
	
	public List<String[]> SelectAll()
	{		
		List<String[]> list = new ArrayList<String[]>();
		//query (table, String[] columns, selection, String[] selectionArgs, groupBy,  having,  orderBy)
		Cursor cursor = database.query(TABLE, new String[]{KEY_ROWID, KEY_AMOUNT, KEY_DATE_PAY, KEY_PAY, KEY_REASON, KEY_OTHER, KEY_COMMENT}, null, null, null, null, KEY_ROWID);
                if(cursor.moveToFirst()){
                     do{
                         list.add(new String[]{cursor.getString(0), cursor.getString(1), cursor.getString(2)});
                     }while(cursor.moveToNext());
                     if(cursor!=null && !cursor.isClosed()){
                         cursor.close();
                    }
                }
		return list;
	}
	
	public List<String[]> SelectData(String [] selectionArgs){
		String selection;
		String colum_data[];
		List<String[]> list = new ArrayList<String[]>();
		selection = KEY_AMOUNT + "=? AND " + KEY_PAY + "=?" ;		
		//query (table_name, String[] columns, selection, String[] selectionArgs, groupBy,  having,  orderBy)
		Cursor cursor = database.query(TABLE, column_name, selection, selectionArgs, null, null, KEY_DATE_PAY);

		if(cursor.moveToFirst()){
			 do{
				 //list.add(new String[]{cursor.getString(0), cursor.getString(1), cursor.getString(2)});				 
				 list.add(new String[]{cursor.getColumnIndex(KEY_AMOUNT), 
										cursor.getColumnIndex(KEY_REASON), 
										cursor.getColumnIndex(KEY_DATE_PAY)});
			 }while(cursor.moveToNext());
			 if(cursor!=null && !cursor.isClosed()){
				 cursor.close();
			}
		}
		return list;
	}
	
	public List<String[]> SelectData(String cond){
		String sql;		
		List<String[]> list = new ArrayList<String[]>();
		
		sql = "SELECT " + KEY_AMOUNT + ", " + KEY_DATE_PAY + ", " + KEY_REASON + ", " + KEY_OTHER + " FROM " + clsContant.TBL_SPENDING + " " + cond;
		Cursor cursor=db.rawQuery(sql, null);
		while(cursor.moveToNext()){
			 list.add(new String[]{cursor.getColumnIndex(KEY_AMOUNT), 
									cursor.getColumnIndex(KEY_REASON), 
									cursor.getColumnIndex(KEY_DATE_PAY)});
		}
		return list;
	}
}