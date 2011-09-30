package spending.com;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class clsPayment extends Activity {

	private static final String TAG = clsPayment.class.getSimpleName();
	EditText edtAmount;
	EditText edtDate;
	Spinner spnReason;
	EditText edtOther;
	EditText edtComment;
	Button btnSave;
	Button btnCancel;

	SpendingDbAdapter mDbHelper;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void onCreate(Bundle saved) {
		super.onCreate(saved);
		setContentView(R.layout.payment);

		String arrReason[] = { "AA", "BB", "CC" };
		ArrayAdapter<String> adapter;
		try {
			mDbHelper = new SpendingDbAdapter(this);
			mDbHelper.open();
			
			edtAmount = (EditText) findViewById(R.id.edtAmount);
			edtDate = (EditText) findViewById(R.id.edtDate);
			spnReason = (Spinner) findViewById(R.id.spnReason);
			edtOther = (EditText) findViewById(R.id.edtOther);
			edtComment = (EditText) findViewById(R.id.edtComment);
			btnSave = (Button) findViewById(R.id.btnSave);
			btnCancel = (Button) findViewById(R.id.btnCancel);

			// xem lai
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
					// Intent intent = new Intent();
					// setResult(RESULT_OK, intent);
					finish();
				}
			});
		} catch (Exception ex) {
			Log.i(TAG, "***** onCreate() Error: " + ex.getMessage());
		}
	}

	private boolean checkValid() {
		if (edtAmount.getText().length() == 0) {
			return false;
		}
		if (edtDate.getText().length() == 0) {
			return false;
		}
		if (spnReason.getSelectedItem().toString().length() == 0) {
			return false;
		}
		return true;
	}

	private void saveData() {
		String reason;
		try {
			if (edtOther.getText().toString().length() != 0)
				reason = edtOther.getText().toString();
			else
				reason = spnReason.getSelectedItem().toString();

			mDbHelper = new SpendingDbAdapter(this);
			mDbHelper.insert(Integer.parseInt(edtAmount.getText().toString()), edtDate.getText().toString(), 1, reason,
					edtComment.getText().toString());
			clearData();
		} catch (Exception ex) {
			Log.i(TAG, "***** saveData() Error: " + ex.getMessage());
		}
	}

	private void clearData() {
		edtAmount.setText("");
		edtDate.setText("");
		edtOther.setText("");
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