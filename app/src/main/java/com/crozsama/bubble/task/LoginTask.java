package com.crozsama.bubble.task;

import android.content.Context;
import android.os.AsyncTask;

import com.crozsama.bubble.network.Api;
import com.crozsama.bubble.network.SignInResponse;


public class LoginTask extends AsyncTask<Void, Void, SignInResponse> {
    private OnPostExecutedListener onPostExecuted;
    private OnCancelledListener onCancelled;
    private Context ctx;
    private String username, password;

    public LoginTask(Context ctx, String username, String password) {
        this.ctx = ctx;
        this.username = username;
        this.password = password;
    }

    @Override
    protected SignInResponse doInBackground(Void... args) {
        return Api.signIn(ctx, username, password);
    }

    @Override
    protected void onPostExecute(SignInResponse response) {
        if (this.onPostExecuted != null) {
            onPostExecuted.onPostExecuted(response);
        }
    }

    @Override
    protected void onCancelled() {
        if (this.onCancelled != null) {
            this.onCancelled.onCancelled();
        }
    }

    public LoginTask setOnPostExecuted(OnPostExecutedListener listener) {
        this.onPostExecuted = listener;
        return this;
    }

    public LoginTask setOnCancelled(OnCancelledListener listener) {
        this.onCancelled = listener;
        return this;
    }
}