package org.chedream.android.fragments;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
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
import org.chedream.android.helpers.Const;
import org.chedream.android.helpers.RealmHelper;
import org.chedream.android.helpers.RoundedImageViewHelper;
import org.chedream.android.model.Dream;
import org.chedream.android.views.NotifyingScrollView;

import io.realm.Realm;


public class DetailsFragment extends Fragment {
    public static final String ARG_SECTION_NUMBER = "args";
    private ActionBarActivity mActivity;
    private Realm mRealm;
    private Dream mDream;
    private RealmHelper mRealmHelper = new RealmHelper();

    public static DetailsFragment getInstance(Dream dream) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_SECTION_NUMBER, dream);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (mRealmHelper.isDreamInDatabase(mRealm, mDream)) {
            menu.findItem(R.id.action_add_to_favorite).setIcon(R.drawable.ic_action_important);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_to_favorite:
                if (mRealmHelper.isDreamInDatabase(mRealm, mDream)) {
                    mRealmHelper.deleteDreamFromDatabase(mRealm, mDream, mActivity);
                    item.setIcon(R.drawable.ic_action_not_important);
                } else {
                    mRealmHelper.addDreamToDatabase(mRealm, mDream, mActivity);
                    item.setIcon(R.drawable.ic_action_important);
                }
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

        mDream = getArguments().getParcelable(ARG_SECTION_NUMBER);
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
                Const.ChedreamAPI.BASE_POSTER_URL + mDream.getMediaPoster().getProviderReference(),
                mainImage,
                options
        );

        RoundedImageViewHelper avatar = (RoundedImageViewHelper) view.findViewById(R.id.img_avatar);
        imageLoader.displayImage(
                mDream.getAuthor().getAvatar().getProviderReference(),
                avatar,
                options
        );

        TextView likesNumber = (TextView) view.findViewById(R.id.txt_likes_number);
        if (mDream.getUsersWhoFavorites() != null) {
            String likes = String.valueOf(mDream.getUsersWhoFavorites().size());
            likesNumber.setText(likes);
        } else {
            likesNumber.setText("0");
        }
        TextView userName = (TextView) view.findViewById(R.id.txt_user_name);
        userName.setText(mDream.getAuthor().getFirstName() + " " + mDream.getAuthor().getLastName());

        TextView dreamTitle = (TextView) view.findViewById(R.id.dream_title_textview);
        dreamTitle.setText(mDream.getTitle());

        TextView dreamDescription = (TextView) view.findViewById(R.id.dream_description_textview);
        dreamDescription.setText(Html.fromHtml(mDream.getDescription()));

        Button estimateButton = (Button) view.findViewById(R.id.estimate_btn);

        Button membersButton = (Button) view.findViewById(R.id.members_btn);
    }


}
