package com.vgmoose.movement2;

import java.util.ArrayList;

import android.test.ActivityInstrumentationTestCase2;

public class testSack extends ActivityInstrumentationTestCase2<Main> {

	private Sack sack;

	public testSack() { 
		super(Main.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		sack = new Sack(0, 0);
	}
	
	public void testAdd()
	{
		// add a dummy present to the sack
		Present p = new Present(0, 0, null, null);
		sack.addPresent(p);
		
		// get the contents of the sack
		ArrayList<Present> contents = sack.getPresents();
		Present sackPresent = contents.remove(0);
		
		// pass the test if the present in the sack is the one we added
		assertTrue(sackPresent == p);
	}
	
	public void testFillWeight()
	{
		// TODO: fill me out, look at testAdd() to see how to make it look
	}
	
	public void testFillQuantity()
	{
		// TODO: fill me out, look at testAdd() to see how to make it look
	}
	
	public void testRemove()
	{
		// TODO: fill me out, look at testAdd() to see how to make it look
	}
	
	public void testClear()
	{
		// TODO: fill me out, look at testAdd() to see how to make it look
	}
	
	public void testWeight()
	{
		// TODO: fill me out, look at testAdd() to see how to make it look
	}
}