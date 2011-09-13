
public class SearchSpending{

	Button btnBack;
	Button btnCancel;
		
	@Override
	public void onCreate(Bunlde saved){
		btnBack = (Button)findViewById(R.id.btnBack);
		btnCancel = (Button)findViewById(R.id.btnCancel);
		
		btnBack.setOnClickListener(new View.onClickListener(){
			@Override
			public void onClick(View view){
				Intent i = new Intent(this, SearchSpending.class);
				i.putExtra(2222, id);
				// Activity returns an result if called with startActivityForResult
		
				startActivityForResult(i, ACTIVITY_EDIT);
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
	
	// Called with the result of the other activity
	// requestCode was the origin request code send to the activity
	// resultCode is the return code, 0 is everything is ok
	// intend can be use to get some data from the caller
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		//fillData();

	}
}