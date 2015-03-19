package org.chedream.android.fragments;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.chedream.android.R;
import org.chedream.android.database.RealmHelper;
import org.chedream.android.helpers.RoundedImageViewHelper;
import org.chedream.android.model.test.Dream;
import org.chedream.android.views.NotifyingScrollView;

import io.realm.Realm;

public class DetailsFragment extends Fragment {
    public static final String ARG_SECTION_NUMBER = "args";
    private ActionBarActivity mActivity;
    private Realm mRealm;
    private Dream mDream;

    public static DetailsFragment getInstance(Dream dream) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_SECTION_NUMBER, dream);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        //if it wont work - move to onViewCreated  part of code below cause it always were there
        mActivity = (ActionBarActivity) getActivity();
        mRealm = Realm.getInstance(mActivity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_details, menu);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_to_favorite:
                RealmHelper realmHelper = new RealmHelper();
                //Dream dream = (Dream) getArguments().getSerializable(ARG_SECTION_NUMBER);
                realmHelper.addTestDreamToDatabase(mRealm,mDream);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Drawable backgroundActionBar =
                new ColorDrawable(getResources().getColor(R.color.color_primary));

        mActivity.getSupportActionBar().setBackgroundDrawable(backgroundActionBar);
        backgroundActionBar.setAlpha(0);

        final int actionBarHeight = mActivity.getSupportActionBar().getHeight();
        final int imageHeight = 500;
        final int maxAlpha = 250;
        ((NotifyingScrollView) view.findViewById(R.id.details_main_container)).setOnScrollChangedListener(
                new NotifyingScrollView.OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt) {
                        int headerHeight = imageHeight - actionBarHeight;
                        float ratio = (float)
                                Math.min(Math.max(t, 0), headerHeight) / headerHeight;
                        int newAlpha = (int) (ratio * maxAlpha);
                        backgroundActionBar.setAlpha(newAlpha);
                    }
                });

        mDream = (Dream) getArguments().getSerializable(ARG_SECTION_NUMBER);
        ActionBar actionBar = mActivity.getSupportActionBar();
        actionBar.setTitle(mDream.getTitle());

        ImageView mainImage = (ImageView) view.findViewById(R.id.img_dream_main);
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity().getBaseContext()));

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.people)
                .showImageOnFail(R.drawable.people)
                .showImageForEmptyUri(R.drawable.people)
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .considerExifParams(true)
                .build();

        imageLoader.displayImage(
                mDream.getImage(),
                mainImage,
                options
        );

        RoundedImageViewHelper avatar = (RoundedImageViewHelper) view.findViewById(R.id.img_avatar);
        imageLoader.displayImage(
                mDream.getImage(),
                avatar,
                options
        );

        TextView likesNumber = (TextView) view.findViewById(R.id.txt_likes_number);
        likesNumber.setText(R.string.sample_count_of_likes); //get number of likes, when API will be available to give it

        TextView userName = (TextView) view.findViewById(R.id.txt_user_name);
        userName.setText(R.string.sample_user_name); //only test try for now. It will get name from User-class

        TextView dreamTitle = (TextView) view.findViewById(R.id.dream_title_textview);
        dreamTitle.setText(mDream.getTitle());

        TextView dreamDescription = (TextView) view.findViewById(R.id.dream_description_textview);
        dreamDescription.setText(mDream.getDescription());

        Button estimateButton = (Button) view.findViewById(R.id.estimate_btn);

        Button membersButton = (Button) view.findViewById(R.id.members_btn);

        Button financialSupportButton = (Button) view.findViewById(R.id.financial_support_btn);
    }


}
