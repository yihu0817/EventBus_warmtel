package com.warmtel.eventbus;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.warmtel.eventbus.demo1.MainDemoActivity;
import com.warmtel.eventbus.demo2.music.MusicActivity;
import com.warmtel.eventbus.demo2.music_eventbus.MusicPlayerActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ListActivity {
    public ListView mListView;
    public List<MainIntentBean> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListView = getListView();
        inintData();
        MainIntentAdapter adapter = new MainIntentAdapter(this);
        adapter.setList(list);
        setListAdapter(adapter);
        mListView.setSelection(list.size()-1);
    }

    /**
     * 初始化数据源
     */
    public void inintData(){
        addItem("Activity与Fragment交互EventBus实现", MainDemoActivity.class);
        addItem("音乐播放交互Binder实现",MusicActivity.class);
        addItem("音乐播放交互EventBus实现",MusicPlayerActivity.class);
    }

    public <T> void addItem(String title,Class<T> t){
        MainIntentBean mainIntentBean = new MainIntentBean(title,t);
        list.add(mainIntentBean);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        MainIntentAdapter adapter = (MainIntentAdapter) l.getAdapter();
        MainIntentBean mainIntentBean = (MainIntentBean) adapter.getItem(position);
        Intent intent = new Intent(this,mainIntentBean.toActivityClass);
        startActivity(intent);
    }

    public class MainIntentAdapter extends BaseAdapter{
        private List<MainIntentBean> list = new ArrayList<>();
        private LayoutInflater layoutInflater;
        public MainIntentAdapter(Context context){
            layoutInflater = LayoutInflater.from(context);
        }
        public void setList(List<MainIntentBean> list){
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = layoutInflater.inflate(android.R.layout.simple_list_item_1,null);
                TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
                convertView.setTag(textView);
            }
            TextView textView = (TextView) convertView.getTag();

            MainIntentBean item = (MainIntentBean) getItem(position);
            textView.setText(item.title);
            return convertView;
        }
    }


    public class MainIntentBean<T>{
        private String title;
        private Class<T> toActivityClass;

        public MainIntentBean(String title, Class<T> toActivityClass) {
            this.title = title;
            this.toActivityClass = toActivityClass;
        }
    }
}
