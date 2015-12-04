package com.cardinalblue.trek;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import java.util.concurrent.Callable;

import bolts.Continuation;
import bolts.Task;

public class PreloaderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preloader);

        // Full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);


        Task.callInBackground(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                // TODO: Check update, db migration.
                Thread.sleep(2000);
                return null;
            }
        }).continueWith(new Continuation<Object, Object>() {
            @Override
            public Object then(Task<Object> task) throws Exception {
                // TODO: goto start page.
                finish();
                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);
    }
}
