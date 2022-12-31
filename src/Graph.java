import javax.swing.*;
import java.awt.*;
import java.net.URI;

public class Graph extends JFrame {


    public Graph() {
        super("Graph");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        var p0 = new int[]{200, 200};
        var p1 = new int[]{400, 500};
        var p2 = new int[]{600, 200};


        //add a polygon to the panel

        var xPoints = new int[]{p0[0], p1[0], p2[0]};
        var yPoints = new int[]{p0[1], p1[1], p2[1]};
        var polygon = new Polygon(xPoints, yPoints, 3);
        var panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.RED);
                g.drawPolygon(subdivide(polygon));
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(800, 600);
            }
        };

        add(panel);
        pack();
        setVisible(true);
    }


    public static void main(String[] args) {
        new Graph();
    }


    public static Polygon subdivide(Polygon polygon) {

        var p0 = new Vector2(polygon.xpoints[0], polygon.ypoints[0]);
        var p1 = new Vector2(polygon.xpoints[1], polygon.ypoints[1]);
        var p2 = new Vector2(polygon.xpoints[2], polygon.ypoints[2]);

        //length
        var length = Vector2.distance(p0, p1);
        var thirdLength = length / 3;


        //Calculation of the left triangle
        //add 1/3 of the length to p0
        var p011 = p0.add(p1.subtract(p0).normalize().multiply(length / 3));
        //add 2/3 of the length to the vector from p0 to p1
        var p012 = p0.add(p1.subtract(p0).normalize().multiply(length * 2 / 3));
        var center1 = p011.centerBeween(p012);
        var ortho1 = center1.orthogonalVector(p011).normalize().multiply(Vector2.height(thirdLength));
        var p013 = center1.add(ortho1);

        //Calculation of the right triangle
        //add 1/3 of the length to p1

        return null;

    }


    static class Vector2 {
        public final double x;
        public final double y;

        public Vector2(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public Vector2 invert() {
            return new Vector2(-x, -y);
        }

        public static double distance(Vector2 p0, Vector2 p1) {
            return Math.sqrt(Math.pow(p1.x - p0.x, 2) + Math.pow(p1.y - p0.y, 2));
        }

        public Vector2 add(Vector2 other) {
            return new Vector2(x + other.x, y + other.y);
        }

        public double length() {
            return Math.sqrt(x * x + y * y);
        }

        public Vector2 normalize() {
            double len = length();
            return new Vector2(x / len, y / len);
        }

        public Vector2 centerBeween(Vector2 other) {
            return new Vector2((x + other.x) / 2, (y + other.y) / 2);
        }

        public Vector2 subtract(Vector2 p0) {
            return new Vector2(x - p0.x, y - p0.y);
        }

        public Vector2 orthogonalVector(Vector2 p0) {
            return new Vector2(p0.y - y, x - p0.x);
        }

        public Vector2 multiply(double v) {
            return new Vector2(x * v, y * v);
        }

        @Override
        public String toString() {
            return "Vector2{" + "x=" + x + ", y=" + y + '}';
        }

        public static double height(double length) {
            //calculate the height of a equally sided triangle with the given length of the sides
            return (length / 2) * Math.sqrt(3);
        }
    }
}