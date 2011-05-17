package gameobjects;

import io.ResourceManager;

import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;



public class GearPair implements IGameObject {

	private Gear smallGear, bigGear;
	
	private Vector2f position;
	
	private double rotationSpeed;
	private double rotation;
	
	public GearPair(float gearsRotationSpeed, Vector2f position, float scale ) {
		
		
		this.position = position;		
		
		Random random = new Random();
		
		this.rotationSpeed = (random.nextDouble()-0.5) / 100;
		
		int color = random.nextInt(155) + 100;
		
		Color filterColor = new Color( color, color, color, random.nextInt(200) );

		//Each gear need a own image object, because when making tranforming/rotating images they
		//need be separate image objects so they don't mess each other transformations.		
		Image smallGearImage =  ResourceManager.getInstance().getImage("SMALL_GEAR").copy();
		smallGearImage.setCenterOfRotation( smallGearImage.getWidth()/2*scale, smallGearImage.getHeight()/2*scale);
		Vector2f smallPos = new Vector2f( position.x, position.y );
		smallGear = new Gear( smallPos, gearsRotationSpeed, smallGearImage, 291.2f/2, filterColor );
		smallGear.scale = scale;
				

		Image bigGearImage = ResourceManager.getInstance().getImage("BIG_GEAR").copy();
		bigGearImage.setCenterOfRotation( bigGearImage.getWidth()/2*scale, bigGearImage.getHeight()/2*scale);
		Vector2f bigPos = new Vector2f( position.x + smallGearImage.getWidth()*scale - 30*scale,
										position.y - (bigGearImage.getHeight() - smallGearImage.getHeight())/2*scale);
		
		bigGear = new Gear( bigPos, -gearsRotationSpeed, bigGearImage, 312/2, filterColor );
		bigGear.rotation = 12;
		bigGear.scale = scale;		
	}

	@Override
	public void render(GameContainer cont, Graphics g) throws SlickException {	
		g.rotate( position.x, position.y, (float) rotation );
		smallGear.render(cont, g);
		bigGear.render(cont, g);
		g.resetTransform();
	}

	@Override
	public void update(GameContainer cont, int delta) throws SlickException {
		smallGear.update(cont, delta);
		bigGear.update(cont, delta);		
		rotation += rotationSpeed * delta;
	}
	
	public enum GearType {
		BIG_GEAR, SMALL_GEAR;
	}
	
	public class Gear implements IGameObject {

		private Image image;
		private Color filterColor;
		
		private Vector2f position;
		private double rotationSpeed;
		private double rotation;
		private float scale;
		
		public Gear( Vector2f position, float rotationSpeed, Image image, float radius, Color filterColor ) {
						
			this.position = position;
			this.image = image;
			this.rotationSpeed = rotationSpeed/(2* Math.PI *radius);
			
			this.filterColor = filterColor;
		}

		@Override
		public void render(GameContainer cont, Graphics g) {
			image.draw( position.x, position.y, scale, filterColor);
		}

		@Override
		public void update(GameContainer cont, int delta) {
			rotation += rotationSpeed*delta;
			image.setRotation( (float) rotation );	
		}
	}
}