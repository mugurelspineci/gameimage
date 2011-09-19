package spending.com;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.util.Log;
import android.database.Cursor;

public class clsSearch extends Activity{

	private static final String TAG = clsSearch.class.getSimpleName();

	EditText edtDateFrom;
	EditText edtDateTo;
	EditText edtAmountFrom;
	EditText edtAmountTo;
	Spinner spnReason;		
	EditText edtOther;
	Button btnSearch;
	Button btnCancel;
	SpendingDbAdapter  mDbHelper;
	
	@Override
	public void onCreate(Bundle saved){
		super.onCreate(saved);
		setContentView(R.layout.Search);
		
		mDbHelper = new SpendingDbAdapter(this);
		mDbHelper.open();
	}
	
	private void searchData() {
		// if (mRowId != null) {
			// Cursor todo = mDbHelper.fetchData(mRowId);
			// startManagingCursor(todo);
			// String category = todo.getString(todo
					// .getColumnIndexOrThrow(TodoDbAdapter.KEY_CATEGORY));
			
			// for (int i=0; i<mCategory.getCount();i++){
				
				// String s = (String) mCategory.getItemAtPosition(i); 
				// Log.e(null, s +" " + category);
				// if (s.equalsIgnoreCase(category)){
					// mCategory.setSelection(i);
				// }
			// }
			
			// mTitleText.setText(todo.getString(todo.getColumnIndexOrThrow(TodoDbAdapter.KEY_SUMMARY)));
			// mBodyText.setText(todo.getString(todo.getColumnIndexOrThrow(TodoDbAdapter.KEY_DESCRIPTION)));
		// } 
	}
	
}