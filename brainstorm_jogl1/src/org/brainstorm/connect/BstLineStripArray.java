package org.brainstorm.connect;

import javax.vecmath.Matrix4f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;


public class BstLineStripArray extends BstGeometry {
	
	float DEFAULT_LINEWIDTH = 0.5f;
	float[] lineWidth;
	boolean renderInQuad = false;
	BstIndexedTriangleArray CylinderModel;
	
	public BstLineStripArray(int vertexCount) {
		super(vertexCount);
		lineWidth = new float[vertexCount];
		for (int i = 0; i < lineWidth.length; ++i) {
			lineWidth[i] = DEFAULT_LINEWIDTH;
		}
		// Default line is opaque
		this.appAtrb.setTransparencyAttributes(new BstTransparencyAttributes(0f));
		this.appAtrb.transAtrb.setBlendingEnabled(false);
	}
	
	public void setLineWidth(float width) {
		for (int i = 0; i < lineWidth.length; ++i) {
			lineWidth[i] = width;
		}
	}
	
	public void setLineWidth(float[] width) {
		for (int i = 0; i < lineWidth.length; ++i) {
			lineWidth[i] = width[i];
		}
	}
	
	public void render(OpenGLWrapper gwl) {
		if (renderInQuad)
			renderLineInQuad(gwl);
		else
			renderLine(gwl);
		//CylinderModel.draw(gwl);
	}
	
	@SuppressWarnings("static-access")
	public void renderLine(OpenGLWrapper gwl) {
		gwl.gl.glLineWidth(lineWidth[0]);
		gwl.gl.glBegin(gwl.gl.GL_LINE_STRIP);
			
			for (int i = 0; i < vertices.length; ++i) {
				gwl.gl.glColor4f(vertexColor[i].x, vertexColor[i].y, vertexColor[i].z, vertexColor[i].w);
				gwl.gl.glVertex3f(vertices[i].x, vertices[i].y, vertices[i].z);
			}
		
		gwl.gl.glEnd();
	}
	
	@SuppressWarnings("static-access")
	public void renderLineInQuad(OpenGLWrapper gwl) {
		Vector3f PtoD = new Vector3f();
		Vector3f direction = new Vector3f();
		Vector3f Zero = new Vector3f(0f, 0f, 0f);
		gwl.gl.glBegin(gwl.gl.GL_QUAD_STRIP);	
			for (int i = 0; i < vertices.length; ++i) {
				if (i == 0)
					direction.sub(vertices[i], Zero);
				else if (i == vertices.length - 1) 
					direction.sub(Zero, vertices[i]);
				else
					direction.sub(vertices[i], vertices[i+1]);
				
				direction.normalize();
				PtoD.set(-direction.y, direction.x, direction.z);
				PtoD.scale(lineWidth[i] * 0.01f);
				gwl.gl.glColor4f(vertexColor[i].x, vertexColor[i].y, vertexColor[i].z, vertexColor[i].w);
				gwl.gl.glVertex3f(vertices[i].x - PtoD.x, vertices[i].y - PtoD.y, vertices[i].z);
				gwl.gl.glVertex3f(vertices[i].x + PtoD.x, vertices[i].y + PtoD.y, vertices[i].z);
			}
		gwl.gl.glEnd();
	}
	
	public void buildCylinder() {
		float r = lineWidth[0];
		int steps = 10;
		
		Vector3f[] circle = new Vector3f[steps];
		float angle;
		for (int i = 0; i < steps; i++) {
			angle = 2 * 3.14159265f * (float)i / (float)steps;
			circle[i] = new Vector3f(r * (float)Math.cos(angle), r * (float)Math.sin(angle), 0f);
		}
		
		int segments = vertices.length;
		Vector3f forward = new Vector3f();
		Vector3f PtoD = new Vector3f();
		Vector3f Zero = new Vector3f(0f, 0f, 0f);
		Point3f[] coordinates = new Point3f[segments * steps];
		
		for (int i = 0; i < segments; i++)
		{
			if (i == 0)
				forward.sub(vertices[i], Zero);
			else if (i == vertices.length - 1)
				forward.sub(Zero, vertices[i]);
			else
				forward.sub(vertices[i], vertices[i+1]);
			
			forward.normalize();
			PtoD.set(-forward.y, forward.x, forward.z);
			PtoD.scale(lineWidth[i] * 0.01f);
			
			r = lineWidth[i] * 0.01f;
			for (int y = 0; y < steps; y++) {
				angle = 2 * 3.14159265f * (float)y / (float)steps;
				coordinates[i*steps+y] = new Point3f(vertices[i].x + r * (float)Math.cos(angle), 
													 vertices[i].y + r * (float)Math.sin(angle),
													 vertices[i].z);
			}
		}

		int a, b, c, d;
		int iIndex = 0;
		int[] tubeStripIndex = new int[(segments)*(steps)*6+segments*6];
		for (int i = 0; i < segments-1; i++)
		{
			for (int k = 0; k < steps-1; k++)
			{
				a = i*steps+k; b = a+1; c = a+steps; d = b+steps;
				tubeStripIndex[iIndex++] = a;
				tubeStripIndex[iIndex++] = c;
				tubeStripIndex[iIndex++] = b;
				tubeStripIndex[iIndex++] = b;
				tubeStripIndex[iIndex++] = c;
				tubeStripIndex[iIndex++] = d;
			}
			a = (i+1)*steps-1; b = a+1-steps; c = a+steps; d = b+steps;
			tubeStripIndex[iIndex++] = a;
			tubeStripIndex[iIndex++] = c;
			tubeStripIndex[iIndex++] = b;
			tubeStripIndex[iIndex++] = b;
			tubeStripIndex[iIndex++] = c;
			tubeStripIndex[iIndex++] = d;
		}
		
		CylinderModel = new BstIndexedTriangleArray(coordinates.length, tubeStripIndex.length);
		CylinderModel.setCoordinates(coordinates);
		CylinderModel.setIndices(tubeStripIndex);
	}
	
}
