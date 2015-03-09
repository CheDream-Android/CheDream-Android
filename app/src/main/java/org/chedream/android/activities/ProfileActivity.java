package org.chedream.android.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import org.chedream.android.R;
import org.chedream.android.fragments.LoginFragment;
import org.chedream.android.fragments.ProfileFragment;

public class ProfileActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container_profile, new LoginFragment())
                    .commit();
        }
    }
}
