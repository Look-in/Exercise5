package by.controller.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Фильтр вытягивает переданные в сессию сообщения и передает
 * в request.
 *
 * @author Serg Shankunas
 */
public class MessageFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    private String getAndNullSessionMessage(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) return null;
        String message = (String) session.getAttribute("message");
        session.removeAttribute("message");
        return message;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        request.setAttribute("message", getAndNullSessionMessage(request));
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

}