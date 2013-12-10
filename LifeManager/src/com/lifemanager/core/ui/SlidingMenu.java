package com.lifemanager.core.ui;

import java.lang.reflect.Method;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.lifemanager.R;
import com.lifemanager.core.ui.CustomViewAbove.OnPageChangeListener;

public class SlidingMenu extends RelativeLayout {

	/**
	 * Constant value for use with setMode(). Puts the menu to the left of the
	 * content.
	 */
	public static final int LEFT = 0;

	/**
	 * Constant value for use with setMode(). Puts the menu to the right of the
	 * content.
	 */
	public static final int RIGHT = 1;

	/**
	 * Constant value for use with setMode(). Puts menus to the left and right
	 * of the content.
	 */
	public static final int LEFT_RIGHT = 2;

	/**
	 * Constant value for use with setTouchModeAbove(). Allows the SlidingMenu
	 * to be opened with a swipe gesture on the screen's margin
	 */
	public static final int TOUCHMODE_MARGIN = 0;

	/**
	 * Constant value for use with setTouchModeAbove(). Allows the SlidingMenu
	 * to be opened with a swipe gesture anywhere on the screen
	 */
	public static final int TOUCHMODE_FULLSCREEN = 1;

	/**
	 * Constant value for use with setTouchModeAbove(). Denies the SlidingMenu
	 * to be opened with a swipe gesture
	 */
	public static final int TOUCHMODE_NONE = 2;

	private CustomViewAbove _ViewAbove;

	private CustomViewBehind _ViewBehind;

	private OnOpenListener _OpenListener;

	private OnCloseListener _CloseListener;

	/**
	 * The listener interface for receiving onOpen events. The class that is
	 * interested in processing a onOpen event implements this interface, and
	 * the object created with that class is registered with a component using
	 * the component's <code>addOnOpenListener<code> method. When
	 * the onOpen event occurs, that object's appropriate
	 * method is invoked
	 */
	public interface OnOpenListener {

		/**
		 * On open.
		 */
		public void onOpen();
	}

	/**
	 * The listener interface for receiving onOpened events. The class that is
	 * interested in processing a onOpened event implements this interface, and
	 * the object created with that class is registered with a component using
	 * the component's <code>addOnOpenedListener<code> method. When
	 * the onOpened event occurs, that object's appropriate
	 * method is invoked.
	 * 
	 * @see OnOpenedEvent
	 */
	public interface OnOpenedListener {

		/**
		 * On opened.
		 */
		public void onOpened();
	}

	/**
	 * The listener interface for receiving onClose events. The class that is
	 * interested in processing a onClose event implements this interface, and
	 * the object created with that class is registered with a component using
	 * the component's <code>addOnCloseListener<code> method. When
	 * the onClose event occurs, that object's appropriate
	 * method is invoked.
	 * 
	 * @see OnCloseEvent
	 */
	public interface OnCloseListener {

		/**
		 * On close.
		 */
		public void onClose();
	}

	/**
	 * The listener interface for receiving onClosed events. The class that is
	 * interested in processing a onClosed event implements this interface, and
	 * the object created with that class is registered with a component using
	 * the component's <code>addOnClosedListener<code> method. When
	 * the onClosed event occurs, that object's appropriate
	 * method is invoked.
	 * 
	 * @see OnClosedEvent
	 */
	public interface OnClosedListener {

		/**
		 * On closed.
		 */
		public void onClosed();
	}

	/**
	 * Instantiates a new SlidingMenu.
	 * 
	 * @param context
	 *            the associated Context
	 */
	public SlidingMenu(Context context) {
		this(context, null);
	}

