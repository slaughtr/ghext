package net.a40two.pext;

public class Settings {

    //this is where settings for the text editor and app will go, using defaults from Ted for now

    /** Number of recent files to remember */
    public static int MAX_RECENT_FILES = 10;

    /** Show the lines numbers */
    public static boolean SHOW_LINE_NUMBERS = true;
    /** automatic break line to fit one page */
    public static boolean WORDWRAP = true;
    /** color setting */
//    public static int COLOR = COLOR_CLASSIC;

    /** Text size setting */
    public static int TEXT_SIZE = 12;

//    /** Default end of line */
//    public static int DEFAULT_END_OF_LINE = EOL_LINUX;
//    /** End Of Line style */
//    public static int END_OF_LINE = EOL_LINUX;
//    /** Encoding */
//    public static String ENCODING = ENC_UTF8;

    /** Let auto save on quit be triggered */
    public static boolean FORCE_AUTO_SAVE = false;
    public static boolean AUTO_SAVE_OVERWRITE = false;

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
     * @return the end of line characters according to the current settings
     */
//    public static String getEndOfLine() {
//        switch (END_OF_LINE) {
//            case EOL_MAC: // Mac OS
//                return "\r";
//            case EOL_WINDOWS: // Windows
//                return "\r\n";
//            case EOL_LINUX: // Linux / Android
//            default:
//                return "\n";
//        }
//    }

    /**
     * Update the settings from the given {@link SharedPreferences}
     *
     * @param settings
     *            the settings to read from
     */
//    public static void updateFromPreferences(SharedPreferences settings) {
//
//        MAX_RECENT_FILES = getStringPreferenceAsInteger(settings,
//                PREFERENCE_MAX_RECENTS, "10");
//        SHOW_LINE_NUMBERS = settings.getBoolean(PREFERENCE_SHOW_LINE_NUMBERS,
//                true);
//        WORDWRAP = settings.getBoolean(PREFERENCE_WORDWRAP, false);
//        TEXT_SIZE = getStringPreferenceAsInteger(settings,
//                PREFERENCE_TEXT_SIZE, "12");
//        DEFAULT_END_OF_LINE = getStringPreferenceAsInteger(settings,
//                PREFERENCE_END_OF_LINES, ("" + EOL_LINUX));
//        FORCE_AUTO_SAVE = settings.getBoolean(PREFERENCE_AUTO_SAVE, false);
//        AUTO_SAVE_OVERWRITE = settings.getBoolean(
//                PREFERENCE_AUTO_SAVE_OVERWRITE, false);
//        COLOR = getStringPreferenceAsInteger(settings, PREFERENCE_COLOR_THEME,
//                ("" + COLOR_CLASSIC));
//                false);
//        FLING_TO_SCROLL = settings.getBoolean(PREFERENCE_FLING_TO_SCROLL, true);
//
//        BACK_BTN_AS_UNDO = settings.getBoolean(PREFERENCE_BACK_BUTTON_AS_UNDO,
//                false);
//        UNDO = settings.getBoolean(PREFERENCE_ALLOW_UNDO, true);

//    }

//    public static Typeface getTypeface(Context ctx) {
//        File fontFile = getFontFile(ctx);
//        Typeface res = Typeface.MONOSPACE;
//        if (fontFile.exists() && fontFile.canRead()) {
//            res = Typeface.createFromFile(getFontFile(ctx));
//        }
//        return res;
//    }
}
