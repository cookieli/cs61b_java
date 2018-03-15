public class NBody {
    
    public static double readRadius(String name) {
        In in = new In(name);
        int num = in.readInt();
        double radius = in.readDouble();
        return radius;
    }
    public static Planet[] readPlanets(String name) {
        In in = new In(name);
        int num = in.readInt();
        Planet[] planets = new Planet[num];
        double radius = in.readDouble();
        for(int i = 0; i < num; i ++) {
            double xP = in.readDouble();
            double yP = in.readDouble();
            double xV = in.readDouble();
            double yV = in.readDouble();
            double m  = in.readDouble();
            String img = in.readString();
            planets[i] = new Planet(xP, yP, xV, yV, m, img);
        }
        return planets;
    }
    public static void main(String[] args) {
        double time = 0.0;
        double T  = Double.parseDouble(args[0]);
        double dt =Double.parseDouble(args[1]);
        String filename = args[2];
        double radius = NBody.readRadius(filename);
        Planet[] planets = NBody.readPlanets(filename);
        StdDraw.setScale(-radius, radius);
        StdDraw.picture(0, 0, "images/starfield.jpg");
        for(int i = 0; i < planets.length; i++) {
            planets[i].draw();
        }
        while (time < T) {
            double[] xForces = new double[planets.length];
            double[] yForces = new double[planets.length];
            for (int i = 0; i < planets.length; i ++) {
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
            }
            for (int i = 0; i < planets.length; i++) {
                planets[i].update(dt, xForces[i], yForces[i]);
            }
            StdDraw.picture(0, 0, "images/starfield.jpg");
            for(int i = 0; i < planets.length; i++) {
                planets[i].draw();
            }
            StdDraw.show(10);
            time += dt;
        }
    }
}
