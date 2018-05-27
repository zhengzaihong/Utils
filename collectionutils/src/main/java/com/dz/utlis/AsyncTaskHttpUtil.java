package com.dz.utlis;

import android.os.AsyncTask;

import com.dz.utlis.bean.HttpResult;


/**
 * creat_user: zhengzaihong
 * email:1096877329@qq.com
 * creat_date: 2018/5/27
 * creat_time: 12:11
 * describe: 异步处理类
 **/

public class AsyncTaskHttpUtil extends AsyncTask<String, Integer, HttpResult> {

    private AsyncTaskListener mAsyncTaskListener;

    private int sleepTime = 0;

    @Override
    protected void onPostExecute(HttpResult httpResult) {
        if (mAsyncTaskListener != null) {
            mAsyncTaskListener.onPostExecute(httpResult);
        }
    }

    @Override
    protected HttpResult doInBackground(String... strings) {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (mAsyncTaskListener != null) {
            return mAsyncTaskListener.doInBackground();
        }
        return null;


    }

    public interface AsyncTaskListener {
        HttpResult doInBackground();

        void onPostExecute(HttpResult httpResult);
    }

    public void setAsyncTaskListener(AsyncTaskListener mAsyncTaskListener) {
        this.mAsyncTaskListener = mAsyncTaskListener;
    }

    public void setSleepTime(int sleepTime) {
        this.sleepTime = sleepTime;
    }
}
