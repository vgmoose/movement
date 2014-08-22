package com.vgmoose.movement2;

import java.util.ArrayList;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

public class testSack extends ActivityInstrumentationTestCase2<Main> {

	private Sack sack;

	public testSack() { 
		super(Main.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		sack = new Sack(0, 0, true);
	}
	
	public void testAdd()
	{
		// add a dummy present to the sack
		Present p = new Present(0, 0, null, null);
		sack.addPresent(p);
		
		// get the contents of the sack
		Present sackPresent = sack.getPresents().get(0);
		
		// pass the test if the present in the sack is the one we added
		assertTrue(sackPresent == p);
	}
	
	public void testFillWeight()
	{
		Present p1 = new Present(1, 0, null, null);
		Present p2 = new Present(3, 0, null, null);
		Present p3 = new Present(4, 0, null, null);
		Present p4 = new Present(1, 0, null, null);

		sack.setMaxWeight(5);
		sack.addPresent(p1);
		sack.addPresent(p2);
		
		//skips the 3rd pressent because it is too big to fit
		sack.addPresent(p3);
		
		sack.addPresent(p4);
		assertEquals(5,sack.getCurrentWeight());
	}
	
	public void testFillQuantity()
	{
		// TODO: fill me out, look at testAdd() to see how to make it look
	}
	
	public void testRemove()
	{
		// add a dummy present to the sack
		Present p = new Present(0, 0, null, null);
		Present p2 = new Present(1, 0, null, null);
		sack.setMaxWeight(5);
		sack.addPresent(p);
		sack.addPresent(p2);
		
		// get the contents of the sack
		Present sackPresent = sack.losePresents(1).get(0);
		
		// pass the test if the present in the sack is the one we added
		assertTrue(sackPresent != sack.getPresents().get(0));
	}
	
	public void testClear()
	{
		Present p = new Present(0, 0, null, null);
		sack.addPresent(p);
		sack.addPresent(p);
		sack.addPresent(p);

		sack.clearSack();
		
		assertTrue(sack.getPresents().isEmpty());
	}
	
	public void testWeight()
	{
		// TODO: fill me out, look at testAdd() to see how to make it look
	}
}