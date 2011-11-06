package spending.com;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

public class clsDelete extends Activity {
	private static final String TAG = clsDelete.class.getSimpleName();
	EditText edtDateFrom;
	EditText edtDateTo;
	Button btnDelete;
	Button btnCancel;
	SpendingDbAdapter mDbHelper;
	String currTime; // luu ngay thang hien tai

	@SuppressWarnings("static-access")
	@Override
	public void onCreate(Bundle saved) {
		super.onCreate(saved);
		setContentView(R.layout.delete);
		try {

			mDbHelper = new SpendingDbAdapter(this);
			edtDateFrom = (EditText) findViewById(R.id.edtDateFrom);
			edtDateTo = (EditText) findViewById(R.id.edtDateTo);

			btnDelete = (Button) findViewById(R.id.btnDelete);
			btnCancel = (Button) findViewById(R.id.btnCancel);

			android.text.format.DateFormat datetime = new android.text.format.DateFormat();
			currTime = "" + datetime.format("dd/MM/yyyy", new Date());
			edtDateTo.setText(currTime);

			btnDelete.setOnClickListener(new View.OnClickListener() {
				public void onClick(View view) {
					if (checkValid())
						dialogMsgImage("Bạn có muốn xóa không?");
				}
			});

			btnCancel.setOnClickListener(new View.OnClickListener() {
				public void onClick(View view) {
					finish();
				}
			});
			
			loadEventText();
		} catch (Exception ex) {
			Log.i(TAG, "***** onCreate() Error: " + ex.getMessage());
		}
	}

	public void dialogMsgImage(String dg) {
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

	private void deleteData() {
		AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
		try {
			mDbHelper.open();
			if (mDbHelper.deleteData(edtDateFrom.getText().toString(), edtDateTo.getText().toString()) == true) {
				clearData();
				alt_bld.setMessage(clsContant.msgDelFinish).setCancelable(false)
						.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// Action for 'Yes' Button
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

	private void loadEventText() {
		edtDateFrom.setOnClickListener(new View.OnClickListener() {
			// @Override
			public void onClick(View v) {
				final Calendar c = Calendar.getInstance();
				int y = c.get(Calendar.YEAR);
				int m = c.get(Calendar.MONTH);
				int d = c.get(Calendar.DAY_OF_MONTH);
				DatePickerDialog dp = new DatePickerDialog(clsDelete.this, new DatePickerDialog.OnDateSetListener() {
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
				DatePickerDialog dp = new DatePickerDialog(clsDelete.this, new DatePickerDialog.OnDateSetListener() {
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

	private boolean checkValid() {
		if (edtDateFrom.getText().toString().length() == 0 && edtDateTo.getText().toString().length() != 0)
			return true;
		if (edtDateFrom.getText().toString().length() != 0 && !CommonUtil.isValidDate(edtDateFrom.getText().toString())) {
			new AlertDialog.Builder(clsDelete.this).setTitle("Lỗi Nhập")
					.setMessage("Ngày tháng không đúng (dd/mm/yyyy)").setPositiveButton("OK", null).show();
			return false;
		}

		if (edtDateTo.getText().toString().length() != 0 && !CommonUtil.isValidDate(edtDateTo.getText().toString())) {
			new AlertDialog.Builder(clsDelete.this).setTitle("Lỗi Nhập")
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
