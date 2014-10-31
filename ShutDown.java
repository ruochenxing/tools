package com.common.other;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ShutDown {

	// 定时关机
	public static void shutDownAtTime(String time) {
		Runtime run = Runtime.getRuntime();
		try {
			run.exec("at " + time + " Shutdown -s");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// 倒计时关机
	public static void shutDownHaveTime(String time) {
		Runtime run = Runtime.getRuntime();
		try {
			run.exec("Shutdown.exe -s -t " + time);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void shutDownAtNow() {
		Runtime run = Runtime.getRuntime();
		try {
			run.exec("Shutdown.exe -s");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// restart
	public static void reboot() {
		Runtime run = Runtime.getRuntime();
		try {
			run.exec("Shutdown.exe -r");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void shutDownHaveTimeWithMess(String time, String mes) {
		Runtime run = Runtime.getRuntime();
		try {
			run.exec("shutdown -s -t " + time + " -c " + mes + "");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 取消定时关机
	public static void noShutDown() {
		Runtime run = Runtime.getRuntime();
		try {
			run.exec("shutdown -a");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void test() {
		try {
			Process ps = Runtime.getRuntime().exec("c:\\test.bat");
			System.out.println(loadStream(ps.getInputStream()));
			System.out.println(loadStream(ps.getErrorStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static String loadStream(InputStream in) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		StringBuffer sb = new StringBuffer();
		String line;
		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}
		return sb.toString();
	}
}
