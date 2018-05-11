package org.brainstorm.connect;

import javax.vecmath.Matrix4d;
import javax.vecmath.Matrix4f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4d;


public class BstCamera {

	Vector3f m_xAxis;
	Vector3f m_yAxis;
	Vector3f m_zAxis;
	
	float m_fovx;
	float m_aspectRation;
	float m_znear;
	float m_zfar;
	float m_zoom;
	
	Vector3f m_eye;
	Vector3f m_target;
	Vector3f m_targetYAxis;
	Vector3f m_viewDir;
	
	Matrix4f m_viewMatrix;
	Matrix4f m_projMatrix;
	Matrix4f m_viewProjMatrix;
	
	public BstCamera() {
		m_xAxis = new Vector3f(1f, 0f, 0f);
		m_yAxis = new Vector3f(0f, 1f, 0f);
		m_zAxis = new Vector3f(0f, 0f, 1f);
		
		m_eye = new Vector3f();
		m_target = new Vector3f();
		m_targetYAxis = new Vector3f();
		m_viewDir = new Vector3f();
		
		m_viewMatrix = new Matrix4f();
		m_projMatrix = new Matrix4f();
		m_viewProjMatrix = new Matrix4f();
		
		m_viewMatrix.setIdentity();
		m_projMatrix.setIdentity();
		m_viewProjMatrix.setIdentity();
		
		m_zoom = 1;
	}
	
	public void lookAt(Vector3f target) {
		lookAt(m_eye, target, m_yAxis);
	}
	
	public void lookAt(Vector3f eye, Vector3f target, Vector3f up) {
		m_eye = eye;
		m_target = target;
		m_targetYAxis = up;
		
		m_zAxis.sub(eye, target);
		m_zAxis.normalize();
		
		// -m_zAxis
		m_viewDir.set(m_zAxis);
		m_viewDir.negate();
		
		m_xAxis.cross(up, m_zAxis);
		m_xAxis.normalize();
		
		m_yAxis.cross(m_zAxis, m_xAxis);
		m_yAxis.normalize();
		
		m_viewMatrix.setRow(0, m_xAxis.x, m_xAxis.y, m_xAxis.z, -m_xAxis.dot(eye));
		m_viewMatrix.setRow(1, m_yAxis.x, m_yAxis.y, m_yAxis.z, -m_yAxis.dot(eye));
		m_viewMatrix.setRow(2, m_zAxis.x, m_zAxis.y, m_zAxis.z, -m_zAxis.dot(eye));
		m_viewMatrix.setRow(3, 0, 0, 0, 1);
	}
	
	public void perspective(float fovx, float aspect, float znear, float zfar) {
		float e = 1.0f / (float)Math.tan(Math.toRadians(fovx) / 2.0);
		float aspectInv = 1.0f / aspect;
		float fovy = 2.0f * (float)Math.atan(aspectInv / e);
		float xScale = 1.0f / (float)Math.tan(0.5f * fovy);
		float yScale = xScale / aspectInv;
		
		m_projMatrix.setRow(0, xScale, 0, 0, 0);
		m_projMatrix.setRow(1, 0, yScale, 0, 0);
		m_projMatrix.setRow(2, 0, 0, (zfar - znear) / (znear - zfar), -1);
		m_projMatrix.setRow(3, 0, 0, (zfar * znear * 2) / (znear - zfar), 0);
		
		m_viewProjMatrix.mul(m_viewMatrix, m_projMatrix);
		
		m_fovx = fovx;
		m_aspectRation = aspect;
		m_znear = znear;
		m_zfar = zfar;
	}
	
	public boolean testIntersect(Vector3f origin, Vector3f direction, Vector3f center, float radius) {
		Vector3f diff = new Vector3f();
		diff.sub(origin,center);
		
	    float a0 = diff.dot(diff) - radius*radius;
	    if (a0 <= 0f) {
	        // P is inside the sphere
	        return true;
	    }
	    // else: P is outside the sphere

	    float a1 = direction.dot(diff);
	    if (a1 >= 0f) {
	        return false;
	    }
	    // Quadratic has a real root if discriminant is nonnegative.
	    return a1*a1 >= a0;
	}
	
