package org.chedream.android.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

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
            if (sharedPrefs.getBoolean(Const.SP_LOGIN_STATUS, false)) {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
