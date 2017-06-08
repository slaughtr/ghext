package net.a40two.pext.adapters;

public class PasteSyntaxHelper {

    public static String getSyntax(String name) {
        String shortName = "text";

        //need short names to submit syntax

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
}