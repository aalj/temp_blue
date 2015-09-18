/**
 * ArcMenu.java
 * com.example.view
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2015-8-5 		zhang
 *
 * Copyright (c) 2015, TNT All Rights Reserved.
 */

package com.example.view;

import com.example.arcmenu.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.transition.ChangeBounds;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.ToggleButton;

/**
 * ClassName:ArcMenu Function: 
 * 卫星菜单的实现
 * ADD FUNCTION Reason: 
 * ADD REASON
 * 
 * @author zhang
 * @version
 * @since Ver 1.1
 * @Date 2015-8-5 下午7:08:37
 * 
 * @see
 * 
 */
public class ArcMenu extends ViewGroup {

	private static final int POS_LEFT_TOP = 0;
	private static final int POS_LEFT_BOTTOM = 1;
	private static final int POS_RIGHT_TOP = 2;
	private static final int POS_RIGHT_BOTTOM = 3;

	private Position mposition = Position.RIGHT_BOTTOM;

	private int mRadius;
	/**
	 * 菜单的状态
	 */
	private Status mCurrentStatus = Status.CLOSE;

	/*
	 * 菜单的主按钮
	 */
	private View mCButton;

	private OnMenuItemClickListener mMenuItemClickListener;

	public enum Status {
		OPEN, CLOSE;
	}

	public enum Position {
		LEFT_TOP, LEFT_BOTTOM, RIGHT_TOP, RIGHT_BOTTOM
	}

	/**
	 * 回调子菜单的接口
	 */
	public interface OnMenuItemClickListener {
		void onclick(View view, int pos);
	}

	public void setOnmMenuItemClickListener(
			OnMenuItemClickListener mMenuItemClickListener) {
		this.mMenuItemClickListener = mMenuItemClickListener;
	}

	public ArcMenu(Context context) {

		this(context, null);
		// TODO Auto-generated constructor stub

	}

	public ArcMenu(Context context, AttributeSet attrs) {

		this(context, attrs, 0);
		// TODO Auto-generated constructor stub

	}

	public ArcMenu(Context context, AttributeSet attrs, int defStyle) {

		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		mRadius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				100, getResources().getDisplayMetrics());

