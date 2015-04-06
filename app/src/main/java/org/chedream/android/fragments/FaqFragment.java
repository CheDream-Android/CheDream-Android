package org.chedream.android.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.List;

/**
 * NOT FINISHED YET
 */

public class FaqFragment extends Fragment {

    private ProgressBar mProgressBar;
    private TextView mTextView;

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

        mTextView = (TextView) view.findViewById(R.id.faqs_textview);
        mProgressBar = (ProgressBar) view.findViewById(R.id.faqs_progress_bar);

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
                ArrayList<FAQ> faqs = gson.fromJson(
                        response.toString(),
                        new TypeToken<ArrayList<FAQ>>(){}.getType()
                );

                mTextView.setText(Html.fromHtml(buildFAQsString(faqs)));
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

    private String buildFAQsString(List<FAQ> faqs) {
        StringBuilder faqsStrBuilder = new StringBuilder("");

        for (FAQ faq : faqs) {
            faqsStrBuilder.append(String.format(
                    "<b>Запитання</b>:<br>%s<br>",
                    faq.getQuestion()));
            faqsStrBuilder.append(String.format(
                    "<br><b>Відповідь</b>:<br>%s<br><br><br>",
                    faq.getAnswer()));
        }

        return faqsStrBuilder.toString();
    }
}
