package com.tomproject.marubatsu;

/**
 * Created by Eishi on 2015/12/06.
 */
public class GameParam {
    public static final int STARTED = 1;
    public static final int WAITED = 2;
    public static final int TOUCHED = 3;
    public static final int MOVED = 4;
    public static final int COLLIDED = 5;
    public static final int FINISHED = 6;


    public static final int BLOCK_NUM = 25;  // ブロック数

    // ブロックのマーク
    public static final int MARU = 0;   // ○
    public static final int BATSU = 1;  // ×
    public static final int NONE = -1;  // 何もなし
    public static final int BALL = 2;   // ball


    // ブロックかボールかMovementの作成で使う
    public static final int MOV_BLOCK = 0;
    public static final int MOV_BALL = 1;

    // ブロックの大きさ
    public static final float BLOCK_WIDTH = 3.0f;

    // ボールの大きさ
    public static final float BALL_RADIUS = 1.0f;

    // ブロックとブロックの間隔
    public static final float BLOCK_INTERVAL = 4.0f;

}
