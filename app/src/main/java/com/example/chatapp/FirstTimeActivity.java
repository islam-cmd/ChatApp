package com.example.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.sinch.android.rtc.ClientRegistration;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.SinchClientListener;
import com.sinch.android.rtc.SinchError;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallClient;
import com.sinch.android.rtc.calling.CallClientListener;
import com.sinch.android.rtc.calling.CallListener;
import com.sinch.android.rtc.video.VideoCallListener;
import com.sinch.android.rtc.video.VideoController;
import com.sinch.android.rtc.video.VideoFrame;
//import com.sinch.android.rtc.video.VideoFrameListener;
import java.util.List;
import com.sinch.android.rtc.video.VideoFrame;

import androidx.appcompat.app.AppCompatActivity;

public class FirstTimeActivity extends AppCompatActivity {

    private static final String APP_KEY = "2b7f52f2-8d67-423a-91ac-aedbac787a2e";
    private static final String APP_SECRET = "shuCuCqMFkaAOiTTHNiuRQ==";
    //    private static final String ENVIRONMENT = "sandbox.sinch.com";
    private static final String ENVIRONMENT = "clientapi.sinch.com";

    private Call call;
    private TextView callState;
    private SinchClient sinchClient;
    private Button button;
    private String callerId;
    private String recipientId;
    FrameLayout local;
    FrameLayout remote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time);
        local = findViewById(R.id.Local);
        remote = findViewById(R.id.Remote);
        Intent intent = getIntent();
        final String id = intent.getStringExtra("userid");
        android.content.Context context = this.getApplicationContext();
        sinchClient = Sinch.getSinchClientBuilder().context(context)
                .applicationKey(APP_KEY)
                .applicationSecret(APP_SECRET)
                .environmentHost(ENVIRONMENT)
                .userId(FirebaseAuth.getInstance().getCurrentUser().getUid().trim())
                .build();
// Specify the client capabilities.
// At least one of the calling and video capabilities should be enabled.
        sinchClient.setSupportCalling(true);
        sinchClient.setSupportManagedPush(true);
// or

//        sinchClient.setSupportActiveConnectionInBackground(true);
//        sinchClient.startListeningOnActiveConnection();


        sinchClient.start();
        call = sinchClient.getCallClient().callUser(id);

        call.addCallListener(new VideoCallListener() {
            @Override
            public void onCallProgressing(Call call) {

            }

            @Override
            public void onCallEstablished(Call call) {

            }

            @Override
            public void onCallEnded(Call call) {

            }

            @Override
            public void onShouldSendPushNotification(Call call, List<PushPair> list) {

            }

//            @Override
//            public void onVideoTrackAdded(Call call) {
//                VideoController vc =getVideoController();
//                View myPreview = vc.getLocalView();
//                View remoteView = vc.getRemoteView();
//                local.addView(myPreview);
//                remote.addView(remoteView);
//            }

            @Override
            public void onVideoTrackAdded(Call call) {

            }

            @Override
            public void onVideoTrackPaused(Call call) {

            }

            @Override
            public void onVideoTrackResumed(Call call) {

            }
        });
    }

    //    public void onVideoTrackAdded(Call call) {
//        // Get a reference to your SinchClient, in the samples this is done through the service interface:
//        VideoController vc = getSinchServiceInterface().getVideoController();
//        View myPreview = vc.getLocalView();
//        View remoteView = vc.getRemoteView();
//        local.addView(myPreview);
//        remote.addView(remoteView);
//
//        // Add the views to your view hierarchy
//
//    }
    public void onCallEnded(Call call) {
        // Remove Sinch video views from your view hierarchy
    }


}