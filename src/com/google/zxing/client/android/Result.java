package com.google.zxing.client.android;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;

public class Result extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result);
		final TextView tv = (TextView) findViewById(R.id.result_duration);
		final ImageView iv = (ImageView) findViewById(R.id.result_image);

		Intent intent = getIntent();
		byte qrcode[] = intent.getByteArrayExtra("qrcode");
		long duration = intent.getLongExtra("duration", 0);

		tv.setText("Duration: " + String.valueOf(duration) + "ms");
		Bitmap bmp = BitmapFactory.decodeByteArray(qrcode, 0, qrcode.length);
		iv.setImageBitmap(bmp);

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// kill process
			Log.d("Felix", "Back key pressed, will kill the process.");
			android.os.Process.killProcess(android.os.Process.myPid());

		}

		return true;

	}

}
