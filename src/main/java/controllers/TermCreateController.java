package controllers;

import db.DBManager;
import entity.Discipline;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


@WebServlet(name = "TermCreateController", urlPatterns = "/term-create")
public class TermCreateController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ArrayList<Discipline> disciplines = DBManager.getAllActiveDisciplines();
        req.setAttribute("disciplines", disciplines);
        req.getRequestDispatcher("/WEB-INF/jsp/term-create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String duration = req.getParameter("duration");
        String[] idsDisc = req.getParameterValues("idsDisc");

        if (duration == null || idsDisc == null || duration.equals("")){
            req.setAttribute("error", "1");
            ArrayList<Discipline> disciplines = DBManager.getAllActiveDisciplines();
            req.setAttribute("disciplines", disciplines);
            req.getRequestDispatcher("/WEB-INF/jsp/term-create.jsp").forward(req, resp);
            return;
        }

        String newName = "Семестр " + (DBManager.getLastNumTerm() + 1);
        DBManager.createTerm(newName, duration, idsDisc);
        resp.sendRedirect("/terms");
    }
}
