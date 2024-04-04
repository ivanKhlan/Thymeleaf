import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;

@WebFilter(value = "/time/*")
public class TimezoneValidateFilter extends HttpFilter {


    public static String zone;
    @Override
    protected void doFilter(HttpServletRequest req,
                            HttpServletResponse res,
                            FilterChain chain) throws IOException, ServletException {

        String time = Timezone.getTimezone(req);

        if (isValidTimezone(time)) {

            zone = displayTimeInTimezone(time);

            res.setContentType(zone);
            chain.doFilter(req,res);
        } else {
            if (time != null && !time.contains("UTC")) {

                res.setContentType("text/html; charset=utf-8");
                res.getWriter().write("Invalid timezone");
                res.setStatus(400);

            } else {
                chain.doFilter(req,res);
            }

        }

    }

    public static boolean isValidTimezone(String timezone) {
        String[] availableTimezones = TimeZone.getAvailableIDs();

        for (String availableTimezone : availableTimezones) {
            if (availableTimezone.equals(timezone)) {
                return true;
            }
        }

        return false;
    }

    public static String displayTimeInTimezone(String timezone) {
        TimeZone timeZone = TimeZone.getTimeZone(timezone);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(timeZone);
        return dateFormat.format(new Date());


    }
}
