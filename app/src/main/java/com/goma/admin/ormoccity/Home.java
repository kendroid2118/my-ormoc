package com.goma.admin.ormoccity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

//import application.goma.admin.ormoccity.R;

//import com.google.firebase.iid.FirebaseInstanceId;
//import com.goma.admin.myormoccity.R;
//import com.*;


public class Home extends AppCompatActivity
        implements NetworkChangeReceiver.ConnectionChangeCallback,NavigationView.OnNavigationItemSelectedListener {
    WebView webhome;
    String back="";
    private InterstitialAd mInterstitialAd;
    private AdView mAdView;
    String prev = "";
//    ProgressBar progressBar;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        FirebaseInstanceId.getInstance().getToken();
//        FirebaseMessaging.getInstance().subscribeToTopic("test");



        IntentFilter intentFilter = new
                IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");

        NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver();

        registerReceiver(networkChangeReceiver, intentFilter);

        networkChangeReceiver.setConnectionChangeCallback(this);

//        new SendPostRequest().execute();

        mAdView = findViewById(R.id.adView_home);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        webhome = findViewById(R.id.wbhome);
        webhome.setWebViewClient(new Browser_home());
        webhome.setWebChromeClient(new MyChrome());
        Intent in = getIntent();
        back = in.getStringExtra("back");
        Fragment fragment = null;

//        isInternetConnected(Home.this);
        if(isInternetConnected(Home.this) == false){
            webhome.loadUrl("file:///android_asset/default.html");
        }else {
            if (back == "home") {
                    webhome.loadUrl("http://myormoc.com/home.php");
                back = "";
            } else if (back == "news") {
                    webhome.loadUrl("file:///android_asset/default.html");
                back = "";
            } else if (back == "home") {
                    webhome.loadUrl("http://myormoc.com/home.php");
            } else if (back == "events") {
                    webhome.loadUrl("http://myormoc.com/events.php");
                back = "";
            } else if (back == "dest") {
                    webhome.loadUrl("http://myormoc.com/destinations.php");
                back = "";
            } else if (back == "cult") {
                    webhome.loadUrl("http://myormoc.com/culturals.php");
                back = "";
            } else if (back == "hotels") {
                    webhome.loadUrl("http://myormoc.com/hotels.php");
                back = "";
            } else if (back == "resto") {
                    webhome.loadUrl("http://myormoc.com/restaurants.php");
                back = "";
            } else if (back == "prod") {
                    webhome.loadUrl("http://myormoc.com/products.php");
                back = "";
            } else if (back == "malls") {
                    webhome.loadUrl("http://myormoc.com/malls.php");
                back = "";
            } else if (back == "church") {
                    webhome.loadUrl("http://myormoc.com/churches.php");
                back = "";
            } else if (back == "banks") {
                    webhome.loadUrl("http://myormoc.com/banks.php");
                back = "";
            } else if (back == "spas") {
                    webhome.loadUrl("http://myormoc.com/spa_wellness.php");
                back = "";
            } else if (back == "hosp") {
                    webhome.loadUrl("http://myormoc.com/hospitals.php");
                back = "";
            } else if (back == "ucont") {
                    webhome.loadUrl("http://myormoc.com/useful_contacts.php");
                back = "";
            } else if (back == "history") {
                webhome.loadUrl("file:///android_asset/History.html");
                back = "";
            }else if (back == "transpo") {
                webhome.loadUrl("http://myormoc.com/travel.html");
                back = "";
            }else if (back == "about") {
                webhome.loadUrl("file:///android_asset/about.html");
                back = "";
            } else if (back == "contact") {
//                    webhome.loadUrl("http://myormoc.com/contact.php");
//                Intent i = new Intent(this,Contact.class);
//                startActivity(i);
                fragment = new Contact();
                back = "";
            } else if (back == "ann") {
                    webhome.loadUrl("http://myormoc.com/announcements.php");
                back = "";
            } else if (back == "live") {
                    webhome.loadUrl("http://myormoc.com/live_stream.php");
                back = "";
            } else if (back == "peso") {
                    webhome.loadUrl("http://myormoc.com/jobs.php");
                back = "";
            }
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.setCustomAnimations(R.anim.fab_slide_in_from_left,R.anim.fab_slide_out_to_right);
            ft.replace(R.id.content_main, fragment);
            ft.commit();
        }


        //webhome.loadUrl("http://myormoc.dx.am/home.php");
        webhome.getSettings().setJavaScriptEnabled(true);
        webhome.getSettings().setDisplayZoomControls(false);
        webhome.getSettings().setBuiltInZoomControls(false);
        webhome.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView viewx, String urlx) {
                if (urlx.startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_DIAL,
                            Uri.parse(urlx));
                    startActivity(intent);
                }else if(urlx.startsWith("http:") || urlx.startsWith("https:")) {
                    viewx.loadUrl(urlx);
                }
                return true;



