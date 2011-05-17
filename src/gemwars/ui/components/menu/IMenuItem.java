package gemwars.ui.components.menu;

import gameobjects.IGameObject;

import org.newdawn.slick.UnicodeFont;

public interface IMenuItem extends Comparable<IMenuItem>, IGameObject {

	public abstract void setFont(UnicodeFont font);

	public abstract void setText(String text);
	
	public abstract String getText();

	public abstract void setIndex(int index);

	public abstract int getIndex();

	public abstract boolean getActive();

	public abstract void setActive(boolean isActive);

	public abstract Object getValue();	
}