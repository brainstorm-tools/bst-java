package org.brainstorm.connect;

import java.awt.Dimension;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import javax.media.opengl.*;


public class GraphicsFramework extends GLCanvas implements GLEventListener {

    private static final long serialVersionUID = -8376370802647613132L;
    float Yaw = 0;
    public int Width, Height;
    private int preferredWidth, preferredHeight;
    BrainMeasureVisualiser vis;

    public GraphicsFramework() {
        super();

        vis = new BrainMeasureVisualiser(this);

        addGLEventListener(this);

        /*
        this.addMouseWheelListener(new MouseWheelListener() {
        public void mouseWheelMoved(MouseWheelEvent arg0) {
        /*
        float Pitch = 45;
        float xx = (float) (Math.cos(Math.toRadians(Yaw)) * Math.cos(Math.toRadians(Pitch)));
        float yy = (float) (Math.sin(Math.toRadians(Yaw)) * Math.cos(Math.toRadians(Pitch)));
        float zz = (float) Math.sin(Math.toRadians(Pitch));
        lookAt(xx, yy, zz, 0, 0, 0, 0, 1, 0);
        Yaw += arg0.getWheelRotation();
        repaint();
        
        }
        });
         */

        /*this.addMouseListener(new MouseListener() {
        public void mouseClicked(MouseEvent arg0) {
        //int index = raypickNearestNode(arg0.getX(), arg0.getY(), 4);
        //System.out.println(index);
        //setNodeColor(index, 1, 1, 0);
        //repaint();
        }
        public void mouseEntered(MouseEvent arg0) {
        
        }
        public void mouseExited(MouseEvent arg0) {
        
        }
        public void mousePressed(MouseEvent arg0) {
        
        }
        public void mouseReleased(MouseEvent arg0) {
        
        }
        });*/
    }

    public void setPreferredWindowSize(int w, int h) {
        preferredWidth = w;
        preferredHeight = h;
    }

    public Dimension getPreferredSize() {
        return new Dimension(preferredWidth, preferredHeight);
    }

    public void start() {
        vis.startBackgroundWork();
    }

    public void stop() {
        vis.stopBackgroundWork();
    }

    public void init(GLAutoDrawable drawable) {
        vis.setOpenGLHandle(drawable);
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        Width = width;
        Height = height;
        vis.setOpenGLHandle(drawable);
        vis.resize(width, height);
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
        // Leave this empty
    }

    public void display(GLAutoDrawable drawable) {
        vis.setOpenGLHandle(drawable);
        vis.draw();
    }

    public void dispose(GLAutoDrawable arg0) {
    }

    public void setMeasureLinkBlendingFunction(int source, int destination) {
        for (int i = 0; i < vis.MeasureLinks.size(); ++i) {
            vis.MeasureLinks.get(i).lineStrip.appAtrb.transAtrb.setBlendingSource(source);
            vis.MeasureLinks.get(i).lineStrip.appAtrb.transAtrb.setBlendingDestination(destination);
        }
    }

    public void setNodeBlendingFunction(int source, int destination) {
        for (int i = 0; i < vis.Nodes.size(); ++i) {
            vis.Nodes.get(i).innerCircle.appAtrb.transAtrb.setBlendingFunction(source, destination);
            vis.Nodes.get(i).outerCircle.appAtrb.transAtrb.setBlendingFunction(source, destination);
        }
    }

    public void renderInQuad(boolean renderInQuad) {
        for (int i = 0; i < vis.MeasureLinks.size(); ++i) {
            vis.MeasureLinks.get(i).lineStrip.renderInQuad = renderInQuad;
        }
        for (int i = 0; i < vis.HierarchyLinks.size(); ++i) {
            vis.HierarchyLinks.get(i).lineStrip.renderInQuad = renderInQuad;
        }
    }
    /* ----- CAMERA ----- */

    public void lookAt(float eX, float eY, float eZ, float tX, float tY, float tZ, float uX, float uY, float uZ) {
        vis.lookAt(eX, eY, eZ, tX, tY, tZ, uX, uY, uZ);
    }

    public void zoom(float factor) {
        vis.cam.m_zoom = factor;
    }
    /* ----- ----- */

    public void addHierarchyLink(int[] keyFramesIndex) {
        vis.addHierarchyLink(keyFramesIndex);
    }

