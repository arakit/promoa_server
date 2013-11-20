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
import jp.crudefox.server.util.CFServletParams.UploadFile;
import jp.dmtc.ing.promoa.beans.Next1Bean;

/**
 * Servlet implementation class Create
 */
@WebServlet(name = "next1", urlPatterns = { "/next1" })
public class Next1 extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Next1() {
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
			response.setContentType("text/html; charset=utf-8");

			HttpSession ses = request.getSession();


			CFServletParams params = new CFServletParams(this, request, response, new File("/upfiles"));

			UploadFile src_image_upfile = params.getFileParam("src_image");

			if(src_image_upfile==null){
				response.sendRedirect("failed_src_image.html");
				return ;
			}

			File prof_file = new File(getServletContext().getRealPath("/img/prof/"+System.currentTimeMillis()));
			boolean suc_rename = src_image_upfile.tmpfile.renameTo(prof_file);

			if(!suc_rename){
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "リネーム失敗");
				return ;
			}

			ses.setAttribute("src_image", prof_file.getName());
//			for(String name : params.getNames()){
//				ses.setAttribute(name, "on");
//			}

			File parts_dir = new File( getServletContext().getRealPath("/img/parts/") );

			Next1Bean b = new Next1Bean();

			for(File file : parts_dir.listFiles() ){
				b.names.add(file.getName());
				System.out.println("name:"+file.getName());
			}
			b.src_image = src_image_upfile.tmpfile.getAbsolutePath();


			ses.setAttribute("n1", b);
		    //ViewであるJSPを呼び出す
		    RequestDispatcher rDispatcher =
		     request.getRequestDispatcher("/next1.jsp");
		    rDispatcher.forward(request, response);

		}catch(Exception e){
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
		}

	}


}
