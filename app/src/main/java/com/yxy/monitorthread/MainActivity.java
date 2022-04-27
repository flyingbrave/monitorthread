package com.yxy.monitorthread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.yxy.monitormodel.ThreadInfoManager;
import com.yxy.monitormodel.bean.ThreadInfo;
import com.yxy.monitormodel.proxy.ProxyThread;

public class MainActivity extends AppCompatActivity {
    Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.sss).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thread=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("tag5","Thread2");
                    }
                });
                thread.setName("yxy-thread");
                thread.start();

            }
        });

        findViewById(R.id.sss2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(thread instanceof ProxyThread){
                    Log.i("tag5","thread instanceof ProxyThread  替换成功");
                }
                ThreadInfo threadInfo=ThreadInfoManager.getINSTANCE().getThreadInfoById(thread.getId());
                if(threadInfo!=null){
                    Log.i("tag5","threadInfo "+threadInfo.getName());
                }
            }
        });
    }
}