    public void addPrecomputedHierarchyLink(float[] data) {
        vis.addHierarchyLinks(data);
    }

    public void addConnectivityLink(int[] keyFramesIndex) {
        vis.addConnectivityLink(keyFramesIndex);
    }

    public void addAllMeasureLink(int[] keyFrames) {
        vis.addAllMeasureLink(keyFrames);
    }

    public void addPrecomputedMeasureLinks(float[] data) {
        vis.addConnectivityLinks(data);
    }

    public void addNode(float posX, float posY) {
        vis.addNode(posX, posY);
    }

    public void addNode(float posX, float posY, float posZ) {
        vis.addNode(posX, posY, posZ);
    }

    public void addNode(float[] posX, float[] posY, float[] posZ) {
        for (int i = 0; i < posX.length; ++i) {
            vis.addNode(posX[i], posY[i], posZ[i]);
        }
    }

    public void removeNode(int index) {
        vis.removeNode(index);
    }

    public void setNodePosition(int index, float posX, float posY, float posZ) {
        vis.setNodePosition(index, posX, posY, posZ);
    }

    public void setNodeInnerColor(int[] indices, float iR, float iG, float iB) {
        for (int i = 0; i < indices.length; ++i) {
            vis.setNodeInnerCircleColor(indices[i], iR, iG, iB);
        }
    }

    public void setNodeOuterColor(int[] indices, float iR, float iG, float iB) {
        for (int i = 0; i < indices.length; ++i) {
            vis.setNodeOuterRingColor(indices[i], iR, iG, iB);
        }
    }

    public void setNodeTransparency(int index, float iA) {
        vis.setNodeTransparency(index, iA);
    }

    public void setNodeSize(int[] indexes, float radius) {
        for (int i = 0; i < indexes.length; ++i) {
            vis.Nodes.get(indexes[i]).setDisplayRadius(radius);
        }
    }

    public void setNodeOuterRadius(int[] indexes, float radius) {
        for (int i = 0; i < indexes.length; ++i) {
            vis.Nodes.get(indexes[i]).setOuterCircleRadius(radius);
        }
    }

    public void setNodeInnerRadius(int[] indexes, float radius) {
        for (int i = 0; i < indexes.length; ++i) {
            vis.Nodes.get(indexes[i]).setInnerCircleRadius(radius);
        }
    }

    public void setNodeInnerCircleVisibility(int[] indexes, boolean visible) {
        for (int i = 0; i < indexes.length; ++i) {
            vis.Nodes.get(indexes[i]).innerCircle.appAtrb.rendAtrb.setVisible(visible);
        }
    }

    public void setNodeOuterCircleVisibility(int[] indexes, boolean visible) {
        for (int i = 0; i < indexes.length; ++i) {
            vis.Nodes.get(indexes[i]).outerCircle.appAtrb.rendAtrb.setVisible(visible);
        }
    }

    public void setNodeVisibility(int index, boolean visible) {
        vis.setNodeVisibility(index, visible);
    }

    /* Measure Links modifier */
    public void setMeasureLinkTransparency(int[] indexes, float trans) {
        for (int i = 0; i < indexes.length; ++i) {
            vis.setMeasureLinkTransparency(indexes[i], trans);
        }
    }

    public void setMeasureLinkColorGradient(int[] indexes, float[] sR, float[] sG, float[] sB, float[] eR, float[] eG, float[] eB) {
        for (int i = 0; i < indexes.length; ++i) {
            vis.setMeasureLinkColorGradient(indexes[i], sR[i], sG[i], sB[i], eR[i], eG[i], eB[i]);
        }
    }

    public void setMeasureLinkWidth(int[] indexes, float width) {
        for (int i = 0; i < indexes.length; ++i) {
            vis.setMeasureLinkWidth(indexes[i], width);
        }
    }

    public void setMeasureLinkVisibility(int[] indexes, boolean visible) {
        for (int i = 0; i < indexes.length; ++i) {
            vis.setMeasureLinkVisibility(indexes[i], visible);
        }
    }

    public void setMeasureLinkOffset(int[] indexes, float[] offsets) {
        for (int i = 0; i < indexes.length; ++i) {
            vis.MeasureLinks.get(indexes[i]).setMeasureOffset(0, 0, offsets[i]);
        }
    }
    /*	*/

