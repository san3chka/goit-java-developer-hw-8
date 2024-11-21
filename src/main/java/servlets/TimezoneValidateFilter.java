package servlets;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@WebFilter(value = "/time")
public class TimezoneValidateFilter extends HttpFilter {

    private static final String[] VALID_UTC_CODES = {
            "UTC-12", "UTC-11", "UTC-10", "UTC-9", "UTC-8", "UTC-7", "UTC-6", "UTC-5",
            "UTC-4", "UTC-3", "UTC-2", "UTC-1", "UTC+0", "UTC+1", "UTC+2", "UTC+3",
            "UTC+4", "UTC+5", "UTC+6", "UTC+7", "UTC+8", "UTC+9", "UTC+10", "UTC+11", "UTC+12", "UTC+13", "UTC+14"
    };

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String timezone = req.getParameter("timezone");

        if (timezone != null && isValidTimezone(timezone.replace(" ", "+"))) {
            chain.doFilter(req, res);
        } else {
            res.setStatus(400);
            res.setContentType("application/json");
            res.getWriter().write("{\"error\": \"Invalid timezone\"}");
            res.getWriter().close();
        }
    }

    private boolean isValidTimezone(String timezone) {
        return Arrays.asList(VALID_UTC_CODES).contains(timezone);
    }
}
