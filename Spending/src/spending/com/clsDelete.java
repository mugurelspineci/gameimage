package spending.com;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


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
		setContentView(R.layout.delete);
		try {

			mDbHelper = new SpendingDbAdapter(this);
			edtDateFrom = (EditText) findViewById(R.id.edtDateFrom);
			edtDateTo = (EditText) findViewById(R.id.edtDateTo);

			btnDelete = (Button) findViewById(R.id.btnDelete);
			btnCancel = (Button) findViewById(R.id.btnCancel);

			btnDelete.setOnClickListener(new View.OnClickListener() {
				public void onClick(View view) {
					dialogMsgImage("Ban co muon xoa khong?");
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
	
	public void dialogMsgImage(String dg){
		AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
		alt_bld.setMessage(dg).setCancelable(false)
				.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// Action for 'Yes' Button	
						deleteData();
					}
				}).setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// Action for 'NO' Button
						dialog.cancel();
					}
				});
		AlertDialog alert = alt_bld.create();
		// Title for AlertDialog
		alert.setTitle("Spending...");
		// Icon for AlertDialog
		alert.setIcon(R.drawable.icon);
		alert.show();
	}
	
	private void deleteData(){
		try{
			//Xoa dang bi loi
//			if(mDbHelper.deleteData(edtDateFrom.getText().toString(),edtDateTo.getText().toString())==true){
//				clearData();
//				Log.i(TAG, "***** deleteData() Da xoa");
//			}else
//				Log.i(TAG, "***** deleteData(): Loi trong qua trinh xoa");
			clearData();
			
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
				mDbHelper= null;
		} catch (Exception ex) {
			Log.i(TAG, "***** onDestroy() Error: " + ex.getMessage());
		}
	}
}
