package com.lifemanager.phone.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import com.lifemanager.logging.Logger;

public class MainPanel extends RelativeLayout {
	protected static final Logger LOG = Logger.getLogger("MainPanel");
	//
	private static SlidingTaskFrame _slidingView = null;
	//
	private static View _menuView;
	//
	private final LayoutParams BEHIND_PARAMS = new LayoutParams(
			LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
	//
	private final LayoutParams ABOVE_PARAMS = new LayoutParams(
			LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

	public MainPanel(Context context) {
		super(context);
	}

	public MainPanel(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MainPanel(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setMenuView(View view) {
		_menuView = view;
		_menuView.setFocusable(true);
		_menuView.setClickable(true);
		BEHIND_PARAMS.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		addView(view, BEHIND_PARAMS);
	}

	public void setTaskView(View view) {
		_slidingView = new SlidingTaskFrame(getContext());
		_slidingView.setView(view);
		addView(_slidingView, ABOVE_PARAMS);
		_slidingView.invalidate();
		//
		_slidingView.setClickable(true);
		_slidingView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				LOG.debug("_slidingView#onClick::::" + v);
			}
		});
	}

	public void addTaskView(View taskView) {
		_slidingView.setView(taskView);
		_slidingView.invalidate();
	}

	public void showTaskView() {
		_slidingView.hideMenuView();
	}

	public void onMenuActionIconClick() {
		LOG.debug("onMenuActionIconClick function called ... ");
		switchMenuView();
	}

	private void switchMenuView() {
		_slidingView.switchMenuView();
		LOG.debug("_menuView.request focus ");
		_menuView.requestFocus();
	}

	private static class SlidingTaskFrame extends ViewGroup {

		private FrameLayout mContainer;
		private Scroller mScroller;
		private VelocityTracker mVelocityTracker;
		private int mTouchSlop;
		private float mLastMotionX;
		private float mLastMotionY;
		private static final int SNAP_VELOCITY = 1000;

		public SlidingTaskFrame(Context context) {
			super(context);
			init();
		}

		@Override
		protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
			mContainer.measure(widthMeasureSpec, heightMeasureSpec);
		}

		@Override
		protected void onLayout(boolean changed, int l, int t, int r, int b) {
			final int width = r - l;
			final int height = b - t;
			mContainer.layout(0, 0, width, height);
		}

		private void init() {
			mContainer = new FrameLayout(getContext());
			mContainer.setBackgroundColor(0xff000000);
			mScroller = new Scroller(getContext());
			mTouchSlop = ViewConfiguration.get(getContext())
					.getScaledTouchSlop();
			super.addView(mContainer);
		}

		public void setView(View v) {
			if (mContainer.getChildCount() > 0) {
				mContainer.removeAllViews();
			}
			mContainer.addView(v);
		}

		@Override
		public void scrollTo(int x, int y) {
			super.scrollTo(x, y);
			postInvalidate();
		}

		@Override
		public void computeScroll() {
			if (!mScroller.isFinished()) {
				if (mScroller.computeScrollOffset()) {
					int oldX = getScrollX();
					int oldY = getScrollY();
					int x = mScroller.getCurrX();
					int y = mScroller.getCurrY();
					if (oldX != x || oldY != y) {
						scrollTo(x, y);
					}
					// Keep on drawing until the animation has finished.
					invalidate();
				} else {
					clearChildrenCache();
				}
			} else {
				clearChildrenCache();
			}
		}

		private boolean mIsBeingDragged;

		@Override
		public boolean onInterceptTouchEvent(MotionEvent ev) {

			final int action = ev.getAction();
			final float x = ev.getX();
			final float y = ev.getY();

			switch (action) {
			case MotionEvent.ACTION_DOWN:
				mLastMotionX = x;
				mLastMotionY = y;
				mIsBeingDragged = false;
				return mIsBeingDragged;

			case MotionEvent.ACTION_MOVE:
				final float dx = x - mLastMotionX;
				final float xDiff = Math.abs(dx);
				final float yDiff = Math.abs(y - mLastMotionY);
				if (xDiff > mTouchSlop && xDiff > yDiff) {
					mIsBeingDragged = true;
					mLastMotionX = x;
				}
				return mIsBeingDragged;
			case MotionEvent.ACTION_UP:
				return false;
			}

			return false;
		}

		@Override
		public boolean onTouchEvent(MotionEvent ev) {
			if (mVelocityTracker == null) {
				mVelocityTracker = VelocityTracker.obtain();
			}
			mVelocityTracker.addMovement(ev);

			final int action = ev.getAction();
			final float x = ev.getX();
			final float y = ev.getY();

			switch (action) {
			case MotionEvent.ACTION_DOWN:
				LOG.info("onTouchEvent:ACTION_DOWN ");
				// if (!mScroller.isFinished()) {
				// mScroller.abortAnimation();
				// }
				// mLastMotionX = x;
				// mLastMotionY = y;
				// if (getScrollX() == -getMenuViewWidth()
				// && mLastMotionX < getMenuViewWidth()) {
				// return false;
				// }

				// if (getScrollX() == getDetailViewWidth()
				// && mLastMotionX > getMenuViewWidth()) {
				// return false;
				// }

				break;
			case MotionEvent.ACTION_MOVE:
				LOG.info("onTouchEvent:ACTION_MOVE & mIsBeingDragged:"
						+ mIsBeingDragged);
				if (mIsBeingDragged) {
					enableChildrenCache();
					final float deltaX = mLastMotionX - x;
					mLastMotionX = x;
					float oldScrollX = getScrollX();
					float scrollX = oldScrollX + deltaX;
					if (deltaX < 0 && oldScrollX < 0) {// move to right
						if (scrollX > 0) {
						} else {
							scrollX = oldScrollX;
						}
					} else if (deltaX > 0 && oldScrollX >= 0) {// move to
																// left
						if (scrollX < getMenuViewWidth()) {
						} else {
							scrollX = getMenuViewWidth();
						}
					}
					scrollTo((int) scrollX, getScrollY());
				}
				break;
			case MotionEvent.ACTION_CANCEL:
				LOG.info("onTouchEvent:ACTION_CANCEL");
			case MotionEvent.ACTION_UP:
				LOG.info("onTouchEvent:ACTION_UP & mIsBeingDragged:"
						+ mIsBeingDragged);
				if (mIsBeingDragged) {
					final VelocityTracker velocityTracker = mVelocityTracker;
					velocityTracker.computeCurrentVelocity(1000);
					int velocityX = (int) velocityTracker.getXVelocity();
					velocityX = 0;
					int oldScrollX = getScrollX();
					LOG.error("oldScrollX == " + oldScrollX);
					int menuViewWidth = getMenuViewWidth();
					int dx = 0;
					if (oldScrollX < 0) {
						dx = -oldScrollX;
					} else {
						if (oldScrollX > menuViewWidth / 2
								|| velocityX < -SNAP_VELOCITY) {
							dx = menuViewWidth - oldScrollX;
						} else if (oldScrollX <= menuViewWidth / 2
								|| velocityX > SNAP_VELOCITY) {
							dx = -oldScrollX;
						}
					}
					smoothScrollTo(dx);
					clearChildrenCache();
				}
				return false;
			}
			if (mVelocityTracker != null) {
				mVelocityTracker.recycle();
				mVelocityTracker = null;
			}
			return true;
		}

		private int getMenuViewWidth() {
			if (_menuView == null) {
				return 0;
			}
			return _menuView.getWidth();
		}

		public void showMenuView() {
			int menuWidth = getMenuViewWidth();
			int oldScrollX = getScrollX();
			if (oldScrollX == 0) {
				smoothScrollTo(menuWidth);
			}
		}

		public void hideMenuView() {
			int menuWidth = getMenuViewWidth();
			int oldScrollX = getScrollX();
			if (oldScrollX == menuWidth) {
				smoothScrollTo(-menuWidth);
			}
		}

		public void switchMenuView() {
			int menuWidth = getMenuViewWidth();
			int oldScrollX = getScrollX();
			if (oldScrollX == 0) {
				smoothScrollTo(menuWidth);
			} else if (oldScrollX == menuWidth) {
				smoothScrollTo(-menuWidth);
			}
		}

		private void smoothScrollTo(int dx) {
			int duration = 500;
			int oldScrollX = getScrollX();
			mScroller.startScroll(oldScrollX, getScrollY(), dx, getScrollY(),
					duration);
			invalidate();
		}

		private void enableChildrenCache() {
			final int count = getChildCount();
			for (int i = 0; i < count; i++) {
				final View layout = (View) getChildAt(i);
				layout.setDrawingCacheEnabled(true);
			}
		}

		private void clearChildrenCache() {
			final int count = getChildCount();
			for (int i = 0; i < count; i++) {
				final View layout = (View) getChildAt(i);
				layout.setDrawingCacheEnabled(false);
			}
		}

	}

}
