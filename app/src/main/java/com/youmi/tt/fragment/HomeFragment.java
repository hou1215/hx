package com.youmi.tt.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.youmi.tt.R;
import com.youmi.tt.adapter.HomeGoodsAdapter;
import com.youmi.tt.base.BaseFragment;
import com.youmi.tt.entity.TestModel;
import com.youmi.tt.request.HomeGoodsRequst;
import com.youmi.tt.view.BannerViewPager;
import com.youmi.tt.view.recyclerview.RecyclerViewWrap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link BaseFragment} subclass.
 */
public class HomeFragment extends BaseFragment implements HomeGoodsRequst.IHomeGoodView{


    @Bind(R.id.loadview_ll) LinearLayout loadview_ll;
    @Bind(R.id.recyclerview) RecyclerViewWrap recyclerview;

    private List<TestModel> datas;
    private LinearLayoutManager manager;
    private HomeGoodsAdapter adapter;
    private String url = "http://www.putaoji.com/apix/pubaTopic/lists?user_token=&uid=&api_version=1.0.0&ctype=4&row=10&page=1&search_uid=";
    private HomeGoodsRequst requst;
    private BannerViewPager banner;

    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (main_layout == null){
            main_layout = inflateView(R.layout.fragment_home, container);
            ButterKnife.bind(this, main_layout);

            datas = new ArrayList<>();
            datas.add(new TestModel());
            adapter = new HomeGoodsAdapter(getActivity(), datas);
            manager = new LinearLayoutManager(getActivity());
            recyclerview.setLayoutManager(manager);
            recyclerview.setHasFixedSize(true);

            recyclerview.setIAdapter(adapter);
            setRefreshLister(recyclerview);
            initHeader();

            reqData(URL_LIST,LOAD_AUTO);
        }

        return main_layout;
    }

    private void initHeader() {

        View header = inflateView(R.layout.item_home_header);
        banner = fv(R.id.viewpager,header);
        banner.setBannerStyle(BannerViewPager.CIRCLE_INDICATOR);
        banner.setOnBannerImageListener(new BannerViewPager.OnLoadImageListener() {
            @Override
            public void OnLoadImage(ImageView view, Object url) {
                loadImage(view, url.toString());
            }
        });

        banner.setOnBannerClickListener(new BannerViewPager.OnBannerClickListener() {

            @Override
            public void OnBannerClick(View view, int position) {
                position--;
                toast(position + "");
            }
        });
        banner.setImages(3);
        recyclerview.addHeaderView(header);
    }

    private void reqData(int url_type, int load_type) {


        if (requst == null) {
            requst = new HomeGoodsRequst(this,true);
        }
        requst.reqData(url_type, load_type);

    }

    @Override
    public void onSuccessGoods(int url_type, int load_type, List<TestModel> list) {

        if (list != null){
            datas.addAll(list);
            recyclerview.getAdapter().notifyDataSetChanged();
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

            }
        }
    }

    @Override
    public void hideLoadingUI(int url_type, int load_type, boolean success) {
        if (url_type == URL_LIST) {
            if (load_type == LOAD_AUTO) {
                setViewVisible(loadview_ll);

            } else if (load_type == LOAD_TOP) {
                recyclerview.setRefreshing(false);
            }
        }

    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        reqData(URL_LIST,LOAD_TOP);
    }

    @Override
    public void onLoadMore(View view) {
        super.onLoadMore(view);
    }
}
