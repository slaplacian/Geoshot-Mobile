package com.example.geoshot.utils.imageUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
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

    public static void setImageToImageView(HomeAdapter.ViewHolder holder, String base64EncodedString,
                                           ImageView holderIimageView) {
        Bitmap decodedBitmap = ImageUtils.decodeBase64ToBitmap(base64EncodedString);
        ImageLoader imageLoader = Coil.imageLoader(holder.itemView.getContext());
        ImageRequest request = new ImageRequest.Builder(holder.itemView.getContext())
                .data(decodedBitmap)
                .target(holderIimageView)
                .build();
        imageLoader.enqueue(request);
    }
}
