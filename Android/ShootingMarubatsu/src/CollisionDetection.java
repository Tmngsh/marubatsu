package com.tomproject.marubatsu;

import java.util.ArrayList;

/**
 * Created by Eishi on 2015/12/06.
 */
public class CollisionDetection {
    public int detect(ArrayList<GameObject> blocks, GameObject ball){
        // ballのpositionを取得
        float [] ballP = ball.getMovement().getPosition();

        for(int i = 0; i < GameParam.BLOCK_NUM; i++) {
            // blocks[i]のpositionを取得
            float [] blockP = blocks.get(i).getMovement().getPosition();

            // z座標の差が円の半径とブロックの幅/2を足したものより小さいか調べる
            if(Math.abs(ballP[2] - blockP[2]) <= (GameParam.BALL_RADIUS + GameParam.BLOCK_WIDTH/2.0f)) {
                // y座標の差が1.5以内か調べる
                if(Math.abs(ballP[1] - blockP[1]) <= GameParam.BLOCK_WIDTH/2.0f) {
                    // x座標の差が1.5以内か調べる
                    if(Math.abs(ballP[0] - blockP[0]) <= GameParam.BLOCK_WIDTH/2.0f) {
                        return i;
                    }
                }
            }
        }

        if(ballP[2] >= 30.0f) {
            return -2;
        }

        // どのブロックとも衝突していない時 -1 を返す
        return -1;

    }
}
