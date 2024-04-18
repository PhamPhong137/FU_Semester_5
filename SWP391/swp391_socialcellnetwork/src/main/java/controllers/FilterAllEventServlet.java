/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dal.CalendarDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import models.Event;
import models.EventUser;
import models.User;

/**
 *
 * @author PC-Phong
 */
public class FilterAllEventServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet FilterAllEventServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FilterAllEventServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        PrintWriter out = response.getWriter();

        int eventid = Integer.parseInt(request.getParameter("eventid"));
        Event getInfoByEventId = CalendarDAO.INSTANCE.getInfoByEventId(eventid);
        out.print("<table>\n"
                + "                                                        <tr>\n"
                + "                                                            <td>\n"
                + "                                                                <i class=\"fas fa-cube\" style=\"color: blue\"></i>\n"
                + "                                                            </td>\n"
                + "                                                            <td>\n"
                + "                                                                <div>\n"
                + "                                                                    <p style=\"font-weight: 600; font-size: 23px\">Hello</p>\n"
                + "                                                                    <p>" + eventid + "</p>\n"
                + "                                                                </div>\n"
                + "                                                            </td>\n"
                + "                                                        </tr>\n"
                + "                                                        <tr>\n"
                + "                                                            <td>\n"
                + "                                                                <i class=\"fas fa-map-marker-alt\"></i>\n"
                + "                                                            </td>\n"
                + "                                                            <td>\n"
                + "                                                                HaNoi - Hola\n"
                + "                                                            </td>\n"
                + "                                                        </tr>\n"
                + "                                                        <tr>\n"
                + "                                                            <td>\n"
                + "                                                                <i class=\"fas fa-bell\"></i>\n"
                + "                                                            </td>\n"
                + "                                                            <td>\n"
                + "                                                                10 minutes before\n"
                + "                                                            </td>\n"
                + "                                                        </tr>\n"
                + "                                                        <tr>\n"
                + "                                                            <td>\n"
                + "                                                                <i class=\"fas fa-calendar-week\"></i>\n"
                + "                                                            </td>\n"
                + "                                                            <td>\n"
                + "                                                                Pham Phong\n"
                + "                                                            </td>\n"
                + "                                                        </tr>\n"
                + "                                                    </table>");
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        PrintWriter out = response.getWriter();

        String month_raw = request.getParameter("month");
        String year = month_raw.substring(0, 4);
        String month = month_raw.substring(5, 7);
        List<EventUser> listEventMonth = CalendarDAO.INSTANCE.loadAllEventByMonthAndYear(currentUser.getUser_id(), month, year);
        if (!listEventMonth.isEmpty()) {
            Map<String, List<EventUser>> eventsByDay = new TreeMap<>();

            for (EventUser event : listEventMonth) {
                String date = event.getStart_date().toString().substring(0, 10); // "YYYY-MM-DD"
                eventsByDay.computeIfAbsent(date, k -> new ArrayList<>()).add(event);
            }
            out.println(" <table>\n"
                    + "                                            <thead>\n"
                    + "                                                <tr class=\"table-header\">\n"
                    + "                                                    <th>\n"
                    + "                                                        Date\n"
                    + "                                                    </th>\n"
                    + "                                                    <th></th>\n"
                    + "                                                    <th>\n"
                    + "                                                        <p>Creater</p>\n"
                    + "                                                        <p>Title </p>\n"
                    + "                                                        <p>Description</p>\n"
                    + "                                                    </th>\n"
                    + "                                                </tr>    \n"
                    + "                                            </thead>\n"
                    + "                                            <tbody>");

            for (Map.Entry<String, List<EventUser>> entry : eventsByDay.entrySet()) {
                String date = entry.getKey();
                List<EventUser> events = entry.getValue();
                int lunar_day = Integer.parseInt(date.substring(8, 10));
                int lunar_month = Integer.parseInt(date.substring(5, 7));
                int lunar_year = Integer.parseInt(date.substring(0, 4));

                // convert to lunar day
                int[] lunarDate = convertSolar2Lunar(lunar_day, lunar_month, lunar_year, 7);
                out.println("<tr><td><div>\n"
                        + "                                                            <h4 class=\"no-padding-margin\"> " + date.substring(8, 10) + "/" + date.substring(5, 7) + "</h4>\n"
                        + "                                                            <p style=\"font-size: 12px\"> " + lunarDate[0] + "/" + lunarDate[1] + " </p>\n"
                        + "                                                        </div></td> <td></td>");

                out.println("<td>");
                for (int i = 0; i < events.size(); i++) {
                    out.println("<div style=\"cursor: pointer;\" onclick=\"openEventdetail(" + events.get(i).getEvent_id() + ")\">\n"
                            + "                                                            <p>" + events.get(i).getName() + "</p>\n"
                            + "                                                            <p><i class=\"fas fa-circle\" style=\"color: " + events.get(i).getColor() + "\"></i> " + events.get(i).getTitle() + "</p>\n"
                            + "                                                            <p>" + events.get(i).getDescription() + "</p>\n"
                            + "                                                        </div>");
                }
            }
            out.println("</td></tr>  </tbody></table>");
        } else {
            out.println("<div class=\"alert alert-warning alert-dismissible fade show\" role=\"alert\" style=\"width: 100%\">\n"
                    + "\n"
                    + "                                        <h5 style=\"font-weight: 600\">Don't have any event on this month </h5>  \n"
                    + "                                    </div>");
        }

    }

    public static final double PI = Math.PI;

    public static int INT(double d) {
        return (int) Math.floor(d);
    }

    public static int jdFromDate(int dd, int mm, int yy) {
        int a, y, m, jd;
        a = INT((14 - mm) / 12);
        y = yy + 4800 - a;
        m = mm + 12 * a - 3;
        jd = dd + INT((153 * m + 2) / 5) + 365 * y + INT(y / 4) - INT(y / 100) + INT(y / 400) - 32045;
        if (jd < 2299161) {
            jd = dd + INT((153 * m + 2) / 5) + 365 * y + INT(y / 4) - 32083;
        }
        return jd;
    }

    public static int[] jdToDate(int jd) {
        int a, b, c, d, e, m, day, month, year;
        if (jd > 2299160) {
            a = jd + 32044;
            b = INT((4 * a + 3) / 146097);
            c = a - INT((b * 146097) / 4);
        } else {
            b = 0;
            c = jd + 32082;
        }
        d = INT((4 * c + 3) / 1461);
        e = c - INT((1461 * d) / 4);
        m = INT((5 * e + 2) / 153);
        day = e - INT((153 * m + 2) / 5) + 1;
        month = m + 3 - 12 * INT(m / 10);
        year = b * 100 + d - 4800 + INT(m / 10);
        return new int[]{day, month, year};
    }

    public static double NewMoon(int k) {
        double T, T2, T3, dr, Jd1, M, Mpr, F, C1, deltat, JdNew;
        T = k / 1236.85; // Time in Julian centuries from 1900 January 0.5
        T2 = T * T;
        T3 = T2 * T;
        dr = PI / 180;
        Jd1 = 2415020.75933 + 29.53058868 * k + 0.0001178 * T2 - 0.000000155 * T3;
        Jd1 = Jd1 + 0.00033 * Math.sin((166.56 + 132.87 * T - 0.009173 * T2) * dr); // Mean new moon
        M = 359.2242 + 29.10535608 * k - 0.0000333 * T2 - 0.00000347 * T3; // Sun's mean anomaly
        Mpr = 306.0253 + 385.81691806 * k + 0.0107306 * T2 + 0.00001236 * T3; // Moon's mean anomaly
        F = 21.2964 + 390.67050646 * k - 0.0016528 * T2 - 0.00000239 * T3; // Moon's argument of latitude
        C1 = (0.1734 - 0.000393 * T) * Math.sin(M * dr) + 0.0021 * Math.sin(2 * dr * M);
        C1 = C1 - 0.4068 * Math.sin(Mpr * dr) + 0.0161 * Math.sin(dr * 2 * Mpr);
        C1 = C1 - 0.0004 * Math.sin(dr * 3 * Mpr);
        C1 = C1 + 0.0104 * Math.sin(dr * 2 * F) - 0.0051 * Math.sin(dr * (M + Mpr));
        C1 = C1 - 0.0074 * Math.sin(dr * (M - Mpr)) + 0.0004 * Math.sin(dr * (2 * F + M));
        C1 = C1 - 0.0004 * Math.sin(dr * (2 * F - M)) - 0.0006 * Math.sin(dr * (2 * F + Mpr));
        C1 = C1 + 0.001 * Math.sin(dr * (2 * F - Mpr)) + 0.0005 * Math.sin(dr * (2 * Mpr + M));
        if (T < -11) {
            deltat = 0.001 + 0.000839 * T + 0.0002261 * T2 - 0.00000845 * T3 - 0.000000081 * T * T3;
        } else {
            deltat = -0.000278 + 0.000265 * T + 0.000262 * T2;
        }
        JdNew = Jd1 + C1 - deltat;
        return JdNew;
    }

    public static double SunLongitude(double jdn) {
        double T, T2, dr, M, L0, DL, L;
        T = (jdn - 2451545.0) / 36525; // Time in Julian centuries from 2000-01-01 12:00:00 GMT
        T2 = T * T;
        dr = PI / 180; // degree to radian
        M = 357.5291 + 35999.0503 * T - 0.0001559 * T2 - 0.00000048 * T * T2; // mean anomaly, degree
        L0 = 280.46645 + 36000.76983 * T + 0.0003032 * T2; // mean longitude, degree
        DL = (1.9146 - 0.004817 * T - 0.000014 * T2) * Math.sin(dr * M);
        DL = DL + (0.019993 - 0.000101 * T) * Math.sin(dr * 2 * M) + 0.00029 * Math.sin(dr * 3 * M);
        L = L0 + DL; // true longitude, degree
        L = L * dr;
        L = L - PI * 2 * INT(L / (PI * 2)); // Normalize to (0, 2*PI)
        return L;
    }

    public static int getSunLongitude(double dayNumber, double timeZone) {
        return INT((SunLongitude(dayNumber - 0.5 - timeZone / 24) / PI) * 6);
    }

    public static int getNewMoonDay(int k, double timeZone) {
        return INT(NewMoon(k) + 0.5 + timeZone / 24);
    }

    public static int getLunarMonth11(int yy, double timeZone) {
        int k, off, nm, sunLong;
        off = jdFromDate(31, 12, yy) - 2415021;
        k = INT(off / 29.530588853);
        nm = getNewMoonDay(k, timeZone);
        sunLong = getSunLongitude(nm, timeZone); // sun longitude at local midnight
        if (sunLong >= 9) {
            nm = getNewMoonDay(k - 1, timeZone);
        }
        return nm;
    }

    public static int getLeapMonthOffset(int a11, double timeZone) {
        int k, last, arc, i;
        k = INT((a11 - 2415021.076998695) / 29.530588853 + 0.5);
        last = 0;
        i = 1; // We start with the month following lunar month 11
        arc = getSunLongitude(getNewMoonDay(k + i, timeZone), timeZone);
        do {
            last = arc;
            i++;
            arc = getSunLongitude(getNewMoonDay(k + i, timeZone), timeZone);
        } while (arc != last && i < 14);
        return i - 1;
    }

    public static int[] convertSolar2Lunar(int dd, int mm, int yy, double timeZone) {
        int k, dayNumber, monthStart, a11, b11, lunarDay, lunarMonth, lunarYear, lunarLeap, diff;
        dayNumber = jdFromDate(dd, mm, yy);
        k = INT((dayNumber - 2415021.076998695) / 29.530588853);
        monthStart = getNewMoonDay(k + 1, timeZone);
        if (monthStart > dayNumber) {
            monthStart = getNewMoonDay(k, timeZone);
        }
        a11 = getLunarMonth11(yy, timeZone);
        b11 = a11;
        if (a11 >= monthStart) {
            lunarYear = yy;
            a11 = getLunarMonth11(yy - 1, timeZone);
        } else {
            lunarYear = yy + 1;
            b11 = getLunarMonth11(yy + 1, timeZone);
        }
        lunarDay = dayNumber - monthStart + 1;
        diff = INT((monthStart - a11) / 29);
        lunarLeap = 0;
        lunarMonth = diff + 11;
        if (b11 - a11 > 365) {
            int leapMonthDiff = getLeapMonthOffset(a11, timeZone);
            if (diff >= leapMonthDiff) {
                lunarMonth = diff + 10;
                if (diff == leapMonthDiff) {
                    lunarLeap = 1;
                }
            }
        }
        if (lunarMonth > 12) {
            lunarMonth = lunarMonth - 12;
        }
        if (lunarMonth >= 11 && diff < 4) {
            lunarYear -= 1;
        }
        return new int[]{lunarDay, lunarMonth, lunarYear, lunarLeap};
    }

    public static int[] convertLunar2Solar(int lunarDay, int lunarMonth, int lunarYear, int lunarLeap, double timeZone) {
        int k, a11, b11, off, leapOff, leapMonth, monthStart;
        if (lunarMonth < 11) {
            a11 = getLunarMonth11(lunarYear - 1, timeZone);
            b11 = getLunarMonth11(lunarYear, timeZone);
        } else {
            a11 = getLunarMonth11(lunarYear, timeZone);
            b11 = getLunarMonth11(lunarYear + 1, timeZone);
        }
        k = INT(0.5 + (a11 - 2415021.076998695) / 29.530588853);
        off = lunarMonth - 11;
        if (off < 0) {
            off += 12;
        }
        if (b11 - a11 > 365) {
            leapOff = getLeapMonthOffset(a11, timeZone);
            leapMonth = leapOff - 2;
            if (leapMonth < 0) {
                leapMonth += 12;
            }
            if (lunarLeap != 0 && lunarMonth != leapMonth) {
                return new int[]{0, 0, 0}; // Invalid date
            } else if (lunarLeap != 0 || off >= leapOff) {
                off += 1;
            }
        }
        monthStart = getNewMoonDay(k + off, timeZone);
        return jdToDate(monthStart + lunarDay - 1);
    }
}