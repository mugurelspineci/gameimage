package spending.com;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SpendingDatabaseHelper extends SQLiteOpenHelper {

	private static final String TAG = SpendingDatabaseHelper.class.getSimpleName();
	// The Android's default system path of your application database.
	// private static String DB_PATH = "/data/data/YOUR_PACKAGE/databases/";

	private SQLiteDatabase myDataBase;
	private final Context myContext;

	// Database creation sql statement
	private static final String DATABASE_CREATE = "CREATE TABLE "
			+ clsContant.TBL_SPENDING
			+ " ( "
			+ " _id INTEGER PRIMARY KEY autoincrement, amount NUMERIC not null, date_pay TEXT not null, pay integer not null"
			+ ", reason TEXT, comment TEXT);";

	/**
	 * Constructor Takes and keeps a reference of the passed context in order to access to the application assets and
	 * resources.
	 * 
	 * @param context
	 */
	public SpendingDatabaseHelper(Context context) {
		super(context, clsContant.DATABASE_TABLE, null, clsContant.DATABASE_VERSION);
		this.myContext = context;
	}

	// Method is called during creation of the database
	@Override
	public void onCreate(SQLiteDatabase database) {
		try {
			database.execSQL(DATABASE_CREATE);
			Log.i(TAG, "*****onCreate(): tao database" );
		} catch (Exception ex) {
			throw new Error("Error copying database: " + ex.getMessage());
		}
	}

	// Method is called during an upgrade of the database, e.g. if you increase
	// the database version
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS todo");
		onCreate(database);
	}

	/**
	 * Creates a empty database on the system and rewrites it with your own database.
	 * */
	public void createDataBase() throws IOException {
		boolean dbExist = checkDataBase();
		if (dbExist) {
			// do nothing - database already exist
			Log.i(TAG, "*****createDataBase(): database da ton tai,  " );
		} else {
			Log.i(TAG, "*****createDataBase(): database khong ton tai " );
			// By calling this method and empty database will be created into the default system path
			// of your application so we are gonna be able to overwrite that database with our database.
			this.getReadableDatabase();

			try {
				copyDataBase();
			} catch (IOException e) {
				throw new Error("Error copying database");
			}
		}

	}

	/*
	 * Function: Check if the database already exist to avoid re-copying the file each time you open the application.
	 * 
	 * @return true if it exists, false if it doesn't
	 */
	private boolean checkDataBase() {
		SQLiteDatabase checkDB = null;
		try {
			String myPath = clsContant.DB_PATH + clsContant.DATABASE_TABLE;
			checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

		} catch (SQLiteException e) {
			// database does't exist yet.
		}

		if (checkDB != null) {
			checkDB.close();
		}

		return checkDB != null ? true : false;
	}

	/**
	 * Copies your database from your local assets-folder to the just created empty database in the system folder, from
	 * where it can be accessed and handled. This is done by transfering bytestream.
	 * */
	private void copyDataBase() throws IOException {

		Log.i(TAG, "*****copyDataBase() " );
		// Open your local db as the input stream
		InputStream myInput = myContext.getAssets().open(clsContant.DATABASE_TABLE);

		// Path to the just created empty db
		String outFileName = clsContant.DB_PATH + clsContant.DATABASE_TABLE;

		// Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);

		// transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}

		// Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();

	}

	public void openDataBase() throws SQLException {

		// Open the database
		String myPath = clsContant.DB_PATH + clsContant.DATABASE_TABLE;
		myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

	}

	@Override
	public synchronized void close() {
		if (myDataBase != null)
			myDataBase.close();
		super.close();
	}

}