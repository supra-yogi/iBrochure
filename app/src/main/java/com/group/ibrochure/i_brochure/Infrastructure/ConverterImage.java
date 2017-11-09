package com.group.ibrochure.i_brochure.Infrastructure;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

/**
 * Created by Yogi on 09/11/2017.
 */

public class ConverterImage {

    public static String encodeBase64(ImageView imageView) {
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bb = baos.toByteArray();
        return Base64.encodeToString(bb, Base64.DEFAULT);
    }

    public static Bitmap decodeBase64(String encode) {
        byte[] decodedString = Base64.decode(encode, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }
}
