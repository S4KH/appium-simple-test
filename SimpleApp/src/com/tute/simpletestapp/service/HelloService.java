package com.tute.simpletestapp.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;
import android.text.format.DateFormat;
import android.util.Log;

import com.tute.simpletestapp.activities.MainActivity.ResponseReceiver;

public class HelloService extends IntentService {

	public HelloService() {
		super("HelloService");
	}

	private static final String TAG = "BGService";

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.i(TAG, "Service onHandleIntent");

		SystemClock.sleep(5000); // 5 seconds
		String resultTxt = "Resp:"
				+ DateFormat.format("MM/dd/yy h:mmaa",
						System.currentTimeMillis());
		publishResults(resultTxt);

	}

	private void publishResults(String result) {
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction(ResponseReceiver.ACTION_RESP);
		broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		broadcastIntent.putExtra("result", result);
		sendBroadcast(broadcastIntent);
	}

}
