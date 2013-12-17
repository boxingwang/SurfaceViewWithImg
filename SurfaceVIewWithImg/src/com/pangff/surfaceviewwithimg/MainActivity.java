package com.pangff.surfaceviewwithimg;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    SurfaceViewWithImg mySurfaceView = new SurfaceViewWithImg(getApplicationContext());
    setContentView(mySurfaceView);
  }



}
