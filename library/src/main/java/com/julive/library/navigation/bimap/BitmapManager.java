package com.julive.library.navigation.bimap;

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

}



