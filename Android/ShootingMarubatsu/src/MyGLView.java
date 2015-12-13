package com.tomproject.marubatsu;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.widget.Toast;

/**
 * Created by Eishi on 2015/11/22.
 */
public class MyGLView extends GLSurfaceView {

    MyRenderer myRenderer;
    //Context context;

    public MyGLView(Context context){
        super(context);
        this.setEGLContextClientVersion(2);
        myRenderer = new MyRenderer(context);
        this.setRenderer(myRenderer);

    }

    public void setTouchedStatePosition(boolean state, float[] position) {
        myRenderer.setTouchedStatePosition(state, position);
    }


}
