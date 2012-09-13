package com.vahancesar.HeroGame;

import java.lang.reflect.Array;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

public class Level implements MatrixElements {
	private final int LEVEL_PLAYING = 0;
	private final int LEVEL_FAILED = 1;
	private final int LEVEL_FINISHED = 2;
	
	public static int PIXEL_PER_CELL_WIDTH;
	public static int PIXEL_PER_CELL_HEIGHT;

	
	private final int MAX_LEVELS = 20;
	private int mCurrentLevel = 0;
	private int mLevelState;

	private LevelActivity mLevelActivity;
	private RenderLoop mRenderLoop;

	private Bitmap mBackground;

	private int mWidth, mHeight;

	private Hero mHero;

	private int mLevelMatrix[][];

	/*
	 * private Vector<Barrier> mBarriers; private Vector<Jewel> mJewels; private
	 * Vector<Healer> rHealers; private Vector<Crab> mCrabs; private
	 * Vector<Guard> mGuards;
	 */

	Level(int level, int width, int height, LevelActivity levelActivity,
			Hero hero) {
		initializeCellSizes();
		mCurrentLevel = level;
		mHero = hero;
		mLevelActivity = levelActivity;
		mRenderLoop = new RenderLoop(levelActivity);
		mWidth = width;
		mHeight = height;
		mBackground = BitmapFactory.decodeResource(
				levelActivity.getResources(), R.drawable.background);
		restartLevel();
			
	}

	public void nextFrame(Canvas c) {
		switch (mLevelState) {
		case LEVEL_PLAYING:
			drawLevel(c);
			updateLevel();
			break;
		case LEVEL_FINISHED:
			drawLevelFinished();
			break;
		case LEVEL_FAILED:
			drawLevelFailed();
			break;
		}
	}

	private void updateLevel() {
		/* Checks gravity, jumpings, */
		updateHeroGravity();

		if (mHero.getHeroState() == Hero.HERO_RUNNING) {
			if (mHero.updateStillCounter())
				mHero.stateStill(); // Still until another button pressed
		}

		/*
		 * Check if he is touching a jewel, a bad guy, he would die..., update
		 * all that stuff
		 */
		/* TODO */
	}

	private boolean heroCorrectPosition() {
		/* Within display bounds */
		if (mHero.getBounds().left >= 0 && mHero.getBounds().top >= 0) {
			if ((mHero.getBounds().left + mHero.getBounds().right) < mWidth
					&& (mHero.getBounds().top + mHero.getBounds().bottom) < mHeight) {
				return true;
			}
		}
		/* TODO Check if we hit with barriers. Vahan's matrix */

		return false;
	}

	private void updateHeroGravity() {
		if (mHero.getHeroState() == Hero.HERO_JUMPING) {
			Log.i("e", "Hero Jumping");

			mHero.moveUp();
			if (heroCorrectPosition()) {
				mHero.updateJump();
				if (!mHero.keepJumping()) {
					mHero.stateFalling();
				}
			} else {
				mHero.moveDown();
				mHero.stateFalling();
			}

		} else if (mHero.getHeroState() == Hero.HERO_FALLING) {
			Log.i("e", "Hero falling");
			mHero.moveDown();
			if (!heroCorrectPosition()) { // Hero landed!
				mHero.moveUp();
				mHero.stateStill();
			}
		} else { // Lets check if there is a hole or something while running, so
					// we have to fall
			Log.i("e", "Hero still/running");
			mHero.moveDown(); // Gravity!
			if (heroCorrectPosition()) { // Wow, there is a hole!
				mHero.stateFalling();
			} else { // False alarm
				mHero.moveUp();
			}
		}
	}

	private void drawLevel(Canvas c) {
		// Drawing the background
		c.drawBitmap(mBackground, 0, 0, null);

		// Draw elements
		for (int i = 0; i < MATRIX_WIDTH; ++i) {
			for (int j = 0; j < MATRIX_HEIGHT; ++j) {
				int coordx = i * PIXEL_PER_CELL_WIDTH;
				int coordy = j * PIXEL_PER_CELL_HEIGHT;
				switch (mLevelMatrix[i][j]) {
				case NOTHING:
					break;
				case BLUE_JEWEL:
					c.drawBitmap(Jewel.getJewelBitMap(BLUE_JEWEL), coordx,
							coordy, null);
					break;
				case YELLOW_JEWEL:
					c.drawBitmap(Jewel.getJewelBitMap(YELLOW_JEWEL), coordx,
							coordy, null);
					break;
				case RED_JEWEL:
					c.drawBitmap(Jewel.getJewelBitMap(RED_JEWEL), coordx,
							coordy, null);
					break;
				case GREEN_JEWEL:
					c.drawBitmap(Jewel.getJewelBitMap(GREEN_JEWEL), coordx,
							coordy, null);
					break;
				case CRYSTAL_JEWEL:
					c.drawBitmap(Jewel.getJewelBitMap(CRYSTAL_JEWEL), coordx,
							coordy, null);
					break;
				case ZIRCON_JEWEL:
					c.drawBitmap(Jewel.getJewelBitMap(ZIRCON_JEWEL), coordx,
							coordy, null);
					break;
				case BARRIER:
					c.drawBitmap(GraphicsFactory.getJewelBitMap(BARRIER) , coordx, coordy, null);
					break;
				}
			}
		}
		
		// Draw enemies

		// Draw the hero
		mHero.draw(c);

		// Draw hud (points, etc) last of all

	}