//                return true;
            }
        });



//        firebase







        mInterstitialAd = new InterstitialAd(this);
                mInterstitialAd.setAdUnitId("ca-app-pub-8224488979884241/6302230145");
        //this is for sample interstitial ad ;
//        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }


    class Browser_home extends WebViewClient {

        Browser_home() {
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            setTitle(view.getTitle());
//            progressBar.setVisibility(View.GONE);
            super.onPageFinished(view, url);

        }
    }

    private class MyChrome extends WebChromeClient {

        private View mCustomView;
        private WebChromeClient.CustomViewCallback mCustomViewCallback;
        protected FrameLayout mFullscreenContainer;
        private int mOriginalOrientation;
        private int mOriginalSystemUiVisibility;

        MyChrome() {}

        public Bitmap getDefaultVideoPoster()
        {
            if (mCustomView == null) {
                return null;
            }
            return BitmapFactory.decodeResource(getApplicationContext().getResources(), 2130837573);
        }

        public void onHideCustomView()
        {
            ((FrameLayout)getWindow().getDecorView()).removeView(this.mCustomView);
            this.mCustomView = null;
            getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
            setRequestedOrientation(this.mOriginalOrientation);
            this.mCustomViewCallback.onCustomViewHidden();
            this.mCustomViewCallback = null;
        }

        public void onShowCustomView(View paramView, WebChromeClient.CustomViewCallback paramCustomViewCallback)
        {
            if (this.mCustomView != null)
            {
                onHideCustomView();
                return;
            }
            this.mCustomView = paramView;
            this.mOriginalSystemUiVisibility = getWindow().getDecorView().getSystemUiVisibility();
            this.mOriginalOrientation = getRequestedOrientation();
            this.mCustomViewCallback = paramCustomViewCallback;
            ((FrameLayout)getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(-1, -1));
            getWindow().getDecorView().setSystemUiVisibility(3846);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else {
//            super.onBackPressed();
        }


        Fragment fragment = null;
//        int id = item.getItemId();

        if (back == "home") {
            // Handle the camera action
            if(isInternetConnected(Home.this) == false){
                webhome.loadUrl("file:///android_asset/default.html");
            }else{
                webhome.loadUrl("http://myormoc.com/home.php");
            }
            back="";
        }else if (back == "news") {
            // Handle the camera action
            if(isInternetConnected(Home.this) == false){
                webhome.loadUrl("file:///android_asset/default.html");
            }else{
                webhome.loadUrl("http://myormoc.com/news.php");
            }
            back="";
        } else if (back == "home") {
            if(isInternetConnected(Home.this) == false){
                webhome.loadUrl("file:///android_asset/default.html");
            }else{
                webhome.loadUrl("http://myormoc.com/home.php");
            }
            back="";
        } else if (back == "events") {
            if(isInternetConnected(Home.this) == false){
                webhome.loadUrl("file:///android_asset/default.html");
            }else{
                webhome.loadUrl("http://myormoc.com/events.php");
            }
            back="";
        } else if (back == "dest") {
            if(isInternetConnected(Home.this) == false){
                webhome.loadUrl("file:///android_asset/default.html");
            }else{
                webhome.loadUrl("http://myormoc.com/destinations.php");
            }
            back="";
        } else if (back == "cult") {
            if(isInternetConnected(Home.this) == false){
                webhome.loadUrl("file:///android_asset/default.html");
            }else{
                webhome.loadUrl("http://myormoc.com/culturals.php");
            }
            back="";
        } else if (back == "hotels") {
            if(isInternetConnected(Home.this) == false){
                webhome.loadUrl("file:///android_asset/default.html");
            }else{
                webhome.loadUrl("http://myormoc.com/hotels.php");
            }
            back="";
        } else if (back == "resto") {
            if(isInternetConnected(Home.this) == false){
                webhome.loadUrl("file:///android_asset/default.html");
            }else{
                webhome.loadUrl("http://myormoc.com/restaurants.php");
            }
            back="";
        }else if (back == "prod") {
            if(isInternetConnected(Home.this) == false){
                webhome.loadUrl("file:///android_asset/default.html");
            }else{
                webhome.loadUrl("http://myormoc.com/products.php");
            }
            back="";
        }else if (back == "malls") {
            if(isInternetConnected(Home.this) == false){
                webhome.loadUrl("file:///android_asset/default.html");
            }else{
                webhome.loadUrl("http://myormoc.com/malls.php");
            }
            back="";
        }else if (back == "church") {
            if(isInternetConnected(Home.this) == false){
                webhome.loadUrl("file:///android_asset/default.html");
            }else{
                webhome.loadUrl("http://myormoc.com/churches.php");
            }
            back="";
        }else if (back == "banks") {
            if(isInternetConnected(Home.this) == false){
                webhome.loadUrl("file:///android_asset/default.html");
            }else{
                webhome.loadUrl("http://myormoc.com/banks.php");
            }
            back="";
        }else if (back == "spas") {
            if(isInternetConnected(Home.this) == false){
                webhome.loadUrl("file:///android_asset/default.html");
            }else{
                webhome.loadUrl("http://myormoc.com/spa_wellness.php");
            }
            back="";
        }else if (back == "hosp") {
            if(isInternetConnected(Home.this) == false){
                webhome.loadUrl("file:///android_asset/default.html");
            }else{
                webhome.loadUrl("http://myormoc.com/hospitals.php");
            }
            back="";
        }else if (back == "ucont") {
            if(isInternetConnected(Home.this) == false){
                webhome.loadUrl("file:///android_asset/default.html");
            }else{
                webhome.loadUrl("http://myormoc.com/useful_contacts.php");
            }
            back="";
        }else if (back == "about") {
//            if(isInternetConnected(Home.this) == false){
            webhome.loadUrl("file:///android_asset/about.html");
//            }else{
//                webhome.loadUrl("http://myormoc.dx.am/about.php");
//            }
            back="";
        }else if (back == "history") {
//            if(isInternetConnected(Home.this) == false){
            webhome.loadUrl("file:///android_asset/History.html");
//            }else{
//                webhome.loadUrl("http://myormoc.dx.am/about.php");
//            }
            back="";
        }else if (back == "transpo") {
//            if(isInternetConnected(Home.this) == false){
                webhome.loadUrl("http://myormoc.com/travel.html");
//            }else{
//                webhome.loadUrl("http://myormoc.dx.am/about.php");
//            }
            back="";
        }else if (back == "contact") {
            if(isInternetConnected(Home.this) == false){
                webhome.loadUrl("file:///android_asset/default.html");
            }else{
                fragment = new Contact();

            }
            back="";
        }else if (back == "ann") {
            if(isInternetConnected(Home.this) == false){
                webhome.loadUrl("file:///android_asset/default.html");
            }else{
                webhome.loadUrl("http://myormoc.com/announcements.php");
            }
            back="";
        }else if (back == "live") {
            if(isInternetConnected(Home.this) == false){
                webhome.loadUrl("file:///android_asset/default.html");
            }else{
                webhome.loadUrl("http://myormoc.com/live_stream.php");
            }
            back="";
        }else if (back == "peso") {
            if(isInternetConnected(Home.this) == false){
                webhome.loadUrl("file:///android_asset/default.html");
            }else{
                webhome.loadUrl("http://myormoc.com/jobs.php");
            }
            back="";
        }else {
            new AlertDialog.Builder(this)
                    .setTitle("My Ormoc City")
                    .setMessage("Are you sure you want to exit?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // continue with delete

                    Home.this.finish();
//                    System.exit(0);

                }
            })
    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // do nothing
                    if(isInternetConnected(Home.this) == false){
                        webhome.loadUrl("file:///android_asset/default.html");
                    }else{
                        webhome.loadUrl("http://myormoc.com/home.php");
                    }
                    back="";
                }
            })
    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.setCustomAnimations(R.anim.fab_slide_in_from_left,R.anim.fab_slide_out_to_right);
            ft.replace(R.id.content_main, fragment);
            ft.commit();
        }



