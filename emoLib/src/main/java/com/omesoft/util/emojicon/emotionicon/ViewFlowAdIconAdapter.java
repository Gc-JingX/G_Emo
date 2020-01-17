package com.omesoft.util.emojicon.emotionicon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.omesoft.util.emojicon.EmojiconEditText;
import com.omesoft.util.emojicon.R;
import com.omesoft.util.emojicon.emotionicon.xmlpaser.PullEmotionIconParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @ClassName: ViewFlowAdIconAdapter
 * @Description: TODO(表情面板)
 * @author omesoft_tkz
 * @date 2015-12-1 上午10:44:09
 *
 */
public class ViewFlowAdIconAdapter extends BaseAdapter {
	private Context context;
	private int colum;
	private int rows;
	private LayoutInflater layoutInflater;
	private HashMap<String, List<EmotionIcon>> MapResoure = new HashMap<String, List<EmotionIcon>>();
	public static EmojiconEditText mEmojiconEditText;

	public ViewFlowAdIconAdapter(Context context, int colum, int rows,
								 EmojiconEditText mEmojiconEditText) {
		this.context = context;
		this.colum = colum;
		this.rows = rows;
		this.mEmojiconEditText = mEmojiconEditText;
		this.layoutInflater = LayoutInflater.from(context);
		List<EmotionIcon> EmotionIcons = new ArrayList<EmotionIcon>();
		try {
			InputStream is = context.getAssets().open(
					"emotionicon/emotionicon.xml");
			PullEmotionIconParser pull = new PullEmotionIconParser();
			EmotionIcons = pull.parse(is);
		} catch (Exception e) {
		}

		// 一页可以显示多少个表情
		int onePageNum = colum * rows - 1;
		int PageNum = (int) Math
				.ceil(((float) EmotionIcons.size() / (float) onePageNum));

		int nowpostion = 0;

		for (int i = 0; i < PageNum; i++) {
			List<EmotionIcon> newEmotionIcons = new ArrayList<EmotionIcon>();
			for (int j = 0; j < onePageNum; j++) {
				EmotionIcon mEmotionIcon = new EmotionIcon();
				if (nowpostion < EmotionIcons.size()) {
					mEmotionIcon = EmotionIcons.get(nowpostion);
					nowpostion++;
				}
				newEmotionIcons.add(mEmotionIcon);
			}
			MapResoure.put(i + "", newEmotionIcons);
		}
	}

	@Override
	public int getCount() {
		return MapResoure.size();
	}

	@Override
	public Object getItem(int position) {
		return MapResoure.get("" + position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = layoutInflater.inflate(
					R.layout.omesoft_emoji_gridview, parent,false);
		}
		EmojGirdView mMyGirdView = (EmojGirdView) convertView
				.findViewById(R.id.omesofticon);
		mMyGirdView.setNumColumns(colum);
		GirdViewAdapter mGirdViewAdapter = new GirdViewAdapter(context,
				(List<EmotionIcon>) getItem(position));
		mMyGirdView.setAdapter(mGirdViewAdapter);
		return convertView;
	}

	public void setEmojiconEditText(EmojiconEditText mEmojiconEditText) {
		this.mEmojiconEditText = mEmojiconEditText;
	}
}
