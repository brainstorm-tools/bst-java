package org.brainstorm.connect;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;
import static javax.media.opengl.GL2.*;   // JOGL2
import javax.media.opengl.*;

public class BrainMeasureVisualiser implements Runnable {

	Thread thread = null;
	boolean threadSuspended;
	
	GraphicsFramework gf = null;
	OpenGLWrapper gwl = new OpenGLWrapper();

	BstCamera cam = new BstCamera();
	int[] m_Viewport = new int[4];
	double[] m_ModelMatrix = new double[16];
	double[] m_ProjectionMatrix = new double[16];
	
	public Vector<Node> Nodes = new Vector<Node>();
	public Vector<Link> HierarchyLinks = new Vector<Link>();
	public Vector<Link> MeasureLinks = new Vector<Link>();
	
	public boolean NodesAreVisible = true;
	public boolean HierarchyIsVisible = false;
	public boolean MeasureIsVisible = true;
	
	public int WindowWidth = 0;
	public int WindowHeight = 0;
	
	// Fun stuff
	public Vector<BstRadialBox> RadialBoxes = new Vector<BstRadialBox>();
	public Vector<BstPolygonRegion> Polygons = new Vector<BstPolygonRegion>();
	public Vector<BstLineStripArray> RegionOutline = new Vector<BstLineStripArray>();
	
	public float LineDetailMultiplier = 1.0f;
	
	// Rendering Options
	public HashMap<Integer,Boolean> RenderingOptions = new HashMap<Integer,Boolean>();
	public Vector4f ClearColor = new Vector4f(0f, 0f, 0f, 0f);
	
	public BrainMeasureVisualiser(GraphicsFramework parent) {
		gf = parent;
		lookAt(0,0,0,0,0,0,0,1,0);
	}
	
	public void lookAt(float eX, float eY, float eZ, float tX, float tY, float tZ, float uX, float uY, float uZ) {
		cam.lookAt(new Vector3f(eX, eY, eZ), new Vector3f(tX, tY, tZ), new Vector3f(uX, uY, uZ));
		cam.m_viewMatrix.transpose();
	}
	
	public void setOpenGLHandle(GLAutoDrawable graphic) {
		gwl.updateFromAutoDrawable(graphic);
	}
	
	public void setupDrawingContext() {

	}
	
	@SuppressWarnings("static-access")
	public void resize(int w, int h) {
		WindowWidth = w;
		WindowHeight = h;
		
		gwl.gl.glViewport(0, 0, WindowWidth, WindowHeight);
		gwl.gl.glGetIntegerv(gwl.gl.GL_VIEWPORT, m_Viewport, 0);
	    
		gf.repaint();
	}
	
	@SuppressWarnings("static-access")
	public void draw() {
		// Clear buffer
		gwl.gl.glClearColor(ClearColor.x, ClearColor.y, ClearColor.z, ClearColor.w);
		gwl.gl.glClear(gwl.gl.GL_COLOR_BUFFER_BIT | gwl.gl.GL_DEPTH_BUFFER_BIT);
		
		// Setup constant rendering options
		gwl.gl.glShadeModel(gwl.gl.GL_SMOOTH);
		gwl.gl.glCullFace(gwl.gl.GL_BACK);

		// Enable / Disable options
		Iterator it = RenderingOptions.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
			if ((Boolean)pairs.getValue() == true)
				gwl.gl.glEnable((Integer)pairs.getKey());
			else
				gwl.gl.glDisable((Integer)pairs.getKey());
	    }
	    
		float LightAmbient[] = { 0.01f, 0.01f, 0.01f, 1.0f };
		float LightDiffuse[] = { 1.0f, 1.0f, 1.0f, 1.0f };
		float LightPosition[] = {0.0f, -6.0f, 0.0f, 1.0f };
		gwl.gl.glEnable(GL_LIGHT0);
		gwl.gl.glLightfv(GL_LIGHT0, GL_AMBIENT, LightAmbient, 0);
		gwl.gl.glLightfv(GL_LIGHT0, GL_DIFFUSE, LightDiffuse, 0);
		gwl.gl.glLightfv(GL_LIGHT0, GL_POSITION, LightPosition, 0);
		gwl.gl.glEnable(GL_LIGHT1);
		LightPosition = new float[]{0.0f, 6.0f, 0.0f, 1.0f };
		gwl.gl.glLightfv(GL_LIGHT1, GL_AMBIENT, LightAmbient, 0);
		gwl.gl.glLightfv(GL_LIGHT1, GL_DIFFUSE, LightDiffuse, 0);
		gwl.gl.glLightfv(GL_LIGHT1, GL_POSITION, LightPosition, 0);
		
