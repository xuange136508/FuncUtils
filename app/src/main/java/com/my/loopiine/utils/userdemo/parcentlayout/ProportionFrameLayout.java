package com.my.loopiine.utils.userdemo.parcentlayout;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.my.loopiine.R;

public class ProportionFrameLayout extends FrameLayout {

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

	public ProportionFrameLayout(Context context) {
		super(context);
	}

	public ProportionFrameLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ProportionFrameLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Proportion);
		widthProportion = typedArray.getFloat(R.styleable.Proportion_widthProportion, 0);
		heightProportion = typedArray.getFloat(R.styleable.Proportion_heightProportion, 0);
		int defIndex = ProportionType.values().length;
		int index = typedArray.getInt(R.styleable.Proportion_widthProportionType, defIndex);
		if (index != defIndex)
			widthProportionType = ProportionType.values()[index];
		index = typedArray.getInt(R.styleable.Proportion_heightProportionType, defIndex);
		;
		if (index != defIndex)
			heightProportionType = ProportionType.values()[index];
		typedArray.recycle();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
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
			border = (int) (getContext().getResources().getDisplayMetrics().widthPixels * proportion);
		} else if (ProportionType.screenHeight == proportionType) {
			border = (int) (getContext().getResources().getDisplayMetrics().heightPixels * proportion);
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
}
