package org.chedream.android.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.chedream.android.R;
import org.chedream.android.activities.MainActivity;
import org.chedream.android.helpers.ChedreamHttpClient;
import org.chedream.android.helpers.Const;
import org.chedream.android.model.FAQ;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * NOT FINISHED YET
 */

public class FaqFragment extends Fragment {

    private ProgressBar mProgressBar;
    private ArrayList<FAQ> mListFaq = new ArrayList<>();

    public static FaqFragment newInstance(int sectionNumber) {
        FaqFragment fragment = new FaqFragment();
        Bundle args = new Bundle();
        args.putInt(Const.ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_faq, container, false);

        mProgressBar = (ProgressBar) view.findViewById(R.id.faqs_progress_bar);

        final ExpandableListView mListView = (ExpandableListView) view.findViewById(R.id.faq_listview);

        ChedreamHttpClient.get(Const.ChedreamAPI.Get.ALL_FAQS, null, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                mProgressBar.setVisibility(View.GONE);

                Gson gson = new Gson();
                mListFaq = gson.fromJson(
                        response.toString(),
                        new TypeToken<ArrayList<FAQ>>(){}.getType()
                );

                mListView.setAdapter(new FaqAdapter());
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



        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(Const.ARG_SECTION_NUMBER));
    }


    class FaqAdapter extends BaseExpandableListAdapter {

        @Override
        public int getGroupCount() {
            return mListFaq.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return 1;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return mListFaq.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return null;
        }

        @Override
        public long getGroupId(int groupPosition) {
            return 0;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.faq_group_item, null);
            }

            FAQ faq = (FAQ) getGroup(groupPosition);
            TextView question = (TextView) convertView.findViewById(R.id.txt_faq_group);
            question.setText(Html.fromHtml(faq.getQuestion()));
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.faq_child_item, null);
            }

            FAQ faq = (FAQ) getGroup(groupPosition);
            TextView answerTextView = (TextView) convertView.findViewById(R.id.txt_faq_child);
            String answer = faq.getAnswer();
            answer = answer.replace("<p>&nbsp;</p>", "");
            answer = answer.replace("<p>", "");
            answer = answer.replace("</p>", "");
//            StringBuilder builder = new StringBuilder(answer)
//                    .delete(answer.length() - 13, answer.length());
            answerTextView.setText(Html.fromHtml(answer));
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }
    }
}
