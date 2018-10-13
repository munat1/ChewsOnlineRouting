import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.shape.Line;
import javafx.scene.shape.Circle;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


public class Netzwerk {
    Pane ebene = new Pane();

    HashSet<Vector2D> knotenMenge = new HashSet<>();
    public void addKnoten(Vector2D v){
        if(!knotenMenge.contains(v))
            knotenMenge.add(v);
    }
    public void triangulate() {
        DelaunayTriangulator d = new DelaunayTriangulator(new ArrayList<Vector2D>(knotenMenge));
        d.triangulate();
        List<Triangle2D> triangles = d.getTriangles();
        for (Triangle2D t : triangles){
            addKante(t.a, t.b);
            addKante(t.b, t.c);
            addKante(t.c, t.a);

        }
    }
    public void addKante(Vector2D v, Vector2D w){
        v.addNeighbour(w);
        w.addNeighbour(v);
    }
    public String toString() {
        return "Knoten{" + knotenMenge + "}";
    }
    public void clustering(Pane ebene){
        this.ebene = ebene;
        kmeansnew kk = new kmeansnew();
        Vector2D c_1_old = kk.initializeCenter_1(this);
        Vector2D c_2_old = kk.initializeCenter_2(this);
        ArrayList<Vector2D> cl_1_old = kk.createCluster_1(this, c_1_old, c_2_old);
        ArrayList<Vector2D> cl_2_old = kk.createCluster_2(this, c_1_old, c_2_old);
        Vector2D c_1_new = kk.trainCenter_1(cl_1_old, c_1_old);
        Vector2D c_2_new = kk.trainCenter_2(cl_2_old, c_2_old);
       while (c_1_new.distance(c_1_old) > 1.0 && c_2_new.distance(c_2_old) > 1.0){
            cl_1_old.clear();
            cl_2_old.clear();
            cl_1_old = kk.createCluster_1(this, c_1_new, c_2_new);
            cl_2_old = kk.createCluster_2(this, c_1_new, c_2_new);
            c_1_old = c_1_new;
            c_2_old = c_2_new;
            c_1_new = kk.trainCenter_1(cl_1_old, c_1_old);
            c_2_new = kk.trainCenter_2(cl_2_old, c_2_old);
        }
        Vector2D minsofarc1 = new Vector2D(10000,10000);
        Vector2D minsofarc2 = new Vector2D(10000,10000);
        for (Vector2D v : this.knotenMenge){
            if (c_1_new.distance(v) < c_1_new.distance(minsofarc1)){
                minsofarc1 = v;
            }
            if (c_2_new.distance(v) < c_2_new.distance(minsofarc2)){
                minsofarc2 = v;
            }
        }
        Circle centrum1 = new Circle();
        centrum1.setCenterX(c_1_new.x);
        centrum1.setCenterY(c_1_new.y);
        centrum1.setRadius(100.0);
        centrum1.setStrokeWidth(1.0);
        centrum1.setFill(Color.color(1,0,0,0.2));
        ebene.getChildren().add(centrum1);
        Circle centrum2 = new Circle();
        centrum2.setCenterX(c_2_new.x);
        centrum2.setCenterY(c_2_new.y);
        centrum2.setRadius(100.0);
        centrum2.setStrokeWidth(1.0);
        centrum2.setFill(Color.color(1,0,0,0.2));
        ebene.getChildren().add(centrum2);
        minsofarc1.isHighway = true;
        minsofarc2.isHighway = true;
        minsofarc1.addNeighbour(minsofarc2);
        minsofarc2.addNeighbour(minsofarc1);

    }
    public ArrayList<Vector2D> Chew(Vector2D s, Vector2D t, Pane ebene) {
        this.ebene = ebene;
        ArrayList<Vector2D> route = new ArrayList<Vector2D>();
        Vector2D current = s;
        route.add(s);
        while (!current.equals(t)) {
            Edge2D rightest = rightestTriangle(s, t, current);
            if(rightest.b.equals(t)){
                route.add(t);
                break;
            }
            Vector2D rightestPoint = rightestPoint(s, t, findeKreis(rightest.a, rightest.b, current), findeKreis(rightest.a, rightest.b, current).sub(rightest.a).mag());
            Vector2D leftistPoint = leftistPoint(s, t, findeKreis(rightest.a, rightest.b, current), findeKreis(rightest.a, rightest.b, current).sub(rightest.a).mag());
            if (determineTheSide(current.sub(leftistPoint), rightestPoint.sub(leftistPoint)) == determineTheSide(rightest.b.sub(leftistPoint), rightestPoint.sub(leftistPoint))) {
                    route.add(rightest.b);
                    current = rightest.b;
            } else {
                    route.add(rightest.a);
                    current = rightest.a;
            }
        }
        return route;
    }
    public Vector2D findeKreis(Vector2D p_1, Vector2D p_2, Vector2D p_3){
        Vector2D midP1P2 = p_1.add(p_2).mult(0.5);
        Vector2D midP2P3 = p_2.add(p_3).mult(0.5);
        double tangenteP1P2 = -1 / (p_2.sub(p_1).y/p_2.sub(p_1).x);
        double tangenteP2P3 = -1 / (p_3.sub(p_2).y/p_3.sub(p_2).x);
        double bAB = midP1P2.y - tangenteP1P2 * midP1P2.x;
        double bBC = midP2P3.y - tangenteP2P3 * midP2P3.x;
        double x = (bAB - bBC) / (tangenteP2P3 - tangenteP1P2);
        Vector2D circumcenter = new Vector2D(x,(tangenteP1P2 * x) + bAB);
        return circumcenter;
    }
    public Vector2D rightestPoint(Vector2D s, Vector2D t, Vector2D c, double r){
        Vector2D v = t.sub(s).mult(c.sub(s).dot(t.sub(s))/t.sub(s).dot(t.sub(s))).add(s);
        double lenght = Math.sqrt(r*r - c.sub(v).mag() * c.sub(v).mag());
        System.out.println(lenght);
        Vector2D rightest = v.add(t.sub(s).mult(1/t.sub(s).mag()).mult(lenght));
        return rightest;
    }
    public  Vector2D leftistPoint(Vector2D s, Vector2D t, Vector2D c, double r){
        Circle knoten = new Circle();
        knoten.setCenterX(c.x);
        knoten.setCenterY(c.y);
        knoten.setRadius(r);
        knoten.setStrokeWidth(1);
        knoten.setStroke(Color.BLACK);
        knoten.setFill(Color.color(0,0,0,0));
        ebene.getChildren().add(knoten);

        Vector2D leftist = c.add(s.sub(t).mult(1/s.sub(t).mag()).mult(r));
        return leftist;
    }
    public static Vector2D findIntersection(Edge2D l1, Edge2D l2) {
        double a1 = l1.b.y - l1.a.y;
        double b1 = l1.a.x - l1.b.x;
        double c1 = a1 * l1.a.x + b1 * l1.a.y;

        double a2 = l2.b.y - l2.a.y;
        double b2 = l2.a.x - l2.b.x;
        double c2 = a2 * l2.a.x + b2 * l2.a.y;

        double delta = a1 * b2 - a2 * b1;
        return new Vector2D((b2 * c1 - b1 * c2) / delta, (a1 * c2 - a2 * c1) / delta);
    }
    public double determineTheSide(Vector2D x, Vector2D y){
        return x.cross(y)/Math.abs(x.cross(y));
    }
    public Edge2D rightestTriangle(Vector2D s, Vector2D t, Vector2D current){
        Vector2D nexttoS = new Vector2D(0,0);
        for(Vector2D v: s.nachbarMenge){
            nexttoS = v;
            break;
        }
        Edge2D rightest = new Edge2D(s, nexttoS);
        for (Vector2D v:current.nachbarMenge){
            if (v.equals(t)){
                return new Edge2D(current, v);
            }
            for (Vector2D w : v.nachbarMenge){
                if (current.nachbarMenge.contains(w) || current.equals(w)){
                    if (determineTheSide(v.sub(s), t.sub(s)) != determineTheSide(w.sub(s), t.sub(s))) {
                        if (findIntersection(new Edge2D(v, w), new Edge2D(s, t)).distance(t) < findIntersection(rightest, new Edge2D(s, t)).distance(t)) {
                            rightest = new Edge2D(v, w);
                        }
                    }
                }
            }
        }
        return rightest;
    }

}
