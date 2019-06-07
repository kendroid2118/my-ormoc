package com.goma.admin.ormoccity;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

//import com.google.android.gms.ads.internal.gmsg.HttpClient;

//import okhttp3.FormBody;
//import okhttp3.OkHttpClient;
//import okhttp3.RequestBody;
//import okhttp3.Request;


public class FirebaseIdService extends FirebaseInstanceIdService {
    private static final String TAG = "FirebaseIDService";
    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + token);
        registerToken(token);
        // TODO: Implement this method to send any registration to your app's servers.

    }
    private void registerToken(String token) {



    }

}
