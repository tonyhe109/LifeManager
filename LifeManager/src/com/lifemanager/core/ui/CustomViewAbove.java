package com.lifemanager.core.ui;

import android.content.Context;
import android.util.FloatMath;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.lifemanager.ui.core.SlidingMenu;
import com.lifemanager.ui.core.SlidingMenu.OnClosedListener;
import com.lifemanager.ui.core.SlidingMenu.OnOpenedListener;

public class CustomViewAbove extends ViewGroup implements CustomView {

	private static final int MAX_SETTLE_DURATION = 600; // ms
	private static final int MIN_DISTANCE_FOR_FLING = 25; // dips

	protected int _TouchMode = SlidingMenu.TOUCHMODE_MARGIN;

	private CustomViewBehind _ViewBehind;
	private OnPageChangeListener _OnPageChangeListener;
	private OnPageChangeListener _InternalPageChangeListener;
	private OnClosedListener _ClosedListener;
	private OnOpenedListener _OpenedListener;
	private View _Content;
	private int _CurItem;
	private Scroller _Scroller;
	private boolean _Scrolling;
	private boolean _ScrollingCacheEnabled;
	
	private static final boolean USE_CACHE = false;

	private float _ScrollX = 0.0f;
	private boolean _Enabled = true;
	
	
	/**
	 * Callback interface for responding to changing state of the selected page.
	 */
	public interface OnPageChangeListener {

		/**
		 * This method will be invoked when the current page is scrolled, either
		 * as part of a programmatically initiated smooth scroll or a user
		 * initiated touch scroll.
		 * 
		 * @param position
		 *            Position index of the first page currently being
		 *            displayed. Page position+1 will be visible if
		 *            positionOffset is nonzero.
		 * @param positionOffset
		 *            Value from [0, 1) indicating the offset from the page at
		 *            position.
		 * @param positionOffsetPixels
		 *            Value in pixels indicating the offset from position.
		 */
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels);

