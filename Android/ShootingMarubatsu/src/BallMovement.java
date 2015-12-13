package com.tomproject.marubatsu;

/**
 * Created by Eishi on 2015/12/10.
 */
public class BallMovement extends Movement {
    private float touchedPositionX;     // touched状態の時にタッチされた場所を3Dの座標系に合わせて
    private float touchedPositionY;     // 記録しておくための変数

    private float moveZ;       // ボールが動くときに使う

    public BallMovement() {
        position[0] = 0.0f;
        position[1] = 0.0f;
        position[2] = 0.0f;

        // scaleは1.0fで初期化
        scale[0] = GameParam.BALL_RADIUS / 2.0f;
        scale[1] = GameParam.BALL_RADIUS / 2.0f;
        scale[2] = GameParam.BALL_RADIUS / 2.0f;

        // rotateは0.0fで初期化
        rotateX = 0.0f;
        rotateY = 0.0f;

        moveZ = 0.0f;
    }

    @Override
    public void touchedUpdate(float[] touchedPosition) {
        super.touchedUpdate(touchedPosition);

        touchedPositionX = -touchedPosition[0] / 30.0f;     // 画面の座標系のx軸と逆
        touchedPositionY = -touchedPosition[1] / 25.0f;     // 画面の座標系のy軸と逆
    }

    @Override
    public void movedUpdate() {
        super.movedUpdate();

        position[0] = touchedPositionX / 20.0f * moveZ;
        position[1] = touchedPositionY / 20.0f * moveZ;
        position[2] = moveZ;

        rotateX = (moveZ * 4.0f) % 360.0f;

        moveZ += 0.4;

    }
}
