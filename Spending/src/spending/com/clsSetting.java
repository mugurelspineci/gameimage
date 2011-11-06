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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class clsSetting extends Activity {
	private static final String TAG = clsSetting.class.getSimpleName();
	EditText edtReason;
	Button btnSave;
	Button btnCancel;
	String strReason;
	ListView lv1;
	View view;
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
					if (edtReason.getText().toString().length() != 0)
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
			// Log.i(TAG, "***** saveData() Da luu xuong db, id=" + insert);
		} catch (Exception ex) {
			new AlertDialog.Builder(clsSetting.this).setTitle("Lưu").setMessage("Không thể lưu")
					.setPositiveButton("OK", null).show();
			Log.i(TAG, "***** saveData() Error: " + ex.getMessage());
		}
	}

	private void loadData() {
		ArrayList<String> arrList;
		ArrayList<clsData> myArray = new ArrayList<clsData>();
		clsData data;
		arrList = mDbHelper.SelectReason();
		if (arrList == null)
			return;

		for (String reason : arrList) {
			data = new clsData(reason);
			myArray.add(data);
		}

		lv1 = (ListView) findViewById(R.id.lstReason);
		lv1.setAdapter(new clsReasonBaseAdapter(this, myArray));

		OnItemClickListener listener;
		listener = new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
				// TODO Auto-generated method stub
				String msg;
				clsData clsReason = (clsData) lv1.getItemAtPosition(position);
				strReason = clsReason.getReason();
				// v1.removeViewAt(position);
				view = v;
				msg = String.format(clsContant.msgDelete2, strReason);
				dialogMsgImage(msg);
				// Toast.makeText(clsShow.this, "Setting is Selected " + cls.getReason(),
				// Toast.LENGTH_SHORT).show();
			}
		};

		lv1.setOnItemClickListener(listener);
		// Log.i(TAG, "***** loadData() called");
	}

	private void dialogMsgImage(String dg) {
		AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
		alt_bld.setMessage(dg).setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// Action for 'Yes' Button
				deleteReason();
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

	private void deleteReason() {
		AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
		try {
			mDbHelper.open();
			if (mDbHelper.deleteReason(strReason) == true) {
				clearData();
				loadData();
				// lv1..clearChoices();
				alt_bld.setMessage(clsContant.msgDelFinish).setCancelable(false)
						.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// Action for 'Yes' Button

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

	private void clearData() {
		edtReason.setText("");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		try {
			if (mDbHelper != null) {
				mDbHelper.close();
				Log.i(TAG, "***** onDestroy() da vao");
			}
		} catch (Exception ex) {
			Log.i(TAG, "***** onDestroy() Error: " + ex.getMessage());
		}
	}
}
