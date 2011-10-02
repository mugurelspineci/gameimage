package spending.com;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class clsDelete extends Activity {
	private static final String TAG = clsDelete.class.getSimpleName();
	RadioButton rdIncome;
	RadioButton rdPayment;
	EditText edtDateFrom;
	EditText edtDateTo;
	Button btnDelete;
	Button btnCancel;
	SpendingDbAdapter mDbHelper;

	@Override
	public void onCreate(Bundle saved) {
		super.onCreate(saved);
		setContentView(R.layout.search);
		try {

			mDbHelper = new SpendingDbAdapter(this);
			edtDateFrom = (EditText) findViewById(R.id.edtDateFrom);
			edtDateTo = (EditText) findViewById(R.id.edtDateTo);

			btnDelete = (Button) findViewById(R.id.btnDelete);
			btnCancel = (Button) findViewById(R.id.btnCancel);
			rdIncome = (RadioButton) findViewById(R.id.rdIncome);
			rdPayment = (RadioButton) findViewById(R.id.rdPayment);

			btnDelete.setOnClickListener(new View.OnClickListener() {
				public void onClick(View view) {

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
}
