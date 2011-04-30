package gemwars;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

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
    
	public Gemwars() {
		super("GemWars");
		
        this.addState(new MainMenuState(MAINMENUSTATE));
        this.addState(new GameplayState(GAMEPLAYSTATE));
        this.addState(new ConfigurationMenuState(CONFIGURATION_MENU_STATE));
        
        this.enterState(MAINMENUSTATE);
	}

	/**
	 * Pääohjelma
	 * @param args ei käytössä
	 * @throws  
	 */
	public static void main(String[] args) throws SlickException {		
        AppGameContainer app = new AppGameContainer(new Gemwars());        
        app.setDisplayMode(640, 480, false);
        app.start();
	}
	
	@Override
	public void initStatesList(GameContainer gameContainer) throws SlickException {
        this.getState(MAINMENUSTATE).init(gameContainer, this);
        this.getState(GAMEPLAYSTATE).init(gameContainer, this);
		
	}
}

