package com.warmtel.eventbus.demo1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.warmtel.eventbus.R;

import de.greenrobot.event.EventBus;

public class MainDemoActivity extends AppCompatActivity {
    private Button mSendBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_demo_layout);
        mSendBtn = (Button) findViewById(R.id.event_btn);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.event_layout,TextFragment.newInstance())
                .commit();

        mSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "EventBus事件发布";
                int age = 23;
                EventBus.getDefault().post(new EventMessage(message,age));
            }
        });
    }
}
