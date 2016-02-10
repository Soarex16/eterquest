package com.soarex.eterquest.util;

import org.newdawn.slick.opengl.PNGDecoder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * Created by shumaf on 10.02.16.
 */
public class ImageUtil {

    public static ByteBuffer loadIcon(InputStream is) throws IOException {
        try {
            PNGDecoder decoder = new PNGDecoder(is);
            ByteBuffer bb = ByteBuffer.allocateDirect(decoder.getWidth()*decoder.getHeight()*4);
            decoder.decode(bb, decoder.getWidth()*4, PNGDecoder.RGBA);
            bb.flip();
            return bb;
        } finally {
            is.close();
        }
    }
}
