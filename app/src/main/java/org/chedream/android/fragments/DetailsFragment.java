package org.chedream.android.fragments;

import android.content.Context;
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
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

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
    public static final String ALPHA_SAVED = "ALPHA_SAVED";
    private ActionBarActivity mActivity;
    private Realm mRealm;
    private Dream mDream;
    private RealmHelper mRealmHelper = new RealmHelper();
    private int mAlpha;

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
                    if (mDream.getDreamEquipmentContributions() != null) {
                        mRealmHelper.addDreamToDatabase(mRealm, mDream, mActivity);
                        item.setIcon(R.drawable.ic_action_important);
                    } else {
                        Toast.makeText(mActivity, getString(R.string.chose_real_dream_message), Toast.LENGTH_SHORT).show();
                    }
                }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Drawable backgroundActionBar =
                new ColorDrawable(getResources().getColor(R.color.color_primary));

        if (savedInstanceState != null) {
            mAlpha = savedInstanceState.getInt(ALPHA_SAVED);
        }
        mActivity.getSupportActionBar().setBackgroundDrawable(backgroundActionBar);
        backgroundActionBar.setAlpha(mAlpha);

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
                        mAlpha = (int) (ratio * maxAlpha);
                        backgroundActionBar.setAlpha(mAlpha);
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
        if (mDream.getAuthor().getAvatar() != null) {
            imageLoader.displayImage(
                    mDream.getAuthor().getAvatar().getProviderReference(),
                    avatar,
                    options
            );
        }

        ListView listView = (ListView) view.findViewById(R.id.members_list);

        //To be honestly, here we must check the param 'isDreamFromDatabase', but this param is false always here (I don't get why)
        //so simple check if some of contribution parameters is null (it is, if you get dream from DB)
        if (mDream.getDreamFinancialResources() != null) {
            if (getContrSize() != 0) {
                MembersListAdapter adapter = new MembersListAdapter(getActivity());
                listView.setAdapter(adapter);
                setListViewHeightBasedOnChildren(listView);
            }
        } else {
            TextView supportTitle = (TextView) view.findViewById(R.id.support_title);
            supportTitle.setVisibility(View.GONE);
            listView.setVisibility(View.GONE);
        }
        TextView userName = (TextView) view.findViewById(R.id.txt_user_name);
        userName.setText(mDream.getAuthor().getFirstName() + " " + mDream.getAuthor().getLastName());

        TextView dreamTitle = (TextView) view.findViewById(R.id.dream_title_textview);
        dreamTitle.setText(mDream.getTitle());

        TextView dreamDescription = (TextView) view.findViewById(R.id.dream_description_textview);
        dreamDescription.setText(Html.fromHtml(mDream.getDescription()));

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ALPHA_SAVED, mAlpha);
    }

    /**
     * Method for Setting the Height of the ListView dynamically.
     * Hack to fix the issue of not showing all the items of the ListView
     * when placed inside a ScrollView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        MembersListAdapter listAdapter = (MembersListAdapter) listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            if (listItem instanceof ViewGroup)
                listItem.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams
                        .WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    public int getContrSize() {
        int firstSize;
        int secondSize;
        if (mDream.getDreamFinancialContributions().size() >= mDream.getDreamEquipmentContributions().size()) {
            firstSize = mDream.getDreamFinancialContributions().size();
        } else {
            firstSize = mDream.getDreamEquipmentContributions().size();
        }
        if (mDream.getDreamOtherContributions().size() >= mDream.getDreamWorkContributions().size()) {
            secondSize = mDream.getDreamOtherContributions().size();
        } else {
            secondSize = mDream.getDreamWorkContributions().size();
        }
        if (firstSize >= secondSize) {
            return firstSize;
        } else {
            return secondSize;
        }
    }

    private class MembersListAdapter extends BaseAdapter {
        private LayoutInflater mLayoutInflater;
        private int mSize = getContrSize();

        public MembersListAdapter(Context context) {
            mLayoutInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return mSize;
        }

        @Override
        public Object getItem(int position) {
            return mDream.getUsersWhoFavorites().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = mLayoutInflater.inflate(R.layout.support_item, parent, false);

                viewHolder = new ViewHolder();

                viewHolder.mFinSupportTitle = (TextView) convertView.findViewById(R.id.fin_support_title);
                viewHolder.mFinContrTitle = (TextView) convertView.findViewById(R.id.fin_contr_textview);
                viewHolder.mFinContrQuantity = (TextView) convertView.findViewById(R.id.fin_contr_quantity);

                viewHolder.mEquipSupportTitle = (TextView) convertView.findViewById(R.id.equip_support_title);
                viewHolder.mEquipContrTitle = (TextView) convertView.findViewById(R.id.equip_contr_textview);
                viewHolder.mEquipContrQuantity = (TextView) convertView.findViewById(R.id.equip_contr_quantity);

                viewHolder.mWorkSupportTitle = (TextView) convertView.findViewById(R.id.work_support_title);
                viewHolder.mWorkContrTitle = (TextView) convertView.findViewById(R.id.work_contr_textview);
                viewHolder.mWorkContrQuantity = (TextView) convertView.findViewById(R.id.work_contr_quantity);

                viewHolder.mOtherSupportTitle = (TextView) convertView.findViewById(R.id.other_support_title);
                viewHolder.mOtherContrTitle = (TextView) convertView.findViewById(R.id.other_contr_textview);
                viewHolder.mOtherContrQuantity = (TextView) convertView.findViewById(R.id.other_contr_quantity);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            if (!mDream.getDreamFinancialContributions().isEmpty() && mDream.getDreamFinancialContributions().size() > position) {
                viewHolder.mFinContrTitle.setText(mDream.getDreamFinancialContributions().get(position).getFinancialResourcse().getTitle());
                viewHolder.mFinContrQuantity.setText(String.valueOf(mDream.getDreamFinancialContributions()
                        .get(position).getQuantity()) + " грн");
            } else {
                viewHolder.mFinContrTitle.setVisibility(View.GONE);
                viewHolder.mFinContrQuantity.setVisibility(View.GONE);
                viewHolder.mFinSupportTitle.setVisibility(View.GONE);
            }

            if (!mDream.getDreamEquipmentContributions().isEmpty() && position < mDream.getDreamEquipmentContributions().size()) {
                viewHolder.mEquipContrTitle.setText(mDream.getDreamEquipmentContributions().get(position).getEquipmentResource().getTitle());
                viewHolder.mEquipContrQuantity.setText(String.valueOf(mDream.getDreamEquipmentContributions().get(position).getQuantity()));
            } else {
                viewHolder.mEquipContrTitle.setVisibility(View.GONE);
                viewHolder.mEquipContrQuantity.setVisibility(View.GONE);
                viewHolder.mEquipSupportTitle.setVisibility(View.GONE);
            }

            if (!mDream.getDreamWorkContributions().isEmpty() && mDream.getDreamWorkContributions().size() > position) {
                viewHolder.mWorkContrTitle.setText(mDream.getDreamWorkContributions().get(position).getWorkResource().getTitle());
                viewHolder.mWorkContrQuantity.setText(String.valueOf(mDream.getDreamWorkContributions().get(position).getQuantity()) + " дн.");
            } else {
                viewHolder.mWorkContrTitle.setVisibility(View.GONE);
                viewHolder.mWorkContrQuantity.setVisibility(View.GONE);
                viewHolder.mWorkSupportTitle.setVisibility(View.GONE);
            }

            if (!mDream.getDreamOtherContributions().isEmpty() && mDream.getDreamOtherContributions().size() > position) {
                viewHolder.mOtherContrTitle.setText(mDream.getDreamOtherContributions().get(position).getTitle());
                if (mDream.getDreamOtherContributions().get(position).getQuantity() != 0) {
                    viewHolder.mOtherContrQuantity.setText(String.valueOf(mDream.getDreamOtherContributions().get(position).getQuantity()));
                } else {
                    viewHolder.mOtherContrQuantity.setText(" ");
                }
            } else {
                viewHolder.mOtherContrTitle.setVisibility(View.GONE);
                viewHolder.mOtherContrQuantity.setVisibility(View.GONE);
                viewHolder.mOtherSupportTitle.setVisibility(View.GONE);
            }
            return convertView;
        }
    }

    private static class ViewHolder {
        TextView mFinContrTitle, mFinContrQuantity, mEquipContrTitle, mEquipContrQuantity, mFinSupportTitle,
                mWorkContrTitle, mWorkContrQuantity, mOtherContrTitle, mOtherContrQuantity, mEquipSupportTitle,
                mOtherSupportTitle, mWorkSupportTitle;
    }

}