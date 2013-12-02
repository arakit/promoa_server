package jp.dmtc.ing.promoa.mosaic;



import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class prot{
//  public static void main(String[] args){
//	  prot test = new prot();
//    test.addWindowListener(new WindowAdapter(){
//      public void windowClosing(WindowEvent e){System.exit(0);}
//    });
//
//    test.setBounds( 0, 0, 800, 600);
//    test.setVisible(true);
//  }

  int mNum = 0;
  int noudo = 0;		//出力濃度　０～３０が理想


  public void paint(Graphics g, File src_image, BufferedImage[] tile){
    Graphics2D g2 = (Graphics2D)g;

    int iconsize = 20;
    int[] tilelevel = new int[100];
    int[] tilelevelmax = new int[100];
    int i,j,cnt=0;

    BufferedImage img = null;								//BufferedImageセット
	mNum = tile.length;

    try {
    	img = ImageIO.read(src_image);			//画像読み込み

    	//画像の濃淡平均値抽出
		Color c = null;
		int col1=0;
		for (i = 0; i <tile.length; i++) {
			tilelevelmax[i]=255;
			for (int iy = 0; iy < tile[i].getHeight(); iy++) {
				for (int ix = 0; ix < tile[i].getWidth(); ix++) {
					c = new Color(tile[i].getRGB(ix, iy));
					col1 += c.getRed();
					if(tilelevelmax[i]>c.getRed()) tilelevelmax[i]=c.getRed();
					cnt++;
				}
			}
			tilelevel[i] = col1/cnt;
			cnt=0;
			col1=0;
		}

		//画像のモザイク化

		double ave=0,rave=0,gave=0,bave=0;
		for (i = 0; i < img.getHeight(); i+=iconsize) {
			for (j = 0; j <img.getWidth(); j+=iconsize) {
				for (int k = 0; k < iconsize; k++) {
					for (int l = 0; l < iconsize; l++) {
						if(i+k>=img.getHeight()) break;		//あまり強制break
						if(j+l>=img.getWidth()) break;
						int col = img.getRGB(j+l, i+k);
						ave += toGray(col);			//グレースケール値取得（関数）
						rave += toRed(col);			//レッドスケール値取得（関数）
						gave += toGreen(col);		//グリーンスケール値取得（関数）
						bave += toBlue(col);		//ブルースケール値取得（関数）
						cnt++;
					}
				}
				ave = ave/cnt;
				rave = rave/cnt;
				gave = gave/cnt;
				bave = bave/cnt;
				cnt=0;
				BufferedImage tileimg = null;
				tileimg = tile[compare(tilelevel,ave)];

				/* アイコン画像カラー変換 */
				for (int k = 0; k < iconsize; k++) {
					for (int l = 0; l < iconsize; l++) {
						int col = tileimg.getRGB(l, k);
						tileimg.setRGB(l, k, toPaint(rave,gave,bave,toGray(col),tilelevelmax[compare(tilelevel,ave)]));
					}
				}
			    g2.drawImage(tileimg, j, i, null);			//画像タイルはめ込み！
//				for (int k = 0; k < iconsize; k++) {
//					for (int l = 0; l < iconsize; l++) {
//						if(i+k>=img.getHeight()) break;			//あまり強制break
//						if(j+l>=img.getWidth()) break;
//						img.setRGB(j+l, i+k, toMozaik(ave));	//単純モザイク用
//					}
//				}
				ave=0;rave=0;gave=0;bave=0;
				tileimg = null;
			}
		}
    } catch (Exception e) {
    	e.printStackTrace();
    }

    //g2.drawImage(img, 0, 50, this);

   }


/* グレースケール変換（中間値法） */
protected int toGray(int col)
{
	Color c = new Color(col);
	int max = Math.max(c.getRed(), Math.max(c.getGreen(), c.getBlue()));
	int min = Math.min(c.getRed(), Math.min(c.getGreen(), c.getBlue()));
	int v = (max+min)/2;
	return v;
}


/* レッドスケール変換 */
protected int toRed(int col)
{
	Color c = new Color(col);
	return c.getRed();
}

/* グリーンスケール変換 */
protected int toGreen(int col)
{
	Color c = new Color(col);
	return c.getGreen();
}

/* ブルースケール変換 */
protected int toBlue(int col)
{
	Color c = new Color(col);
	return c.getBlue();
}


/* モザイク変換 */
protected int toMozaik(double v)
{
	Color c = new Color((int)v, (int)v, (int)v, 255);
	return c.getRGB();
}


/* 画像配色 */
protected int toPaint(double r, double g,double b, int gr,int max)
{

	if(r>=230 && g>=230 && b>=230){
		r = 230-noudo;
		g = 230-noudo;
		b = 230-noudo;
		if(r<0) r=0;
		if(g<0) g=0;
		if(b<0) b=0;
	}
	Color c = null;

	if(gr == 255){
		r = 255;
		g = 255;
		b = 255;
		c = new Color((int)r, (int)g, (int)b, 255);
	}
	else{
		r += (gr - max)*0.1;
		g += (gr - max)*0.1;
		b += (gr - max)*0.1;
		r -= noudo;
		g -= noudo;
		b -= noudo;
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

/* 比較関数*/
protected int compare(int[] gl, double ave)
{
	int i;
	for(i=1;i<mNum;i++){		//バグ防止のため１から
		if(ave <= gl[i])
			break;
	}
	if(gl[i]-ave>ave-gl[i-1]) return i;
	else return i-1;
}


}






