package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;

@WebServlet(value = "/time")
public class TimeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");

        resp.getWriter().write(getTime(req));
    }

    private String getTime(HttpServletRequest req) {
        ZonedDateTime utcNow = ZonedDateTime.now(ZoneId.of(getParameterName(req)));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss ' UTC'");

        return utcNow.format(formatter);
    }

    private String getParameterName(HttpServletRequest request) {
            String timeZone = request.getParameter("timezone");
            if(timeZone == null || timeZone.isBlank()) {
                return "UTC";
            }
            return timeZone.replace(" ", "+");
    }
}

