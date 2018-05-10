package by.controller.login;

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
        pages.put("/view1.*", new String[]{"client", "bookmaker", "administrator"});
        pages.put("/modify.*", new String[]{"bookmaker", "administrator"});
    }

    private String[] getURIAccessRoles(String requestURI) {
        return pages.entrySet().stream().filter(k -> requestURI.matches(k.getKey())).findFirst()
                .map(Map.Entry::getValue).orElse(null);
    }

    private User getSessionUser(HttpSession session) {
        if (session == null) return null;
        return (User) session.getAttribute("user");
    }

    private String validateRedirectAccessURI(HttpServletRequest request, HttpSession session) {
        String[] pageAccessRoles = getURIAccessRoles(request.getRequestURI());
        if (pageAccessRoles == null) {
            return null;
        }
        User user = getSessionUser(session);
        if (user == null) {
            return request.getContextPath() + "/loginForm";
        }
        if (Arrays.asList(pageAccessRoles).contains(user.getRole().getRole())) {
            return null;
        }
        return request.getContextPath() + "/AccessDenied";
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);
        String redirectURI = validateRedirectAccessURI(request, session);
        if (redirectURI == null) {
            request.setCharacterEncoding("UTF-8");
            response.setContentType("text/html; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            chain.doFilter(request, response);
        } else {
            response.sendRedirect(redirectURI);
        }
    }

    @Override
    public void destroy() {
    }

}