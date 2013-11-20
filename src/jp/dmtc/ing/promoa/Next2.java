package jp.dmtc.ing.promoa;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.crudefox.server.util.CFServletParams;
import jp.crudefox.server.util.TextUtil;
import jp.dmtc.ing.promoa.beans.Next2Bean;

/**
 * Servlet implementation class Create
 */
@WebServlet(name = "next2", urlPatterns = { "/next2" })
public class Next2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Next2() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doProc(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doProc(request, response);
	}


	protected void doProc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try{
			//response.setContentType("application/json; charset=utf-8");

			HttpSession ses = request.getSession();
			if( ses.isNew() ){
				response.sendError( HttpServletResponse.SC_BAD_REQUEST , "セッションが以上です。");
				return ;
			}

			CFServletParams params = new CFServletParams(this, request, response, new File("/upfiles"));

			//データ初期化
			for(String name : Collections.list(ses.getAttributeNames()) ){
				if( name.startsWith("image") ){
					ses.removeAttribute(name);
				}
			}

			java.util.List<String> image_names = new ArrayList<String>();
			for(String name : params.getNames()){
				System.out.println("gg:"+name);
				if( name.startsWith("image") ){
					String on = params.getStringParam(name);
					if(TextUtil.isEmpty(on)) continue;
					image_names.add(on);
				}
			}

			for(int i=0;i<image_names.size();i++){
				ses.setAttribute("image"+i, image_names.get(i));
			}

			Next2Bean b = new Next2Bean();

			for(String name : Collections.list( ses.getAttributeNames()) ){
				if(!name.startsWith("image")) continue ;
				b.names.add((String)ses.getAttribute(name));
				System.out.println("jj:"+name+":"+ses.getAttribute(name));
			}
			b.src_image = (String) ses.getAttribute("src_image");


			ses.setAttribute("n2", b);
		    //ViewであるJSPを呼び出す
		    RequestDispatcher rDispatcher =
		     request.getRequestDispatcher("/next2.jsp");
		    rDispatcher.forward(request, response);

		}catch(Exception e){
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
		}

	}


}
