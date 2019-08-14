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
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 支持 http 图片转 bitmap
 * TODO 检查 bitmap 过大则压缩
 */
public class BitmapManager {

    private static final String TAG = BitmapManager.class.getSimpleName();

    private static BitmapManager mInstance;

    private ExecutorService mExecutorService;

    private BitmapManager() {
        mExecutorService = new ThreadPoolExecutor(0, 8,
                5L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());
    }

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

    public void httpBitMap(String url, ResultBitmapListener resultBitmapListener) {
        mExecutorService.execute(new WorkThread(url, resultBitmapListener));
    }

    class WorkThread extends Thread {

        private String url;
        private ResultBitmapListener resultBitmapListener;

        private WorkThread(String url, ResultBitmapListener resultBitmapListener) {
            this.url = url;
            this.resultBitmapListener = resultBitmapListener;
        }

        @Override
        public void run() {
            Log.e(TAG, " Thread name: " + getName() + " Thread id: " + getId());

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


//    private static Context mContext;
//    public static void init(Context context){
//        mContext = context;
//    }


