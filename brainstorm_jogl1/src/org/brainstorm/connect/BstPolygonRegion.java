package org.brainstorm.connect;

import javax.vecmath.Color3f;

public class BstPolygonRegion implements BstDrawable {

	public BstIndexedTriangleArray ita;

	public int FillingPolygonMode = OpenGLWrapper.OGL_FRONT_AND_BACK;
	
	public boolean drawOutline = true;
	public boolean drawFilling = true;
	
	public BstPolygonRegion(int vertexCount, int indexCount) {
		
		ita = new BstIndexedTriangleArray(vertexCount, indexCount);
		
		BstAppearance a = new BstAppearance();
	    
	    BstTransparencyAttributes ta = new BstTransparencyAttributes();
	    BstRenderingAttributes ra = new BstRenderingAttributes();
	    BstColoringAttributes ca = new BstColoringAttributes();
	    
	    ra.setVisible(false);
	    
	    a.setTransparencyAttributes(ta);
	    a.setRenderingAttributes(ra);
	    a.setColoringAttributes(ca);
	    
	    ita.setAppearance(a);
	}
	
	public void setVisible(boolean visible) {
		ita.appAtrb.rendAtrb.setVisible(visible);
	}
	
	public void setDrawOutline(boolean outline) {
		drawOutline = outline;
	}
	
	public void setDrawFilling(boolean filling) {
		drawFilling = filling;
	}

	@SuppressWarnings("static-access")
	public void draw(OpenGLWrapper gwl) {
//		gwl.gl.glPushAttrib(gwl.gl.GL_ALL_ATTRIB_BITS);
//		
//		gwl.gl.glEnable(gwl.gl.GL_POLYGON_OFFSET_FILL);
//		gwl.gl.glPolygonOffset(-2.5f,-2.5f);
//		gwl.gl.glPolygonMode(gwl.gl.GL_FRONT_AND_BACK, gwl.gl.GL_LINE);
//		gwl.gl.glLineWidth(2.0f);
//		ita.setAllVertexColorsTo(new Color3f(0,1,1));
//		ita.draw(gwl);
//		
//		gwl.gl.glPolygonMode(gwl.gl.GL_FRONT_AND_BACK, gwl.gl.GL_FILL);
//		ita.setAllVertexColorsTo(new Color3f(0.7f,0.7f,0.7f));
//		ita.draw(gwl);

//		if (drawFilling) {
//			gwl.gl.glDepthRange(0.1, 1.0);
//			gwl.gl.glPolygonMode(FillingPolygonMode, gwl.gl.GL_FILL);
//			ita.draw(gwl);
//		}
//		if (drawOutline) {
//			// ToDo
//			gwl.gl.glDepthRange(0.0, 0.9);
			ita.draw(gwl);
//		}
//		gwl.gl.glPopAttrib();
		
				
	}
	
}
