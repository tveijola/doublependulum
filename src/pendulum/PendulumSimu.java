package pendulum;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import pendulum.graphics.MainWindowManager;

public class PendulumSimu extends Application {
	
	private final double WIN_W = 1000;
	private final double WIN_H = 1000;
	
	private final double ox = WIN_W / 2;
	private final double oy = WIN_H / 2 - 100;
	
	private final double h  = 0.01;
	private final double scale = 50;
	
	private double r1 = 3;
	private double r2 = 2;

	private double m1 = 4;
	private double m2 = 3;
	
	private double g = 9.81;
	private double dampFactor = 0;
	
	private double iniAng1 = Math.PI / 2;
	private double iniAng2 = Math.PI - 0.001;
	
	private double ang1 = Math.PI / 2;
	private double ang2 = Math.PI;
	
	private double prevX2;
	private double prevY2;
	
	private double [] state;
	private Runge4 diffsolver;
	
	private MainWindowManager mwm;
	private AnimationTimer animator;
	
	public PendulumSimu() {

	}
	
	public static void main (String args[]) {
		launch(args);
	}
	
	public void start (Stage stage) {
		
		PendulumSimu sim = new PendulumSimu();
		sim.mwm = new MainWindowManager(sim);
		
		stage.setScene(sim.mwm.getScene());
		stage.setTitle("Pendulum");
		stage.show();
		
		sim.state = new double[] {iniAng1, iniAng2, 0.0, 0.0};
		sim.diffsolver = new Runge4(sim.h, sim.state);
		
		sim.animator = new AnimationTimer () {
			
			double gravity;	double damping;
			double bob2_x;	double bob2_y;
			double mBob1;	double mBob2;
			double rodLen1;	double rodLen2;
			
			public void handle (long arg0) {
				
				gravity = sim.g;
				damping = 1 - 0.05 * h * sim.dampFactor;
				rodLen1 = sim.r1;
				rodLen2 = sim.r2;
				mBob1 = sim.m1;
				mBob2 = sim.m2;
				
				sim.getMWM().getPendManager().setBobSize(mBob1, mBob2);
				
				sim.diffsolver.updateState(gravity, mBob1, mBob2, rodLen1, rodLen2, damping);
				ang1      = sim.diffsolver.getState()[0];
				ang2      = sim.diffsolver.getState()[1];
				
				bob2_x = rodLen1 * Math.sin(ang1) * sim.scale + rodLen2 * Math.sin(ang2) * sim.scale;
				bob2_y = rodLen1 * Math.cos(ang1) * sim.scale + rodLen2 * Math.cos(ang2) * sim.scale;
				
				sim.getMWM().getPendManager().setPendulumPosition(sim.diffsolver.getState(), sim);
				sim.getMWM().getPathManager().addLine(sim.prevX2, sim.prevY2, bob2_x, bob2_y);
				// sim.getMWM().getPathManager().addPoint(x2, y2);
				
				sim.setPrevX2Y2(bob2_x, bob2_y);
			    
			}
			
		};
	}
	
	public double getScale () {
		return this.scale;
	}
	
	public double getOriginX () {
		return this.ox;
	}
	
	public double getOriginY () {
		return this.oy;
	}
	
	public double getWinW () {
		return this.WIN_W;
	}
	
	public double getWinH () {
		return this.WIN_H;
	}
	
	public double getR1 () {
		return this.r1;
	}
	
	public double getR2 () {
		return this.r2;
	}
	
	public double getM1 () {
		return this.m1;
	}
	
	public double getM2 () {
		return this.m2;
	}
	
	public double getAngle1 () {
		return this.ang1;
	}
	
	public double getAngle2 () {
		return this.ang2;
	}
	
	public AnimationTimer getAnimatorTimer () {
		return this.animator;
	}
	
	public MainWindowManager getMWM () {
		return this.mwm;
	}
	
	public void setState (double [] s) {
		this.state = s;
	}
	
	public void setSolverState (double [] s) {
		this.diffsolver.setState(s);
	}
	
	public void setGravity (double val) {
		this.g = val;
	}
	
	public void setDampFactor (double val) {
		this.dampFactor = val;
	}
	
	public void setR1 (double val) {
		this.r1 = val;
	}
	
	public void setR2 (double val) {
		this.r2 = val;
	}
	
	public void setM1 (double val) {
		this.m1 = val;
	}
	
	public void setM2 (double val) {
		this.m2 = val;
	}
	
	public void resetPrevX2Y2 () {
		this.prevX2 = this.r1 * Math.sin(this.state[0]) * this.scale + this.r2 * Math.sin(this.state[1]) * this.scale;
		this.prevY2 = this.r1 * Math.cos(this.state[0]) * this.scale + this.r2 * Math.cos(this.state[1]) * this.scale;
	}
	
	public void setPrevX2Y2 (double x2, double y2) {
		this.prevX2 = x2;
		this.prevY2 = y2;
	}
	
	
}
