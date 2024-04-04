import javax.servlet.http.HttpServletRequest;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Timezone {

    public static String getTimezone(HttpServletRequest req) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss ");
        if (req.getParameter("timezone") == null) {
            return ZonedDateTime.now().format(formatter) + "UTC";
        }

        return req.getParameter("timezone");

    }

    public static String[] parsing(String time) {
        String[] argument;

        for (int i = 0; i < time.length(); i++) {
            if (time.charAt(i) == ' ') {
                argument = time.split(" ");
                TimeServlet.sign = "+";
                return argument;
            } else if (time.charAt(i) == '-') {
                argument = time.split("-");
                TimeServlet.sign = "-";
                return argument;
            }

        }
        if (TimeServlet.sign == null) {
            return new String[]{time};
        }
        return null;
    }
}
