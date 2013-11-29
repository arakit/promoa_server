package jp.dmtc.ing.promoa.oauth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.dmtc.ing.promoa.CFConst;
import facebook4j.Facebook;
import facebook4j.FacebookFactory;

@WebServlet(name = "signin", urlPatterns = { "/fb/signin" })

public class SignInServlet extends HttpServlet {
    private static final long serialVersionUID = -7453606094644144082L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Facebook facebook = new FacebookFactory().getInstance();
//        request.getSession().setAttribute("facebook", facebook);
//        StringBuffer callbackURL = request.getRequestURL();
//        int index = callbackURL.lastIndexOf("/");
//        callbackURL.replace(index, callbackURL.length(), "").append("/callback");
//        response.sendRedirect(facebook.getOAuthAuthorizationURL(callbackURL.toString()));

    	Facebook fb;
    	fb = new FacebookFactory().getInstance();
        request.getSession().setAttribute(CFConst.SESATT_FACEBOOK, fb);

    	System.out.println("attempt get access token callback.");
        StringBuffer callbackURL = request.getRequestURL();
        int index = callbackURL.lastIndexOf("/");
        callbackURL.replace(index, callbackURL.length(), "").append("/callback");
        System.out.println("redirect="+callbackURL.toString());
        response.sendRedirect(fb.getOAuthAuthorizationURL(callbackURL.toString()));

    }
}
