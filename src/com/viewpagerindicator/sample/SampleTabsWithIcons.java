package com.viewpagerindicator.sample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.viewpagerindicator.IconPagerAdapter;
import com.viewpagerindicator.sample.ComboTabIndicator.OnComboTabIndicatorClickListener;

public class SampleTabsWithIcons extends FragmentActivity implements OnComboTabIndicatorClickListener {
   
	private static final String TAG_INDICATOR_LISTENER = "il";
	
	private static final String[] CONTENT = new String[] { "Local0", "Local1", "Local2", "Local3", "Local4", "Local5", "Local6", "Local7" };
	
    private static final int[] ICONS = new int[] {
            R.drawable.perm_group_calendar,
            R.drawable.perm_group_camera,
            R.drawable.perm_group_device_alarms,
            R.drawable.perm_group_location,
            R.drawable.perm_group_calendar,
            R.drawable.perm_group_camera,
            R.drawable.perm_group_device_alarms,
            R.drawable.perm_group_location,
    };
    
	private FragmentPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_tabs);

        mPagerAdapter = new GoogleMusicAdapter(getSupportFragmentManager());

        ViewPager pager = (ViewPager)findViewById(R.id.pager);
        pager.setAdapter(mPagerAdapter);

        ComboTabIndicator mIndicator = new ComboTabIndicator(this, R.id.combo_tab_indicator_container, ViewBaseController.INVALID_LAYOUT_RES_ID);
        
        mIndicator.setViewPager(pager);
        
        mIndicator.setEditButtonVisible(View.VISIBLE);
        
        mIndicator.setOnComboTabIndicatorClickListener(this);
    }

    class GoogleMusicAdapter extends FragmentPagerAdapter implements IconPagerAdapter {
        public GoogleMusicAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return TestFragment.newInstance(CONTENT[position % CONTENT.length]);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return CONTENT[position % CONTENT.length].toUpperCase();
        }

        @Override public int getIconResId(int index) {
          return ICONS[index];
        }

      @Override
        public int getCount() {
          return CONTENT.length;
        }
    }

	@Override
	public void onEditButtonClicked(View v) {
		Log.d("tt", "onEditButtonClicked()");
	}

//	@Override
//	public void onClick(View v) {
//		Log.d(TAG_INDICATOR_LISTENER, "onClick() v = " + v);
//	}
//
//	@Override
//	public boolean onDrag(View v, DragEvent event) {
//		Log.d(TAG_INDICATOR_LISTENER, "onPageScrollStateChanged() v = " + v);
//		Log.d(TAG_INDICATOR_LISTENER, "onPageScrollStateChanged() event = " + event);
//		return false;
//	}
//
//	@Override
//	public void onPageScrollStateChanged(int arg0) {
//		Log.d(TAG_INDICATOR_LISTENER, "onPageScrollStateChanged() arg0 = " + arg0);
//		
//	}
//
//	@Override
//	public void onPageScrolled(int arg0, float arg1, int arg2) {
//		Log.d(TAG_INDICATOR_LISTENER, "onPageScrolled() arg0 = " + arg0);
//		Log.d(TAG_INDICATOR_LISTENER, "onPageScrolled() arg1 = " + arg1);
//		Log.d(TAG_INDICATOR_LISTENER, "onPageScrolled() arg2 = " + arg2);
//	}

//	@Override
//	public void onPageSelected(int position) {
//		Log.d(TAG_INDICATOR_LISTENER, "onPageSelected() position = " + position);
//		updateShadeIndicator(isInvisibleChildInTheRightAfterSelect(position));
//	}
	
//	private void updateShadeIndicator(boolean  isExistChildInRight) {
//		Log.d("tt", "updateIndicatorShade() isExistChildInRight = " + isExistChildInRight);
//		if (isExistChildInRight) {
//			mShadeIndicator.setVisibility(View.VISIBLE);
//			getContainer().setPadding(0, 0, 100, 0);
//		} else {
//			mShadeIndicator.setVisibility(View.GONE);
//			getContainer().setPadding(0, 0, 0, 0);
//		}
//	}
	
