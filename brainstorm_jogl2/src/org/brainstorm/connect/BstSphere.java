package org.brainstorm.connect;

import static javax.media.opengl.GL2.*;   // JOGL2

public class BstSphere extends BstGeometry {
	
	// Same ID used by all BSTSphere object
	// We assume all sphere will have the same level of detail
	// Hence the same shape
	// public static int listID;
	
	public static float DEFAULT_RADIUS = 0.2f;
	
	public float radius;
	
	public BstSphere() {
		super(0);
		
		radius = DEFAULT_RADIUS;
	}
	
	public void setRadius(float rad) {
		radius = rad;
	}
	
	protected void render(OpenGLWrapper gwl) {
		float cx = 0.f;
		float cy = 0.f;
		int num_segments = 25;
		
		double theta = 2 * 3.1415926 / num_segments;
		// Calculate the tangential factor 
		double tangetial_factor = Math.tan(theta);
		// Calculate the radial factor 
		double radial_factor = Math.cos(theta);
		// We start at angle = 0 
		float x = this.radius;
		float y = 0; 
	    
		gwl.gl.glBegin(GL_POLYGON); 
		for (int ii = 0; ii < num_segments; ii++) 
		{ 
			// Output vertex 
			gwl.gl.glVertex2f(x + cx, y + cy);
	        
			//calculate the tangential vector 
			//remember, the radial vector is (x, y) 
			//to get the tangential vector we flip those coordinates and negate one of them 
			float tx = -y; 
			float ty = x; 
	        
			//add the tangential vector 
			x += tx * tangetial_factor; 
			y += ty * tangetial_factor; 
	        
			//correct using the radial factor 
			x *= radial_factor; 
			y *= radial_factor; 
		} 
		gwl.gl.glEnd(); 
	}
	
	protected void renderPoint(OpenGLWrapper gwl) {
		//gwl.gl.glEnable(gwl.gl.GL_POINT_SMOOTH);
		//gwl.gl.glPointSize(radius);
		
		//gwl.gl.glBegin(gwl.gl.GL_POLYGON);
		//gwl.gl.glVertex2f(arg0, arg1)
		//gwl.gl.glBegin(gwl.gl.GL_POINTS);
		//gwl.gl.glVertex2f(0, 0);
		
		//gwl.gl.glEnd();
	}
	
}
