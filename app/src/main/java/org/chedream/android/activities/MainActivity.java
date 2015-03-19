package org.chedream.android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import org.chedream.android.R;
import org.chedream.android.fragments.ContactsFragment;
import org.chedream.android.fragments.DreamsFragment;
import org.chedream.android.fragments.FaqFragment;
import org.chedream.android.fragments.NavigationDrawerFragment;

import static org.chedream.android.helpers.Const.Navigation.*;

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

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
        switch (position) {
            case PROFILE:
                Intent intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
                return;
            case ALL_DREAMS:
                fragment = DreamsFragment.newInstance(position);
                break;
            case FAVOURITE_DREAMS:
                fragment = DreamsFragment.newInstance(position);
                break;
            case FAQ:
                fragment = FaqFragment.newInstance(position);
                break;
            case CONTACTS:
                fragment = ContactsFragment.newInstance(position);
                break;
            case 4:
                fragment = DreamsFragment.newInstance(position);
                break;
            default:
                return;
        }
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
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
            case 4:
                mTitle = getString(R.string.title_selection_favorites);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }
}
