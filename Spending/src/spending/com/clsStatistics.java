package spending.com;

import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class clsStatistics extends Activity {
	private static final String TAG = clsStatistics.class.getSimpleName();
	EditText edtDateFrom;
	EditText edtDateTo;
	Button btnStatistics;
	Button btnCancel;
	SpendingDbAdapter mDbHelper;
	String currTime; // luu ngay thang hien tai

	@SuppressWarnings("static-access")
	@Override
	public void onCreate(Bundle saved) {
		super.onCreate(saved);
		setContentView(R.layout.statistics);
		try {

			mDbHelper = new SpendingDbAdapter(this);
			edtDateFrom = (EditText) findViewById(R.id.edtDateFrom);
			edtDateTo = (EditText) findViewById(R.id.edtDateTo);

			btnStatistics = (Button) findViewById(R.id.btnStatistics);
			btnCancel = (Button) findViewById(R.id.btnCancel);

			android.text.format.DateFormat datetime = new android.text.format.DateFormat();
			currTime = "" + datetime.format("dd/MM/yyyy", new Date());
			edtDateTo.setText(currTime);
			
			btnStatistics.setOnClickListener(new View.OnClickListener() {
				public void onClick(View view) {
					if (checkValid())
						getStatistics();
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

	private void getStatistics() {
		ArrayList<clsData> arrList;
		try {
			mDbHelper.open();
			arrList = mDbHelper.SelectData(edtDateFrom.getText().toString().trim(), edtDateTo.getText().toString()
					.trim(), "", "", "", 3);
			// arrList = mDbHelper.SelectAll();
			if (arrList == null){
				new AlertDialog.Builder(clsStatistics.this).setTitle("Lỗi Nhập")
				.setMessage("Dữ liệu không có").setPositiveButton("OK", null).show();
				clearData();				
				return;
			}
			
			Intent intent = new Intent().setClass(this, clsChart.class);
			intent.putParcelableArrayListExtra("DATA", arrList);
			startActivity(intent);
			
		} catch (Exception ex) {
			Log.i(TAG, "***** getStatistics() Error: " + ex.getMessage());
		}
	}

	private boolean checkValid() {
		if (edtDateFrom.getText().toString().trim().length() != 0 &&!CommonUtil.isValidDate(edtDateFrom.getText().toString())) {
			new AlertDialog.Builder(clsStatistics.this).setTitle("Lỗi Nhập")
					.setMessage("Ngày tháng không đúng (dd/mm/yyyy)").setPositiveButton("OK", null).show();
			return false;
		}

		if (edtDateTo.getText().toString().trim().length() != 0 && !CommonUtil.isValidDate(edtDateTo.getText().toString())) {
			new AlertDialog.Builder(clsStatistics.this).setTitle("Lỗi Nhập")
					.setMessage("Ngày tháng không đúng (dd/mm/yyyy)").setPositiveButton("OK", null).show();
			return false;
		}
		return true;
	}

	private void clearData() {
		edtDateFrom.setText("");
		edtDateTo.setText(currTime);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		try {
			if (mDbHelper != null)
				mDbHelper = null;
		} catch (Exception ex) {
			Log.i(TAG, "***** onDestroy() Error: " + ex.getMessage());
		}
	}
}
