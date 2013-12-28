package jp.dmtc.ing.promoa.mosaic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;

public class colorprot{
//  public static void main(String[] args){
//	  colorprot test = new colorprot();
//    test.addWindowListener(new WindowAdapter(){
//      public void windowClosing(WindowEvent e){System.exit(0);}
//    });
//
//    test.setBounds( 0, 0, 1200, 700);	//画面サイズ
//    test.setVisible(true);
//  }

//  int mNum = 223;			//アイコン画像の枚数
  int noudo = -10;		//出力濃度　－３０～３０が理想


  public void paint(Graphics g){
    Graphics2D g2 = (Graphics2D)g;


    int iconsize = 20;										//アイコンのサイズ
    String[] tilename ;//new String[300];					//アイコンの名前
//    int[] tilelevel = new int[300];							//アイコンの濃淡平均値
//    int[] tilelevelmax = new int[300];						//アイコンの濃淡最大値(アイコン画像のグラデーション表示に使用)
    BufferedImage img = null;								//BufferedImageセット
    BufferedImage[] tile;

    try {
    	//ライブラリから画像情報読み込み
    	//readImage(tilename,tilelevel,tilelevelmax);

    	//モザイクにする画像を読み込む
    	img = ImageIO.read(new File("guitar.jpg"));

	  File dir = new File("./pictgram");
	  tilename = dir.list();

	  tile = new BufferedImage[tilename.length];			//BufferedImageセット
	  //tilename = new String[tilename.length];

	  for(int i=0; i<tilename.length; i++ ){
		  //File f = new File("./pictgram/"+tilename[i]);
		  //System.out.println("name="+f.getName()+" "+f.exists());
		  tile[i]=ImageIO.read(new File("./pictgram/"+tilename[i]));
		  //System.out.println("img="+tile[i]);
	  }

	  drawVer3(g2, img, tile, iconsize);

    } catch (Exception e) {
    	e.printStackTrace();
    }

   }

//～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～

  protected void drawVer3(Graphics2D g2,BufferedImage img,BufferedImage[] tile,int iconsize) throws IOException{




//	    int iconsize = 20;										//アイコンのサイズ
	    int[] tilelevel = new int[tile.length];							//アイコンの濃淡平均値
	    int[] tilelevelmax = new int[tile.length];						//アイコンの濃淡最大値(アイコン画像のグラデーション表示に使用)
//	    BufferedImage img = null;								//BufferedImageセット
//	    BufferedImage[] tile = new BufferedImage[300];			//BufferedImageセット

	    try {
	    	//ライブラリから画像情報読み込み
	    	//readImage(tilename,tilelevel,tilelevelmax);

	    	//アイコンの濃淡平均値を調べる
	    	average(tile,tilelevel,tilelevelmax);

	    	//ソート
	    	quicksort(1,tile.length-1,tile,tilelevel,tilelevelmax);

//	    	//モザイクにする画像を読み込む
//	    	img = ImageIO.read(new File("guitar.jpg"));

	    	//画像のモザイク化
	    	toMozaiku(g2,img,tile,iconsize,tilelevel,tilelevelmax);

	    } catch (Exception e) {
	    	e.printStackTrace();
	    }

  }



