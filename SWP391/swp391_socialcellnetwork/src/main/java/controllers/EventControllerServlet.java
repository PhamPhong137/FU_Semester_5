/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dal.CalendarDAO;
import dal.FriendDAO;
import dal.NotifiDAO;
import dal.TagMemberDAO;
import dal.RemindEventDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import models.User;

/**
 *
 * @author PC-Phong
 */
public class EventControllerServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet </title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet  at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // String date = request.getParameter("date");
        int eventid = Integer.parseInt(request.getParameter("eventid"));
        int type = Integer.parseInt(request.getParameter("type"));
        if (type == 0) {
            CalendarDAO.INSTANCE.deleteEvent(eventid);
        } else {
            String title = request.getParameter("title");
            int feq = Integer.parseInt(request.getParameter("feq"));
            String description = request.getParameter("description");
            String place = request.getParameter("place");
            int access = Integer.parseInt(request.getParameter("access"));
            CalendarDAO.INSTANCE.updateEvent(eventid, title, feq, description, place, access);
            NotifiDAO.INSTANCE.updateNotification(eventid, title);
        }
        //      request.getRequestDispatcher("showevent?date=" + date).forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");

        PrintWriter out = response.getWriter();

        String title = request.getParameter("title_event");
        String solardate_row = request.getParameter("solar_date");
        String lunardate_row = request.getParameter("lunar_date");
        int freq = Integer.parseInt(request.getParameter("repeat"));
        String place = request.getParameter("place");
        String description = request.getParameter("description");
        int access = Integer.parseInt(request.getParameter("access"));
        String tagmember[] = request.getParameterValues("tagfriend");

        String firstremind_row = request.getParameter("first_remind");
        String secondremind_row = request.getParameter("second_remind");

        if (!solardate_row.isEmpty()) {
            LocalDate solardate = LocalDate.parse(solardate_row);
            LocalDateTime remindDateTime = solardate.atStartOfDay();
            Timestamp remindTimestamp = Timestamp.valueOf(remindDateTime);
            LocalDateTime now = LocalDateTime.now();
            Timestamp date = Timestamp.valueOf(now);
            if (freq == 1) {
                int eventid = CalendarDAO.INSTANCE.addEvent(currentUser.getUser_id(), title, solardate.toString(), description, freq, place, access);
                if (tagmember != null) {
                    for (int k = 0; k < tagmember.length; k++) {
                        TagMemberDAO.INSTANCE.addTagEvent(eventid, Integer.parseInt(tagmember[k]));
                        int relatiID = FriendDAO.INSTANCE.getRelationId(currentUser.getUser_id(), Integer.parseInt(tagmember[k]));
                        String notificationDescription = "You have been tagged in the event: '" + title + "' by " + currentUser.getF_name() + " " + currentUser.getL_name() + ".";
                        NotifiDAO.INSTANCE.createNotification(Integer.parseInt(tagmember[k]), 3, date, notificationDescription, relatiID);
                        NotifiDAO.INSTANCE.createNotificationRemineEvent(Integer.parseInt(tagmember[k]), 3, date, notificationDescription, remindTimestamp, relatiID, eventid);
                    }
                }
                handleReminder(eventid, solardate_row, firstremind_row, secondremind_row, currentUser.getUser_id());
            } else {
                if (tagmember != null) {
                    for (int k = 0; k < tagmember.length; k++) {
                        int relatiID = FriendDAO.INSTANCE.getRelationId(currentUser.getUser_id(), Integer.parseInt(tagmember[k]));
                        String notificationDescription = "You have been tagged in the event: '" + title + "' by " + currentUser.getF_name() + " " + currentUser.getL_name() + ".";   
                        NotifiDAO.INSTANCE.createNotification(Integer.parseInt(tagmember[k]), 3, date, notificationDescription, relatiID);
                    }
                }
                for (int i = 0; i < 50; i++) {
                    LocalDate nextYearSolarDate = solardate.plusYears(i);
                    LocalDateTime nextYearDateTime = nextYearSolarDate.atStartOfDay();
                    Timestamp nextYearDate = Timestamp.valueOf(nextYearDateTime);
                    int eventid = CalendarDAO.INSTANCE.addEvent(currentUser.getUser_id(), title, nextYearSolarDate.toString(), description, freq, place, access);
                    if (tagmember != null) {
                        for (int k = 0; k < tagmember.length; k++) {
                            TagMemberDAO.INSTANCE.addTagEvent(eventid, Integer.parseInt(tagmember[k]));
                            int relatiID = FriendDAO.INSTANCE.getRelationId(currentUser.getUser_id(), Integer.parseInt(tagmember[k]));
                            String notificationDescription = "You have been tagged in the event: '" + title + "' by " + currentUser.getF_name() + " " + currentUser.getL_name() + ".";
                            NotifiDAO.INSTANCE.createNotificationRemineEvent(Integer.parseInt(tagmember[k]), 3, date, notificationDescription, nextYearDate, relatiID, eventid);
                        }
                    }
                    handleReminder(eventid, solardate_row, firstremind_row, secondremind_row, currentUser.getUser_id());
                }
            }
        } else {
            int year = Integer.parseInt(lunardate_row.substring(0, 4));
            int month = Integer.parseInt(lunardate_row.substring(5, 7));
            int day = Integer.parseInt(lunardate_row.substring(8, 10));
            LocalDate lunardate = LocalDate.parse(lunardate_row);
            String lunardateString = "";
            LocalDateTime now = LocalDateTime.now();
            Timestamp date = Timestamp.valueOf(now);
            if (freq == 1) {
                int[] lunarDate = convertLunar2Solar(day, month, year, 0, 7);
                lunardateString = String.format("%04d-%02d-%02d", lunarDate[2], lunarDate[1], lunarDate[0]);
                LocalDate lunarDateLocal = LocalDate.parse(lunardateString);
                LocalDateTime lunarDateTime = lunarDateLocal.atStartOfDay();
                Timestamp lunarTimestamp = Timestamp.valueOf(lunarDateTime);
                int eventid = CalendarDAO.INSTANCE.addEvent(currentUser.getUser_id(), title, lunardateString, description, freq, place, access);
                if (tagmember != null) {
                    for (int k = 0; k < tagmember.length; k++) {
                        TagMemberDAO.INSTANCE.addTagEvent(eventid, Integer.parseInt(tagmember[k]));
                        int relatiID = FriendDAO.INSTANCE.getRelationId(currentUser.getUser_id(), Integer.parseInt(tagmember[k]));
                        String notificationDescription = "You have been tagged in the event: '" + title + "' by " + currentUser.getF_name() + " " + currentUser.getL_name() + ".";
                        NotifiDAO.INSTANCE.createNotification(Integer.parseInt(tagmember[k]), 3, date, notificationDescription, relatiID);
                        NotifiDAO.INSTANCE.createNotificationRemineEvent(Integer.parseInt(tagmember[k]), 3, date, notificationDescription, lunarTimestamp, relatiID, eventid);
                    }
                }
                handleReminder(eventid, lunardateString, firstremind_row, secondremind_row, currentUser.getUser_id());

            } else {
                if (tagmember != null) {
                    for (int k = 0; k < tagmember.length; k++) {
                        int relatiID = FriendDAO.INSTANCE.getRelationId(currentUser.getUser_id(), Integer.parseInt(tagmember[k]));
                        String notificationDescription = "You have been tagged in the event: '" + title + "' by " + currentUser.getF_name() + " " + currentUser.getL_name() + ".";
                        NotifiDAO.INSTANCE.createNotification(Integer.parseInt(tagmember[k]), 3, date, notificationDescription, relatiID);
                    }
                }
                for (int i = 0; i < 50; i++) {
                    LocalDate nextYearLunarDate = lunardate.plusYears(i);
                    int[] lunarDate = convertLunar2Solar(lunardate.getDayOfMonth(), lunardate.getMonthValue(), nextYearLunarDate.getYear(), 0, 7);
                    lunardateString = String.format("%04d-%02d-%02d", lunarDate[2], lunarDate[1], lunarDate[0]);
                    LocalDate lunarDateLocal = LocalDate.parse(lunardateString);
                    LocalDateTime lunarDateTime = lunarDateLocal.atStartOfDay();
                    Timestamp lunarTimestamp = Timestamp.valueOf(lunarDateTime);
                    int eventid = CalendarDAO.INSTANCE.addEvent(currentUser.getUser_id(), title, lunardateString, description, freq, place, access);
                    if (tagmember != null) {
                        for (int k = 0; k < tagmember.length; k++) {
                            TagMemberDAO.INSTANCE.addTagEvent(eventid, Integer.parseInt(tagmember[k]));
                            int relatiID = FriendDAO.INSTANCE.getRelationId(currentUser.getUser_id(), Integer.parseInt(tagmember[k]));
                            String notificationDescription = "You have been tagged in the event: '" + title + "' by " + currentUser.getF_name() + " " + currentUser.getL_name() + ".";
                            NotifiDAO.INSTANCE.createNotificationRemineEvent(Integer.parseInt(tagmember[k]), 3, date, notificationDescription, lunarTimestamp, relatiID, eventid);
                        }
                    }
                    handleReminder(eventid, lunardateString, firstremind_row, secondremind_row, currentUser.getUser_id());

                }
            }
        }
        response.sendRedirect("home");
    }

    public void handleReminder(int eventID, String eventDate, String firstRemindRow, String secondRemindRow, int userid) {
        if (!firstRemindRow.isEmpty()) {
            LocalDate eventDateLocal = LocalDate.parse(eventDate);
            int firstRemind = Integer.parseInt(firstRemindRow);
            LocalDate firstRemindDate = eventDateLocal.minusDays(firstRemind);
            RemindEventDAO.INSTANCE.addRemindEvent(eventID, null, firstRemindDate.toString(), userid);
        }

        if (!secondRemindRow.isEmpty()) {
            LocalDate eventDateLocal = LocalDate.parse(eventDate);
            int secondRemind = Integer.parseInt(secondRemindRow);
            LocalDate secondRemindDate = eventDateLocal.minusDays(secondRemind);
            RemindEventDAO.INSTANCE.addRemindEvent(eventID, null, secondRemindDate.toString(), userid);

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
