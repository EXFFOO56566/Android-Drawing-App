package com.viavilab.androiddrawing;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class List_Adapter extends  ArrayAdapter<List_Item> {
	Context context;
	int layoutResourceId;
	ArrayList<List_Item> data = new ArrayList<List_Item>();

	public List_Adapter(Context context, int layoutResourceId,
			ArrayList<List_Item> data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		RecordHolder holder = null;
		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);

			holder = new RecordHolder();
			holder.txttitle = (TextView) row.findViewById(R.id.item_title);
			holder.txtdes=(TextView) row.findViewById(R.id.item_description);
			holder.imageItem = (ImageView) row.findViewById(R.id.item_image);
			row.setTag(holder);
		} else {
			holder = (RecordHolder) row.getTag();
		}

		List_Item item = data.get(position);
		holder.txttitle.setText(item.getName());
		holder.txtdes.setText(item.getDescription());
		holder.imageItem.setImageResource(item.getIcon());
		return row;

	}
 	static class RecordHolder {
		TextView txttitle,txtdes;
		ImageView imageItem;

	}
} 

 