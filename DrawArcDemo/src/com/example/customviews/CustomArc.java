package com.example.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import com.example.drawarcdemo.R;

public class CustomArc extends View {
	
	//sizes
	private int mRectWidth;
	private int mArcWidth;
	
	//colors
	private int mRectColor;
	private int mArcColor;
	private int mRimColor;
	
	//paints
	private Paint mRectPaint = new Paint();
	private Paint mArcPaint = new Paint();
	private Paint mRimPaint = new Paint();
	private Paint mProgressPaint = new Paint();
    private Paint mPercentPaint = new Paint();
	
	//rects
	private RectF mRectBounds = new RectF();
	private RectF mArcBounds = new RectF();
	private Rect mProgBounds = new Rect();
    private Rect mPercentBounds = new Rect();
    
    int mDegree = 0;
    int mProgress = 0;
    private String mProgressText = "0";

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
		
		//2 ways to draw the rim
		/*
		canvas.drawCircle(this.getWidth() / 2, 
                this.getHeight() / 2, 
                (mRectBounds.width() - mArcWidth)/2, 
                mRimPaint);
		*/
		canvas.drawArc(mArcBounds, 360, 360, false, mRimPaint);
		
		canvas.drawArc(mArcBounds, -90, mDegree, false, mArcPaint);
		
		//canvas.drawRect(mArcBounds, mRectPaint);
		//canvas.drawRect(mRectBounds, mRectPaint);
		
		mProgressPaint.getTextBounds(mProgressText, 0, mProgressText.length(), mProgBounds);
        mPercentPaint.getTextBounds("%", 0, 1, mPercentBounds);
        float offset = 
                (mProgBounds.width() + mPercentBounds.width() + mPercentBounds.width() / 2) / 2;
        
        Log.d("caohzh", "!!!! 90's w: " + mProgBounds.width());
        Log.d("caohzh", "!!!! 90's h: " + mProgBounds.height());
        Log.d("caohzh", "!!!! %'s w: " + mPercentBounds.width());
        Log.d("caohzh", "!!!! %'s h: " + mPercentBounds.height());
        
        Log.d("caohzh", "!!!! offset: " + offset);
        Log.d("caohzh", "!!!! this w: " + this.getWidth());
        Log.d("caohzh", "!!!! this h: " + this.getHeight());
        
        Log.d("caohzh", "!!!! 90's x: " + (this.getWidth() / 2 - offset));
        Log.d("caohzh", "!!!! 90's y: " + (this.getHeight() / 2 + mProgBounds.height() / 2));
        Log.d("caohzh", "!!!! %'s x: " + (this.getWidth() / 2 + offset - mPercentBounds.width()));
        Log.d("caohzh", "!!!! %'s y: " + (this.getHeight() / 2 - mProgBounds.height() / 2 + mPercentBounds.height()));
        
        canvas.drawText(mProgressText, 
                this.getWidth() / 2 - offset, 
                this.getHeight() / 2 + mProgBounds.height() / 2, 
                mProgressPaint);
        
        canvas.drawText("%", 
                this.getWidth() / 2 + offset - mPercentBounds.width(),
                this.getHeight() / 2 - mProgBounds.height() / 2 + mPercentBounds.height(), 
                mPercentPaint);
	}

	private void setDefaultAttr(Context context) {
		mRectWidth = context.getResources().getDimensionPixelSize(R.dimen.default_rect_width);
		mArcWidth = context.getResources().getDimensionPixelSize(R.dimen.default_arc_width);
		mRectColor = context.getResources().getColor(R.color.default_rect_color);
		mArcColor = context.getResources().getColor(R.color.default_arc_color);
		mRimColor = context.getResources().getColor(R.color.default_rim_color);
	}
	
	private void parseAttributes(TypedArray a) {
		mRectWidth = (int)a.getDimension(R.styleable.CustomArc_rectWidth, 
				mRectWidth);
		mArcWidth = (int)a.getDimension(R.styleable.CustomArc_arcWidth, 
				mArcWidth);
		mRectColor = a.getColor(R.styleable.CustomArc_rectColor, mRectColor);
		mArcColor = a.getColor(R.styleable.CustomArc_arcColor, mArcColor);
		mRimColor = a.getColor(R.styleable.CustomArc_rimColor, mRimColor);
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
		
		mRimPaint.setColor(mRimColor);
        mRimPaint.setAntiAlias(true);
        mRimPaint.setStyle(Style.STROKE);
        mRimPaint.setStrokeWidth(mArcWidth);
		
		mProgressPaint.setColor(Color.BLACK);
        mProgressPaint.setStyle(Style.FILL);
        mProgressPaint.setAntiAlias(true);
        mProgressPaint.setTextSize(120);
        mProgressPaint.setTypeface(Typeface.DEFAULT_BOLD);
        
        mPercentPaint.setColor(Color.BLACK);
        mPercentPaint.setStyle(Style.FILL);
        mPercentPaint.setAntiAlias(true);
        mPercentPaint.setTextSize((int)(120 * 0.7));
        mPercentPaint.setTypeface(Typeface.DEFAULT_BOLD);
	}
	
	public void setProgress(int i) {
        mProgress = i;
        mDegree = Math.round( ((float)mProgress / 100) * 360 );
        mProgressText = String.valueOf(mProgress);
        this.post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				invalidate();
			}
        	
        });
    }
	
	public void resetCount() {
        mDegree = 0;
        mProgress = 0;
        mProgressText = "0";
        this.post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				invalidate();
			}
        	
        });
    }
}
