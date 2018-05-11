package org.brainstorm.connect;
import javax.vecmath.Color3f;


public class BstColoringAttributes {

	static int SHADE_FLAT = 0;
	static int SHADE_GOURAUD = 1;
	
	public Color3f col;
	public int shade;
	
	public BstColoringAttributes() {
		this(new Color3f(0f, 0f, 0f), 0);
	}
	
	public BstColoringAttributes(Color3f color) {
		this(color, 0);
	}
	
	public BstColoringAttributes(Color3f color, int shadeModel) {
		col = new Color3f(color);
		if (shadeModel < 2) {
			shade = shadeModel;
		}
	}
	
	public void getColor(Color3f color) {
		color.set(col);
	}
	
	public void setColor(Color3f color) {
		col.set(color);
	}
	
	public void setColor3f(float R, float G, float B) {
		col.set(R, G, B);
	}
	
}
