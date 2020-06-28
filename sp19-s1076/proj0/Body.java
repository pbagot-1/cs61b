import java.lang.*;

public class Body{
	private static final double g = 6.67e-11;
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;

	public Body(double xP, double yP, double xV,
              double yV, double m, String img) {
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

	public Body(Body b) {
		xxPos = b.xxPos;
		yyPos = b.yyPos;
		xxVel = b.xxVel;
		yyVel = b.yyVel;
		mass = b.mass;
		imgFileName = b.imgFileName;
	}

	public double calcDistance(Body a) {
		return Math.sqrt(Math.pow((this.xxPos - a.xxPos),2) + Math.pow((this.yyPos - a.yyPos),2));
	}

	public double calcForceExertedBy(Body a) {
		return (double) (g*a.mass*this.mass) / (Math.pow(this.calcDistance(a),2));
	}

	public double calcForceExertedByX(Body a) {
		return (this.calcForceExertedBy(a)*(a.xxPos-this.xxPos)) / this.calcDistance(a);
	}

	public double calcForceExertedByY(Body a) {
		return (this.calcForceExertedBy(a)*(a.yyPos-this.yyPos)) / this.calcDistance(a);
	}

	public double calcNetForceExertedByX(Body[] allBodies) {
		double totalX = 0;
		for (Body a : allBodies) {
			if (!this.equals(a)) {
				totalX += this.calcForceExertedByX(a);
			}
		}

		return totalX;
	}

	public double calcNetForceExertedByY(Body[] allBodies) {
		double totalY = 0;
		for (Body a : allBodies) {
			if (!this.equals(a)) {
				totalY += this.calcForceExertedByY(a);
			}
		}

		return totalY;
	}

	public void update(double dt, double fX, double fY) {
		double ax = fX/this.mass;
		double ay = fY/this.mass;
		this.xxVel = this.xxVel + dt*ax;
		this.yyVel = this.yyVel + dt*ay;
		this.xxPos = this.xxPos + dt*(this.xxVel);
		this.yyPos = this.yyPos  + dt*(this.yyVel);
	}

	public void draw() {
		StdDraw.picture(this.xxPos,this.yyPos,"images/"+imgFileName); 
	}


}