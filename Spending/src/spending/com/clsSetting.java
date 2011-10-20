package spending.com;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


public class clsSetting extends Activity {
	private static final String TAG = clsSetting.class.getSimpleName();
	EditText edtReason;
	Button btnSave;
	Button btnCancel;

	SpendingDbAdapter mDbHelper;

	@Override
	public void onCreate(Bundle saved) {
		super.onCreate(saved);
		setContentView(R.layout.setting);

		try {

			mDbHelper = new SpendingDbAdapter(this);
			mDbHelper.open();

			edtReason = (EditText) findViewById(R.id.edtReason);

			btnSave = (Button) findViewById(R.id.btnSave);
			btnCancel = (Button) findViewById(R.id.btnCancel);

			btnSave.setOnClickListener(new View.OnClickListener() {
				public void onClick(View view) {
					if(edtReason.getText().toString().length()!=0)
						saveData();
				}
			});

			btnCancel.setOnClickListener(new View.OnClickListener() {
				public void onClick(View view) {
					finish();
				}
			});
			
			loadData();
		} catch (Exception ex) {
			Log.i(TAG, "***** onCreate() Error: " + ex.getMessage());
		}
	}


	private void saveData() {
		long insert;
		try {		
			insert = mDbHelper.insertReason(edtReason.getText().toString());
			if (insert > 0) {
				new AlertDialog.Builder(clsSetting.this).setTitle("Lưu").setMessage("Đã lưu thành công")
						.setPositiveButton("OK", null).show();
				clearData();
				loadData();
			}
			//Log.i(TAG, "***** saveData() Da luu xuong db, id=" + insert);
		} catch (Exception ex) {
			new AlertDialog.Builder(clsSetting.this).setTitle("Lưu").setMessage("Không thể lưu")
			.setPositiveButton("OK", null).show();
			Log.i(TAG, "***** saveData() Error: " + ex.getMessage());
		}
	}

	private void loadData(){
		ArrayList<String> arrList;
		ArrayList<clsData> myArray =new ArrayList<clsData>();
		clsData data;
		arrList = mDbHelper.SelectReason();
		if (arrList == null)
			return;
		
		for (String reason : arrList) {
			data = new clsData(reason);
			myArray.add(data);
		}

		final ListView lv1 = (ListView) findViewById(R.id.lstReason);
		lv1.setAdapter(new clsReasonBaseAdapter(this, myArray));
		Log.i(TAG, "***** loadData() called");
	}
	
	private void clearData() {
		edtReason.setText("");		
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
