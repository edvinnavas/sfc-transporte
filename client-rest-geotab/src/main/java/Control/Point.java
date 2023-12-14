package Control;

import java.io.Serializable;

public class Point implements Serializable {

    private static final long serialVersionUID = 1L;

    Double x, y;

    Point(Double p, Double q) {
        x = p;
        y = q;
    }
    
}