		gwl.gl.glMatrixMode(GL_PROJECTION);
		gwl.gl.glLoadIdentity();
		
		float left = -1 * cam.m_zoom;
		float right = 1 * cam.m_zoom;
		float bottom = -1 * cam.m_zoom;
		float top = 1 * cam.m_zoom;
		
		float aspect = (float)WindowWidth/(float)WindowHeight;
		if (aspect < 1.0) {
		   bottom /= aspect;
		   top /= aspect;
		}
		else {
		   left *= aspect;
		   right *= aspect;
		}
		
		gwl.gl.glMatrixMode(GL_PROJECTION);
		gwl.gl.glOrtho(left, right, bottom, top, 0, 1000);
		gwl.gl.glGetDoublev(GL_PROJECTION_MATRIX, m_ProjectionMatrix, 0);
		
		gwl.gl.glMatrixMode(GL_MODELVIEW);
		gwl.gl.glLoadIdentity();
		gwl.glu.gluLookAt(cam.m_eye.x, cam.m_eye.y, cam.m_eye.z, cam.m_target.x, cam.m_target.y, cam.m_target.z, cam.m_targetYAxis.x, cam.m_targetYAxis.y, cam.m_targetYAxis.z);
		
		
		//float[] t = new float[]{cam.m_viewMatrix.m00, cam.m_viewMatrix.m01, cam.m_viewMatrix.m02, cam.m_viewMatrix.m03,
		//						cam.m_viewMatrix.m10, cam.m_viewMatrix.m11, cam.m_viewMatrix.m12, cam.m_viewMatrix.m13,
		//						cam.m_viewMatrix.m20, cam.m_viewMatrix.m21, cam.m_viewMatrix.m22, cam.m_viewMatrix.m23,
		//						cam.m_viewMatrix.m30, cam.m_viewMatrix.m31, cam.m_viewMatrix.m32, cam.m_viewMatrix.m33};
		//gwl.gl.glLoadMatrixf(t, 0);
		gwl.gl.glGetDoublev(GL_MODELVIEW_MATRIX, m_ModelMatrix, 0);
    	
		// Keep original for each group
		gwl.gl.glPushMatrix();
		
		int i;
		
		if (NodesAreVisible) {
			for (i = 0; i < Nodes.size(); ++i) {
				gwl.gl.glPushMatrix();
				Nodes.get(i).draw(gwl);
				gwl.gl.glPopMatrix();
			}
			// Draw Text separately
			for (i = 0; i < Nodes.size(); ++i) {
				gwl.gl.glPushMatrix();
				Nodes.get(i).drawText(gwl);
				gwl.gl.glPopMatrix();
			}
		}
		
		if (HierarchyIsVisible) {
			for (i = 0; i < HierarchyLinks.size(); ++i) {
				gwl.gl.glPushMatrix();	
				HierarchyLinks.get(i).draw(gwl);
				gwl.gl.glPopMatrix();
			}
		}
		
		if (MeasureIsVisible) {
			for (i = 0; i < MeasureLinks.size(); ++i) {
				gwl.gl.glPushMatrix();
				MeasureLinks.get(i).draw(gwl);
				gwl.gl.glPopMatrix();
			}
		}
				
		for (i = 0; i < RadialBoxes.size(); ++i) {
			gwl.gl.glPushMatrix();
			RadialBoxes.get(i).draw(gwl);
			gwl.gl.glPopMatrix();
		}
		
		for (i = 0; i < Polygons.size(); ++i) {
			gwl.gl.glPushMatrix();
			Polygons.get(i).draw(gwl);
			gwl.gl.glPopMatrix();
		}
		
		for (i = 0; i < RegionOutline.size(); ++i) {
			gwl.gl.glPushMatrix();
			RegionOutline.get(i).draw(gwl);
			gwl.gl.glPopMatrix();
		}

