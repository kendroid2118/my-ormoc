package com.goma.admin.ormoccity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class Contact extends Fragment {
    EditText sub, msg;
    ImageButton send, clear;
    String to = "kendroid2k18@gmail.com";
    private AdView mAdView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_contact, container, false);
        getActivity().setTitle("Contact Us");

        mAdView = v.findViewById(R.id.adviewContact);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        sub = (EditText)v.findViewById(R.id.et_subject);
        msg = (EditText)v.findViewById(R.id.et_message);
        clear = (ImageButton) v.findViewById(R.id.imgbtnclear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sub.setText("");
                msg.setText("");
            }
        });

        send = (ImageButton) v.findViewById(R.id.imgbtnsend);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!msg.getText().toString().isEmpty()) {

                    String m = msg.getText().toString();
                    String s = sub.getText().toString();

                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{to});
                    intent.putExtra(Intent.EXTRA_SUBJECT, s);
                    intent.putExtra(Intent.EXTRA_TEXT, m);

                    intent.setType("message/rfc822");

                    startActivity(Intent.createChooser(intent, "Select Email app"));
                }
                else {
                    Toast t1 = Toast.makeText(getContext(), "Please enter your message.", Toast.LENGTH_SHORT);
                    t1.setGravity(Gravity.CENTER,0,0);
                    t1.show();
                    msg.requestFocus();
                }
                sub.setText("");
                msg.setText("");
            }
        });


        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


}
