package com.omesoft.util.emojicon.emotionicon.xmlpaser;

import com.omesoft.util.emojicon.emotionicon.EmotionIcon;

import java.io.InputStream;
import java.util.List;

public interface EmotionIconPaser {
    /**
     * 解析输入流 得到EmotionIcon对象集合
     *
     * @param is
     * @return
     * @throws Exception
     */
    public List<EmotionIcon> parse(InputStream is) throws Exception;

    /**
     * 序列化EmotionIcon对象集合 得到XML形式的字符串
     *
     * @param EmotionIcon
     * @return
     * @throws Exception
     */
    public String serialize(List<EmotionIcon> EmotionIcons) throws Exception;
}
