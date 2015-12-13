package com.tomproject.marubatsu;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by Eishi on 2015/12/06.
 */
public class DrawObject {
    private int program;

    public DrawObject(Context context) {
        String vertexCode = ReadShader.read(context, "shader/vertexShader2");
        String fragmentCode = ReadShader.read(context, "shader/fragmentShader2");

        program = MyGLProgram.getProgram(vertexCode, fragmentCode);
    }

    public void draw(Object3D obj, float[] mMatrix, int mark) {
        // 行列を設定
        float[] vpMatrix = new float[16];
        float[] m = new float[16];
        Matrix.setIdentityM(vpMatrix, 0);
        Matrix.setLookAtM(m, 0, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 20.0f, 0.0f, 1.0f, 0.0f);  // 視点変換
        Matrix.multiplyMM(vpMatrix, 0, m, 0, vpMatrix, 0);
        Matrix.frustumM(m, 0, -0.5f, 0.5f, -1.0f, 1.0f, 1.0f, 100f);        // 透視変換
        Matrix.multiplyMM(vpMatrix, 0, m, 0, vpMatrix, 0);

        float[] mvpMatrix = new float[16];
        Matrix.multiplyMM(mvpMatrix, 0, vpMatrix, 0, mMatrix, 0);   // モデル変換行列とビュー変換を掛ける

        // programを有効にする
        GLES20.glUseProgram(program);
        int positionAttrib = GLES20.glGetAttribLocation(program, "vPosition");
        int normalAttrib = GLES20.glGetAttribLocation(program, "vNormal");

        int colorHandle = GLES20.glGetUniformLocation(program, "color");
        int matrixHandle = GLES20.glGetUniformLocation(program, "mvpMatrix");

        // colorをセット
        switch (mark) {
            case GameParam.NONE:
            GLES20.glUniform3f(colorHandle, 0.2f, 0.2f, 0.2f);
                break;
            case GameParam.MARU:
            GLES20.glUniform3f(colorHandle, 0.0f, 0.0f, 1.0f);
                break;
            case GameParam.BATSU:
                GLES20.glUniform3f(colorHandle, 1.0f, 0.0f, 0.0f);
                break;
            default:
                GLES20.glUniform3f(colorHandle, 1.0f, 1.0f, 1.0f);
        }

        // mvpMatrixをセット
        GLES20.glUniformMatrix4fv(matrixHandle, 1, false, mvpMatrix, 0);

        // PositionBufferの準備
        FloatBuffer positionBuffer = CreateBuffer.createFloatBuffer(obj.getPosition());
        GLES20.glEnableVertexAttribArray(positionAttrib);
        GLES20.glVertexAttribPointer(positionAttrib, 3, GLES20.GL_FLOAT, false, 0, positionBuffer);


        // NormalBufferの準備
        FloatBuffer normalBuffer = CreateBuffer.createFloatBuffer(obj.getNormal());
        GLES20.glEnableVertexAttribArray(normalAttrib);
        GLES20.glVertexAttribPointer(normalAttrib, 3, GLES20.GL_FLOAT, false, 0, normalBuffer);

        // IndexBufferの準備
        ShortBuffer indexBuffer = CreateBuffer.createShortBuffer(obj.getIndex());

        // 描画
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, obj.getIndex().length, GLES20.GL_UNSIGNED_SHORT, indexBuffer);

        GLES20.glDisableVertexAttribArray(positionAttrib);
        GLES20.glDisableVertexAttribArray(normalAttrib);
    }

}
