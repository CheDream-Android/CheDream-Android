package org.chedream.android.fragments;

import android.app.Activity;
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

import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.chedream.android.R;
import org.chedream.android.activities.MainActivity;
import org.chedream.android.helpers.Const;
import org.chedream.android.model.FAQ;
import org.chedream.android.model.FAQsContainer;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

/**
    NOT FINISHED YET
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

        private final String LOG_TAG = DownloadFAQSTask.class.getSimpleName();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = Const.API_BASE_URL + "faqs.json";
            InputStream source = retrieveStream(url);
            Reader reader = new InputStreamReader(source);
            Gson gson = new Gson();
            FAQsContainer container = gson.fromJson(reader, FAQsContainer.class);

            return buildFAQsString(container.getFaqs());
        }

        @Override
        protected void onPostExecute(String faqsStr) {
            super.onPostExecute(faqsStr);
            mProgressBar.setVisibility(View.GONE);
            mTextView.setText(Html.fromHtml(faqsStr));
        }

        private InputStream retrieveStream(String url) {
            DefaultHttpClient client = new DefaultHttpClient();
            HttpGet getRequest = new HttpGet(url);

            try {
                HttpResponse getResponse = client.execute(getRequest);
                final int statusCode = getResponse.getStatusLine().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    Log.e(LOG_TAG, "Error " + statusCode + " for URL " + url);
                    return null;
                }
                HttpEntity getResponseEntity = getResponse.getEntity();
                return getResponseEntity.getContent();
            }
            catch (IOException e) {
                getRequest.abort();
                Log.e(LOG_TAG, "Error for URL " + url, e);
            }
            return null;
        }

        private String buildFAQsString(List<FAQ> faqs) {
            StringBuilder faqsStrBuilder = new StringBuilder("");

            for(FAQ faq: faqs) {
                faqsStrBuilder.append(String.format(
                        "<b>Запитання</b>:<br>%s<br>",
                        faq.getQuestion()));
                faqsStrBuilder.append(String.format(
                        "<br><b>Відповідь</b>:<br>%s<br><br><br>",
                        faq.getQuestion()));
            }

            return faqsStrBuilder.toString();
        }
    }
}
