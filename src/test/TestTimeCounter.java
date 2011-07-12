package test;

import junit.framework.Assert;

import org.junit.Test;

import utils.TimeCounter;


public class TestTimeCounter {

	@Test
	public void testTimeCounter() throws InterruptedException {
		
		TimeCounter timeCounter = new TimeCounter();
		
		for( int i = 0; i<10; i++ ) {
			timeCounter.start();
			
			Thread.sleep(200);
						
			Assert.assertTrue( "Value should be greater than 0.2s", timeCounter.timeElapsedInSeconds() >= 0.2 );
			Assert.assertTrue( "Value should be greater than 200ms", timeCounter.timeElapsedInMilliseconds() >= 200 );
			
			timeCounter.stop();
			timeCounter.reset();
			
			Assert.assertEquals( 0.0, (double)timeCounter.timeElapsedInSeconds() );
			Assert.assertEquals( 0, timeCounter.timeElapsedInMilliseconds() );
			
			
			timeCounter.start();
			
			Thread.sleep(200);
			
			
			timeCounter.stop();
			Assert.assertTrue( "Value should be greater than 0.2s", timeCounter.timeElapsedInSeconds() >= 0.2 );
						
			Thread.sleep(200);
			
			Assert.assertTrue( "Value should be greater than 0.2s", timeCounter.timeElapsedInSeconds() >= 0.2 );
		}
	}	
}
