package com.youmi.tt.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 带旋转动画的加载图片
 * 
 * @author dzl 2016/10/31
 *
 */
public class ProgressImageView extends ImageView {

	private ObjectAnimator animator;

	public ProgressImageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		start();
	}

	public ProgressImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ProgressImageView(Context context) {
		this(context, null);
	}

	public void start() {

		Drawable drawable = getDrawable();
		if (drawable != null) {

			if (animator != null) {
				animator.cancel();
			}
			
			animator = ObjectAnimator.ofFloat(this, "rotation", 0, 360f);
			animator.setDuration(1000);
			animator.setRepeatCount(-1);
			animator.start();

		}

	}

}
