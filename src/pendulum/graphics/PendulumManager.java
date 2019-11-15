package pendulum.graphics;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import pendulum.PendulumSimu;

public class PendulumManager {
	
	private Group  pendulumGroup;
	
	private Circle bob1;
	private Circle bob2;
	private Line   rod1;
	private Line   rod2;
	private Line   pivot;
	
	public PendulumManager (PendulumSimu sim) {
		
		this.pendulumGroup = new Group();
		
		double scale = sim.getScale();
		
		double ox = sim.getOriginX();
		double oy = sim.getOriginY();
		
		double x1 = sim.getR1() * Math.sin(sim.getAngle1()) * scale;
		double y1 = sim.getR1() * Math.cos(sim.getAngle1()) * scale;
		
		double x2 = x1 + sim.getR2() * Math.sin(sim.getAngle2()) * scale;
		double y2 = y1 + sim.getR2() * Math.cos(sim.getAngle2()) * scale;

		this.pivot = createRod(ox, 0, 0, 0, 0, oy);
		
		this.bob1 = createWeight(ox, oy, x1, y1, sim.getM1());
		this.rod1 = createRod(ox, oy, 0, 0, x1, y1);

		this.bob2 = createWeight(ox, oy, x2, y2, sim.getM2());
		this.rod2 = createRod(ox, oy, x1, y1, x2, y2);
		
		pendulumGroup.getChildren().addAll(this.pivot, this.bob1, this.bob2, this.rod1, this.rod2);
		
	}
	
	public Group getPendulumGroup () {
		return this.pendulumGroup;
	}
	
	public void setPendulumPosition (double [] state, PendulumSimu sim) {
		
		double scale = sim.getScale();
		double angle1 = state[0];
		double angle2 = state[1];
		
		double x1 = sim.getR1() * Math.sin(angle1) * scale;
		double y1 = sim.getR1() * Math.cos(angle1) * scale;
		double x2 = x1 + sim.getR2() * Math.sin(angle2) * scale;
		double y2 = y1 + sim.getR2() * Math.cos(angle2) * scale;
		
		setBobPos(this.bob1, x1, y1);
		setBobPos(this.bob2, x2, y2);
		setRodPos(this.rod1, 0,  0,  x1, y1);
		setRodPos(this.rod2, x1, y1, x2, y2);
		
	}
	
	public void setBobSize (double m1, double m2) {
		this.bob1.setRadius(m1);
		this.bob2.setRadius(m2);
	}
	
	private Circle createWeight (double ox, double oy, double x, double y, double mass) {
		Circle circle = new Circle();
		circle.setTranslateX(ox);
		circle.setTranslateY(oy);
		circle.setCenterX(x);
		circle.setCenterY(y);
		circle.setRadius(mass);
		circle.setFill(Color.BLUE);
		return circle;
	}

	private Line createRod (double ox, double oy, double x1, double y1, double x2, double y2) {
		Line line = new Line();
		line.setTranslateX(ox);
		line.setTranslateY(oy);
		line.setStartX(x1);
		line.setStartY(y1);
		line.setEndX(x2);
		line.setEndY(y2);
		return line;
	}
	
	private void setBobPos (Circle c, double x, double y) {
		c.setCenterX(x);
		c.setCenterY(y);
	}
	
	private void setRodPos (Line l, double x1, double y1, double x2, double y2) {
		l.setStartX(x1);
		l.setStartY(y1);
		l.setEndX(x2);
		l.setEndY(y2);
	}
}
