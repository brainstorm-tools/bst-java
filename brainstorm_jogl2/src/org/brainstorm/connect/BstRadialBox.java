package org.brainstorm.connect;

import javax.vecmath.Color4f;
import javax.vecmath.Point3f;


public class BstRadialBox extends BstIndexedTriangleArray {
	
	// 10 vertex for each arc
	public static int lineDetail = 10;
	
	public BstLineStripArray lsa;
	
	public BstRadialBox(int vertexCount, int indexCount) {
		super(vertexCount, indexCount);
		
		TRIANGLE_DRAWING_METHOD = GL_TRIANGLE_STRIP;
		
		setAllVertexColorsTo(new Color4f(0.4f, 0.4f, 0.4f, 1.0f));
	}
		
	public BstIndexedTriangleArray constructPolygon(float startAngle, float endAngle, float innerRadius, float boxWidth) {
		int vertexCount = 2 * (lineDetail + 1);
		int indexCount = vertexCount;
		BstIndexedTriangleArray rITA = new BstIndexedTriangleArray(vertexCount, indexCount);
    	Point3f[] vertices = new Point3f[vertexCount];
    	int[] indexes = new int[indexCount];
    	
    	int index = 0;
    	float upper = innerRadius + boxWidth;
		float factor = (endAngle - startAngle) / lineDetail;
		for (int i = 0; i <= lineDetail; ++i) {
			float sa = startAngle + factor * i;
			vertices[index] = new Point3f(innerRadius * (float)Math.cos(sa), innerRadius * (float)Math.sin(sa), 0.0f);
			index++;
		}
		for (int i = lineDetail; i >= 0; --i) {
			float sa = startAngle + factor * i;
			vertices[index] = new Point3f(upper * (float)Math.cos(sa), upper * (float)Math.sin(sa), 0.0f);
			index++;
		}
		
		index = 0;
		for (int i = 0; i <= lineDetail; ++i) {
			indexes[index++] = i;
			indexes[index++] = (2 * (lineDetail + 1)) - i - 1;
		}
		
		rITA.TRIANGLE_DRAWING_METHOD = rITA.GL_TRIANGLE_STRIP;
		rITA.setCoordinates(vertices);
		rITA.setIndices(indexes);
		rITA.setAllVertexColorsTo(new Color4f(1,1,1,0.5f));
    	
    	return rITA;
	}
	
	public BstLineStripArray constructLine(float startAngle, float endAngle, float innerRadius, float boxWidth) {
		int vertexCount = 2 * (lineDetail + 1) + 1;
		BstLineStripArray rLsa = new BstLineStripArray(vertexCount);
    	Point3f[] vertices = new Point3f[vertexCount];
    	
		int index = 0;
		Point3f temp;
		float upper = innerRadius + boxWidth;
		float factor = (endAngle - startAngle) / lineDetail;
		for (int i = 0; i <= lineDetail; ++i) {
			float sa = startAngle + factor * i;
			temp = new Point3f(innerRadius * (float)Math.cos(sa), innerRadius * (float)Math.sin(sa), 0.0f);
			vertices[index] = temp;
			index++;
		}
		for (int i = lineDetail; i >= 0; --i) {
			float sa = startAngle + factor * i;
			temp = new Point3f(upper * (float)Math.cos(sa), upper * (float)Math.sin(sa), 0.0f);
			vertices[index] = temp;
			index++;
		}
		vertices[index] = vertices[0];
		rLsa.setCoordinates(vertices);
		
		return rLsa;
	}
	
}
