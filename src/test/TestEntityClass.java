package test;

import junit.framework.Assert;
import gameobjects.AEntity;

import org.junit.Test;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;


public class TestEntityClass {

	@Test
	public void testCreatingAEntity() throws SlickException {
		
		AEntity entity = getEntity();
		
		entity.setPos(5, 4);
		
		Assert.assertEquals( 5, entity.positionX );
		Assert.assertEquals( 4, entity.positionY );
		
		entity.initPosition(3, 3);
		
		Assert.assertEquals( 3, entity.positionX );
		Assert.assertEquals( 3, entity.positionY );
		Assert.assertEquals( 3, entity.startPosX );
		Assert.assertEquals( 3, entity.startPosY );
		
		entity.setPos(50, 30);
		
		entity.resetToStaringPosition();
		
		Assert.assertEquals(3, entity.positionX);
		Assert.assertEquals(3, entity.positionY);
	}
	
	@Test
	public void testCompareTo() {
		AEntity e1 = getEntity();
		e1.layer = 0;
		AEntity e2 = getEntity();
		e2.layer = 1;
		AEntity e3 = getEntity();
		e3.layer = -1;
		
		AEntity e4 = getEntity();
		e3.layer = 0;
		
		Assert.assertEquals(0, e1.compareTo(e3));
		Assert.assertEquals(-1, e2.compareTo(e3));
		Assert.assertEquals(0, e1.compareTo(e4));
		Assert.assertEquals(1, e4.compareTo(e2));
	}
	
	
	private AEntity getEntity() {
		
		AEntity entity = new AEntity() {	
			
			@Override
			public void update(GameContainer cont, int delta) throws SlickException {}
			
			@Override
			public void render(GameContainer cont, Graphics grap) throws SlickException {}
		};
		
		
		return entity;
	}
}
