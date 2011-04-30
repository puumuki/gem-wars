package gemwars;


/**
 * Used to manage various game options
 *
 */
public class Options {

	private static Options __instance = null;
	
	private float soundVolume = (float)1.0;
	private float musicVolume = (float)0.8;
	
	
	private Options() {
		
	}
	
	public static Options getInstance() {
		if (__instance == null) {
			__instance = new Options();
		}
		return __instance;
	}

	public void save() {
		
	}
	
	public void load() {
		
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

}
