package org.chedream.android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.facebook.CallbackManager;
import com.vk.sdk.VKUIHelper;

import org.chedream.android.R;
import org.chedream.android.fragments.LoginFragment;

public class ProfileActivity extends ActionBarActivity {
    public static CallbackManager sCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        VKUIHelper.onCreate(this);
        sCallbackManager = CallbackManager.Factory.create();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container_profile, new LoginFragment())
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        VKUIHelper.onResume(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        sCallbackManager.onActivityResult(requestCode, resultCode, data);
        VKUIHelper.onActivityResult(this, requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        VKUIHelper.onDestroy(this);
    }
}