	public void drawLevelFailed() {

	}

	public void drawLevelFinished() {

	}

	public void onTouchEvent(MotionEvent event) {
		switch (mLevelState) {
		case LEVEL_PLAYING: // Hero is playing
			if (event.getX() <= mWidth / 3) {
				Log.i("a", "IZQ!");
				onLeftEvent();
			} else if (mWidth / 3 < event.getX()
					&& event.getX() <= mWidth * 2 / 3) {
				Log.i("a", "JUMP");
				onJumpEvent();
			} else {
				Log.i("a", "RIGHT");
				onRightEvent();
			}
			break;
		case LEVEL_FINISHED: // Hero finished the level
			break;
		case LEVEL_FAILED: // Hero failed the level
			break;
		}
	}

	private void onLeftEvent() {
		mHero.moveLeft();
		if (heroCorrectPosition()) {
			if (mHero.getHeroState() != Hero.HERO_FALLING
					&& mHero.getHeroState() != Hero.HERO_JUMPING) {
				mHero.stateGoLeft();
			}
		} else {
			mHero.moveRight(); //While jumping
			mHero.setJumpingDirection(Hero.DIR_RIGHT);
		}
	}

	private void onRightEvent() {
		mHero.moveRight();
		if (heroCorrectPosition()) {
			if (mHero.getHeroState() != Hero.HERO_FALLING
					&& mHero.getHeroState() != Hero.HERO_JUMPING) {
				mHero.stateGoRight();
			}
		} else {
			mHero.moveLeft(); //While jumping
			mHero.setJumpingDirection(Hero.DIR_LEFT);
		}
	}

	private void onJumpEvent() {
		if (mHero.getHeroState() != Hero.HERO_FALLING
				&& mHero.getHeroState() != Hero.HERO_JUMPING) {
			mHero.stateJump();
		}
	}

	// Private methods
	private void restartLevel() {
		mLevelState = LEVEL_PLAYING;
		mLevelMatrix = new int[MATRIX_WIDTH][MATRIX_HEIGHT];
		Jewel.loadBitmaps(mLevelActivity.getApplicationContext());
		GraphicsFactory.loadBitmaps(mLevelActivity.getApplicationContext());

		for (int i = 0; i < MATRIX_WIDTH; i++)
			for (int j = 0; j < MATRIX_HEIGHT; j++)
				mLevelMatrix[i][j] = NOTHING;

		loadLevel();
	}

	public void startLevel() {
		mRenderLoop.start();
	}

	private void loadLevel() {
		levelTest();
	}

	private void levelTest() { 
		mHero.setPosition(300, 50);
		mLevelMatrix[5][5] = BLUE_JEWEL;
		mLevelMatrix[9][2] = YELLOW_JEWEL;
		mLevelMatrix[3][7] = CRYSTAL_JEWEL; 
		mLevelMatrix[15][7] = CRYSTAL_JEWEL;
		mLevelMatrix[6][6] = BARRIER;
		mLevelMatrix[2][8] = BARRIER;
		mLevelMatrix[4][8] = BARRIER;
		mLevelMatrix[15][8] = BARRIER;
	}

	public void stopLevel() {
		mRenderLoop.stop();
	}
	
	public void initializeCellSizes() {
		PIXEL_PER_CELL_WIDTH = LevelActivity.mHeight / 9;
		PIXEL_PER_CELL_HEIGHT =  LevelActivity.mWidth / 16;
	}
	//a[0] is the left top object
	//a[1] is the left bottom object
	//a[2] is the right top object
	//a[3] is the right bottom object

	private boolean checkNeighbors(int x, int y) {
		 
		return false;
	}
	/*
	 * private void _level1(); private void _level2();
	 */
}
