package org.chedream.android.activities;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;

import org.chedream.android.R;
import org.chedream.android.fragments.ContactsFragment;
import org.chedream.android.fragments.DreamsFragment;
import org.chedream.android.fragments.FaqFragment;
import org.chedream.android.fragments.NavigationDrawerFragment;

import static org.chedream.android.helpers.Const.Navigation.ALL_DREAMS;
import static org.chedream.android.helpers.Const.Navigation.CONTACTS;
import static org.chedream.android.helpers.Const.Navigation.FAQ;
import static org.chedream.android.helpers.Const.Navigation.FAVOURITE_DREAMS;
import static org.chedream.android.helpers.Const.Navigation.PROFILE;

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    private static final String TAG = MainActivity.class.getName();
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment;
        final String fragmentReplaceTag;
        switch (position) {
            case PROFILE:
                Log.i(TAG, "going into profile activity");
                Intent intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
                return;
            case ALL_DREAMS:
                fragment = DreamsFragment.newInstance(position);
                fragmentReplaceTag = DreamsFragment.class.getName();
                break;
            case FAVOURITE_DREAMS:
                fragment = DreamsFragment.newInstance(position);
                fragmentReplaceTag = "FavoriteDreams";
                break;
            case FAQ:
                fragment = FaqFragment.newInstance(position);
                fragmentReplaceTag = FaqFragment.class.getName();
                break;
            case CONTACTS:
                fragment = ContactsFragment.newInstance(position);
                fragmentReplaceTag = ContactsFragment.class.getName();
                break;
            default:
                return;
        }
        if (fragmentManager.findFragmentByTag(fragmentReplaceTag) == null) {
            Log.i(TAG, "we are into fragment change block");
            Log.i(TAG, fragmentReplaceTag);
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment, fragmentReplaceTag)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();
        }
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case ALL_DREAMS:
                mTitle = getString(R.string.title_section_dreams);
                break;
            case FAVOURITE_DREAMS:
                mTitle = getString(R.string.title_section_favo_dreams);
                break;
            case FAQ:
                mTitle = getString(R.string.title_section_faq);
                break;
            case CONTACTS:
                mTitle = getString(R.string.title_section_contacts);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.global, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }
}
