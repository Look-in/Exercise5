package by.servlet;

import by.dao.RaceDao;
import by.dao.old.SelectDefaultItemDao;
import by.dao.old.SelectItemType;
import by.entity.event.AttributeToCompare;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet(
        name = "SelectItemServlet",
        description = "Сервлет для передачи списка товаров",
        urlPatterns = "/selectitemservlet")
public class SelectItemServlet extends javax.servlet.http.HttpServlet {

    @Inject
    SelectDefaultItemDao selectDefaultItemDao;

    @Inject
    SelectItemType selectItemType;

    @Inject
    RaceDao raceDao;

    private void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("sortBy", AttributeToCompare.values());
        /*if (request.getParameter("sortingBy") != null) {
            ItemComparator.compare(item, AttributeToCompare.valueOf(request.getParameter("sortingBy")));
        }*/
        request.setAttribute("race", raceDao.getRaces());
        request.getRequestDispatcher("jsp/item_list.jsp").forward(request, response);
    }

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doRequest(request, response);
    }

}
