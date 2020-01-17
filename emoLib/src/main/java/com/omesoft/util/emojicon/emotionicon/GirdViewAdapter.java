package com.omesoft.util.emojicon.emotionicon;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;

import com.omesoft.util.emojicon.R;

import java.util.List;

public class GirdViewAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private Context context;
	private List<EmotionIcon> EmotionIcons;
	public GirdViewAdapter(Context context, List<EmotionIcon> EmotionIcons) {
		this.context = context;
		this.EmotionIcons = EmotionIcons;
		inflater = LayoutInflater.from(context);
	}

	public int getCount() {
		return EmotionIcons.size() + 1;
	}

	public Object getItem(int arg0) {
		return null;
	}

	public long getItemId(int arg0) {
		return 0;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.gridview_item, parent,
					false);
			viewHolder = new ViewHolder();
			viewHolder.icon = (ImageButton) convertView.findViewById(R.id.icon);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		if (position == getCount() - 1) {
			viewHolder.icon.setVisibility(View.VISIBLE);
			viewHolder.icon.setImageResource(R.drawable.omeosft_delete);
		} else {
			if (EmotionIcons.get(position).getName() == null) {
				viewHolder.icon.setVisibility(View.INVISIBLE);
			} else {
				viewHolder.icon.setVisibility(View.VISIBLE);
				int resId = context.getResources().getIdentifier(
						EmotionIcons.get(position).getResource(), "drawable",
						context.getPackageName());
				viewHolder.icon.setImageResource(resId);
			}
		}
		if (viewHolder.icon.getVisibility() == View.VISIBLE) {
			viewHolder.icon.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (position == getCount() - 1) {
						// Log.e("tkz", "点击删除表情：：");
						ViewFlowAdIconAdapter.mEmojiconEditText
								.deleteEditTextSpan();
					} else {
						// Log.e("tkz", "表情点击：："
						// + EmotionIcons.get(position).getName());
						// ViewFlowAdIconAdapter.mEmojiconEditText.append(EmotionIcons.get(position).getName());
						ViewFlowAdIconAdapter.mEmojiconEditText
								.insertText(EmotionIcons.get(position)
										.getName());
					}
				}
			});
		}
		return convertView;
	}

	private class ViewHolder {
		public ImageButton icon;
	}
}
