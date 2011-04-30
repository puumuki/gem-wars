package test;

import io.Options;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;


import org.junit.Test;

public class TestOptions {
	
	
	
	@Test
	public void testLoadingOptions() throws IllegalArgumentException, IllegalAccessException, IOException {
		
		Options options = Options.getInstance();
		options.setMusicVolume(3f);
		options.setSoundVolume(2.1f);		
		options.save( new File("Gemwars.properties"));		
		options.load(new File("Gemwars.properties"));
		
		Assert.assertEquals(3f, options.getMusicVolume());
		Assert.assertEquals(3f, options.getMusicVolume());
	}
}
