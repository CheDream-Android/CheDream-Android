package org.chedream.android.helpers;

public class Const {
    public static final String ARG_SECTION_NUMBER = "section_number";

    public static final String SP_LOGIN_STATUS = "login_status";
    public static final String SP_USER_PICTURE_URL = "user_pic_url";
    public static final String SP_USER_NAME = "user_name";
    public static final String SP_SOCIAL_NETWORK_ID = "social_network";



    public static class ChedreamAPI {

        public static final String BASE_URL = "http://api.chedream.org";

        public static class Get {
            public static final String ALL_DREAMS = "/dreams.json";
            public static final String ALL_FAQS = "/faqs.json";
        }

        public static final String BASE_IMAGE_URL =
                "http://chedream.org/upload/media/pictures/0001/01/";
        public static final String BASE_POSTER_URL =
                "http://chedream.org/upload/media/poster/0001/01/";
    }

    public static class SocialNetworks {
        public static final int N0_SOC_NETWORK = 0;
        public static final int FB_ID = 1;
        public static final int VK_ID = 2;
        public static final int GPLUS_ID = 3;

        public static final int GPLUS_REQUEST_CODE_RESOLVE_ERR = 9000;
    }

    public static class Navigation {
        public static final int PROFILE = 0;
        public static final int ALL_DREAMS = 1;
        public static final int FAVOURITE_DREAMS = 2;
        public static final int FAQ = 3;
        public static final int CONTACTS = 4;
    }
}