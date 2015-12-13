package com.tomproject.marubatsu;

import android.graphics.Rect;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    MyGLView myGLView;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float [] position = new float[2];
        position[0] = event.getX();     // 画面のx座標
        position[1] = event.getY();     // 画面のy座標

        // Viewの中心を(0, 0)にした座標系に変換
        // 画面上でのViewの中心の座標
        Rect rect = new Rect();
        myGLView.getGlobalVisibleRect(rect);    // グローバル座標系のviewの座標

        float centerX = (rect.left + rect.right)/ 2;
        float centerY = (rect.top + rect.bottom) / 2;

        position[0] -= centerX;
        position[1] -= centerY;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                myGLView.setTouchedStatePosition(true, position);
                break;
            case MotionEvent.ACTION_MOVE:
                myGLView.setTouchedStatePosition(true, position);
                break;
            case MotionEvent.ACTION_UP:
                myGLView.setTouchedStatePosition(false, position);
                break;
            default:
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myGLView = new MyGLView(this);
        setContentView(myGLView);

    }

    @Override
    protected void onPause() {
        super.onPause();
        myGLView.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
        myGLView.onResume();

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
