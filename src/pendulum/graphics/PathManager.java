package pendulum.graphics;

import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import pendulum.PendulumSimu;

public class PathManager {
	
	Group pathGroup;
	
	double scale;
	
	public PathManager (PendulumSimu sim) {
		this.pathGroup = new Group();
		this.pathGroup.setTranslateX(sim.getOriginX());
		this.pathGroup.setTranslateY(sim.getOriginY());
		this.scale = sim.getScale();
	}
	
	public Group getPathGroup () {
		return this.pathGroup;
	}
	
	public void addPoint (double x, double y) {
		Circle p = new Circle();
		p.setCenterX(x);
		p.setCenterY(y);
		p.setRadius(1);
		this.pathGroup.getChildren().add(p);
	}
	
	public void addLine (double x1, double y1, double x2, double y2) {
		Line l = new Line();
		l.setStartX(x1);
		l.setStartY(y1);
		l.setEndX(x2);
		l.setEndY(y2);
		this.pathGroup.getChildren().add(l);
	}
}
