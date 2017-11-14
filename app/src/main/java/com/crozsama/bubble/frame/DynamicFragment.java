package com.crozsama.bubble.frame;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.crozsama.bubble.R;
import com.crozsama.bubble.base.CommonAdapter;
import com.crozsama.bubble.base.ViewHolder;
import com.crozsama.bubble.network.Api;
import com.crozsama.bubble.network.DynamicResponse;
import com.crozsama.bubble.network.ErrorCode;
import com.crozsama.bubble.task.HttpTask;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;


public class DynamicFragment extends Fragment {
    private View view;
    private ListView dynamicListView;
    private CommonAdapter adapter;
    private List<DynamicResponse.Bubble> bubbles;
    private HttpTask loadTask;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dynamic, container, false);
        dynamicListView = (ListView) view.findViewById(R.id.list_dynamic);
        bubbles = new ArrayList<>();
        adapter = new DynamicAdapter(this.getActivity(), bubbles, R.layout.item_dynamic);
        dynamicListView.setAdapter(adapter);

        load();
        return view;
    }


    private void load() {
        if (loadTask != null) {
            return;
        }
//        loadTask = new DynamicTask();
        loadTask = new HttpTask(Api.Method.GET, Api.getUserDynamicsUrl());
        loadTask.setOnPostExecuted(new HttpTask.OnPostExecutedListener() {
            @Override
            public void onPostExecuted(String str) {
                DynamicResponse response = new GsonBuilder().create().fromJson(str, DynamicResponse.class);
                loadTask = null;
                if (response == null) {
                    Toast.makeText(getActivity(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (response.code != ErrorCode.CODE_OK) {
                    Toast.makeText(getActivity(), ErrorCode.getErrorString(getActivity(), response.code), Toast.LENGTH_LONG).show();
                    return;
                }
                bubbles.addAll(response.data);
                adapter.notifyDataSetChanged();
            }
        }).setOnCancelled(new HttpTask.OnCancelledListener() {
            @Override
            public void onCancelled() {
                loadTask = null;
            }
        }).execute();
    }


    class DynamicAdapter extends CommonAdapter<DynamicResponse.Bubble> {
        private DynamicAdapter(Context context, List<DynamicResponse.Bubble> datas, int layoutId) {
            super(context, datas, layoutId);
        }

        @Override
        public void convert(ViewHolder holder, DynamicResponse.Bubble bubble) {
            holder.setText(bubble.content, R.id.text_dynamic_content);
        }
    }
}
