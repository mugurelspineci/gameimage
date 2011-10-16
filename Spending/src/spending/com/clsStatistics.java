package spending.com;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class clsStatistics extends Activity{
	private static final String TAG = clsDelete.class.getSimpleName();
	EditText edtDateFrom;
	EditText edtDateTo;
	Button btnStatistics;
	Button btnCancel;
	SpendingDbAdapter mDbHelper;

	@Override
	public void onCreate(Bundle saved) {
		super.onCreate(saved);
		setContentView(R.layout.statistics);
		try {

			mDbHelper = new SpendingDbAdapter(this);
			edtDateFrom = (EditText) findViewById(R.id.edtDateFrom);
			edtDateTo = (EditText) findViewById(R.id.edtDateTo);

			btnStatistics = (Button) findViewById(R.id.btnStatistics);
			btnCancel = (Button) findViewById(R.id.btnCancel);

			btnStatistics.setOnClickListener(new View.OnClickListener() {
				public void onClick(View view) {
					//dialogMsgImage("Bạn có muốn xóa không?");
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

	public void dialogMsgImage(String dg) {
		AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
		alt_bld.setMessage(dg).setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// Action for 'Yes' Button
				
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
}
