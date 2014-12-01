package com.common.image;

/**
 * 绘制有自定义文字图片
 * 
 * Eclipse默认把这些受访问限制的API设成了ERROR。
 * 只要把Windows-Preferences-Java-Complicer-Errors/Warnings里面的
 * Deprecated and restricted API中的Forbidden references(access rules)
 * 选为Warning就可以编译通过。
 * */
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.awt.image.BufferedImage;
import java.awt.*;

public class ChartGraphics {

	BufferedImage image;

	void createImage(String fileLocation) {
		try {
			FileOutputStream fos = new FileOutputStream(fileLocation);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
			encoder.encode(image);
			bos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void graphicsGeneration(String name, String id, String classname,
			String imgurl) {

		int imageWidth = 500;// 图片的宽度
		int imageHeight = 200;// 图片的高度
		image = new BufferedImage(imageWidth, imageHeight,BufferedImage.TYPE_INT_RGB);
		Graphics graphics = image.getGraphics();
//		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, imageWidth, imageHeight);
		graphics.setColor(Color.RED);//字体颜色
		Font f = new Font("宋体", Font.BOLD, 30);//设计字体显示效果 Font mf = new Font(String 字体，int 风格，int 字号);
		graphics.setFont(f);
		graphics.drawString("姓名 : " + name, 50, 75);//横坐标,纵坐标  
		graphics.drawString("学号 : " + id, 50, 150);

		// 改成这样:
		BufferedImage bimg = null;
		try {
			bimg = javax.imageio.ImageIO.read(new java.io.File(imgurl));
		} catch (Exception e) {
		}

		if (bimg != null)
			graphics.drawImage(bimg, 230, 0, null);
		graphics.dispose();

		createImage(imgurl);

	}

	public static void main(String[] args) {
		ChartGraphics cg = new ChartGraphics();
		try {
			cg.graphicsGeneration("ewew", "1", "12","c:/7.jpg");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