    /* Region modifiers	*/
    public void setRegionLinkVisibility(int[] indexes, boolean visible) {
        for (int i = 0; i < indexes.length; ++i) {
            vis.setHierarchyLinkVisibility(indexes[i], visible);
        }
    }

    public void setRegionLinkWidth(int[] indexes, float width) {
        for (int i = 0; i < indexes.length; ++i) {
            vis.HierarchyLinks.get(indexes[i]).setLinkWidth(width);
        }
    }

    public void setRegionLinkOffset(int[] indexes, float[] offsets) {
        for (int i = 0; i < indexes.length; ++i) {
            vis.HierarchyLinks.get(indexes[i]).setMeasureOffset(0, 0, offsets[i]);
        }
    }

    public void setRegionLinkColorGradient(int[] indexes, float[] sR, float[] sG, float[] sB, float[] eR, float[] eG, float[] eB) {
        for (int i = 0; i < indexes.length; ++i) {
            vis.setRegionLinkColorGradient(indexes[i], sR[i], sG[i], sB[i], eR[i], eG[i], eB[i]);
        }
    }
    /*	*/

    /* Text modifiers */
    public void setNodeText(int index, String text) {
        vis.setNodeText(index, text);
    }

    public void setTextTransparency(int[] indices, float iA) {
        for (int i = 0; i < indices.length; ++i) {
            vis.setTextTransparency(indices[i], iA);
        }
    }

    public void setTextVisible(int[] indices, boolean visible) {
        for (int i = 0; i < indices.length; ++i) {
            vis.setTextVisible(indices[i], visible);
        }
    }

    public void setTextSize(int[] indices, float iSize) {
        for (int i = 0; i < indices.length; ++i) {
            vis.setTextSize(indices[i], iSize);
        }
    }

    public void setTextPosition(int[] indices, float[] iX, float[] iY, float[] iZ) {
        for (int i = 0; i < indices.length; ++i) {
            vis.setTextPosition(indices[i], iX[i], iY[i], iZ[i]);
        }
    }

    public void setTextOrientation(int[] indices, float[] iAngle) {
        for (int i = 0; i < indices.length; ++i) {
            vis.setTextOrientation(indices[i], iAngle[i]);
        }
    }

    public void setTextAlignment(int[] indices, int al) {
        for (int i = 0; i < indices.length; ++i) {
            vis.setTextAlignment(indices[i], al);
        }
    }

    public void setTextColor(int[] indices, float r, float g, float b) {
        for (int i = 0; i < indices.length; ++i) {
            vis.Nodes.get(indices[i]).setTextColor(r, g, b);
        }
    }
    /*	*/

    /* Polygon modifiers */
    public void setPolygonTransparency(int[] indices, float trans) {
        for (int i = 0; i < indices.length; ++i) {
            vis.setPolygonTransparency(indices[i], trans);
        }
    }

    public void setPolygonColor(int[] indices, float[] iR, float[] iG, float[] iB) {
        for (int i = 0; i < indices.length; ++i) {
            vis.setPolygonColor(indices[i], iR[i], iG[i], iB[i]);
        }
    }

    public void setPolygonVisible(int[] indices, boolean visible) {
        for (int i = 0; i < indices.length; ++i) {
            vis.setPolygonVisible(indices[i], visible);
        }
    }

    public void setPolygonDrawOutline(int[] indices, boolean outline) {
        for (int i = 0; i < indices.length; ++i) {
            vis.Polygons.get(indices[i]).setDrawOutline(outline);
        }
    }

    public void setPolygonDrawFilling(int[] indices, boolean filling) {
        for (int i = 0; i < indices.length; ++i) {
            vis.Polygons.get(indices[i]).setDrawFilling(filling);
        }
    }

    /*	*/
    public int raypickNearestNode(int x, int y, float minDistanceThreshold) {
        return vis.raypickNearestNode(x, y, minDistanceThreshold);
    }

    public int raypickNearestTriangle(int x, int y) {
        return vis.raypickNearestPolygon(x, y);
    }

    public void addRadialBox(float[] x, float[] y, float[] z, int[] indices) {
        BstRadialBox rb = new BstRadialBox(x.length, indices.length);
        Point3f[] vertices = new Point3f[x.length];
        for (int i = 0; i < x.length; ++i) {
            vertices[i] = new Point3f(x[i], y[i], z[i]);
        }
        rb.setCoordinates(vertices);
        rb.setIndices(indices);
        vis.RadialBoxes.add(rb);
    }

