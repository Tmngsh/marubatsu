package com.tomproject.marubatsu;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Eishi on 2015/11/22.
 */
public class MyRenderer implements Renderer{
    GameManager gameManager;
    Context context;
    private int width;
    private int height;


    MyRenderer(Context c) {
        this.context = c;
    }

    @Override
    public void onDrawFrame(GL10 unused) {
        // 背景色
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);

        // Gameのupdate
        gameManager.update();


    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gameManager = new GameManager(context);

    }

    public void setTouchedStatePosition(boolean state, float[] position){
        gameManager.setTouchedStatePosition(state, position);
    }

}
