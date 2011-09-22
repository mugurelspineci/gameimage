package spending.com;

public class MyCustomBaseAdapter extends BaseAdapter {
	private static ArrayList<clsData> searchArrayList;
	private LayoutInflater mInflater;

	public MyCustomBaseAdapter(Context context, ArrayList<clsData> results) {
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
		
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.custom_row_view, null);
			holder = new ViewHolder();
			holder.txtAmount = (TextView) convertView.findViewById(R.id.txtAmount);
			holder.txtDate = (TextView) convertView.findViewById(R.id.txtDate);
			holder.txtReason = (TextView) convertView.findViewById(R.id.txtReason);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.txtAmount.setText(searchArrayList.get(position).getName());
		holder.txtDate.setText(searchArrayList.get(position).getCityState());
		holder.txtReason.setText(searchArrayList.get(position).getPhone());

		return convertView;
	}
	 


 }
