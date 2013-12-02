package jp.dmtc.ing.promoa.mosaic;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Mosaicing {


	public static void draw3(Graphics2D g2,BufferedImage img,BufferedImage[] tile,int iconsize) throws IOException	{

		promoa2 c = new promoa2();
		c.drawVer3(g2, img, tile, iconsize);

//		promoa c = new promoa();
//		c.drawVer3(g2, img, tile, iconsize);

//		colorprot c = new colorprot();
//		c.drawVer3(g2, img, tile, iconsize);

	}



}
