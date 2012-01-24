package com.amieggs.gyaru;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    public void onClick(View view){
    	switch (view.getId()){
    	case R.id.indexgyarumamba:
    		Intent mambaIntent = new Intent(this,GyaruActivity.class);
    		mambaIntent.putExtra("com.amieggs.gyaru.type", "mamba");
    		startActivity(mambaIntent);
    		break;
    	case R.id.indexgyaruleopard:
    		Intent leopardIntent = new Intent(this,GyaruActivity.class);
    		leopardIntent.putExtra("com.amieggs.gyaru.type", "leopard");
    		startActivity(leopardIntent);
    		break;
    		
    	}
    }
}