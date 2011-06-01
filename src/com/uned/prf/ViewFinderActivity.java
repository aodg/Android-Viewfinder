package com.uned.prf;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import java.util.TreeSet;


public class ViewFinderActivity extends Activity implements OnSharedPreferenceChangeListener{
    /** Called when the activity is first created. */
    CameraPreview mCameraPreview;
    ViewFinderOverlay mViewFinderOverlay;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     	// Hide the window title.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main);
        mCameraPreview = (CameraPreview)this.findViewById(R.id.cameraPreview);
        mViewFinderOverlay = (ViewFinderOverlay)this.findViewById(R.id.cameraOverlay);
    }
    
	public void onPause()
	{
		super.onPause();
	}
	
	public void onResume()
	{
		super.onResume();
		this.updateOverlayData();
	}
	
	private void updateOverlayData()
	{
		// Send base length and List / Set of lengths to overlay
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		int base,len1,len2,len3;
		String value;
		value = prefs.getString(getString(R.string.key_base_length), null);
		base = (value == null ? 35 : Integer.valueOf(value));
		value = prefs.getString(getString(R.string.key_lengths_one), null);
		len1 = (value == null ? 50 : Integer.valueOf(value));
		value = prefs.getString(getString(R.string.key_lengths_two), null);
		len2 = (value == null ? 90 : Integer.valueOf(value));
		value = prefs.getString(getString(R.string.key_lengths_three), null);
		len3 = (value == null ? 150 : Integer.valueOf(value));
		
		mViewFinderOverlay.setBaseLength(base);
		mViewFinderOverlay.clearLengths();
		mViewFinderOverlay.addLength(len1);
		mViewFinderOverlay.addLength(len2);
		mViewFinderOverlay.addLength(len3);
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
    
    public void onSharedPreferenceChanged ( SharedPreferences pSharedPreferences, String key)
    {
    	// Take care of the changed preferences;
    	this.updateOverlayData();
    }
}