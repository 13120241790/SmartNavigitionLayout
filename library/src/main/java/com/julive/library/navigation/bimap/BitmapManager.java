package com.julive.library.navigation.bimap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BitmapManager {

    private static final String TAG = BitmapManager.class.getSimpleName();

    private static BitmapManager mInstance;

    public static BitmapManager getInstance() {
        if (mInstance == null) {
            synchronized (BitmapManager.class) {
                if (mInstance == null) {
                    mInstance = new BitmapManager();
                }
            }
        }
        return mInstance;
    }

    private ExecutorService mExecutorService;

    private BitmapManager() {
        mExecutorService = Executors.newCachedThreadPool();
    }

    public void httpToBitMap(String url, ResultBitmapListener resultBitmapListener) {
        mExecutorService.execute(new WorkRunnable(url, resultBitmapListener));
    }

    class WorkRunnable implements Runnable {

        private String url;
        private ResultBitmapListener resultBitmapListener;

        private WorkRunnable(String url, ResultBitmapListener resultBitmapListener) {
            this.url = url;
            this.resultBitmapListener = resultBitmapListener;
        }

        @Override
        public void run() {

            Log.e(TAG, " Thread name: " + Thread.currentThread().getName() + " Thread id: " + Thread.currentThread().getId());

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
}



