package com.example.geoshot.generalUtilities.imageUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

import com.example.geoshot.ui.home.HomeAdapter;

import coil.Coil;
import coil.ImageLoader;
import coil.request.ImageRequest;


public class ImageUtils {
    public static Bitmap decodeBase64ToBitmap(String base64Str) throws IllegalArgumentException {
        byte[] decodedBytes = Base64.decode(base64Str, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    public static void setImageToImageView(View itemView, String base64EncodedString,
                                           ImageView holderImageView) {
        Bitmap decodedBitmap = ImageUtils.decodeBase64ToBitmap(base64EncodedString);
        ImageLoader imageLoader = Coil.imageLoader(itemView.getContext());
        ImageRequest request = new ImageRequest.Builder(itemView.getContext())
                .data(decodedBitmap)
                .target(holderImageView)
                .build();
        imageLoader.enqueue(request);
    }
}
