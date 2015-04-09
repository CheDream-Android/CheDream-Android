package org.chedream.android.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.chedream.android.R;
import org.chedream.android.activities.DetailsActivity;
import org.chedream.android.activities.MainActivity;
import org.chedream.android.helpers.ChedreamAPIHelper;
import org.chedream.android.helpers.ChedreamHttpClient;
import org.chedream.android.helpers.Const;
import org.chedream.android.helpers.RealmHelper;
import org.chedream.android.model.Dream;
import org.chedream.android.model.Dreams;
import org.json.JSONObject;

import java.util.List;

import io.realm.Realm;
import io.realm.exceptions.RealmMigrationNeededException;

import static org.chedream.android.helpers.Const.IMAGELOADER;

public class DreamsFragment extends Fragment {

    private Dreams mDreams;
    private List<Dream> mDreamsFromDB;
    private ActionBarActivity mActivity;
    private Realm mRealm;
    private RealmHelper mRealmHelper;
    private GridViewAdapter mGridViewAdapter;

    private boolean mIsDataFromDBOnScreen = false;
    private ViewStub mEmptyFavDreamList;


    public static DreamsFragment newInstance(int sectionNumber) {
        DreamsFragment fragment = new DreamsFragment();
        Bundle args = new Bundle();
        args.putInt(Const.ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public DreamsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mActivity = (ActionBarActivity) getActivity();
        try {
            mRealm = Realm.getInstance(mActivity);
        } catch (RealmMigrationNeededException e) {
            e.printStackTrace();
            Realm.deleteRealmFile(mActivity);
        }
        mRealmHelper = new RealmHelper();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mRealm != null) {
            mRealm.close();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (getArguments().getInt(Const.ARG_SECTION_NUMBER) == Const.Navigation.FAVOURITE_DREAMS
                && mDreamsFromDB != null && !mDreamsFromDB.isEmpty()) {
            menu.findItem(R.id.action_delete_all_favorites).setVisible(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete_all_favorites:
                mDreamsFromDB = mRealmHelper.getDreamsFromDatabase(mRealm);
                if (!mDreamsFromDB.isEmpty()) {
                    new AlertDialog.Builder(mActivity)
                            .setTitle(R.string.dialog_delete_all_fav_title)
                            .setMessage(R.string.dialog_delete_all_fav_message)
                            .setPositiveButton("Так", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mRealmHelper.deleteAllDreamsFromDatabase(mRealm, mActivity, mDreamsFromDB);
                                    mGridViewAdapter.notifyDataSetChanged();
                                    mRealm = Realm.getInstance(mActivity);
                                    mEmptyFavDreamList.setVisibility(View.VISIBLE);
                                }
                            })
                            .setNegativeButton("Ні", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .show();
                } else {
                    Toast.makeText(mActivity, R.string.no_fav_dreams, Toast.LENGTH_SHORT).show();
                }
                item.setVisible(false);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dreams, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final GridView gridView = (GridView) view.findViewById(R.id.grid_view);
        int orientation = getResources().getConfiguration().orientation;
        if (Configuration.ORIENTATION_LANDSCAPE == orientation) {
            gridView.setNumColumns(3);
        }

        mGridViewAdapter = new GridViewAdapter(getActivity());
        gridView.setDrawSelectorOnTop(true);
        mEmptyFavDreamList = (ViewStub) view.findViewById(R.id.viewstub_no_fav_dreams);

        mEmptyFavDreamList = (ViewStub) view.findViewById(R.id.viewstub_no_fav_dreams);

        final ProgressBar downloadingProgressBar =
                (ProgressBar) view.findViewById(R.id.downloading_progress_bar);

        //checking, what section is selected
        if (getArguments().getInt(Const.ARG_SECTION_NUMBER) == Const.Navigation.FAVOURITE_DREAMS) {
            mDreamsFromDB = mRealmHelper.getDreamsFromDatabase(mRealm);
            mIsDataFromDBOnScreen = true;

            if (mDreamsFromDB.isEmpty()) {
                mEmptyFavDreamList.setVisibility(View.VISIBLE);
            }

            mGridViewAdapter.notifyDataSetChanged();
            gridView.setAdapter(mGridViewAdapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getActivity(), DetailsActivity.class);
                    intent.putExtra(DetailsFragment.ARG_SECTION_NUMBER, mDreamsFromDB.get(position));
                    startActivity(intent);
                }
            });

        } else {
            mIsDataFromDBOnScreen = false;
            ChedreamHttpClient.get(Const.ChedreamAPI.Get.ALL_DREAMS, null, new JsonHttpResponseHandler() {
                @Override
                public void onStart() {
                    super.onStart();
                    downloadingProgressBar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    downloadingProgressBar.setVisibility(View.GONE);

                    Gson gson = new Gson();
                    mDreams = gson.fromJson(response.toString(), Dreams.class);
                    gridView.setAdapter(mGridViewAdapter);
                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(getActivity(), DetailsActivity.class);
                            intent.putExtra(DetailsFragment.ARG_SECTION_NUMBER, mDreams.getDreams().get(position));
                            startActivity(intent);

                            Log.d("DreamsFragment",
                                    Integer.toString(ChedreamAPIHelper.getCurrentFinContribQuantity(mDreams.getDreams().get(position))));


                        }
                    });
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setMessage(getActivity().getResources().getString(R.string.dialog_no_internet_message))
                                    .setCancelable(false)
                                    .setNegativeButton("OK",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                }
                                            });
                            AlertDialog alert = builder.create();
                            alert.show();
                        }
                    });
                }
            });

        }

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(Const.ARG_SECTION_NUMBER));
    }

    private class GridViewAdapter extends BaseAdapter {

        private final String TAG = GridViewAdapter.class.getSimpleName();
        private LayoutInflater mLayoutInflater;

        public GridViewAdapter(Context context) {
            mLayoutInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            if (!mIsDataFromDBOnScreen) {
                return mDreams.getDreams().size();
            } else {
                return mDreamsFromDB.size();
            }
        }

        @Override
        public Object getItem(int position) {
            if (!mIsDataFromDBOnScreen) {
                return mDreams.getDreams().get(position);
            } else {
                return mDreamsFromDB.get(position);
            }
        }

        public Dream getDream(int position) {
            return (Dream) getItem(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;

            if (convertView == null) {
                convertView = mLayoutInflater.inflate(R.layout.dream_item, parent, false);

                viewHolder = new ViewHolder();
                viewHolder.mImageViewMain = (ImageView) convertView.findViewById(R.id.image_view_main);

                viewHolder.mTitle = (TextView) convertView.findViewById(R.id.textview_title);

                viewHolder.mBarMoney = (ProgressBar) convertView.findViewById(R.id.progress_bar_money);
                viewHolder.mBarTools = (ProgressBar) convertView.findViewById(R.id.progress_bar_tools);
                viewHolder.mBarPeople = (ProgressBar) convertView.findViewById(R.id.progress_bar_people);

                viewHolder.mContainerMoney = convertView.findViewById(R.id.progress_money_container);
                viewHolder.mContainerTools = convertView.findViewById(R.id.progress_tools_container);
                viewHolder.mContainerPeople = convertView.findViewById(R.id.progress_people_container);

                viewHolder.mCountLikes = (TextView) convertView.findViewById(R.id.textview_count_of_likes);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }


            String title;
            String imageUrl;
            String likes;
            int finResQuantity;
            int visibilityFin;
            int workResQuantity;
            int visibilityWork;
            int equipResQuantity;
            int visibilityEquip;
            int finProgress;
            int workProgress;
            int equipProgress;

            if (!mIsDataFromDBOnScreen) {
                Dream dream = getDream(position);

                title = dream.getTitle();
                imageUrl = Const.ChedreamAPI.BASE_POSTER_URL +
                        dream.getMediaPoster().getProviderReference();
                finResQuantity = ChedreamAPIHelper.getOverallFinResQuantity(dream);
                visibilityFin = finResQuantity != 0 ? View.VISIBLE : View.GONE;
                workResQuantity = ChedreamAPIHelper.getOverallWorkResQuantity(dream);
                visibilityWork = workResQuantity != 0 ? View.VISIBLE : View.GONE;
                equipResQuantity = ChedreamAPIHelper.getOverallEquipResQuantity(dream);
                visibilityEquip = equipResQuantity != 0 ? View.VISIBLE : View.GONE;
                finProgress = ChedreamAPIHelper.getCurrentFinContribQuantity(dream);
                workProgress = ChedreamAPIHelper.getCurrentWorkContribQuantity(dream);
                equipProgress = ChedreamAPIHelper.getCurrentEquipContribQuantity(dream);
                likes = String.valueOf(dream.getUsersWhoFavorites().size());
            } else {
                title = mDreamsFromDB.get(position).getTitle();
                imageUrl = Const.ChedreamAPI.BASE_POSTER_URL +
                        mDreamsFromDB.get(position).getMediaPoster().getProviderReference();
                finResQuantity = mRealmHelper.getFinResQuantity(mRealm, position);
                visibilityFin = finResQuantity != 0 ? View.VISIBLE : View.GONE;
                workResQuantity = mRealmHelper.getWorkResQuantity(mRealm, position);
                visibilityWork = workResQuantity != 0 ? View.VISIBLE : View.GONE;
                equipResQuantity = mRealmHelper.getEquipResQuantity(mRealm, position);
                visibilityEquip = equipResQuantity != 0 ? View.VISIBLE : View.GONE;
                finProgress = mRealmHelper.getFinContQuantity(mRealm, position);
                workProgress = mRealmHelper.getWorkContQuantity(mRealm, position);
                equipProgress = mRealmHelper.getEquipContQuantity(mRealm, position);
                likes = String.valueOf(mDreamsFromDB.get(position).getUsersWhoFavorites().size());
            }

            //to get shown dreams while is no internet connection, need to save images into cache or on external card

            IMAGELOADER.displayImage(imageUrl, viewHolder.mImageViewMain);

            viewHolder.mTitle.setText(title);
            viewHolder.mCountLikes.setText(likes);

            viewHolder.mBarMoney.setMax(finResQuantity);
            viewHolder.mContainerMoney.setVisibility(visibilityFin);

            viewHolder.mBarPeople.setMax(workResQuantity);
            viewHolder.mContainerPeople.setVisibility(visibilityWork);

            viewHolder.mBarTools.setMax(equipResQuantity);
            viewHolder.mContainerTools.setVisibility(visibilityEquip);

            viewHolder.mBarMoney.setProgress(finProgress);
            viewHolder.mBarPeople.setProgress(workProgress);
            viewHolder.mBarTools.setProgress(equipProgress);

            PorterDuff.Mode mode = viewHolder.mBarMoney.
                    getProgress() == finResQuantity ? PorterDuff.Mode.SRC_IN : PorterDuff.Mode.DST;
            viewHolder.mBarMoney.getProgressDrawable().setColorFilter(Color.GREEN, mode);
            mode = viewHolder.mBarPeople.
                    getProgress() == workResQuantity ? PorterDuff.Mode.SRC_IN : PorterDuff.Mode.DST;
            viewHolder.mBarPeople.getProgressDrawable().setColorFilter(Color.GREEN, mode);
            mode = viewHolder.mBarTools.
                    getProgress() == equipResQuantity ? PorterDuff.Mode.SRC_IN : PorterDuff.Mode.DST;
            viewHolder.mBarTools.getProgressDrawable().setColorFilter(Color.GREEN, mode);
            return convertView;
        }
    }


    private static class ViewHolder {
        ImageView mImageViewMain;
        ProgressBar mBarMoney, mBarTools, mBarPeople;
        View mContainerMoney, mContainerTools, mContainerPeople;
        TextView mTitle, mCountLikes;
    }
}
