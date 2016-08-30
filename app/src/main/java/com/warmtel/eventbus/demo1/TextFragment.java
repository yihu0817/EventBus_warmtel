package com.warmtel.eventbus.demo1;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

public class TextFragment extends Fragment {
    private TextView mTextView;
    public static TextFragment newInstance() {
        Bundle args = new Bundle();
        TextFragment fragment = new TextFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(android.R.layout.simple_list_item_1,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mTextView = (TextView) getView().findViewById(android.R.id.text1);
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEventShowMessage(EventMessage message){
        mTextView.setText(message.getMessage() + " age :"+ message.getAge());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
