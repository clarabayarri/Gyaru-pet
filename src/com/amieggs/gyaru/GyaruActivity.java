package com.amieggs.gyaru;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;

public class GyaruActivity extends Activity implements OnTouchListener, OnCompletionListener {

	private String type;
	private Map<String,ImageView> items;
	private Map<String, Integer> hands;
	private Map<String, Integer> sounds;
	
	MediaPlayer mp = null;
	
	int windowwidth;
    int windowheight;
    
    ImageView hand;
    
    Handler handler = new Handler();
    Runnable hideHand = new Runnable() {
    	public void run() {
    		hand.setVisibility(ImageView.GONE);
    	}
    };
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		windowwidth = getWindowManager().getDefaultDisplay().getWidth();
	    windowheight = getWindowManager().getDefaultDisplay().getHeight();
		
	    setContentView(R.layout.gyaru);
	    
		this.type = getIntent().getExtras().getString("com.amieggs.gyaru.type");
		
		ImageView mamba = (ImageView)findViewById(R.id.gyaru);
		mamba.setMaxWidth((int)0.6*windowwidth);
		
		hand = (ImageView)findViewById(R.id.handImage);
		
		fillItemsMap();
	}
	
	private void fillItemsMap(){
		items = new HashMap<String,ImageView>();
		hands = new HashMap<String, Integer>();
		sounds = new HashMap<String, Integer>();
		
		fillItems();
		
		if("mamba".equals(type)) fillMapsMamba();
		else fillMapsLeopard();
	}
	
	private void fillItems() {
		ImageView kawaii = (ImageView)findViewById(R.id.kawaii);
		kawaii.setOnTouchListener(this);
		items.put("kawaii", kawaii);
	}
	
	private void fillMapsMamba() {
		fillHandsMamba();
		fillSoundsMamba();
	}
	
	private void fillHandsMamba() {
		hands.put("kawaii", R.drawable.manokawaii);
		hands.put("bikkuri", R.drawable.manobikkuri);
		hands.put("ikitai", R.drawable.manoikitai);
		hands.put("kowai", R.drawable.manokowaii);
		hands.put("oishii", R.drawable.manooishii);
	}
	
	private void fillSoundsMamba() {
		sounds.put("kawaii", R.raw.kawaii);
	}
	
	private void fillMapsLeopard() {
		changeImagesToLeopard();
		fillItemsLeopard();
		fillHandsLeopard();
		fillSoundsLeopard();
	}
	
	private void changeImagesToLeopard() {
		ImageView mamba = (ImageView)findViewById(R.id.gyaru);
		mamba.setImageResource(R.drawable.gyaru1);
		mamba.setMaxHeight((int)0.6*windowheight);
	}
	
	private void fillItemsLeopard() {
		
	}
	
	private void fillHandsLeopard() {
		
	}
	
	private void fillSoundsLeopard() {
		
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
		showHand(item);
		playMedia(item);
	}
	
	private void showHand(String item) {
		hand.setImageResource(hands.get(item));
		hand.setVisibility(ImageView.VISIBLE);
		TranslateAnimation translation = new TranslateAnimation(
	            Animation.RELATIVE_TO_SELF, 0.7f,Animation.RELATIVE_TO_SELF, 0.0f,
	            Animation.RELATIVE_TO_SELF, 0.4f,Animation.RELATIVE_TO_SELF, 0.0f
	        );
		translation.setInterpolator(new AccelerateDecelerateInterpolator());
		translation.setDuration(1500);
		hand.startAnimation(translation);
		
		handler.postDelayed(hideHand, 3500);
	}
	
	private void playMedia(String item) {
		mp = MediaPlayer.create(this, sounds.get(item));
		mp.start();
		mp.setOnCompletionListener(this);
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		mp.release();
		this.mp = null;
	}
}
