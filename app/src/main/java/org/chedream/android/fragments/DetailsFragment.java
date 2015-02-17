package org.chedream.android.fragments;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.chedream.android.R;
import org.chedream.android.helpers.RoundedImageViewHelper;
import org.chedream.android.model.test.Dream;

public class DetailsFragment extends Fragment {
    public static final String ARG_SECTION_NUMBER = "args";

    public static DetailsFragment getInstance(Dream dream) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_SECTION_NUMBER, dream);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Dream dream = (Dream) getArguments().getSerializable(ARG_SECTION_NUMBER);
        ActionBar actionBar = getActivity().getActionBar();
        if (actionBar != null) {
            Toast.makeText(getActivity(), "!= null", Toast.LENGTH_SHORT).show();
            actionBar.setTitle(dream.getTitle());
        }


        ImageView mainImage = (ImageView) view.findViewById(R.id.dream_imageview);
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
                dream.getImage(),
                mainImage,
                options
        );

        RoundedImageViewHelper avatar = (RoundedImageViewHelper) view.findViewById(R.id.avatar_imageview);
        imageLoader.displayImage(
                dream.getImage(),
                avatar,
                options
        );

        TextView likesNumber = (TextView) view.findViewById(R.id.likes_number_textview);
        likesNumber.setText(R.string.sample_count_of_likes); //get number of likes, when API will be available to give it

        TextView userName = (TextView) view.findViewById(R.id.user_name_texview);
        userName.setText(R.string.sample_user_name); //only test try for now. It will get name from User-class

        TextView dreamTitle = (TextView) view.findViewById(R.id.dream_title_textview);
        dreamTitle.setText(dream.getTitle());

        TextView dreamDescription = (TextView) view.findViewById(R.id.dream_description_textview);
        dreamDescription.setText(dream.getDescription());

        Button estimateButton = (Button) view.findViewById(R.id.estimate_btn);

        Button membersButton = (Button) view.findViewById(R.id.members_btn);

        Button financialSupportButton = (Button) view.findViewById(R.id.financial_support_btn);
    }

}
