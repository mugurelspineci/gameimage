package spending.com;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class clsDelete extends Activity {
	private static final String TAG = clsDelete.class.getSimpleName();
	EditText edtDateFrom;
	EditText edtDateTo;
	Button btnDelete;
	Button btnCancel;
	SpendingDbAdapter mDbHelper;

	@Override
	public void onCreate(Bundle saved) {
		super.onCreate(saved);
		setContentView(R.layout.search);
		try {

			mDbHelper = new SpendingDbAdapter(this);
			edtDateFrom = (EditText) findViewById(R.id.edtDateFrom);
			edtDateTo = (EditText) findViewById(R.id.edtDateTo);

			btnDelete = (Button) findViewById(R.id.btnDelete);
			btnCancel = (Button) findViewById(R.id.btnCancel);

			btnDelete.setOnClickListener(new View.OnClickListener() {
				public void onClick(View view) {

				}
			});

			btnCancel.setOnClickListener(new View.OnClickListener() {
				public void onClick(View view) {
					finish();
				}
			});
		} catch (Exception ex) {
			Log.i(TAG, "***** onCreate() Error: " + ex.getMessage());
		}
	}
	
	private void deleteData(){
		try{
			if(mDbHelper.deleteDate("","")){
				clearData();
				Log.i(TAG, "***** deleteData() Da xoa");
			}else
				Log.i(TAG, "***** deleteData() Bi loi trong qua trinh xoa");
			
			
		} catch (Exception ex) {
			Log.i(TAG, "***** deleteData() Error: " + ex.getMessage());
		}
	}
	
	private void clearData(){
		edtDateFrom.setText("");
		edtDateTo.setText("");
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		try {
			if (mDbHelper != null)
				mDbHelper.close();
		} catch (Exception ex) {
			Log.i(TAG, "***** onDestroy() Error: " + ex.getMessage());
		}
	}
}
