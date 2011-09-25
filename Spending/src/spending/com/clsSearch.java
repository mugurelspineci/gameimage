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
	RadioButton rb1;
	RadioButton rb2;
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
			String arrReason[] = { "AA", "BB", "CC" };
			ArrayAdapter<String> adapter;

			mDbHelper = new SpendingDbAdapter(this);
			mDbHelper.open();

			edtAmountFrom = (EditText) findViewById(R.id.edtAmountFrom);
			edtAmountTo = (EditText) findViewById(R.id.edtAmountTo);
			edtDateFrom = (EditText) findViewById(R.id.edtDateFrom);
			spnReason = (Spinner) findViewById(R.id.spnReason);
			edtOther = (EditText) findViewById(R.id.edtOther);
			btnSearch = (Button) findViewById(R.id.btnSearch);
			btnCancel = (Button) findViewById(R.id.btnCancel);

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
		String cond;
		ArrayList<clsData> arrList;
		ArrayList<clsData> arrListData;
		try {
			cond = getCondition();

			SpendingDbAdapter dba = new SpendingDbAdapter(this);

			arrList = dba.SelectData(cond);
			if (arrList == null)
				return;

			// Intent i = new Intent(this, clsShow.class);
			// i.putExtra("SPENDING", arrList);
			// startActivityForResult(i, REQUEST_CODE);
			// startActivity(i);
			// startActivityForResult(i, 0);

			Intent intent = new Intent().setClass(this, clsSearch.class);
			arrListData = new ArrayList<clsData>();
			for (int i = 0; i < arrList.size(); i++)
				arrListData.add(arrList.get(i));
			intent.putParcelableArrayListExtra("DATA", arrListData);
			startActivity(intent);
		} catch (Exception ex) {
			Log.i(TAG, "***** searchData() Error: " + ex.getMessage());
		}
	}

	private String getCondition() {
		String cond = " WHERE 1=1 ";
		if (edtDateFrom.getText().toString().trim().length() != 0
				&& edtDateTo.getText().toString().trim().length() != 0) {
			cond += " AND " + clsContant.KEY_DATE_PAY + ">" + edtDateFrom.getText().toString().trim() + " AND "
					+ clsContant.KEY_DATE_PAY + "<" + edtDateTo.getText().toString().trim();
		} else if (edtDateFrom.getText().toString().trim().length() != 0
				&& edtDateTo.getText().toString().trim().length() == 0) {
			cond += " AND " + clsContant.KEY_DATE_PAY + ">" + edtDateFrom.getText().toString().trim();
		} else if (edtDateFrom.getText().toString().trim().length() == 0
				&& edtDateTo.getText().toString().trim().length() != 0) {
			cond += " AND " + clsContant.KEY_DATE_PAY + "<" + edtDateTo.getText().toString().trim();
		}

		if (edtAmountFrom.getText().toString().trim().length() != 0
				&& edtAmountTo.getText().toString().trim().length() != 0) {
			cond += " AND " + clsContant.KEY_AMOUNT + ">" + edtAmountFrom.getText().toString().trim() + " AND "
					+ clsContant.KEY_AMOUNT + "<" + edtAmountTo.getText().toString().trim();
		} else if (edtAmountFrom.getText().toString().trim().length() != 0
				&& edtAmountTo.getText().toString().trim().length() == 0) {
			cond += " AND " + clsContant.KEY_AMOUNT + ">" + edtAmountFrom.getText().toString().trim();
		} else if (edtAmountFrom.getText().toString().trim().length() == 0
				&& edtAmountTo.getText().toString().trim().length() != 0) {
			cond += " AND " + clsContant.KEY_AMOUNT + "<" + edtAmountTo.getText().toString().trim();
		}

		if (spnReason.getSelectedItem().toString().length() != 0) {
			cond += " AND " + clsContant.KEY_REASON + " like %" + spnReason.getSelectedItem().toString() + "%";
		}

		if (edtOther.getText().toString().length() != 0) {
			cond += " AND " + clsContant.KEY_REASON + " like %" + edtOther.getText().toString() + "%";
		}

		if (rb1.isChecked() == true) {
			cond += " AND " + clsContant.KEY_PAY + "=1 ";
		} else {
			cond += " AND " + clsContant.KEY_PAY + "=0";
		}
		return cond;
	}

}