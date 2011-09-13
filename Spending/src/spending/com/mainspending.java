package spending.com;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

public class mainspending extends Activity {
	
	public void onCreate()
	{
		ImageView imgPayMoney = (ImageView) findViewById(R.id.myImageId1);
		imgPayMoney.setOnClickListener(new onClickListener(){
			public void onClick(View v){
				Intent payMoney = new Intent(this, mainspending.class);
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
		imgStatistics.setOnClickListener(new onClickListener(){
			public void onClick(View v){
				Intent Statistics = new Intent(this, Statistics.class);
				startActivity(Statistics);
			}		
		});
		
	}
}
