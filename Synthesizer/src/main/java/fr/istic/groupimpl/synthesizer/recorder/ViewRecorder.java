package fr.istic.groupimpl.synthesizer.recorder;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import fr.istic.groupimpl.synthesizer.component.ControllerComponent;
import fr.istic.groupimpl.synthesizer.component.ViewComponent;

/**
 * 
 * View of recorder module
 *  
 * @author Team GroupImpl
 */
public class ViewRecorder extends ViewComponent implements Initializable {

	/** The root module pane. */
	@FXML private Pane rootModulePane;
	
	/** The top. */
	@FXML private GridPane top;
	
	/** The input. */
	@FXML private ImageView input;
	
	/** The fx sample name. */
	@FXML private Label fxSampleName;
	
	/** The fx btn prepare. */
	@FXML private Button fxBtnPrepare;
	
	/** The fx btn start. */
	@FXML private Button fxBtnStart;
	
	/** The fx btn stop. */
	@FXML private Button fxBtnStop;
	
	/** The time. */
	@FXML private Label time;
	/** The chronometre timer. */
	private Timer chronometreTimer;
	
	/** The date start record. */
	private Date dateStartRecord;
	

	/** The controller. */
	private ControllerRecorder controller;

	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resource) {
		((Label) top.lookup("#titleModule")).setText("Recorder");
		
		// implementation of controller
		controller = new ControllerRecorder(this);
		
		addPort("player_input", input);		
		
		// Listener close module
		top.lookup("#closeModuleFx").addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			cleanup();			
			controller.handleViewClose();
			Pane parent = (Pane) rootModulePane.getParent();
			parent.getChildren().remove(rootModulePane);
		});
		
		fxBtnPrepare.setDisable(false);
   	 	fxBtnStart.setDisable(true);
   	 	fxBtnStop.setDisable(true);
	}

	/* (non-Javadoc)
	 * @see fr.istic.groupimpl.synthesizer.component.ViewComponent#getComponentRoot()
	 */
	@Override
	protected Pane getComponentRoot() {
		return rootModulePane;
	}

	/* (non-Javadoc)
	 * @see fr.istic.groupimpl.synthesizer.component.ViewComponent#getController()
	 */
	@Override
	protected ControllerComponent getController() {
		return controller;
	}

	/* (non-Javadoc)
	 * @see fr.istic.groupimpl.synthesizer.component.ViewComponent#getFilename()
	 */
	@Override
	public String getFilename() {
		return "fxml/recorder.fxml";
	}
	
	/**
	 * Handles the click on the start button.
	 */
	@FXML
	public void handleStartClicked() {
		fxBtnPrepare.setDisable(true);
   	 	fxBtnStart.setDisable(true);
   	 	fxBtnStop.setDisable(false);
		controller.handleViewStartClicked();
		dateStartRecord = new Date();
		chronometreTimer.scheduleAtFixedRate(new ChronometreTimer(), 0, 1000);
	}

	/**
	 * Handles the click on the stop button.
	 */
	@FXML
	public void handleStopClicked() {
		fxBtnPrepare.setDisable(false);
   	 	fxBtnStart.setDisable(true);
   	 	fxBtnStop.setDisable(true);
		controller.handleViewStopClicked();
		chronometreTimer.cancel();
	}
	
	/**
	 * Handles the click on the prepare button.
	 */
	@FXML
	public void handlePrepareClicked() {
	
		FileChooser fileChooser = new FileChooser();         
	     //Set extension filter
	     FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("music wave file (*.wav)", "*.wav");
	     fileChooser.getExtensionFilters().add(extFilter);
	     fileChooser.setInitialFileName(controller.getSampleFileName());
	     
	     //Show save file dialog
	     File file = fileChooser.showSaveDialog(null);
	     if(file != null){
	    	 controller.prepareFile(file.getAbsolutePath());	
	    	 chronometreTimer = new Timer();
	     }
   	 	fxBtnStart.setDisable(false);
	}
	
	
	/**
	 * Gets the fx sample name.
	 *
	 * @return the fx sample name
	 */
	public Label getFxSampleName() {
		return fxSampleName;
	}
	
	/**
	 * The Class ChronometreTimer.
	 */
	class ChronometreTimer extends TimerTask {

		/** The date format. */
		private SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.TimerTask#run()
		 */
		public void run() {
			long diff = (new Date().getTime() - dateStartRecord.getTime());
			setLabelTimer(diff);
		}

		/**
		 * Sets the label timer.
		 *
		 * @param diff
		 *            the new label timer
		 */
		private void setLabelTimer(long diff) {
			Date date = new Date();
			date.setTime(diff);
			Platform.runLater(() -> {
				time.setText(dateFormat.format(date));
			});
		}
	}
}
