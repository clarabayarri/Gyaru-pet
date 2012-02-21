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
	private Map<String, Integer> itemResources;
	private Map<String, Integer> hands;
	private Map<String, Integer> sounds;
	
	ImageView movingImage;
	
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
		
		View topItemBar = findViewById(R.id.topItemBar);
		topItemBar.bringToFront();
		
		movingImage = (ImageView)findViewById(R.id.movingImage);
	}
	
	private void fillItemsMap(){
		items = new HashMap<String,ImageView>();
		hands = new HashMap<String, Integer>();
		sounds = new HashMap<String, Integer>();
		itemResources = new HashMap<String, Integer>();
		
		fillItems();
		
		if("mamba".equals(type)) fillMapsMamba();
		else fillMapsLeopard();
	}
	
	private void fillItems() {
		ImageView kawaii = (ImageView)findViewById(R.id.kawaii);
		kawaii.setOnTouchListener(this);
		items.put("kawaii", kawaii);
		//TODO: canviar a la imatge kawaii
		itemResources.put("kawaii", R.drawable.stitch);
		
		ImageView kowai = (ImageView)findViewById(R.id.kowai);
		kowai.setOnTouchListener(this);
		items.put("kowai", kowai);
		//TODO: canviar a la imatge kawaii
		itemResources.put("kowai", R.drawable.stitch);
		
		ImageView oishii = (ImageView)findViewById(R.id.oishii);
		oishii.setOnTouchListener(this);
		items.put("oishii", oishii);
		//TODO: canviar a la imatge kawaii
		itemResources.put("oishii", R.drawable.stitch);
		
		ImageView ikitai = (ImageView)findViewById(R.id.ikitai);
		ikitai.setOnTouchListener(this);
		items.put("ikitai", ikitai);
		//TODO: canviar a la imatge kawaii
		itemResources.put("ikitai", R.drawable.stitch);
		
		ImageView eeeeh = (ImageView)findViewById(R.id.eeeeh);
		eeeeh.setOnTouchListener(this);
		items.put("eeeeh", eeeeh);
		//TODO: canviar a la imatge kawaii
		itemResources.put("eeeeh", R.drawable.stitch);
		
		ImageView bikkuri = (ImageView)findViewById(R.id.bikkuri);
		bikkuri.setOnTouchListener(this);
		items.put("bikkuri", bikkuri);
		//TODO: canviar a la imatge kawaii
		itemResources.put("bikkuri", R.drawable.stitch);
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
		//TODO: posar els que toquen
		sounds.put("kowai", R.raw.kawaii);
		sounds.put("bikkuri", R.raw.kawaii);
		sounds.put("ikitai", R.raw.kawaii);
		sounds.put("oishii", R.raw.kawaii);
		sounds.put("eeeeh", R.raw.kawaii);
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
        switch(event.getAction()) {
        case MotionEvent.ACTION_DOWN:
        	startMovingView(v);
        	break;
        case MotionEvent.ACTION_MOVE:
        	moveMovingImage(event);
            break;
        case MotionEvent.ACTION_UP:
            returnViewToPlace(v);
            itemSelected(v);
        	break;
        default:
            break;
        }
            return true;
	}
	
	private void startMovingView(View v){
		//LayoutParams layoutParams = (LayoutParams) v.getLayoutParams();
		v.setVisibility(View.INVISIBLE);
		String key = getKeyForView(v);
		movingImage.setImageResource(itemResources.get(key));
		//movingImage.setLayoutParams(layoutParams);
		movingImage.setVisibility(View.VISIBLE);
	}
	
	private void moveMovingImage(MotionEvent event){
		LayoutParams layoutParams = (LayoutParams) movingImage.getLayoutParams();
		int x_cord = (int)event.getRawX();
    	int y_cord = (int)event.getRawY();

        if(x_cord>windowwidth){
        	x_cord=windowwidth;
        }
        
        if(y_cord>windowheight){
        	y_cord=windowheight;
        }

        layoutParams.leftMargin = x_cord - movingImage.getWidth()/2;
        layoutParams.topMargin = y_cord - movingImage.getHeight()/2;
        movingImage.setLayoutParams(layoutParams);
	}
	
	private void returnViewToPlace(View v){
		LayoutParams layoutParams = (LayoutParams) v.getLayoutParams();
		layoutParams.leftMargin = 0;
        layoutParams.topMargin = 0;
        v.setLayoutParams(layoutParams);
        movingImage.setVisibility(View.GONE);
        v.setVisibility(View.VISIBLE);
	}
	
	private void itemSelected(View v){
		performItem(getKeyForView(v));
	}
	
	private String getKeyForView(View v){
		Set<String> possibleItems = items.keySet();
		for(String key : possibleItems){
			if(v.equals(items.get(key))){
				return key;
			}
		}
		return null;
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
