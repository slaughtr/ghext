package net.a40two.pext;

public class Settings {

    //this is where settings for the text editor and app will go, using defaults from Ted for now

    //Pastebin settings
    public static int EXPIRE = 0;
    public static int PRIVACY = 0;
    public static int SYNTAX = 0;
    public static int RESULT_LIMIT = 50;

    /** Show the lines numbers */
    public static boolean SHOW_LINE_NUMBERS = true;
    /** automatic break line to fit one page */
    public static boolean WORDWRAP = true;
    /** color setting */
//    public static int COLOR = COLOR_CLASSIC;

    /** Text size setting */
    public static int TEXT_SIZE = 12;

    /** enable fling to scroll */
    public static boolean FLING_TO_SCROLL = true;

    /** Use Undo instead of quit ? */
    public static boolean UNDO = true;
    /** Undo stack capacity */
    public static int UNDO_MAX_STACK = 25;
    /** Use back button as undo */
    public static boolean BACK_BTN_AS_UNDO = false;

    /** Use a Home Page */
    public static boolean USE_HOME_PAGE = false;
    /** Home Page Path */
    public static String HOME_PAGE_PATH = "";

    /**
     * Update the settings from the given {@link SharedPreferences}
     *
     * @param settings
     *            the settings to read from
     */
//    public static void updateFromPreferences(SharedPreferences settings) {
//
//        SHOW_LINE_NUMBERS = settings.getBoolean(PREFERENCE_SHOW_LINE_NUMBERS,
//                true);
//        WORDWRAP = settings.getBoolean(PREFERENCE_WORDWRAP, false);
//        TEXT_SIZE = getStringPreferenceAsInteger(settings,
//                PREFERENCE_TEXT_SIZE, "12");
//        COLOR = getStringPreferenceAsInteger(settings, PREFERENCE_COLOR_THEME,
//                ("" + COLOR_CLASSIC));
//                false);
//        FLING_TO_SCROLL = settings.getBoolean(PREFERENCE_FLING_TO_SCROLL, true);
//
//        BACK_BTN_AS_UNDO = settings.getBoolean(PREFERENCE_BACK_BUTTON_AS_UNDO,
//                false);
//        UNDO = settings.getBoolean(PREFERENCE_ALLOW_UNDO, true);
//    }
}
