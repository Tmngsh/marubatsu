package com.tomproject.marubatsu;

/**
 * Created by Eishi on 2015/12/06.
 */
public class GameObject {
    Object3D object3D;
    Movement movement;
    int mark;   // ブロックのマーク

    public GameObject(int arrayNumber, int movType){
        // 使用するmovTypeに指定されたMovmentを使う
        switch (movType){
            case GameParam.MOV_BLOCK:
                this.movement = new BlockMovement(arrayNumber);
                break;
            case GameParam.MOV_BALL:
                this.movement = new BallMovement();
                break;
            default:
        }

        this.mark = GameParam.NONE;  // 最初は何も入っていないのでfalse
    }

    public void setObject3D(Object3D obj) {
        this.object3D = obj;
    }

    public Object3D getObject3D() {
        return this.object3D;
    }

    public Movement getMovement(){
        return this.movement;
    }

    // 現在のマーク
    public void setMark(int s) { this.mark = s;}
    public int getMark() { return this.mark;}



}
