package org.chedream.android.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import org.chedream.android.activities.BaseSocialActivity;
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

    public LoginFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BaseSocialActivity) getActivity()).initGoogleApiClient(this, this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
                                ((BaseSocialActivity) getActivity()).getFbCallbackManager(),
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

                                                            ((BaseSocialActivity) getActivity())
                                                                    .setLoginStatus(true, Const.SocialNetworks.FB_ID);
                                                            moveToProfile();
                                                        }
                                                    }
                                                }).executeAsync();
                                    }

                                    @Override
                                    public void onCancel() {
                                        Log.d(LOG_TAG, "fb: login canceled");
                                    }

                                    @Override
                                    public void onError(FacebookException e) {
                                        ((BaseSocialActivity) getActivity()).showAllertDialog(getActivity()
                                                .getResources().getString(R.string.dialog_soc_network_connection_failure));
                                    }
                                });
                        LoginManager.getInstance().logInWithReadPermissions(
                                getActivity(),
                                Arrays.asList("public_profile")
                        );
                        break;
                    case Const.SocialNetworks.VK_ID:
                        ((BaseSocialActivity) getActivity()).initVKSdk(sdkListener);
                        VKSdk.authorize(sVkScope, true, false);
                        break;
                    case Const.SocialNetworks.GPLUS_ID:
                        ((BaseSocialActivity) getActivity()).getGoogleApiClient().connect();

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
            ((BaseSocialActivity) getActivity()).showAllertDialog(getActivity()
                    .getResources().getString(R.string.dialog_soc_network_connection_failure));
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
                    ((BaseSocialActivity) getActivity()).setLoginStatus(true, Const.SocialNetworks.VK_ID);
                    moveToProfile();
                }
            });
            Log.d(LOG_TAG, "New User Token received");
        }

        @Override
        public void onAcceptUserToken(VKAccessToken token) {
            super.onAcceptUserToken(token);
            Log.d(LOG_TAG, "User Token accepted");
        }
    };

    //Google Plus callbacks
    @Override
    public void onConnected(Bundle bundle) {
        mConnectionProgressDialog.dismiss();
        ((BaseSocialActivity) getActivity()).setLoginStatus(true, Const.SocialNetworks.GPLUS_ID);
        Person person = Plus.PeopleApi
                .getCurrentPerson(((BaseSocialActivity) getActivity()).getGoogleApiClient());
        String avatarUrl = person.getImage().getUrl();
        StringBuilder builder = new StringBuilder(avatarUrl)
                .delete(avatarUrl.length() - 2, avatarUrl.length())
                .append("200");
        saveUserData(person.getDisplayName(), builder.toString());
        moveToProfile();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(LOG_TAG, "G+: Connection suspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(LOG_TAG, "G+: Connection failed");
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(getActivity(), REQUEST_CODE_RESOLVE_ERR);
            } catch (IntentSender.SendIntentException e) {
                ((BaseSocialActivity) getActivity()).getGoogleApiClient().connect();
            }
        }
        mConnectionProgressDialog.dismiss();
    }

    private void saveUserData(String username, String avatarUrl) {
        SharedPreferences.Editor editor = PreferenceManager
                .getDefaultSharedPreferences(getActivity()).edit();
        editor.putString(Const.SP_USER_NAME, username);
        editor.putString(Const.SP_USER_PICTURE_URL, avatarUrl);
        editor.apply();
    }

    private void moveToProfile() {
        FragmentTransaction transaction =
                getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_container_profile, new ProfileFragment());
        transaction.commit();
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
            ViewHolder viewHolder;

            if (convertView == null) {
                convertView = mLayoutInflater.inflate(R.layout.soc_network_item, parent, false);

                viewHolder = new ViewHolder();
                viewHolder.logoImageView = (ImageView) convertView.findViewById(R.id.logo_imageview);
                viewHolder.titleTextView = (TextView) convertView.findViewById(R.id.title_textview);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.logoImageView.setImageResource(mSocialNetworks.get(position).getLogo());
            viewHolder.titleTextView.setText(mSocialNetworks.get(position).getTitle());

            return convertView;
        }

        class ViewHolder {
            ImageView logoImageView;
            TextView titleTextView;
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
