package com.youmi.tt.request;

import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.youmi.tt.base.BaseRequst;
import com.youmi.tt.entity.TestModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by hx on 2016/11/23.
 */
public class ShopCarGoodsRequst extends BaseRequst {


    public ShopCarGoodsRequst(IShopCarGoodView view, boolean show_error) {
        super(view, show_error);
    }


    @Override
    protected void onSuccess(JSONObject jsonObject, int url_type, int load_type, Bundle bundle) {
        List<TestModel> lists = null;
        try {
            if (lists == null)
            {

                lists = JSON.parseArray(jsonObject.getJSONObject("data").getJSONArray("topics").toString(),TestModel.class);
                ((IShopCarGoodView) view).onSuccessGoods(url_type,load_type,lists);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public interface IShopCarGoodView extends IBaseView {
        void onSuccessGoods(int url_type, int load_type, List<TestModel> list);
    }

}
