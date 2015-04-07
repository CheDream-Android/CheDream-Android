package org.chedream.android.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;

import com.facebook.CallbackManager;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;
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

    public GoogleApiClient getGoogleApiClient() {
        return mGoogleApiClient;
    }

    public void initGoogleApiClient(ConnectionCallbacks callbacks, OnConnectionFailedListener listener) {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .addConnectionCallbacks(callbacks)
                .addOnConnectionFailedListener(listener)
                .build();
    }

    public CallbackManager getFbCallbackManager() {
        return mFbCallbackManager;
    }
}
