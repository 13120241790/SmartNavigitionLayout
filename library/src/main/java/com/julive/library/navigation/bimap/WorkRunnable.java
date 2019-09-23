package com.julive.library.navigation.bimap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WorkRunnable implements Runnable {

    private String url;
    private ResultBitmapListener resultBitmapListener;

    public WorkRunnable(String url, ResultBitmapListener resultBitmapListener) {
        this.url = url;
        this.resultBitmapListener = resultBitmapListener;
    }

    @Override
    public void run() {

        Log.e(BitmapManager.class.getSimpleName(), " Thread name: " + Thread.currentThread().getName() + " Thread id: " + Thread.currentThread().getId());

        URL imageUrl = null;

        try {
            imageUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            if (bitmap != null) {
                resultBitmapListener.resultBitmap(bitmap);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
