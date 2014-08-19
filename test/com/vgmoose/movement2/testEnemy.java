package com.vgmoose.movement2;

import java.util.ArrayList;

import android.test.ActivityInstrumentationTestCase2;

public class testEnemy extends ActivityInstrumentationTestCase2<Main> {

	private Enemy enemy;

	public testEnemy() { 
		super(Main.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		enemy = new Enemy(0, 0, null);
	
	}
	
	public void testEnemyStuff()
	{
		// TODO: make a bunch of tests here similar to testSack
	}

}