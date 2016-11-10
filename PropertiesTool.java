package com.ocr.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class PropertiesTool {

	// 使用这种方式时，需要知道文件的绝对路径 适合在自己的服务器上跑
	public static final String FILE_PATH = System.getProperty("user.dir")
			+ "/src/main/java/com/ocr/util/test.properties"; // 文件路径

	// 使用这种方式时，一旦重新编译了，原本修改的文件就会被覆盖掉
	// public static final String FILE_PATH=
	// PropertiesTool.class.getResource("").getPath()+"log4j.properties"; //
	// 文件路径
	public static final Properties p = new Properties();
	public static long last_modify_time = 0;

	public static void main(String[] args) {
		put1("a", "aaac");
	}

	public static void put(String key, String value) {
		try {
			p.setProperty(key, value);
			OutputStream fos = new FileOutputStream(FILE_PATH);
			p.store(fos, key);
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void put1(String key, String value) {
		String oldValue = get(key);
		FileModify.write(FILE_PATH, FileModify.modify(FILE_PATH, key + "=" + oldValue, key + "=" + value));
	}

	public static String get(String key) {
		File f = new File(FILE_PATH);
		if (!f.exists()) {
			return "";
		}
		if (last_modify_time == 0 || last_modify_time < f.lastModified()) {
			try {
				System.out.println("loading....");
				p.load(new FileInputStream(f));
				last_modify_time = f.lastModified();
				return p.getProperty(key);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			return p.getProperty(key);
		}
		return "";
	}
}
