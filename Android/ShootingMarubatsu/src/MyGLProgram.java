package com.tomproject.marubatsu;

import android.opengl.GLES20;
import android.util.Log;

/**
 * Created by Eishi on 2015/11/22.
 */
public class MyGLProgram {
    public static int getProgram(String vsSrc, String fsSrc){
        // vertexShaderのコンパイル
        int vs = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
        GLES20.glShaderSource(vs, vsSrc);
        GLES20.glCompileShader(vs);

        // コンパイルチェック
        final int[] vsCompileStatus = new int[1];
        GLES20.glGetShaderiv(vs, GLES20.GL_COMPILE_STATUS, vsCompileStatus, 0);

        if (vsCompileStatus[0] ==0){
            // コンパイル失敗
            GLES20.glDeleteShader(vs);
            vs = 0;
            // ログにエラーを出力
            Log.d("debug", "VERTEXSHADER_COMPILE_ERROR");
        }

        // fragmentShaderのコンパイル
        int fs = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);
        GLES20.glShaderSource(fs, fsSrc);
        GLES20.glCompileShader(fs);


        // コンパイルチェック
        final int[] fsCompileStatus = new int[1];
        GLES20.glGetShaderiv(fs, GLES20.GL_COMPILE_STATUS, fsCompileStatus, 0);

        if (fsCompileStatus[0] ==0){
            // コンパイル失敗
            GLES20.glDeleteShader(fs);
            fs = 0;
            Log.d("debug", "FRAGMENTSHADER_COMPILE_ERROR");
        }

        // shaderをリンクしてProgramを作る
        int shaderProgram;
        shaderProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(shaderProgram, vs);
        GLES20.glAttachShader(shaderProgram, fs);
        GLES20.glLinkProgram(shaderProgram);

        // リンク結果をチェック
        final int[] linkStatus = new int[1];
        GLES20.glGetProgramiv(shaderProgram, GLES20.GL_LINK_STATUS, linkStatus, 0);
        if(linkStatus[0] == 0) {
            // リンク失敗
            GLES20.glDeleteProgram(shaderProgram);
            shaderProgram = 0;
            Log.d("debug", "LINK_ERROR");
        }
        return shaderProgram;
    }
}
