package org.chedream.android.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKSdk;
import com.vk.sdk.VKSdkListener;
import com.vk.sdk.VKUIHelper;

import org.chedream.android.R;
import org.chedream.android.helpers.Const;

public abstract class BaseSocialActivity extends ActionBarActivity {

    private GoogleApiClient mGoogleApiClient;
    private CallbackManager mFbCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        VKUIHelper.onCreate(this);

        mFbCallbackManager = CallbackManager.Factory.create();

    }

    @Override
    protected void onResume() {
        super.onResume();
        VKUIHelper.onResume(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mFbCallbackManager.onActivityResult(requestCode, resultCode, data);
        VKUIHelper.onActivityResult(this, requestCode, resultCode, data);
        if (requestCode == Const.SocialNetworks.GPLUS_REQUEST_CODE_RESOLVE_ERR && resultCode == RESULT_OK) {
            getGoogleApiClient().connect();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        VKUIHelper.onDestroy(this);
    }

    public CallbackManager getFbCallbackManager() {
        return mFbCallbackManager;
    }

    public GoogleApiClient getGoogleApiClient() {
        return mGoogleApiClient;
    }

    public void initVKSdk(VKSdkListener sdkListener) {
        VKSdk.initialize(
                sdkListener,
                getResources().getString(R.string.vkontakte_app_id),
                VKAccessToken.tokenFromSharedPreferences(this, "VK_ACCESS_TOKEN"));
    }

    public void initGoogleApiClient(ConnectionCallbacks callbacks, OnConnectionFailedListener listener) {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .addConnectionCallbacks(callbacks)
                .addOnConnectionFailedListener(listener)
                .build();
    }
}