package jp.dmtc.ing.promoa;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.dmtc.ing.promoa.beans.MBean;
import jp.dmtc.ing.promoa.data.MData;
import jp.dmtc.ing.promoa.mosaic.prot;
import facebook4j.Facebook;

/**
 * Servlet implementation class Create
 */
@WebServlet(name = "create", urlPatterns = { "/create" })
public class Create extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Create() {
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

	protected void doProc(HttpServletRequest request, HttpServletResponse response) throws ServletException {

		try{
			//response.setContentType("application/json; charset=utf-8");
			//response.setContentType("image/png");

			HttpSession ses = request.getSession();
			if( ses.isNew() ){
				response.sendError( HttpServletResponse.SC_BAD_REQUEST , "セッション管理失敗");
				return ;
			}

			Facebook fb = (Facebook) ses.getAttribute(CFConst.SESATT_FACEBOOK);
			MData md = (MData) ses.getAttribute(CFConst.SESATT_MOSAIC_DATA);

			System.out.println("合成を開始します。");

			File src_image = new File(getServletContext().getRealPath("./img/prof/"+md.src_iamge_name));
			BufferedImage img = ImageIO.read(src_image);

			//CFServletParams params = new CFServletParams(this, request, response, new File("/upfiles"));

//			ArrayList<File> files = new ArrayList<File>();
//			for(String name : Collections.list(ses.getAttributeNames()) ){
//				if( !name.startsWith("image") ) continue;
//				files.add( new File( getServletContext().getRealPath("./img/parts/"+(String)ses.getAttribute(name)) )  );
//			}

			ArrayList<URL> files = new ArrayList<URL>();
			for(String e : md.select_images_id){
				URL url = new URL( md.image_urls.get(e) );
				files.add(url);
			}
			

			//File parts_dir = new File( getServletContext().getRealPath("./img/parts/") );
			BufferedImage[] tile = new BufferedImage[files.size()];
			for(int i=0;i<files.size();i++){
				tile[i] =  ImageIO.read(files.get(i));
			}


			Graphics2D g2 = (Graphics2D) img.getGraphics();

			g2.setColor(Color.RED);
			g2.drawRect(0, 0, img.getWidth(), img.getHeight());

			prot prot = new prot();
			prot.paint(img.getGraphics(), src_image, tile);

			//OutputStream os = response.getOutputStream();
			File save_file = new File(getServletContext().getRealPath("./img/complete/"+System.currentTimeMillis()));
			boolean suc_save = ImageIO.write(img, "png", save_file);

			if(!suc_save){
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "サーバー無い保存失敗");
				return ;
			}
			
			md.complete_iamge_name = save_file.getName();

//			response.sendRedirect("./img/complete/"+save_file.getName());

//			int outbuf_len = 0;
//			byte[] outbuf = new byte[1024];
//			while(outbuf=os.){
//
//			}

			MBean b = new MBean();
			b.complete_image_url = "./img/complete/" + md.complete_iamge_name;

			ses.setAttribute("b", b);
		    //ViewであるJSPを呼び出す
		    RequestDispatcher rDispatcher =
		     request.getRequestDispatcher("/complete.jsp");
		    rDispatcher.forward(request, response);

		}catch(Exception e){
			e.printStackTrace();
		}

	}


}
