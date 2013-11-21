package jp.dmtc.ing.promoa;

import java.io.File;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.crudefox.server.util.CFServletParams;
import jp.crudefox.server.util.TextUtil;
import jp.dmtc.ing.promoa.beans.MBean;
import jp.dmtc.ing.promoa.data.MData;
import facebook4j.Facebook;

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
				response.sendError( HttpServletResponse.SC_BAD_REQUEST , "セッションが異常です。");
				return ;
			}

			Facebook fb = (Facebook) ses.getAttribute(CFConst.SESATT_FACEBOOK);
			MData md = (MData) ses.getAttribute(CFConst.SESATT_MOSAIC_DATA);


			CFServletParams params = new CFServletParams(this, request, response, new File("/upfiles"));

//			//データ初期化
//			for(String name : Collections.list(ses.getAttributeNames()) ){
//				if( name.startsWith("image") ){
//					ses.removeAttribute(name);
//				}
//			}
			md.select_images_id.clear();

			//java.util.List<String> image_names = new ArrayList<String>();
			for(String name : params.getNames()){
				System.out.println("gg:"+name);
				if( name.startsWith("image") ){
					String on = params.getStringParam(name);
					if(TextUtil.isEmpty(on)) continue;
					if(!md.image_urls.containsKey(on)) continue;
					//image_names.add(on);
					md.select_images_id.add(on);
				}
			}

//			for(int i=0;i<image_names.size();i++){
//				ses.setAttribute("image"+i, image_names.get(i));
//			}

			MBean b = new MBean();

			for(String e : md.select_images_id){
				b.names.add(e);
				b.urls.add(md.image_urls.get(e));
			}
			b.src_image = "./img/prof/" + md.src_iamge_name;

//			for(String name : Collections.list( ses.getAttributeNames()) ){
//				if(!name.startsWith("image")) continue ;
//				b.names.add((String)ses.getAttribute(name));
//				System.out.println("jj:"+name+":"+ses.getAttribute(name));
//			}
//			b.src_image = md.src_iamge_url;


			ses.setAttribute("b", b);
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
