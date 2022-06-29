package controllers;

import db.DBManager;
import entity.Mark;
import entity.Student;
import entity.Term;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "StudentProgressController", urlPatterns = "/student-progress")
public class StudentProgressController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStud = req.getParameter("idHiddenProgress");
        String idTerm = req.getParameter("idTerm");

        // 1 - вытянуть студента по id
        // 2 - вытянуть все активные семестры
        // 3 - вытянуть выбранный семестр
        // 4 - вытянуть все оценки с дисциплинами по выбранному семестру
        // 5 - посчитать и передать средний балл

        Student student = DBManager.getStudentById(idStud);
        ArrayList<Term> terms = DBManager.getAllActiveTerms();


        if (idTerm == null || idTerm.equals("")) {
            if (terms.size() != 0) {
                idTerm = terms.get(0).getId() + "";
            } else {
                idTerm = "-1";
            }
        }

        Term selectedTerm = DBManager.getTermById(idTerm);
        ArrayList<Mark> marks = DBManager.getMarks(idStud, idTerm);

        // <% DBManager.avgMark(marks); %>
        // 3 3 4 5 4    -1 -1 -1 -1 -1

        double summMarks = 0;

        for (Mark m : marks) {
            if (m.getMark() != -1) {
                summMarks = summMarks + m.getMark();
            }
        }

        if (marks.size() > 0 && summMarks != 0){
            req.setAttribute("avgMark", (summMarks / marks.size()));
        }else {
            req.setAttribute("avgMark", "0.0");
        }


        req.setAttribute("student", student);
        req.setAttribute("terms", terms);
        req.setAttribute("selectedTerm", selectedTerm);
        req.setAttribute("marks", marks);

        req.getRequestDispatcher("/WEB-INF/jsp/student-progress.jsp").forward(req, resp);
    }
}
