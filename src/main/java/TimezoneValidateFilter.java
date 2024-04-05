import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@WebFilter(value = "/time/*")
public class TimezoneValidateFilter extends HttpFilter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String timezone = httpRequest.getParameter("timezone");

        if (timezone == null || timezone.isEmpty()) {

            Cookie[] cookies = httpRequest.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("lastTimezone")) {
                        timezone = cookie.getValue();
                        break;
                    }
                }
            }

            if (timezone == null || timezone.isEmpty()) {
                timezone = "UTC";
            }
        }

        timezone = URLDecoder.decode(timezone, StandardCharsets.UTF_8);

        if (!isValidTimezone(timezone)) {
            httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpResponse.getWriter().write("Invalid timezone");
            return;
        }

        if (timezone != null && !timezone.isEmpty()) {
            String encodedTimezone = URLEncoder.encode(timezone, StandardCharsets.UTF_8);
            Cookie cookie = new Cookie("lastTimezone", encodedTimezone);
            httpResponse.addCookie(cookie);
        }
        chain.doFilter(request, response);
    }

    private boolean isValidTimezone(String timezone) {
        try {
            TimeZone.getTimeZone(timezone);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
