package com.tomproject.marubatsu;

import java.util.ArrayList;

/**
 * Created by Eishi on 2015/12/08.
 */
public class CheckGameFinished {
    public boolean check(ArrayList<GameObject> blocks) {

        int rowLen = (int) Math.sqrt(GameParam.BLOCK_NUM);  // ブロックの一列の長さ

        for (int i = 0; i < GameParam.BLOCK_NUM; i++) {
            int x = i % rowLen;
            int y = i / rowLen;

            if (x >= 1 && x < rowLen - 1) {
                // 横の処理を描く
                int a = blocks.get(y * rowLen + x - 1).getMark();
                int b = blocks.get(y * rowLen + x).getMark();
                int c = blocks.get(y * rowLen + x + 1).getMark();
                if(a != GameParam.NONE && b != GameParam.NONE && c != GameParam.NONE) {
                    if (a == b && b == c) {  // 3つのマークが同じとき
                        return true;
                    }
                }
            }

            if (y >= 1 && y < rowLen - 1) {
                // 縦の処理を描く
                int a = blocks.get((y - 1) * rowLen + x).getMark();
                int b = blocks.get(y * rowLen + x).getMark();
                int c = blocks.get((y + 1) * rowLen + x).getMark();

                if(a != GameParam.NONE && b != GameParam.NONE && c != GameParam.NONE) {
                    if (a == b && b == c) {  // 3つのマークが同じとき
                        return true;
                    }
                }

            }

            if (x >= 1 && x < rowLen - 1) {
                if(y >= 1 && y < rowLen -1) {

                    // 斜めの処理を描く
                    int a = blocks.get((y - 1) * rowLen + x - 1).getMark();
                    int b = blocks.get(y * rowLen + x).getMark();
                    int c = blocks.get((y + 1)* rowLen + x + 1).getMark();

                    if(a != GameParam.NONE && b != GameParam.NONE && c != GameParam.NONE) {
                        if (a == b && b == c) {  // 3つのマークが同じとき
                            return true;
                        }
                    }

                    // 逆の斜めの処理を描く
                    a = blocks.get((y + 1) * rowLen + x - 1).getMark();
                    b = blocks.get(y * rowLen + x).getMark();
                    c = blocks.get((y - 1)* rowLen + x + 1).getMark();

                    if(a != GameParam.NONE && b != GameParam.NONE && c != GameParam.NONE) {
                        if (a == b && b == c) {  // 3つのマークが同じとき
                            return true;
                        }
                    }

                }

            }

        }

        return false;
    }
}
