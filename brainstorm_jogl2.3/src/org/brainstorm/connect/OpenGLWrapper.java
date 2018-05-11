package org.brainstorm.connect;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.glu.GLU;
import static com.jogamp.opengl.GL2.*;   // JOGL2

public class OpenGLWrapper {
	
	ClassLoader classLoader = OpenGLWrapper.class.getClassLoader();
	
	static int OGL_FRONT_AND_BACK = GL_FRONT_AND_BACK;
	static int OGL_FRONT = GL_FRONT;
	static int OGL_BACK = GL_BACK;
	
	
	public GLAutoDrawable drawable;
	public GL2 gl;    // JOGL2
	public GLU glu = new GLU(); 

	OpenGLWrapper() {
		
	}
	
	OpenGLWrapper(GLAutoDrawable glad) {
		updateFromAutoDrawable(glad);
	}
	
	public void updateFromAutoDrawable(GLAutoDrawable glad) {
		drawable = glad;
		gl = glad.getGL().getGL2(); // JOGL2
	}
	
	// Drawing primitive
	public void glVertex3f(float arg0, float arg1, float arg2) { gl.glVertex3f(arg0, arg1, arg2); }
	public void glColor3f(float arg0, float arg1, float arg2) { gl.glColor3f(arg0, arg1, arg2); }
	public void glColor4f(float arg0, float arg1, float arg2, float arg3) { gl.glColor4f(arg0, arg1, arg2, arg3); }
	
	// Display list
	public int glGenList(int arg0) { return gl.glGenLists(arg0); }
	
}
