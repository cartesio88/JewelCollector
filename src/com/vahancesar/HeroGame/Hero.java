package com.vahancesar.HeroGame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class Hero extends Drawable {
	public static final int HERO_STILL = 0;
	public static final int HERO_RUNNING = 1;
	public static final int HERO_JUMPING = 2;
	public static final int HERO_FALLING = 3;
	public static final int DIR_LEFT = 0;
	public static final int DIR_RIGHT = 1;

	private final int SPEED_RUNNING = 2;
	private final int SPEED_JUMP = 15; // This may change
	private final int FORCE_JUMP = 140;
	private final int MAX_LIFE = 100;

	private Sprite mCurrentSprite;
	private Sprite mAnimRun[];
	private Sprite mAnimJumping[], mAnimStill[];
		
	private int mScore = 0;
	private boolean mHasSpade = false;
	private int mHeroState = HERO_STILL;
	private int mDir = DIR_RIGHT;
	private int mLife = MAX_LIFE;
	private int mJumpProgress = 0;
	private int mStillCounter = 0;

	/* Items */
	Hero(Context c) {
		loadSprites(c);
		getBounds().right = mAnimStill[DIR_LEFT].getSprite(0).getHeight();
		getBounds().bottom = mAnimStill[DIR_LEFT].getSprite(0).getWidth();
		restart();
	}

	public void restart() {
		mScore = 0;
		mDir = DIR_RIGHT;
		mLife = MAX_LIFE;
		this.stateStill();
		mHasSpade = false;
		mJumpProgress = 0;
	}

	public void moveLeft() {
		getBounds().left -= SPEED_RUNNING;
	}

	public void moveRight() {
		getBounds().left += SPEED_RUNNING;
	}

	public void moveUp() {
		getBounds().top -= SPEED_JUMP;
	}

	public void moveDown() {
		getBounds().top += SPEED_JUMP;
	}

	public void updateJump() {
		mJumpProgress += SPEED_JUMP;
	}

	public void stateGoLeft() {
		mDir = DIR_LEFT;
		mCurrentSprite = mAnimRun[mDir];
		mHeroState = HERO_RUNNING;
		resetStillCounter();
	}

	public void stateGoRight() {
		mDir = DIR_RIGHT;
		mCurrentSprite = mAnimRun[mDir];
		mHeroState = HERO_RUNNING;
		resetStillCounter();
	}

	public boolean keepJumping() {
		return mJumpProgress < FORCE_JUMP;
	}

	public void stateJump() {
		mCurrentSprite = mAnimJumping[mDir];
		mHeroState = HERO_JUMPING;
	}

	public void stateFalling() {
		mCurrentSprite = mAnimJumping[mDir];
		mHeroState = HERO_FALLING;
		mJumpProgress = 0;
	}

	public void stateStill() {
		mCurrentSprite = mAnimStill[mDir];
		mHeroState = HERO_STILL;
	}

	public void resetStillCounter() {
		mStillCounter = 0;
	}

	public boolean updateStillCounter() {
		++mStillCounter;
		if (mStillCounter > 5) {
			mStillCounter = 0;
			return true;
		}
		return false;
	}

	public void setPosition(int x, int y) {
		getBounds().left = x;
		getBounds().top = y;
	}
	
	public void setJumpingDirection(int dir){
		mDir = dir;
		mCurrentSprite = mAnimJumping[mDir];
	}

	@Override
	public void draw(Canvas c) {
		c.drawBitmap(mCurrentSprite.getNextSprite(), getBounds().left,getBounds().top, null);
	}

	public int getHeroState() {
		return mHeroState;
	}
	
	private void loadSprites(Context c){
		/* Left animation sprite */
		mAnimRun = new Sprite[2];
		mAnimRun[DIR_LEFT] = new Sprite(8, 100, c);
		mAnimRun[DIR_LEFT].addImage(R.drawable.thief1);
		mAnimRun[DIR_LEFT].addImage(R.drawable.thief2);
		mAnimRun[DIR_LEFT].addImage(R.drawable.thief3);
		mAnimRun[DIR_LEFT].addImage(R.drawable.thief4);
		mAnimRun[DIR_LEFT].addImage(R.drawable.thief5);
		mAnimRun[DIR_LEFT].addImage(R.drawable.thief6);
		mAnimRun[DIR_LEFT].addImage(R.drawable.thief7);
		mAnimRun[DIR_LEFT].addImage(R.drawable.thief8);

		/* Run animation sprite */
		mAnimRun[DIR_RIGHT] = new Sprite(8, 200, c);
		mAnimRun[DIR_RIGHT].addImageInversed(R.drawable.thief1);
		mAnimRun[DIR_RIGHT].addImageInversed(R.drawable.thief2);
		mAnimRun[DIR_RIGHT].addImageInversed(R.drawable.thief3);
		mAnimRun[DIR_RIGHT].addImageInversed(R.drawable.thief4);
		mAnimRun[DIR_RIGHT].addImageInversed(R.drawable.thief5);
		mAnimRun[DIR_RIGHT].addImageInversed(R.drawable.thief6);
		mAnimRun[DIR_RIGHT].addImageInversed(R.drawable.thief7);
		mAnimRun[DIR_RIGHT].addImageInversed(R.drawable.thief8);

		/* Jumping left */
		mAnimJumping = new Sprite[2];
		mAnimJumping[DIR_LEFT] = new Sprite(1, 200, c);
		mAnimJumping[DIR_LEFT].addImage(R.drawable.thief1);

		/* Jumping right */
		mAnimJumping[DIR_RIGHT] = new Sprite(1, 200, c);
		mAnimJumping[DIR_RIGHT].addImageInversed(R.drawable.thief1);

		/* Still left */
		mAnimStill  =new Sprite[2];
		mAnimStill[DIR_LEFT] = new Sprite(1, 200, c);
		mAnimStill[DIR_LEFT].addImage(R.drawable.thief1);

		/* Still right */
		mAnimStill[DIR_RIGHT] = new Sprite(1, 200, c);
		mAnimStill[DIR_RIGHT].addImageInversed(R.drawable.thief1);

	}

	@Override
	public int getOpacity() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void setAlpha(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setColorFilter(ColorFilter arg0) {
		// TODO Auto-generated method stub

	}

}
