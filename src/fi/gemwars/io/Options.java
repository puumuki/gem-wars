package fi.gemwars.io;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Properties;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Log;

/**
 * Used to manage various game options. 
 * 
 * If you are adding new values to this don't use
 * the primitive data types. Primitive data types are not yeat supported.  
 *
 * Properties are saved to an INI-file as key value pairs.
 * Each property have it own key that is determined with @Property annotation.
 * Only field that have @Property annotation are considered as a property.
 * If you have not set any value for @Property() then the field name is used as a key. 
 * 
 * Supported data types are: Boolean, Byte, String, Integer, Long, Float, Double
 * 
 * NO PRIMITIVE DATA TYPES!
 * 
 * When adding a new property do next steps:
 * 
 * 1. Create a field for property
 * 2. Use the @Property annotation and give property a key that is used in ini-file.
 * 3. Create Setter & Getters
 * 
 */
public class Options {

	private static Options __instance = null;

	@Property("soundvolume")
	private Float soundVolume = 1.0f;

	@Property("musicvolume")
	private Float musicVolume = 0.8f;

	@Property("isfullscreeninuse")
	private Boolean fullscreen = false;

	@Property("screenwidth")
	private Integer screenWitdh = 640;

	@Property("screenheight")
	private Integer screenHeight = 480;	

	@Property("targetFrameRate")
	private Integer targetFrameRate = 60;

	@Property("gemwarsFormUrl")
	private String gemwarsFormUrl = "http://teemuki.eu/gemwars/";

	//-- Add new property here and add setter getter for it. --
		
	private Options() {}

	/**
	 * Default configuration file path
	 */
	public final static String CONFIGURATION_FILE = "Gemwars.properties";

	public static Options getInstance() {
		if (__instance == null) {
			__instance = new Options();
		}
		return __instance;
	}

	public void save(File file) throws IllegalArgumentException, IllegalAccessException, IOException {

		Properties properties = new Properties();

		for( Field field : this.getClass().getDeclaredFields() ) {

			Property annotation = field.getAnnotation(Property.class);

			//This is how make sure that we are hanling property field
			if( annotation != null ) {			

				readKey(field, annotation);

				//We use annotation value as a property key
				properties.put( annotation.value(), field.get(this).toString() );

			}
		}

		FileOutputStream stream = new FileOutputStream(file);
		properties.store(stream, "Gemwars properties file is a Gem" );

		Log.info("Configurations saved to a file : " + file.getAbsolutePath() );
	}

	private String readKey(Field field, Property annotation) {
		String key = annotation.value();

		if( key.length() == 0 ) {
			key = field.getName();
		}

		return key.trim();
	}

	public void load(File file) throws SlickException {
		 
		try {
			
			Log.info("Starting loading configurations from " + file.getAbsolutePath());
			Log.info("Configurations:");
			
			FileInputStream stream = new FileInputStream(file);
		
			Properties properties = new Properties();
			properties.load(stream);				
	
			for( Field field : this.getClass().getDeclaredFields() ) {

				if( field.isAnnotationPresent(Property.class) ) {
	
					Property annotation = field.getAnnotation(Property.class);
					
					String key = readKey(field, annotation);
					String value = properties.getProperty(key);
	
					if( value != null ) {					
						populateField(field, value); 
					}
					
					Log.info( key + "=" + value );
				}
			}			
		} catch (FileNotFoundException e) {
			throw new SlickException("No configuration file could't be found. From path: " + file.getAbsolutePath());
		} catch (IOException e) {
			throw new SlickException("Fatal error reading configuration file. File path:  " + file.getAbsolutePath());
		}	

		Log.info("Configuration are now loaded from file :" + file.getAbsolutePath());
	}

	/**
	 * Method populated this class fields by using reflection. 
	 * 
	 * @param field
	 * @param value field value in string format
	 * @throws SlickException thrown if converting string value fails
	 */
	protected void populateField(Field field, String value) throws SlickException {

		field.setAccessible(true);

		Class<?>fieldType = findFieldType(field);
				
		try {						
			Constructor<?> consturctor = fieldType.getConstructor(String.class);			
			Object result = consturctor.newInstance(value);
			field.set(this, result);
		} catch (Exception e) {
			throw new SlickException("Problems read field named " + field.getName() 
									  + " field type " + field.getType().getSimpleName(), e);
		}					
	}

	private Class<?> findFieldType(Field field) {
		Class<?>fieldTypes[] = {Double.class, Float.class, 
								Long.class, Integer.class, 
								Short.class, Byte.class,
								Boolean.class,
								String.class};
		
		for( Class<?>fieldType : fieldTypes ) {
			if( field.getType().equals(fieldType) ) {
				return fieldType;
			}
		}
		
		return null;
	}
	
	public void setSoundVolume(float soundVolume) {
		this.soundVolume = soundVolume;
	}
	
	
	public float getSoundVolume() {
		return soundVolume;
	}
	
	public void setMusicVolume(float musicVolume) {
		this.musicVolume = musicVolume;
	}
	
	
	public float getMusicVolume() {
		return musicVolume;
	}
	
	public Boolean getFullscreen() {
		return fullscreen;
	}
	
	public void setFullscreen(Boolean fullscreen) {
		this.fullscreen = fullscreen;
	}
	
	public Integer getScreenWitdh() {
		return screenWitdh;
	}
	
	public void setScreenWitdh(Integer screenWitdh) {
		this.screenWitdh = screenWitdh;
	}
	
	public Integer getScreenHeight() {
		return screenHeight;
	}
	
	public void setScreenHeight(Integer screenHeight) {
		this.screenHeight = screenHeight;
	}
	
	public void setSoundVolume(Float soundVolume) {
		this.soundVolume = soundVolume;
	}
	
	public Integer getTargetFrameRate() {
		return targetFrameRate;
	}
	
	public void setTargetFrameRate(Integer targetFrameRate) {
		this.targetFrameRate = targetFrameRate;
	}
	
	public String getGemwarsFormUrl() {
		return gemwarsFormUrl;
	}
	
	public void setGemwarsFormUrl(String gemwarsFormUrl) {
		this.gemwarsFormUrl = gemwarsFormUrl;
	}
}
