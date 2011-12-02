package spending.com;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.util.Log;

public class clsIncome extends Activity {

	private static final String TAG = clsIncome.class.getSimpleName();
	RadioButton rdIncome;
	RadioButton rdPayment;
	EditText edtAmount;
	EditText edtDate;
	Spinner spnReason;
	EditText edtOther;
	EditText edtComment;
	Button btnSave;
	Button btnCancel;

	SpendingDbAdapter mDbHelper;
	String currTime; // luu ngay thang hien tai

	@SuppressWarnings("static-access")
	@Override
	public void onCreate(Bundle saved) {
		super.onCreate(saved);
		setContentView(R.layout.income);

		// String arrReason[] = { "Lương", "Ăn sáng", "Xăng", "Thay nhớt", "Đi siêu thị" };
		// ArrayAdapter<String> adapter;
		try {

			mDbHelper = new SpendingDbAdapter(this);
			mDbHelper.open();

			rdIncome = (RadioButton) findViewById(R.id.rdIncome);
			rdPayment = (RadioButton) findViewById(R.id.rdPayment);
			edtAmount = (EditText) findViewById(R.id.edtAmount);
			edtDate = (EditText) findViewById(R.id.edtDate);
			spnReason = (Spinner) findViewById(R.id.spnReason);
			edtOther = (EditText) findViewById(R.id.edtOther);
			edtComment = (EditText) findViewById(R.id.edtComment);
			btnSave = (Button) findViewById(R.id.btnSave);
			btnCancel = (Button) findViewById(R.id.btnCancel);

			// currTime = DateFormat.getDateFormat(this).format(new Date());
			android.text.format.DateFormat datetime = new android.text.format.DateFormat();
			currTime = "" + datetime.format("dd/MM/yyyy", new Date());
			edtDate.setText(currTime);

			// adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrReason);
			// spnReason.setAdapter(adapter);

			btnSave.setOnClickListener(new View.OnClickListener() {
				public void onClick(View view) {
					if (checkValid() == true)
						saveData();
				}
			});

			btnCancel.setOnClickListener(new View.OnClickListener() {
				public void onClick(View view) {
					finish();
				}
			});

			edtDate.setOnClickListener(new View.OnClickListener() {
				// @Override
				public void onClick(View v) {
					final Calendar c = Calendar.getInstance();
					int y = c.get(Calendar.YEAR);
					int m = c.get(Calendar.MONTH);
					int d = c.get(Calendar.DAY_OF_MONTH);
					DatePickerDialog dp = new DatePickerDialog(clsIncome.this,
							new DatePickerDialog.OnDateSetListener() {
								public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
									String erg = "";
									erg = String.valueOf(dayOfMonth);
									erg += "/" + String.valueOf(monthOfYear + 1);
									erg += "/" + year;
									edtDate.setText(erg);
								}
							}, y, m, d);
					dp.setTitle("Ngay Thang Nam");
					dp.show();
				}
			});

			loadData();
		} catch (Exception ex) {
			Log.i(TAG, "***** onCreate() Error: " + ex.getMessage());
		}
	}

	/* Initiating Menu XML file (menu.xml) */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.layout.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.mnIncome:
			// Single menu item is selected do something
			// Ex: launching new activity/screen or show alert message
			// Toast.makeText(spendingActivity.this, "Income is Selected", Toast.LENGTH_SHORT).show();
			Intent income = new Intent(this, clsIncome.class);
			startActivity(income);
			return true;
		case R.id.mnDelete:
			// Toast.makeText(spendingActivity.this, "Payment is Selected", Toast.LENGTH_SHORT).show();
			Intent delete = new Intent(this, clsDelete.class);
			startActivity(delete);
			return true;
		case R.id.mnSearch:
			// Toast.makeText(spendingActivity.this, "Search is Selected", Toast.LENGTH_SHORT).show();
			Intent search = new Intent(this, clsSearch.class);
			startActivity(search);
			return true;
		case R.id.mnStatistics:
			// Toast.makeText(spendingActivity.this, "Statistics is Selected", Toast.LENGTH_SHORT).show();
			Intent statistics = new Intent(this, clsStatistics.class);
			startActivity(statistics);
			return true;
		case R.id.mnSetting:
			// Toast.makeText(spendingActivity.this, "Setting is Selected", Toast.LENGTH_SHORT).show();
			Intent setting = new Intent(this, clsSetting.class);
			startActivity(setting);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void loadData() {
		ArrayList<String> arrList = new ArrayList<String>();
		ArrayAdapter<String> adapter;
		arrList = mDbHelper.SelectReason();
		if (arrList == null)
			return;
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrList);
		spnReason.setAdapter(adapter);
		Log.i(TAG, "***** loadData() called");
	}

	private boolean checkValid() {
		if (rdIncome.isChecked() == false && rdPayment.isChecked() == false) {
			new AlertDialog.Builder(clsIncome.this).setTitle("Nhập thiếu").setMessage("Chưa chọn Thu hay Chi")
					.setPositiveButton("OK", null).show();
			return false;
		}
		if (edtAmount.getText().length() == 0) {
			new AlertDialog.Builder(clsIncome.this).setTitle("Nhập thiếu").setMessage("Chưa nhập số tiền")
					.setPositiveButton("OK", null).show();
			return false;
		}
		if (edtDate.getText().length() == 0) {
			return false;
		} else if (!CommonUtil.isValidDate(edtDate.getText().toString())) {
			new AlertDialog.Builder(clsIncome.this).setTitle("Lỗi Nhập")
					.setMessage("Ngày tháng không đúng (dd/mm/yyyy)").setPositiveButton("OK", null).show();
			return false;
		}
		if (!CommonUtil.checkFloat(edtAmount.getText().toString())) {
			new AlertDialog.Builder(clsIncome.this).setTitle("Lỗi").setMessage("Vui lòng nhập số")
					.setPositiveButton("OK", null).show();
			return false;
		}
		return true;
	}

	private void saveData() {
		// SpendingDbAdapter db;
		String reason;
		int income = 0;
		long insert;
		String[] arrDate;
		String strDate;
		try {
			if (edtOther.getText().toString().length() != 0)
				reason = edtOther.getText().toString();
			else
				reason = spnReason.getSelectedItem().toString();

			if (rdIncome.isChecked() == true) {
				income = 1;
			} else {
				income = 2;
			}
			arrDate = edtDate.getText().toString().split("/");
			if (arrDate.length != 3)
				strDate = "";
			else
				strDate = arrDate[2] + "/" + arrDate[1] + "/" + arrDate[0];
			insert = mDbHelper.insert(Float.parseFloat(edtAmount.getText().toString()), strDate, income, reason,
					edtComment.getText().toString());
			if (insert > 0) {
				new AlertDialog.Builder(clsIncome.this).setTitle("Lưu").setMessage("Đã lưu thành công")
						.setPositiveButton("OK", null).show();
				clearData();
			}
			// Log.i(TAG, "***** saveData() Da luu xuong db, id=" + insert);
		} catch (Exception ex) {
			new AlertDialog.Builder(clsIncome.this).setTitle("Lưu").setMessage("Không thể lưu")
					.setPositiveButton("OK", null).show();
			Log.i(TAG, "***** saveData() Error: " + ex.getMessage());
		}
	}

	private void clearData() {
		edtAmount.setText("");
		edtDate.setText(currTime);
		edtOther.setText("");
		edtComment.setText("");
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
