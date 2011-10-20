package spending.com;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class clsReasonBaseAdapter extends BaseAdapter {
	//private static final String TAG = clsSearch.class.getSimpleName();
	private static ArrayList<clsData> searchArrayList;
	private LayoutInflater mInflater;

	public clsReasonBaseAdapter(Context context, ArrayList<clsData> results) {
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
		TextView txtReason;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.reason, null);
			holder = new ViewHolder();
			holder.txtReason = (TextView) convertView.findViewById(R.id.txtReason);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.txtReason.setText(searchArrayList.get(position).getReason());

		return convertView;
	}

}
