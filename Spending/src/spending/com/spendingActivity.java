package spending.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class spendingActivity extends Activity {
	/** Called when the activity is first created. */
	private static final String TAG = spendingActivity.class.getSimpleName();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		try {
			ImageView imgIncome = (ImageView) findViewById(R.id.imgIncome);
			imgIncome.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent income = new Intent(v.getContext(), clsIncome.class);
					startActivity(income);
					// Intent myIntent = new Intent(view.getContext(), Activity2.class);
					// startActivityForResult(myIntent, 0);
				}
			});

			ImageView imgPayment = (ImageView) findViewById(R.id.imgPayment);
			imgPayment.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent payment = new Intent(v.getContext(), clsPayment.class);
					startActivity(payment);
				}
			});

			ImageView imgSearch = (ImageView) findViewById(R.id.imgSearch);
			imgSearch.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent search = new Intent(v.getContext(), clsSearch.class);
					startActivity(search);
				}
			});

			ImageView imgStatistics = (ImageView) findViewById(R.id.imgStatistics);
			imgStatistics.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					// Intent inStatistics = new Intent(v.getContext(), cls.class);
					// startActivity(inStatistics);
				}
			});
		} catch (Exception ex) {
			Log.i(TAG, "***** onCreate() Error: " + ex.getMessage());
		}
	}
}