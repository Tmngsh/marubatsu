package com.tomproject.marubatsu;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Eishi on 2015/12/06.
 */
public class GameManager {
    private DrawObject draw;

    private Object3D maru3D;    // ○のブロックの3Dデータ
    private Object3D batsu3D;   // ×のブロックの3Dデータ
    private Object3D none3D;    // 何も入っていないブロックの3Dデータ
    private Object3D ball3D;    // ballの3Dデータ

    private GameObject ball = null;
    private ArrayList<GameObject> blocks;
    private CollisionDetection cd = null;
    int gameState;
    boolean touchedState;
    float[] touchedPosition;
    int nowPlayer;  // 現在のプレイヤー
    int colNum;     // 当たったブロックの番号

    boolean gameFinish;     // ゲームが終了したかどうか
    String winner;      // 勝利者

    public GameManager (Context context) {
        // drawの初期化
        draw = new DrawObject(context);

        // MyGLObjectの初期化
        maru3D = new Object3D(context, "object/maru.tob", "maru.png");
        batsu3D = new Object3D(context, "object/batsu.tob", "batsu.png");
        none3D = new Object3D(context, "object/cube.tob", "none.png");
        ball3D = new Object3D(context, "object/ball.tob", "ball.png");

        gameState = GameParam.STARTED;  // 最初はSTARTED状態



        gameFinish = false;
    }

    public void update() {
        // gameStateの状態で処理を分岐
        switch(gameState) {
            case GameParam.STARTED:
                startedUpdate();
                break;
            case GameParam.WAITED:
                waitedUpdate();
                break;
            case GameParam.TOUCHED:
                touchedUpdate();
                break;
            case GameParam.MOVED:
                movedUpdate();
                break;
            case GameParam.COLLIDED:
                collidedUpdate();
                break;
            case GameParam.FINISHED:
                finishedUpdate();
                break;
            default:
        }

        draw();
    }

    private void draw() {

        // ブロックを描画する
        for(int i = 0; i < GameParam.BLOCK_NUM; i++){
            draw.draw(blocks.get(i).getObject3D(), blocks.get(i).getMovement().getModelMatrix(), blocks.get(i).getMark());
        }

        // ボールが存在していれば描画する
        if(ball != null){
            draw.draw(ball.getObject3D(), ball.getMovement().getModelMatrix(), GameParam.BALL);
        }
    }

    private void startedUpdate() {


        // blocksの初期化
        blocks = new ArrayList<>();
        for(int i = 0; i < GameParam.BLOCK_NUM; i++) {
            GameObject obj = new GameObject(i, GameParam.MOV_BLOCK);
            obj.setObject3D(none3D);
            blocks.add(obj);
        }

        // touchedPositionの初期化
        touchedPosition = new float[3];
        touchedPosition[0] = 0.0f;
        touchedPosition[1] = 0.0f;
        touchedPosition[2] = 0.0f;

        // 最初は○から始まる
        nowPlayer = GameParam.MARU;

        // waitedに遷移する
        gameState = GameParam.WAITED;
    }

    private void waitedUpdate() {
        // blockのupdate
        for (int i = 0; i < GameParam.BLOCK_NUM; i++) {
            blocks.get(i).getMovement().waitedUpdate();
        }

        // 画面がタッチされた時、touchedに遷移する
        if(touchedState) {
            gameState = GameParam.TOUCHED;
        }
    }

    private void touchedUpdate() {
        // ballのインスタンスを生成
        if(ball == null) {
            ball = new GameObject(0, GameParam.MOV_BALL);
            ball.setObject3D(ball3D);
        }
        // ballのupdate
        ball.getMovement().touchedUpdate(touchedPosition);

        // blockのupdate
        for (int i = 0; i < GameParam.BLOCK_NUM; i++) {
            blocks.get(i).getMovement().touchedUpdate(touchedPosition);
        }

        // 画面からタッチが外れた時、movedに遷移する
        if(!touchedState) {
            gameState = GameParam.MOVED;
        }
    }

    private void movedUpdate() {
        // CollisionDetectionのインスタンスの生成
        if(cd == null) {
            cd = new CollisionDetection();
        }
        // ballのupdate
        ball.getMovement().movedUpdate();
        // blockのupdate
        for (int i = 0; i < GameParam.BLOCK_NUM; i++) {
            blocks.get(i).getMovement().movedUpdate();
        }

        // ボールとブロックの衝突検出
        colNum = cd.detect(blocks, ball);
        if(colNum >= 0 && colNum < GameParam.BLOCK_NUM) {
            int mark = blocks.get(colNum).getMark();
            if(mark == GameParam.NONE) {    // ブロックのマークがない時
                // 現在のプレイヤーのマークを入れる
                if (nowPlayer == GameParam.MARU) {
                    blocks.get(colNum).setMark(nowPlayer);
                    blocks.get(colNum).setObject3D(maru3D);
                } else {
                    blocks.get(colNum).setMark(nowPlayer);
                    blocks.get(colNum).setObject3D(batsu3D);
                }
            }
            // ballを削除する
            ball = null;
            // 衝突したのでcollidedに遷移する
            gameState = GameParam.COLLIDED;
        } else if(colNum == -2) {   // ボールのz座標が30を超えた時
            // ballを削除する
            ball = null;

            // プレイヤーを変える
            nowPlayer = (nowPlayer == GameParam.MARU) ? GameParam.BATSU : GameParam.MARU;

            // waitedに遷移する
            gameState = GameParam.WAITED;
        }

    }

    private void collidedUpdate() {
        if(blocks.get(colNum).getMovement().collidedUpdate()) { // 衝突したブロックの移動が終わったとき
            CheckGameFinished fin = new CheckGameFinished();

            if(fin.check(blocks)) {   // 終了しているとき
                // finishedに遷移する
                gameState = GameParam.FINISHED;
            }else {
                // プレイヤーを変える
                nowPlayer = (nowPlayer == GameParam.MARU) ? GameParam.BATSU : GameParam.MARU;

                // waitedに遷移する
                gameState = GameParam.WAITED;
            }
        }
    }

    private void finishedUpdate() {
        switch (nowPlayer) {
            case GameParam.MARU:
                winner = "Winner " + "○";
                break;
            case GameParam.BATSU:
                winner = "Winner " + "×";
                break;
        }
        gameFinish = true;

        // startedに戻る
        gameState = GameParam.STARTED;

    }

    public void setTouchedStatePosition(boolean state, float[] position) {
        touchedState = state;
        touchedPosition = position;
    }

    public  void displayWinner(Context context){
        if(gameFinish) {
            Toast.makeText(context, winner, Toast.LENGTH_LONG).show();

            gameFinish = false;
        }
    }


}