//        FragmentManager manager = getSupportFragmentManager();
//        FragmentTransaction transaction = manager.beginTransaction();
//        transaction.replace(R.id.content_main, fragment);
//        transaction.commit();

        DrawerLayout drawer2 = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer2.closeDrawer(GravityCompat.START);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        Fragment fragment = null;
        if (id == R.id.nav_events && prev !="contact") {
//            fragment = new Announcements();
            back="ann";
            if(isInternetConnected(Home.this) == false){
                webhome.loadUrl("file:///android_asset/default.html");
            }else{
                webhome.loadUrl("http://myormoc.com/announcements.php");
            }

                        if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
//                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }
            this.setTitle("Announcements");
        }else if(id == R.id.nav_live && prev !="contact"){
            back="live";
            if(isInternetConnected(Home.this) == false){
                webhome.loadUrl("file:///android_asset/default.html");
            }else{
                webhome.loadUrl("http://myormoc.com/live_stream.php");
            }

            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
//                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }
            this.setTitle("Featured Video");
        }else{
            Intent i = new Intent(Home.this, Home.class);
            i.putExtra("back",back);
            startActivity(i);
            prev = "contact";
        }
//        else if (id == R.id.nav_events) {
//            if (mInterstitialAd.isLoaded()) {
//                mInterstitialAd.show();
//            } else {
////                Log.d("TAG", "The interstitial wasn't loaded yet.");
//            }
//
//        }

