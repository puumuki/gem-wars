package fi.gemwars.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TimeCounterTest {

	@Test
	public void testTimeCounter() throws Exception {

		TimeCounter timeCounter = new TimeCounter();

		for (int i = 0; i < 10; i++) {
			timeCounter.start();

			Thread.sleep(200);

			assertTrue("Value should be greater than 0.2s", timeCounter.timeElapsedInSeconds() >= 0.2);
			assertTrue("Value should be greater than 200ms", timeCounter.timeElapsedInMilliseconds() >= 200);

			timeCounter.stop();
			timeCounter.reset();

			assertEquals(0.0, timeCounter.timeElapsedInSeconds(), 0.001f);
			assertEquals(0, timeCounter.timeElapsedInMilliseconds());

			timeCounter.start();

			Thread.sleep(200);

			timeCounter.stop();
			assertTrue("Value should be greater than 0.2s", timeCounter.timeElapsedInSeconds() >= 0.2);

			Thread.sleep(200);

			assertTrue("Value should be greater than 0.2s", timeCounter.timeElapsedInSeconds() >= 0.2);
		}
	}
}
