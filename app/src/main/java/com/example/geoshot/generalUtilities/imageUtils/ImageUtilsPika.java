package com.example.geoshot.generalUtilities.imageUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import com.squareup.picasso.Transformation;

public class ImageUtilsPika {

    public ImageUtilsPika() {}

    public void setImageToViewProfile(Context context, ImageView imageView, String encodedString) {


        // Decodificar a string Base64
        byte[] decodedString = Base64.decode(encodedString, Base64.DEFAULT);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(decodedString);
        Bitmap decodedBitmap = BitmapFactory.decodeStream(inputStream);

        Picasso.get()
                .load(getBitmapUri(context,decodedBitmap))
                .transform(new CircleTransform()) // Aplicar transformação circular
                .centerCrop()                      // Crop para centro
                .fit()
                .into(imageView);
    }

    public void setImageToView(Context context, ImageView imageView, String encodedString) {


        // Decodificar a string Base64
        byte[] decodedString = Base64.decode(encodedString, Base64.DEFAULT);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(decodedString);
        Bitmap decodedBitmap = BitmapFactory.decodeStream(inputStream);

        Picasso.get()
                .load(getBitmapUri(context,decodedBitmap))
                .into(imageView);
    }

    private Uri getBitmapUri(Context context, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Title", null);
        return Uri.parse(path);
    }

    public class CircleTransform implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
            if (squaredBitmap != source) {
                source.recycle();
            }

            Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            BitmapShader shader = new BitmapShader(squaredBitmap,
                    BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
            paint.setShader(shader);
            paint.setAntiAlias(true);

            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);

            squaredBitmap.recycle();
            return bitmap;
        }

        @Override
        public String key() {
            return "circle";
        }
    }
}
