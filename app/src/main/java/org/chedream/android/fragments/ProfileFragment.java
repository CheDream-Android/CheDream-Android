package org.chedream.android.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKSdk;
import com.vk.sdk.VKSdkListener;
import com.vk.sdk.api.VKError;
import com.vk.sdk.dialogs.VKCaptchaDialog;

import org.chedream.android.R;
import org.chedream.android.activities.BaseSocialActivity;
import org.chedream.android.helpers.Const;
import org.chedream.android.helpers.RoundedImageViewHelper;

public class ProfileFragment extends Fragment implements ConnectionCallbacks, OnConnectionFailedListener {


    private ProgressDialog mConnectionProgressDialog;

    public ProfileFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        switch (sharedPrefs.getInt(Const.SP_SOCIAL_NETWORK_ID, Const.SocialNetworks.N0_SOC_NETWORK)) {
            case Const.SocialNetworks.FB_ID:
                FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
                break;
            case Const.SocialNetworks.GPLUS_ID:
                if (((BaseSocialActivity) getActivity()).getGoogleApiClient() == null) {
                    ((BaseSocialActivity) getActivity()).initGoogleApiClient(this, this);
                    ((BaseSocialActivity) getActivity()).getGoogleApiClient().connect();

                    mConnectionProgressDialog = new ProgressDialog(getActivity());
                    mConnectionProgressDialog.setMessage("Signing in...");
                    mConnectionProgressDialog.show();
                }
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        final SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        RoundedImageViewHelper profilePicture =
                (RoundedImageViewHelper) rootView.findViewById(R.id.img_avatar);

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.people)
                .showImageOnFail(R.drawable.people)
                .showImageForEmptyUri(R.drawable.people)
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .considerExifParams(true)
                .build();

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity().getBaseContext()));
        imageLoader.displayImage(
                sharedPrefs.getString(Const.SP_USER_PICTURE_URL, null),
                profilePicture,
                options
        );

        TextView userNameTextView = (TextView) rootView.findViewById(R.id.txt_user_name);
        userNameTextView.setText(sharedPrefs.getString(Const.SP_USER_NAME, null));

        Button logoutButton = (Button) rootView.findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (sharedPrefs.getInt(
                        Const.SP_SOCIAL_NETWORK_ID,
                        Const.SocialNetworks.N0_SOC_NETWORK
                )) {
                    case Const.SocialNetworks.FB_ID:
                        LoginManager.getInstance().logOut();
                        break;
                    case Const.SocialNetworks.VK_ID:
                        ((BaseSocialActivity) getActivity()).initVKSdk(new VKSdkListener() {
                            @Override
                            public void onCaptchaError(VKError vkError) {
                                new VKCaptchaDialog(vkError).show();
                            }

                            @Override
                            public void onTokenExpired(VKAccessToken vkAccessToken) {
                                VKSdk.logout();
                            }

                            @Override
                            public void onAccessDenied(VKError vkError) {
                                ((BaseSocialActivity) getActivity()).showAllertDialog(getActivity()
                                        .getResources().getString(R.string.dialog_soc_network_connection_failure));
                            }
                        });
                        break;
                    case Const.SocialNetworks.GPLUS_ID:
                        //Plus.AccountApi.clearDefaultAccount(((BaseSocialActivity) getActivity()).getGoogleApiClient());
                        //Plus.AccountApi.revokeAccessAndDisconnect(((BaseSocialActivity) getActivity()).getGoogleApiClient());
                        break;
                }
                ((BaseSocialActivity) getActivity())
                        .setLoginStatus(false, Const.SocialNetworks.N0_SOC_NETWORK);
                FragmentTransaction transaction =
                        getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_container_profile, new LoginFragment());
                transaction.commit();
            }
        });

        return rootView;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onConnected(Bundle bundle) {
        mConnectionProgressDialog.dismiss();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("Profile: ", "suspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(
                        getActivity(),
                        Const.SocialNetworks.GPLUS_REQUEST_CODE_RESOLVE_ERR
                );
            } catch (IntentSender.SendIntentException e) {
                ((BaseSocialActivity) getActivity()).getGoogleApiClient().connect();
            }
        }
    }
}
