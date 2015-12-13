package com.tomproject.marubatsu;

/**
 * Created by Eishi on 2015/12/10.
 */
public class BlockMovement extends Movement {

    private int rad;     // waitedの間オブジェクトを動かす


    @Override
    public void movedUpdate() {
        super.movedUpdate();

        this.waitedUpdate();
    }

    @Override
    public void waitedUpdate() {
        super.waitedUpdate();

        // rotateX = rad;
        rotateY = rad;
        rad += 2;
        if(rad >= 360) {
            rad = 0;
        }
    }

    // コンストラクタ
    public BlockMovement(int arrayNumber) {
        super();

        // 配列の番号でpositionを初期化
        float x = GameParam.BLOCK_INTERVAL * (float)(arrayNumber % (int)Math.sqrt(GameParam.BLOCK_NUM)) - GameParam.BLOCK_INTERVAL * (float)(Math.sqrt(GameParam.BLOCK_NUM) - 3);
        float y = -GameParam.BLOCK_INTERVAL * (float)(arrayNumber / (int)Math.sqrt(GameParam.BLOCK_NUM)) + GameParam.BLOCK_INTERVAL * (float)(Math.sqrt(GameParam.BLOCK_NUM) - 3);
        float z = 21.5f;
        position[0] = x;
        position[1] = y;
        position[2] = z;

        // scaleは1.0fで初期化
        scale[0] = GameParam.BLOCK_WIDTH / 2.0f;    // 3Dモデルの幅が2.0あるので2で割る
        scale[1] = GameParam.BLOCK_WIDTH / 2.0f;
        scale[2] = GameParam.BLOCK_WIDTH / 2.0f;

        // rotateは0.0fで初期化
        rotateX = 0.0f;
        rotateY = 0.0f;
    }
}
