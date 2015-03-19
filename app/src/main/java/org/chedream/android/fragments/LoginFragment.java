package org.chedream.android.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.chedream.android.R;
import org.chedream.android.helpers.Const;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * NOT FINISHED YET
 */

public class LoginFragment extends Fragment {

    private static final String LOG_TAG = LoginFragment.class.getSimpleName();

    private UiLifecycleHelper mUiHelper;

    private Session.StatusCallback mStatusCallback =
            new SessionStatusCallback();

    public LoginFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUiHelper = new UiLifecycleHelper(getActivity(), mStatusCallback);
        mUiHelper.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        LoginButton fbLoginButton = (LoginButton) view.findViewById(R.id.fb_login_button);
        fbLoginButton.setReadPermissions(Arrays.asList("email", "public_profile"));
        fbLoginButton.setFragment(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Session session = Session.getActiveSession();
        if (session != null &&
                (session.isOpened() || session.isClosed())) {
            onSessionStateChange(session, session.getState(), null);
        }
        mUiHelper.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mUiHelper.onActivityResult(requestCode, resultCode, data);
        Log.i(LOG_TAG, "Result code: " + Integer.toString(resultCode));
    }

    @Override
    public void onPause() {
        super.onPause();
        mUiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUiHelper.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mUiHelper.onSaveInstanceState(outState);
    }

    private void setLoginStatus(boolean isLogged) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        sp.edit().putBoolean(Const.SP_LOGIN_STATUS, isLogged).apply();;

    }

    private void onSessionStateChange(Session session, SessionState state, Exception exception) {

        if (state.isOpened()) {
            setLoginStatus(true);


                Request.newMeRequest(session, new Request.GraphUserCallback() {
                    @Override
                    public void onCompleted(GraphUser graphUser, Response response) {
                        String picUrl = "https://graph.facebook.com/"
                                + graphUser.getId() + "/picture?type=large";
                        SharedPreferences.Editor editor = PreferenceManager
                                .getDefaultSharedPreferences(getActivity()).edit();
                        editor.putString(Const.SP_USER_PICTURE_URL, picUrl);
                        editor.putString(Const.SP_USER_NAME, graphUser.getName());
                        editor.apply();
                    }
                }).executeAsync();

            Log.i(LOG_TAG, "Logged in...");
        } else if (state.isClosed()) {
            setLoginStatus(false);
            Log.i(LOG_TAG, "Logged out...");
        }
    }

//    private void downloadAndSavePicture(final String url) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                InputStream is = retrieveStream(url);
//                FileOutputStream fos = null;
//                try {
//                    fos = getActivity().openFileOutput(
//                            Const.PROFILE_PICTURE_FILE_NAME,
//                            Context.MODE_PRIVATE);
//
//                    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
//
//                    int nRead;
//                    byte[] data = new byte[16384];
//
//                    while ((nRead = is.read(data, 0, data.length)) != -1) {
//                        buffer.write(data, 0, nRead);
//                    }
//
//                    buffer.flush();
//
//                    fos.write(buffer.toByteArray());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } finally {
//                    if (fos != null) {
//                        try {
//                            fos.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//
//            private InputStream retrieveStream(String url) {
//                DefaultHttpClient client = new DefaultHttpClient();
//                HttpGet getRequest = new HttpGet(url);
//
//                try {
//                    HttpResponse getResponse = client.execute(getRequest);
//                    final int statusCode = getResponse.getStatusLine().getStatusCode();
//                    if (statusCode != HttpStatus.SC_OK) {
//                        Log.e(LOG_TAG, "Error " + statusCode + " for URL " + url);
//                        return null;
//                    }
//                    HttpEntity getResponseEntity = getResponse.getEntity();
//                    return getResponseEntity.getContent();
//                } catch (IOException e) {
//                    getRequest.abort();
//                    Log.e(LOG_TAG, "Error for URL " + url, e);
//                }
//                return null;
//            }
//        }).start();
//
//    }

    private class SessionStatusCallback implements Session.StatusCallback {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    }
}
