package org.chedream.android.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
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
import android.widget.Toast;

import org.chedream.android.R;
import org.chedream.android.activities.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    public static PlaceholderFragment newInstance(int sectionNumber) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public PlaceholderFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GridView gridView = (GridView) view.findViewById(R.id.grid_view);
        int orientation = getResources().getConfiguration().orientation;
        if (Configuration.ORIENTATION_LANDSCAPE == orientation) {
            gridView.setNumColumns(3);
        }
        ArrayList<String> dreams = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            dreams.add(Integer.toString(i));
        }
        gridView.setAdapter(new GridViewAdapter(getActivity(), dreams));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = "Selected " + (position + 1);
                Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    private static class GridViewAdapter extends BaseAdapter {

        List<String> mDreams;
        private LayoutInflater mLayoutInflater;

        public GridViewAdapter(Context context, List<String> dreams) {
            mDreams = dreams;
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

        public String getDream(int position) {
            return (String) getItem(position);
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

                viewHolder.mBarMoney = (ProgressBar) convertView.findViewById(R.id.progress_bar_money);
                viewHolder.mBarTools = (ProgressBar) convertView.findViewById(R.id.progress_bar_tools);
                viewHolder.mBarPeople = (ProgressBar) convertView.findViewById(R.id.progress_bar_people);

                viewHolder.mContainerMoney = convertView.findViewById(R.id.progress_money_container);
                viewHolder.mContainerTools = convertView.findViewById(R.id.progress_tools_container);
                viewHolder.mContainerPeople = convertView.findViewById(R.id.progress_people_container);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            int res = position % 2 == 0 ? R.drawable.sample_image : R.drawable.sample_image_2;

            viewHolder.mImageViewMain.setImageDrawable(convertView.getResources().getDrawable(res));

            Random rand = new Random();
            viewHolder.mBarMoney.setProgress(rand.nextInt(100));
            viewHolder.mBarTools.setProgress(rand.nextInt(100));
            viewHolder.mBarPeople.setProgress(rand.nextInt(100));
            if (rand.nextInt(10) == 5) {
                viewHolder.mContainerMoney.setVisibility(View.GONE);
            }
            if (rand.nextInt(100) == 5) {
                viewHolder.mContainerTools.setVisibility(View.GONE);
            }
            return convertView;
        }
    }

    private static class ViewHolder {
        ImageView mImageViewMain;
        ProgressBar mBarMoney, mBarTools, mBarPeople;
        View mContainerMoney, mContainerTools, mContainerPeople;
    }
}
