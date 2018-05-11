package org.brainstorm.list;

import java.awt.*;

/**
 * @author Francois Tadel
 */
public class BstListItem {
    private String fileName = "";
    private String name = "";
    private String type = "";
    private Object userData = null;
    private Color  color = null;
    
    // CONSTRUCTORS
    public BstListItem(String type_, String fileName_, String name_, Object userData_, float r, float g, float b) {
        this.fileName = fileName_;
        this.name     = name_;
        this.type     = type_;
        this.userData = userData_;
        this.color    = new Color(r,g,b);
    }
    public BstListItem(String type_, String fileName_, String name_, Object userData_, Color color) {
        this.fileName = fileName_;
        this.name     = name_;
        this.type     = type_;
        this.userData = userData_;
        this.color    = color;
    }
    public BstListItem(String type_, String fileName_, String name_, Object userData_) {
        this.fileName = fileName_;
        this.name     = name_;
        this.type     = type_;
        this.userData = userData_;
    }
    public BstListItem(String type_, String fileName_, String name_) {
        this.fileName = fileName_;
        this.name     = name_;
        this.type     = type_;
    }
    
    public String getName(){
        return this.name;
    }
    public String getFileName(){
        return this.fileName;
    }
    public String getType(){
        return this.type;
    }
    public Object getUserData(){
        return this.userData;
    }
    public Color getColor() {
        return color;
    }
    
    public void setColor(Color color) {
        this.color = color;
    }
    public void setColor(float r, float g, float b) {
        this.color = new Color(r,g,b);
    }
    public void setUserData(Object o){
        this.userData = o;
    }

    public String toString(){
        return this.name;
    }
}
