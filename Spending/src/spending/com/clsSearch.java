package spending.com;

import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.util.Log;
import android.content.Intent;

public class clsSearch extends Activity {

	private static final String TAG = clsSearch.class.getSimpleName();
	RadioButton rdIncome;
	RadioButton rdPayment;
	EditText edtDateFrom;
	EditText edtDateTo;
	EditText edtAmountFrom;
	EditText edtAmountTo;
	Spinner spnReason;
	EditText edtOther;
	Button btnSearch;
	Button btnCancel;
	SpendingDbAdapter mDbHelper;
	String currTime; // luu ngay thang hien tai

	@SuppressWarnings({ "unchecked", "rawtypes", "static-access" })
	@Override
	public void onCreate(Bundle saved) {
		super.onCreate(saved);
		setContentView(R.layout.search);
		try {
			String arrReason[] = { "", "Lương", "Ăn sáng", "Xăng", "Thay nhớt", "Đi siêu thị" };
			ArrayAdapter<String> adapter;

			android.text.format.DateFormat datetime = new android.text.format.DateFormat();
			currTime = "" + datetime.format("dd/MM/yyyy", new Date());
			
			mDbHelper = new SpendingDbAdapter(this);

			edtAmountFrom = (EditText) findViewById(R.id.edtAmountFrom);
			edtAmountTo = (EditText) findViewById(R.id.edtAmountTo);
			edtDateFrom = (EditText) findViewById(R.id.edtDateFrom);
			edtDateTo = (EditText) findViewById(R.id.edtDateTo);
			spnReason = (Spinner) findViewById(R.id.spnReason);
			edtOther = (EditText) findViewById(R.id.edtOther);
			btnSearch = (Button) findViewById(R.id.btnSearch);
			btnCancel = (Button) findViewById(R.id.btnCancel);
			rdIncome = (RadioButton) findViewById(R.id.rdIncome);
			rdPayment = (RadioButton) findViewById(R.id.rdPayment);

			edtDateTo.setText(currTime);
			
			adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrReason);
			spnReason.setAdapter(adapter);

			btnSearch.setOnClickListener(new View.OnClickListener() {
				public void onClick(View view) {
					if (checkValid())
						searchData();
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// super.onActivityResult(requestCode, resultCode, data);
		// TODO handle here.
		String tmp;
		try {
			if (requestCode == 0) {
				if (resultCode == RESULT_OK) {
					tmp = data.getExtras().getString("BACK");
					if (tmp == "home")
						finish();
				} else if (resultCode == RESULT_CANCELED) {
					// Handle cancel
				}
			}
		} catch (Exception ex) {
			Log.i(TAG, "***** onActivityResult() Error: " + ex.getMessage());
		}
	}

	private void searchData() {
		// String cond;
		int pay;
		String reason;
		ArrayList<clsData> arrList;
		// ArrayList<clsData> arrListData;
		try {
			// cond = getCondition();
			mDbHelper.open();

			if (edtOther.getText().toString().length() != 0)
				reason = edtOther.getText().toString().trim();
			else
				reason = spnReason.getSelectedItem().toString();

			if (rdIncome.isChecked())
				pay = 1;
			else if (rdPayment.isChecked())
				pay = 2;
			else
				pay = 3;

			arrList = mDbHelper.SelectData(edtDateFrom.getText().toString().trim(), edtDateTo.getText().toString().trim(),
					edtAmountFrom.getText().toString().trim(), edtAmountTo.getText().toString().trim(), reason, pay);
			// arrList = mDbHelper.SelectAll();
			if (arrList == null)
				return;

			// Log.i(TAG, "***** searchData() loaded data ");
			Intent intent = new Intent().setClass(this, clsShow.class);
			intent.putParcelableArrayListExtra("DATA", arrList);
			startActivity(intent);
		} catch (Exception ex) {
			Log.i(TAG, "***** searchData() Error: " + ex.getMessage());
		}
	}

	private boolean checkValid() {
		if (edtDateFrom.getText().toString().trim().length() != 0 && !CommonUtil.isValidDate(edtDateFrom.getText().toString())) {
			new AlertDialog.Builder(clsSearch.this).setTitle("Lỗi Nhập")
					.setMessage("Ngày tháng không đúng (dd/mm/yyyy)").setPositiveButton("OK", null).show();
			return false;
		}

		if (edtDateTo.getText().toString().trim().length() != 0 && !CommonUtil.isValidDate(edtDateTo.getText().toString())) {
			new AlertDialog.Builder(clsSearch.this).setTitle("Lỗi Nhập")
					.setMessage("Ngày tháng không đúng (dd/mm/yyyy)").setPositiveButton("OK", null).show();
			return false;
		}

		if (edtAmountFrom.getText().toString().trim().length() != 0 && !CommonUtil.checkFloat(edtAmountFrom.getText().toString())) {
			new AlertDialog.Builder(clsSearch.this).setTitle("Lỗi").setMessage("Vui lòng nhập số")
					.setPositiveButton("OK", null).show();
			return false;
		}

		if (edtAmountTo.getText().toString().trim().length() != 0 && !CommonUtil.checkFloat(edtAmountTo.getText().toString().trim())) {
			new AlertDialog.Builder(clsSearch.this).setTitle("Lỗi").setMessage("Vui lòng nhập số")
					.setPositiveButton("OK", null).show();
			return false;
		}
		return true;

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