package com.dds.webrtclib;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Parcelable;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.dds.webrtclib.utils.PermissionUtil;

import org.webrtc.EglBase;
import org.webrtc.MediaStream;
import org.webrtc.RendererCommon;
import org.webrtc.SurfaceViewRenderer;
import org.webrtc.VideoRenderer;

/**
 * 单聊界面
 * 1. 一对一视频通话
 * 2. 一对一语音通话
 */
public class ChatSingleActivity extends AppCompatActivity implements IWebRTCHelper {
    private SurfaceViewRenderer local_view;
    private SurfaceViewRenderer remote_view;
    private ProxyRenderer localRender;
    private ProxyRenderer remoteRender;
    private EglBase rootEglBase;

    private WebRTCHelper helper;
    private ChatSingleFragment chatSingleFragment;

    private String signal;
    private Parcelable[] iceServers;
    private String room;
    private boolean videoEnable;
    private boolean isSwappedFeeds;

    public static void openActivity(Activity activity, String signal, MyIceServer[] iceServers, String room, boolean videoEnable) {
        Intent intent = new Intent(activity, ChatSingleActivity.class);
        intent.putExtra("signal", signal);
        intent.putExtra("ice", iceServers);
        intent.putExtra("room", room);
        intent.putExtra("videoEnable", videoEnable);
        activity.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                        | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wr_activity_chat_single);
        initVar();
    }


    private void initVar() {
        Intent intent = getIntent();
        signal = intent.getStringExtra("signal");
        iceServers = intent.getParcelableArrayExtra("ice");
        room = intent.getStringExtra("room");
        videoEnable = intent.getBooleanExtra("videoEnable", false);
        chatSingleFragment = new ChatSingleFragment();
        replaceFragment(chatSingleFragment, videoEnable);

        if (videoEnable) {
            local_view = findViewById(R.id.local_view_render);
            remote_view = findViewById(R.id.remote_view_render);
            local_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setSwappedFeeds(!isSwappedFeeds);
                }
            });
            rootEglBase = EglBase.create();
            // 本地图像初始化
            local_view.init(rootEglBase.getEglBaseContext(), null);
            local_view.setScalingType(RendererCommon.ScalingType.SCALE_ASPECT_FIT);
            local_view.setZOrderMediaOverlay(true);
            localRender = new ProxyRenderer();
            //远端图像初始化
            remote_view.init(rootEglBase.getEglBaseContext(), null);
            remote_view.setScalingType(RendererCommon.ScalingType.SCALE_ASPECT_FIT);
            remoteRender = new ProxyRenderer();
            setSwappedFeeds(true);
        }

        startCall();

    }

    private void setSwappedFeeds(boolean isSwappedFeeds) {
        this.isSwappedFeeds = isSwappedFeeds;
        localRender.setTarget(isSwappedFeeds ? remote_view : local_view);
        remoteRender.setTarget(isSwappedFeeds ? local_view : remote_view);
    }

    private void startCall() {
        if (!PermissionUtil.isNeedRequestPermission(ChatSingleActivity.this)) {
            helper = new WebRTCHelper(this, ChatSingleActivity.this, iceServers);
            helper.initSocket(signal, room, videoEnable);
        }

    }

    private void replaceFragment(Fragment fragment, boolean videoEnable) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("videoEnable", videoEnable);
        fragment.setArguments(bundle);
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.wr_container, fragment)
                .commit();

    }


    @Override  // 屏蔽返回键
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return keyCode == KeyEvent.KEYCODE_BACK || super.onKeyDown(keyCode, event);
    }

    @Override
    public void onSetLocalStream(MediaStream stream, String socketId) {
        if (videoEnable) {
            stream.videoTracks.get(0).setEnabled(true);
            stream.videoTracks.get(0).addRenderer(new VideoRenderer(localRender));
        }

    }

    @Override
    public void onAddRemoteStream(MediaStream stream, String socketId) {
        if (videoEnable) {
            setSwappedFeeds(false);
            stream.videoTracks.get(0).setEnabled(true);
            stream.videoTracks.get(0).addRenderer(new VideoRenderer(remoteRender));
        }
    }

    @Override
    public void onCloseWithId(String socketId) {
        exit();
    }

    // 切换摄像头
    public void switchCamera() {
        helper.switchCamera();
    }

    // 挂断
    public void hangUp() {
        exit();
        this.finish();
    }

    // 静音
    public void toggleMic(boolean enable) {
        helper.toggleMute(enable);
    }

    // 扬声器
    public void toggleSpeaker(boolean enable) {
        helper.toggleSpeaker(enable);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        exit();
    }

    private void exit() {
        helper.exitRoom();
        if (localRender != null) {
            localRender.setTarget(null);
        }

        if (local_view != null) {
            local_view.release();
            local_view = null;
        }
        if (remote_view != null) {
            remote_view.release();
            remote_view = null;
        }
        this.finish();

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        for (int i = 0; i < permissions.length; i++) {
            Log.i(WebRTCHelper.TAG, "[Permission] " + permissions[i] + " is " + (grantResults[i] == PackageManager.PERMISSION_GRANTED ? "granted" : "denied"));
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                finish();
                break;
            }
        }

        helper = new WebRTCHelper(this, ChatSingleActivity.this, iceServers);
        helper.initSocket(signal, room, videoEnable);


    }
}
