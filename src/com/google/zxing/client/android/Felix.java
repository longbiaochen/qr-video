package com.google.zxing.client.android;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import android.content.Context;
import android.content.Intent;
import android.util.Base64;
import android.util.Log;

public class Felix {

	public static boolean focus = true;
	public static boolean found = false;
	public static int NUM = 18;
	static String msg[] = new String[NUM];
	static int cnt = 0;
	public static long start, end, duration;
	public static long fs, fe, fd; // per frame
	public static long c = 0;
	public static String fn = "/sdcard/camera.txt";
	public static File f;
	public static PrintWriter writer;

	public static void init() {
		try {
			writer = new PrintWriter(fn);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void save(String text, Context context) {
		if (cnt < NUM) {
			String[] parcel = text.split(" ");
			int i = Integer.parseInt(parcel[0]);
			Log.d("Felix", "Felix: " + i);
			if (i > NUM)
				return;
			if (msg[i] == null) {
				cnt++;
				Log.d("Felix", "Felix: " + text);
				msg[i] = parcel[2];
			}

		} else {
			end = System.currentTimeMillis();
			duration = end - start;
			Log.d("Felix", "=======================");
			found = true;
			String qrString = "";
			byte[] blob;
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			for (int j = 0; j < NUM; j++) {
				Log.d("Felix", "Felix: " + msg[j]);
				qrString = msg[j];
				blob = Base64.decode(qrString, Base64.DEFAULT);
				try {
					os.write(blob);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			byte qrcode[] = os.toByteArray();
			Intent intent = new Intent(context, Result.class);
			intent.putExtra("qrcode", qrcode);
			intent.putExtra("duration", duration);
			context.startActivity(intent);

		}

	}

	public static void log(long text) {
		// save to /sdcard/latest_media_info.txt
		try {
			writer.println(text);
			writer.flush();
		} catch (Exception e) {
			Log.e("Felix", "Error saving media info.");
			e.printStackTrace();
		}
		Log.d("Felix", "log: " + text);

	}
}