    public void addPolygon(float x[], float y[], float z[], int[] ind) {
        int vertexCount = x.length;
        int indexCount = ind.length;
        Point3f[] vertices = new Point3f[vertexCount];

        for (int i = 0; i < vertexCount; ++i) {
            vertices[i] = new Point3f(x[i], y[i], z[i]);
        }

        BstPolygonRegion pol = new BstPolygonRegion(vertexCount, indexCount);
        pol.ita.setCoordinates(vertices);
        pol.ita.setIndices(ind);

        vis.Polygons.add(pol);
    }

    public void addPolygon(float[] v, int[] ind) {
        int vertexCount = v.length / 3;
        int indexCount = ind.length;
        Point3f[] vertices = new Point3f[vertexCount];

        int vInd = 0;
        for (int i = 0; i < vertexCount; ++i) {
            vertices[i] = new Point3f(v[vInd], v[vInd + 1], v[vInd + 2]);
            vInd += 3;
        }

        BstPolygonRegion pol = new BstPolygonRegion(vertexCount, indexCount);
        pol.ita.setCoordinates(vertices);
        pol.ita.setIndices(ind);

        vis.Polygons.add(pol);
    }

    public void addPolygon(float[] v, int[] ind, boolean computeNormal) {
        int vertexCount = v.length / 3;
        int indexCount = ind.length;
        Point3f[] vertices = new Point3f[vertexCount];

        int vInd = 0;
        for (int i = 0; i < vertexCount; ++i) {
            vertices[i] = new Point3f(v[vInd], v[vInd + 1], v[vInd + 2]);
            vInd += 3;
        }

        BstPolygonRegion pol = new BstPolygonRegion(vertexCount, indexCount);
        pol.ita.setCoordinates(vertices);
        pol.ita.setIndices(ind);

        if (computeNormal) {
            pol.ita.computeNormal();
        }

        vis.Polygons.add(pol);
    }

    public void addRegionOutline(float[] x, float[] y, float[] z) {
        int nVertex = x.length;
        Point3f[] frames = new Point3f[nVertex];
        for (int i = 0; i < nVertex; ++i) {
            frames[i] = new Point3f(x[i], y[i], z[i]);
        }
        BstLineStripArray ls = new BstLineStripArray(nVertex);
        ls.renderInQuad = false;
        ls.setCoordinates(frames);
        vis.RegionOutline.add(ls);
    }

    public void setRegionOutlineColor(int indice, float r, float g, float b) {
        vis.RegionOutline.get(indice).setAllVertexColorsTo(new Color3f(r, g, b));
    }

    public void setRegionOutlineThickness(int indice, float width) {
        vis.RegionOutline.get(indice).setLineWidth(width);
    }

    public void setRegionOutlineTransparency(int indice, float trans) {
        vis.RegionOutline.get(indice).setVertexTransparency(trans);
    }

    public void OpenGLDisable(int field) {
        vis.OpenGLDisable(field);
    }

    public void OpenGLEnable(int field) {
        vis.OpenGLEnable(field);
    }

    public void setClearColor(float arg0, float arg1, float arg2, float arg3) {
        vis.ClearColor.set(arg0, arg1, arg2, arg3);
    }

    public void setNodesIsVisible(boolean visible) {
        vis.NodesAreVisible = visible;
    }

    public void setMeasureIsVisible(boolean visible) {
        vis.MeasureIsVisible = visible;
    }

    public void setRegionIsVisible(boolean visible) {
        vis.HierarchyIsVisible = visible;
    }

    public void resetDisplay() {
        vis.MeasureLinks.clear();
        vis.HierarchyLinks.clear();
        vis.Nodes.clear();
    }

    public void clearMeasureLinks() {
        vis.MeasureLinks.clear();
    }

    public void clearRegionLinks() {
        vis.HierarchyLinks.clear();
    }

    public void clearLinks() {
        vis.MeasureLinks.clear();
        vis.HierarchyLinks.clear();
    }

    public void clearAll() {
        vis.MeasureLinks.clear();
        vis.HierarchyLinks.clear();
        vis.Nodes.clear();
        vis.cam = null;
    }
}