//        FragmentManager manager = getSupportFragmentManager();
//        FragmentTransaction transaction = manager.beginTransaction();
//        transaction.replace(R.id.content_main, fragment);
//        transaction.commit();


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Fragment fragment = null;
        int id = item.getItemId();
        Intent i = new Intent(this,Home.class);
        FragmentTransaction ft;
        ft = this.getSupportFragmentManager().beginTransaction();
//        Fragment ft = getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT);



        if (id == R.id.nav_news && !prev.equals("contact")) {
            // Handle the camera action

//            fragment = new News();
            if(isInternetConnected(Home.this) == false){
                webhome.loadUrl("file:///android_asset/default.html");
            }else{
                webhome.loadUrl("http://myormoc.com/news.php");
            }

            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
//                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }

            back="news";
            prev = "";
            this.setTitle("News & Updates");
        } else if (id == R.id.nav_home && !prev.equals("contact")) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
//                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }
//            fragment = new Events();
            if(isInternetConnected(Home.this) == false){
                webhome.loadUrl("file:///android_asset/default.html");
            }else{
                webhome.loadUrl("http://myormoc.com/home.php");
            }
            back="home";
            prev = "";
            this.setTitle("Home");
        }else if (id == R.id.nav_events && !prev.equals("contact")) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
//                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }
//            fragment = new Events();
            if(isInternetConnected(Home.this) == false){
                webhome.loadUrl("file:///android_asset/default.html");
            }else{
                webhome.loadUrl("http://myormoc.com/events.php");
            }
            back="events";
            prev = "";
            this.setTitle("Events & Activities");
        } else if (id == R.id.nav_destinations && !prev.equals("contact")) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
//                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }
//            fragment = new Destinations();
            if(isInternetConnected(Home.this) == false){
                webhome.loadUrl("file:///android_asset/default.html");
            }else{
                webhome.loadUrl("http://myormoc.com/destinations.php");
            }
            back="dest";
            prev = "";
            this.setTitle("Destinations");
        } else if (id == R.id.nav_culturals && !prev.equals("contact")) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
//                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }
//            fragment = new Cultural();
            if(isInternetConnected(Home.this) == false){
                webhome.loadUrl("file:///android_asset/default.html");
            }else{
                webhome.loadUrl("http://myormoc.com/culturals.php");
            }
            back="cult";
            prev = "";
            this.setTitle("Cultural Heritage");
        } else if (id == R.id.nav_hotels && !prev.equals("contact")) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
//                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }
//            fragment = new Hotels();
            if(isInternetConnected(Home.this) == false){
                webhome.loadUrl("file:///android_asset/default.html");
            }else{
                webhome.loadUrl("http://myormoc.com/hotels.php");
            }
            back="hotels";
            prev = "";
            this.setTitle("Hotels & Inns");
        } else if (id == R.id.nav_resto && !prev.equals("contact")) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
//                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }
//            fragment = new Restaurants();
            if(isInternetConnected(Home.this) == false){
                webhome.loadUrl("file:///android_asset/default.html");
            }else{
                webhome.loadUrl("http://myormoc.com/restaurants.php");
            }
            back="resto";
            prev = "";
            this.setTitle("Restaurants & Diners");
        }else if (id == R.id.nav_products && !prev.equals("contact")) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
//                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }
//            fragment = new Products();
            if(isInternetConnected(Home.this) == false){
                webhome.loadUrl("file:///android_asset/default.html");
            }else{
                webhome.loadUrl("http://myormoc.com/products.php");
            }
            back="prod";
            prev = "";
            this.setTitle("Products & Delicacies");
        }else if (id == R.id.nav_malls && !prev.equals("contact")) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
