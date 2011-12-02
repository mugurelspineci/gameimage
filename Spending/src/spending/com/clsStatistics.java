package spending.com;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class clsStatistics extends Activity implements SurfaceHolder.Callback {
	private static final String TAG = clsStatistics.class.getSimpleName();
	EditText edtDateFrom;
	EditText edtDateTo;
	Button btnStatistics;
	Button btnCancel;
	TextView txtIncome;
	TextView txtPayment;
	TextView txtBalance;

	SpendingDbAdapter mDbHelper;
	String currTime; // luu ngay thang hien tai

	private SurfaceView mSurfaceView;
	private SurfaceHolder mSurfaceHolder;
	private int scnWidth;
	private int scnHeight;
	ArrayList<clsData> arrList;

	@SuppressWarnings("static-access")
	@Override
	public void onCreate(Bundle saved) {
		super.onCreate(saved);
		setContentView(R.layout.statistics);
		try {

			mDbHelper = new SpendingDbAdapter(this);
			edtDateFrom = (EditText) findViewById(R.id.edtDateFrom);
			edtDateTo = (EditText) findViewById(R.id.edtDateTo);
			txtIncome = (TextView) findViewById(R.id.txtIncomeA);
			txtPayment = (TextView) findViewById(R.id.txtPaymentA);
			txtBalance = (TextView) findViewById(R.id.txtBalanceA);
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

			// Lay do phan giai cua man hinh
			DisplayMetrics dm = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(dm);

			scnWidth = dm.widthPixels;
			scnHeight = dm.heightPixels;

			mSurfaceView = (SurfaceView) this.findViewById(R.id.Paper);
			mSurfaceHolder = mSurfaceView.getHolder();
			mSurfaceHolder.addCallback(this);

			loadEventText();
			// mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_NORMAL);
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

	private void loadEventText() {
		edtDateFrom.setOnClickListener(new View.OnClickListener() {
			// @Override
			public void onClick(View v) {
				final Calendar c = Calendar.getInstance();
				int y = c.get(Calendar.YEAR);
				int m = c.get(Calendar.MONTH);
				int d = c.get(Calendar.DAY_OF_MONTH);
				DatePickerDialog dp = new DatePickerDialog(clsStatistics.this,
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
				DatePickerDialog dp = new DatePickerDialog(clsStatistics.this,
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

	private void getStatistics() {
		float income, payment;
		String strDate1, strDate2;
		String[] arrDate;
		String balance;
		try {
			
			arrDate = edtDateFrom.getText().toString().trim().split("/");
			if (arrDate.length != 3)
				strDate1 = "";
			else			
				strDate1 = arrDate[2] + "/" + arrDate[1] + "/" + arrDate[0];

			arrDate = edtDateTo.getText().toString().trim() .split("/");
			if (arrDate.length != 3)
				strDate2 = "";
			else
				strDate2 = arrDate[2] + "/" + arrDate[1] + "/" + arrDate[0];
			
			mDbHelper.open();
			arrList = mDbHelper.SelectStatistics(strDate1, strDate2);
			if (arrList == null || arrList.size() < 2) {
				new AlertDialog.Builder(clsStatistics.this).setTitle("Lỗi Nhập").setMessage("Dữ liệu không có")
						.setPositiveButton("OK", null).show();
				return;
			}
			clearData();

			income = Float.parseFloat(arrList.get(0).getAmount());
			payment = Float.parseFloat(arrList.get(1).getAmount());

			txtIncome.setText(arrList.get(0).getAmount());
			txtPayment.setText(arrList.get(1).getAmount());

			DecimalFormat fmt = new DecimalFormat("0.00");
			balance = CommonUtil.trimZeros(fmt.format(income - payment));
			txtBalance.setText(balance);

			drawLine(income, payment);
		} catch (Exception ex) {
			Log.i(TAG, "***** getStatistics() Error: " + ex.getMessage());
		}
	}

	private void drawLine(float income, float payment) {
		// Rect rect;
		Paint paint;
		Canvas canvas;
		float width;
		float height;
		float startHeight;
		// float income, payment;
		float hIncome, hPayment;
		int[] location = new int[2];
		try {
			canvas = mSurfaceHolder.lockCanvas();
			canvas.drawColor(Color.BLACK);
			if (income == 0 && payment == 0) {
				mSurfaceHolder.unlockCanvasAndPost(canvas);
				return;
			}
			paint = new Paint();

			// lay toa do x, y cua control surfaceView
			mSurfaceView.getLocationOnScreen(location);

			height = scnHeight - location[1];
			width = scnWidth / 2;
			startHeight = height - 100;
			if (income == payment) {
				hPayment = startHeight / 2;
				hIncome = startHeight / 2;
			}
			if (income > payment) {
				hIncome = startHeight - 20;
				hPayment = hIncome * payment / income;
			} else {
				hPayment = startHeight - 20;
				hIncome = hPayment * income / payment;
			}

			paint.setColor(Color.BLUE);
			canvas.drawRect(width - 50, startHeight - hIncome, width, startHeight, paint);
			canvas.drawText("Thu", width - 35, startHeight + 15, paint);

			paint.setColor(Color.RED);
			canvas.drawRect(width, startHeight - hPayment, width + 50, startHeight, paint);
			canvas.drawText("Chi", width + 12, startHeight + 16, paint);

			paint.setColor(Color.GREEN);
			canvas.drawLine(0, startHeight, scnWidth, startHeight, paint);

			mSurfaceHolder.unlockCanvasAndPost(canvas);

			Log.i(TAG, "***** drawLine() da ve ");
		} catch (Exception ex) {
			Log.i(TAG, "***** drawLine() Error: " + ex.getMessage());
		} finally {

		}
	}

	private boolean checkValid() {
		if (edtDateFrom.getText().toString().trim().length() != 0
				&& !CommonUtil.isValidDate(edtDateFrom.getText().toString())) {
			new AlertDialog.Builder(clsStatistics.this).setTitle("Lỗi Nhập")
					.setMessage("Ngày tháng không đúng (dd/mm/yyyy)").setPositiveButton("OK", null).show();
			return false;
		}

		if (edtDateTo.getText().toString().trim().length() != 0
				&& !CommonUtil.isValidDate(edtDateTo.getText().toString())) {
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

	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// TODO Auto-generated method stub

	}

	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		// mSurfaceView.setBackgroundColor(Color.CYAN);
		// drawLine();
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub

	}
}
