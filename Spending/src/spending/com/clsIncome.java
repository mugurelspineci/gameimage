package spending.com;

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

	@SuppressWarnings({ "unchecked", "rawtypes", "static-access" })
	@Override
	public void onCreate(Bundle saved) {
		super.onCreate(saved);
		setContentView(R.layout.income);

		String arrReason[] = { "Lương", "Ăn sáng", "Xăng", "Thay nhớt", "Đi siêu thị" };
		ArrayAdapter<String> adapter;
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

			adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrReason);
			spnReason.setAdapter(adapter);

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
		} catch (Exception ex) {
			Log.i(TAG, "***** onCreate() Error: " + ex.getMessage());
		}
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
		} 
		else if (!CommonUtil.isValidDate(edtDate.getText().toString())) {
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
			insert = mDbHelper.insert(Float.parseFloat(edtAmount.getText().toString()), edtDate.getText().toString(),
					income, reason, edtComment.getText().toString());
			if (insert > 0) {
				new AlertDialog.Builder(clsIncome.this).setTitle("Lưu").setMessage("Đã lưu thành công")
						.setPositiveButton("OK", null).show();
				clearData();
			}
			//Log.i(TAG, "***** saveData() Da luu xuong db, id=" + insert);
		} catch (Exception ex) {
			new AlertDialog.Builder(clsIncome.this).setTitle("Lưu").setMessage("Không thể lưu")
			.setPositiveButton("OK", null).show();
			Log.i(TAG, "***** saveData() Error: " + ex.getMessage());
		}
	}

	private void clearData() {
		edtAmount.setText("");
		// edtDate.setText("");
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
