package org.brainstorm.connect;


import javax.vecmath.Color3f;
import javax.vecmath.Color4f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;


public class Link implements BstDrawable {
	
	public BstLineStripArray lineStrip;
	public boolean lineIsComputed;
	public Vector3f measureOffset;
	
	public Link(Point3f[] frames, int vertexCount) {
		lineStrip = buildLineStripArray(frames, vertexCount);
		//lineStrip.buildCylinder();
		//lineStrip.CylinderModel.computeNormal();
		
	    BstAppearance a = new BstAppearance();
	    
	    BstTransparencyAttributes ta = new BstTransparencyAttributes();
	    BstRenderingAttributes ra = new BstRenderingAttributes();
	    BstColoringAttributes ca = new BstColoringAttributes();
	    
	    ra.setVisible(false);
	    
	    a.setTransparencyAttributes(ta);
	    a.setRenderingAttributes(ra);
	    a.setColoringAttributes(ca);
	    
	    lineStrip.setAppearance(a);
	    
	    measureOffset = new Vector3f(0,0,0);
	}
	
	public BstLineStripArray buildLineStripArray(Point3f[] frames, int vertexCount)
	{
		BstLineStripArray ls = new BstLineStripArray(vertexCount);

	    ls.setCoordinates(frames);	  
	    
	    return ls;
	}
	
	public void setFrameColor(int index, Color4f col) {
		lineStrip.vertexColor[index].set(col);
	}
	
	public void setFramePosition(int index, Point3f pos) {
		lineStrip.vertices[index].set(pos);
	}
	
	public void setAllFrameToColor(Color4f col) {
		for (int i = 0; i < lineStrip.vertices.length; ++i) {
			lineStrip.vertexColor[i].set(col.x, col.y, col.z, col.w);
		}
	}
	
	public void setAllFrameToGradient(Color3f colStart, Color3f colEnd) {
		float startFactor = 0, endFactor = 0;
		for (int i = 0; i < lineStrip.vertexColor.length; ++i) {
			startFactor = (float)(lineStrip.vertexColor.length - i) / (float)lineStrip.vertexColor.length;
			endFactor = (float)i / (float)lineStrip.vertexColor.length;
			lineStrip.vertexColor[i].set(startFactor * colStart.x + endFactor * colEnd.x,
							   	  startFactor * colStart.y + endFactor * colEnd.y,
								  startFactor * colStart.z + endFactor * colEnd.z,
								  lineStrip.vertexColor[i].w);
		}
	}
	
	public void setAllFrameToOpacity(float iA) {
		for (int i = 0; i < lineStrip.vertexColor.length; ++i) {
			lineStrip.vertexColor[i].w = iA;
		}
	}
	
	public void setLinkWidth(float width) {
		lineStrip.setLineWidth(width);
	}
	
	public void setVisible(boolean visible) {
		BstRenderingAttributes ra = lineStrip.appAtrb.rendAtrb;
		ra.setVisible(visible);
	}
	
	public void enableBlending(boolean enable) {
		lineStrip.appAtrb.transAtrb.setBlendingEnabled(enable);
	}
	
	public void setMeasureOffset(float arg0, float arg1, float arg2){
		measureOffset.set(arg0, arg1, arg2);
		updateLineWithOffset();
	}
	
	public void updateLineWithOffset() {
		Point3f center = new Point3f(0,0,0);
		float maxDist = center.distance(lineStrip.vertices[0]);
		float dist = 0;
		for (int i = 1; i < lineStrip.vertices.length - 1; ++i) {
			dist = center.distance(lineStrip.vertices[i]) / maxDist;
			lineStrip.vertices[i].z = (1 - dist) * measureOffset.z;
		}
	}

	public void draw(OpenGLWrapper gwl) {
		//gwl.gl.glTranslatef(measureOffset.x, measureOffset.y, measureOffset.z);
		lineStrip.draw(gwl);
	}
	
}
