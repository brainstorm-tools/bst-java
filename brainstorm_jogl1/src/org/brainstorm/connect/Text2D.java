package org.brainstorm.connect;

import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Rectangle2D;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

import com.sun.opengl.util.j2d.TextRenderer;   // JOGL


public class Text2D implements BstDrawable {
	
	public TextRenderer tr = new TextRenderer(new Font("SansSerif", Font.PLAIN, 30));
	
	// 1:Left, 2:Center, 3:Right
	public int alignment;
	public Vector3f alignOffset = new Vector3f();
	
	public Vector3f refPosition = new Vector3f(0,0,0);
	
	public String text;
	public BstTransform3D trans = new BstTransform3D();
	public BstAppearance appAtrb = new BstAppearance();;
	
	public Text2D() {
		text = new String("");
		trans.setTranslation(0, 0, 0);
		//trans.setScale(new Vector3f(0.025f, 0.025f, 0.025f));
		trans.setScale(new Vector3f(1/30f, 1/30f, 1/30f));
	
		BstColoringAttributes ca = new BstColoringAttributes();
		ca.setColor(new Color3f(Color.WHITE));
		
		BstTransparencyAttributes ta = new BstTransparencyAttributes();
		ta.setTransparency(0.0f);
		
		appAtrb.setColoringAttributes(ca);
		appAtrb.setTransparencyAttributes(ta);
		
		alignment = 1;
	}
	
	public void setPosition(Vector3f pos) {
		trans.setTranslation(pos);
		updateDisplayTranslation();
	}
	
	public void setTextAlignment(int align) {
		alignment = align;
		updateDisplayTranslation();
	}
	
	public void setScale(float scale) {
		trans.setScale(new Vector3f(scale, scale, scale));
		updateDisplayTranslation();
	}
	
	public void setColor(float r, float g, float b) {
		appAtrb.colorAtrb.setColor3f(r, g, b);
	}
	
	public void updateDisplayTranslation() {
		Rectangle2D bounds = tr.getBounds(text);
        alignOffset.y = -(float) bounds.getHeight() / 2;
        if (alignment == 2) {
        	alignOffset.x = (float)-bounds.getWidth() / 2;
        } else if (alignment == 3) {
        	alignOffset.x = (float)-bounds.getWidth();
        }
	}

	public void draw(OpenGLWrapper gwl) {
		
		boolean isVisible = true;
		if (appAtrb.hasRenderingAttributes()) {
			isVisible = appAtrb.rendAtrb.getVisible();
		}
		
		if (isVisible)
		{
			// Apply 3D transformation
			trans.applyTransform(gwl);
			
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
			
			tr.begin3DRendering();
			tr.setColor(color.x, color.y, color.z, 1 - transparency);
			tr.draw3D(text, alignOffset.x, alignOffset.y, 0, 1);
		    tr.end3DRendering();
		}
	}
	
}
