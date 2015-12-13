package com.tomproject.marubatsu;

import android.opengl.GLES20;
import android.opengl.Matrix;

/**
 * Created by Eishi on 2015/12/06.
 */
public class Movement {
    protected float[] position;
    protected float[] scale;
    protected float rotateX;    // x軸回りの回転
    protected float rotateY;    // y軸回りの回転

    public Movement(){

        position = new float[3];
        scale = new float[3];

    }

    public void startedUpdate() {}
    public void waitedUpdate() {}
    public void touchedUpdate(float[] touchedPosition) {}
    public void movedUpdate() {}
    public boolean collidedUpdate() { return true;}
    public void finishedUpdate() {}

    // scale, rotate, positionの順にかけたMatrixを返す
    public float[] getModelMatrix() {
        float[] mMatrix = new float[16];    // 最後に返すMatrix
        Matrix.setIdentityM(mMatrix, 0);    // mMatrixの初期化

        float[] pM = new float[16];                                         // 移動用Matrix
        Matrix.setIdentityM(pM, 0);
        Matrix.translateM(pM, 0, position[0], position[1], position[2]);    // 移動
        float[] sM = new float[16];                                         // 拡大縮小用Matrix
        Matrix.setIdentityM(sM, 0);
        Matrix.scaleM(sM, 0, scale[0], scale[1], scale[2]);                 // 拡大縮小
        float[] rxM = new float[16];                                        // x軸回りの回転用Matrix
        Matrix.setIdentityM(rxM, 0);
        Matrix.rotateM(rxM, 0, rotateX, 1, 0, 0);                           // x軸回りの回転
        float[] ryM = new float[16];                                        // y軸回りの回転用Matrix
        Matrix.setIdentityM(ryM, 0);
        Matrix.rotateM(ryM, 0, rotateY, 0, 1, 0);                           // y軸回りの回転

        Matrix.multiplyMM(mMatrix, 0, rxM, 0, mMatrix, 0);
        Matrix.multiplyMM(mMatrix, 0, ryM, 0, mMatrix, 0);
        Matrix.multiplyMM(mMatrix, 0, sM, 0, mMatrix, 0);
        Matrix.multiplyMM(mMatrix, 0, pM, 0, mMatrix, 0);

        return mMatrix;
    }

    // positionを取得
    public float[] getPosition() { return position; }



}
