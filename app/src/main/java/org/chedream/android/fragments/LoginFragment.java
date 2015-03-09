package org.chedream.android.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.LoginButton;

import org.chedream.android.R;
import org.chedream.android.helpers.Const;

import java.util.Arrays;

/**
    NOT FINISHED YET
 */

public class LoginFragment extends Fragment {

    private static final String LOG_TAG = LoginFragment.class.getSimpleName();

    private UiLifecycleHelper mUiHelper;
    private TextView mUserInfoTextView;

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

        mUserInfoTextView = (TextView) view.findViewById(R.id.user_info_textview);

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
                (session.isOpened() || session.isClosed()) ) {
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
        SharedPreferences.Editor editor = getActivity()
                .getSharedPreferences(Const.SP_FILE_NAME, Context.MODE_PRIVATE).edit();
        editor.putBoolean(Const.SP_LOGIN_STATUS, isLogged);
        editor.apply();
    }

    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        if (state.isOpened()) {
            setLoginStatus(true);
            mUserInfoTextView.setVisibility(View.VISIBLE);
            Log.i(LOG_TAG, "Logged in...");
        } else if (state.isClosed()) {
            setLoginStatus(false);
            mUserInfoTextView.setVisibility(View.INVISIBLE);
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
