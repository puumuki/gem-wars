package gemwars.ui.components.menu;

import java.io.File;

public class FileMenuItem extends BasicMenuItem {

	private File file;
	
	public FileMenuItem(int posX, int posY, String text, File file ) {
		super(posX, posY, text);
		this.file = file;
		
	}

	public File getFile() {
		return file;
	}	
}
