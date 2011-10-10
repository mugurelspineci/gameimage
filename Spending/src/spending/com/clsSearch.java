package spending.com;

import java.util.ArrayList;

import android.app.Activity;
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void onCreate(Bundle saved) {
		super.onCreate(saved);
		setContentView(R.layout.search);
		try {
			String arrReason[] = { "Lương", "Ăn sáng", "Xăng", "Thay nhớt", "Đi siêu thị" };
			ArrayAdapter<String> adapter;

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

			adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrReason);
			spnReason.setAdapter(adapter);

			btnSearch.setOnClickListener(new View.OnClickListener() {
				public void onClick(View view) {
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
				reason = edtOther.getText().toString();
			else
				reason = spnReason.getSelectedItem().toString();

			if (rdIncome.isChecked())
				pay = 1;
			else if (rdPayment.isChecked())
				pay = 2;
			else
				pay = 3;
			
			arrList = mDbHelper.SelectData(edtDateFrom.getText().toString().trim(), edtDateTo.getText().toString(),
					edtAmountFrom.getText().toString(), edtAmountTo.getText().toString(), reason, pay);
			//arrList = mDbHelper.SelectAll();
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