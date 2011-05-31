package com.uned.prf;

import java.io.IOException;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.util.AttributeSet;

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
	mHolder.addCallback(this);
	mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
}

public void surfaceCreated(SurfaceHolder holder) {
    // Hemos creado el Surface
	// Adquirimos la c‡mara y le decimos d—nde queremos que nos meta los datos (nena)
	mCamera = Camera.open();
    try {
       mCamera.setPreviewDisplay(holder);
    } catch (IOException exception) {
        mCamera.release();
        mCamera = null;
        // TODO: Aqu’ deber’a haber l—gica de la c‡mara
    }
}

public void surfaceDestroyed(SurfaceHolder holder) {
    // La superficie se va a destruir al volver, as’ que paramos la preview.
	// CameraDevice no es compartido, as’ que es importnate liberarlo al pausar la actividad.
    mCamera.stopPreview();
    mCamera = null;
}

public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
    Camera.Parameters parameters = mCamera.getParameters();
    parameters.setPreviewSize(w, h);
    mCamera.setParameters(parameters);
    mCamera.startPreview();
}

}