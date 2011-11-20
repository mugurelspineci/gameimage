package spending.com;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class clsShow extends Activity {

	private static final String TAG = clsShow.class.getSimpleName();
	EditText edtAmountFrom;
	TextView txtSum;
	CheckBox ckDelete;
	ListView lv1;
	SpendingDbAdapter mDbHelper;
	ArrayList<clsData> myArray;
	String rowid;
	int pos=0;

	@Override
	public void onCreate(Bundle saved) {
		super.onCreate(saved);
		double sum = 0;
		setContentView(R.layout.show);
		txtSum = (TextView) findViewById(R.id.txtSum);
		ckDelete = (CheckBox) findViewById(R.id.chkDelete);
		mDbHelper = new SpendingDbAdapter(this);
		// clsCustomBaseAdapter show;
		try {
			Bundle nBundle = getIntent().getExtras();
			myArray = nBundle.getParcelableArrayList("DATA");
			if (myArray == null) {
				Log.i(TAG, "***** onCreate() Khong co du lieu ");
				return;
			}

			lv1 = (ListView) findViewById(R.id.ListView01);
			lv1.setAdapter(new clsCustomBaseAdapter(this, myArray));
			OnItemClickListener listener;
			listener = new OnItemClickListener() {
				public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
					// TODO Auto-generated method stub
					String msg;
					if (ckDelete.isChecked()) {
						clsData cls = (clsData) lv1.getItemAtPosition(position);
						rowid = "" + cls.getDataId();
						msg = String.format(clsContant.msgDelete, cls.getAmount(), cls.getDatePay());
						 dialogMsgImage(msg);
						 pos = position;
						
						// lv1.setAdapter(new clsCustomBaseAdapter(this, myArray));
					}
					// Toast.makeText(clsShow.this, "Setting is Selected " + cls.getReason(),
					// Toast.LENGTH_SHORT).show();
				}
			};

			lv1.setOnItemClickListener(listener);

			for (clsData data : myArray) {
				if (data.getPay() == 1)
					sum += Double.parseDouble(data.getAmount());
				else if (data.getPay() == 2)
					sum -= Double.parseDouble(data.getAmount());
			}
			txtSum.setText(sum + "");

			Log.i(TAG, "***** onCreate() Called");
		} catch (Exception ex) {
			Log.i(TAG, "***** onCreate() Error: " + ex.getMessage());
		}
	}

	private void removeData() {
		try {
			myArray.remove(pos);
			lv1.setAdapter(new clsCustomBaseAdapter(this, myArray));
			Log.i(TAG, "***** removeData() Called");
		} catch (Exception ex) {
			Log.i(TAG, "***** removeData() Error: " + ex.getMessage());
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

	public void deleteData() {
		AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
		try {
			mDbHelper.open();			
			if (mDbHelper.deleteData(rowid) == true) {
				removeData();
				alt_bld.setMessage(clsContant.msgDelFinish).setCancelable(false)
						.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// Action for 'Yes' Button
								//!!!Error_khong vao ham 
								// deleteData();								
							}
						});
				Log.i(TAG, "***** deleteData() Da xoa");
			} else {
				alt_bld.setMessage(clsContant.errDel).setCancelable(false)
						.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// Action for 'Yes' Button
								// deleteData();
							}
						});
				Log.i(TAG, "***** deleteData(): Loi trong qua trinh xoa");
			}
		} catch (Exception ex) {
			Log.i(TAG, "***** deleteData() Error: " + ex.getMessage());
		}
	}

	private void dialogMsgImage(String dg) {
		AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
		alt_bld.setMessage(dg).setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
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