/*

package pendulum;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainWindowManager_OLD {

	private PathManager pathManager;
	private PendulumManager pendManager;
	
	private Group  root;
	private Button btn1;
	private Stage optScreen;
	private GridPane optionsScreen;
	private Circle circle1;
	private Circle circle2;
	private Line   line1;
	private Line   line2;
	private Scene  scene;
	private Button strBtn;
	private Button stpBtn;
	private Button resBtn;

	public MainWindowManager (double win_w, double win_h, 
			double ang1,  double ang2,
			double r1,    double r2,
			double m1,    double m2,
			PendulumSimu sim) {

		this.root   = new Group();

		double x1 = win_w / 2 + r1 * Math.sin(ang1) * 100;
		double y1 = 200 + r1 * Math.cos(ang1) * 100;

		double x2 = x1 + r2 * Math.sin(ang2) * 100;
		double y2 = y1 + r2 * Math.cos(ang2) * 100;

		this.circle1 = createWeight(x1, y1, m1);
		this.line1   = createRod(win_w / 2, 200, x1, y1);

		this.circle2 = createWeight(x2, y2, m2);
		this.line2   = createRod(x1, y1, x2, y2);

		this.btn1 = new Button("options");
		this.btn1.setLayoutX(0); this.btn1.setLayoutY(0);
		this.strBtn = new Button("Start");
		this.strBtn.setLayoutX(100); this.strBtn.setLayoutY(0);
		this.stpBtn = new Button("Stop");
		this.stpBtn.setLayoutX(200); this.stpBtn.setLayoutY(0);
		this.resBtn = new Button("Reset");
		this.resBtn.setLayoutX(300); this.resBtn.setLayoutY(0);
		
		this.btn1.setOnAction(click -> {
			this.optScreen.show();
		});
		this.strBtn.setOnAction(click -> {
			sim.animator.start();
		});
		this.stpBtn.setOnAction(click -> {
			sim.animator.stop();
		});
		this.resBtn.setOnAction(click -> {
			
			
			Stage setStartAngles = new Stage();
			VBox root = new VBox();
			
			Slider angle1 = new Slider(0, 2*Math.PI, Math.PI / 2);
			angle1.setMaxSize(300, 100);
			angle1.setMinSize(300, 100);
			angle1.setShowTickMarks(true);
			angle1.setShowTickLabels(true);
			angle1.setMajorTickUnit(Math.PI / 4);
			angle1.setBlockIncrement(Math.PI / 16);
			
			Slider angle2 = new Slider(0, 2*Math.PI, Math.PI);
			angle2.setMaxSize(300, 100);
			angle2.setMinSize(300, 100);
			angle2.setShowTickMarks(true);
			angle2.setShowTickLabels(true);
			angle2.setMajorTickUnit(Math.PI / 4);
			angle2.setBlockIncrement(Math.PI / 16);
			
			Button btn = new Button ("OK");
			btn.setOnAction(click1 -> {
				setStartAngles.close();
			});
			
			root.getChildren().addAll(angle1, angle2, btn);
			
			Scene sc = new Scene(root);
			setStartAngles.setScene(sc);
			setStartAngles.showAndWait();
			
			double [] s = {angle1.getValue(), angle2.getValue(), 0.0, 0.0};
			sim.setState(s);
			sim.setSolverState(s);
		});
		
		this.optScreen = new Stage();
		this.createOptionScreen(sim);

		Line lineVertCenter = createRod(600, 0, 600, 200);
		Line lineHoriCenter = createRod(0, 400, 1200, 400);
		lineVertCenter.setFill(Color.LIGHTGRAY);
		lineHoriCenter.setFill(Color.LIGHTGRAY);

		root.getChildren().addAll(btn1, strBtn, stpBtn, resBtn, circle1, line1, circle2, line2, lineVertCenter);

		this.scene = new Scene(root, win_w, win_h);

	}

	public PathManager getPathManager () {
		return this.pathManager;
	}
	public PendulumManager getPendulumManager () {
		return this.pendManager;
	}
	
	
	public Group getRoot () {
		return this.root;
	}

	public Circle getCircle1 () {
		return this.circle1;
	}

	public Circle getCircle2 () {
		return this.circle2;
	}

	public Line getLine1 () {
		return this.line1;
	}

	public Line getLine2 () {
		return this.line2;
	}

	public Scene getScene () {
		return this.scene;
	}

	private Circle createWeight (double x, double y, double mass) {
		Circle circle = new Circle();
		circle.setCenterX(x);
		circle.setCenterY(y);
		circle.setRadius(mass);
		circle.setFill(Color.BLUE);
		return circle;
	}

	private Line createRod (double pivotX, double pivotY, double x, double y) {
		Line line = new Line();
		line.setStartX(pivotX);
		line.setStartY(pivotY);
		line.setEndX(x);
		line.setEndY(y);
		return line;
	}













	private void createOptionScreen (PendulumSimu sim) {

		// Set modality
		this.optScreen.initModality(Modality.APPLICATION_MODAL);

		//
		this.optionsScreen = new GridPane();
		this.optionsScreen.setPadding(new Insets(10, 10, 10, 10));
		this.optionsScreen.setVgap(10);
		this.optionsScreen.setHgap(70);

		// Create option sliders
		Slider gravitySlider = new Slider(0, 25, 9.81);
		gravitySlider.setId("g");
		gravitySlider.setShowTickMarks(true);
		gravitySlider.setShowTickLabels(true);
		gravitySlider.setMajorTickUnit(5);
		gravitySlider.setBlockIncrement(0.1);

		Slider dampFactorSlider = new Slider(0, 1, 0);
		dampFactorSlider.setId("dampFactor");
		dampFactorSlider.setShowTickMarks(true);
		dampFactorSlider.setShowTickLabels(true);
		dampFactorSlider.setMajorTickUnit(0.2);
		dampFactorSlider.setBlockIncrement(0.01);

		Slider r1Slider = new Slider(0.1, 5, 4);
		r1Slider.setId("r1");
		r1Slider.setShowTickMarks(true);
		r1Slider.setShowTickLabels(true);
		r1Slider.setMajorTickUnit(1);
		r1Slider.setBlockIncrement(0.1);

		Slider r2Slider = new Slider(0.1, 5, 2);
		r2Slider.setId("r2");
		r2Slider.setShowTickMarks(true);
		r2Slider.setShowTickLabels(true);
		r2Slider.setMajorTickUnit(1);
		r2Slider.setBlockIncrement(0.1);

		Slider m1Slider = new Slider(0.1, 25, 4);
		m1Slider.setId("m1");
		m1Slider.setShowTickMarks(true);
		m1Slider.setShowTickLabels(true);
		m1Slider.setMajorTickUnit(5);
		m1Slider.setBlockIncrement(0.1);

		Slider m2Slider = new Slider(0.1, 25, 3);
		m2Slider.setId("m2");
		m2Slider.setShowTickMarks(true);
		m2Slider.setShowTickLabels(true);
		m2Slider.setMajorTickUnit(5);
		m2Slider.setBlockIncrement(0.1);

		// CAPTIONS 

		Label gravityCaption = new Label("Gravity");
		Label dampFactorCaption = new Label("Damp Factor");
		Label r1Caption = new Label("Rod 1 Length");
		Label r2Caption = new Label("Rod 2 Length");
		Label m1Caption = new Label("Weight 1 Mass");
		Label m2Caption = new Label("Weight 2 Mass");

		Label gravityValue = new Label(Double.toString(gravitySlider.getValue()));
		Label dampFactorValue = new Label(Double.toString(dampFactorSlider.getValue()));
		Label r1Value = new Label(Double.toString(r1Slider.getValue()));
		Label r2Value = new Label(Double.toString(r2Slider.getValue()));
		Label m1Value = new Label(Double.toString(m1Slider.getValue()));
		Label m2Value = new Label(Double.toString(m2Slider.getValue()));

		gravitySlider.valueProperty().addListener(new ChangeListener<Number> () {
			public void changed (ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				sim.setGravity(new_val.doubleValue());
				gravityValue.setText(String.format("%.2f", new_val));
			}
		});

		dampFactorSlider.valueProperty().addListener(new ChangeListener<Number> () {
			public void changed (ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				sim.setDampFactor(new_val.doubleValue());
				dampFactorValue.setText(String.format("%.2f", new_val));
			}
		});

		r1Slider.valueProperty().addListener(new ChangeListener<Number> () {
			public void changed (ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				sim.setR1(new_val.doubleValue());
				r1Value.setText(String.format("%.2f", new_val));
			}
		});
		
		r2Slider.valueProperty().addListener(new ChangeListener<Number> () {
			public void changed (ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				sim.setR2(new_val.doubleValue());
				r2Value.setText(String.format("%.2f", new_val));
			}
		});
		
		m1Slider.valueProperty().addListener(new ChangeListener<Number> () {
			public void changed (ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				sim.setM1(new_val.doubleValue());
				m1Value.setText(String.format("%.2f", new_val));
			}
		});
		
		m2Slider.valueProperty().addListener(new ChangeListener<Number> () {
			public void changed (ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				sim.setM2(new_val.doubleValue());
				m2Value.setText(String.format("%.2f", new_val));
			}
		});


		this.optionsScreen.add(gravityCaption, 0, 0);
		this.optionsScreen.add(dampFactorCaption, 0, 1);
		this.optionsScreen.add(r1Caption, 0, 2);
		this.optionsScreen.add(r2Caption, 0, 3);
		this.optionsScreen.add(m1Caption, 0, 4);
		this.optionsScreen.add(m2Caption, 0, 5);

		this.optionsScreen.add(gravitySlider, 1, 0);
		this.optionsScreen.add(dampFactorSlider, 1, 1);
		this.optionsScreen.add(r1Slider, 1, 2);
		this.optionsScreen.add(r2Slider, 1, 3);
		this.optionsScreen.add(m1Slider, 1, 4);
		this.optionsScreen.add(m2Slider, 1, 5);

		this.optionsScreen.add(gravityValue, 2, 0);
		this.optionsScreen.add(dampFactorValue, 2, 1);
		this.optionsScreen.add(r1Value, 2, 2);
		this.optionsScreen.add(r2Value, 2, 3);
		this.optionsScreen.add(m1Value, 2, 4);
		this.optionsScreen.add(m2Value, 2, 5);

		// Show the options window
		Scene optionScene = new Scene(this.optionsScreen);
		optScreen.setScene(optionScene);
	}

}
*/