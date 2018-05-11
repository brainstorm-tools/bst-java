package org.brainstorm.connect;

public class BstTransparencyAttributes {

	static public int GL_SRC_COLOR = 768;
	static public int GL_ONE_MINUS_SRC_COLOR = 769;
	static public int GL_SRC_ALPHA = 770;
	static public int GL_ONE_MINUS_SRC_ALPHA = 771;
	static public int GL_ONE_MINUS_DST_COLOR = 775;
	static public int GL_ONE = 1;
	static public int GL_ZERO = 0;

	public int blendingFunctionSource = GL_SRC_ALPHA;
	public int blendingFunctionDestination = GL_ONE;
	
	public float transparency;
	public boolean blendingEnabled;
	
	public BstTransparencyAttributes() {
		this(0f);
	}
	
	public BstTransparencyAttributes(float trans) {
		setTransparency(trans);
		setBlendingSource(BstTransparencyAttributes.GL_SRC_ALPHA);
		setBlendingDestination(BstTransparencyAttributes.GL_ONE_MINUS_SRC_ALPHA);
	}
	
	public void setBlendingEnabled(boolean arg0) {
		blendingEnabled = arg0;
	}
	
	public void setTransparency(float trans) {
		transparency = trans;
		setBlendingEnabled(trans != 0);
	}
	
	public void setBlendingSource(int source) {
		blendingFunctionSource = source;
	}
	
	public void setBlendingDestination(int destination) {
		blendingFunctionDestination = destination;
	}
	
	public void setBlendingFunction(int source, int destination) {
		setBlendingSource(source);
		setBlendingDestination(destination);
	}
	
	@SuppressWarnings("static-access")
	public void applyAttributes(OpenGLWrapper gwl) {
		// If blending mode activated
		if (blendingEnabled) {
			gwl.gl.glEnable(gwl.gl.GL_BLEND);
			//gwl.gl.glEnable(gwl.gl.GL_DEPTH_TEST);
			gwl.gl.glBlendFunc(blendingFunctionSource, blendingFunctionDestination);
		}
		else {
			disableAttributes(gwl);
		}
	}
	
	@SuppressWarnings("static-access")
	public void disableAttributes(OpenGLWrapper gwl) {
		gwl.gl.glDisable(gwl.gl.GL_BLEND);
		//gwl.gl.glEnable(gwl.gl.GL_DEPTH_TEST);
	}
	
}
