package com.amieggs.gyaru;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class GyaruActivity extends Activity implements OnClickListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String type = getIntent().getExtras().getString("com.amieggs.gyaru.type");
		System.out.println(type);
		if("mamba".equals(type)){
			setContentView(R.layout.gyarumamba);
		}
		else setContentView(R.layout.gyaruleopard);
		
	}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}

}
