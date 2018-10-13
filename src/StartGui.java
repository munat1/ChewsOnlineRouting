import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.shape.Line;
import javafx.scene.shape.Circle;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

public class StartGui extends Application {
  HBox basisEbene = new HBox();
  Pane ebenenContainer = new Pane();
  Pane knotenEbene = new Pane();
  Pane kantenEbene = new Pane();
  Scene scene = new Scene(basisEbene, 1100, 600);
  PointLoader points = new PointLoader();
  Netzwerk N = new Netzwerk();

  public static void main(String[] args) {
      launch(args);
  }

  public void start(Stage stage) {
    stage.setTitle("Benutzeroberfleache");
    stage.setScene(scene);
    basisEbene.getChildren().add(ebenenContainer);
    ebenenContainer.getChildren().add(knotenEbene);
    ebenenContainer.getChildren().add(kantenEbene);
    getKnoten();
    displayKnoten();
    N.clustering(knotenEbene);
    displayKanten();
    route();
    stage.show();
  }


  public void getKnoten() {
      for (int j = 0; j < points.getVertices().size(); j++) {
        N.addKnoten(points.getVertices().get(j));
      }
  }

  public void displayKnoten() {
    for (Vector2D v : N.knotenMenge) {
      Circle knoten = new Circle();
      knoten.setCenterX(v.x);
      knoten.setCenterY(v.y);
      knoten.setRadius(6.0);
      knotenEbene.getChildren().add(knoten);
    }
  }
  public void displayKanten(){
    N.triangulate();
    for (Vector2D v : N.knotenMenge) {
      for (Vector2D w : v.nachbarMenge) {
        Line l = new Line(v.x, v.y, w.x, w.y);
        l.setStrokeWidth(2);
        if (v.isHighway && w.isHighway){
          l.setStroke(Color.BLUE);
          l.setStrokeWidth(6);
        }
        kantenEbene.getChildren().add(l);
      }
    }
  }
  public void route(){
    Vector2D s = new Vector2D(0,0);
    Vector2D t = new Vector2D(0,0);
    for(Vector2D v: N.knotenMenge){
      if(points.getVertices().get(0).x == v.x && points.getVertices().get(0).y == v.y){
        s = v;
        System.out.println("s set");
        Circle knoten = new Circle();
        knoten.setCenterX(v.x);
        knoten.setCenterY(v.y);
        knoten.setRadius(6.0);
        knoten.setFill(Color.YELLOW);
        knotenEbene.getChildren().add(knoten);

      }
      if(points.getVertices().get(1).x == v.x && points.getVertices().get(1).y == v.y){
        t = v;
        System.out.println("t set");
        Circle knoten = new Circle();
        knoten.setCenterX(v.x);
        knoten.setCenterY(v.y);
        knoten.setRadius(6.0);
        knoten.setFill(Color.GREEN);
        knotenEbene.getChildren().add(knoten);

      }
    }
    Line li = new Line(s.x, s.y, t.x, t.y);
    li.setStroke(Color.GREEN);
    li.setStrokeWidth(1);
    kantenEbene.getChildren().add(li);
    ArrayList <Vector2D> route = N.Chew(s,t, kantenEbene);
    for (int i=0; i<route.size()-1; i++) {
      Line l = new Line(route.get(i).x, route.get(i).y, route.get(i + 1).x, route.get(i + 1).y);
      l.setStroke(Color.RED);
      l.setStrokeWidth(3);
      kantenEbene.getChildren().add(l);
    }
  }





}
