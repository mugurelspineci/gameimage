package spending.com;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class Income extends Activity{

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
		
		String arrReason = {"AA","BB","CC"};
		ArrayAdapter adapter;
		
		edtAmount = (EditText)findViewById(R.id.edtAmount);
		edtDate = (EditText)findViewById(R.id.edtDate);
		spnReason = (Spinner)findViewById(R.id.spnReason);
		edtOther = (EditText)findViewById(R.id.edtOther);
		edtComment = (EditText)findViewById(R.id.edtComment);
		btnSave = (Button)findViewById(R.id.btnSave);
		btnCancel = (Button)findViewById(R.id.btnCancel);
	
		adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrReason);
		spnReason.setAdapter(adapter);
		
		btnSave.setOnClickListener(new View.onClickListener(){
			@Override
			public void onClick(View view){
				if(checkValid()==true)
					saveData();
			}
		});
		
		btnCancel.setOnClickListener(new View.onClickListener(){
			@Override
			public void onClick(View view){
				//Intent intent = new Intent();
                //setResult(RESULT_OK, intent);
                finish();
			}
		});
	}
	
	private boolean checkValid(){
		if(edtAmount.getText().length()==0){
			return false;
		}
		if(editDate.getText().length()==0){
			return false;
		}
		if(spnReason.getText().length()==0){
			return false;
		}
	}
	
	private void saveData(){
	
	}

}