	/**
	 * Instantiates a new SlidingMenu.
	 * 
	 * @param context
	 *            the associated Context
	 * @param attrs
	 *            the attrs
	 */
	public SlidingMenu(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	/**
	 * Instantiates a new SlidingMenu.
	 * 
	 * @param context
	 *            the associated Context
	 * @param attrs
	 *            the attrs
	 * @param defStyle
	 *            the def style
	 */
	public SlidingMenu(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		LayoutParams behindParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		_ViewBehind = new CustomViewBehind(context);
		addView(_ViewBehind, behindParams);
		LayoutParams aboveParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		_ViewAbove = new CustomViewAbove(context);
		addView(_ViewAbove, aboveParams);
		// register the CustomViewBehind with the CustomViewAbove
		_ViewAbove.setOppositeView(_ViewBehind);
		// register the CustomViewAbove with the CustomViewBehind
		_ViewBehind.setOppositeView(_ViewAbove);
		_ViewAbove.setOnPageChangeListener(new OnPageChangeListener() {
			public static final int POSITION_OPEN = 0;
			public static final int POSITION_CLOSE = 1;

			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
			}

			public void onPageSelected(int position) {
				if (position == POSITION_OPEN && _OpenListener != null) {
					_OpenListener.onOpen();
				} else if (position == POSITION_CLOSE && _CloseListener != null) {
					_CloseListener.onClose();
				}
			}
		});

		// now style everything!
		TypedArray ta = context.obtainStyledAttributes(attrs,
				R.styleable.SlidingMenu);
		// set the above and behind views if defined in xml
		int mode = ta.getInt(R.styleable.SlidingMenu_mode, LEFT);
		setMode(mode);
		int viewAbove = ta.getResourceId(R.styleable.SlidingMenu_viewAbove, -1);
		if (viewAbove != -1) {
			setContent(viewAbove);
		} else {
			setContent(new FrameLayout(context));
		}
		int viewBehind = ta.getResourceId(R.styleable.SlidingMenu_viewBehind,
				-1);
		if (viewBehind != -1) {
			setMenu(viewBehind);
		} else {
			setMenu(new FrameLayout(context));
		}
		int touchModeAbove = ta.getInt(R.styleable.SlidingMenu_touchModeAbove,
				TOUCHMODE_MARGIN);
		setTouchModeAbove(touchModeAbove);
		int touchModeBehind = ta.getInt(
				R.styleable.SlidingMenu_touchModeBehind, TOUCHMODE_MARGIN);
		setTouchModeBehind(touchModeBehind);

		int offsetBehind = (int) ta.getDimension(
				R.styleable.SlidingMenu_behindOffset, -1);
		int widthBehind = (int) ta.getDimension(
				R.styleable.SlidingMenu_behindWidth, -1);
		if (offsetBehind != -1 && widthBehind != -1)
			throw new IllegalStateException(
					"Cannot set both behindOffset and behindWidth for a SlidingMenu");
		else if (offsetBehind != -1)
			setBehindOffset(offsetBehind);
		else if (widthBehind != -1)
			setBehindWidth(widthBehind);
		else
			setBehindOffset(0);
		float scrollOffsetBehind = ta.getFloat(
				R.styleable.SlidingMenu_behindScrollScale, 0.33f);
		setBehindScrollScale(scrollOffsetBehind);
		int shadowRes = ta.getResourceId(
				R.styleable.SlidingMenu_shadowDrawable, -1);
		if (shadowRes != -1) {
			setShadowDrawable(shadowRes);
		}
		int shadowWidth = (int) ta.getDimension(
				R.styleable.SlidingMenu_shadowWidth, 0);
		setShadowWidth(shadowWidth);
		boolean fadeEnabled = ta.getBoolean(
				R.styleable.SlidingMenu_fadeEnabled, true);
		setFadeEnabled(fadeEnabled);
		float fadeDeg = ta.getFloat(R.styleable.SlidingMenu_fadeDegree, 0.33f);
		setFadeDegree(fadeDeg);
		boolean selectorEnabled = ta.getBoolean(
				R.styleable.SlidingMenu_selectorEnabled, false);
		setSelectorEnabled(selectorEnabled);
		int selectorRes = ta.getResourceId(
				R.styleable.SlidingMenu_selectorDrawable, -1);
		if (selectorRes != -1)
			setSelectorDrawable(selectorRes);
		ta.recycle();
	}

