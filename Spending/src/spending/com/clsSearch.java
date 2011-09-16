package spending.com;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.util.Log;

public class clsSearch extends Activity{

	private static final String TAG = clsSearch.class.getSimpleName();
	EditText edtAmount ;
	EditText edtDate;
	Spinner spnReason;		
	EditText edtOther ;
	EditText edtComment;
	Button btnSave;
	Button btnCancel;
	
	@Override
	public void onCreate(Bundle saved){
		super.onCreate(saved);
		setContentView(R.layout.main);
		
	}
}