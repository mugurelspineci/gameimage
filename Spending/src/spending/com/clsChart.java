package spending.com;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.EditText;
import android.widget.ListView;

public class clsChart extends Activity implements SurfaceHolder.Callback {

	
	private static final String TAG = clsSearch.class.getSimpleName();
	EditText edtTemp;
	private SurfaceView mSurfaceView;
	private SurfaceHolder mSurfaceHolder;
	
	@Override
	public void onCreate(Bundle saved) {
		super.onCreate(saved);
		setContentView(R.layout.chart);
		
		try {
			edtTemp = (EditText) findViewById(R.id.edtTemp);
			
			Bundle nBundle = getIntent().getExtras();
			ArrayList<clsData> myArray = nBundle.getParcelableArrayList("DATA");
			if (myArray == null) {
				Log.i(TAG, "***** onCreate() Khong co du lieu ");
				return;
			}

			for (clsData data : myArray) {
				edtTemp.setText(data.getAmount());
			}
			  mSurfaceView = (SurfaceView) this.findViewById(R.id.Paper);
			  mSurfaceHolder = mSurfaceView.getHolder();
			  mSurfaceHolder.addCallback(this);
		      mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_NORMAL);
		        
			//setContentView(new clsChartShow(this, 1, 2));
		} catch (Exception ex) {
			Log.i(TAG, "***** onCreate() Error: " + ex.getMessage());
		}
	}

	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		 mSurfaceView.setBackgroundColor(Color.CYAN);
	}

	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		
	}
}
