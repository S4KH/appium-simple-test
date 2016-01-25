package com.tute.simpletestapp.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Broadcast receiver to start service when phone boots up
 * 
 * @author skh
 *
 */
public class Receiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {
			Intent serviceIntent = new Intent(context, HelloService.class);
			context.startService(serviceIntent);
		}

	}

}
