package se.qori.game;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import se.egy.graphics.Drawable;

public class Entity implements Drawable{
	private double x;
	private double y;
	private Image img;
	private Rectangle rec = null;
	
	public Entity(Image img, int x, int y) {
		this.x = x;
		this.y = y;
		this.img = img;
		rec = new Rectangle((int)x, (int)y/*, img.getWidth(null), img.getHeight(null)*/);
	}
	
	public Rectangle getRectangle(){
	    rec.setLocation((int)x, (int)y);
	    return rec;
	}
	
	public boolean collision(Entity entity){
	    getRectangle(); // Uppdaterar positionen på den egna rektangeln
	    return rec.intersects(entity.getRectangle());
	}
	
	public double getX(){
		return this.x;
	}

	public double getY(){
		return this.y;
	}
	
	public void setX(double x){
		this.x = x;
	}
	
	public void setY(double y){
		this.y = y;
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.drawImage(img, (int)x, (int)y, null);
	}

	public int getWidth() {
		return img.getWidth(null);
	}
}
