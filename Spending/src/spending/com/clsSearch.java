package spending.com;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
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

	@SuppressWarnings({ "static-access" })
	@Override
	public void onCreate(Bundle saved) {
		super.onCreate(saved);
		setContentView(R.layout.search);
		try {

			android.text.format.DateFormat datetime = new android.text.format.DateFormat();
			currTime = "" + datetime.format("dd/MM/yyyy", new Date());
			
			mDbHelper = new SpendingDbAdapter(this);
			mDbHelper.open();
			
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
			
			loadData();
			loadEventText();
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

	 /* Initiating Menu XML file (menu.xml) */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.layout.menu, menu);
        return true;
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item){	
		 switch (item.getItemId())
	        {
	        case R.id.mnIncome:
	        	// Single menu item is selected do something
	        	// Ex: launching new activity/screen or show alert message
	            //Toast.makeText(spendingActivity.this, "Income is Selected", Toast.LENGTH_SHORT).show();
	        	Intent income = new Intent(this, clsIncome.class);
				startActivity(income);
	            return true;
	        case R.id.mnDelete:
	        	//Toast.makeText(spendingActivity.this, "Payment is Selected", Toast.LENGTH_SHORT).show();
	        	Intent delete = new Intent(this, clsDelete.class);
				startActivity(delete);
	            return true;
	        case R.id.mnSearch:
	        	//Toast.makeText(spendingActivity.this, "Search is Selected", Toast.LENGTH_SHORT).show();
	        	Intent search = new Intent(this, clsSearch.class);
				startActivity(search);
	            return true;
	        case R.id.mnStatistics:
	        	//Toast.makeText(spendingActivity.this, "Statistics is Selected", Toast.LENGTH_SHORT).show();
	        	 Intent statistics = new Intent(this, clsStatistics.class);
				 startActivity(statistics);
	            return true;
	        case R.id.mnSetting:
	        	//Toast.makeText(spendingActivity.this, "Setting is Selected", Toast.LENGTH_SHORT).show();
	        	Intent setting = new Intent(this, clsSetting.class);
				 startActivity(setting);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	        }
	}
	
	private void loadData(){
		ArrayList<String> arrList = new ArrayList<String>();
		ArrayAdapter<String> adapter;
		arrList = mDbHelper.SelectReason();
		if (arrList == null)
			return;
		arrList.add("");
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrList);		
		spnReason.setAdapter(adapter);
		spnReason.setSelection(adapter.getCount()-1);
		Log.i(TAG, "***** loadData() called");
	}
	
	private void loadEventText(){
		edtDateFrom.setOnClickListener(new View.OnClickListener() {
			// @Override
			public void onClick(View v) {
				final Calendar c = Calendar.getInstance();
				int y = c.get(Calendar.YEAR);
				int m = c.get(Calendar.MONTH);
				int d = c.get(Calendar.DAY_OF_MONTH);
				DatePickerDialog dp = new DatePickerDialog(clsSearch.this,
						new DatePickerDialog.OnDateSetListener() {
							public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
								String erg = "";
								erg = String.valueOf(dayOfMonth);
								erg += "/" + String.valueOf(monthOfYear + 1);
								erg += "/" + year;
								edtDateFrom.setText(erg);
							}
						}, y, m, d);
				dp.setTitle("Ngay Thang Nam");
				dp.show();
			}
		});
		
		edtDateTo.setOnClickListener(new View.OnClickListener() {
			// @Override
			public void onClick(View v) {
				final Calendar c = Calendar.getInstance();
				int y = c.get(Calendar.YEAR);
				int m = c.get(Calendar.MONTH);
				int d = c.get(Calendar.DAY_OF_MONTH);
				DatePickerDialog dp = new DatePickerDialog(clsSearch.this,
						new DatePickerDialog.OnDateSetListener() {
							public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
								String erg = "";
								erg = String.valueOf(dayOfMonth);
								erg += "/" + String.valueOf(monthOfYear + 1);
								erg += "/" + year;
								edtDateTo.setText(erg);
							}
						}, y, m, d);
				dp.setTitle("Ngay Thang Nam");
				dp.show();
			}
		});
	}
	
	private void searchData() {
		// String cond;
		int pay;
		String reason;
		ArrayList<clsData> arrList;
		// ArrayList<clsData> arrListData;
		try {
			// cond = getCondition();
			//mDbHelper.open();

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