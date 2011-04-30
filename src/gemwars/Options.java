package gemwars;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Properties;

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
	
	private Options() {}
	
	/**
	 * Default configuration file path
	 */
	private final static String CONFIGURATION_FILE = "Gemwars.properties";
	
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
	
	public void load(File file) throws NumberFormatException, 
							  IllegalArgumentException, 
							  IllegalAccessException, 
							  IOException {
					
		if( file.isFile() ) {
			FileInputStream stream = new FileInputStream(file);
			
			Properties properties = new Properties();
			properties.load(stream);				
							
			for( Field field : this.getClass().getDeclaredFields() ) {
				
				Property annotation = field.getAnnotation(Property.class);
										
				if( annotation  != null ) {
				
					String key = readKey(field, annotation);
					String value = properties.getProperty(key);
					
					field.setAccessible(true);
					
					//This is an ugly way to convert string to back datatypes. 
					if( field.getType().equals( Double.class )) {
						field.set(this, Double.parseDouble(value));
					}
					if( field.getType().equals( Float.class )) {
						field.set(this, Float.parseFloat(value));
					}
					if( field.getType().equals( String.class )) {
						field.set(this, value );
					}
					if( field.getType().equals( Integer.class )) {
						field.set(this, Integer.parseInt(value) );
					}						
					if( field.getType().equals( Short.class )) {
						field.set(this, Short.parseShort(value) );
					}
					if( field.getType().equals( Long.class )) {
						field.set(this, Long.parseLong(value));
					}
					if( field.getType().equals( Byte.class )) {
						field.set(this, Byte.parseByte(value));
					}	
				}
			}
		}
		
		Log.info("Configuration are now loaded from file :" + file.getAbsolutePath());
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
}