		gwl.gl.glPopMatrix();
	}
	
	public void startBackgroundWork() {
		if (thread == null) {
			thread = new Thread(this);
			threadSuspended = false;
			thread.start();
		}
		else {
			if (threadSuspended) {
				threadSuspended = false;
				synchronized(this) {
					notify();
				}
			}
		}
	}

	public void stopBackgroundWork() {
		threadSuspended = true;
	}
	
	public void run() {
		try {
			while (true) {
				// Now the thread checks to see if it should suspend itself
				if (threadSuspended) {
					synchronized(this) {
						while (threadSuspended) {
							wait();
						}
					}
				}
				
				//for (int i = 0; i < MeasureLinks.size(); ++i) {
					//if (!MeasureLinks.get(i).lineIsComputed) {
						//updateLink(i);
						//MeasureLinks.get(i).lineIsComputed = true;
						//gf.requestRedraw();
						
						//break;
						
					//}
				//}
				// Should it be our object or static thread ?
				//Thread.sleep(50);
				
			}
		}
		catch (InterruptedException e) {
			// Error msg
		}
	}
	
	public void refineLink(int index, int[] keyFramesIndex) {
		Link link = MeasureLinks.get(index);
		Point3f[] keyFrames;
		if (keyFramesIndex.length == 0) {
			keyFrames = new Point3f[link.lineStrip.vertices.length];
			for (int i = 0; i < keyFrames.length; ++i) {
	    		keyFrames[i] = new Point3f(link.lineStrip.vertices[i]);
	    	}
		}
		else {
			keyFrames = new Point3f[keyFramesIndex.length];
	    	for (int i = 0; i < keyFramesIndex.length; ++i) {
	    		keyFrames[i] = new Point3f(Nodes.get(keyFramesIndex[i]).Position());
	    	}
		}
    	
		int vertexCount = link.lineStrip.vertices.length;
		int totalVertexCount = (vertexCount + vertexCount - 1);
		int numberOfSegment = totalVertexCount - 1;
		
		int knotLength = keyFrames.length * 2;
		double[] knot = new double[knotLength];
		for (int i = 0; i < knotLength; ++i) {
			knot[i] = (i < knotLength / 2) ? 0.0 : 1.0;
 		}
		BSpline sCurve = new BSpline(keyFrames, knot, keyFrames.length);
		
		Point3f[] newKeyFrames = new Point3f[totalVertexCount];
		for (int i = 0; i < totalVertexCount; i++) {
			newKeyFrames[i] = sCurve.computeFor((float)(i) / (float)numberOfSegment);
    	}
		
		link = null;
		link = new Link(newKeyFrames, totalVertexCount);
		MeasureLinks.set(index, link);
	}
	
	public void setDetailMutliplerTo(float newMultiplier) {
    	this.LineDetailMultiplier = newMultiplier;
    }
	
	// Global visibility barrier
	public void setNodesIsVisible(boolean visible) { this.NodesAreVisible = visible; }
	public void setMeasureIsVisible(boolean visible) { this.MeasureIsVisible = visible; }
	public void setHierarchyIsVisible(boolean visible) { this.HierarchyIsVisible = visible; }
    
    public void addHierarchyLink(int[] keyFramesIndex) {
    	Point3f[] keyFrames = new Point3f[keyFramesIndex.length];
    	for (int i = 0; i < keyFramesIndex.length; ++i) {
    		keyFrames[i] = new Point3f(Nodes.get(keyFramesIndex[i]).Position());
    	}
    	
	    int VertexCount = (keyFrames.length == 2) ? 3 : keyFrames.length * 3;
	    int SegmentCount = VertexCount - 1;
	    
	    int knotLength = keyFrames.length * 2;
		double[] knot = new double[knotLength];
		for (int i = 0; i < knotLength; ++i) {
			knot[i] = (i < knotLength / 2) ? 0.0 : 1.0;
		}
		BSpline sCurve = new BSpline(keyFrames, knot, keyFrames.length);
		
		Point3f[] keyVertex = new Point3f[VertexCount];
	    
		for (int i = 0; i < VertexCount; i++) {
	    	keyVertex[i] = sCurve.computeFor((float)(i) / (float)SegmentCount);
	    }
	    
	    Link newLink = new Link(keyVertex, VertexCount);
	    newLink.lineIsComputed = false;
	    HierarchyLinks.add(newLink);
	}
    
    public void addConnectivityLink(int[] keyFramesIndex) {
    	Point3f[] keyFrames = new Point3f[keyFramesIndex.length];
    	for (int i = 0; i < keyFramesIndex.length; ++i) {
    		keyFrames[i] = new Point3f(Nodes.get(keyFramesIndex[i]).Position());
    	}
    	
	    int VertexCount = (keyFrames.length == 2) ? 3 : keyFrames.length * 3;
	    int SegmentCount = VertexCount - 1;
	    
	    int knotLength = keyFrames.length * 2;
		double[] knot = new double[knotLength];
		for (int i = 0; i < knotLength; ++i) {
			knot[i] = (i < knotLength / 2) ? 0.0 : 1.0;
		}
		BSpline sCurve = new BSpline(keyFrames, knot, keyFrames.length);
		
		Point3f[] keyVertex = new Point3f[VertexCount];
	    
		for (int i = 0; i < VertexCount; i++) {
	    	keyVertex[i] = sCurve.computeFor((float)(i) / (float)SegmentCount);
	    }
	    
	    Link newLink = new Link(keyVertex, VertexCount);
	    newLink.lineIsComputed = false;
	    MeasureLinks.add(newLink);
	}
    
    public void addHierarchyLinks(float[] data) {
    	Point3f[] path;
    	int index = 0;
    	while (index < data.length) {
    		path = new Point3f[(int)data[index++]];
    		for (int y = 0; y < path.length; ++y) {
    			path[y] = new Point3f(data[index++], data[index++], data[index++]);
    		}
    		Link newLink = new Link(path, path.length);
    		HierarchyLinks.add(newLink);
    	}
    }
    
    public void addConnectivityLinks(float[] data) {
    	Point3f[] path;
    	int index = 0;
    	while (index < data.length) {
    		path = new Point3f[(int)data[index++]];
    		for (int y = 0; y < path.length; ++y) {
    			path[y] = new Point3f(data[index++], data[index++], data[index++]);
    		}
    		Link newLink = new Link(path, path.length);
    		MeasureLinks.add(newLink);
    	}
    }
    
    public void addAllMeasureLink(int[] keyFrames) {
    	int[] path;
    	for (int i = 0; i < keyFrames.length;) {
    		path = new int[keyFrames[i]];
    		path = copyOfRange(keyFrames, i + 1, i + 1 + keyFrames[i]);
    		addConnectivityLink(path);
    		i += keyFrames[i] + 1;
    	}
    }
    
    public void addAllHierarchyLink(int[] keyFrames) {
    	int[] path;
    	for (int i = 0; i < keyFrames.length;) {
    		path = new int[keyFrames[i]];
    		path = copyOfRange(keyFrames, i + 1, i + 1 + keyFrames[i]);
    		addHierarchyLink(path);
    		i += keyFrames[i] + 1;
    	}
    }
    
	public void addNode(float posX, float posY) {
		addNode(posX, posY, 0.0f);
    }
	
	public void addNode(float posX, float posY, float posZ) {
		Node newNode = new Node(posX, posY, posZ);
		Nodes.add(newNode);
	}
	
    public void removeNode(int index) {
    	Nodes.remove(index);
    }
    
    
    /* Nodes modifiers */
    public void setNodePosition(int index, float posX, float posY, float posZ) {
    	Nodes.get(index).setPosition(posX, posY, posZ);
	}
    
	public void setNodeColor(int index, float iR, float iG, float iB) {
		setNodeOuterRingColor(index, iR, iG, iB);
	}
	
	public void setNodeInnerCircleColor(int index, float iR, float iG, float iB) {
		Nodes.get(index).setInnerCircleColor(iR, iG, iB);
	}
	
	public void setNodeOuterRingColor(int index, float iR, float iG, float iB) {
		Nodes.get(index).setOuterCircleColor(iR, iG, iB);
	}
	
	public void setNodeVisibility(int index, boolean visible) {
		Nodes.get(index).setVisibility(visible);
	}
	
	public void setNodeTransparency(int index, float trans) {
		Nodes.get(index).setTransparency(trans);
	}
	
	public void setNodeSize(int index, float radius) {
		Nodes.get(index).setDisplayRadius(radius);
	}
	/*	*/
	
	
	/* Measure link modifiers */
	public void setMeasureLinkWidth(int index, float width) {
		MeasureLinks.get(index).setLinkWidth(width);
	}
	
	public void setMeasureLinkColorGradient(int index, float sR, float sG, float sB, float eR, float eG, float eB) {
		MeasureLinks.get(index).setAllFrameToGradient(new Color3f(sR, sG, sB), 
												      new Color3f(eR, eG, eB));
	}
	
	public void setMeasureLinkTransparency(int index, float trans) {
		MeasureLinks.get(index).enableBlending(trans != 0);
		MeasureLinks.get(index).setAllFrameToOpacity(1 - trans);
	}
    
    public void setMeasureLinkVisibility(int index, boolean visible) {
    	MeasureLinks.get(index).setVisible(visible);
    }
    
    public void setMeasureLinkOffset(int index, float offset) {
    	MeasureLinks.get(index).measureOffset.set(0,0,offset);
    }
    /*	*/
    
    
    /* Hierarchy link modifiers */
    public void setHierarchyLinkVisibility(int index, boolean visible) {
    	HierarchyLinks.get(index).setVisible(visible);
    }
    
    public void setRegionLinkColorGradient(int index, float sR, float sG, float sB, float eR, float eG, float eB) {
		HierarchyLinks.get(index).setAllFrameToGradient(new Color3f(sR, sG, sB), 
												        new Color3f(eR, eG, eB));
	}
    /*	*/
    
    
    /* Text modifiers */
    public void setNodeText(int index, String text) {
    	Nodes.get(index).setText(text);
    }
    
    public void setTextTransparency(int index, float iA) {
    	Nodes.get(index).setTextTransparency(iA);
    }
    
    public void setTextVisible(int index, boolean visible) {
    	Nodes.get(index).setTextVisible(visible);
    }
    
    public void setTextSize(int index, float iSize) {
    	Nodes.get(index).setTextSize(iSize);
    }
    
    public void setTextPosition(int index, float iX, float iY, float iZ) {
    	Nodes.get(index).setTextPosition(iX, iY, iZ);
    }
    
    public void setTextOrientation(int index, float iAngle) {
    	Nodes.get(index).setTextOrientation(iAngle);
    }
    
    public void setTextAlignment(int index, int al) {
//        gf.invoke(false,
//            new GLRunnable() {
//                public boolean run(GLAutoDrawable gf) {
//                    Nodes.get(index).setTextAlignment(al);
//                    return true;
//                }
//            });
	Nodes.get(index).setTextAlignment(al);
    }
    /*	*/
    
    
    /* Polygon modifiers */
    public void setPolygonTransparency(int index, float trans) {
		BstPolygonRegion pol = Polygons.get(index);
		pol.ita.appAtrb.transAtrb.setTransparency(trans);
		pol.ita.setVertexTransparency(trans);
    }
    
    public void setPolygonColor(int index, float iR, float iG, float iB) {
    	Polygons.get(index).ita.setAllVertexColorsTo(new Color3f(iR, iG, iB));
    }
    
    public void setPolygonVisible(int index, boolean visible) {
    	Polygons.get(index).ita.appAtrb.rendAtrb.setVisible(visible);
    }
    /*	*/
    
    
    public boolean isRenderingOptionFieldSet(int field) {
    	return RenderingOptions.containsKey(field);
    }
    
    public void OpenGLDisable(int field) {
    	RenderingOptions.put(new Integer(field), false);
    }
    
    public void OpenGLEnable(int field) {
    	RenderingOptions.put(new Integer(field), true);
    }

    
    public int raypickNearestNode(int x, int y, float minDistanceThreshold) {
		float clickX = x;
		float clickY = WindowHeight - y;
		
		Vector3f objStart = new Vector3f();
		Vector3f objEnd = new Vector3f();
		Vector3f objDir = new Vector3f();

		double coordEnd[] = new double[4];
		double coord[] = new double[4];
		
		boolean hasUnProjected = gwl.glu.gluUnProject(clickX, clickY, 0.0f, m_ModelMatrix, 0, m_ProjectionMatrix, 0, m_Viewport, 0, coordEnd, 0);
		if (!hasUnProjected) {
			System.out.println("Critical OpenGL function does not compute correctly. Contact administrator.");
		}
		gwl.glu.gluUnProject(clickX, clickY, 1.0f, m_ModelMatrix, 0, m_ProjectionMatrix, 0, m_Viewport, 0, coord, 0);
		
		boolean perspectiveProjection = false;
		if (perspectiveProjection) {
			objStart.set(cam.m_eye);
			objEnd.set((float)coordEnd[0], (float)coordEnd[1], (float)coordEnd[2]);
    		objDir.sub(objEnd, objStart);
    		objDir.normalize();
		} 
		else {
			objStart.set((float)coordEnd[0], (float)coordEnd[1], (float)coordEnd[2]);
			objEnd.set((float)coord[0], (float)coord[1], (float)coord[2]);
    		objDir.sub(objEnd, objStart);
    		objDir.normalize();
		}
		
    	float nearestDist = 99999999;
	    int nearestNode = -1;
	    for (int i = 0; i < Nodes.size(); ++i) {
			Vector3f pos = new Vector3f(Nodes.get(i).Position());
			if (cam.testIntersect(objStart, objDir, pos, minDistanceThreshold)) {
				Vector3f diff = new Vector3f();
				diff.sub(pos,objStart);
				float lineParam = objDir.dot(diff);
				
				Vector3f closest0 = new Vector3f(pos);
				Vector3f closest1 = new Vector3f(objStart);
				Vector3f dirCpy = new Vector3f(objDir);
				dirCpy.scale(lineParam);
				closest1.add(dirCpy);
			    
				diff.sub(closest1, closest0);
			    float dist = diff.lengthSquared();
			    if (dist < nearestDist) {
			    	nearestNode = i;
			    	nearestDist = dist;
			    }
			}
		}
		return nearestNode;
	}
    
    public int raypickNearestPolygon(int x, int y) {
		float clickX = x;
		float clickY = WindowHeight - y;
		
		Vector3f objStart = new Vector3f();
		Vector3f objEnd = new Vector3f();
		Vector3f objDir = new Vector3f();

		double coordEnd[] = new double[4];
		double coord[] = new double[4];
		
		boolean hasUnProjected = gwl.glu.gluUnProject(clickX, clickY, 0.0f, m_ModelMatrix, 0, m_ProjectionMatrix, 0, m_Viewport, 0, coordEnd, 0);
		if (!hasUnProjected) {
			System.out.println("Critical OpenGL function does not compute correctly. Contact administrator.");
		}
		gwl.glu.gluUnProject(clickX, clickY, 1.0f, m_ModelMatrix, 0, m_ProjectionMatrix, 0, m_Viewport, 0, coord, 0);
		
		boolean perspectiveProjection = false;
		if (perspectiveProjection) {
			objStart.set(cam.m_eye);
			objEnd.set((float)coordEnd[0], (float)coordEnd[1], (float)coordEnd[2]);
    		objDir.sub(objEnd, objStart);
    		objDir.normalize();
		} 
		else {
			objStart.set((float)coordEnd[0], (float)coordEnd[1], (float)coordEnd[2]);
			objEnd.set((float)coord[0], (float)coord[1], (float)coord[2]);
    		objDir.sub(objEnd, objStart);
    		objDir.normalize();
		}
		
    	// float nearestDist = 99999999;
	    int nearestTriangle = -1;
	    for (int i = 0; i < RadialBoxes.size(); ++i) {
	    	BstRadialBox rb = RadialBoxes.get(i);
			if (cam.testIntersectTriangle(objStart, objDir, rb.vertices[0],
															rb.vertices[1],
															rb.vertices[2])) {
				nearestTriangle = i;
			}
		}
		return nearestTriangle;
	}

    public static int[] copyOfRange(int[] original, int from, int to) {
        int newLength = to - from;
        if (newLength < 0)
            throw new IllegalArgumentException(from + " > " + to);
        int[] copy = new int[newLength];
        System.arraycopy(original, from, copy, 0,
                         Math.min(original.length - from, newLength));
        return copy;
    }

}
