package controllers;

import db.DBManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@WebServlet(name = "StudentCreateController", urlPatterns = "/student-create")
public class StudentCreateController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/student-create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String surname = req.getParameter("surname");
        String name = req.getParameter("name");
        String group = req.getParameter("group");
        String date = req.getParameter("date");

        if (surname.equals("") || name.equals("") || group.equals("") || date.equals("")){
            req.setAttribute("error", "1");
            req.getRequestDispatcher("/WEB-INF/jsp/student-create.jsp").forward(req, resp);
            return;
        }
        // string ---> Date
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        Date dateFromUser = null;
        try {
            dateFromUser = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Date --->String (for DateBase)
        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateToDatabase = formatter.format(dateFromUser);

        int idGroup = DBManager.getGroupId(group);
        DBManager.createStudent(surname, name, idGroup, dateToDatabase);
        resp.sendRedirect("/students");


    }
}
