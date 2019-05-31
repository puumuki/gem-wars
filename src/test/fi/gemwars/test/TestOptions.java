package fi.gemwars.test;

import fi.gemwars.io.Options;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import junit.framework.Assert;


import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.newdawn.slick.SlickException;

public class TestOptions {
	
	@Rule
	public TemporaryFolder testFolder = new TemporaryFolder();
		
	@Test
	public void testLoadingOptions() throws IllegalArgumentException, IllegalAccessException, IOException, SlickException {			
		
		File target = testFolder.newFile("testGemwars.properties");
		
		
		Options options = Options.getInstance();
		options.setMusicVolume(3f);
		options.setSoundVolume(2.1f);		
		options.setFullscreen(false);
		
		options.save(target);
				
		Properties properties = new Properties();
		
		properties.load( new FileInputStream(target));
		
		properties.setProperty("screenwidth", "999");
		properties.setProperty("screenheight", "888");
		
		FileOutputStream stream = new FileOutputStream(new File("testGemwars.properties"));
		properties.store(stream, "");
				
		stream.close();
		
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
	 * NumberFormatException is thrown wrapped in a {@link SlickException} 
	 * 
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws IOException
	 * @throws SlickException 
	 */
	@Test( expected=SlickException.class)
	public void testLoadingInvalidValue() throws IllegalArgumentException, IllegalAccessException, IOException, SlickException {			
		
		File target = new File("testGemwars.properties"); 
		
		Options options = Options.getInstance();

		options.save(target);
		
		Properties properties = new Properties();
		
		properties.load( new FileInputStream(target));
		
		properties.setProperty("screenwidth", "asfedasf");

		FileOutputStream stream = new FileOutputStream(new File("testGemwars.properties"));
		properties.store(stream, "");
		stream.close();
		
		options.load(target);

		
	}
}
