package com.amieggs.gyaru;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;

public class GyaruActivity extends Activity implements OnTouchListener {

	private String type;
	private Map<String,ImageView> items;
	private Map<String, Integer> sounds;
	
	MediaPlayer mp = null;
	
	int windowwidth;
    int windowheight;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		windowwidth = getWindowManager().getDefaultDisplay().getWidth();
	    windowheight = getWindowManager().getDefaultDisplay().getHeight();
		
	    setContentView(R.layout.gyaru);
	    
		this.type = getIntent().getExtras().getString("com.amieggs.gyaru.type");
		
		ImageView mamba = (ImageView)findViewById(R.id.gyaru);
		mamba.setMaxWidth((int)0.6*windowwidth);
		
		fillItemsMap();
	}
	
	private void fillItemsMap(){
		items = new HashMap<String,ImageView>();
		sounds = new HashMap<String, Integer>();
		
		//fill items
		ImageView kawaii = (ImageView)findViewById(R.id.kawaii);
		kawaii.setOnTouchListener(this);
		items.put("kawaii", kawaii);
			
		//fill sounds
		sounds.put("kawaii", R.raw.kawaii);
		
		if("leopard".equals(type)) changeImagesToLeopard();
	}
	
	private void changeImagesToLeopard() {
		ImageView mamba = (ImageView)findViewById(R.id.gyaru);
		mamba.setImageResource(R.drawable.gyaru1);
		mamba.setMaxHeight((int)0.6*windowheight);
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

            layoutParams.leftMargin = x_cord - v.getWidth()/2;
            layoutParams.topMargin = y_cord - v.getHeight()/2;
            v.setLayoutParams(layoutParams);
            break;
        case MotionEvent.ACTION_UP:
        	layoutParams.leftMargin = 0;
            layoutParams.topMargin = 0;
            v.setLayoutParams(layoutParams);
            itemSelected(v);
        	break;
        default:
            break;
        }
            return true;
	}
	
	private void itemSelected(View v){
		Set<String> possibleItems = items.keySet();
		for(String key : possibleItems){
			if(v.equals(items.get(key))){
				performItem(key);
			}
		}
	}
	
	private void performItem(String item){
		mp = MediaPlayer.create(this, sounds.get(item));
		mp.start();
		mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){

			@Override
			public void onCompletion(MediaPlayer mediaplayer) {
				mp.stop();
				mp.release();
				mp = null;
			}
			
		});
	}
}
