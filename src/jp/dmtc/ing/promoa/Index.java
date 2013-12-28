package jp.dmtc.ing.promoa;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.dmtc.ing.promoa.beans.MBean;
import jp.dmtc.ing.promoa.data.MData;
import facebook4j.Album;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.Friend;
import facebook4j.Like;
import facebook4j.Paging;
import facebook4j.Photo;
import facebook4j.Picture;
import facebook4j.Reading;
import facebook4j.ResponseList;
import facebook4j.User;

/**
 * Servlet implementation class Create
 */
@WebServlet(name = "index", urlPatterns = { "/index.html" })
public class Index extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Index() {
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
			
			//request.getSession().invalidate();
			

			System.out.println("index!");

	        Facebook fb;

	        fb = (Facebook) request.getSession().getAttribute(CFConst.SESATT_FACEBOOK);

			HttpSession ses = request.getSession();
//			CFServletParams params = new CFServletParams(this, request, response, new File("/upfiles"));

//			if(fb!=null){
//				exec_facebook(md, fb);
//			}

			response.setContentType("text/html; charset=utf-8");

			MBean b = new MBean();

			if(fb!=null){
				try {
					User user = fb.getMe();
					if(user!=null){
						b.login_fb = true;
					}
				} catch (Exception e) {

				}
			}

			ses.setAttribute("b", b);
		    //ViewであるJSPを呼び出す
		    RequestDispatcher rDispatcher =
		     request.getRequestDispatcher("/index.jsp");
		    rDispatcher.forward(request, response);

		}catch(Exception e){
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
		}

	}




	private void exec_facebook(MData md, Facebook fb) throws FacebookException{


        User user = fb.getMe();
        System.out.println( user.getFirstName() );

//
//			ResponseList<Friendlist> friends = fb.getFriendlists();
//			for(Friendlist f : friends){
//				System.out.println( f.getName() );
//			}

		System.out.println("----");






		ResponseList<Friend> friends = fb.getFriends(new Reading().fields("picture","cover"));

		System.out.println("friends size="+friends.size());
		for(Friend f : friends){
			//Picture pic = f.getPicture();
			System.out.println( ""+f.getName() );
			System.out.println( ""+f.getPicture() );
			System.out.println( ""+f.getCover() );
			System.out.println("----");
		}

		System.out.println("----");

		//response.setContentType("application/json; charset=utf-8");


//			File parts_dir = new File( getServletContext().getRealPath("/img/parts/") );
//
//			MBean b = new MBean();
//
//			for(File file : parts_dir.listFiles() ){
//				b.names.add(file.getName());
//				System.out.println("name:"+file.getName());
//			}



		ResponseList<Album> albums = fb.getAlbums(new Reading().fields("photos","cover_photo"));
		for(int i=0;i<albums.size();i++){
			Album album = albums.get(i);
			//fb.getAlbumPhotos(arg0);

			ResponseList<Photo> ps = fb.getAlbumPhotos(album.getId(), new Reading().fields("photos","picture","images"));

			for(Photo p : ps){
				List<Photo.Image> imgs =  p.getImages();
				Photo.Image kimg = null;
				for(Photo.Image img : imgs){
					System.out.println("w="+img.getWidth()+",h="+img.getHeight());
					if(img.getWidth()==180) kimg = img;
				}
				if(kimg==null) continue;
				//String cid = album.getCoverPhoto();
//				String cid = p.getIcon();
//				String url = "https://graph.facebook.com/"+cid+"/picture";
				String url = kimg.getSource().toString();//p.getPicture().toString();
				md.image_urls.put( "image_"+md.image_urls.size() , url);
			}

		}


		for(Friend f : friends){
			Picture pic = f.getPicture();
			if(pic==null || pic.isSilhouette()) continue;
			String url = pic.getURL().toString();
			md.image_urls.put( "image_"+md.image_urls.size() , url);

//				b.names.add(pic.getURL().toString());
//				b.urls.add(pic.getURL().toString());
		}

		ResponseList<Like> likes = fb.getUserLikes(new Reading().fields("picture","cover"));
		Paging<Like> likes_paging = null;
		while( likes!=null && likes.size()>0 ){
			for(Like f : likes){
				//Picture pic = f.getMetadata().
				//if(pic==null) continue;
				//https://graph.facebook.com/me/likes?access_token=
				String url = "https://graph.facebook.com/"+f.getId()+"/picture";
				md.image_urls.put( "image_"+md.image_urls.size() , url);
//					b.names.add(url);
//					b.urls.add(url);
			}

			likes_paging = likes.getPaging();
			if(likes_paging==null) break;
			likes = fb.fetchNext(likes_paging);
		}
	}

}
