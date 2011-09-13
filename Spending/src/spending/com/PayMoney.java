package spending.com;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class PayMoney extends Activity{

	EditText edtAmount ;
	EditText edtDate;
	EditText edtReason;
	EditText edtOther ;
	EditText edtComment;
	Button btnSave;
	Button btnCancel;
	
@Override
public void onCreate(Bundle saved){
	super.onCreate(saved);
	setContentView(R.layout.main);
	
	edtAmount = (EditText)findViewById(R.id.edtAmount);
	edtDate = (EditText)findViewById(R.id.edtDate);
	edtReason = (EditText)findViewById(R.id.edtReason);
	edtOther = (EditText)findViewById(R.id.edtOther);
	edtComment = (EditText)findViewById(R.id.edtComment);
	btnSave = (Button)findViewById(R.id.btnSave);
	btnCancel = (Button)findViewById(R.id.btnCancel);
	
	
	
	

}

}
