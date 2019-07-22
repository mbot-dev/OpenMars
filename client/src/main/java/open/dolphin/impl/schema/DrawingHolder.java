package open.dolphin.impl.schema;

import java.awt.Graphics2D;
import java.awt.Point;

/**
 *
 * @author Kazushi Minagawa.
 */
public interface DrawingHolder {
    
    boolean contains(Point p);
    
    void draw(Graphics2D g2);
    
    void translate(double x, double y);

}
