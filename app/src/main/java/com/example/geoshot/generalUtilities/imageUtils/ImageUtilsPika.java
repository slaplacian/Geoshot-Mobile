package com.example.geoshot.generalUtilities.imageUtils;

import android.content.Context;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;

public class ImageUtilsPika {
    private static final String TAG = "ImageUtils";

    public static void setImageToViewProfile(Context context, ImageView imageView,  String base64EncodedString) {
        try {
            byte[] decodedBytes = Base64.decode(base64EncodedString, Base64.DEFAULT);

            Glide.with(context)
                    .asBitmap()
                    .load(decodedBytes)
                    .transform(new CircleCrop())
                    .into(imageView);

            Log.d(TAG, "Image successfully set to ImageView.");
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "Base64 decode error: " + e.getMessage());
        }
    }
}