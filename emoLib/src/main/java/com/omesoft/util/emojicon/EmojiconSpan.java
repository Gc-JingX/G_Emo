package com.omesoft.util.emojicon;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.style.DynamicDrawableSpan;

public class EmojiconSpan extends DynamicDrawableSpan {
	private final Context mContext;
	private final int mResourceId;
	private final int mSize;
	private Drawable mDrawable;

	public EmojiconSpan(Context context, int resourceId, int size) {
		super();
		mContext = context;
		mResourceId = resourceId;
		mSize = size;
	}

	public Drawable getDrawable() {
		if (mDrawable == null) {
			try {
				mDrawable = mContext.getResources().getDrawable(mResourceId);
				int size = mSize;
				mDrawable.setBounds(0, 0, size, size);
			} catch (Exception e) {
				// swallow
			}
		}
		return mDrawable;
	}

	@Override
	public void draw(Canvas canvas, CharSequence text, int start, int end,
					 float x, int top, int y, int bottom, Paint paint) {
		Drawable b = getDrawable();

		canvas.save();
		int transY = ((bottom - top) - (b.getBounds().bottom - b.getBounds().top))
				/ 2 + top;
		canvas.translate(x, (float) transY);
		b.draw(canvas);
		canvas.restore();
	}
}