package com.omesoft.util.emojicon;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.widget.TextView;

import com.omesoft.util.emojicon.emotionicon.SmileyParser;

public class EmojiconTextView extends TextView {
    private int mEmojiconSize;
    private static SmileyParser parser = null;
    private float size = 1.3F;

    public EmojiconTextView(Context context) {
        super(context);
        init(null);

    }

    public EmojiconTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public EmojiconTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs == null) {
            mEmojiconSize = (int) (getTextSize() * size);
        } else {
            TypedArray a = getContext().obtainStyledAttributes(attrs,
                    R.styleable.Emojicon);
            mEmojiconSize = (int) a.getDimension(
                    R.styleable.Emojicon_emojiconSize, getTextSize() * size);
            a.recycle();
        }
        setText(getText());
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        EmojiconHandler.addEmojis(getContext(), builder, mEmojiconSize);
        if (parser == null) {
            parser = new SmileyParser(getContext(), mEmojiconSize);
        }
        super.setText(parser.replace(builder, mEmojiconSize), type);
    }

    public void setEmojiconSize(int pixels) {
        mEmojiconSize = pixels;
    }
}
