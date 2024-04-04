import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

//@WebServlet(value = "/time/*")
public class TimeServlet extends HttpServlet {

    public static String sign = null;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String time = Timezone.getTimezone(req);
        System.out.println(time);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss ");
        resp.setContentType("text/html; charset=utf-8");
        if (time == null) {
            resp.getWriter().write(ZonedDateTime.now().format(formatter) + "UTC");
        } else {
            String[] argument = Timezone.parsing(time);

            String belt = null;
            String hours = null;
            assert argument != null;
            if (argument.length > 1) {
                belt = argument[0];
                hours = argument[1];
            } else {
                resp.getWriter().write(TimezoneValidateFilter.zone);
            }

            String formattedDate = setTimeZone(hours, formatter);

            resp.getWriter().write(formattedDate + belt + sign + hours);
        }

        resp.getWriter().close();
    }

    public String setTimeZone(String hours, DateTimeFormatter formatter) {
        if (sign.equals("+")) {
            return ZonedDateTime.now()
                    .plusHours(Long.parseLong(hours))
                    .format(formatter);
        } else {
            return ZonedDateTime.now()
                    .minusHours(Long.parseLong(hours))
                    .format(formatter);
        }
    }

}
