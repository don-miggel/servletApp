package com.example.demo.session;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Это название 2-х параметров, которые мы передаем
        String user = request.getParameter("user");
        String pwd = request.getParameter("pwd");
        // Это значение наших параметров
//        String userID = "admin";
//        String password = "password";

        if (validateUser(user, pwd)) {
            HttpSession session = request.getSession();
            session.setAttribute("user", "user");
            //setting session to expiry in 30 mins
            session.setMaxInactiveInterval(30 * 60);
            Cookie userName = new Cookie("user", user);
            userName.setMaxAge(30 * 60);
            response.addCookie(userName);
            PrintWriter out = response.getWriter();
            out.println("Welcome back to the team, " + user + "!");
        } else {
            PrintWriter out = response.getWriter();
            out.println("Either user name or password is wrong!");
        }
    }

    private static Map<String, String> initializeUsers(){
        Map<String, String> credentials = new HashMap<>();
        credentials.put("pete", "pete23");
        credentials.put("sara", "sara23");
        credentials.put("toni", "toni12");
        credentials.put("terry", "terry12");
        credentials.put("dan", "dan12");
        return credentials;
    }

    private static boolean validateUser(String name, String password){

        var userList = initializeUsers();
        return userList.keySet().stream()
                .filter(usrName->usrName.equals(name)).findFirst()
                .filter(usrName -> userList.get(usrName).equals(password)).isPresent();
    }
}
