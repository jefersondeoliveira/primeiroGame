package com.example.primeirogame;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Logica extends SurfaceView implements Runnable {

	private Boolean running = false, gameover;
	private Thread renderThread = null;
	private SurfaceHolder holder;
	private Paint paint;
	private Integer playerX, playerY, playerRadius;
	private Integer enemyX, enemyY, enemyRadius;
	private Integer score;
	private Double distance;

	public Logica(Context context) {
		super(context);
		paint = new Paint();
		holder = getHolder();
		init();
	}

	public void init() {
		playerX = playerY = 300;
		playerRadius = 10;
		enemyX = enemyY = enemyRadius = 0;
		score = 0;
		gameover = false;
	}

	@Override
	public void run() {
		while (running) {
			// verifica se a tela já esta pronta
			if (!holder.getSurface().isValid())
				continue;

			// bloquea o canvas
			Canvas canvas = holder.lockCanvas();
			canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.sky), 0, 0, null);

			// desenha o player
			drawPlayer(canvas);
			// desenha o inimigo
			drawEnemy(canvas);

			// detecta colisão
			checkCollision();

			if (gameover) {
				stopGame(canvas);
				running = false;
			}

			// atualiza placar
			drawScore(canvas);
			
			//Restart e Exit
			drawButtons(canvas);

			// atualiza e libera o canvas
			holder.unlockCanvasAndPost(canvas);
		}
	}

	private void drawPlayer(Canvas canvas) {
		paint.setColor(Color.GREEN);
//		canvas.drawCircle(playerX, playerY, playerRadius, paint);
		canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.nave), playerX, playerY, null);
	}

	private void drawEnemy(Canvas canvas) {
		paint.setColor(Color.RED);
		enemyRadius+=10;
		canvas.drawCircle(enemyX, enemyY, enemyRadius, paint);
	}

	public void moveDown(Integer pixels) {
		playerY += pixels;
	}

	private void checkCollision() {
		// Calcula a hipotenusa
		distance = Math.pow(playerY - enemyY, 2)
				+ Math.pow(playerX - enemyX, 2);
		distance = Math.sqrt(distance);

		// verifica distancia entre os raios
		if (distance <= playerRadius + enemyRadius) {
			gameover = true;
		}
	}

	private void stopGame(Canvas canvas) {
		paint.setStyle(Style.FILL);
		paint.setColor(Color.LTGRAY);
		paint.setTextSize(80);
		canvas.drawText("GAME OVER", 50, 150, paint);
	}

	private void drawScore(Canvas canvas) {
		paint.setStyle(Style.FILL);
		paint.setColor(Color.WHITE);
		paint.setTextSize(50);
		canvas.drawText(String.valueOf(score), 50, 200, paint);
	}

	public void addScore(Integer points) {
		score += points;
	}

	private void drawButtons(Canvas canvas) {
		// Restart
		paint.setStyle(Style.FILL);
		paint.setColor(Color.WHITE);
		paint.setTextSize(30);
		canvas.drawText("Restart", 50, 300, paint);

		// Exit
		paint.setStyle(Style.FILL);
		paint.setColor(Color.WHITE);
		paint.setTextSize(30);
		canvas.drawText("Exit", 50, 500, paint);
	}

	public void resume() {
		running = true;
		renderThread = new Thread(this);
		renderThread.start();
	}

	public void pause() {
		running = false;
	}

}
