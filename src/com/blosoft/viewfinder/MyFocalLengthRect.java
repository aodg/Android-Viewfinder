package com.blosoft.viewfinder;

import android.graphics.Rect;
import java.lang.Comparable;

public class MyFocalLengthRect implements Comparable<MyFocalLengthRect>{
	private Rect mRect;
	private Integer mLength;
	private boolean mChosenOne;
	
	public MyFocalLengthRect(Rect rect, Integer length)
	{
		this.mRect = rect;
		this.mLength = length;
		this.mChosenOne = false;
	}
	
	public Rect getRect()
	{
		return mRect;
	}
	
	public int getLength()
	{
		return mLength.intValue();
	}
	
	public String getLengthString()
	{
		return new String(mLength + " mm");	
	}
	
	public int compareTo(MyFocalLengthRect pComparer)
	{
		return this.mLength.compareTo(pComparer.mLength);
	}
	
	public boolean equals(MyFocalLengthRect pComparer)
	{
		return (pComparer.getLength() == this.getLength());
 	}
	public void setChosen(boolean pChoice)
	{
		this.mChosenOne = pChoice;
	}
	
	public boolean isChosen()
	{
		return mChosenOne;
	}
}
