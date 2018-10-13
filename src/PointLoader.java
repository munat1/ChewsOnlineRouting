import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.io.File;

public class PointLoader {

    public String fileText = null;
    File file = new File("/Users/munat/Desktop/aktüelpg/test1/fancyrandom");

    public PointLoader(){
        try{
            this.fileText = readWholeFile(file);
        } catch(Exception e){
            System.out.println("ERROR neu");
        }
    }

    public String readWholeFile(File file) throws FileNotFoundException {
        String entireFileText = new Scanner(file)
                .useDelimiter("\\A").next();
        return (entireFileText);
    }

    public List<Vector2D> getPointsFromFile(String prefix) {
        String text = this.fileText;
        String result = text.substring(text.indexOf(prefix + "Start") + prefix.length() + 5 , text.indexOf(prefix + "End"));
        String lines[] = result.split("\\r?\\n");
        List<Vector2D> Vector2DSet = new ArrayList<>();

        for(int i = 1; i < lines.length; i += 2){
            Vector2D Vector2D = new Vector2D(Double.parseDouble(lines[i])+400.0 ,300.0- Double.parseDouble(lines[i+1]));
            Vector2DSet.add(Vector2D);
        }

        return Vector2DSet;
    }

    public List<Vector2D> getVertices() {
        List<Vector2D> nodes = getPointsFromFile("GraphNodes");
        //nodes.addAll(0, getVector2DsFromFile("HighwayNodes"));
        nodes.addAll(0, getPointsFromFile("RoutingNodes"));
        HashSet<String> hs = new HashSet<>();
        List<Vector2D> nodes_tmp = new ArrayList<>();
        for(Vector2D v : nodes) {
            if (!hs.contains(v.toString())) {
                nodes_tmp.add(v);
                hs.add(v.toString());
            }
        }
        return nodes_tmp;
    }
}
