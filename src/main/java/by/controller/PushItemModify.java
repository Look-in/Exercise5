package by.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "PushItemModify",
        description = "Сервлет для передачи списка товаров",
        urlPatterns = "/pushitemmodify")

public class PushItemModify extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     /*   Item item = ItemFactory.createItem(Integer.valueOf(request.getParameter("itemType")));
        ChangeInstance dao = ItemFactory.getItemInstanceDao(Integer.valueOf(request.getParameter("itemType")));
        switch (request.getParameter("action")) {
            case "ADD":
                dao.create(item);
                break;
            case "EDIT":
                dao.update(item);
                break;
            case "DELETE":
                dao.delete(item.getItemId());
                break;
        }*/
        request.getRequestDispatcher("selectitemservlet").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
