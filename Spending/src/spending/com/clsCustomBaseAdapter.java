package spending.com;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class clsCustomBaseAdapter extends BaseAdapter {
	private static ArrayList<clsData> searchArrayList;
	private LayoutInflater mInflater;

	public clsCustomBaseAdapter(Context context, ArrayList<clsData> results) {
		searchArrayList = results;
		mInflater = LayoutInflater.from(context);
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
		TextView txtAmount;
		TextView txtDate;
		TextView txtReason;
	}
		
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_result, null);
			holder = new ViewHolder();
			holder.txtAmount = (TextView) convertView.findViewById(R.id.txtAmount);
			holder.txtDate = (TextView) convertView.findViewById(R.id.txtDate);
			holder.txtReason = (TextView) convertView.findViewById(R.id.txtReason);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.txtAmount.setText(searchArrayList.get(position).getAmount());
		holder.txtDate.setText(searchArrayList.get(position).getDatePay());
		holder.txtReason.setText(searchArrayList.get(position).getReason());

		return convertView;
	}
	 


 }
