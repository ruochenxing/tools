package com.ocr.util;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.ColorModel;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtil {
	private BufferedImage image;
	private int iw;
	private int ih;
	private String path;
	private String dest;
	
	public ImageUtil(String path,String dest){
		this.path=path;
		this.dest=dest;
	}
	public void init(BufferedImage image) {
		this.image = image;
		this.iw = image.getWidth();
		this.ih = image.getHeight();
	}

	public void init(File file) throws IOException {
		init(ImageIO.read(file));
	}

	public void cleanImage(String pathFile, String name, String ext) throws IOException {
		File file = new File(pathFile);
		init(file);
		grey();
		toGrey();
		toMedian();
//		toBrighten();
		File destFile = new File(dest + "/" + name + "." + ext);
		ImageIO.write(getProcessedImg(), ext, destFile);
	}

	public void displayRGB() throws IOException {
		for (int x = 0; x < iw; x++) {
			for (int y = 0; y < ih; y++) {
				int argb = image.getRGB(x, y) & 0xFFFFFF;// getRGB获取的是ARGB
				int red = (argb & 0xff0000) >> 16;
				int green = (argb & 0xff00) >> 8;
				int blue = (argb & 0xff);
				System.out.println(
						"[" + x + "," + y + "]:" + argb + "\tred:" + red + "\tgreen:" + green + "\tblue:" + blue);
			}
		}
	}

	public void grey_1() {
		int[][] gray = new int[iw][ih];
		for (int x = 0; x < iw; x++) {
			for (int y = 0; y < ih; y++) {
				int argb = image.getRGB(x, y);
				// 图像加亮（调整亮度识别率非常高）
				int r = (int) (((argb >> 16) & 0xFF) * 1.1 + 30);
				int g = (int) (((argb >> 8) & 0xFF) * 1.1 + 30);
				int b = (int) (((argb >> 0) & 0xFF) * 1.1 + 30);
				if (r >= 255) {
					r = 255;
				}
				if (g >= 255) {
					g = 255;
				}
				if (b >= 255) {
					b = 255;
				}
				gray[x][y] = (int) Math.pow(
						(Math.pow(r, 2.2) * 0.2973 + Math.pow(g, 2.2) * 0.6274 + Math.pow(b, 2.2) * 0.0753), 1 / 2.2);
			}
		}
		for (int x = 0; x < iw; x++) {
			for (int y = 0; y < ih; y++) {
				image.setRGB(x, y, gray[x][y]);
			}
		}
	}

	/**
	 * 图像二值化
	 */
	public void grey() {
		int[] pixels = new int[iw * ih];
		PixelGrabber pg = new PixelGrabber(image.getSource(), 0, 0, iw, ih, pixels, 0, iw);
		try {
			pg.grabPixels();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 设定二值化的域值，默认值为100
		int grey = 90;
		// 对图像进行二值化处理，Alpha值保持不变
		ColorModel cm = ColorModel.getRGBdefault();
		for (int i = 0; i < iw * ih; i++) {
			int red, green, blue;
			int alpha = cm.getAlpha(pixels[i]);
			if (cm.getRed(pixels[i]) > grey) {
				red = 255;
			} else {
				red = 0;
			}
			if (cm.getGreen(pixels[i]) > grey) {
				green = 255;
			} else {
				green = 0;
			}
			if (cm.getBlue(pixels[i]) > grey) {
				blue = 255;
			} else {
				blue = 0;
			}
			pixels[i] = alpha << 24 | red << 16 | green << 8 | blue; // 通过移位重新构成某一点像素的RGB值
		}
		// 将数组中的象素产生一个图像
		Image tempImg = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(iw, ih, pixels, 0, iw));
		image = new BufferedImage(tempImg.getWidth(null), tempImg.getHeight(null), BufferedImage.TYPE_INT_BGR);
		image.createGraphics().drawImage(tempImg, 0, 0, null);
	}

	/**
	 * 中值滤波
	 */
	public void toMedian() {
		int[] pixels = new int[iw * ih];
		PixelGrabber pg = new PixelGrabber(image.getSource(), 0, 0, iw, ih, pixels, 0, iw);
		try {
			pg.grabPixels();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 对图像进行中值滤波，Alpha值保持不变
		ColorModel cm = ColorModel.getRGBdefault();
		for (int i = 1; i < ih - 1; i++) {
			for (int j = 1; j < iw - 1; j++) {
				int red, green, blue;
				int alpha = cm.getAlpha(pixels[i * iw + j]);
				int red4 = cm.getRed(pixels[i * iw + j - 1]);
				int red5 = cm.getRed(pixels[i * iw + j]);
				int red6 = cm.getRed(pixels[i * iw + j + 1]);
				// 水平方向进行中值滤波
				if (red4 >= red5) {
					if (red5 >= red6) {
						red = red5;
					} else {
						if (red4 >= red6) {
							red = red6;
						} else {
							red = red4;
						}
					}
				} else {
					if (red4 > red6) {
						red = red4;
					} else {
						if (red5 > red6) {
							red = red6;
						} else {
							red = red5;
						}
					}
				}
				int green4 = cm.getGreen(pixels[i * iw + j - 1]);
				int green5 = cm.getGreen(pixels[i * iw + j]);
				int green6 = cm.getGreen(pixels[i * iw + j + 1]);
				// 水平方向进行中值滤波
				if (green4 >= green5) {
					if (green5 >= green6) {
						green = green5;
					} else {
						if (green4 >= green6) {
							green = green6;
						} else {
							green = green4;
						}
					}
				} else {
					if (green4 > green6) {
						green = green4;
					} else {
						if (green5 > green6) {
							green = green6;
						} else {
							green = green5;
						}
					}
				}
				int blue4 = cm.getBlue(pixels[i * iw + j - 1]);
				int blue5 = cm.getBlue(pixels[i * iw + j]);
				int blue6 = cm.getBlue(pixels[i * iw + j + 1]);
				// 水平方向进行中值滤波
				if (blue4 >= blue5) {
					if (blue5 >= blue6) {
						blue = blue5;
					} else {
						if (blue4 >= blue6) {
							blue = blue6;
						} else {
							blue = blue4;
						}
					}
				} else {
					if (blue4 > blue6) {
						blue = blue4;
					} else {
						if (blue5 > blue6) {
							blue = blue6;
						} else {
							blue = blue5;
						}
					}
				}
				pixels[i * iw + j] = alpha << 24 | red << 16 | green << 8 | blue;
			}
		}
		// 将数组中的象素产生一个图像
		Image tempImg = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(iw, ih, pixels, 0, iw));
		image = new BufferedImage(tempImg.getWidth(null), tempImg.getHeight(null), BufferedImage.TYPE_INT_BGR);
		image.createGraphics().drawImage(tempImg, 0, 0, null);
	}

	public void display() {
		// 矩阵打印
		for (int y = 0; y < ih; y++) {
			for (int x = 0; x < iw; x++) {
				if (isBlack(image.getRGB(x, y))) {
					System.out.print("*");
				} else {
					System.out.print(" ");
				}
			}
			System.out.println();
		}
	}

	public void write(String ext, File file) throws IOException {
		ImageIO.write(image, ext, file);
	}

	public void toGrey() {
		ColorConvertOp ccp = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
		image = ccp.filter(image, null);
	}

	// Brighten using a linear formula that increases all color values
	public void toBrighten() {
		RescaleOp rop = new RescaleOp(1.25f, 0, null);
		image = rop.filter(image, null);
	}

	// Blur by "convolving" the image with a matrix
	public void toBlur() {
		float[] data = { .1111f, .1111f, .1111f, .1111f, .1111f, .1111f, .1111f, .1111f, .1111f, };
		ConvolveOp cop = new ConvolveOp(new Kernel(3, 3, data));
		image = cop.filter(image, null);
	}

	// Sharpen by using a different matrix
	public void toSharpen() {
		float[] data = { 0.0f, -0.75f, 0.0f, -0.75f, 4.0f, -0.75f, 0.0f, -0.75f, 0.0f };
		ConvolveOp cop = new ConvolveOp(new Kernel(3, 3, data));
		image = cop.filter(image, null);
	}

	// 11) Rotate the image 180 degrees about its center point
	public void toRotate() {
		AffineTransformOp atop = new AffineTransformOp(
				AffineTransform.getRotateInstance(Math.PI, image.getWidth() / 2, image.getHeight() / 2),
				AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		image = atop.filter(image, null);
	}

	public static boolean isBlack(int colorInt) {
		Color color = new Color(colorInt);
		if (color.getRed() + color.getGreen() + color.getBlue() <= 300) {
			return true;
		}
		return false;
	}

	public static boolean isWhite(int colorInt) {
		Color color = new Color(colorInt);
		if (color.getRed() + color.getGreen() + color.getBlue() > 300) {
			return true;
		}
		return false;
	}

	public static boolean isBlackOrWhite(int colorInt) {
		if (getColorBright(colorInt) < 30 || getColorBright(colorInt) > 730) {
			return true;
		}
		return false;
	}

	public static int getColorBright(int colorInt) {
		Color color = new Color(colorInt);
		return color.getRed() + color.getGreen() + color.getBlue();
	}

	// 获取灰度化阙值
	@SuppressWarnings("unused")
	private static int ostu(int[][] gray, int w, int h) {
		int[] histData = new int[w * h];
		// Calculate histogram
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				int red = 0xFF & gray[x][y];
				histData[red]++;
			}
		}
		// Total number of pixels
		int total = w * h;
		float sum = 0;
		for (int t = 0; t < 256; t++)
			sum += t * histData[t];
		float sumB = 0;
		int wB = 0;
		int wF = 0;
		float varMax = 0;
		int threshold = 0;
		for (int t = 0; t < 256; t++) {
			wB += histData[t]; // Weight Background
			if (wB == 0)
				continue;
			wF = total - wB; // Weight Foreground
			if (wF == 0)
				break;
			sumB += (float) (t * histData[t]);
			float mB = sumB / wB; // Mean Background
			float mF = (sum - sumB) / wF; // Mean Foreground
			// Calculate Between Class Variance
			float varBetween = (float) wB * (float) wF * (mB - mF) * (mB - mF);
			// Check if new maximum found
			if (varBetween > varMax) {
				varMax = varBetween;
				threshold = t;
			}
		}
		return threshold;
	}

	public BufferedImage getProcessedImg() {
		return image;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static final int[] startX = new int[] { 0, 18, 46, 69 };// 每张图片的切割位置横坐标
																	// 纵坐标为39
	public static final int[] widths = new int[] { 18, 28, 23, 20 };

	public void splitImage() throws IOException {
		File dirs = new File(path);
		File[] imgs = dirs.listFiles();
		for (File img : imgs) {
			splitOneImage(img, "jpg");
		}
	}

	public void splitOneImage(File img, String ext) throws IOException {
		if (!img.getName().endsWith(ext)) {
			return;
		}
		String fileName = img.getName();
		FileInputStream fin = new FileInputStream(img);
		BufferedImage bi = ImageIO.read(fin);
		for (int i = 0; i < startX.length; i++) {
			_splitOneImage(i, fileName, bi);
		}
	}

	private void _splitOneImage(int index, String fileName, BufferedImage image) throws IOException {
		BufferedImage binaryBufferedImage = new BufferedImage(widths[index], 39, BufferedImage.TYPE_BYTE_BINARY);// TYPE_INT_BGR
		for (int x = 0; x < widths[index]; x++) {
			for (int y = 0; y < 39; y++) {
				int argb = image.getRGB(startX[index] + x, y);
				binaryBufferedImage.setRGB(x, y, argb);
			}
		}
		String pname = fileName.substring(0, fileName.lastIndexOf("."));
		String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
		ImageIO.write(binaryBufferedImage, ext, new File(dest, pname + "-" + index + "." + ext));
	}
}
