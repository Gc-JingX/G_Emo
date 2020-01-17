package com.omesoft.util.emojicon.emotionicon;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.style.DynamicDrawableSpan;

/**
 *
 * @ClassName: VerticalImageSpan
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author omesoft_tkz
 * @date 2015-12-1 下午5:08:52
 *
 */
public class VerticalImageSpan extends DynamicDrawableSpan {
	private float Multiple = 1.0F;
	private int mEmojiconSize;
	private Context context;
	private int resourceId;

	private Drawable mDrawable;

	public VerticalImageSpan(Context context, int resourceId, float Multiple,
							 int mEmojiconSize) {
		super();
		this.context = context;
		this.resourceId = resourceId;
		this.mEmojiconSize = mEmojiconSize;
	}

	public Drawable getDrawable() {
		if (mDrawable == null) {
			try {
				mDrawable = context.getResources().getDrawable(resourceId);
				mEmojiconSize = (int) ((float) mEmojiconSize * 1.3F);
				mDrawable.setBounds(0, 0, mEmojiconSize, mEmojiconSize);
			} catch (Exception e) {
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