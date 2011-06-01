package com.uned.prf;

import java.io.IOException;
import java.util.List;
 
import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.util.AttributeSet;
import android.graphics.PixelFormat;
import android.hardware.Camera.Size;

import android.os.Build;

// Formas de mejorar esta clase:
// http://andar.googlecode.com/svn-history/r140/trunk/AndAR/src/edu/dhbw/andar/CameraParameters.java
// http://developer.android.com/resources/samples/ApiDemos/src/com/example/android/apis/graphics/CameraPreview.html
// Mirar el c—digo

class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private final String TAG = "cameraPreview";
	
	SurfaceHolder mHolder;
    Camera mCamera;
    Context mContext;
    Size mPreviewSize;
    List<Size> mSupportedPreviewSizes;
    
public CameraPreview(Context context) {
    super(context);
    this.mHolderInit();
}

public CameraPreview(Context context, AttributeSet attrs)
{
	super(context,attrs);
	this.mHolderInit();
}

private void mHolderInit()
{	
	// Nos registramos como callback para el surfaceHolder
	mHolder = getHolder();
	mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	mHolder.addCallback(this);
}

public void surfaceCreated(SurfaceHolder holder) {
    // Hemos creado el Surface
	// Adquirimos la c‡mara y le decimos d—nde queremos que nos meta los datos (nena)
	// En lugar de esto deber’amos usar un factory como este:
	// https://github.com/commonsguy/cw-advandroid/tree/master/Camera/Picture/src/com/commonsware/android/camera
	mCamera = Camera.open();
	
    try {
       mCamera.setPreviewDisplay(mHolder);
    } catch (Throwable e) {
        mCamera.release();
        mCamera = null;
        // TODO aqu’ deber’a haber un Dialog que informe a la gente de que algo ha petado y mate la app
    }
}

public void surfaceDestroyed(SurfaceHolder holder) {
    // La superficie se va a destruir al volver, as’ que paramos la preview.
	// CameraDevice no es compartido, as’ que es importante liberarlo al pausar la actividad.
    mCamera.stopPreview();
    mCamera.release();
}

public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
    Camera.Parameters parameters = mCamera.getParameters();
    List<Size> availableSizes = parameters.getSupportedPreviewSizes();
    Size destSize = getOptimalPreviewSize(availableSizes,w,h);
    if (destSize != null)
    {
    	parameters.setPreviewSize(destSize.width,destSize.height);
    }
    else
    {
    	parameters.setPreviewSize(w,h);
    }
    parameters.setPictureFormat(PixelFormat.JPEG);
    mCamera.setParameters(parameters);
    requestLayout();
    mCamera.startPreview();
}

private Size getOptimalPreviewSize(List<Size> sizes, int w, int h) {
    final double ASPECT_TOLERANCE = 0.1;
    double targetRatio = (double) w / h;
    if (sizes == null) return null;

    Size optimalSize = null;
    double minDiff = Double.MAX_VALUE;

    int targetHeight = h;

    // Try to find an size match aspect ratio and size
    for (Size size : sizes) {
        double ratio = (double) size.width / size.height;
        if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
        if (Math.abs(size.height - targetHeight) < minDiff) {
            optimalSize = size;
            minDiff = Math.abs(size.height - targetHeight);
        }
    }

    // Cannot find the one match the aspect ratio, ignore the requirement
    if (optimalSize == null) {
        minDiff = Double.MAX_VALUE;
        for (Size size : sizes) {
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }
    }
    return optimalSize;
}

}