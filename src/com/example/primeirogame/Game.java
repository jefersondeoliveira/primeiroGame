package com.example.primeirogame;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.app.Activity;

public class Game extends Activity implements OnTouchListener{
	
	Logica view;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		//Logica do jogo
		view = new Logica(this);
		
		view.setOnTouchListener(this);
		
		//Configura view
		setContentView(view);
	}

	@Override
	protected void onResume() {
		super.onResume();
		view.resume();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(event.getX() < 100 && event.getY() > 290 && event.getY() < 310){
			view.init();
		}
		if(event.getX() < 100 && event.getY() > 490 && event.getY() < 510){
			System.exit(0);
		}
		
		view.moveDown(10);
		view.addScore(10);
		
		return false;
	}

	@Override
	protected void onPause() {
		super.onPause();
		view.pause();
	}
	
	
}
