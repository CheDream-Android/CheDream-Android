package org.chedream.android.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;

import com.facebook.CallbackManager;
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

    public void setLoginStatus(boolean isLogged, int socialNetworkId) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(this);
        sp.edit().putBoolean(Const.SP_LOGIN_STATUS, isLogged)
                .putInt(Const.SP_SOCIAL_NETWORK_ID, socialNetworkId).apply();
    }

    public void showAllertDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setCancelable(false)
                .setNegativeButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
