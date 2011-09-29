package spending.com;
	
public class clsContant{
	// The Android's default system path of your application database.
	public static String DB_PATH = "/data/data/YOUR_PACKAGE/databases/";
	public static final String DATABASE_TABLE = "DB_SPENDING";
	public static final String TBL_SPENDING = "SPENDING";
	public static final int DATABASE_VERSION = 1;
	// Database fields
	public static final String KEY_ROWID = "_id";
	public static final String KEY_AMOUNT = "amount";
	public static final String KEY_DATE_PAY = "date_pay";
	public static final String KEY_PAY = "pay";
	public static final String KEY_REASON = "reason";
	public static final String KEY_COMMENT = "comment";
}