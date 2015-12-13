package com.tomproject.marubatsu;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Eishi on 2015/12/09.
 */
public class ReadObjData {
    private float[] position;
    private float[] normal;
    private float[] texture;
    private short[] index;

    public ReadObjData(Context context, String fileName) {
        InputStream is = null;
        BufferedReader br = null;

        try {
            try {

                is = context.getAssets().open(fileName);
                br = new BufferedReader(new InputStreamReader(is));

                // 1行ずつファイルを読み込んでいく
                String line;

                // 頂点座標の長さ
                int vLen;
                // インデックスの長さ
                int iLen;
                // 現在の配列の位置
                int nowP = 0, nowN = 0, nowT = 0;
                int nowI = 0;

                while ((line = br.readLine()) != null) {
                    // 読み込んだ行を空白で区切る
                    String[] strings = line.split(" ");

                    // 行の最初の文字列で処理を分岐する
                    switch (strings[0]) {
                        case "vLen":
                            vLen = Integer.valueOf(strings[1]);
                            // 頂点座標の長さの配列を作る
                            position = new float[vLen];
                            normal = new float[vLen];
                            texture = new float[vLen];
                            break;

                        case "iLen":
                            iLen = Integer.valueOf(strings[1]);
                            // インデックスの長さの配列を作る
                            index = new short[iLen];
                            break;

                        case "v":
                            position[nowP++] = Float.valueOf(strings[1]);
                            position[nowP++] = Float.valueOf(strings[2]);
                            position[nowP++] = Float.valueOf(strings[3]);
                            break;

                        case "vt":
                            texture[nowT++] = Float.valueOf(strings[1]);
                            texture[nowT++] = Float.valueOf(strings[2]);
                            texture[nowT++] = Float.valueOf(strings[3]);
                            break;

                        case "vn":
                            normal[nowN++] = Float.valueOf(strings[1]);
                            normal[nowN++] = Float.valueOf(strings[2]);
                            normal[nowN++] = Float.valueOf(strings[3]);
                            break;

                        case "f":
                            index[nowI++] = Short.valueOf(strings[1]);
                            index[nowI++] = Short.valueOf(strings[2]);
                            index[nowI++] = Short.valueOf(strings[3]);

                        default:
                            break;
                    }
                }

            } finally {
                if(is != null) is.close();
                if(br != null) br.close();
            }
        }  catch (Exception e) {
            // エラー発生時の処理
        }
        
    }

    public float[] getPosition() { return this.position;}
    public float[] getNormal() { return this.normal;}
    public float[] getTexture() { return this.texture;}
    public short[] getIndex() { return this.index;}
}
