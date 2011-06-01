package com.uned.prf;

import java.util.List;
import android.view.View;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Color;
import android.util.AttributeSet;
import java.util.TreeSet;
import android.graphics.Rect;
import java.util.Iterator;
import java.lang.Math;
import java.util.ArrayList;

public class ViewFinderOverlay extends View{
	TreeSet<Float> mFocalLengths;
	Float mBaseLength;
	List<Rect> mRectangles;
	Context mContext;
	int mWidth = 0;
	int mHeight = 0;
	
	
	public ViewFinderOverlay(Context context) { 
         super(context); 
         this.init(context);
   } 
	
	public ViewFinderOverlay (Context context,AttributeSet attrs)
	{
		super(context,attrs);
		this.init(context);
	}
	
	private void init(Context pContext)
	{
		mContext=pContext;
		mFocalLengths = new TreeSet<Float>();
		mBaseLength = new Float(1.0); 
		mRectangles = new ArrayList<Rect>();
		updateDrawables();
	}
	
	public void setBaseLength(int pBaseLength)
	{
		mBaseLength = new Float(pBaseLength);
		updateDrawables();
	}
	
	public void addLength(int pLength)
	{
		if (pLength > mBaseLength.intValue())
		{
			mFocalLengths.add(new Float(pLength));
			updateDrawables();
		}
	}
	
	public void clearLengths()
	{
		mFocalLengths.clear();
		updateDrawables();
	}
	
	public void setScreenSize(int width, int height)
	{
		mWidth = width;
		mHeight = height;
	}
	
	private void updateDrawables()
	{
		int tW = mWidth;
		int tH = mHeight;
		double tRatio;
		Iterator<Float> tIterator = mFocalLengths.iterator();
		while (tIterator.hasNext())
		{
			Float tCurrentLength = tIterator.next();
			tRatio = tCurrentLength / mBaseLength;
			tRatio = Math.sqrt(tRatio);
			int measurementsX = (int)Math.round(tW / tRatio);
			int measurementsY = (int)Math.round(tH / tRatio);
			Rect tRect = new Rect((int)(tW - measurementsX) / 2,
									(int)(tH - measurementsY) / 2,
									(int)(tW + measurementsX) / 2,
									(int)(tH + measurementsY) / 2);
			mRectangles.add(tRect);
		}
		
	}
	
   @Override 
   protected void onDraw(Canvas canvas) { 
           // TODO Auto-generated method stub 
           super.onDraw(canvas); 
           Paint paint = new Paint(); 
           paint.setStrokeWidth(3);
           paint.setStyle(Paint.Style.STROKE); 
           paint.setARGB(255, 255, 0, 0);
           Iterator<Rect> tIterator = mRectangles.iterator();
           while (tIterator.hasNext())
           {
        	   Rect tRect = tIterator.next();
        	   canvas.drawRect(tRect,paint);
           }
   } 
   
}
