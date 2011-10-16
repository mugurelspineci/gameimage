package spending.com;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class clsCustomBaseAdapter extends BaseAdapter {
	//private static final String TAG = clsSearch.class.getSimpleName();
	private static ArrayList<clsData> searchArrayList;
	private LayoutInflater mInflater;
	private double sum;

	public clsCustomBaseAdapter(Context context, ArrayList<clsData> results) {
		searchArrayList = results;
		mInflater = LayoutInflater.from(context);
		sum = 0;
	}
	
	public Double getSum(){		
		return sum;
	}
	public int getCount() {
		return searchArrayList.size();
	}

	public Object getItem(int position) {
		return searchArrayList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	static class ViewHolder {
		TextView txtSum;
		TextView txtAmount;
		TextView txtDate;
		TextView txtReason;
		TextView txtPay;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		int pay;
		//double amount;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_result, null);
			holder = new ViewHolder();
			holder.txtAmount = (TextView) convertView.findViewById(R.id.txtAmount);
			holder.txtPay = (TextView) convertView.findViewById(R.id.txtPay);
			holder.txtDate = (TextView) convertView.findViewById(R.id.txtDate);
			holder.txtReason = (TextView) convertView.findViewById(R.id.txtReason);
			// holder.txtSum = (TextView) convertView.findViewById(R.id.txtSum);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		//amount = Double.parseDouble(searchArrayList.get(position).getAmount());
		holder.txtAmount.setText(searchArrayList.get(position).getAmount());		
		holder.txtDate.setText(searchArrayList.get(position).getDatePay());
		holder.txtReason.setText(searchArrayList.get(position).getReason());
		pay = searchArrayList.get(position).getPay();
		if (pay == 1) {
			holder.txtPay.setText("+");
			//sum += amount;
		} else {
			holder.txtPay.setText("-");
			//sum -= amount;
		}
		//Log.i(TAG, "***** getView() sum="  + sum +", amount=" + amount);
		//sum += amount;
		// holder.txtSum.setText("");
		return convertView;
	}

}
