package jp.dmtc.ing.promoa.oauth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.dmtc.ing.promoa.CFConst;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.auth.AccessToken;

@WebServlet(name = "callback", urlPatterns = { "/fb/callback" })
public class CallbackServlet extends HttpServlet {
    private static final long serialVersionUID = 6305643034487441839L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Facebook fb = (Facebook) request.getSession().getAttribute(CFConst.SESATT_FACEBOOK);
        String oauthCode = request.getParameter("code");
        System.out.println("code="+oauthCode);
        try {
            AccessToken at = fb.getOAuthAccessToken(oauthCode);
            fb.setOAuthAccessToken(at);
            request.getSession().setAttribute(CFConst.SESATT_ACCESS_TOKEN, at);
        } catch (FacebookException e) {
            throw new ServletException(e);
        }
        String ridirec_url = request.getContextPath() + "/";
        System.out.println("ridirect="+ridirec_url);
        response.sendRedirect(ridirec_url);
    }
}