//                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }
//            fragment = new Products();
            if(isInternetConnected(Home.this) == false){
                webhome.loadUrl("file:///android_asset/default.html");
            }else{
                webhome.loadUrl("http://myormoc.com/malls.php");
            }
            back="malls";
            prev = "";
            this.setTitle("Malls & Groceries");
        }else if (id == R.id.nav_church && !prev.equals("contact")) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
//                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }
//            fragment = new Churches();
            if(isInternetConnected(Home.this) == false){
                webhome.loadUrl("file:///android_asset/default.html");
            }else{
                webhome.loadUrl("http://myormoc.com/churches.php");
            }

            back="church";
            prev = "";
            this.setTitle("Churches");
        }else if (id == R.id.nav_banks && !prev.equals("contact")) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
//                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }
//            fragment = new Banks();
            if(isInternetConnected(Home.this) == false){
                webhome.loadUrl("file:///android_asset/default.html");
            }else{
                webhome.loadUrl("http://myormoc.com/banks.php");
            }
            back="banks";
            prev = "";
            this.setTitle("Banks");
        }else if (id == R.id.nav_spas && !prev.equals("contact")) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
//                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }
//            fragment = new Spas();
            if(isInternetConnected(Home.this) == false){
                webhome.loadUrl("file:///android_asset/default.html");
            }else{
                webhome.loadUrl("http://myormoc.com/spa_wellness.php");
            }
            back="spas";
            prev = "";
            this.setTitle("Spa & Wellness");
        }else if (id == R.id.nav_clinics && !prev.equals("contact")) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
//                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }
//            fragment = new Hospitals();
            if(isInternetConnected(Home.this) == false){
                webhome.loadUrl("file:///android_asset/default.html");
            }else{
                webhome.loadUrl("http://myormoc.com/hospitals.php");
            }
            back="hosp";
            prev = "";
            this.setTitle("Hospitals & Clinics");
        }else if (id == R.id.nav_ucontacts && !prev.equals("contact")) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
//                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }
//            fragment = new Directory();
            if(isInternetConnected(Home.this) == false){
                webhome.loadUrl("file:///android_asset/default.html");
            }else{
                webhome.loadUrl("http://myormoc.com/useful_contacts.php");
            }
            back="ucont";
            prev = "";
            this.setTitle("Useful Contacts");
        }else if (id == R.id.nav_peso && !prev.equals("contact")) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
//                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }
//            fragment = new Directory();
            if(isInternetConnected(Home.this) == false){
                webhome.loadUrl("file:///android_asset/default.html");
            }else{
                webhome.loadUrl("http://myormoc.com/jobs.php");
            }
            back="peso";
            prev = "";
            this.setTitle("Job Posting");
        }else if (id == R.id.nav_history && !prev.equals("contact")) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {

            }

            webhome.loadUrl("file:///android_asset/History.html");

            prev = "";
            back="history";
            this.setTitle("History");
        }else if (id == R.id.nav_transpo && !prev.equals("contact")) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {

            }

            webhome.loadUrl("http://myormoc.com/travel.html");

            prev = "";
            back="transpo";
            this.setTitle("Travel Information");
        }else if (id == R.id.nav_about && !prev.equals("contact")) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {

            }

            webhome.loadUrl("file:///android_asset/about.html");

            prev = "";
            back="about";
            this.setTitle("About");
        }else if (id == R.id.nav_contact) {
//            if (mInterstitialAd.isLoaded()) {
//                mInterstitialAd.show();
//            } else {
//            }
            fragment = new Contact();
//            Intent i = new Intent(this, Home.class);
//            startActivity(i);
            prev = "contact";
        }

        else if(prev.equals("contact")){

//            ft = getSupportFragmentManager().beginTransaction();
//            ft.remove(fragment);
//            ft.commit();
            startActivity(i);
            prev = "contact";
        }

        if (fragment != null) {
            ft = getSupportFragmentManager().beginTransaction();
//            ft.setCustomAnimations(R.anim.fab_slide_in_from_left,R.anim.fab_slide_out_to_right);
            ft.replace(R.id.content_main, fragment);
            ft.commit();
//            fragment = new Contact();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public static boolean isInternetConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }


    @Override
    public void onConnectionChange(boolean isConnected) {
        if(isConnected){
            webhome.loadUrl("http://myormoc.com/home.php");
            // will be called when internet is back
        }
        else{
            webhome.loadUrl("file:///android_asset/default.html");
            // will be called when internet is gone.
        }
    }



}



