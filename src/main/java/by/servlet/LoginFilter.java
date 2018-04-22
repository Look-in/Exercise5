package by.servlet;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@WebFilter("/*")
public class LoginFilter implements Filter {

    private static Map<String, List<String>> pages = new ConcurrentHashMap<>();

    @Override
    public void init(FilterConfig filterConfig) {
        pages.put("client", new ArrayList<>());
        pages.put("bookmaker", new ArrayList<>());
        pages.put("administrator", new ArrayList<>());
        List<String> client  = pages.get("client");
        client.add("/selectitemservlet*");
        client.add("PushItemModify");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);
        String loginURI = request.getContextPath() + "/loginForm";
        boolean loggedIn = session != null && session.getAttribute("role") != null;
        boolean loginRequest = request.getRequestURI().equals(loginURI);
        if (loggedIn || loginRequest) {
            chain.doFilter(request, response);
        } else {
            response.sendRedirect(loginURI);
        }
    }

    @Override
    public void destroy() {
    }

}