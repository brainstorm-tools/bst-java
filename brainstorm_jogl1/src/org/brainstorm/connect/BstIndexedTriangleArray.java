package org.brainstorm.connect;

public class BstIndexedTriangleArray extends BstIndexedGeometry {

	public int GL_TRIANGLE_STRIP = 5;
	public int GL_TRIANGLE = 4;
	public int GL_TRIANGLE_FAN = 6;
	
	public int TRIANGLE_DRAWING_METHOD = GL_TRIANGLE;
	
	public BstIndexedTriangleArray(int vertexCount, int indexCount) {
		super(vertexCount, indexCount);
	}
	
	public void buildRenderList(OpenGLWrapper gwl) {
		
	}

	protected void render(OpenGLWrapper gwl) {
		
		boolean useNormal = false;
		if (normals.length == indexCount() / 3)
			useNormal = true;
		int Index = 0;
		
		gwl.gl.glBegin(TRIANGLE_DRAWING_METHOD);
		for (int i = 0; i < indexCount(); i++) {
			if (i % 3 == 0 && useNormal) {
				gwl.gl.glNormal3f(
						normals[Index].x, 
						normals[Index].y,
						normals[Index].z);
				Index += 1;
			}
			gwl.gl.glColor4f(
					vertexColor[indices[i]].x,
					vertexColor[indices[i]].y,
					vertexColor[indices[i]].z,
					vertexColor[indices[i]].w);
			gwl.gl.glVertex3f(
					vertices[indices[i]].x,
					vertices[indices[i]].y,
					vertices[indices[i]].z);
		}
		gwl.gl.glEnd();
		
	}
	
}
