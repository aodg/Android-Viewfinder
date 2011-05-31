package com.uned.prf;

import java.io.IOException;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.util.AttributeSet;
import android.graphics.PixelFormat;

class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    SurfaceHolder mHolder;
    Camera mCamera;

public CameraPreview(Context context) {
    super(context);
    this.mHolderInit();
}

public CameraPreview(Context context, AttributeSet attrs)
{
	super(context,attrs);
	this.mHolderInit();
}

public void mHolderInit()
{	
	// Nos registramos como callback para el surfaceHolder
	mHolder = getHolder();
	mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	mHolder.addCallback(this);
}

public void surfaceCreated(SurfaceHolder holder) {
    // Hemos creado el Surface
	// Adquirimos la c‡mara y le decimos d—nde queremos que nos meta los datos (nena)
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
    parameters.setPreviewSize(w, h);
    parameters.setPictureFormat(PixelFormat.JPEG);
    mCamera.setParameters(parameters);
    mCamera.startPreview();
}

}