  static BufferedImage copyImage(BufferedImage src){
	    BufferedImage dist=new BufferedImage(
	            src.getWidth(), src.getHeight(), src.getType());
	    dist.setData(src.getData());
	    return dist;
	}

/* モザイク変換 */
protected void toMozaiku(Graphics2D g2,BufferedImage img,BufferedImage[] tile,int iconsize,int[] tilelevel,int[] tilelevelmax) throws IOException
{

	int cnt = 0;
	double ave=0,rave=0,gave=0,bave=0;						//各色の濃淡平均値を求めるための変数
	for (int i = 0; i < img.getHeight(); i+=iconsize) {
		for (int j = 0; j <img.getWidth(); j+=iconsize) {
			for (int k = 0; k < iconsize; k++) {
				for (int l = 0; l < iconsize; l++) {
					if(i+k>=img.getHeight()) break;			//あまり強制break
					if(j+l>=img.getWidth()) break;
					int col = img.getRGB(j+l, i+k);			//画像の１ピクセルのRGB情報の変数
					ave += toGray(col);						//グレースケール値取得（関数）
					rave += toRed(col);						//レッドスケール値取得（関数）
					gave += toGreen(col);					//グリーンスケール値取得（関数）
					bave += toBlue(col);					//ブルースケール値取得（関数）
					cnt++;
				}
			}
			ave = ave/cnt;
			rave = rave/cnt;
			gave = gave/cnt;
			bave = bave/cnt;
			cnt=0;

			int tilenum = compare(tilelevel,ave,tile.length);			//アイコン濃度情報とタイルの濃度で比較して、一番濃度が近いアイコンを選択
			//BufferedImage tileimg = ImageIO.read(new File("pictgram/" + tilename[tilenum]));			//タイルに貼り付けるアイコン画像(色を変えるから別に用意)
			BufferedImage tileimg = copyImage(tile[tilenum]);			//タイルに貼り付けるアイコン画像(色を変えるから別に用意)

			/* アイコン画像カラー変換 */
			for (int k = 0; k < tileimg.getHeight(); k++) {
				for (int l = 0; l < tileimg.getWidth(); l++) {
					int col = tileimg.getRGB(l, k);
					tileimg.setRGB(l, k, toPaint(rave,gave,bave,toGray(col),tilelevelmax[tilenum]));	//カラーに画像に上書き
				}
			}
			
			
		    g2.drawImage(tileimg, j, i, iconsize, iconsize,null);				//画像タイルはめ込み！
//			for (int k = 0; k < iconsize; k++) {
//				for (int l = 0; l < iconsize; l++) {
//					if(i+k>=img.getHeight()) break;			//あまり強制break
//					if(j+l>=img.getWidth()) break;
//					img.setRGB(j+l, i+k, toGrayMozaiku(ave));	//単純モザイク用
//				}
//			}
			ave=0;rave=0;gave=0;bave=0;
			tileimg = null;
		}
	}
}

//～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～

/* ライブラリから画像情報読み込み */
protected void readImage(String[] tilename,int[] tilelevel,int[] tilelevelmax) throws IOException
{
  FileReader in = new FileReader("./iconlist.txt");//ファイルの読み込み
  BufferedReader br = new BufferedReader(in);//BufferedReader型br定義
  String line; //String型のlineを定義
  int cnt=0;
  while ((line = br.readLine()) != null) {//1行ずつを読み込む
      String[] str = line.split(",");
      tilename[cnt] = str[0];
      tilelevel[cnt] = Integer.parseInt(str[1]) ;
      tilelevelmax[cnt] = Integer.parseInt(str[2]) ;
      cnt++;
  }
  br.close();//ストリームを閉じる
  in.close();//ストリームを閉じる
}


//～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～

/* アイコンの濃淡平均値計算 */
protected void average(BufferedImage[] tile,int[] tilelevel,int[] tilelevelmax)
{
Color c = null;
int cnt = 0;
for (int i = 0; i < tile.length; i++) {															//アイコンの枚数分濃度平均値を計算
	tilelevelmax[i]=255;
	for (int iy = 0; iy < tile[i].getHeight(); iy++) {
		for (int ix = 0; ix < tile[i].getWidth(); ix++) {
			c = new Color(tile[i].getRGB(ix, iy));
			tilelevel[i] += c.getRed();												//白黒だからとりあえず赤で
			if(tilelevelmax[i]>c.getRed()) tilelevelmax[i]=c.getRed();				//画像の一番濃い濃度を記憶
			cnt++;
		}
	}
	tilelevel[i] = tilelevel[i]/cnt;												//平均値を割り出す
	cnt=0;
}
}

//～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～

/* グレースケール変換（中間値法） */
protected int toGray(int col)
{
	Color c = new Color(col);
	int max = Math.max(c.getRed(), Math.max(c.getGreen(), c.getBlue()));
	int min = Math.min(c.getRed(), Math.min(c.getGreen(), c.getBlue()));
	int v = (max+min)/2;
	return v;
}

//～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～

/* グレースケールモザイク変換 */		//白黒のみで使用
protected int toGrayMozaiku(double v)
{
	Color c = new Color((int)v, (int)v, (int)v, 255);
	return c.getRGB();
}

//～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～

/* レッドスケール変換 */
protected int toRed(int col)
{
	Color c = new Color(col);
	return c.getRed();
}

//～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～

/* グリーンスケール変換 */
protected int toGreen(int col)
{
	Color c = new Color(col);
	return c.getGreen();
}

//～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～

/* ブルースケール変換 */
protected int toBlue(int col)
{
	Color c = new Color(col);
	return c.getBlue();
}

//～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～

/* 画像配色 */			//アイコンの1ピクセルでの処理！
protected int toPaint(double r, double g,double b, int gr,int max)
{
	Color c = null;

	if(gr == 255){		//まっしろの場合は完全に白！
		c = new Color(255,255,255, 255);
	}
	else{				//グラデーションをきれいに見せる為に色々
		if(r>=230 && g>=230 && b>=230){
			r = 230-noudo;
			g = 230-noudo;
			b = 230-noudo;
		}
		r += (gr - max)*0.1 - noudo;
		g += (gr - max)*0.1 - noudo;
		b += (gr - max)*0.1 - noudo;
		if(r>255) r=255;
		if(g>255) g=255;
		if(b>255) b=255;
		if(r<0) r=0;
		if(g<0) g=0;
		if(b<0) b=0;
		c = new Color((int)r, (int)g, (int)b, 255);
	}
	return c.getRGB();
}

//～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～

/* 比較関数 */
protected int compare(int[] gl, double ave, int num)
{
	int i;
	for(i=1;i<num-1;i++){		//バグ防止のため１から
		if(ave <= gl[i])
			break;
	}
	if(gl[i]-ave>ave-gl[i-1]) return i;
	else return i-1;
}

//～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～

/* クイックソート */

public static void quicksort(int lp,int rp,BufferedImage[] tile,int[] tilelevel,int[] tilelevelmax)
{
	int s = lp-1;
	int e = rp;

	while(true)
	{
		while(tilelevel[s]>=tilelevel[lp])
		{

			if(lp==rp)
				break;
			lp++;
		}
		while(tilelevel[s]<=tilelevel[rp])
		{

			if(lp==rp)
				break;
			rp--;
		}
		if(lp==rp)
		{
			if(tilelevel[s]>tilelevel[lp])
				change(s,lp,tile,tilelevel,tilelevelmax);
			else
			{
				lp--;
				rp--;
				change(s,lp,tile,tilelevel,tilelevelmax);
			}
			break;
		}
		change(lp,rp,tile,tilelevel,tilelevelmax);
	}
	if(s < lp-1)
		quicksort(s+1,lp-1,tile,tilelevel,tilelevelmax);
	if(rp+1 < e)
		quicksort(lp+2,e,tile,tilelevel,tilelevelmax);
	return;
}

//～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～

/* 並び替え処理 */
public static void change(int x,int y,BufferedImage[] tile,int[] tilelevel,int[] tilelevelmax)
{
	int ch;
	BufferedImage sch;
	sch=tile[x];
	tile[x]=tile[y];
	tile[y]=sch;
	ch=tilelevel[x];
	tilelevel[x]=tilelevel[y];
	tilelevel[y]=ch;
	ch=tilelevelmax[x];
	tilelevelmax[x]=tilelevelmax[y];
	tilelevelmax[y]=ch;
}

}