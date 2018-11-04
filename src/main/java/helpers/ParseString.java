package helpers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseString {

    public static String getNumberFromString(String input){
        Pattern p = Pattern.compile("[0-9]+");
        Matcher match = p.matcher(input);

        if (match.find()) {
            return match.group(0);
        }
        else
            return "Can't parse the string = " + input;
    }
}
