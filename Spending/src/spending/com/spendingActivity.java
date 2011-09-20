package spending.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class spendingActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		ImageView imgIncome = (ImageView) findViewById(R.id.imgIncome);
		imgIncome.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				Intent payMoney = new Intent(v.getContext(), clsIncome.class);
				startActivity(payMoney);
				//Intent myIntent = new Intent(view.getContext(), Activity2.class);
                //startActivityForResult(myIntent, 0);
			}		
		});
		
		ImageView imgPayment = (ImageView) findViewById(R.id.imgPayment);
		imgPayment.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				Intent inMoney = new Intent(v.getContext(), clsPayment.class);
				startActivity(inMoney);
			}		
		});
		
		ImageView imgSearch = (ImageView) findViewById(R.id.imgSearch);
		imgSearch.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				Intent search = new Intent(v.getContext(), clsSearch.class);
				startActivity(search);
			}		
		});
		
		ImageView imgStatistics = (ImageView) findViewById(R.id.imgStatistics);
		imgStatistics.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
//				Intent inStatistics = new Intent(v.getContext(), cls.class);
//				startActivity(inStatistics);
			}		
		});
    }
}