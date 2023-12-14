package Control;

import java.io.Serializable;

public class Poligono implements Serializable {

    private static final long serialVersionUID = 1L;

    public Boolean onSegment(Point p, Point q, Point r) {
        if (q.x <= Math.max(p.x, r.x) && q.x >= Math.min(p.x, r.x) && q.y <= Math.max(p.y, r.y) && q.y >= Math.min(p.y, r.y)) {
            return true;
        }

        return false;
    }

    public Integer orientation(Point p, Point q, Point r) {
        Double val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);

        if (val == 0) {
            return 0;
        }

        return (val > 0) ? 1 : 2;
    }

    public Boolean doIntersect(Point p1, Point q1, Point p2, Point q2) {
        Integer o1 = orientation(p1, q1, p2);
        Integer o2 = orientation(p1, q1, q2);
        Integer o3 = orientation(p2, q2, p1);
        Integer o4 = orientation(p2, q2, q1);

        if (o1 != o2 && o3 != o4) {
            return true;
        }

        if (o1 == 0 && onSegment(p1, p2, q1)) {
            return true;
        }

        if (o2 == 0 && onSegment(p1, q2, q1)) {
            return true;
        }

        if (o3 == 0 && onSegment(p2, p1, q2)) {
            return true;
        }

        if (o4 == 0 && onSegment(p2, q1, q2)) {
            return true;
        }

        return false;
    }

    public Boolean isInside(Point polygon[], Integer n, Point p) {

        Double INF = 10000.0;

        if (n < 3) {
            return false;
        }

        Point extreme = new Point(INF, p.y);

        Integer count = 0, i = 0;
        do {
            Integer next = (i + 1) % n;
            if (doIntersect(polygon[i], polygon[next], p, extreme)) {
                if (orientation(polygon[i], p, polygon[next]) == 0) {
                    return onSegment(polygon[i], p, polygon[next]);
                }

                count++;
            }
            i = next;
        } while (i != 0);

        return (count & 1) == 1 ? true : false;
    }

}
