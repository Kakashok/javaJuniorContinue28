package controllers;

import db.DBManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "StudentDeleteController", urlPatterns = "/student-delete")
public class StudentDeleteController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ids = req.getParameter("idsHiddenDelete");
        // 1 3 5
        String[] idsToDelete = ids.split(" ");
        for (String id : idsToDelete) {
            DBManager.deleteStudent(id);
        }
        resp.sendRedirect("/students");
    }
}
