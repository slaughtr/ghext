package net.a40two.pext;

import net.a40two.pext.models.User;

public class Constants {
    //dev key, needed for everything but anon pastes
    public static final String DEV_API_KEY = BuildConfig.DEV_API_KEY;
    public static final String DEV_API_KEY_PARAM = "api_dev_key";

    //necessary URLs
    public static final String BASE_URL = "https://pastebin.com/api/api_post.php";
    public static final String RAW_OUTPUT_URL = "https://pastebin.com/api/api_raw.php";
    public static final String NEW_USER_KEY_URL = "https://pastebin.com/api/api_login.php";

    //additional necessary bits for calls
    public static final String API_OPTION = "api_option";
    /*the above is somewhat confusing. It's used for several things, including pasting, getting lists, etc
    "paste" = make a new paste
    "show_paste" = get logged in user's raw paste (also need paste key)
    "list" = get list of logged in user's pastes
    */
    public static final String PASTE_BODY_PARAM = "api_paste_code";
    public static final String PASTE_NAME_PARAM = "api_paste_name";
    public static final String PASTE_FORMAT_PARAM = "api_paste_format";
    //format for syntax highlighting. Absolutely tons of options listed on pastebin.com/api
    public static final String PASTE_PRIVATE_PARAM = "api_paste_private";
    public static final String PASTE_EXPIRE_PARAM = "api_paste_expire_date";
    public static final String RESULTS_LIMIT_PARAM = "api_results_limit";
    public static final String PASTE_KEY_PARAM = "api_paste_key";
    //above is used for getting paste to fetch

    //logging in, pasting as user
    public static final String PREFERENCES_USER_API_KEY = "userApiKey";
    public static final String USERNAME_PARAM = "api_user_name";
    public static final String PASSWORD_PARAM = "api_user_password";
    public static final String USER_API_KEY_PARAM = "api_user_key";
    public static boolean LOGGED_IN = false;
    public static User CURRENT_USER;
    public static String USER_NAME = "";
    public static String USER_PASSWORD = "";
    public static String USER_API_KEY = "";

    //Other things that might be needed here and there
    public static final String MY_GITHUB = "https://github.com/slaughtr";
    public static final String MY_LINKEDIN = "https://linkedin.com/in/dallas-slaughter";
    public static final String MY_BITCOIN = "1H2MTgcP7UDrjAy4Kk6PcDFsWvnwfRkKNt";

    //Firebase
    public static final String FIREBASE_CHILD_SAVED_EDITOR_STATE = "savedFromEditor";
    //TODO: this should probably be an array of things saved/clipboard items,
    //so that it can be used in the "new paste" menu and maybe eventually in the paste functionality
}
