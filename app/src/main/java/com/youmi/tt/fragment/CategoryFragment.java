package com.youmi.tt.fragment;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.youmi.tt.R;
import com.youmi.tt.activity.GoodsDetailActivity;
import com.youmi.tt.adapter.CategoryAdapter;
import com.youmi.tt.adapter.CategoryGoodsAdapter;
import com.youmi.tt.base.BaseAdapter;
import com.youmi.tt.base.BaseFragment;
import com.youmi.tt.entity.TestModel;
import com.youmi.tt.request.CategoryGoodsRequst;
import com.youmi.tt.view.recyclerview.RecyclerViewWrap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.youmi.tt.utils.ActivityUtil.goToActivityFromRight2Left;

/**
 * A simple {@link BaseFragment} subclass.
 */
public class CategoryFragment extends BaseFragment implements CategoryGoodsRequst.ICategpryGoodView, SwipeRefreshLayout.OnRefreshListener {


    @Bind(R.id.listview) ListView listView;
    @Bind(R.id.recyclerview) RecyclerViewWrap recyclerview;
    @Bind(R.id.loadview_ll) LinearLayout loadview_ll;
    @Bind(R.id.swipe_layout) SwipeRefreshLayout swipe_layout;

    private View view;
    private List<String> lists;
    private List<TestModel> goods;
    private CategoryAdapter adapter;
    private LinearLayoutManager manager;
    private CategoryGoodsAdapter goodsAdapter;
    private CategoryGoodsRequst requst;
    private String url = "http://www.putaoji.com/apix/pubaTopic/lists?user_token=&uid=&api_version=1.0.0&ctype=4&row=10&page=1&search_uid=";


    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (view == null){
            view = inflater.inflate(R.layout.fragment_category, container, false);
            ButterKnife.bind(this, view);

            setData();

            adapter = new CategoryAdapter(getActivity(),lists);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    adapter.setSelect(i);
                    adapter.notifyDataSetChanged();
                    reqData(0,LOAD_AUTO);
                }
            });

            goods = new ArrayList<>();
            goodsAdapter = new CategoryGoodsAdapter(getActivity(),goods);

            manager = new LinearLayoutManager(getActivity());
            recyclerview.setLayoutManager(manager);
            recyclerview.setHasFixedSize(true);

            swipe_layout.setEnabled(true);
            swipe_layout.setOnRefreshListener(this);

            // 使用 setIAdapter 不是setAdapter
            recyclerview.setAdapter(goodsAdapter);
            goodsAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View itemView, int position, int... what) {
                    goToActivityFromRight2Left(getActivity(), GoodsDetailActivity.class);
                }
            });

            reqData(URL_LIST,LOAD_AUTO);
        }

        return view;
    }

    private void reqData(int url_type, int load_type) {


        if (requst == null) {
            requst = new CategoryGoodsRequst(this,true);
        }
        requst.reqData(url_type, load_type);

    }


    private void setData() {
        lists = new ArrayList<>();
        lists.add("精品快餐");
        lists.add("冷饮饮品");
        lists.add("粮油干货");
        lists.add("家居清洁");
        lists.add("营养冲饮");
        lists.add("酒类");
        lists.add("纸巾洗护");
        lists.add("代购");
        lists.add("预售");
    }

    public void updateview(){
        recyclerview.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onSuccessGoods(int url_type, int load_type, List<TestModel> list) {
        goods.clear();
        if (list != null){
            goods.addAll(list);
            updateview();
        }
    }

    @Override
    public String getUrl(int url_type) {
        return url;
    }

    @Override
    public Map<String, String> getParams(int url_type, int load_type, Bundle bundle) {
        return null;
    }

    @Override
    public void onError(int url_type, int load_type, String error) {

    }

    @Override
    public void showLoadingUI(int url_type, int load_type) {
        if (url_type == URL_LIST) {
            // ... 显示加载UI
            if (load_type == LOAD_AUTO) {
                setViewVisible(loadview_ll, true);

            } else if (load_type == LOAD_TOP) {
                swipe_layout.setRefreshing(true);
            }
        }
    }

    @Override
    public void hideLoadingUI(int url_type, int load_type, boolean success) {
        if (url_type == URL_LIST) {
            if (load_type == LOAD_AUTO) {
                setViewVisible(loadview_ll);

            } else if (load_type == LOAD_TOP) {
                if (swipe_layout.isRefreshing()) {
                    swipe_layout.setRefreshing(false);
                }
            }
        }
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        reqData(URL_LIST,LOAD_TOP);
    }
}
