import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;
import java.util.List;
public class kmeansnew {
    Netzwerk g = new Netzwerk();
    public ArrayList<Vector2D> weitestePunkte(Netzwerk g){
        double distanceold = 0;
        ArrayList<Vector2D> weitestePunkte = new ArrayList<Vector2D>();
        for (Vector2D v:g.knotenMenge){
            for (Vector2D w:g.knotenMenge){
                if (distanceold<v.distance(w)){
                    weitestePunkte.clear();
                    weitestePunkte.add(v);
                    weitestePunkte.add(w);
                    distanceold = v.distance(w);
                }
            }
        }
        return weitestePunkte;
    }
    public Vector2D initializeCenter_1(Netzwerk g) {
        ArrayList<Vector2D> centren = weitestePunkte(g);
        Vector2D c_1 = new Vector2D(centren.get(0).x, centren.get(0).y);
        return c_1;
    }
    public Vector2D initializeCenter_2(Netzwerk g) {
        ArrayList<Vector2D> centren = weitestePunkte(g);
        Vector2D c_2 = new Vector2D(centren.get(1).x, centren.get(1).y);
        return c_2;
    }
    public ArrayList<Vector2D> createCluster_1(Netzwerk g, Vector2D center_1, Vector2D center_2){
        ArrayList<Vector2D> cluster_1_old = new ArrayList<Vector2D>();
        for (Vector2D v : g.knotenMenge){
            if (v.distance(center_1) < v.distance(center_2) || v.distance(center_1) == v.distance(center_2)){
                cluster_1_old.add(v);
            }
        }
        return cluster_1_old;
    }
    public ArrayList<Vector2D> createCluster_2(Netzwerk g, Vector2D center_1, Vector2D center_2){
        ArrayList<Vector2D> cluster_2_old = new ArrayList<Vector2D>();
        for (Vector2D v : g.knotenMenge){
            if (v.distance(center_1) > v.distance(center_2)){
                cluster_2_old.add(v);
            }
        }
        return cluster_2_old;
    }
    public Vector2D trainCenter_1(ArrayList<Vector2D> cluster, Vector2D center_1){
        Vector2D cluster_center_1_new = center_1;
        for (int i = 0; i < cluster.size(); i++) {
            cluster_center_1_new = cluster_center_1_new.add(cluster.get(i));
        }
        if (cluster.size() != 0) {
            cluster_center_1_new.x /= cluster.size()+1;
            cluster_center_1_new.y /= cluster.size()+1;
            return cluster_center_1_new;
        }
        else return center_1;
    }
    public Vector2D trainCenter_2(ArrayList<Vector2D> cluster, Vector2D center_2){
        Vector2D cluster_center_2_new = center_2;
        for (int i = 0; i < cluster.size(); i++) {
            cluster_center_2_new = cluster_center_2_new.add(cluster.get(i));
        }
        if (cluster.size() != 0) {
            cluster_center_2_new.x /= cluster.size()+1;
            cluster_center_2_new.y /= cluster.size()+1;
            return cluster_center_2_new;
        }
        else return center_2;
    }
}
