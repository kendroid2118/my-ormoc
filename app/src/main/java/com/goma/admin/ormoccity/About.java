package com.goma.admin.ormoccity;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;



/**
 * Created by User on 09/22/2017.
 */

public class About extends Fragment{
    WebView web;
//    TextView ml;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("About");

    }



    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_about, container, false);
        web = (WebView) v.findViewById(R.id.wbabout);
        //wb.setWebViewClient(new About.MyBrowser());
        web.loadUrl("file:///asset/about.html");
        web.getSettings().setJavaScriptEnabled(true);

        return v;
    }
    private class MyBrowser extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            wb.loadUrl(url);
            return true;
        }

    }

}

