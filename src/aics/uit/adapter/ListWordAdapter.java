package aics.uit.adapter;

import java.util.ArrayList;

import aics.uit.R;
import aics.uit.item.ItemWord;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListWordAdapter extends BaseAdapter {
	private final Activity activity;
	private final ArrayList data;
	private static LayoutInflater inflater = null;
	public Resources res;
	int i = 0;
	public View vi;
	public ViewHolder holder;
	private ItemWord itemWord;

	public ListWordAdapter(Activity a, ArrayList al, Resources resLocal) {
		activity = a;
		data = al;
		res = resLocal;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		if (data.size() <= 0) {
			return 0;
		}
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		vi = convertView;
		if (convertView == null) {
			vi = inflater.inflate(R.layout.itemword, null);
			holder = new ViewHolder();
			holder.text = (TextView) vi.findViewById(R.id.tvWord);
			holder.text2 = (TextView)vi.findViewById(R.id.tvMean);
			vi.setTag(holder);
		} else {
			holder = (ViewHolder) vi.getTag();
		}
		if (data.size() <= 0) {
			// holder.text.setText("No Data");
		} else {
			itemWord = null;
			itemWord = (ItemWord) data.get(position);
			holder.text.setText(itemWord.getWord());
			holder.text2.setText(itemWord.getMean());
		}
		return vi;
	}

	public static class ViewHolder {
		public TextView text;
		public TextView text2;

	}

}
