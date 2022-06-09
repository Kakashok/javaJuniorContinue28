package db;

import entity.Group;
import entity.Student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DBManager {
    public static ArrayList<Student> getAllActiveStudent(){
        ArrayList<Student> students = new ArrayList<>();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/students_28_29?user=root&password=QWERTY1234");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT s.id, s.surname, s.name, s.id_group, g.group, s.date FROM student as s\n" +
                    "left join groupe as g on s.id_group = g.id\n" +
                    " where status = '1'");

            while (rs.next()){
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

    public  static int getGroupId(String group){
        /**
         * Проверяет есть ли такая группа в базе. Если есть - отдает ее id
         * Если нет - записываем новую группу в таблицу и возвращаем ее id
         */
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/students_28_29?user=root&password=QWERTY1234");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM groupe as g where g.group = \""+group+"\"");

            while (rs.next()){
                return  rs.getInt("id");
            }

            // здесь код отработает только если в базе нет такой группы
            // вносим в таблицу группа информацию о новой группе
            stmt.execute("INSERT INTO `groupe` (`group`) VALUES ('"+group+"');");
            // достаем id новой созданной группы и возвращаем его
            rs = stmt.executeQuery("SELECT * FROM groupe ORDER BY id DESC LIMIT 1");
            while (rs.next()){
                return rs.getInt("id");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;

    }

    public static void createStudent(String surname, String name, int idGroup, String date){

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/students_28_29?user=root&password=QWERTY1234");
            Statement stmt = conn.createStatement();
            stmt.execute("INSERT INTO `student` (`surname`, `name`, `id_group`, `date`) VALUES ('"+surname+"', '"+name+"', '"+idGroup+"', '"+date+"');");



        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void modifyStudent(String id, String surname, String name, int idGroup, String date){

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/students_28_29?user=root&password=QWERTY1234");
            Statement stmt = conn.createStatement();
            stmt.execute("UPDATE `student` SET `surname` = '"+surname+"', `name` = '"+name+"', `date` = '"+date+"' WHERE (`id` = '"+id+"');\n");



        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void deleteStudent(String id){

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/students_28_29?user=root&password=QWERTY1234");
            Statement stmt = conn.createStatement();
            stmt.execute("UPDATE `student` SET `status` = '0' WHERE (`id` = '"+id+"');");



        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static Student getStudentById(String id){

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/students_28_29?user=root&password=QWERTY1234");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT s.id, s.surname, s.name, s.id_group, g.group, s.date FROM student as s\n" +
                    "left join groupe as g on s.id_group = g.id\n" +
                    " where status = '1' AND s.id = " + id);

            while (rs.next()){
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

}

