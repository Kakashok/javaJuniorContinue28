package db;

import constants.Constants;
import entity.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DBManager {
    public static ArrayList<Student> getAllActiveStudent() {
        ArrayList<Student> students = new ArrayList<>();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(Constants.CONNECTION_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT s.id, s.surname, s.name, s.id_group, g.group, s.date FROM student as s\n" +
                    "left join groupe as g on s.id_group = g.id\n" +
                    " where status = '1'");

            while (rs.next()) {
                // System.out.println(rs.getInt("id"));
                // System.out.println(rs.getString("surname"));
                // System.out.println(rs.getString("name"));

                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name"));
                student.setSurname(rs.getString("surname"));

                Group group = new Group();
                group.setId(rs.getInt("id_group"));
                group.setGroup(rs.getString("group"));

                student.setGroup(group);
                student.setDate(rs.getDate("date"));

                students.add(student);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return students;

    }

    public static int getGroupId(String group) {
        /**
         * Проверяет есть ли такая группа в базе. Если есть - отдает ее id
         * Если нет - записываем новую группу в таблицу и возвращаем ее id
         */
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(Constants.CONNECTION_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM groupe as g where g.group = \"" + group + "\"");

            while (rs.next()) {
                return rs.getInt("id");
            }

            // здесь код отработает только если в базе нет такой группы
            // вносим в таблицу группа информацию о новой группе
            stmt.execute("INSERT INTO `groupe` (`group`) VALUES ('" + group + "');");
            // достаем id новой созданной группы и возвращаем его
            rs = stmt.executeQuery("SELECT * FROM groupe ORDER BY id DESC LIMIT 1");
            while (rs.next()) {
                return rs.getInt("id");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;

    }

    public static void createStudent(String surname, String name, int idGroup, String date) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(Constants.CONNECTION_URL);
            Statement stmt = conn.createStatement();
            stmt.execute("INSERT INTO `student` (`surname`, `name`, `id_group`, `date`) VALUES ('" + surname + "', '" + name + "', '" + idGroup + "', '" + date + "');");


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void modifyStudent(String id, String surname, String name, int idGroup, String date) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(Constants.CONNECTION_URL);
            Statement stmt = conn.createStatement();
            stmt.execute("UPDATE `student` SET `surname` = '" + surname + "', `name` = '" + name + "', `date` = '" + date + "' WHERE (`id` = '" + id + "');\n");


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void deleteStudent(String id) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(Constants.CONNECTION_URL);
            Statement stmt = conn.createStatement();
            stmt.execute("UPDATE `student` SET `status` = '0' WHERE (`id` = '" + id + "');");


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static Student getStudentById(String id) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(Constants.CONNECTION_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT s.id, s.surname, s.name, s.id_group, g.group, s.date FROM student as s\n" +
                    "left join groupe as g on s.id_group = g.id\n" +
                    " where status = '1' AND s.id = " + id);

            while (rs.next()) {
                // System.out.println(rs.getInt("id"));
                // System.out.println(rs.getString("surname"));
                // System.out.println(rs.getString("name"));

                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name"));
                student.setSurname(rs.getString("surname"));

                Group group = new Group();
                group.setId(rs.getInt("id_group"));
                group.setGroup(rs.getString("group"));

                student.setGroup(group);
                student.setDate(rs.getDate("date"));

                return student;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public static ArrayList<Term> getAllActiveTerms() {
        ArrayList<Term> terms = new ArrayList<>();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(Constants.CONNECTION_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM term where status = 1");

            while (rs.next()) {

                Term term = new Term();
                term.setId(rs.getInt("id"));
                term.setTerm(rs.getString("term"));
                term.setDuration(rs.getString("duration"));

                terms.add(term);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return terms;
    }

    public static Term getTermById(String idTerm) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(Constants.CONNECTION_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM term where status = 1 AND id=" + idTerm);

            while (rs.next()) {

                Term term = new Term();
                term.setId(rs.getInt("id"));
                term.setTerm(rs.getString("term"));
                term.setDuration(rs.getString("duration"));

                return term;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ArrayList<Mark> getMarks(String idStud, String idTerm) {

        ArrayList<Mark> marks = new ArrayList<>();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(Constants.CONNECTION_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT d.id, d.discipline, m.mark FROM mark as m\n" +
                    "left join term_discipline as td on m.id_term_discipline = td.id\n" +
                    "left join discipline as d on td.id_discipline = d.id\n" +
                    "where m.id_student = " + idStud + " and td.id_term = " + idTerm);

            while (rs.next()) {
                Mark mark = new Mark();
                mark.setMark(rs.getInt("mark"));
                Discipline discipline = new Discipline();
                discipline.setId(rs.getInt("id"));
                discipline.setDiscipline(rs.getString("discipline"));
                mark.setDiscipline(discipline);

                marks.add(mark);
            }

            if (marks.isEmpty()) {
                rs = stmt.executeQuery("SELECT * FROM term_discipline as td\n" +
                        "left join discipline as d on td.id_discipline = d.id\n" +
                        "where td.id_term = " + idTerm + " and d.status = '1'");

                while (rs.next()) {
                    Mark mark = new Mark();
                    mark.setMark(-1);
                    Discipline discipline = new Discipline();
                    discipline.setId(rs.getInt("id_discipline"));
                    discipline.setDiscipline(rs.getString("discipline"));
                    mark.setDiscipline(discipline);

                    marks.add(mark);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return marks;
    }

    public static ArrayList<Discipline> getAllActiveDisciplinesByTerm(String idTerm) {
        ArrayList<Discipline> disciplines = new ArrayList<>();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(Constants.CONNECTION_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM term_discipline as td\n" +
                    "left join discipline as d on td.id_discipline = d.id\n" +
                    "where td.id_term = " + idTerm + " and d.status = '1'");

            while (rs.next()) {

                Discipline discipline = new Discipline();
                discipline.setId(rs.getInt("id_discipline"));
                discipline.setDiscipline(rs.getString("discipline"));
                disciplines.add(discipline);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return disciplines;
    }

    public static void deleteTerm(String id) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(Constants.CONNECTION_URL);
            Statement stmt = conn.createStatement();
            stmt.execute("UPDATE `term` SET `status` = '0' WHERE (`id` = '" + id + "');");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Discipline> getAllActiveDisciplines() {
        ArrayList<Discipline> disciplines = new ArrayList<>();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(Constants.CONNECTION_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM discipline where status = '1'");

            while (rs.next()) {

                Discipline discipline = new Discipline();
                discipline.setId(rs.getInt("id"));
                discipline.setDiscipline(rs.getString("discipline"));
                disciplines.add(discipline);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return disciplines;
    }

    public static int getLastNumTerm() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(Constants.CONNECTION_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SSELECT * FROM term where status = '1' ORDER BY ID DESC LIMIT 1");

            while (rs.next()) {
                String name = rs.getString("term");
                name = name.replace("Семестр = ", "");
                return Integer.parseInt(name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void createTerm(String name, String duration, String[] disciplines) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(Constants.CONNECTION_URL);
            Statement stmt = conn.createStatement();
            stmt.execute("INSERT INTO `term` (`term`, `duration`) VALUES ('" + name + "', '" + duration + "');\n");
            ResultSet rs = stmt.executeQuery("SELECT * FROM term ORDER BY ID DESC LIMIT 1");
            int idTerm = -1;
            while (rs.next()) {
                idTerm = rs.getInt("id");
            }
            for (String idDisc : disciplines) {
                stmt.execute("INSERT INTO `term_discipline` (`id_term`, `id_discipline`) VALUES ('" + idTerm + "', '" + idDisc + "');\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void modifyTerm(String id, String duration, String[] disciplines) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(Constants.CONNECTION_URL);
            Statement stmt = conn.createStatement();
            stmt.execute("UPDATE `term` SET `duration` = '" + duration + "' WHERE (`id` = '" + id + "');");
            stmt.execute("DELETE FROM `term_discipline` WHERE (`id_term` = '" + id + "');");

            for (String idDisc : disciplines) {
                stmt.execute("INSERT INTO `term_discipline` (`id_term`, `id_discipline`) VALUES ('" + id + "', '" + idDisc + "');\n");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void modifyTerm(String id, String duration) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(Constants.CONNECTION_URL);
            Statement stmt = conn.createStatement();
            stmt.execute("UPDATE `term` SET `duration` = '" + duration + "' WHERE (`id` = '" + id + "');");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Role> getAllRoles() {
        ArrayList<Role> roles = new ArrayList<>();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(Constants.CONNECTION_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM role");

            while (rs.next()) {
                Role role = new Role();
                role.setId(rs.getInt("id"));
                role.setRole(rs.getString("role"));
                roles.add(role);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return roles;
    }

    public static boolean canLogin(String login, String password, String role) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(Constants.CONNECTION_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM user_role as ur\n" +
                    "left join user as u on ur.id_user = u.id\n" +
                    "where ur.id_role = '"+role+"' and u.user = '"+login+"' and u.password = '"+password+"'");

            while (rs.next()) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


}

