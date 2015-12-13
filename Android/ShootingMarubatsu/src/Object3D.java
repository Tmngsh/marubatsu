package com.tomproject.marubatsu;

import android.content.Context;

/**
 * Created by Eishi on 2015/12/09.
 */
public class Object3D {
    private float[] position;
    private float[] normal;
    private float[] texture;
    private short[] index;
    private String textureFileName;

    public Object3D(Context context, String objFileName, String texFileName){
        // 3Dデータを読み込む
        ReadObjData rod = new ReadObjData(context, objFileName);
        position = rod.getPosition();
        normal = rod.getNormal();
        texture = rod.getTexture();
        index = rod.getIndex();
        textureFileName = texFileName;
    }

    public float[] getPosition() {
        return position;
    }

    public float[] getNormal() {
        return normal;
    }

    public float[] getTexture() {
        return texture;
    }

    public short[] getIndex() {
        return index;
    }
}
