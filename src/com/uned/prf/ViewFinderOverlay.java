package com.uned.prf;

import android.view.View;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Color;
import android.util.AttributeSet;

public class ViewFinderOverlay extends View {
	public ViewFinderOverlay(Context context) { 
         super(context); 
           // TODO Auto-generated constructor stub 
   } 
	
	public ViewFinderOverlay (Context context,AttributeSet attrs)
	{
		super(context,attrs);
	}
   @Override 
   protected void onDraw(Canvas canvas) { 
           // TODO Auto-generated method stub 
           Paint paint = new Paint(); 
           paint.setStyle(Paint.Style.FILL); 
           paint.setColor(Color.BLACK); 
           canvas.drawText("Test Text", 10, 10, paint); 
           super.onDraw(canvas); 
   } 
   
}
