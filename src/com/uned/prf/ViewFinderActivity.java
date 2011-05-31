package com.uned.prf;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class ViewFinderActivity extends Activity {
    /** Called when the activity is first created. */
    CameraPreview mCameraPreview;
    ViewFinderOverlay mViewFinderOverlay;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mCameraPreview = (CameraPreview)this.findViewById(R.id.cameraPreview);
        mViewFinderOverlay = (ViewFinderOverlay)this.findViewById(R.id.cameraOverlay);
    }
    
	public void onPause()
	{
		super.onPause();
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.menuPrefsButton:
            return showPrefs();
        case R.id.menuAboutButton:
            return showAbout();
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    private boolean showAbout()
    {
    	Intent i = new Intent(this, AboutActivity.class);   
    	startActivity(i);
    	return true;
    }
    
    private boolean showPrefs()
    {
    	Intent i = new Intent(this, SettingsActivity.class);
    	startActivity(i);
    	return true;
    }
}