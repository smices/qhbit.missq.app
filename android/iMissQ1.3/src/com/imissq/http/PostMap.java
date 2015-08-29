package com.imissq.http;

import android.text.TextUtils;

import com.lidroid.xutils.http.RequestParams;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PostMap extends HashMap<String, String> {

    public List<NameValuePair> getNVPListFromMap() {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        for (String key : this.keySet()) {
            if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(this.get(key))) {
                BasicNameValuePair bnvp = new BasicNameValuePair(key, this.get(key));
                params.add(bnvp);
            } else {

            }

        }
        return params;
    }

    public RequestParams getRequestParams() {
        RequestParams params = new RequestParams();
        for (String key : this.keySet()) {
            if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(this.get(key))) {
                params.addBodyParameter(key, this.get(key));
            } else {

            }

        }
        return params;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        StringBuffer sb = new StringBuffer();
        for (String key : this.keySet()) {
            sb.append("key:" + key + " ,value:" + this.get(key) + ",\n");
        }
        return sb.toString();
    }
}
