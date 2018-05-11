package org.brainstorm.connect;


public class BstAppearance {

	public BstColoringAttributes colorAtrb;
	public BstTransparencyAttributes transAtrb;
	public BstRenderingAttributes rendAtrb;
	
	public BstAppearance() {
		//colorAtrb = new BSTColoringAttributes();
		//transAtrb = new BSTTransparencyAttributes();
		rendAtrb = new BstRenderingAttributes();
	}
	
	public void setColoringAttributes(BstColoringAttributes color) { colorAtrb = color; }
	public boolean hasColoringAttributes() { return colorAtrb != null; }
	
	public void setTransparencyAttributes(BstTransparencyAttributes trans) { transAtrb = trans; }
	public boolean hasTransparencyAttributes() { return transAtrb != null; }
	
	public void setRenderingAttributes(BstRenderingAttributes rend) { rendAtrb = rend; }
	public boolean hasRenderingAttributes() { return rendAtrb != null; }
	
}
