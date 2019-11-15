package pendulum.graphics;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import pendulum.PendulumSimu;

public class MainWindowManager {
	
	private PathManager     pathManager;
	private PendulumManager pendManager;
	private ControlManager  contManager;
	
	private Group rootNode;
	private Scene scene;
	
	public MainWindowManager (PendulumSimu sim) {
		
		this.rootNode = new Group();
		this.pathManager = new PathManager(sim);
		this.pendManager = new PendulumManager(sim);
		this.contManager = new ControlManager(sim);
		
		this.rootNode.getChildren().addAll(this.pendManager.getPendulumGroup(), this.pathManager.getPathGroup(), this.contManager.getButtonGroup());
		this.scene = new Scene(rootNode, sim.getWinW(), sim.getWinH());
		this.scene.setFill(Color.DARKGRAY);
	}
	
	
	public PathManager getPathManager() {
		return this.pathManager;
	}

	public PendulumManager getPendManager() {
		return this.pendManager;
	}

	public ControlManager getContManager() {
		return this.contManager;
	}
	
	public Scene getScene () {
		return this.scene;
	}
	
}
