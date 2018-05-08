package by.controller;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "ViewItemModify",
        description = "Сервлет для отображения страницы модификации товара",
        urlPatterns = "/viewitemmodify")

public class ViewItemModify extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("itemservlet").forward(request, response);
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       /* Item item = ItemFactory.createItem(Integer.valueOf(request.getParameter("itemType")));
        if (request.getParameter("itemId") != null) {
            item.setItemId(Integer.valueOf(request.getParameter("itemId")));
            ItemFactory.getSelectItemDao(Integer.valueOf(request.getParameter("itemType"))).readItem(item);
        }
        request.setAttribute("item", item);
       // request.setAttribute("statuses", ItemFactory.getItemStatusDao().readItemStatus());
     /*   request.getRequestDispatcher(String.format("jsp/%smodify.jsp", ItemFactory.getItemTypeDao()
                .readItemType(Integer.valueOf(request.getParameter("itemType"))).toLowerCase())).forward(request, response);*/
    }

}