	/**
	 * Controls whether the SlidingMenu can be opened with a swipe gesture.
	 * Options are {@link #TOUCHMODE_MARGIN TOUCHMODE_MARGIN},
	 * {@link #TOUCHMODE_FULLSCREEN TOUCHMODE_FULLSCREEN}, or
	 * {@link #TOUCHMODE_NONE TOUCHMODE_NONE}
	 * 
	 * @param i
	 *            the new touch mode
	 */
	public void setTouchModeAbove(int i) {
		if (i != TOUCHMODE_FULLSCREEN && i != TOUCHMODE_MARGIN
				&& i != TOUCHMODE_NONE) {
			throw new IllegalStateException(
					"TouchMode must be set to either"
							+ "TOUCHMODE_FULLSCREEN or TOUCHMODE_MARGIN or TOUCHMODE_NONE.");
		}
		_ViewAbove.setTouchMode(i);
	}

	/**
	 * Controls whether the SlidingMenu can be opened with a swipe gesture.
	 * Options are {@link #TOUCHMODE_MARGIN TOUCHMODE_MARGIN},
	 * {@link #TOUCHMODE_FULLSCREEN TOUCHMODE_FULLSCREEN}, or
	 * {@link #TOUCHMODE_NONE TOUCHMODE_NONE}
	 * 
	 * @param i
	 *            the new touch mode
	 */
	public void setTouchModeBehind(int i) {
		if (i != TOUCHMODE_FULLSCREEN && i != TOUCHMODE_MARGIN
				&& i != TOUCHMODE_NONE) {
			throw new IllegalStateException(
					"TouchMode must be set to either"
							+ "TOUCHMODE_FULLSCREEN or TOUCHMODE_MARGIN or TOUCHMODE_NONE.");
		}
		_ViewBehind.setTouchMode(i);
	}

	/**
	 * Sets which side the SlidingMenu should appear on.
	 * 
	 * @param mode
	 *            must be either SlidingMenu.LEFT or SlidingMenu.RIGHT
	 */
	public void setMode(int mode) {
		if (mode != LEFT && mode != RIGHT && mode != LEFT_RIGHT) {
			throw new IllegalStateException(
					"SlidingMenu mode must be LEFT, RIGHT, or LEFT_RIGHT");
		}
		_ViewBehind.setMode(mode);
	}

	/**
	 * Set the above view content from a layout resource. The resource will be
	 * inflated, adding all top-level views to the above view.
	 * 
	 * @param res
	 *            the new content
	 */
	public void setContent(int res) {
		setContent(LayoutInflater.from(getContext()).inflate(res, null));
	}

	/**
	 * Set the above view content to the given View.
	 * 
	 * @param view
	 *            The desired content to display.
	 */
	public void setContent(View view) {
		_ViewAbove.setContent(view);
		showContent();
	}

	/**
	 * Closes the menu and shows the above view.
	 */
	public void showContent() {
		showContent(true);
	}

	/**
	 * Closes the menu and shows the above view.
	 * 
	 * @param animate
	 *            true to animate the transition, false to ignore animation
	 */
	public void showContent(boolean animate) {
		_ViewAbove.setCurrentItem(1, animate);
	}

	/**
	 * Set the behind view (menu) content from a layout resource. The resource
	 * will be inflated, adding all top-level views to the behind view.
	 * 
	 * @param res
	 *            the new content
	 */
	public void setMenu(int res) {
		setMenu(LayoutInflater.from(getContext()).inflate(res, null));
	}

	/**
	 * Set the behind view (menu) content to the given View.
	 * 
	 * @param view
	 *            The desired content to display.
	 */
	public void setMenu(View v) {
		_ViewBehind.setContent(v);
	}

