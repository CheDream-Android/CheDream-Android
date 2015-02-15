package org.chedream.android.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import org.chedream.android.R;
import org.chedream.android.fragments.DetailsFragment;

public class DetailsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new DetailsFragment())
                    .commit();
        }
    }
}
