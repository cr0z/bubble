package com.crozsama.bubble.task;

import android.os.AsyncTask;

import com.crozsama.bubble.network.Api;

import java.io.IOException;
import java.util.Map;

public class HttpTask extends AsyncTask<Void, Void, String> {
    private OnPostExecutedListener onPostExecuted;
    private OnCancelledListener onCancelled;
    private Api.Method method;
    private String addr;
    private Map<String, String>[] body;

    public HttpTask(Api.Method method, String addr, Map<String, String>... body) {
        this.method = method;
        this.addr = addr;
        this.body = body;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            switch (this.method) {
                case GET:
                    return Api.get(this.addr);
                case POST:
                    return Api.post(this.addr, this.body);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String t) {
        this.onPostExecuted.onPostExecuted(t);
    }

    @Override
    protected void onCancelled() {
        this.onCancelled.onCancelled();
    }

    public HttpTask setOnPostExecuted(OnPostExecutedListener listener) {
        this.onPostExecuted = listener;
        return this;
    }

    public HttpTask setOnCancelled(OnCancelledListener listener) {
        this.onCancelled = listener;
        return this;
    }

    public interface OnPostExecutedListener {
        void onPostExecuted(String s);
    }

    public interface OnCancelledListener {
        void onCancelled();
    }
}
