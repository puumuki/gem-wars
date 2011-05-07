package gemwars;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.text.html.Option;

import io.Options;
import io.ResourceManager;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;



/**
 * Tästä se lähtee.
 *
 *
 *  This file is part of GemWars.
 *
 *  GemWars is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  GemWars is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with GemWars.  If not, see <http://www.gnu.org/licenses/>.
 */
public class Gemwars extends StateBasedGame {
	
    public static final int MAINMENUSTATE          = 0;
    public static final int GAMEPLAYSTATE          = 1;
    public static final int CONFIGURATION_MENU_STATE = 2;
    public static final int NYAN_STATE = 3;
    
	public Gemwars() {
		super("GemWars");
	}

	/**
	 * Pääohjelma
	 * @param args ei käytössä
	 * @throws  
	 */
	public static void main(String[] args) throws SlickException {	
		
		Options properties = loadConfigurations();
		
        AppGameContainer app = new AppGameContainer(new Gemwars());        
        
        app.setDisplayMode(properties.getScreenWitdh(), 
        				   properties.getScreenHeight(), 
        				   properties.getFullscreen());
        
        app.setTargetFrameRate(properties.getTargetFrameRate());
        app.setMusicVolume(properties.getMusicVolume());
        app.setSoundVolume(properties.getSoundVolume());
        
        app.start();
	}
	
	private static Options loadConfigurations() throws SlickException {
		Options properties = Options.getInstance();
		
		try {
			properties.load( new File(Options.CONFIGURATION_FILE));
		} catch (NumberFormatException e) {
			throw new SlickException("Can't parse number from the string.", e);
		} catch (IllegalArgumentException e) {
			throw new SlickException("General error",e);
		} catch (IllegalAccessException e) {
			throw new SlickException("General error",e);
		} catch (IOException e) {
			throw new SlickException("Can't read confguration file. ",e);
		}
		
		return properties;
	}
	
	@Override
	public void initStatesList(GameContainer gameContainer) throws SlickException {
        this.addState(new MainMenuState(MAINMENUSTATE));
        this.addState(new GameplayState(GAMEPLAYSTATE));
        this.addState(new ConfigurationMenuState(CONFIGURATION_MENU_STATE));
        this.addState(new NyanState(NYAN_STATE));
        this.enterState(NYAN_STATE);
	}
}

