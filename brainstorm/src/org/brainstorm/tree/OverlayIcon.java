package org.brainstorm.tree;

import javax.swing.ImageIcon;
import java.awt.*;

/**
 * @author Francois Tadel
 */
public class OverlayIcon extends ImageIcon {

    private ImageIcon base;
    private ImageIcon overlay;

    public OverlayIcon(ImageIcon base) {
        super(base.getImage());
        this.base = base;
        this.overlay = null;
    }
    public OverlayIcon(ImageIcon base, ImageIcon overlay) {
        super(base.getImage());
        this.base = base;
        this.overlay = overlay;
    }

    public void add(ImageIcon overlay){
        this.overlay = overlay;
    }

    @Override
    public synchronized void paintIcon(Component c, Graphics g, int x, int y) {
        base.paintIcon(c, g, x, y);
        if (overlay != null){
            overlay.paintIcon(c, g, x, y);
        }
    }
}
