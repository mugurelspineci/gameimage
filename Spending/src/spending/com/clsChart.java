package spending.com;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class clsChart extends Activity {

	private static final String TAG = clsSearch.class.getSimpleName();

	@Override
	public void onCreate(Bundle saved) {
		super.onCreate(saved);
		setContentView(R.layout.chart);
		try {

		} catch (Exception ex) {
			Log.i(TAG, "***** onCreate() Error: " + ex.getMessage());
		}
	}
}
