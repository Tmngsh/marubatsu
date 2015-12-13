package com.tomproject.marubatsu;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by Eishi on 2015/11/25.
 */
public class ReadShader {
    public static String read(Context c, String filePath) {
        InputStreamReader is = null;
        BufferedReader br = null;
        String code = "";

        try {
            try{
                // ファイルの読み込み
                is = new InputStreamReader(c.getAssets().open(filePath));
                br = new BufferedReader(is);

                String line;
                while((line = br.readLine()) != null) {
                    code += line + "\n";
                }


            } finally {
                is.close();
                br.close();
            }
        } catch (Exception e) {
            // エラー発生時の処理
            Log.d("debug", "SHADER_ERROR!!");
        }

        return code;
    }
}
