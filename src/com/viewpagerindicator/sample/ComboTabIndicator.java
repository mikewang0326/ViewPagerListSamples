package com.viewpagerindicator.sample;

import com.viewpagerindicator.IcsLinearLayout;
import com.viewpagerindicator.TabPageIndicator;
import com.viewpagerindicator.TabPageIndicator.TabView;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class ComboTabIndicator extends ViewBaseController implements OnClickListener, OnTouchListener, OnPageChangeListener{
	private static final String TAG_CTI = "cti";
	
	private ImageView mShadeIndicator;
	private ImageView mEditIndicator;
	private TabPageIndicator mIndicator;
	
	private OnComboTabIndicatorClickListener mOnComboTabIndicatorClickListener;
	
	public ComboTabIndicator(Activity act, int viewResId, int layoutResId) {
		super(act, viewResId, layoutResId);
	}

	@Override
	protected void initView() {
		mIndicator = (TabPageIndicator) mView.findViewById(R.id.indicator);
		mShadeIndicator = (ImageView) mView.findViewById(R.id.indicator_shade);
		mEditIndicator = (ImageView) mView.findViewById(R.id.indicator_edit);
		
		mEditIndicator.setOnClickListener(this);
		
		mIndicator.setOnTouchListener(this);
		mIndicator.setOnPageChangeListener(this);
	}
	
	public void setViewPager (ViewPager viewpager) {
		mIndicator.setViewPager(viewpager);
	}
	
	public void setOnComboTabIndicatorClickListener(OnComboTabIndicatorClickListener listener) {
		mOnComboTabIndicatorClickListener = listener;
	}
	
	public void setEditButtonVisible(int visibility) {
		mEditIndicator.setVisibility(visibility);
	}
	
	public void notifyDataSetChanged() {
		mIndicator.notifyDataSetChanged();
	}
	
	public interface OnComboTabIndicatorClickListener {
		void onEditButtonClicked(View v); 
	}

	@Override
	public void onClick(View v) {
		if (null == mOnComboTabIndicatorClickListener)
			return;
        switch (v.getId()) {
		case R.id.indicator_edit:
			mOnComboTabIndicatorClickListener.onEditButtonClicked(v);
			break;
		}		
	}
	

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		Log.d(TAG_CTI, "onTouch()");
		updateShadeIndicator(isInvisibleChildInTheRightAfterTouch());
		return false;
	}
	
	private void updateShadeIndicator(boolean  isExistChildInRight) {
		Log.d("tt", "updateIndicatorShade() isExistChildInRight = " + isExistChildInRight);
		if (isExistChildInRight) {
			mShadeIndicator.setVisibility(View.VISIBLE);
			getContainer().setPadding(0, 0, 100, 0);
		} else {
			mShadeIndicator.setVisibility(View.GONE);
			getContainer().setPadding(0, 0, 0, 0);
		}
	}
	
	public IcsLinearLayout getContainer () {
		return (IcsLinearLayout) mIndicator.getContainer();
	} 
	
	public boolean isInvisibleChildInTheRightAfterTouch() {
		boolean ret = false;
		IcsLinearLayout container = (IcsLinearLayout) mIndicator.getContainer();
		int childCount = container.getChildCount();
		TabView lastChildView = (TabView) container.getChildAt(childCount - 1);
		
		Log.d(TAG_CTI, "updateIndicatorShade() lastChildView.getMeasuredWidth() = " + lastChildView.getMeasuredWidth());
		int[] locationInScreen = new int[2];
		lastChildView.getLocationOnScreen(locationInScreen);
		
		Log.d(TAG_CTI, "updateIndicatorShade() lastChildView locationInScreen[0] = " + locationInScreen[0]);
		Log.d(TAG_CTI, "updateIndicatorShade() lastChildView locationInScreen[1] = " + locationInScreen[1]);
		
		WindowManager mWindowManager = (WindowManager) mActivity.getApplicationContext()
				.getSystemService(Context.WINDOW_SERVICE);
		
		int screenWidth = mWindowManager.getDefaultDisplay().getWidth();
		Log.d(TAG_CTI, "updateIndicatorShade() lastChildView screenWidth= " + screenWidth);
		
		if (locationInScreen[0] > screenWidth) {
			ret = true;
		}  
		
		Log.d(TAG_CTI, "isInvisibleChildInTheRightAfterTouch ret = " + ret);
		
		return ret;
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
	}

	@Override
	public void onPageSelected(int position) {
		Log.d(TAG_CTI, "onPageSelected() position = " + position);
		updateShadeIndicator(isInvisibleChildInTheRightAfterSelect(position));
	}
	
	public boolean isInvisibleChildInTheRightAfterSelect(int position) {
		boolean ret = false;
		/*
		 *  select child's three state
		 *  1, in the middle
		 *  2, in the left
		 *  3, in the right
		 * 
		 */
		
		if (canScollToMiddle(position)) {//middle
			Log.d(TAG_CTI, "updateIndicatorShade() canScollToMiddle(position)");
			if ((getLengthSumFromRightToSelect(position) - getContainer().getChildAt(position).getWidth()) > (getRemainingLengthExceptSelect(position) / 2 )) {
				ret = true;
			}
			
		} else if (canScrollToLeft(position)) {//in the left
			Log.d(TAG_CTI, "updateIndicatorShade() canScrollToLeft(position)");
			if (getScreenWidth() - getLengthSumFromTo (0, getContainer().getChildCount() - 2) > 0) {//in the left
				ret = true;
			}
			
		} else if (canScrollToRight(position)) {// int the right
			Log.d(TAG_CTI, "updateIndicatorShade() canScrollToRight(position)"); // in the right
			ret = true;
		} else {
			Log.d("tt", "updateIndicatorShade() default");
			return false;
		}
		return ret;
	}
	
	public boolean canScollToMiddle(int selectPos) {
		return canScrollToLeft(selectPos) && canScrollToLeft(selectPos);
	}
	
	public boolean canScrollToLeft(int selectPos) {
		boolean ret = false;
		View selectChild = getContainer().getChildAt(selectPos);
		int selectChildWidth = selectChild.getWidth();
		if ((getLengthSumFromLeftToSelect(selectPos) + selectChildWidth / 2) > (getScreenWidth() / 2)) {
//			Log.d("tt", "updateIndicatorShade() can scroll to left");
			ret = true; 
		}
		return ret;
	}
	
	public boolean canScrollToRight(int selectPos) {
		boolean ret = false;
		View selectChild = getContainer().getChildAt(selectPos);
		int selectChildWidth = selectChild.getWidth();
		if ((getLengthSumFromRightToSelect(selectPos) + selectChildWidth / 2) > (getScreenWidth() / 2)) {
//			Log.d("tt", "updateIndicatorShade() can scroll to right");
			ret = true;
		} 
		return ret;
	}
	
	public int getScreenWidth () {
		WindowManager mWindowManager = (WindowManager) mActivity.getApplicationContext()
				.getSystemService(Context.WINDOW_SERVICE);
		return mWindowManager.getDefaultDisplay().getWidth();
	}
	
	public int getRemainingLengthExceptSelect(int selectPos) {
		View selectChild = getContainer().getChildAt(selectPos);
		int selectChildWidth = selectChild.getWidth();
		return  getScreenWidth() - selectChildWidth;
	}
	
	public int getLengthSumFromLeftToSelect (int selectPos) {
		return getLengthSumFromTo (0, selectPos - 1);
	}
	
	public int getLengthSumFromRightToSelect(int selectPos) {
		int childCount = getContainer().getChildCount();
		return getLengthSumFromTo(selectPos + 1, childCount -1);
	} 
	
	public int getLengthSumFromTo(int start, int end) {
		int lengthSumFromTo = 0;
		for (int i = start; i <= end; i++) {
			lengthSumFromTo += getContainer().getChildAt(i).getWidth();
		}
		return lengthSumFromTo;
	}

}
