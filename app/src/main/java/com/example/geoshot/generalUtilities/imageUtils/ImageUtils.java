package com.example.geoshot.generalUtilities.imageUtils;

//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.util.Base64;
//import android.view.View;
//import android.widget.ImageView;
//
//import com.example.geoshot.ui.home.HomeAdapter;
//
//import coil.Coil;
//import coil.ImageLoader;
//import coil.request.ImageRequest;


//public class ImageUtils {
//    public static Bitmap decodeBase64ToBitmap(String base64Str) throws IllegalArgumentException {
//        byte[] decodedBytes = Base64.decode(base64Str, Base64.DEFAULT);
//        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
//    }
//
//    public static void setImageToImageView(View itemView, String base64EncodedString,
//                                           ImageView holderImageView) {
//        Bitmap decodedBitmap = ImageUtils.decodeBase64ToBitmap(base64EncodedString);
//        ImageLoader imageLoader = Coil.imageLoader(itemView.getContext());
//        ImageRequest request = new ImageRequest.Builder(itemView.getContext())
//                .data(decodedBitmap)
//                .target(holderImageView)
//                .build();
//        imageLoader.enqueue(request);
//    }
//}

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class ImageUtils {
    private static final String TAG = "ImageUtils";

    public static Bitmap decodeBase64ToBitmap(String base64Str) throws IllegalArgumentException {
        try {
            byte[] decodedBytes = Base64.decode(base64Str, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "Base64 decode error: " + e.getMessage());
            throw e;
        }
    }

    public static void setImageToImageView(View itemView, String base64EncodedString, ImageView holderImageView) {
        try {
            Bitmap decodedBitmap = ImageUtils.decodeBase64ToBitmap(base64EncodedString);
            if (decodedBitmap != null) {
                Log.d(TAG, "Bitmap successfully decoded");
                holderImageView.setImageBitmap(decodedBitmap);
            } else {
                Log.e(TAG, "Bitmap decoding failed");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error setting image to ImageView: " + e.getMessage());
        }
    }
}