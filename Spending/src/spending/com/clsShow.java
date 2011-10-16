package spending.com;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class clsShow extends Activity {

	private static final String TAG = clsShow.class.getSimpleName();
	EditText edtAmountFrom;
	TextView txtSum;
	Button btnBack;
	Button btnHome;

	@Override
	public void onCreate(Bundle saved) {
		super.onCreate(saved);
		double sum = 0;
		setContentView(R.layout.show);
		txtSum = (TextView) findViewById(R.id.txtSum);
		// clsCustomBaseAdapter show;
		try {
			Bundle nBundle = getIntent().getExtras();
			ArrayList<clsData> myArray = nBundle.getParcelableArrayList("DATA");
			if (myArray == null) {
				Log.i(TAG, "***** onCreate() Khong co du lieu ");
				return;
			}

			final ListView lv1 = (ListView) findViewById(R.id.ListView01);
			lv1.setAdapter(new clsCustomBaseAdapter(this, myArray));
			for (clsData data : myArray) {
				if (data.getPay() == 1)
					sum += Double.parseDouble(data.getAmount());
				else if (data.getPay() == 2)
					sum -= Double.parseDouble(data.getAmount());
			}
			// show = new clsCustomBaseAdapter(this, myArray);
			// lv1.setAdapter(show);

			txtSum.setText(sum + "");

			// btnBack = (Button)findViewById(R.id.btnBack);
			// btnHome = (Button)findViewById(R.id.btnHome);

			// btnBack.setOnClickListener(new View.OnClickListener(){
			// public void onClick(View view){
			// Intent iData = new Intent();
			// iData.putExtra( "BACK", "search" );
			// setResult( android.app.Activity.RESULT_OK, iData );
			// finish();
			// }
			// });

			// btnHome.setOnClickListener(new View.OnClickListener(){
			// public void onClick(View view){
			// Intent iData = new Intent();
			// iData.putExtra( "BACK", "home" );
			// setResult( android.app.Activity.RESULT_OK, iData );
			// finish();
			// }
			// });
			Log.i(TAG, "***** onCreate() Called");
		} catch (Exception ex) {
			Log.i(TAG, "***** onCreate() Error: " + ex.getMessage());
		}
	}

}