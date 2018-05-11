package org.brainstorm.connect;

import javax.vecmath.Vector3f;


public class BstTransform3D {

	public Vector3f rotation;
	public Vector3f translation;
	public Vector3f scale;
	
	public BstTransform3D() {
		rotation = new Vector3f();
		translation = new Vector3f();
		scale = new Vector3f(1f, 1f, 1f);
	}
	
	public void applyTransform(OpenGLWrapper gwl) {
		gwl.gl.glTranslatef(translation.x, translation.y, translation.z);
		
		gwl.gl.glRotatef(rotation.x, 1, 0, 0);
		gwl.gl.glRotatef(rotation.y, 0, 1, 0);
		gwl.gl.glRotatef(rotation.z, 0, 0, 1);
		
		gwl.gl.glScalef(scale.x, scale.y, scale.z);
	}
	
	public void setTranslation(Vector3f arg0) { translation.set(arg0); }
	public void setTranslation(float arg0, float arg1, float arg2) { translation.set(arg0, arg1, arg2); }
	
	public void setRotation(Vector3f rot) { rotation.set(rot); }
	
	public void setScale(Vector3f factor) { scale.set(factor); }
	
}
