import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;
import java.util.List;
public class kmeansnew {
    //ArrayList<Vector2D> cluster_1_new = new ArrayList<Vector2D>();
    //ArrayList<Vector2D> cluster_2_new = new ArrayList<Vector2D>();
    Netzwerk g = new Netzwerk();
    public Vector2D initializeCenter_1(Netzwerk g) {
        //Vector2D c_1 = g.knotenMenge.get(ThreadLocalRandom.current().nextInt(0, g.knotenMenge.size() ));
        Vector2D c_1 = new Vector2D(ThreadLocalRandom.current().nextDouble()*200+100, ThreadLocalRandom.current().nextDouble()*200+100);
        return c_1;
    }
    public Vector2D initializeCenter_2(Netzwerk g) {
        //Vector2D c_2 = g.knotenMenge.get(ThreadLocalRandom.current().nextInt(0, g.knotenMenge.size() ));
        Vector2D c_2 = new Vector2D(initializeCenter_1(g).x+100, initializeCenter_1(g).y+100);
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
        else return center_1; //burayi düzelt
    }
    public Vector2D trainCenter_2(ArrayList<Vector2D> cluster, Vector2D center_2){
        Vector2D cluster_center_2_new = center_2;
        for (int i = 0; i < cluster.size(); i++) {
            cluster_center_2_new = cluster_center_2_new.add(cluster.get(i));
        }
        //System.out.println(cluster_center_2_new);
        if (cluster.size() != 0) {
            cluster_center_2_new.x /= cluster.size()+1;
            cluster_center_2_new.y /= cluster.size()+1;
            return cluster_center_2_new;
        }
        else return center_2; //burayi düzelt
    }

        /*if (cluster_1_old.size() != 0) {

        if (cluster_2_old.size() != 0) {
            for (int i = 0; i < cluster_2_old.size(); i++) {
                cluster_center_2_new = cluster_2_old.get(i).add(cluster_center_2_new);
            }
            cluster_center_2_new.mult(1/cluster_2_old.size());
        }
        cluster_1_old.clear();
        cluster_2_old.clear();
        cluster_center_1_old = cluster_center_1_new;
        cluster_center_2_old = cluster_center_2_new;
    }
    public void trainCenters(Netzwerk g){
        while (cluster_center_1_old.distance(cluster_center_1_new) < 1.0 && cluster_center_2_old.distance(cluster_center_2_new) < 1.0) {
            buildClusters(g.knotenMenge, cluster_center_1_old, cluster_center_2_old);
        }
    }*/
}
