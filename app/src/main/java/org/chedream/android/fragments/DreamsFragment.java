package org.chedream.android.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.apache.http.Header;
import org.chedream.android.R;
import org.chedream.android.activities.DetailsActivity;
import org.chedream.android.activities.MainActivity;
import org.chedream.android.database.RealmHelper;
import org.chedream.android.helpers.ChedreamAPIHelper;
import org.chedream.android.helpers.ChedreamHttpClient;
import org.chedream.android.helpers.Const;
import org.chedream.android.model.Dream;
import org.chedream.android.model.Dreams;
import org.chedream.android.model.test.DreamRandomizer;
import org.json.JSONObject;

import java.util.List;

import io.realm.Realm;

public class DreamsFragment extends Fragment {

    private Dreams mDreams;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    private ActionBarActivity mActivity;
    private Realm mRealm;
    private RealmHelper mRealmHelper;
    private GridViewAdapter mGridViewAdapter;


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
        //if it wont work - move to onViewCreated  part of code below cause it always were there
        mActivity = (ActionBarActivity) getActivity();
        mRealm = Realm.getInstance(mActivity);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (getArguments().getInt(Const.ARG_SECTION_NUMBER) == 4) {
            menu.findItem(R.id.action_delete_all_favorites).setVisible(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_delete_all_favorites:
//                mRealm.close();
//                Realm.deleteRealmFile(mActivity);
//                mDreams.clear();
//                mGridViewAdapter.notifyDataSetChanged();
//        }
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

        final ProgressBar downloadingProgressBar =
                (ProgressBar) view.findViewById(R.id.downloading_progress_bar);

        if (getArguments().getInt(Const.ARG_SECTION_NUMBER) == 4) {
//            mRealmHelper = new RealmHelper();
//            mDreams = mRealmHelper.getAllTestDreams(mRealm);
        } else {
            ChedreamHttpClient.get(Const.API_ALL_DREAMS, null, new JsonHttpResponseHandler() {
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
                    mGridViewAdapter = new GridViewAdapter(getActivity());
                    gridView.setAdapter(mGridViewAdapter);
                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(getActivity(), DetailsActivity.class);
                            intent.putExtra(DetailsFragment.ARG_SECTION_NUMBER, mDreams.getDreams().get(position));
                            startActivity(intent);
                        }
                    });


                    options = new DisplayImageOptions.Builder()
                            .cacheOnDisk(true)
                            .cacheInMemory(true)
                            .considerExifParams(true)
                            .build();

                    imageLoader = ImageLoader.getInstance();
                    imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity().getBaseContext()));

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

        private LayoutInflater mLayoutInflater;

        public GridViewAdapter(Context context) {
            mLayoutInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return mDreams.getDreams().size();
        }

        @Override
        public Object getItem(int position) {
            return mDreams.getDreams().get(position);
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

            Dream dream = getDream(position);

            String url;

            if (dream.getCurrentStatus().equals("rejected")) {
                url = Const.API_BASE_POSTER_URL + "4f78cd20a92f4f88c9a0bce0bfd1242a5a91f46b.jpeg";
            } else {
                url = Const.API_BASE_POSTER_URL + dream.getMediaPoster().getProviderReference();
            }

            imageLoader.displayImage(
                    url,
                    viewHolder.mImageViewMain,
                    options);

            viewHolder.mTitle.setText(dream.getTitle());

            viewHolder.mCountLikes.setText(Integer.toString(1));

            viewHolder.mBarMoney.setProgress(ChedreamAPIHelper.getCurrentFinContribQuantity(dream));
            viewHolder.mBarPeople.setProgress(ChedreamAPIHelper.getCurrentWorkContribQuantity(dream));
            viewHolder.mBarTools.setProgress(ChedreamAPIHelper.getCurrentEquipContribQuantity(dream));

            int visibility = ChedreamAPIHelper.getFinResQuantity(dream) != 0 ? View.VISIBLE : View.GONE;
            viewHolder.mContainerMoney.setVisibility(visibility);

            visibility = ChedreamAPIHelper.getWorkResQuantity(dream) != 0 ? View.VISIBLE : View.GONE;
            viewHolder.mContainerPeople.setVisibility(visibility);

            visibility = ChedreamAPIHelper.getEquipResQuantity(dream) != 0 ? View.VISIBLE : View.GONE;
            viewHolder.mContainerTools.setVisibility(visibility);

            final int ORANGE = 0xFF9933;

            PorterDuff.Mode mode = viewHolder.mBarMoney.
                    getProgress() == 100 ? PorterDuff.Mode.SRC_IN : PorterDuff.Mode.DST;
            viewHolder.mBarMoney.getProgressDrawable().setColorFilter(ORANGE, mode);
            mode = viewHolder.mBarPeople.
                    getProgress() == 100 ? PorterDuff.Mode.SRC_IN : PorterDuff.Mode.DST;
            viewHolder.mBarPeople.getProgressDrawable().setColorFilter(ORANGE, mode);
            mode = viewHolder.mBarTools.
                    getProgress() == 100 ? PorterDuff.Mode.SRC_IN : PorterDuff.Mode.DST;
            viewHolder.mBarTools.getProgressDrawable().setColorFilter(ORANGE, mode);

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
