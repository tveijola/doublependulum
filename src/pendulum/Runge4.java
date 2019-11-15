package pendulum;

public class Runge4 {
	
	private double stepsize;
	private double [] state;
	private int dim;
	
	public Runge4 (double h, double [] initialState) {
		this.stepsize = h;
		this.state    = initialState;
		this.dim      = initialState.length;
	}

	public double getStepsize() {
		return this.stepsize;
	}
	
	public void setState (double [] s) {
		this.state = s;
	}

	public double [] getState() {
		return this.state;
	}
	
	public void updateState (double g, double m1, double m2, double l1, double l2, double damp) {
		
		int    n = this.dim;
		double h = this.stepsize;
		double dampFactor = damp;
		
		double x1;
		double x2;
		double x3;
		double x4;
		
		double [] k1 = new double[n];
		double [] k2 = new double[n];
		double [] k3 = new double[n];
		double [] k4 = new double[n];
		
		x1 = this.state[0];
		x2 = this.state[1];
		x3 = this.state[2];
		x4 = this.state[3];
		
		k1[0] = x3;
		k1[1] = x4;
		k1[2] = omega1prime(g, m1, m2, l1, l2, x1, x2, x3, x4);
		k1[3] = omega2prime(g, m1, m2, l1, l2, x1, x2, x3, x4);
		
		x1 = this.state[0] + h / 2 * k1[0];
		x2 = this.state[1] + h / 2 * k1[1];
		x3 = this.state[2] + h / 2 * k1[2];
		x4 = this.state[3] + h / 2 * k1[3];
		
		k2[0] = x3;
		k2[1] = x4;
		k2[2] = omega1prime(g, m1, m2, l1, l2, x1, x2, x3, x4);
		k2[3] = omega2prime(g, m1, m2, l1, l2, x1, x2, x3, x4);
		
		x1 = this.state[0] + h / 2 * k2[0];
		x2 = this.state[1] + h / 2 * k2[1];
		x3 = this.state[2] + h / 2 * k2[2];
		x4 = this.state[3] + h / 2 * k2[3];
		
		k3[0] = x3;
		k3[1] = x4;
		k3[2] = omega1prime(g, m1, m2, l1, l2, x1, x2, x3, x4);
		k3[3] = omega2prime(g, m1, m2, l1, l2, x1, x2, x3, x4);
		
		x1 = this.state[0] + h * k3[0];
		x2 = this.state[1] + h * k3[1];
		x3 = this.state[2] + h * k3[2];
		x4 = this.state[3] + h * k3[3];
		
		k4[0] = x3;
		k4[1] = x4;
		k4[2] = omega1prime(g, m1, m2, l1, l2, x1, x2, x3, x4);
		k4[3] = omega2prime(g, m1, m2, l1, l2, x1, x2, x3, x4);
		
		this.state[0] += h / 6 * (k1[0] + 2*k2[0] + 2*k3[0] + k4[0]);
		this.state[1] += h / 6 * (k1[1] + 2*k2[1] + 2*k3[1] + k4[1]);
		this.state[2] += h / 6 * (k1[2] + 2*k2[2] + 2*k3[2] + k4[2]);
		this.state[3] += h / 6 * (k1[3] + 2*k2[3] + 2*k3[3] + k4[3]);
		
		this.state[2] *= dampFactor;
		this.state[3] *= dampFactor;
		
	}
	
	private double omega1prime (double g, double m1, double m2, double l1, double l2, double theta1, double theta2, double omega1, double omega2) {
		
		double a = -g * (2 * m1 + m2) * Math.sin(theta1);
		double b = -m2 * g * Math.sin(theta1 - 2 * theta2);
		double c = -2 * Math.sin(theta1 - theta2) * m2 * (omega2 * omega2 * l2 + omega1 * omega1 * l1 * Math.cos(theta1 - theta2));
		double d = l1 * (2 * m1 + m2 - m2 * Math.cos(2 * theta1 -2 * theta2));
		
		return (a + b + c) / d;
	}
	
	private double omega2prime (double g, double m1, double m2, double l1, double l2, double theta1, double theta2, double omega1, double omega2) {
		
		double a = 2 * Math.sin(theta1 - theta2);
		double b = omega1 * omega1 * l1 * (m1 + m2) + g * (m1 + m2) * Math.cos(theta1);
		double c = omega2 * omega2 * l2 * m2 * Math.cos(theta1 - theta2);
		double d = l2 * (2 * m1 + m2 - m2 * Math.cos(2 * theta1 -2 * theta2));
		
		return a * (b + c) / d;
	}

}
