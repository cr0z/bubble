package com.crozsama.bubble.frame;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.crozsama.bubble.R;
import com.crozsama.bubble.base.CommonAdapter;
import com.crozsama.bubble.base.ViewHolder;
import com.crozsama.bubble.network.Api;
import com.crozsama.bubble.network.DynamicResponse;
import com.crozsama.bubble.network.ErrorCode;
import com.crozsama.bubble.utils.L;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class DynamicFragment extends Fragment {
    private View view;
    private ListView dynamicListView;
    private CommonAdapter adapter;
    private List<DynamicResponse.Bubble> bubbles;
    private static LoadTask loadTask;


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
        loadTask.execute();
        return view;
    }


    private void load() {
        if (loadTask != null) {
            return;
        }
        loadTask = new LoadTask();

    }

    class LoadTask extends AsyncTask<Void, Void, DynamicResponse> {
        @Override
        protected DynamicResponse doInBackground(Void... voids) {
            String responseStr;
            try {
                responseStr = Api.get(Api.getUserDynamicsUrl());
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            L.d("xxx", responseStr);
            return new GsonBuilder().create().fromJson(responseStr, DynamicResponse.class);
        }

        @Override
        protected void onPostExecute(DynamicResponse response) {
            loadTask = null;
            if (response == null) {
                Snackbar.make(view, R.string.network_error, Snackbar.LENGTH_SHORT).show();
                return;
            }
            if (response.code != ErrorCode.CODE_OK) {
                Snackbar.make(view, ErrorCode.getErrorString(getContext(), response.code), Snackbar.LENGTH_LONG).show();
                return;
            }
            bubbles.addAll(response.data);
            adapter.notifyDataSetChanged();
        }

        @Override
        protected void onCancelled() {
            loadTask = null;
        }
    }

    class DynamicAdapter extends CommonAdapter<DynamicResponse.Bubble> {
        public DynamicAdapter(Context context, List<DynamicResponse.Bubble> datas, int layoutId) {
            super(context, datas, layoutId);
        }

        @Override
        public void convert(ViewHolder holder, DynamicResponse.Bubble bubble) {
            holder.setText(bubble.content, R.id.text_dynamic_content);
        }
    }
}
