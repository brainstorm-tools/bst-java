package org.brainstorm.connect;
import java.awt.Color;

import javax.vecmath.Color3f;
import javax.vecmath.Color4f;
import javax.vecmath.Point3f;


public abstract class BstGeometry implements BstDrawable {
	
	public Point3f[] vertices;
	public Color4f[] vertexColor;
	
	// OpenGL list ID
	int listID;
	boolean listIsBuilt;
	
	public BstTransform3D trans;
	public BstAppearance appAtrb;
	
	public boolean visible;
	
	public BstGeometry(int vertexCount) {
		// Allocate structure for vertex
		vertices = new Point3f[vertexCount];
		// Allocate structure for colors
		vertexColor = new Color4f[vertexCount];
		
		// Default appearance
		appAtrb = new BstAppearance();
		// Default colors
		for (int i = 0; i < vertexColor.length; ++i) {
			vertexColor[i] = new Color4f(1f, 1f, 1f, 1f);
		}
		// List flag
		listIsBuilt = false;
		//
		trans = new BstTransform3D();
	}
	
	public void setAppearance(BstAppearance app) {
		appAtrb = app;
	}
	
	public void draw(OpenGLWrapper gwl) {
		boolean isVisible = true;
		if (appAtrb.hasRenderingAttributes()) {
			isVisible = appAtrb.rendAtrb.getVisible();
		}
		
		if (isVisible)
		{
			// Apply 3D transformation
			if (hasTransformation()) {
				trans.applyTransform(gwl);
			}
			
			// Standard color - White and Opaque
			Color3f color = new Color3f(Color.WHITE);
			float transparency = 0f;

			if (appAtrb.hasColoringAttributes()) {
				color.set(appAtrb.colorAtrb.col);
			}
			if (appAtrb.hasTransparencyAttributes()) {
				appAtrb.transAtrb.applyAttributes(gwl);
				transparency = appAtrb.transAtrb.transparency;
			}
			gwl.gl.glColor4f(color.x, color.y, color.z, 1 - transparency);
			
			if (listIsBuilt) {
				gwl.gl.glCallList(listID);
			}
			else {
				render(gwl);
			}
			
			if (appAtrb.hasTransparencyAttributes()) {
				appAtrb.transAtrb.disableAttributes(gwl);
			}
		}
	}
	
	@SuppressWarnings("static-access")
	public void buildList(OpenGLWrapper gwl) {
		listID = gwl.gl.glGenLists(1);
		gwl.gl.glNewList(listID, gwl.gl.GL_COMPILE);
			render(gwl);
		gwl.gl.glEndList();
		listIsBuilt = true;
	}
	
	public boolean hasTransformation() {
		return trans != null;
	}
	
	abstract protected void render(OpenGLWrapper gwl);
		
	// This function assumes both structure are of same length
	public void setCoordinates(Point3f[] coordinates) {
		for (int i = 0; i < vertices.length; ++i) {
			vertices[i] = new Point3f(coordinates[i]);
		}
	}
	
	public void setColors(Color4f[] colors) {
		for (int i = 0; i < vertexColor.length; ++i) {
			vertexColor[i] = new Color4f(colors[i]);
		}
	}
	
	public void setAllVertexColorsTo(Color4f color) {
		for (int i = 0; i < vertexColor.length; ++i) {
			vertexColor[i] = new Color4f(color);
		}
	}
	
	public void setAllVertexColorsTo(Color3f color) {
		for (int i = 0; i < vertexColor.length; ++i) {
			vertexColor[i].set(color.x, color.y, color.z, vertexColor[i].w);
		}
	}
	
	public void setVertexTransparency(float trans) {
		for (int i = 0; i < vertexColor.length; ++i) {
			vertexColor[i].w = trans;
		}
	}

}
