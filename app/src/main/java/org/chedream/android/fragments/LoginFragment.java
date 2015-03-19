package org.chedream.android.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.vk.sdk.VKUIHelper;

import org.chedream.android.R;
import org.chedream.android.helpers.Const;

import java.util.Arrays;

/**
 * NOT FINISHED YET
 */

public class LoginFragment extends Fragment {

    private static final String LOG_TAG = LoginFragment.class.getSimpleName();

    private UiLifecycleHelper mFbUiHelper;
    //private VKUIHelper mVkUiHelper;

    private Session.StatusCallback mStatusCallback =
            new SessionStatusCallback();

    public LoginFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFbUiHelper = new UiLifecycleHelper(getActivity(), mStatusCallback);
        mFbUiHelper.onCreate(savedInstanceState);
        VKUIHelper.onCreate(getActivity());
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
        mFbUiHelper.onResume();
        VKUIHelper.onResume(getActivity());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mFbUiHelper.onActivityResult(requestCode, resultCode, data);
        VKUIHelper.onActivityResult(getActivity(), requestCode, resultCode, data);
        Log.i(LOG_TAG, "Result code: " + Integer.toString(resultCode));
    }

    @Override
    public void onPause() {
        super.onPause();
        mFbUiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mFbUiHelper.onDestroy();
        VKUIHelper.onDestroy(getActivity());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mFbUiHelper.onSaveInstanceState(outState);
    }

    private void setLoginStatus(boolean isLogged) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        sp.edit().putBoolean(Const.SP_LOGIN_STATUS, isLogged).apply();
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

    private class SessionStatusCallback implements Session.StatusCallback {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    }
}
