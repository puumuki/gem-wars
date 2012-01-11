package fi.gemwars.test;

import fi.gemwars.io.Options;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import junit.framework.Assert;


import org.junit.After;
import org.junit.Test;

public class TestOptions {
	
	@After
	public void cleanUp() {
		try {
			File file = new File("testGemwars.properties");
			
			if( file.exists() ) {
				file.delete();						
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	public void testLoadingOptions() throws IllegalArgumentException, IllegalAccessException, IOException {			
		
		
		Options options = Options.getInstance();
		options.setMusicVolume(3f);
		options.setSoundVolume(2.1f);		
		options.setFullscreen(false);
		
		options.save( new File("testGemwars.properties"));
		
		Properties properties = new Properties();
		
		properties.load( new FileInputStream("testGemwars.properties"));
		
		properties.setProperty("screenwidth", "999");
		properties.setProperty("screenheight", "888");
		
		FileOutputStream stream = new FileOutputStream(new File("testGemwars.properties"));
		properties.store(stream, "");
		
		options.load(new File("testGemwars.properties"));
		
		Assert.assertEquals(3f, options.getMusicVolume());
		Assert.assertEquals(3f, options.getMusicVolume());
		
		Assert.assertEquals( new Integer(999), options.getScreenWitdh());
		Assert.assertEquals( new Integer(888), options.getScreenHeight());
		Assert.assertEquals( new Boolean(false), options.getFullscreen());
		
		stream.close();
	}
	
	/**
	 * When conversion from a string type to a number fails,
	 * NumberFormatException is thrown wrapped in a RuntimeException 
	 * 
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws IOException
	 */
	@Test( expected=RuntimeException.class)
	public void testLoadingInvalidValue() throws IllegalArgumentException, IllegalAccessException, IOException {			
		Options options = Options.getInstance();

		options.save( new File("testGemwars.properties"));
		
		Properties properties = new Properties();
		
		properties.load( new FileInputStream("testGemwars.properties"));
		
		properties.setProperty("screenwidth", "asfedasf");

		FileOutputStream stream = new FileOutputStream(new File("testGemwars.properties"));
		properties.store(stream, "");
		
		options.load(new File("testGemwars.properties"));

		stream.close();
	}
}
