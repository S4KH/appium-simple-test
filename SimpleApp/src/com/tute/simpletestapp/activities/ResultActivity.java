package com.tute.simpletestapp.activities;

import com.tute.simpletestapp.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends Activity {

	private TextView result;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.notification);
		this.result = (TextView) findViewById(R.id.notifResult);

		Bundle extras = getIntent().getExtras();
		if (!extras.isEmpty()) {
			String res = extras.getString("TestValue");
			result.setText(res);
		}
	}

}
