package org.chedream.android.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.VKSdkListener;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiUserFull;
import com.vk.sdk.api.model.VKList;
import com.vk.sdk.dialogs.VKCaptchaDialog;

import org.chedream.android.R;
import org.chedream.android.activities.ProfileActivity;
import org.chedream.android.helpers.Const;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * NOT FINISHED YET
 */

public class LoginFragment extends Fragment implements ConnectionCallbacks, OnConnectionFailedListener {

    private static final String LOG_TAG = LoginFragment.class.getSimpleName();

    private static String[] sVkScope = new String[]{VKScope.PHOTOS, VKScope.NOHTTPS};
    private static final int REQUEST_CODE_RESOLVE_ERR = 9000;

    private ArrayList<SocialNetwork> mSocialNetworks;

    private ProgressDialog mConnectionProgressDialog;
    private GoogleApiClient mGoogleApiClient;

    public LoginFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        initSocNetworks();

        ListView listView = (ListView) view.findViewById(R.id.social_networks_listvew);
        listView.setAdapter(new SocialNetworksAdapter(getActivity()));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (mSocialNetworks.get(position).getId()) {
                    case Const.SocialNetworks.FB_ID:
                        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());

                        LoginManager.getInstance().registerCallback(
                                ProfileActivity.sCallbackManager,
                                new FacebookCallback<LoginResult>() {
                                    @Override
                                    public void onSuccess(LoginResult loginResult) {
                                        GraphRequestAsyncTask request = GraphRequest.newMeRequest(
                                                AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                                                    @Override
                                                    public void onCompleted(JSONObject user, GraphResponse response) {
                                                        if (user != null) {
                                                            String avatarUrl = "https://graph.facebook.com/"
                                                                    + user.optString("id") + "/picture?type=large";
                                                            saveUserData(user.optString("name"), avatarUrl);
                                                        }
                                                    }
                                                }).executeAsync();

                                        setLoginStatus(true, Const.SocialNetworks.FB_ID);
                                    }

                                    @Override
                                    public void onCancel() {
                                        Log.d(LOG_TAG, "fb: login canceled");
                                    }

                                    @Override
                                    public void onError(FacebookException e) {
                                        Log.d(LOG_TAG, "fb: login error");
                                    }
                                });
                        LoginManager.getInstance().logInWithReadPermissions(
                                getActivity(),
                                Arrays.asList("public_profile")
                        );
                        break;
                    case Const.SocialNetworks.VK_ID:
                        VKSdk.initialize(
                                sdkListener,
                                getActivity().getResources().getString(R.string.vkontakte_app_id),
                                VKAccessToken.tokenFromSharedPreferences(getActivity(), "VK_ACCESS_TOKEN"));
                        VKSdk.authorize(sVkScope, true, false);
                        break;
                    case Const.SocialNetworks.GPLUS_ID:
                        mGoogleApiClient.connect();

                        mConnectionProgressDialog = new ProgressDialog(getActivity());
                        mConnectionProgressDialog.setMessage("Signing in...");
                        mConnectionProgressDialog.show();
                        break;
                }
            }
        });

        return view;
    }

    private VKSdkListener sdkListener = new VKSdkListener() {
        @Override
        public void onCaptchaError(VKError captchaError) {
            new VKCaptchaDialog(captchaError).show();
        }

        @Override
        public void onTokenExpired(VKAccessToken expiredToken) {
            VKSdk.authorize(sVkScope, true, false);
        }

        @Override
        public void onAccessDenied(VKError authorizationError) {
            new AlertDialog.Builder(getActivity())
                    .setMessage(authorizationError.errorMessage)
                    .show();
            Log.d(LOG_TAG, "Access Denied");
        }

        @Override
        public void onReceiveNewToken(VKAccessToken newToken) {
            super.onReceiveNewToken(newToken);
            newToken.saveTokenToSharedPreferences(getActivity(), "VK_ACCESS_TOKEN");

            VKRequest request = VKApi.users().get(VKParameters.from(VKApiConst.FIELDS, "photo_200"));
            request.executeWithListener(new VKRequest.VKRequestListener() {
                @Override
                public void onComplete(VKResponse response) {
                    super.onComplete(response);

                    VKList<VKApiUserFull> vkList = (VKList<VKApiUserFull>) response.parsedModel;
                    VKApiUserFull user = vkList.get(0);
                    saveUserData(
                            user.first_name + " " + user.last_name,
                            user.photo_200
                    );
                }
            });

            setLoginStatus(true, Const.SocialNetworks.VK_ID);
            Log.d(LOG_TAG, "New User Token received");
        }

        @Override
        public void onAcceptUserToken(VKAccessToken token) {
            super.onAcceptUserToken(token);
//            Intent i = new Intent(LoginActivity.this, MainActivity.class);
//            startActivity(i);
            Log.d(LOG_TAG, "User Token accepted");
        }
    };

    @Override
    public void onConnected(Bundle bundle) {
        mConnectionProgressDialog.dismiss();
        setLoginStatus(true, Const.SocialNetworks.GPLUS_ID);
        Person person = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
        String avatarUrl = person.getImage().getUrl();
        StringBuilder builder = new StringBuilder(avatarUrl)
                .delete(avatarUrl.length()-2, avatarUrl.length())
                .append("200");
        saveUserData(person.getDisplayName(), builder.toString());
        Log.d(LOG_TAG, "connected");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(LOG_TAG, "G+ disconnected");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (mConnectionProgressDialog.isShowing()) {
            if (connectionResult.hasResolution()) {
                try {
                    connectionResult.startResolutionForResult(getActivity(), REQUEST_CODE_RESOLVE_ERR);
                } catch (IntentSender.SendIntentException e) {
                    mGoogleApiClient.connect();
                }
            }
        }
    }

    private void setLoginStatus(boolean isLogged, int socialNetworkId) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        sp.edit().putBoolean(Const.SP_LOGIN_STATUS, isLogged)
                .putInt(Const.SP_SOCIAL_NETWORK_ID, socialNetworkId).apply();
    }

    private void saveUserData(String username, String avatarUrl) {
        SharedPreferences.Editor editor = PreferenceManager
                .getDefaultSharedPreferences(getActivity()).edit();
        editor.putString(Const.SP_USER_NAME, username);
        editor.putString(Const.SP_USER_PICTURE_URL, avatarUrl);
        editor.apply();
    }

    private void initSocNetworks() {
        mSocialNetworks = new ArrayList<SocialNetwork>();
        mSocialNetworks.add(new SocialNetwork(Const.SocialNetworks.FB_ID, "Facebook", R.drawable.facebook_logo));
        mSocialNetworks.add(new SocialNetwork(Const.SocialNetworks.VK_ID, "Vkontakte", R.drawable.vk_logo));
        mSocialNetworks.add(new SocialNetwork(Const.SocialNetworks.GPLUS_ID, "Google+", R.drawable.google_plus_logo));
    }

    private class SocialNetworksAdapter extends BaseAdapter {

        private LayoutInflater mLayoutInflater;

        public SocialNetworksAdapter(Context context) {
            mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return mSocialNetworks.size();
        }

        @Override
        public Object getItem(int position) {
            return mSocialNetworks.get(position);
        }

        @Override
        public long getItemId(int position) {
            return mSocialNetworks.get(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = mLayoutInflater.inflate(R.layout.soc_network_item, parent, false);

                ImageView logoImageView = (ImageView) convertView.findViewById(R.id.logo_imageview);
                logoImageView.setImageResource(mSocialNetworks.get(position).getLogo());

                TextView titleTextView = (TextView) convertView.findViewById(R.id.title_textview);
                titleTextView.setText(mSocialNetworks.get(position).getTitle());
            }
            return convertView;
        }
    }

    private class SocialNetwork {
        private int id;
        private String title;
        private int logo;

        public SocialNetwork(int id, String title, int logo) {
            this.id = id;
            this.title = title;
            this.logo = logo;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getLogo() {
            return logo;
        }

        public void setLogo(int logo) {
            this.logo = logo;
        }
    }
}
