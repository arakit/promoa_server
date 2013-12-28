package jp.dmtc.ing.promoa.mosaic;


import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class promoa2 extends JFrame{
	public static void main(String[] args){
		promoa2 test = new promoa2();
		test.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){System.exit(0);}
		});
		test.setBounds( 0, 0, 1200, 700);	//画面サイズ
		test.setVisible(true);
	}

	public void paint(Graphics g){
		Graphics2D g2 = (Graphics2D)g;

		int iconsize = 100;								//アイコンのサイズ
		String[] tilename = null;						//アイコンの名前
		BufferedImage img = null;						//BufferedImageセット
		BufferedImage[] tile = null;

		try {
			//モザイクにする画像を読み込む
			img = ImageIO.read(new File("about_header.jpg"));

			File dir = new File("./pic");
			tilename = dir.list();

			tile = new BufferedImage[tilename.length];			//BufferedImageセット

			for(int i=0; i<tilename.length; i++ ){
				tile[i]=ImageIO.read(new File("./pic/"+tilename[i]));
			}
			drawVer3(g2, img, tile, iconsize);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～

	/* 画像処理メソッド */

	protected void drawVer3(Graphics2D g2,BufferedImage img,BufferedImage[] tile,int iconsize) throws IOException{

		try {

			//画像のモザイク化
			toMozaiku(g2,img,tile,iconsize);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～

	/* モザイク変換 */
	protected void toMozaiku(Graphics2D g2,BufferedImage img,BufferedImage[] tile,int iconsize) throws IOException
	{
		g2.drawImage(img, 0, 0, this);				//画像タイルはめ込み！

//		//アイコンサイズ指定
//		if(img.getWidth()>=500 && img.getHeight()>=500) iconsize=100;
//		else if(img.getWidth()>=300 && img.getHeight()>=300) iconsize=70;
//		else iconsize=50;

		int cnt = 0;
		double ave=0;						//各色の濃淡平均値を求めるための変数
		for (int i = 0; i < img.getHeight(); i++) {
			for (int j = 0; j <img.getWidth(); j++) {
				int col = img.getRGB(j, i);			//画像の１ピクセルのRGB情報の変数
				ave += toGray(col);						//グレースケール値取得（関数）
				cnt++;
			}
		}
		ave = ave/cnt;

		float alpha = 0.3F;
		if(ave<=120)alpha = 0.4F;


		for (int i = 0; i < img.getHeight(); i+=iconsize) {
			for (int j = 0; j <img.getWidth(); j+=iconsize) {

				BufferedImage tileimg = copyImage(tile[(int) (Math.random()*tile.length)]);			//タイルに貼り付けるアイコン画像(色を変えるから別に用意)

				/* Java2Dで画像を半透明に重ね合わせる */
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha));
				g2.drawImage(tileimg,j,i,iconsize,iconsize,null);
			}
		}
	}

//～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～

	/* 画像一時コピー */

	static BufferedImage copyImage(BufferedImage src){
		BufferedImage dist=new BufferedImage(src.getWidth(), src.getHeight(), src.getType());
		dist.setData(src.getData());
		return dist;
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

}