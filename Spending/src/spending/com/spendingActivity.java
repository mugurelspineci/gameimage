package spending.com;

import android.app.Activity;
import android.os.Bundle;

public class spendingActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		ImageView imgIncome = (ImageView) findViewById(R.id.income);
		imgIncome.setOnClickListener(new onClickListener(){
			public void onClick(View v){
				Intent payMoney = new Intent(this, PayMoney.class);
				startActivity(payMoney);
				//Intent myIntent = new Intent(view.getContext(), Activity2.class);
                //startActivityForResult(myIntent, 0);
			}		
		});
		
		ImageView imgPayment = (ImageView) findViewById(R.id.payment);
		imgInMoney.setOnClickListener(new onClickListener(){
			public void onClick(View v){
				Intent inMoney = new Intent(this, InMoney.class);
				startActivity(inMoney);
			}		
		});
		
		ImageView imgSearch = (ImageView) findViewById(R.id.search);
		imgSearch.setOnClickListener(new onClickListener(){
			public void onClick(View v){
				Intent search = new Intent(this, search.class);
				startActivity(search);
			}		
		});
		
		ImageView imgStatistics = (ImageView) findViewById(R.id.statistics);
		imgstatistics.setOnClickListener(new onClickListener(){
			public void onClick(View v){
				Intent Statistics = new Intent(this, Statistics.class);
				startActivity(Statistics);
			}		
		});
    }
}