package org.chedream.android.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.chedream.android.R;
import org.chedream.android.activities.MainActivity;
import org.chedream.android.helpers.Const;
import org.chedream.android.helpers.DownloadAndParseJSONHelper;
import org.chedream.android.model.FAQ;
import org.chedream.android.model.FAQsContainer;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
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

        new DownloadFAQSTask().execute();

        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(Const.ARG_SECTION_NUMBER));
    }

    private class DownloadFAQSTask extends AsyncTask<Void, Void, String> {

        private final String API_FAQS = "faqs.json";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = Const.API_BASE_URL + API_FAQS;

            FAQsContainer container =
                    new DownloadAndParseJSONHelper<>(getActivity(), FAQsContainer.class)
                            .DownloadAndParse(url);

            return (container != null) ? buildFAQsString(container.getFaqs()) : null;
        }

        @Override
        protected void onPostExecute(String faqsStr) {
            super.onPostExecute(faqsStr);
            mProgressBar.setVisibility(View.GONE);

            if (faqsStr == null) {
                return;
            }
            mTextView.setText(Html.fromHtml(faqsStr));
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
}
