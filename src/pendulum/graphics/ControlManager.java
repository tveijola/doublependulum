package pendulum.graphics;

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
import javafx.stage.Modality;
import javafx.stage.Stage;
import pendulum.PendulumSimu;

public class ControlManager {

	private Group buttonGroup;
	private Stage optScreen;

	public ControlManager (PendulumSimu sim) {

		this.buttonGroup = new Group();
		this.buttonGroup.setTranslateX(10);
		this.buttonGroup.setTranslateY(10);

		this.optScreen = createOptionsScreen(sim);

		Button optBtn   = createButton("Options", 0, 0);
		Button startBtn = createButton("Start",   0, 40);
		Button stopBtn  = createButton("Stop",    0, 80);
		Button resetBtn = createButton("Reset",   0, 120);

		setActionOptions(optBtn, sim);
		setActionStart(startBtn, sim);
		setActionStop(  stopBtn, sim);
		setActionReset(resetBtn, sim);

		buttonGroup.getChildren().addAll(optBtn, startBtn, stopBtn, resetBtn);

	}
	
	public Group getButtonGroup () {
		return this.buttonGroup;
	}

	private Button createButton(String lab, double x, double y) {
		Button btn = new Button(lab);
		btn.setLayoutX(x);
		btn.setLayoutY(y);
		return btn;
	}

	private void setActionOptions (Button btn, PendulumSimu sim) {
		btn.setOnAction(click -> {
			this.optScreen.show();
		});
	}

	private void setActionStart (Button btn, PendulumSimu sim) {
		btn.setOnAction(click -> {
			sim.resetPrevX2Y2();
			sim.getAnimatorTimer().start();
		});
	}

	private void setActionStop (Button btn, PendulumSimu sim) {
		btn.setOnAction(click -> {
			sim.getAnimatorTimer().stop();
		});
	}
	
	private void setActionReset (Button btn, PendulumSimu sim) {
		btn.setOnAction(click -> {
			
			sim.getAnimatorTimer().stop();
			Stage setStartAngles = new Stage();
			
			// Set modality
			setStartAngles.initModality(Modality.APPLICATION_MODAL);
			
			VBox getAngleBox = new VBox();
			
			Slider angle1 = createSlider(0, 2*Math.PI, Math.PI / 2);
			Slider angle2 = createSlider(0, 2*Math.PI, Math.PI);

			Button btn1 = new Button ("OK!");
			btn1.setOnAction(click1 -> {
				setStartAngles.close();
			});
			
			getAngleBox.getChildren().addAll(angle1, angle2, btn1);
			
			Scene sc = new Scene(getAngleBox);
			setStartAngles.setScene(sc);
			setStartAngles.showAndWait();
			
			sim.getMWM().getPathManager().getPathGroup().getChildren().clear();
			
			double [] s = {angle1.getValue(), angle2.getValue(), 0.0, 0.0};
			sim.getMWM().getPendManager().setPendulumPosition(s, sim);
			sim.setState(s);
			sim.setSolverState(s);
			sim.resetPrevX2Y2();
			
		});
	}
	
	private Slider createSlider (double sliderStart, double sliderEnd, double defValue) {
		Slider sl = new Slider(sliderStart, sliderEnd, defValue);
		sl.setMaxSize(300, 100);
		sl.setMinSize(300, 100);
		sl.setShowTickMarks(true);
		sl.setShowTickLabels(true);
		sl.setMajorTickUnit(Math.PI / 4);
		sl.setBlockIncrement(Math.PI / 16);
		return sl;
	}

	private Stage createOptionsScreen(PendulumSimu sim) {

		Stage oScreen = new Stage();

		// Set modality
		oScreen.initModality(Modality.APPLICATION_MODAL);

		//
		GridPane optionsScreen = new GridPane();
		optionsScreen.setPadding(new Insets(20, 20, 20, 20));
		optionsScreen.setVgap(10);
		optionsScreen.setHgap(70);

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


		optionsScreen.add(gravityCaption, 0, 0);
		optionsScreen.add(dampFactorCaption, 0, 1);
		optionsScreen.add(r1Caption, 0, 2);
		optionsScreen.add(r2Caption, 0, 3);
		optionsScreen.add(m1Caption, 0, 4);
		optionsScreen.add(m2Caption, 0, 5);

		optionsScreen.add(gravitySlider, 1, 0);
		optionsScreen.add(dampFactorSlider, 1, 1);
		optionsScreen.add(r1Slider, 1, 2);
		optionsScreen.add(r2Slider, 1, 3);
		optionsScreen.add(m1Slider, 1, 4);
		optionsScreen.add(m2Slider, 1, 5);

		optionsScreen.add(gravityValue, 2, 0);
		optionsScreen.add(dampFactorValue, 2, 1);
		optionsScreen.add(r1Value, 2, 2);
		optionsScreen.add(r2Value, 2, 3);
		optionsScreen.add(m1Value, 2, 4);
		optionsScreen.add(m2Value, 2, 5);

		// Show the options window
		Scene optionScene = new Scene(optionsScreen);
		oScreen.setScene(optionScene);

		return oScreen;
	}
}
