package com.uned.prf;

import android.graphics.PorterDuff;
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
	MyFocalLengthRect mBaseLength;
	TreeSet<MyFocalLengthRect> mRectangles;
	Context mContext;
	int mWidth = 0;
	int mHeight = 0;
	double mRatio;
	boolean mVerticalIsReference = false;
	
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
		mRectangles = new TreeSet<MyFocalLengthRect>();
	}
	
	public void setBaseLength(int pBaseLength)
	{
		double a = mWidth;
		double b = mHeight;
		int baseWidth = 0;
		int baseHeight = 0;
		double tRatio = b/a;
		// Este if est‡ mal. Queremos saber en quŽ direcci—n tenemos que mirar. 
		if (tRatio > mRatio) 
		{
			mVerticalIsReference = false;
			baseWidth = mWidth;
			baseHeight = (int)	(baseWidth * mRatio);
		}
		else
		{
			mVerticalIsReference = true;
			baseHeight = mHeight;
			baseWidth = (int) (baseHeight * 1/mRatio);
		}
		Rect tRect = new Rect((int)(mWidth - baseWidth) / 2,
									(int)(mHeight - baseHeight) / 2,
									(int)(mWidth + baseWidth) / 2,
									(int)(mHeight + baseHeight) / 2);
		mBaseLength = new MyFocalLengthRect(tRect, pBaseLength);
		// We should update the focal length data. For now, clear it
		mRectangles.clear();
	}
	
	public void addLength(int pLength)
	{
		if (pLength > mBaseLength.getLength())
		{
			int tWidth;
			int tHeight;
			float tRatio = (float)mBaseLength.getLength() / (float)pLength ;
			Rect tRect = mBaseLength.getRect();
			tHeight = (int)(tRect.height() * Math.sqrt(tRatio));
			tWidth = (int)(tRect.width() * Math.sqrt(tRatio));
			
			Rect tRect2 = new Rect((int)(mWidth - tWidth) / 2,
									(int)(mHeight - tHeight) / 2,
									(int)(mWidth + tWidth) / 2,
									(int)(mHeight + tHeight) / 2);
			mRectangles.add(new MyFocalLengthRect(tRect2,pLength));
			}
	}
	
	public void clearLengths()
	{
		mRectangles.clear();
	}
	
	public void setScreenSize(int width, int height)
	{
		mWidth = width;
		mHeight = height;
	}
	
	public void setDesiredRatio(double ratio)
	{
		if (ratio > 1.0)
		{
			mRatio = 1.0/ratio;
		}
		else
		{
			if ( ratio == 0.0)
			{
				mRatio = (double)mHeight / (double)mWidth;
			}
			else
			{
				mRatio = ratio;
			}
		}
	}
	
   @Override 
   protected void onDraw(Canvas canvas) { 
           // TODO Auto-generated method stub 
	   		super.onDraw(canvas); 
	        
	   		//canvas.drawColor(0, PorterDuff.Mode.CLEAR);
           Paint paint = new Paint(); 
           paint.setStrokeWidth(3);
            
           paint.setARGB(255, 255, 0, 0);
           paint.setTextSize(12 * mContext.getResources().getDisplayMetrics().density + 0.5f);
           paint.setStyle(Paint.Style.STROKE);
           canvas.drawRect(mBaseLength.getRect(),paint);
           paint.setStyle(Paint.Style.FILL);
           canvas.drawText(mBaseLength.getLengthString(), mBaseLength.getRect().left+5, mBaseLength.getRect().bottom-5, paint);
           
           Iterator<MyFocalLengthRect> tIterator = mRectangles.iterator();
           while (tIterator.hasNext())
           {
        	   MyFocalLengthRect tRect = tIterator.next();
        	   paint.setStyle(Paint.Style.STROKE);
        	   canvas.drawRect(tRect.getRect(),paint);
        	   paint.setStyle(Paint.Style.FILL);
        	   canvas.drawText(tRect.getLengthString(), tRect.getRect().left+5, tRect.getRect().bottom-5, paint);    
           }
   } 
 
}
