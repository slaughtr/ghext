package net.a40two.pext.adapters;

public class PasteHelper {

    public static String getSyntax(String name) {
        String shortName = "text";
        //TODO: this should probably just be a method in the paste popup/
        //we need short names to submit syntax, from the spinner selection
        if (name.equals("Batch")) { shortName = "dos"; }
        else if (name.equals("BrainFuck")) { shortName = "bf"; }
        else if (name.equals("C#")) { shortName = "csharp"; }
        else if (name.equals("C++")) { shortName = "cpp"; }
        else if (name.equals("Game Maker")) { shortName = "gml"; }
        else if (name.equals("HTML 5")) { shortName = "html5"; }
        else if (name.equals("INI file")) { shortName = "ini"; }
        else if (name.equals("LOL Code")) { shortName = "lolcode"; }
        else if (name.equals("None")) { shortName = "text"; }
        else if (name.equals("Objective C")) { shortName = "objc"; }
        else if (name.equals("Power Shell")) { shortName = "powershell"; }
        else if (name.equals("R")) { shortName = "rsplus"; }
        else if (name.equals("VB.NET")) { shortName = "vbnet"; }
        else if (name.equals("VisualBasic")) { shortName = "vb"; }
        else { shortName = name.toLowerCase(); }

        return shortName;
    }


    public static String getPrivacy(String privacy) {
        String fixedPrivacy = "0";

        if (privacy.equals("Public")) { fixedPrivacy = "0"; }
        else if (privacy.equals("Unlisted")) { fixedPrivacy = "1"; }
        else if (privacy.equals("Private")) { fixedPrivacy = "2"; }

        return fixedPrivacy;
    }


    public static String getExpire(String expires) {
        String fixedExpires = "N";

        if (expires.equals("Never")) { fixedExpires = "N"; }
        else if (expires.equals("10 Minutes")) { fixedExpires = "10M"; }
        else if (expires.equals("1 Hour")) { fixedExpires = "1H"; }
        else if (expires.equals("1 Day")) { fixedExpires = "1D"; }
        else if (expires.equals("1 Week")) { fixedExpires = "1W"; }
        else if (expires.equals("2 Weeks")) { fixedExpires = "2W"; }
        else if (expires.equals("1 Month")) { fixedExpires = "1M"; }

        return fixedExpires;
    }
}