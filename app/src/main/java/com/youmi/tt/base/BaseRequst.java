package com.youmi.tt.base;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.yolanda.nohttp.Headers;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.error.NetworkError;
import com.yolanda.nohttp.error.NotFoundCacheError;
import com.yolanda.nohttp.error.TimeoutError;
import com.yolanda.nohttp.error.URLError;
import com.yolanda.nohttp.error.UnKnownHostError;
import com.yolanda.nohttp.rest.JsonObjectRequest;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;
import com.yolanda.nohttp.rest.StringRequest;
import com.youmi.tt.config.Config;
import com.youmi.tt.utils.CommonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.ProtocolException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 网络请求
 * Created by hx on 2016/11/23.
 */
public abstract class BaseRequst {


    protected final String TAG = "BaseRequst";

    protected IBaseView view;
    protected RequestQueue requestQueue;
    protected Request<JSONObject> request;

    protected boolean show_error;    //toast 网络错误
    protected boolean cancel = true; //cancel上次请求 默认true

    protected int url_type;
    protected int load_type;
    protected Bundle bundle;

    public BaseRequst(IBaseView view) {
        this(view, false);
    }

    public BaseRequst(IBaseView view, boolean show_error) {
        this(view, show_error, true);
    }

    public BaseRequst(IBaseView view, boolean show_error, boolean cancel) {
        this.view = view;
        this.show_error = show_error;
        this.cancel = cancel;
        this.requestQueue = NoHttp.newRequestQueue();
    }


    public boolean isCancel() {
        return cancel;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }

    public void reqData(final int url_type) {
        reqData(url_type, -1);
    }

    public void reqDataReTry() {
        reqData(url_type, load_type, bundle);
    }


    public void reqData(final int url_type, final int load_type, final Bundle... bundle) {

        this.url_type = url_type;
        this.load_type = load_type;
        final Bundle bundle1 = bundle.length > 0 ? bundle[0] : null;
        this.bundle = bundle1;

        if (cancel && request != null) {
            request.cancel();
        }

        if (cancel && request != null) {
            request.cancel();
        }

        if (view.getContext() != null && !view.getContext().isFinishing()) {
            view.showLoadingUI(url_type, load_type);
        }

        String url = view.getUrl(url_type);
        Map<String, String> params = view.getParams(url_type, load_type, bundle1);

        if (params == null) {
            params = new HashMap<>();
        }
        onBindParams(url, params, request);

        request = new JSONObejctRequest(url, RequestMethod.POST);
        request.setConnectTimeout(8000);


        JsonResponse listener = new JsonResponse() {
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                try {
                    int responseCode = response.getHeaders().getResponseCode();
                    JSONObject jsonObject = response.get();

                    if (responseCode >= 400 || jsonObject == null || jsonObject.length() <= 0) {
                        CommonUtils.log("responseCode = " + responseCode,TAG);
                        String error = "服务器错误" + (Config.DEBUG_LOG ? responseCode : "");

                        if (error != null){
                            CommonUtils.toast(view.getContext(),error);
                        }

                        view.hideLoadingUI(url_type, load_type, false);
                        view.onError(url_type, load_type, error);

                    } else {

                        if ( (view.getContext() != null && !view.getContext().isFinishing())) {
                            view.hideLoadingUI(url_type, load_type, true);
                            onSuccess(jsonObject, url_type, load_type, bundle1);

                        }
                    }

                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int what, Response<JSONObject> response) {

                String error = null;

                try {//修改  需捕获异常，当网络很差差或者无网络报错
                    Exception exception = response.getException();
                    CommonUtils.log("code = " + response.getHeaders().getResponseCode() + ", msg = " + exception.getMessage(),TAG);

                    if (exception instanceof NetworkError) {// 网络不好
                        error = "没有网络";
                    } else if (exception instanceof TimeoutError) {// 请求超时
                        error = "网络请求超时";
                    } else if (exception instanceof UnKnownHostError) {// 找不到服务器
                        error = "找不到服务器";
                    } else if (exception instanceof URLError) {// URL是错的
                        error = "网址错误";
                    } else if (exception instanceof NotFoundCacheError) {
                        // 这个异常只会在仅仅查找缓存时没有找到缓存时返回
                        error = "没有发现缓存";
                    } else if (exception instanceof ProtocolException) {
                        error = "系统不支持的请求方式";
                    } else {
                        error = "请求超时 ";
                    }


                    if (error != null){
                        CommonUtils.toast(view.getContext(),error);
                    }

                    view.hideLoadingUI(url_type, load_type, false);
                    view.onError(url_type, load_type, error);

                    exception.printStackTrace();


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        requestQueue.add(url_type, request, listener);
    }


    protected abstract void onSuccess(JSONObject jsonObject, int url_type, int load_type, Bundle bundle);

    // 统一绑定常用参数
    public void onBindParams(String url, Map<String, String> params, Request<?> request) {


        Iterator<Map.Entry<String, String>> it = params.entrySet().iterator();
        CommonUtils.log("params url = " + url, TAG);
        while (it.hasNext()) {

            Map.Entry<String, String> entry1 = it.next();
            CommonUtils.log("params " + entry1.getKey() + " = " + entry1.getValue(), TAG);
            if (entry1.getValue() != null) {
                request.add(entry1.getKey(), entry1.getValue());
            }
        }
    }

    public interface IBaseView {

        public Activity getContext();

        public String getUrl(int url_type);

        public Map<String, String> getParams(int url_type, int load_type, Bundle bundle);


        public void onError(int url_type, int load_type, String error);

        public void showLoadingUI(int url_type, int load_type);

        public void hideLoadingUI(int url_type, int load_type, boolean success);


    }


    public static class JSONObejctRequest extends JsonObjectRequest {

        private final String tag;

        public JSONObejctRequest(String url, RequestMethod requestMethod, String... tag) {
            super(url,requestMethod);
            this.tag = tag.length > 0 ? tag[0] : "BaseRequest";
        }

        @Override
        public JSONObject parseResponse(Headers responseHeaders, byte[] responseBody) throws Throwable {
            JSONObject jsonObject = null;
            String jsonStr = StringRequest.parseResponseString(responseHeaders, responseBody);

            //CommonUtils.log("data = " + jsonStr, tag);
            if (!TextUtils.isEmpty(jsonStr)) {

                try {
                    jsonObject = new JSONObject(jsonStr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (jsonObject == null) {
                try {
                    jsonObject = new JSONObject("{}");
                } catch (JSONException e) {
                }
            }
            return jsonObject;
        }
    }

    public static abstract class JsonResponse implements OnResponseListener<JSONObject> {

        @Override
        public void onStart(int what) {

        }

        @Override
        public void onFinish(int what) {

        }

    }


}
