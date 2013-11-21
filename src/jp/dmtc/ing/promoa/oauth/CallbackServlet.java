package jp.dmtc.ing.promoa.oauth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import facebook4j.Facebook;

@WebServlet(name = "callback", urlPatterns = { "/fb/callback" })
public class CallbackServlet extends HttpServlet {
    private static final long serialVersionUID = 6305643034487441839L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Facebook facebook = (Facebook) request.getSession().getAttribute("facebook");
        String oauthCode = request.getParameter("code");
        System.out.println("code="+oauthCode);
//        try {
//            facebook.getOAuthAccessToken(oauthCode);
//        } catch (FacebookException e) {
//            throw new ServletException(e);
//        }
        System.out.println("ridirect="+request.getContextPath() + "/");
        response.sendRedirect(request.getContextPath() + "/");
    }
}
