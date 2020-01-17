package com.omesoft.util.emojicon.emotionicon;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class EmojGirdView extends GridView {

	public EmojGirdView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public EmojGirdView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public EmojGirdView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		// 右移一位相当于除2，右移n位相当于除以2的n次方
		// UNSPECIFIED(未指定),父元素部队自元素施加任何束缚,子元素可以得到任意想要的大小
		// EXACTLY(完全)，父元素决定自元素的确切大小，子元素将被限定在给定的边界里而忽略它本身大小
		// AT_MOST(至多)，子元素至多达到指定大小的值。
		// makeMeasureSpec，根据提供的大小值和模式创建一个测量值(格式)
		// 一个MeasureSpec封装了父布局传递给子布局的布局要求
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
