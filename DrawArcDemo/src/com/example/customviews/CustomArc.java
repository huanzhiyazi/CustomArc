package com.example.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import com.example.drawarcdemo.R;

public class CustomArc extends View {
	
	//sizes
	private int mRectWidth;
	private int mArcWidth;
	
	//colors
	private int mRectColor;
	private int mArcColor;
	
	//paints
	private Paint mRectPaint = new Paint();
	private Paint mArcPaint = new Paint();
	
	//rects
	private RectF mRectBounds = new RectF();
	private RectF mArcBounds = new RectF();

	public CustomArc(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		setDefaultAttr(context);
		parseAttributes(context.obtainStyledAttributes(attrs, R.styleable.CustomArc));
	}
	
	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();
		setupBounds();
		setuPaints();
		invalidate();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		canvas.drawArc(mArcBounds, -90, 360, false, mArcPaint);
		canvas.drawRect(mArcBounds, mRectPaint);
		canvas.drawRect(mRectBounds, mRectPaint);
	}

	private void setDefaultAttr(Context context) {
		mRectWidth = context.getResources().getDimensionPixelSize(R.dimen.default_rect_width);
		mArcWidth = context.getResources().getDimensionPixelSize(R.dimen.default_arc_width);
		mRectColor = context.getResources().getColor(R.color.default_rect_color);
		mArcColor = context.getResources().getColor(R.color.default_arc_color);
	}
	
	private void parseAttributes(TypedArray a) {
		mRectWidth = (int)a.getDimension(R.styleable.CustomArc_rectWidth, 
				mRectWidth);
		mArcWidth = (int)a.getDimension(R.styleable.CustomArc_arcWidth, 
				mArcWidth);
		mRectColor = a.getColor(R.styleable.CustomArc_rectColor, mRectColor);
		mArcColor = a.getColor(R.styleable.CustomArc_arcColor, mArcColor);
	}
	
	private void setupBounds() {
		mRectBounds = new RectF(0, 0, 
				getLayoutParams().width, 
				getLayoutParams().height);
		
		mArcBounds = new RectF((mArcWidth+1)/2, (mArcWidth+1)/2, 
				getLayoutParams().width - (mArcWidth+1)/2, 
				getLayoutParams().height - (mArcWidth+1)/2);
	}
	
	private void setuPaints() {
		mRectPaint.setColor(mRectColor);
		mRectPaint.setAntiAlias(true);
		mRectPaint.setStyle(Style.STROKE);
		mRectPaint.setStrokeWidth(mRectWidth);
		
		mArcPaint.setColor(mArcColor);
		mArcPaint.setAntiAlias(true);
		mArcPaint.setStyle(Style.STROKE);
		mArcPaint.setStrokeWidth(mArcWidth);
	}
}