	public boolean testIntersectTriangle(Vector3f origin,
										 Vector3f direction, 
										 Point3f p1, 
										 Point3f p2,
										 Point3f p3) {
		float ZERO_TOLERANCE = 0.00001f;
		// Compute the offset origin, edges, and normal
		Vector3f diff = new Vector3f();
		Vector3f edge1 = new Vector3f();
		Vector3f edge2 = new Vector3f();
		Vector3f normal = new Vector3f();
		diff.sub(origin, p1);
		edge1.sub(p2, p1);
		edge2.sub(p3, p1);
		normal.cross(edge1, edge2);
	    
	    // Solve Q + t*D = b1*E1 + b2*E2 (Q = kDiff, D = ray direction,
	    // E1 = kEdge1, E2 = kEdge2, N = Cross(E1,E2)) by
	    //   |Dot(D,N)|*b1 = sign(Dot(D,N))*Dot(D,Cross(Q,E2))
	    //   |Dot(D,N)|*b2 = sign(Dot(D,N))*Dot(D,Cross(E1,Q))
	    //   |Dot(D,N)|*t = -sign(Dot(D,N))*Dot(Q,N)
	    float fDdN = direction.dot(normal);
	    float fSign;
	    if (fDdN > ZERO_TOLERANCE) {
	        fSign = (float)1.0;
	    }
	    else if (fDdN < -ZERO_TOLERANCE) {
	        fSign = (float)-1.0;
	        fDdN = -fDdN;
	    }
	    else {
	        // Ray and triangle are parallel, call it a "no intersection"
	        // even if the ray does intersect.
	        return false;
	    }

	    Vector3f cross = new Vector3f();
	    cross.cross(diff, edge2);
	    float fDdQxE2 = fSign * direction.dot(cross);
	    if (fDdQxE2 >= (float)0.0) {
	    	Vector3f cross2 = new Vector3f();
	    	cross2.cross(edge1, diff);
	        float fDdE1xQ = fSign * direction.dot(cross2);
	        if (fDdE1xQ >= (float)0.0) {
	            if (fDdQxE2 + fDdE1xQ <= fDdN) {
	                // Line intersects triangle, check if ray does
	                float fQdN = -fSign*diff.dot(normal);
	                if (fQdN >= (float)0.0) {
	                    // Ray intersects triangle
	                    return true;
	                } // else: t < 0, no intersection
	            } // else: b1+b2 > 1, no intersection
	        } // else: b2 < 0, no intersection
	    } // else: b1 < 0, no intersection
	    return false;
	}
	
	/* Not working properly */
	public boolean gluUnProject(float winX, float winY, float winZ, double[] modelview, double[] projection, int[] viewport, Vector3f objPos)
	{
		Matrix4d viewMat = new Matrix4d(modelview);
		Matrix4d projMat = new Matrix4d(projection);
		
		Matrix4d viewProjInv = new Matrix4d();
		viewProjInv.mul(viewMat, projMat);
		viewProjInv.invert();
		
		//Vector4d inVector = new Vector4d();
		Vector4d inVector = new Vector4d(
				(float)(winX) * 2 / (float)(viewport[2]) - 1,
				1 - (float)(winY) * 2 / (float)(viewport[3]),
				winZ,
				1);
		//inVector.x = (winX - viewportCenterX) * viewportRadius / viewportRadiusInPixels;
		//inVector.y = (viewportCenterY - winY) * viewportRadius / viewportRadiusInPixels;
		//inVector.z = 2.0f * winZ - 1.0f;//nearPlane;
		//inVector.x = (winX - 0) / viewport[2] * 2.0f - 1.0f;
		//inVector.y = (winY - 0) / viewport[3] * 2.0f - 1.0f;
		//inVector.z = winZ;
		//inVector.w = 1.0f;
		
		//Matrix4d vec = new Matrix4d();
		//vec.setColumn(0, inVector);
		viewProjInv.transform(inVector);
		
		//viewProjInv.getColumn(0, inVector);
		//viewProjInv.transform(inVector);
		
		if (inVector.w == 0.0) {
			return false;
		}
		
		// Invert to normalize x, y, and z values.
		inVector.w = 1.0f / inVector.w;
		
		objPos.x = (float)(inVector.x * inVector.w);
		objPos.y = (float)(inVector.y * inVector.w);
		objPos.z = (float)(inVector.z * inVector.w);
		
		return true;
	}
	
		
}