		// 获取自定义属性
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.ArcMenu, defStyle, 0);

		int pos = a.getInt(R.styleable.ArcMenu_position, POS_RIGHT_BOTTOM);
		switch (pos) {
		case POS_LEFT_TOP:
			mposition = Position.LEFT_TOP;
			break;
		case POS_LEFT_BOTTOM:
			mposition = Position.LEFT_BOTTOM;
			break;
		case POS_RIGHT_TOP:
			mposition = Position.RIGHT_TOP;
			break;
		case POS_RIGHT_BOTTOM:
			mposition = Position.RIGHT_BOTTOM;
			break;

		default:
			break;
		}

		mRadius = (int) a.getDimension(R.styleable.ArcMenu_radius, TypedValue
				.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100,
						getResources().getDisplayMetrics()));
		Log.e("TAG", "position=" + mposition + "," + "radius" + mRadius);
		a.recycle();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int count = getChildCount();
		for (int i = 0; i < count; i++) {
			// 测量child
			measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
		}
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

	}

	@Override
	protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) {
		if (arg0) {
			layoutCButton();
			int count = getChildCount();
			for (int i = 0; i < count - 1; i++) {
				View child = getChildAt(i + 1);

				child.setVisibility(View.GONE);

				int cl = (int) (mRadius * Math.sin(Math.PI / 2 / (count - 2)
						* i));
				int ct = (int) (mRadius * Math.cos(Math.PI / 2 / (count - 2)
						* i));

				int cWidth = child.getMeasuredWidth();
				int cHeight = child.getMeasuredHeight();
				// 如果菜单在底部
				if (mposition == Position.LEFT_BOTTOM
						|| mposition == Position.RIGHT_BOTTOM) {
					ct = getMeasuredHeight() - cHeight - ct;
				}
				if (mposition == Position.RIGHT_TOP
						|| mposition == Position.RIGHT_BOTTOM) {
					cl = getMeasuredWidth() - cWidth - cl;
				}

				child.layout(cl, ct, cl + cWidth, ct + cHeight);
			}
		}
		// TODO Auto-generated method stub

	}

	/**
	 * 
	 * layoutCButton:定位主菜单按钮
	 * 
	 * @param 设定文件
	 * @return void DOM对象
	 * @throws
	 * @since CodingExample　Ver 1.1
	 */
	private void layoutCButton() {

		mCButton = getChildAt(0);
		mCButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// mCButton=findViewById(R.id.button);
				// if(mCButton==null){
				// mCButton=getChildAt(0);
				// }

				rotateCButton(v, 0f, 360f, 300);
				ToggleMenu(300);
			}

			private void ToggleMenu(int duration) {
				// 为menuitem添加平移动画和旋转动画
				int count = getChildCount();
				for (int i = 0; i < count - 1; i++) {
					final View childView = getChildAt(i + 1);
					childView.setVisibility(View.VISIBLE);
					// end 0,0
					// start
					int cl = (int) (mRadius * Math.sin(Math.PI / 2
							/ (count - 2) * i));
					int ct = (int) (mRadius * Math.cos(Math.PI / 2
							/ (count - 2) * i));
					int xflag = 1;
					int yflag = 1;
					if (mposition == Position.LEFT_TOP
							|| mposition == Position.LEFT_BOTTOM) {
						xflag = -1;
					}
					if (mposition == Position.LEFT_TOP
							|| mposition == Position.RIGHT_TOP) {
						yflag = -1;
					}
					AnimationSet animset = new AnimationSet(true);
					Animation tranAnim = null;
					if (mCurrentStatus == Status.CLOSE) {
						tranAnim = new TranslateAnimation(xflag * cl, 0, yflag
								* ct, 0);
						childView.setClickable(true);
						childView.setFocusable(true);
					} else {
						tranAnim = new TranslateAnimation(0, xflag * cl, 0,
								yflag * ct);
						childView.setClickable(false);
						childView.setFocusable(false);
					}
					tranAnim.setFillAfter(true);
					tranAnim.setDuration(duration);
					tranAnim.setStartOffset((i * 100) / count);
					tranAnim.setAnimationListener(new AnimationListener() {

						@Override
						public void onAnimationStart(Animation arg0) {

							// TODO Auto-generated method stub

						}

						@Override
						public void onAnimationRepeat(Animation arg0) {

							// TODO Auto-generated method stub

						}

						@Override
						public void onAnimationEnd(Animation arg0) {
							if (mCurrentStatus == Status.CLOSE) {
								childView.setVisibility(View.GONE);
							}
							// TODO Auto-generated method stub

						}
					});
					// 旋转动画
					RotateAnimation rotateAnimation = new RotateAnimation(0,
							720, Animation.RELATIVE_TO_SELF, 0.5f,
							Animation.RELATIVE_TO_SELF, 0.5f);
					rotateAnimation.setDuration(duration);
					rotateAnimation.setFillAfter(true);
					animset.addAnimation(rotateAnimation);
					animset.addAnimation(tranAnim);
					childView.startAnimation(animset);

					final int pos = i + 1;
					childView.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							if (mMenuItemClickListener != null)
								mMenuItemClickListener.onclick(childView, pos);

							MenuItemAnim(pos - 1);
							changeStatus();
						}
					});
				}
				changeStatus();
			}

			private void MenuItemAnim(int pos) {
				for (int i = 0; i < getChildCount() - 1; i++) {
					View childView = getChildAt(i + 1);
					if (i == pos) {
						childView.startAnimation(scaleBigAnim(300));
					} else {
						childView.startAnimation(scaleSmallAnim(300));
					}
					childView.setClickable(false);
					childView.setFocusable(false);
				}
				// TODO Auto-generated method stub

			}

			// 变小动画
			private Animation scaleSmallAnim(int duration) {
				AnimationSet animationSet = new AnimationSet(true);

				ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.0f,
						1.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f,
						Animation.RELATIVE_TO_SELF, 0.5f);

				AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 0.0f);
				animationSet.addAnimation(scaleAnimation);
				animationSet.addAnimation(alphaAnimation);

				animationSet.setDuration(duration);
				animationSet.setFillAfter(true);

				// TODO Auto-generated method stub
				return animationSet;

			}

			// 变大动画
			private Animation scaleBigAnim(int duration) {
				AnimationSet animationSet = new AnimationSet(true);

				ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 4.0f,
						1.0f, 4.0f, Animation.RELATIVE_TO_SELF, 0.5f,
						Animation.RELATIVE_TO_SELF, 0.5f);
				AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 0.0f);
				animationSet.addAnimation(scaleAnimation);
				animationSet.addAnimation(alphaAnimation);

				animationSet.setDuration(duration);
				animationSet.setFillAfter(true);

				// TODO Auto-generated method stub
				return animationSet;

			}

			private void changeStatus() {
				mCurrentStatus = (mCurrentStatus == Status.CLOSE ? Status.OPEN
						: Status.CLOSE);
				// TODO Auto-generated method stub

			}

			private void rotateCButton(View v, float start, float end,
					int duration) {

				RotateAnimation anim = new RotateAnimation(start, end,
						Animation.RELATIVE_TO_SELF, 0.5f,
						Animation.RELATIVE_TO_SELF, 0.5f);

				anim.setDuration(duration);
				anim.setFillAfter(true);
				v.startAnimation(anim);

			}
		});
		int l = 0;
		int t = 0;
		int width = mCButton.getMeasuredWidth();
		int height = mCButton.getMeasuredHeight();
		switch (mposition) {
		case LEFT_TOP:
			l = 0;
			t = 0;
			break;
		case LEFT_BOTTOM:
			l = 0;
			t = getMeasuredHeight() - height;
			break;
		case RIGHT_TOP:
			l = getMeasuredWidth() - width;
			t = 0;
			break;
		case RIGHT_BOTTOM:
			l = getMeasuredWidth() - width;
			t = getMeasuredHeight() - height;
			break;

		}

		mCButton.layout(l, t, l + width, t + width);

	}

}
