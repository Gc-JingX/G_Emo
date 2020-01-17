package com.omesoft.util.emojicon.emotionicon.xmlpaser;

import android.util.Xml;

import com.omesoft.util.emojicon.emotionicon.EmotionIcon;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PullEmotionIconParser implements EmotionIconPaser {

	@Override
	public List<EmotionIcon> parse(InputStream is) throws Exception {
		List<EmotionIcon> EmotionIcons = null;
		EmotionIcon mEmotionIcon = null;

		XmlPullParser parser = Xml.newPullParser(); // 由android.util.Xml创建一个XmlPullParser实例
		parser.setInput(is, "UTF-8"); // 设置输入流 并指明编码方式

		int eventType = parser.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					EmotionIcons = new ArrayList<EmotionIcon>();
					// Log.e("tkz", "START_DOCUMENT::");
					break;
				case XmlPullParser.START_TAG:
					// Log.e("tkz", "START_TAG::");
					if (parser.getName().equals("emotionicon")) {
						mEmotionIcon = new EmotionIcon();
					} else if (parser.getName().equals("name")) {
						eventType = parser.next();
						mEmotionIcon.setName(parser.getText());
					} else if (parser.getName().equals("resource")) {
						eventType = parser.next();
						mEmotionIcon.setResource(parser.getText());
					}
					break;
				case XmlPullParser.END_TAG:
					// Log.e("tkz", "END_TAG::");
					if (parser.getName().equals("emotionicon")) {
						EmotionIcons.add(mEmotionIcon);
						mEmotionIcon = null;
					}
					break;
			}
			eventType = parser.next();
		}
		return EmotionIcons;
	}

	@Override
	public String serialize(List<EmotionIcon> EmotionIcons) throws Exception {
		return null;
	}

}
