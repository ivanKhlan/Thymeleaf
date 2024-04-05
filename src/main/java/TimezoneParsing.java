public class TimezoneParsing {

    public static String[] parsing(String time) {
        String[] argument;

        for (int i = 0; i < time.length(); i++) {
            if (time.charAt(i) == ' ') {
                argument = time.split(" ");
                TimezoneFormatter.sign = "+";
                return argument;
            } else if (time.charAt(i) == '-') {
                argument = time.split("-");
                TimezoneFormatter.sign = "-";
                return argument;
            }

        }
        if (TimezoneFormatter.sign == null) {
            return new String[]{time};
        }
        return null;
    }
}