		/**
		 * This method will be invoked when a new page becomes selected.
		 * Animation is not necessarily complete.
		 * 
		 * @param position
		 *            Position index of the new selected page.
		 */
		public void onPageSelected(int position);

	}

	/**
	 * Set a listener that will be invoked whenever the page changes or is
	 * incrementally scrolled. See {@link OnPageChangeListener}.
	 * 
	 * @param listener
	 *            Listener to set
	 */
	public void setOnPageChangeListener(OnPageChangeListener listener) {
		_OnPageChangeListener = listener;
	}

	public CustomViewAbove(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		final int width = r - l;
		final int height = b - t;
		_Content.layout(0, 0, width, height);

	}

	@Override
	public void setOppositeView(View v) {
		_ViewBehind = (CustomViewBehind) v;
	}

	@Override
	public void setContent(View v) {
		if (_Content != null)
			this.removeView(_Content);
		_Content = v;
		addView(_Content);
	}

	/**
	 * Set the currently selected page. If the CustomViewPager has already been
	 * through its first layout there will be a smooth animated transition
	 * between the current item and the specified item.
	 * 
	 * @param item
	 *            Item index to select
	 */
	public void setCurrentItem(int item) {
		setCurrentItemInternal(item, true, false);
	}

	/**
	 * Set the currently selected page.
	 * 
	 * @param item
	 *            Item index to select
	 * @param smoothScroll
	 *            True to smoothly scroll to the new item, false to transition
	 *            immediately
	 */
	public void setCurrentItem(int item, boolean smoothScroll) {
		setCurrentItemInternal(item, smoothScroll, false);
	}

	public int getCurrentItem() {
		return _CurItem;
	}

	void setCurrentItemInternal(int item, boolean smoothScroll, boolean always) {
		setCurrentItemInternal(item, smoothScroll, always, 0);
	}

	void setCurrentItemInternal(int item, boolean smoothScroll, boolean always,
			int velocity) {
		if (!always && _CurItem == item) {
			setScrollingCacheEnabled(false);
			return;
		}
		item = _ViewBehind.getMenuPage(item);

		final boolean dispatchSelected = _CurItem != item;

		_CurItem = item;
		final int destX = getDestScrollX(_CurItem);
		if (dispatchSelected && _OnPageChangeListener != null) {
			_OnPageChangeListener.onPageSelected(item);
		}
		if (dispatchSelected && _InternalPageChangeListener != null) {
			_InternalPageChangeListener.onPageSelected(item);
		}
		if (smoothScroll) {
			smoothScrollTo(destX, 0, velocity);
		} else {
			completeScroll();
			scrollTo(destX, 0);
		}
	}
	
	

	@Override
	public void scrollTo(int x, int y) {
		super.scrollTo(x, y);
		_ScrollX = x;
		if (_Enabled)
			_ViewBehind.scrollBehindTo(_Content, x, y);
	}


	/**
	 * Like {@link View#scrollBy}, but scroll smoothly instead of immediately.
	 * 
	 * @param x
	 *            the number of pixels to scroll by on the X axis
	 * @param y
	 *            the number of pixels to scroll by on the Y axis
	 */
	void smoothScrollTo(int x, int y) {
		smoothScrollTo(x, y, 0);
	}

	/**
	 * Like {@link View#scrollBy}, but scroll smoothly instead of immediately.
	 * 
	 * @param x
	 *            the number of pixels to scroll by on the X axis
	 * @param y
	 *            the number of pixels to scroll by on the Y axis
	 * @param velocity
	 *            the velocity associated with a fling, if applicable. (0
	 *            otherwise)
	 */
	void smoothScrollTo(int x, int y, int velocity) {
		if (getChildCount() == 0) {
			// Nothing to do.
			setScrollingCacheEnabled(false);
			return;
		}
		int sx = getScrollX();
		int sy = getScrollY();
		int dx = x - sx;
		int dy = y - sy;
		if (dx == 0 && dy == 0) {
			completeScroll();
			if (isMenuOpen()) {
				if (_OpenedListener != null)
					_OpenedListener.onOpened();
			} else {
				if (_ClosedListener != null)
					_ClosedListener.onClosed();
			}
			return;
		}
		setScrollingCacheEnabled(true);
		_Scrolling = true;
		final int width = getBehindWidth();
		final int halfWidth = width / 2;
		final float distanceRatio = Math.min(1f, 1.0f * Math.abs(dx) / width);
		final float distance = halfWidth + halfWidth
				* distanceInfluenceForSnapDuration(distanceRatio);

		int duration = 0;
		velocity = Math.abs(velocity);
		if (velocity > 0) {
			duration = 4 * Math.round(1000 * Math.abs(distance / velocity));
		} else {
			final float pageDelta = (float) Math.abs(dx) / width;
			duration = (int) ((pageDelta + 1) * 100);
			duration = MAX_SETTLE_DURATION;
		}
		duration = Math.min(duration, MAX_SETTLE_DURATION);
		_Scroller.startScroll(sx, sy, dx, dy, duration);
		invalidate();
	}

	// We want the duration of the page snap animation to be influenced by the
	// distance that
	// the screen has to travel, however, we don't want this duration to be
	// effected in a
	// purely linear fashion. Instead, we use this method to moderate the effect
	// that the distance
	// of travel has on the overall snap duration.
	float distanceInfluenceForSnapDuration(float f) {
		f -= 0.5f; // center the values about 0.
		f *= 0.3f * Math.PI / 2.0f;
		return (float) FloatMath.sin(f);
	}

	private void completeScroll() {
		boolean needPopulate = _Scrolling;
		if (needPopulate) {
			// Done with scroll, no longer want to cache view drawing.
			setScrollingCacheEnabled(false);
			_Scroller.abortAnimation();
			int oldX = getScrollX();
			int oldY = getScrollY();
			int x = _Scroller.getCurrX();
			int y = _Scroller.getCurrY();
			if (oldX != x || oldY != y) {
				scrollTo(x, y);
			}
			if (isMenuOpen()) {
				if (_OpenedListener != null)
					_OpenedListener.onOpened();
			} else {
				if (_ClosedListener != null)
					_ClosedListener.onClosed();
			}
		}
		_Scrolling = false;
	}

	int getBehindWidth() {
		if (_ViewBehind == null) {
			return 0;
		} else {
			return _ViewBehind.getBehindWidth();
		}
	}

	private boolean isMenuOpen() {
		return _CurItem == 0 || _CurItem == 2;
	}

	private void setScrollingCacheEnabled(boolean enabled) {
		if (_ScrollingCacheEnabled != enabled) {
			_ScrollingCacheEnabled = enabled;
			if (USE_CACHE) {
				final int size = getChildCount();
				for (int i = 0; i < size; ++i) {
					final View child = getChildAt(i);
					if (child.getVisibility() != GONE) {
						child.setDrawingCacheEnabled(enabled);
					}
				}
			}
		}
	}

	public int getDestScrollX(int page) {
		switch (page) {
		case 0:
		case 2:
			return _ViewBehind.getMenuLeft(_Content, page);
		case 1:
			return _Content.getLeft();
		}
		return 0;
	}

	@Override
	public void setTouchMode(int i) {
		_TouchMode = i;
	}

}
