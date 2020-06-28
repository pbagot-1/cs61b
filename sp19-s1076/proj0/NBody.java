public class NBody {
	public static double readRadius(String fileName) {
		In in = new In(fileName);
		in.readInt();
		double radius = in.readDouble();
		return radius;
	}

	public static Body[] readBodies(String fileName) {
		In in = new In(fileName);
		int numPlanets = in.readInt();
		Body[] bodies = new Body[numPlanets];
		in.readDouble();
		for (int i = 0; i < numPlanets; i++) {
			bodies[i] = new Body(in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readString());
		}
		
		return bodies;
	}	

	public static void main(String[] args) {
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		Body[] bodies = NBody.readBodies(filename);
		double radius = NBody.readRadius(filename);
		StdDraw.enableDoubleBuffering();



		for (double time = 0; time <= T; time = time + dt) {
			double[] xForces;
			double[] yForces;
			//
			int counter = 0;
			double[] forcesX = new double[bodies.length];
			for (Body a: bodies) {
				forcesX[counter] = a.calcNetForceExertedByX(bodies);
				counter += 1;
			}
			xForces = forcesX;
			//
			int counter2 = 0;
			double[] forcesY = new double[bodies.length];
			for (Body a: bodies) {
				forcesY[counter2] = a.calcNetForceExertedByY(bodies);
				counter2 += 1;
			}
			yForces = forcesY;
			//
			int counter3 = 0;
			for (Body a : bodies) {
				a.update(dt,xForces[counter3],yForces[counter3]);
				counter3 += 1;
			}

			StdDraw.setScale(-radius,radius);
			StdDraw.picture(0,0,"images/starfield.jpg");
			for (Body a : bodies) {
				a.draw();
			}
			StdDraw.show();
			StdDraw.pause(10);
		}
		StdDraw.show();

		StdOut.printf("%d\n", bodies.length);
		StdOut.printf("%.2e\n", radius);
		for (int i = 0; i < bodies.length; i++) {
    	StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                  bodies[i].xxPos, bodies[i].yyPos, bodies[i].xxVel,
                  bodies[i].yyVel, bodies[i].mass, bodies[i].imgFileName);   
		}
	}
} 