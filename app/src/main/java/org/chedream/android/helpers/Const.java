package org.chedream.android.helpers;

public class Const {
    public static final String ARG_SECTION_NUMBER = "section_number";

    public static final String SP_LOGIN_STATUS = "login_status";
    public static final String SP_USER_PICTURE_URL = "user_pic_url";
    public static final String SP_USER_NAME = "user_name";

    public static final String API_BASE_URL = "http://api.chedream.org";
    public static final String API_ALL_DREAMS = "/dreams.json";
    public static final String API_BASE_IMAGE_URL =
            "http://chedream.org/upload/media/pictures/0001/01/";
    public static final String API_BASE_POSTER_URL =
            "http://chedream.org/upload/media/poster/0001/01/";

    public static class Navigation {
        public static final int PROFILE = 0;
        public static final int ALL_DREAMS = 1;
        public static final int FAVOURITE_DREAMS = 2;
        public static final int FAQ = 3;
        public static final int CONTACTS = 4;
    }
}