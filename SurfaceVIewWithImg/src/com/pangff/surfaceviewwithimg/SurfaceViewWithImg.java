package com.pangff.surfaceviewwithimg;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SurfaceViewWithImg extends SurfaceView implements SurfaceHolder.Callback {

  LooperThread _looperThread;
  int resId;
  public SurfaceViewWithImg(int resId,Context context) {
    super(context);
    getHolder().addCallback(this);
    this.resId = resId;
    _looperThread = new LooperThread(getHolder(), this);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    canvas.drawColor(Color.TRANSPARENT,Mode.CLEAR);
    Bitmap _scratch = BitmapFactory.decodeResource(getResources(),resId);
    //canvas.drawColor(Color.BLACK);
    canvas.drawBitmap(_scratch, 10, 10, null);
  }

  public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {}

  public void surfaceCreated(SurfaceHolder arg0) {
    _looperThread.start();
    new Handler().postDelayed(new Runnable() {

      @Override
      public void run() {
        _looperThread.mHandler.sendMessage(Message.obtain());
      }
    }, 500);
  }

  public void surfaceDestroyed(SurfaceHolder arg0) {

    _looperThread.mHandler.post(new Runnable() {
      @Override
      public void run() {
        Looper.myLooper().quit();
      }
    });

  }

  class LooperThread extends Thread {
    public Handler mHandler;
    private SurfaceHolder _surfaceHolder;
    private SurfaceViewWithImg _panel;
    Canvas c;

    public LooperThread(SurfaceHolder surfaceHolder, SurfaceViewWithImg panel) {
      _surfaceHolder = surfaceHolder;
      _panel = panel;
    }

    @Override
    public void run() {
      Looper.prepare();
      mHandler = new Handler() {
        @SuppressLint("WrongCall")
        @Override
        public void handleMessage(Message msg) {
          c = null;
          try {
            c = _surfaceHolder.lockCanvas(null);
            synchronized (_surfaceHolder) {
              _panel.onDraw(c);
            }
          } finally {
            if (c != null) {
              _surfaceHolder.unlockCanvasAndPost(c);
            }
          }
        }
      };
      Looper.loop();
    }
  }
}
