package open.dolphin.impl.scheam.holder;

import java.awt.Graphics2D;
import java.awt.Point;

/**
 *
 * @author kazm
 */
public interface DrawingHolder {
    
    boolean contains(Point p);
    
    void draw(Graphics2D g2);
    
    void translate(double x, double y);

    void rotate(double theta);

    void expand(double sx, double sy);
   
}
