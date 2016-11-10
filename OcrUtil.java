package com.ocr.util;

import java.io.File;

import com.asprise.ocr.Ocr;
//java-ocr-api-15.3.0.3.jar
public class OcrUtil {

	public static void main(String[] args) {
		System.out.println(System.getProperty("user.dir"));
	}

	public static String parseImg(String path) {
		Ocr.setUp();
		Ocr ocr = new Ocr();
		ocr.startEngine("eng", Ocr.SPEED_FASTEST);
		String s = ocr.recognize(new File[] { new File(path) }, Ocr.RECOGNIZE_TYPE_ALL, Ocr.OUTPUT_FORMAT_PLAINTEXT);
		ocr.stopEngine();
		return s;
	}
}