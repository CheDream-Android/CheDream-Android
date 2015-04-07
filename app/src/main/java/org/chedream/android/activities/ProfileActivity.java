package org.chedream.android.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;

import com.facebook.CallbackManager;
import com.google.android.gms.common.api.GoogleApiClient;
import com.vk.sdk.VKUIHelper;

import org.chedream.android.R;
import org.chedream.android.fragments.LoginFragment;
import org.chedream.android.fragments.ProfileFragment;
import org.chedream.android.helpers.Const;

public class ProfileActivity extends BaseSocialActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        if (savedInstanceState == null) {
            if(sharedPrefs.getBoolean(Const.SP_LOGIN_STATUS, false)) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_container_profile, new ProfileFragment())
                        .commit();
            } else {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_container_profile, new LoginFragment())
                        .commit();
            }
        }
    }
}
