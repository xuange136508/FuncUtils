package com.my.loopiine.utils.userdemo.parcentlayout;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.RelativeLayout;

import com.my.loopiine.R;

public class ProportionRelativeLayout extends RelativeLayout {

	/**
	 * 濮ｆ柧绶ョ猾璇茬��
	 * */
	public enum ProportionType {
		/**
		 * 鐏炲繐绠风�硅棄瀹�
		 * */
		screenWidth,
		/**
		 * 鐏炲繐绠锋妯哄
		 * */
		screenHeight,
		/**
		 * 閸欙缚绔寸猾鏄忕珶
		 * */
		anotherBorder
	}

	public enum ScreenOrientation {
		/**
		 * 缁鹃潧鎮�
		 * */
		portrait,
		/**
		 * 濡亜鎮�
		 * */
		landscape,
		/**
		 * 閺嶈鎽庨悧鈺冩倞閺傜懓鎮滈崒铏妳閸ｃ劎鈷戠�规碍鏌熼崥锟�. 閸欐牗鐒欓弬鑲╂暏閹磋埖澧滈幐浣烘畱閺傜懓鎮�, 閻ｅ墎鏁ら幋鎯扮秮閸曟洝艒閸岋拷, 鐎瑰啳绐￠梾銊︽暭鐠侊拷
		 * */
		sensor
	}

	/**
	 * 鐎硅棄瀹冲В鏂剧伐閸婏拷
	 * */
	private float widthProportion;
	/**
	 * 妤傛ê瀹冲В鏂剧伐閸婏拷
	 * */
	private float heightProportion;
	/**
	 * 鐎硅棄瀹冲В鏂剧伐缁鐎�
	 * */
	private ProportionType widthProportionType;
	/**
	 * 妤傛ê瀹冲В鏂剧伐缁鐎�
	 * */
	private ProportionType heightProportionType;
	/**
	 * 鐏炲繐绠烽崣鏍ф倻
	 * */
	private ScreenOrientation screenOrientation;
	/**
	 * 鐏炲繐绠风�硅棄瀹�
	 * */
	private int screenWidth;
	/**
	 * 鐏炲繐绠锋妯哄
	 * */
	private int screenHeight;
	
	public ProportionRelativeLayout(Context context) {
		super(context);
	}

	public ProportionRelativeLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ProportionRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Proportion);
		widthProportion = typedArray.getFloat(R.styleable.Proportion_widthProportion, 0);
		heightProportion = typedArray.getFloat(R.styleable.Proportion_heightProportion, 0);
		int defIndex = ProportionType.values().length;
		int index = typedArray.getInt(R.styleable.Proportion_widthProportionType, defIndex);
		if (index != defIndex)
			widthProportionType = ProportionType.values()[index];
		index = typedArray.getInt(R.styleable.Proportion_heightProportionType, defIndex);
		if (index != defIndex)
			heightProportionType = ProportionType.values()[index];
		screenOrientation = ScreenOrientation.values()[typedArray.getInt(R.styleable.Proportion_screenOrientation, 2)];
		typedArray.recycle();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
		if (ScreenOrientation.portrait == screenOrientation) {
			screenWidth = Math.min(displayMetrics.widthPixels, displayMetrics.heightPixels);
			screenHeight = Math.max(displayMetrics.widthPixels, displayMetrics.heightPixels);
		} else if (ScreenOrientation.landscape == screenOrientation) {
			screenWidth = Math.max(displayMetrics.widthPixels, displayMetrics.heightPixels);
			screenHeight = Math.min(displayMetrics.widthPixels, displayMetrics.heightPixels);
		} else {
			screenWidth = displayMetrics.widthPixels;
			screenHeight = displayMetrics.heightPixels;
		}

		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		if (ProportionType.anotherBorder == widthProportionType) {
			if (heightProportionType != null) {
				heightSize = getBorder(heightProportionType, heightSize, widthSize, heightProportion);
				heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY);
			}
			if (widthProportionType != null) {
				widthSize = getBorder(widthProportionType, widthSize, heightSize, widthProportion);
				widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY);
			}
		}
		if (ProportionType.anotherBorder == heightProportionType || (widthProportionType != ProportionType.anotherBorder && heightProportionType != ProportionType.anotherBorder)) {
			if (widthProportionType != null) {
				widthSize = getBorder(widthProportionType, widthSize, heightSize, widthProportion);
				widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY);
			}
			if (heightProportionType != null) {
				heightSize = getBorder(heightProportionType, heightSize, widthSize, heightProportion);
				heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY);
			}
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	/**
	 * 閺傝纭剁拠瀛樻閿涳拷
	 * 
	 * @param border
	 *            鏉堝湱娈戦梹鍨
	 * */
	private int getBorder(ProportionType proportionType, int border, int anotherBorder, float proportion) {
		if (ProportionType.screenWidth == proportionType) {
			border = (int) (screenWidth * proportion);
		} else if (ProportionType.screenHeight == proportionType) {
			border = (int) (screenHeight * proportion);
		} else {
			border = (int) (anotherBorder * proportion);
		}
		return border;
	}

	public float getWidthProportion() {
		return widthProportion;
	}

	public void setWidthProportion(float widthProportion) {
		this.widthProportion = widthProportion;
	}

	public float getHeightProportion() {
		return heightProportion;
	}

	public void setHeightProportion(float heightProportion) {
		this.heightProportion = heightProportion;
	}

	public ProportionType getWidthProportionType() {
		return widthProportionType;
	}

	public void setWidthProportionType(ProportionType widthProportionType) {
		this.widthProportionType = widthProportionType;
	}

	public ProportionType getHeightProportionType() {
		return heightProportionType;
	}

	public void setHeightProportionType(ProportionType heightProportionType) {
		this.heightProportionType = heightProportionType;
	}
	
	public void setScreenOrientation(ScreenOrientation screenOrientation) {
		this.screenOrientation = screenOrientation;
	}
	
	public ScreenOrientation getScreenOrientation() {
		return screenOrientation;
	}
}