package by.controller;

import by.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@WebFilter("/*")
public class LoginFilter implements Filter {

    private Map<String, String[]> pages = new ConcurrentHashMap<>();

    @Override
    public void init(FilterConfig filterConfig) {
        pages.put("/view.*", new String[]{"client", "bookmaker", "administrator"});
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);
        String redirectURI = request.getContextPath() + "/loginForm";
        String[] pageAccessRoles = null;
        boolean loggedIn = session != null && session.getAttribute("user") != null;
        boolean loginRequest = request.getRequestURI().equals(redirectURI);
        boolean accessURI = false;
        for (Map.Entry<String, String[]> entry : pages.entrySet()) {
            if (request.getRequestURI().matches(entry.getKey())) {
                pageAccessRoles = entry.getValue();
                break;
            }
        }
        if (pageAccessRoles != null && loggedIn) {
            User user = (User) session.getAttribute("user");
            if (Arrays.asList(pageAccessRoles).contains(user.getRole().getRole())) {
                accessURI = true;
            } else {
                redirectURI = request.getContextPath() + "/AccessDenied";
            }
        }
        if (pageAccessRoles == null || accessURI || loginRequest) {
            chain.doFilter(request, response);
        } else {
            response.sendRedirect(redirectURI);
        }
    }

    @Override
    public void destroy() {
    }

}