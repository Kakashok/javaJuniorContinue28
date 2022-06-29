package controllers;

import db.DBManager;
import entity.Role;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "LoginController", urlPatterns = "/login")
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ArrayList<Role> roles = DBManager.getAllRoles();
        req.setAttribute("roles", roles);
        req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String role = req.getParameter("role");

        if (login.equals("") || password.equals("") || role.equals("")) {
            req.setAttribute("error", "2");
            ArrayList<Role> roles = DBManager.getAllRoles();
            req.setAttribute("roles", roles);
            req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
            return;
        }

        if (DBManager.canLogin(login, password, role)) {
            req.getSession().setAttribute("isLogin", "true");
            req.getSession().setAttribute("role", role);
            req.getSession().setAttribute("login", login);
            resp.sendRedirect("/");
        } else {
            req.setAttribute("error", "1");

            ArrayList<Role> roles = DBManager.getAllRoles();
            req.setAttribute("roles", roles);
            req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
        }

    }
}
