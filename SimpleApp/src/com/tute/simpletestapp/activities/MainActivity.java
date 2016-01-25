package com.tute.simpletestapp.activities;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tute.simpletestapp.R;
import com.tute.simpletestapp.service.HelloService;

/**
 * Simple app main activity
 * 
 * @author skh
 *
 */

public class MainActivity extends Activity {

	// First input box
	private EditText text1;
	// Second input box
	private EditText text2;

	// Result textview
	private TextView resultTv;

	private TextView serviceResult;

	final String serverURL = "http://androidexample.com/media/webservice/JsonReturn.php";
	private ResponseReceiver receiver;

	public class ResponseReceiver extends BroadcastReceiver {
		public static final String ACTION_RESP = "com.tute.simpletestapp.activities.SERVICE_FINISHED";

		@Override
		public void onReceive(Context context, Intent intent) {
			serviceResult.setText(intent.getStringExtra("result"));
		}
	}

	// Starting service
	public void startService(View v) {
		Intent intent = new Intent(this, HelloService.class);
		startService(intent);
		serviceResult.setText("Service started.");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gui);

		this.text1 = (EditText) findViewById(R.id.editText1);
		this.text2 = (EditText) findViewById(R.id.editText2);
		this.resultTv = (TextView) findViewById(R.id.result);
		this.serviceResult = (TextView) findViewById(R.id.serviceResult);

		IntentFilter filter = new IntentFilter(ResponseReceiver.ACTION_RESP);
		filter.addCategory(Intent.CATEGORY_DEFAULT);
		receiver = new ResponseReceiver();

		// Registering receiver for the service
		registerReceiver(receiver, filter);

	}

	public void addNumbers(View v) {

		text1.requestFocus();
		// Get the texts from input
		String str1 = text1.getText().toString();
		String str2 = text2.getText().toString();
		if (str1.isEmpty() || str2.isEmpty()) {
			resultTv.setText("Please fill the inputs");
			return;
		}
		int n1, n2;
		try {
			// Parse it into numbers
			n1 = Integer.parseInt(str1);
			n2 = Integer.parseInt(str2);

			// Add numbers and set the result text view
			n2 += n1;
			resultTv.setText(String.valueOf(n2));
		} catch (NumberFormatException e) {
			resultTv.setText("Enter numbers in the input");
		}
	}

	public void compareTexts(View v) {
		// Get the texts from input
		String str1 = text1.getText().toString();
		String str2 = text2.getText().toString();
		resultTv.setText(String.valueOf(str1.equals(str2)));
	}

	public void fireJSONRequest(View v) {
		// Use AsyncTask execute Method To Prevent ANR Problem
		new LongOperation().execute(serverURL);
	}

	public void pushNotif(View v) {
		NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		Intent resultIntent = new Intent(this, ResultActivity.class);
		resultIntent.putExtra("TestValue", "TestValue received");
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		// Adds the back stack
		stackBuilder.addParentStack(ResultActivity.class);
		// Adds the Intent to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
		// Gets a PendingIntent containing the entire back stack
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
				PendingIntent.FLAG_UPDATE_CURRENT);

		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				this).setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle("proximiti").setContentText("Hello World!");

		builder.setContentIntent(resultPendingIntent);
		builder.setAutoCancel(true);

		nm.notify(11, builder.build());
	}

	private class LongOperation extends AsyncTask<String, Void, Void> {

		// Required initialization

		private String Content;
		private String Error = null;
		private ProgressDialog Dialog = new ProgressDialog(MainActivity.this);
		String data = "";
		TextView jsonParsed = (TextView) findViewById(R.id.jsonParsed);
		EditText serverText = (EditText) findViewById(R.id.serverText);

		protected void onPreExecute() {
			// NOTE: You can call UI Element here.

			// Start Progress Dialog (Message)

			Dialog.setMessage("Please wait..");
			Dialog.show();

			try {
				// Set Request parameter
				data += "&" + URLEncoder.encode("data", "UTF-8") + "="
						+ serverText.getText();

			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		// Call after onPreExecute method
		protected Void doInBackground(String... urls) {

			/************ Make Post Call To Web Server ***********/
			BufferedReader reader = null;

			// Send data
			try {

				// Defined URL where to send data
				URL url = new URL(urls[0]);

				// Send POST data request

				URLConnection conn = url.openConnection();
				conn.setDoOutput(true);
				OutputStreamWriter wr = new OutputStreamWriter(
						conn.getOutputStream());
				wr.write(data);
				wr.flush();

				// Get the server response

				reader = new BufferedReader(new InputStreamReader(
						conn.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line = null;

				// Read Server Response
				while ((line = reader.readLine()) != null) {
					// Append server response in string
					sb.append(line + " ");
				}

				// Append Server Response To Content String
				Content = sb.toString();
			} catch (Exception ex) {
				Error = ex.getMessage();
			} finally {
				try {

					reader.close();
				}

				catch (Exception ex) {
				}
			}

			/*****************************************************/
			return null;
		}

		protected void onPostExecute(Void unused) {
			// NOTE: You can call UI Element here.

			// Close progress dialog
			Dialog.dismiss();

			if (Error != null) {

				jsonParsed.setText("Output : " + Error);

			} else {

				/******************
				 * Start Parse Response JSON Data
				 *************/

				String OutputData = "";
				JSONObject jsonResponse;

				try {

					/******
					 * Creates a new JSONObject with name/value mappings from
					 * the JSON string.
					 ********/
					jsonResponse = new JSONObject(Content);

					/*****
					 * Returns the value mapped by name if it exists and is a
					 * JSONArray.
					 ***/
					/******* Returns null otherwise. *******/
					JSONArray jsonMainNode = jsonResponse
							.optJSONArray("Android");

					/*********** Process each JSON Node ************/

					int lengthJsonArr = jsonMainNode.length();

					for (int i = 0; i < lengthJsonArr; i++) {
						/****** Get Object for each JSON node. ***********/
						JSONObject jsonChildNode = jsonMainNode
								.getJSONObject(i);

						/******* Fetch node values **********/
						String name = jsonChildNode.optString("name")
								.toString();
						String number = jsonChildNode.optString("number")
								.toString();
						String date_added = jsonChildNode.optString(
								"date_added").toString();

						OutputData += " Name: " + name + "Number: " + number
								+ "Time: " + date_added;

					}
					/******************
					 * End Parse Response JSON Data
					 *************/

					// Show Parsed Output on screen (activity)
					jsonParsed.setText(OutputData);

				} catch (JSONException e) {

					e.printStackTrace();
				}

			}
		}

	}
}
