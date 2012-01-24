package com.amieggs.gyaru;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;

public class GyaruActivity extends Activity implements OnTouchListener {

	private String type;
	private Map<String,ImageView> items;
	
	int windowwidth;
    int windowheight;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		windowwidth = getWindowManager().getDefaultDisplay().getWidth();
	    windowheight = getWindowManager().getDefaultDisplay().getHeight();
		
		this.type = getIntent().getExtras().getString("com.amieggs.gyaru.type");
		if("mamba".equals(type)){
			setContentView(R.layout.gyarumamba);
		}
		else {
			setContentView(R.layout.gyaruleopard);
		}
		fillItemsMap();
	}
	
	private void fillItemsMap(){
		items = new HashMap<String,ImageView>();
		if("mamba".equals(type)){
			ImageView kawaii = (ImageView)findViewById(R.id.kawaiiMamba);
			kawaii.setOnTouchListener(this);
			items.put("kawaii", kawaii);
		}
		else {
			//initialize leopard items
		}	
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		LayoutParams layoutParams = (LayoutParams) v.getLayoutParams();
        switch(event.getAction()) {
        case MotionEvent.ACTION_DOWN:   
        	break;
        case MotionEvent.ACTION_MOVE:
        	int x_cord = (int)event.getRawX();
        	int y_cord = (int)event.getRawY();

            if(x_cord>windowwidth){
            	x_cord=windowwidth;
            }
            
            if(y_cord>windowheight){
            	y_cord=windowheight;
            }

            layoutParams.leftMargin = x_cord -25;
            layoutParams.topMargin = y_cord - 75;

            v.setLayoutParams(layoutParams);
            break;
        default:
            break;
        }
            return true;
	}

}
