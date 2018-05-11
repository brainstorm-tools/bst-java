package org.brainstorm.connect;

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;


public class Node implements BstDrawable {
	
	BstSphere innerCircle;
	BstSphere outerCircle;
	Text2D textDisplay;
	
	public Node() {
		this(.0f, .0f, .0f);
	}
	
	public Node(float iX, float iY, float iZ) {
		this(new Vector3f(iX, iY, iZ));
	}
	
	public Node(Vector3f iPos) {
		innerCircle = new BstSphere();
		outerCircle = new BstSphere();
		
	    BstAppearance innerApp = innerCircle.appAtrb;
	    innerApp.setTransparencyAttributes(new BstTransparencyAttributes(0));
	    innerApp.setRenderingAttributes(new BstRenderingAttributes());
	    innerApp.setColoringAttributes(new BstColoringAttributes());
	    
	    BstAppearance outerApp = outerCircle.appAtrb;
	    outerApp.setTransparencyAttributes(new BstTransparencyAttributes(0));
	    outerApp.setRenderingAttributes(new BstRenderingAttributes());
	    outerApp.setColoringAttributes(new BstColoringAttributes());
	    
	    setPosition(iPos.x, iPos.y, iPos.z);
	    outerCircle.setAppearance(outerApp);
	    innerCircle.setAppearance(innerApp);
	    innerCircle.setRadius((2.0f / 3.0f) * outerCircle.radius);
	    
	    //textDisplay = new Text2D();
	}
	
	public void setPosition(float iX, float iY, float iZ) {
		innerCircle.trans.setTranslation(iX, iY, iZ + 0.1f);
		outerCircle.trans.setTranslation(iX, iY, iZ);
	}
	
	public Point3f Position() {
		return new Point3f(innerCircle.trans.translation);
	}
	
	// We assume by default the outer ring
	public void setColor(float iR, float iG, float iB) {
		outerCircle.appAtrb.colorAtrb.setColor3f(iR, iG, iB);
	}
	
	public void setInnerCircleColor(float iR, float iG, float iB) {
		innerCircle.appAtrb.colorAtrb.setColor3f(iR, iG, iB);
	}
	
	public void setOuterCircleColor(float iR, float iG, float iB) {
		outerCircle.appAtrb.colorAtrb.setColor3f(iR, iG, iB);
	}

	public void setVisibility(boolean visible) {
		outerCircle.appAtrb.rendAtrb.setVisible(visible);
		innerCircle.appAtrb.rendAtrb.setVisible(visible);
	}
	
	public void setTransparency(float iA) {
		outerCircle.appAtrb.transAtrb.setTransparency(iA);
		innerCircle.appAtrb.transAtrb.setTransparency(iA);
	}
	
	public void setDisplayRadius(float radius) {
		outerCircle.setRadius(radius);
		innerCircle.setRadius(0.75f * outerCircle.radius);
	}
	
	public void setOuterCircleRadius(float radius) {
		outerCircle.setRadius(radius);
	}
	
	public void setInnerCircleRadius(float radius) {
		innerCircle.setRadius(radius);
	}
	
	public void setTextPosition(float iX, float iY, float iZ) {
		if (textDisplay == null) textDisplay = new Text2D();
		textDisplay.setPosition(new Vector3f(iX, iY, iZ));
	}
	
	public void setTextOrientation(float iAngle) {
		if (textDisplay == null) textDisplay = new Text2D();
		textDisplay.trans.setRotation(new Vector3f(0,0,iAngle));
	}
	
	public void setText(String text) {
		if (textDisplay == null) textDisplay = new Text2D();
		textDisplay.text = text;
	}
	
	public void setTextVisible(boolean vis) {
		if (textDisplay == null) textDisplay = new Text2D();
		textDisplay.appAtrb.rendAtrb.setVisible(vis);
	}
	
	public void setTextTransparency(float iA) {
		if (textDisplay == null) textDisplay = new Text2D();
		textDisplay.appAtrb.transAtrb.setTransparency(iA);
	}
	
	public void setTextSize(float iSize) {
		if (textDisplay == null) textDisplay = new Text2D();
	    textDisplay.setScale(iSize);
	}
	
	public void setTextAlignment(int al) {
		if (textDisplay == null) textDisplay = new Text2D();
		textDisplay.setTextAlignment(al);
	}
	
	public void setTextColor(float r, float g, float b) {
		if (textDisplay == null) textDisplay = new Text2D();
		textDisplay.setColor(r, g, b);
	}

	public void draw(OpenGLWrapper gwl) {
		gwl.gl.glPushMatrix();
			outerCircle.draw(gwl);
		gwl.gl.glPopMatrix();
		gwl.gl.glPushMatrix();
			innerCircle.draw(gwl);
		gwl.gl.glPopMatrix();
	}
	
	public void drawText(OpenGLWrapper gwl) {
		if (textDisplay == null)
			return;
		if (textDisplay.text.equalsIgnoreCase(""))
			return;
		gwl.gl.glPushMatrix();
			textDisplay.draw(gwl);
		gwl.gl.glPopMatrix();
	}
	
}
