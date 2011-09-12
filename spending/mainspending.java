
public class mainspending extends Activity{

	@Override
	public onCreate()
	{
		ImageView imgPayMoney = (ImageView) findViewById(R.id.myImageId1);
		imgPayMoney.setOnClickListener(new onClickListener(){
			public void onClick(View v){
				Intent payMoney = new Intent(this, PayMoney.class);
				startActivity(payMoney);
			}		
		});
		
		ImageView imgGetMoney = (ImageView) findViewById(R.id.myImageId2);
		imgGetMoney.setOnClickListener(new onClickListener(){
			public void onClick(View v){
				Intent getMoney = new Intent(this, GetMoney.class);
				startActivity(getMoney);
			}		
		});
		
		ImageView imgSearch = (ImageView) findViewById(R.id.myImageId2);
		imgSearch.setOnClickListener(new onClickListener(){
			public void onClick(View v){
				Intent search = new Intent(this, Search.class);
				startActivity(search);
			}		
		});
		
		ImageView imgStatistics = (ImageView) findViewById(R.id.myImageId2);
		imgstatistics.setOnClickListener(new onClickListener(){
			public void onClick(View v){
				Intent Statistics = new Intent(this, Statistics.class);
				startActivity(Statistics);
			}		
		});
		
	}
	
	
}