package com.omesoft.util.emojicon.emotionicon;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.util.Log;

import com.omesoft.util.emojicon.emotionicon.xmlpaser.PullEmotionIconParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SmileyParser {
	private Context mContext;
	private String[] mSmileyTexts;
	private static Pattern mPattern;
	private HashMap<String, Integer> mSmileyToRes;
	public static int[] DEFAULT_SMILEY_RES_IDS;
	private int mEmojiconSize;

	public SmileyParser(Context context, int mEmojiconSize) {
		mContext = context;
		this.mEmojiconSize = mEmojiconSize;
		List<EmotionIcon> EmotionIcons = new ArrayList<EmotionIcon>();
		try {
			InputStream is = context.getAssets().open(
					"emotionicon/emotionicon.xml");
			PullEmotionIconParser pull = new PullEmotionIconParser();
			EmotionIcons = pull.parse(is);
		} catch (Exception e) {
		}
		Log.e("tkz","EmotionIcons::"+EmotionIcons);
		int size = EmotionIcons.size();
		Log.e("tkz","size::"+size);
		mSmileyTexts = new String[size];
		DEFAULT_SMILEY_RES_IDS = new int[size];

		// int resId = context.getResources().getIdentifier(
		// EmotionIcons.get(position).getResource(), "drawable",
		// context.getPackageName());
		for (int i = 0; i < size; i++) {
			mSmileyTexts[i] = EmotionIcons.get(i).getName();
			DEFAULT_SMILEY_RES_IDS[i] = context.getResources().getIdentifier(
					EmotionIcons.get(i).getResource(), "drawable",
					context.getPackageName());
		}

		mSmileyToRes = buildSmileyToRes();
		mPattern = buildPattern();
	}

	private HashMap<String, Integer> buildSmileyToRes() {
		if (DEFAULT_SMILEY_RES_IDS.length != mSmileyTexts.length) {
			// Log.w("SmileyParser", "Smiley resource ID/text mismatch");
			// 表情的数量需要和数组定义的长度一致！
			throw new IllegalStateException("Smiley resource ID/text mismatch");
		}

		HashMap<String, Integer> smileyToRes = new HashMap<String, Integer>(
				mSmileyTexts.length);
		for (int i = 0; i < mSmileyTexts.length; i++) {
			smileyToRes.put(mSmileyTexts[i], DEFAULT_SMILEY_RES_IDS[i]);
		}

		return smileyToRes;
	}

	/**
	 *
	 * @Title: buildPattern
	 * @Description: TODO 构建正则表达式
	 * @param @return 设定文件
	 * @return Pattern 返回类型
	 * @throws
	 */
	private Pattern buildPattern() {
		Log.e("xx","patternString.toString() "+mSmileyTexts.length);
		StringBuilder patternString = new StringBuilder(mSmileyTexts.length * 3);
		patternString.append('(');
		for (String s : mSmileyTexts) {
			patternString.append(Pattern.quote(s));
			patternString.append('|');
		}
		patternString.replace(patternString.length() - 1,
				patternString.length(), ")");

		Log.e("xx","patternString.toString() "+patternString.toString());
		return Pattern.compile(patternString.toString());
	}

	// 根据文本替换成图片
	public CharSequence replace(CharSequence text, int emojiSize) {
		SpannableStringBuilder builder = new SpannableStringBuilder(text);
		Matcher matcher = mPattern.matcher(text);
		while (matcher.find()) {
//	    Log.e("tkz", "找到表情：：");
			int resId = mSmileyToRes.get(matcher.group());
//	    Log.e("tkz", "找到表情mEmojiconSize::::::" + emojiSize);
			builder.setSpan(new VerticalImageSpan(mContext, resId, 0.05F,
							emojiSize), matcher.start(), matcher.end(),
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		return builder;
	}

	// 查找是否有表情
	public static boolean replace2(CharSequence text ) {
		Matcher matcher = mPattern.matcher(text);
		boolean Is= false;
		while (matcher.find()) {
			Is = true;
			break;
		}
		return Is;
	}

	// 根据文本替换成图片
	public Spannable replace2(Spannable text, int emojiSize) {
		// SpannableStringBuilder builder = new SpannableStringBuilder(text);
		Matcher matcher = mPattern.matcher(text);
		// Log.e("new", "正则表达式检查表情开始：：");
		// while (matcher.find()) {
		// Log.e("tkz", "找到表情：：");
		// int resId = mSmileyToRes.get(matcher.group());
		// Log.e("tkz", "找到表情mEmojiconSize::::::" + emojiSize);
		//
		// }
		// Log.e("new", "正则表达式检查表情结束：：");
		//
		// Log.e("new", "正则表达式检查表情结束：：还有转化图片开始：：");
		while (matcher.find()) {
//	    Log.e("tkz", "找到表情：：");
			int resId = mSmileyToRes.get(matcher.group());
//	    Log.e("tkz", "找到表情mEmojiconSize::::::" + emojiSize);
			text.setSpan(new VerticalImageSpan(mContext, resId, 0.05F,
							emojiSize), matcher.start(), matcher.end(),
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//	    Log.e("tkz", "matcher.start()::" + matcher.start());

//	    Log.e("tkz", "matcher.end()::" + matcher.end());
		}
//	Log.e("new", "正则表达式检查表情结束：：还有转化图片结束：：");
		return text;
	}
}
