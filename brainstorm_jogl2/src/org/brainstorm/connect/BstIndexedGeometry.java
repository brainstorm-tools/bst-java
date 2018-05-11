package org.brainstorm.connect;

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

public abstract class BstIndexedGeometry extends BstGeometry {

	public int[] indices;
	public Vector3f[] normals;
	
	public BstIndexedGeometry(int vertexCount, int indexCount) {
		super(vertexCount);
		
		// Allocate structure for index
		indices = new int[indexCount];
		normals = new Vector3f[0];
	}
	
	public void setIndices(int[] ind) {
		for (int i = 0; i < indices.length; ++i) {
			indices[i] = ind[i];
		}
	}
	
	public void setNormals(float[] nor) {
		int ind = 0;
		normals = new Vector3f[nor.length / 3];
		for (int i = 0; i < nor.length / 3; ++i) {
			normals[i] = new Vector3f(nor[ind], nor[ind+1], nor[ind+2]);
			ind += 3;
		}
	}
	
	public void computeNormal() {
		int ind = 0;
		normals = new Vector3f[indexCount() / 3];
		for (int i = 0; i < indexCount() / 3; ++i) {
			Vector3f v1 = new Vector3f(vertices[indices[ind+2]]);
            v1.sub(vertices[indices[ind]]);
            Vector3f v2 = new Vector3f(vertices[indices[ind+1]]);
            v2.sub(vertices[indices[ind]]);
            v1.cross(v1, v2);
			normals[i] = new Vector3f(v1.x, v1.y, v1.z);
			normals[i].normalize();
			ind += 3;
		}
	}
	
	public int indexCount() {
		return indices.length;
	}
	
}