	/**
	 * Sets the behind offset.
	 * 
	 * @param i
	 *            The margin, in pixels, on the right of the screen that the
	 *            behind view scrolls to.
	 */
	public void setBehindOffset(int i) {
		// RelativeLayout.LayoutParams params =
		// ((RelativeLayout.LayoutParams)mViewBehind.getLayoutParams());
		// int bottom = params.bottomMargin;
		// int top = params.topMargin;
		// int left = params.leftMargin;
		// params.setMargins(left, top, i, bottom);
		_ViewBehind.setWidthOffset(i);
	}

	/**
	 * Sets the behind width.
	 * 
	 * @param i
	 *            The width the Sliding Menu will open to, in pixels
	 */
	@SuppressWarnings("deprecation")
	public void setBehindWidth(int i) {
		int width;
		Display display = ((WindowManager) getContext().getSystemService(
				Context.WINDOW_SERVICE)).getDefaultDisplay();
		try {
			Class<?> cls = Display.class;
			Class<?>[] parameterTypes = { Point.class };
			Point parameter = new Point();
			Method method = cls.getMethod("getSize", parameterTypes);
			method.invoke(display, parameter);
			width = parameter.x;
		} catch (Exception e) {
			width = display.getWidth();
		}
		setBehindOffset(width - i);
	}

	/**
	 * Sets the behind scroll scale.
	 * 
	 * @param f
	 *            The scale of the parallax scroll (i.e. 1.0f scrolls 1 pixel
	 *            for every 1 pixel that the above view scrolls and 0.0f scrolls
	 *            0 pixels)
	 */
	public void setBehindScrollScale(float f) {
		if (f < 0 && f > 1)
			throw new IllegalStateException(
					"ScrollScale must be between 0 and 1");
		_ViewBehind.setScrollScale(f);
	}

	/**
	 * Sets the shadow drawable.
	 * 
	 * @param resId
	 *            the resource ID of the new shadow drawable
	 */
	public void setShadowDrawable(int resId) {
		setShadowDrawable(getContext().getResources().getDrawable(resId));
	}

	/**
	 * Sets the shadow drawable.
	 * 
	 * @param d
	 *            the new shadow drawable
	 */
	public void setShadowDrawable(Drawable d) {
		_ViewBehind.setShadowDrawable(d);
	}

	/**
	 * Sets the shadow width.
	 * 
	 * @param pixels
	 *            the new shadow width, in pixels
	 */
	public void setShadowWidth(int pixels) {
		_ViewBehind.setShadowWidth(pixels);
	}

	/**
	 * Enables or disables the SlidingMenu's fade in and out
	 * 
	 * @param b
	 *            true to enable fade, false to disable it
	 */
	public void setFadeEnabled(boolean b) {
		_ViewBehind.setFadeEnabled(b);
	}

	/**
	 * Sets how much the SlidingMenu fades in and out. Fade must be enabled, see
	 * {@link #setFadeEnabled(boolean) setFadeEnabled(boolean)}
	 * 
	 * @param f
	 *            the new fade degree, between 0.0f and 1.0f
	 */
	public void setFadeDegree(float f) {
		_ViewBehind.setFadeDegree(f);
	}

	/**
	 * Enables or disables whether the selector is drawn
	 * 
	 * @param b
	 *            true to draw the selector, false to not draw the selector
	 */
	public void setSelectorEnabled(boolean b) {
		_ViewBehind.setSelectorEnabled(true);
	}

	/**
	 * Sets the selected view. The selector will be drawn here
	 * 
	 * @param v
	 *            the new selected view
	 */
	public void setSelectedView(View v) {
		_ViewBehind.setSelectedView(v);
	}

	/**
	 * Sets the selector drawable.
	 * 
	 * @param res
	 *            a resource ID for the selector drawable
	 */
	public void setSelectorDrawable(int res) {
		_ViewBehind.setSelectorBitmap(BitmapFactory.decodeResource(
				getResources(), res));
	}

}
