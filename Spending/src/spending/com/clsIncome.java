package spending.com;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.util.Log;

public class clsIncome extends Activity{

	private static final String TAG = clsIncome.class.getSimpleName();
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
		
		String arrReason[] = {"AA","BB","CC"};
		ArrayAdapter<String> adapter;
		
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
		if(edtDate.getText().length()==0){
			return false;
		}
		if(spnReason.getSelectedItem().toString().length()==0){
			return false;
		}
	}
	
	private void saveData(){
		SpendingDbAdapter db
		try{
			db = new SpendingDbAdapter();
			db.open();
			db.insert(edtAmount.getText(), edtDate.getText(), 1, spnReason.getSelectedItem().toString(), 
					edtOther.getText(), edtComment.getText());
		}catch(Exception ex){
			Log.i(TAG, "***** saveData() Error: " + ex.getMessage());
		}
	}

}

