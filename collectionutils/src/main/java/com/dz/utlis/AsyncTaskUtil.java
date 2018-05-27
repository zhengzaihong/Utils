package com.dz.utlis;

import android.os.AsyncTask;

/**
* creat_user: zhengzaihong 
* Email:1096877329@qq.com
* creat_date: 2018/5/22
* creat_time: 9:45
* describe 异步处理类
**/
public class AsyncTaskUtil extends AsyncTask<String, Integer, Void> {

    private AsyncTaskListener mAsyncTaskListener;

    private int sleepTime = 0;

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (mAsyncTaskListener != null) {
            mAsyncTaskListener.onPostExecute();
        }
    }

    @Override
    protected Void doInBackground(String... strings) {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (mAsyncTaskListener != null) {
            mAsyncTaskListener.doInBackground();
        }

        return null;

    }

    public interface AsyncTaskListener {
        void doInBackground();

        void onPostExecute();

    }

    public void setAsyncTaskListener(AsyncTaskListener mAsyncTaskListener) {
        this.mAsyncTaskListener = mAsyncTaskListener;
    }

    public void setSleepTime(int sleepTime) {
        this.sleepTime = sleepTime;
    }
}
