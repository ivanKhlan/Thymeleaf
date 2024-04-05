import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TimezoneFormatter extends HttpServlet {

    public static String sign = null;

    public static String getTime(HttpServletRequest req, HttpServletResponse resp) {

        String time = null;
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("lastTimezone")) {
                    time = cookie.getValue();
                    break;
                }
            }
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss ");
        resp.setContentType("text/html; charset=utf-8");
        if (time == null) {
            return ZonedDateTime.now().format(formatter) + "UTC";

        } else {
            String[] argument = TimezoneParsing.parsing(time);

            String belt = null;
            String hours = null;
            assert argument != null;
            if (argument.length > 1) {
                belt = argument[0];
                hours = argument[1];
            } else {
                belt = argument[0];
            }

            String formattedDate = setTimeZone(hours, formatter);

            if (sign == null) {
                return formattedDate + belt;
            } else {
                return formattedDate + belt + sign + hours;
            }
        }
    }

    public static String setTimeZone(String hours, DateTimeFormatter formatter) {
        if (sign != null) {
            if (sign.equals("+")) {
                return ZonedDateTime.now()
                        .plusHours(Long.parseLong(hours))
                        .format(formatter);
            } else {
                return ZonedDateTime.now()
                        .minusHours(Long.parseLong(hours))
                        .format(formatter);
            }
        } else {
            return ZonedDateTime.now().format(formatter);
        }

    }

}
