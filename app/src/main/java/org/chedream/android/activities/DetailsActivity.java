package org.chedream.android.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import org.chedream.android.R;
import org.chedream.android.fragments.DetailsFragment;
import org.chedream.android.model.Dream;


public class DetailsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Dream dream = getIntent().getParcelableExtra(DetailsFragment.ARG_SECTION_NUMBER);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, DetailsFragment.getInstance(dream))
                    .commit();
        }
    }
}