//	private void updateEditIndicator (boolean isExistEditChild) {
//		Log.d("tt", "updateIndicatorShade() isExistChildInRight = " + isExistEditChild);
//		if (isExistEditChild) {
//			mEditIndicator.setVisibility(View.VISIBLE);
//			getContainer().setPadding(0, 0, 100, 0);
//		} else {
//			mEditIndicator.setVisibility(View.GONE);
//			getContainer().setPadding(0, 0, 0, 0);
//		}
//	}
	
	
//	public boolean isInvisibleChildInTheRightAfterTouch() {
//		boolean ret = false;
//		IcsLinearLayout container = (IcsLinearLayout) mIndicator.getContainer();
//		int childCount = container.getChildCount();
//		TabView lastChildView = (TabView) container.getChildAt(childCount - 1);
//		
//		Log.d(TAG_INDICATOR_LISTENER, "updateIndicatorShade() lastChildView.getMeasuredWidth() = " + lastChildView.getMeasuredWidth());
//		int[] locationInScreen = new int[2];
//		lastChildView.getLocationOnScreen(locationInScreen);
//		
//		Log.d(TAG_INDICATOR_LISTENER, "updateIndicatorShade() lastChildView locationInScreen[0] = " + locationInScreen[0]);
//		Log.d(TAG_INDICATOR_LISTENER, "updateIndicatorShade() lastChildView locationInScreen[1] = " + locationInScreen[1]);
//		
//		WindowManager mWindowManager = (WindowManager) this.getApplicationContext()
//				.getSystemService(Context.WINDOW_SERVICE);
//		
//		int screenWidth = mWindowManager.getDefaultDisplay().getWidth();
//		Log.d(TAG_INDICATOR_LISTENER, "updateIndicatorShade() lastChildView screenWidth= " + screenWidth);
//		
//		if (locationInScreen[0] > screenWidth) {
//			ret = true;
//		}  
//		
//		Log.d(TAG_INDICATOR_LISTENER, "isInvisibleChildInTheRightAfterTouch ret = " + ret);
//		
//		return ret;
//	}
	
	
//	public boolean isInvisibleChildInTheRightAfterSelect(int position) {
//		boolean ret = false;
//		/*
//		 *  select child's three state
//		 *  1, in the middle
//		 *  2, in the left
//		 *  3, in the right
//		 * 
//		 */
//		
//		if (canScollToMiddle(position)) {//middle
//			Log.d("tt", "updateIndicatorShade() canScollToMiddle(position)");
//			if ((getLengthSumFromRightToSelect(position) - getContainer().getChildAt(position).getWidth()) > (getRemainingLengthExceptSelect(position) / 2 )) {
//				ret = true;
//			}
//			
//		} else if (canScrollToLeft(position)) {//in the left
//			Log.d("tt", "updateIndicatorShade() canScrollToLeft(position)");
//			if (getScreenWidth() - getLengthSumFromTo (0, getContainer().getChildCount() - 2) > 0) {//in the left
//				ret = true;
//			}
//			
//		} else if (canScrollToRight(position)) {// int the right
//			Log.d("tt", "updateIndicatorShade() canScrollToRight(position)"); // in the right
//			ret = true;
//		} else {
//			Log.d("tt", "updateIndicatorShade() default");
//			return true;
//		}
//		return ret;
//	}
	
	
//	public IcsLinearLayout getContainer () {
//		return (IcsLinearLayout) mIndicator.getContainer();
//	} 
	
//	public int getRemainingLengthExceptSelect(int selectPos) {
//		View selectChild = getContainer().getChildAt(selectPos);
//		int selectChildWidth = selectChild.getWidth();
//		return  getScreenWidth() - selectChildWidth;
//	}
//	
//	public boolean canScrollToLeft(int selectPos) {
//		boolean ret = false;
//		View selectChild = getContainer().getChildAt(selectPos);
//		int selectChildWidth = selectChild.getWidth();
//		if ((getLengthSumFromLeftToSelect(selectPos) + selectChildWidth / 2) > (getScreenWidth() / 2)) {
////			Log.d("tt", "updateIndicatorShade() can scroll to left");
//			ret = true; 
//		}
//		return ret;
//	}
	
//	public int getScreenWidth () {
//		WindowManager mWindowManager = (WindowManager) this.getApplicationContext()
//				.getSystemService(Context.WINDOW_SERVICE);
//		return mWindowManager.getDefaultDisplay().getWidth();
//	}
//	
//	public boolean canScrollToRight(int selectPos) {
//		boolean ret = false;
//		View selectChild = getContainer().getChildAt(selectPos);
//		int selectChildWidth = selectChild.getWidth();
//		if ((getLengthSumFromRightToSelect(selectPos) + selectChildWidth / 2) > (getScreenWidth() / 2)) {
////			Log.d("tt", "updateIndicatorShade() can scroll to right");
//			ret = true;
//		} 
//		return ret;
//	}
//	
//	public boolean canScollToMiddle(int selectPos) {
//		return canScrollToLeft(selectPos) && canScrollToLeft(selectPos);
//	}
//	
//	public int getLengthSumFromLeftToSelect (int selectPos) {
//		return getLengthSumFromTo (0, selectPos - 1);
//	}
//	
//	public int getLengthSumFromRightToSelect(int selectPos) {
//		int childCount = getContainer().getChildCount();
//		return getLengthSumFromTo(selectPos + 1, childCount -1);
//	} 
//	
//	public int getLengthSumFromTo(int start, int end) {
//		int lengthSumFromTo = 0;
//		for (int i = start; i <= end; i++) {
//			lengthSumFromTo += getContainer().getChildAt(i).getWidth();
//		}
//		return lengthSumFromTo;
//	}
	 

//	@Override
//	public void onChildViewAdded(View parent, View child) {
//		Log.d(TAG_INDICATOR_LISTENER, "onChildViewAdded() child = " + child);
//	}
//
//	@Override
//	public void onChildViewRemoved(View parent, View child) {
//		Log.d(TAG_INDICATOR_LISTENER, "onChildViewRemoved() child = " + child);
//	}
//
//	@Override
//	public void onFocusChange(View v, boolean hasFocus) {
//		Log.d(TAG_INDICATOR_LISTENER, "onFocusChange() hasFocus = " + hasFocus);
//	}
//
//	@Override
//	public void onTabReselected(int position) {
//		Log.d(TAG_INDICATOR_LISTENER, "onTabReselected() position = " + position);
//		
//	}
//
//	@Override
//	public boolean onTouch(View v, MotionEvent event) {
//		Log.d(TAG_INDICATOR_LISTENER, "onTouch()");
//		updateShadeIndicator(isInvisibleChildInTheRightAfterTouch());
//		return false;
//	}
	
}
