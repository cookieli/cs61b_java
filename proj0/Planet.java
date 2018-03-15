public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    public static double G = 0.0000000000667;
    public Planet(double xP, double yP, double xV, double yV, double m, String img){
        this.xxPos       = xP;
        this.yyPos       = yP;
        this.xxVel       = xV;
        this.yyVel       = yV;
        this.mass        = m;
        this.imgFileName = img;
    }
    public Planet(Planet p) {
        this.xxPos = p.xxPos;
        this.yyPos = p.yyPos;
        this.xxVel = p.xxVel;
        this.yyVel = p.yyVel;
        this.mass  = p.mass;
        this.imgFileName = p.imgFileName;
    }
    public double calcDistance(Planet other) {
        double xxDis = this.xxPos - other.xxPos;
        double yyDis = this.yyPos - other.yyPos;
        double distance = xxDis*xxDis + yyDis*yyDis;
        return Math.pow(distance, 0.5);
    }
    public double calcForceExertedBy(Planet p) {
        double dis = this.calcDistance(p);
        double r2 = dis*dis;
        double F = this.G * this.mass * p.mass/r2;
        return F;
    }
    public double calcForceExertedByX(Planet p) {
        double F = calcForceExertedBy(p);
        double xxDis = p.xxPos - this.xxPos;
        double r = calcDistance(p);
        double FX = F * xxDis/r;
        return FX;
    }
    public double calcForceExertedByY(Planet p) {
        double F = calcForceExertedBy(p);
        double yyDis = p.yyPos - this.yyPos;
        double r = calcDistance(p);
        double FY = F * yyDis/r;
        return FY;
    }
    public double calcNetForceExertedByX(Planet[] allPlanets) {
        double FX;
        double FNetX = 0.0;
        for (int i = 0; i < allPlanets.length; i++) {
            if (!allPlanets[i].equals(this)) {
                FX = calcForceExertedByX(allPlanets[i]);
                FNetX += FX;
            }
        }
        return FNetX;
    }
    public double calcNetForceExertedByY(Planet[] allPlanets) {
        double FY;
        double FNetY = 0.0;
        for (int i = 0; i < allPlanets.length; i++) {
            if(!allPlanets[i].equals(this)) {
                FY = calcForceExertedByY(allPlanets[i]);
                FNetY += FY;
            }
        }
        return FNetY;
    }
    public void update(double dt, double fX, double fY) {
        double acc_x = fX/this.mass;
        double acc_y = fY/this.mass;
        double xVel = this.xxVel + dt * acc_x;
        double yVel = this.yyVel + dt * acc_y;
        this.xxVel = xVel;
        this.yyVel = yVel;
        this.xxPos = this.xxPos + dt * this.xxVel;
        this.yyPos = this.yyPos + dt * this.yyVel;
    }
    public void draw() {
        String file = "images/" + this.imgFileName;
        StdDraw.picture(this.xxPos, this.yyPos, file);
    }
}
