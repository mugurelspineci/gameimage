package spending.com;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

public class clsShow extends Activity{

	private static final String TAG = clsShow.class.getSimpleName();
	EditText edtAmountFrom;
	Button btnBack;
	Button btnHome;

	@Override
	public void onCreate(Bundle saved){
		super.onCreate(saved);
		setContentView(R.layout.search);
		try{	
			Bundle nBundle  = getIntent().getExtras();
			ArrayList<String[]> myArray = nBundle.getStringArrayList("SPENDING");
			if(myArray == null){
				Log.i(TAG, "***** Khong co du lieu");
				return;
			}
			
			btnBack = (Button)findViewById(R.id.btnBack);
			btnHome = (Button)findViewById(R.id.btnHome);
			
			btnBack.setOnClickListener(new View.OnClickListener(){
				public void onClick(View view){
					setResult(RESULT_OK, data);
					finish();
				}
			});
			
			btnHome.setOnClickListener(new View.OnClickListener(){
				public void onClick(View view){
					setResult(RESULT_OK, data);
					finish();
				}
			});
		}catch(Exception ex){
			Log.i(TAG, "***** onCreate() Error: " + ex.getMessage());
		}
		
	}
	
}