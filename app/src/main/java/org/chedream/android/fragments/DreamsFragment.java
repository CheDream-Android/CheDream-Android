package org.chedream.android.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.chedream.android.R;
import org.chedream.android.activities.DetailsActivity;
import org.chedream.android.activities.MainActivity;
import org.chedream.android.helpers.Const;
import org.chedream.android.model.test.Dream;

import java.util.List;

public class DreamsFragment extends Fragment {

    private List<Dream> mDreams;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dreams, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GridView gridView = (GridView) view.findViewById(R.id.grid_view);
        int orientation = getResources().getConfiguration().orientation;
        if (Configuration.ORIENTATION_LANDSCAPE == orientation) {
            gridView.setNumColumns(3);
        }
        mDreams = Dream.getDreams(getActivity());
        gridView.setAdapter(new GridViewAdapter(getActivity()));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra(DetailsFragment.ARG_SECTION_NUMBER, mDreams.get(position));
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
            return mDreams.size();
        }

        @Override
        public Object getItem(int position) {
            return mDreams.get(position);
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

            imageLoader.displayImage(
                    dream.getImage(),
                    viewHolder.mImageViewMain,
                    options);

            viewHolder.mTitle.setText(dream.getTitle());

            viewHolder.mCountLikes.setText(Integer.toString(dream.getLikes()));

            viewHolder.mBarMoney.setProgress(dream.getMoneyCurrent());
            viewHolder.mBarPeople.setProgress(dream.getPeopleCurrent());
            viewHolder.mBarTools.setProgress(dream.getToolsCurrent());

            int visibility = dream.isMoney() ? View.VISIBLE : View.GONE;
            viewHolder.mContainerMoney.setVisibility(visibility);

            visibility = dream.isPeople() ? View.VISIBLE : View.GONE;
            viewHolder.mContainerPeople.setVisibility(visibility);

            visibility = dream.isTools() ? View.VISIBLE : View.GONE;
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
