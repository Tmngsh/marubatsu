package com.tomproject.marubatsu;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by Eishi on 2015/12/10.
 */
public class CreateBuffer {
    public static FloatBuffer createFloatBuffer(float[] list){
        ByteBuffer bb = ByteBuffer.allocateDirect(list.length * 4);
        bb.order(ByteOrder.nativeOrder());
        FloatBuffer buffer = bb.asFloatBuffer();
        buffer.put(list);
        buffer.position(0);
        return buffer;
    }

    public static ShortBuffer createShortBuffer(short[] list) {
        ByteBuffer bb = ByteBuffer.allocateDirect(list.length * 2);
        bb.order(ByteOrder.nativeOrder());
        ShortBuffer buffer = bb.asShortBuffer();
        buffer.put(list);
        buffer.position(0);
        return buffer;
    }
}
