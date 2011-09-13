
public class ShowSpending{

	Button btnBack;
	Button btnCancel;
		
	@Override
	public void onCreate(Bunlde saved){
		btnBack = (Button)findViewById(R.id.btnBack);
		btnCancel = (Button)findViewById(R.id.btnCancel);
		
		btnBack.setOnClickListener(new View.onClickListener(){
			@Override
			public void onClick(View view){
				finish()
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
}