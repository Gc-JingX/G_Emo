package com.omesoft.util.emojicon;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;

import com.omesoft.util.emojicon.emotionicon.SmileyParser;

public class EmojiconEditText extends EditText {
	private int mEmojiconSize;
	private static SmileyParser parser = null;
	private float size = 1.3F;
	private boolean delete = false;

	public EmojiconEditText(Context context) {
		super(context);
		mEmojiconSize = (int) (getTextSize() * size);
	}

	public EmojiconEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs);
	}

	public EmojiconEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs);
	}

	private void init(AttributeSet attrs) {
		TypedArray a = getContext().obtainStyledAttributes(attrs,
				R.styleable.Emojicon);
		mEmojiconSize = (int) a.getDimension(R.styleable.Emojicon_emojiconSize,
				getTextSize() * size);
		a.recycle();
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

	@Override
	protected void onTextChanged(CharSequence text, int start,
								 int lengthBefore, int lengthAfter) {
		EmojiconHandler.addEmojis(getContext(), getText(), mEmojiconSize);
	}

	/** 获取EditText光标所在的位置 */
	public int getEditTextCursorIndex() {
		return this.getSelectionStart();
	}

	/** 向EditText指定光标位置插入字符串 */
	public void insertText(String mText) {
		this.getText().insert(getEditTextCursorIndex(), mText);
	}

	/** 向EditText指定光标位置删除字符串 */
	public void deleteText() {
		if (getText().toString().length() > 0) {
			this.getText().delete(getEditTextCursorIndex() - 1,
					getEditTextCursorIndex());
		}
	}

	/**
	 * 删除表情
	 */
	public void deleteEditTextSpan() {
		Spanned s = this.getEditableText();
		EmojiconSpan[] imageSpan = s
				.getSpans(0, s.length(), EmojiconSpan.class);
		delete = false;
		for (int i = imageSpan.length - 1; i >= 0; i--) {
			if (i == imageSpan.length - 1) {
				int start = s.getSpanStart(imageSpan[i]);
				int end = s.getSpanEnd(imageSpan[i]);
				Editable et = this.getText();
				et.delete(start, end);
				delete = true;
			}
		}
		if (!delete) {
			deleteText();
		}
		this.invalidate();
	}

	public void setEmojiconSize(int pixels) {
		mEmojiconSize = pixels;
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.e("xx","onKeyDown  "+event.getAction()+" keyCode "+keyCode);
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onKeyPreIme(int keyCode, KeyEvent event) {
		Log.e("xx"," "+event.getAction()+" keyCode "+keyCode);
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == 1 && onKeyBoardHideListener != null) {
			onKeyBoardHideListener.onKeyHide();
		}
		return super.onKeyPreIme(keyCode, event);
	}

	/**
	 * 键盘监听接口
	 */
	private OnKeyBoardHideListener onKeyBoardHideListener;

	public void setOnKeyBoardHideListener(OnKeyBoardHideListener onKeyBoardHideListener) {
		this.onKeyBoardHideListener = onKeyBoardHideListener;
	}

	public interface OnKeyBoardHideListener {
		void onKeyHide();
	}
}
