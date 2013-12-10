package com.lifemanager.core.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.lifemanager.R;
import com.lifemanager.ui.core.CustomViewAbove;
import com.lifemanager.ui.core.SlidingMenu;

public class CustomViewBehind extends ViewGroup implements CustomView {

	private static final String TAG = "CustomViewBehind";

	private CustomViewAbove _ViewAbove;
	private int _TouchMode = SlidingMenu.TOUCHMODE_MARGIN;
	private View _Content;
	private View _SecondaryContent;
	private int _WidthOffset;
	private int _Mode;
	private float _ScrollScale;
	private Drawable _ShadowDrawable;
	private int _ShadowWidth;

	private boolean _FadeEnabled;
	private float _FadeDegree;

	private boolean _SelectorEnabled = true;
	private Bitmap _SelectorDrawable;
	private View _SelectedView;

	public CustomViewBehind(Context context) {
		super(context);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		final int width = r - l;
		final int height = b - t;
		_Content.layout(0, 0, width - _WidthOffset, height);
		if (_SecondaryContent != null)
			_SecondaryContent.layout(0, 0, width - _WidthOffset, height);

	}

	public void setMode(int mode) {
		if (mode == SlidingMenu.LEFT || mode == SlidingMenu.RIGHT) {
			if (_Content != null)
				_Content.setVisibility(View.VISIBLE);
			if (_SecondaryContent != null)
				_SecondaryContent.setVisibility(View.INVISIBLE);
		}
		_Mode = mode;
	}

	public int getMenuPage(int page) {
		page = (page > 1) ? 2 : ((page < 1) ? 0 : page);
		if (_Mode == SlidingMenu.LEFT && page > 1) {
			return 0;
		} else if (_Mode == SlidingMenu.RIGHT && page < 1) {
			return 2;
		} else {
			return page;
		}
	}

	public int getMenuLeft(View content, int page) {
		if (_Mode == SlidingMenu.LEFT) {
			switch (page) {
			case 0:
				return content.getLeft() - getBehindWidth();
			case 2:
				return content.getLeft();
			}
		} else if (_Mode == SlidingMenu.RIGHT) {
			switch (page) {
			case 0:
				return content.getLeft();
			case 2:
				return content.getLeft() + getBehindWidth();
			}
		} else if (_Mode == SlidingMenu.LEFT_RIGHT) {
			switch (page) {
			case 0:
				return content.getLeft() - getBehindWidth();
			case 2:
				return content.getLeft() + getBehindWidth();
			}
		}
		return content.getLeft();
	}

	int getBehindWidth() {
		return _Content.getWidth();
	}

	@Override
	public void setOppositeView(View v) {
		_ViewAbove = (CustomViewAbove) v;
	}

	@Override
	public void setContent(View v) {
		if (_Content != null)
			removeView(_Content);
		_Content = v;
		addView(_Content);
	}

	public void setTouchMode(int i) {
		_TouchMode = i;
	}

	public void setWidthOffset(int i) {
		_WidthOffset = i;
		requestLayout();
	}

	public void setScrollScale(float scrollScale) {
		_ScrollScale = scrollScale;
	}

	public void setShadowDrawable(Drawable shadow) {
		_ShadowDrawable = shadow;
		invalidate();
	}

	public void setShadowWidth(int width) {
		_ShadowWidth = width;
		invalidate();
	}

	public void setFadeEnabled(boolean b) {
		_FadeEnabled = b;
	}

	public void setFadeDegree(float degree) {
		if (degree > 1.0f || degree < 0.0f)
			throw new IllegalStateException(
					"The BehindFadeDegree must be between 0.0f and 1.0f");
		_FadeDegree = degree;
	}

	public void setSelectorEnabled(boolean b) {
		_SelectorEnabled = b;
	}

	public void setSelectedView(View v) {
		if (_SelectedView != null) {
			_SelectedView.setTag(R.id.selected_view, null);
			_SelectedView = null;
		}
		if (v != null && v.getParent() != null) {
			_SelectedView = v;
			_SelectedView.setTag(R.id.selected_view, TAG + "SelectedView");
			invalidate();
		}
	}

	public void setSelectorBitmap(Bitmap b) {
		_SelectorDrawable = b;
		refreshDrawableState();
	}
	
	public void scrollBehindTo(View content, int x, int y) {
		int vis = View.VISIBLE;
		if (_Mode == SlidingMenu.LEFT) {
			if (x >= content.getLeft())
				vis = View.INVISIBLE;
			scrollTo((int) ((x + getBehindWidth()) * _ScrollScale), y);
		} else if (_Mode == SlidingMenu.RIGHT) {
			if (x <= content.getLeft())
				vis = View.INVISIBLE;
			scrollTo(
					(int) (getBehindWidth() - getWidth() + (x - getBehindWidth())
							* _ScrollScale), y);
		} else if (_Mode == SlidingMenu.LEFT_RIGHT) {
			_Content.setVisibility(x >= content.getLeft() ? View.INVISIBLE
					: View.VISIBLE);
			_SecondaryContent
					.setVisibility(x <= content.getLeft() ? View.INVISIBLE
							: View.VISIBLE);
			vis = x == 0 ? View.INVISIBLE : View.VISIBLE;
			if (x <= content.getLeft()) {
				scrollTo((int) ((x + getBehindWidth()) * _ScrollScale), y);
			} else {
				scrollTo(
						(int) (getBehindWidth() - getWidth() + (x - getBehindWidth())
								* _ScrollScale), y);
			}
		}
		if (vis == View.INVISIBLE)
			Log.v(TAG, "behind INVISIBLE");
		setVisibility(vis);
	}


}
