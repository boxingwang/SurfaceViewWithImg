package com.pangff.surfaceviewwithimg;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.widget.FrameLayout;

public class MainActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    FrameLayout frame = new FrameLayout(this);
    SurfaceViewWithImg bottomSurface = new SurfaceViewWithImg(R.drawable.sina,getApplicationContext());
    SurfaceViewWithImg topSurface = new SurfaceViewWithImg(R.drawable.top,getApplicationContext());
    frame.addView(bottomSurface);
    frame.addView(topSurface);
    
    topSurface.setZOrderOnTop(true);      // 这句不能少
    topSurface.getHolder().setFormat(PixelFormat.TRANSLUCENT);
    
    setContentView(frame);
  }

}
