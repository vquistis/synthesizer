package fr.istic.groupimpl.synthesizer.global;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Supplier;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import fr.istic.groupimpl.synthesizer.cable.Cable;
import fr.istic.groupimpl.synthesizer.component.ViewComponent;
import fr.istic.groupimpl.synthesizer.io.FileUtil;
import fr.istic.groupimpl.synthesizer.io.architecture.Configuration;
import fr.istic.groupimpl.synthesizer.io.architecture.Module;
import fr.istic.groupimpl.synthesizer.logger.Log;
import fr.istic.groupimpl.synthesizer.util.DebugJFXTools;

/**
 * The Class ViewGlobal.
 * Implements all the FXML components and sets them their corresponding commands.
 *
 * @author Team groupImpl
 * 
 */
public class ViewGlobal implements Initializable {

	/** The borderpane. */
	@FXML private BorderPane borderpane; 

	/** The contentpane. */
	@FXML private Pane contentpane;

	/** The scrollpane. */
	@FXML private ScrollPane scrollpane;

	/** The splitpane. */
	@FXML private SplitPane splitpane;

	/** The hb1. */
	@FXML private HBox hb1;

	/** The hb2. */
	@FXML private HBox hb2;

	/** The hb3. */
	@FXML private HBox hb3;

	/** The colorpicker. */
	@FXML private ColorPicker colorpicker; 
	
	/** The look and feel. */
	@FXML private ComboBox<String> lookAndFeel; 

	/** The mouse x. */
	private DoubleProperty mouseX = new SimpleDoubleProperty(0);

	/** The mouse y. */
	private DoubleProperty mouseY = new SimpleDoubleProperty(0);

	/** The ctl. */
	private ControllerGlobal ctl;

	/** The suppliers. */
	private List<Supplier<Module>> suppliers = new ArrayList<Supplier<Module>>();

	/** The stage. */
	private Stage stage;	
	
	/** The play. */
	@FXML
	private Button play;
	
	/** The chrono label. */
	@FXML
	private Label chronoLabel;
	
	/** The record. */
	@FXML
	private Button record;
	
	/** The stop. */
	@FXML
	private Button stop;
	
	/** The chronometre timer. */
	private Timer chronometreTimer;
	
	/** The date start record. */
	private Date dateStartRecord;
	
	/** The date format. */
	private SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");
	
	/** The record started. */
	private boolean recordStarted;

	/**
	 * Adds the cable.
	 *
	 * @param cable the cable
	 */
	public void addCable(Cable cable) {
		contentpane.getChildren().add(contentpane.getChildren().size()-1, cable);
		cable.setOnMouseClicked((event) -> {
			if(event.getButton() == MouseButton.PRIMARY) {
				ctl.handleClickOnCable(cable);
			}
		});
		cable.toFront();
	}

	/**
	 * Removes the cable.
	 *
	 * @param cable the cable
	 */
	public void removeCable(Cable cable) {
		contentpane.getChildren().remove(cable);
	}

	/**
	 * Initializes the controller class.
	 * This method is automatically called after the FXML file has been loaded. It creates a new view and set all the button with new created buttons.
	 *
	 * @param url the url
	 * @param resourceBundle the resourceBundle
	 */
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {

		ctl = ControllerGlobal.getInstance();
		splitpane.setDividerPositions(0.33f, 0.66f);
		splitpane.getDividers().forEach((div) -> {
			div.positionProperty().addListener((a,b,c) -> {
				hb1.requestLayout();
				hb2.requestLayout();
				hb3.requestLayout();
			});
		});

		splitpane.getItems().forEach((hb) -> {
			((HBox) hb).getChildren().addListener( new ListChangeListener<Node>() {
				@Override
				public void onChanged(javafx.collections.ListChangeListener.Change<? extends Node> c) {
					c.next();
					if (c.wasRemoved()) {
						if (c.getList().size()== 0) {
							splitpane.setDividerPositions(0.33f, 0.66f);
						}
					}
				};
			});
		});
		colorpicker.valueProperty().set(Color.BLUEVIOLET);

		contentpane.addEventFilter(MouseEvent.MOUSE_CLICKED, (event) -> {
			if(event.getButton() == MouseButton.SECONDARY) {
				event.consume();
				ctl.handleRightButtonClicked();
				contentpane.setCursor(Cursor.DEFAULT);
				for(Node n : contentpane.getChildren()) {
					if(n instanceof Cable) {
						n.setMouseTransparent(true);
					}
				}
			}
		});

		// automatic resizing
		scrollpane.widthProperty().addListener((obs, oldWidth, newWidth) -> {
			splitpane.setPrefWidth((double) newWidth);
		});
		scrollpane.heightProperty().addListener((obs, oldHeight, newHeight) -> {
			splitpane.setPrefHeight((double) newHeight);
		});

		for(Node n : splitpane.getItems()) {
			n.setOnDragOver((e) -> {
				e.acceptTransferModes(TransferMode.ANY);
				e.consume();
			});
			n.setOnDragDropped((e) -> {
				e.acceptTransferModes(TransferMode.ANY);
				e.consume();				
				Dragboard db = e.getDragboard();
				if (db.hasString()) {
					Log.getInstance().debug("DROP DONE");
					String []pos=db.getString().split(";");
					Node node = ((HBox)splitpane.getItems().get(Integer.parseInt(pos[0]))).getChildren().get(Integer.parseInt(pos[1]));
					HBox box=(HBox) n;
					((HBox)splitpane.getItems().get(Integer.parseInt(pos[0]))).getChildren().remove(Integer.parseInt(pos[1]));
					int index =0;
					for(Node child : box.getChildren()){	
						if (child.contains(e.getX(), e.getY())) {
							break;
						}
						index++;
					}
					box.getChildren().add(index,node);
				}
			});
		}

		splitpane.setOnMouseMoved((e) -> {
			mouseX.set(e.getX());
			mouseY.set(e.getY());
		});

		scrollpane.addEventFilter(DragEvent.ANY, (e) -> {
			double spXMin = scrollpane.getViewportBounds().getMinX();
			double spYMin = scrollpane.getViewportBounds().getMinY();
			double spXMax = scrollpane.getViewportBounds().getMaxX();
			double spYMax = scrollpane.getViewportBounds().getMaxY();
			double x = e.getX();
			double y = e.getY();

			if (y >= spYMin  && y <= (spYMin + 100)) {
				scrollpane.setVvalue(scrollpane.getVvalue() - 0.05);
			}

			if (y <= spYMax  && y >= (spYMax - 100)) {
				scrollpane.setVvalue(scrollpane.getVvalue() + 0.05);
			}

			if (x >= spXMin  && x <= (spXMin + 100)) {
				scrollpane.setHvalue(scrollpane.getHvalue() - 0.05);
			}

			if (x <= spXMax  && x >= (spXMax - 100)) {
				scrollpane.setHvalue(scrollpane.getHvalue() + 0.05);
			}

		});

		ctl.setView(this);
		record.setDisable(false);
		play.setDisable(true);
//		pause.setDisable(true);
		stop.setDisable(true);
		
		record.setGraphic(new ImageView("/img/record/record.png"));
		play.setGraphic(new ImageView("/img/record/play.png"));
		stop.setGraphic(new ImageView("/img/record/stop.png"));
//		pause.setGraphic(new ImageView("/img/record/pause.png"));
	}

	/**
	 * Inits the.
	 */
	public void init() {
		//primaryStage = (Stage) borderpane.getScene().getWindow();
		
		lookAndFeel.getItems().addAll("metal", "wood");
		lookAndFeel.getSelectionModel().selectFirst();
		lookAndFeel.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
			URL cssURL = getClass().getClassLoader().getResource("css/"+newVal+".css");
			stage.getScene().getStylesheets().clear();
			stage.getScene().getStylesheets().add(cssURL.toExternalForm());
		});
		// Default css
		URL cssURL = getClass().getClassLoader().getResource("css/metal.css");
		stage.getScene().getStylesheets().add(cssURL.toExternalForm());
		
		createModule("fxml/out.fxml");
	}

	/**
	 * Creates a new module.
	 *
	 * @param filename the filename component fxml
	 * @param module the module
	 * @return the view component
	 */

	/**
	 * Creates a new module.
	 *
	 * @param filename the filename component fxml
	 */
	public ViewComponent createModule(String filename, Module module) {
		ViewComponent res = null;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(filename));
			Node root = loader.load();
			ViewComponent view = loader.getController();			
			root.parentProperty().addListener((obs,oldVal,newVal) -> {
				if(oldVal != null) {
					hb1.heightProperty().removeListener(view.getListener());
					hb2.heightProperty().removeListener(view.getListener());
					hb3.heightProperty().removeListener(view.getListener());
				}
				if(newVal != null) {
					hb1.heightProperty().addListener(view.getListener());
					hb2.heightProperty().addListener(view.getListener());
					hb3.heightProperty().addListener(view.getListener());
				}
			});
			res = view;
			if (module != null) {
				HBox hb = (HBox) splitpane.getItems().get(module.getPosY());
				hb.getChildren().add(root);
				enableDrag(root);
				view.initComponent(module);
			} else {
				HBox hb = (HBox) splitpane.getItems().get(0);
				hb.getChildren().add(root);
				enableDrag(root);
			}			
			
			suppliers.add(view.getSaveSupplier());
			view.setOnCloseCmd(() -> {
				suppliers.remove(view.getSaveSupplier());
			});
		} catch (IOException e) {
			Log.getInstance().error("Creates a new module failed", e );
		}
		
		return res;
	}
	
	/**
	 * Creates the module.
	 *
	 * @param filename the filename
	 */
	public void createModule(String filename) {
		createModule(filename, null);
	}
	
	

	/**
	 * This method is used to return the position of a component in the container splitPane.
	 *
	 * @param node the node
	 * @return the string. position the component
	 */
	private String getString(Node node) {
		int i=0;
		String res = "";
		for(Node hbox : splitpane.getItems()) {
			ObservableList<Node> nodes = ((HBox) hbox).getChildren();
			if(nodes.contains(node)) {
				res = res + i + ";" + nodes.indexOf(node);
				break;
			}
			i++;
		}
		return res;
	}

	/**
	 * Add enableDrag event to a node.
	 * This event is called when the drag event is enabled
	 *
	 * @param node the node
	 */
	private void enableDrag(Node node) {
		node.setOnDragDetected((event) -> {
			ClipboardContent content = new ClipboardContent();
			content.putString(getString(node));
			Dragboard db = scrollpane.startDragAndDrop(TransferMode.ANY);
			db.setContent(content);
			event.consume();
		});
	}

	/**
	 * Gets the cable color.
	 *
	 * @return the cable color
	 */
	public Color getCableColor() {
		return colorpicker.getValue();
	}

	/**
	 * Handle add replicator. This method adds a new Rep component 
	 */
	@FXML
	public void handleAddRep(){
		createModule("fxml/rep.fxml");		
	}


	/**
	 * Handle add vco. This method adds a new VCO component
	 */
	@FXML
	public void handleAddVco(){
		createModule("fxml/vco.fxml");		
	}

	/**
	 * Handle add vca. This method adds a new VCA component
	 */
	@FXML
	public void handleAddVca(){
		createModule("fxml/vca.fxml");	
	}

	/**
	 * Handle add out. This method adds a new OUT component
	 */
	@FXML
	public void handleAddOut(){
		createModule("fxml/out.fxml");		
	}

	/**
	 * Handle add scope. This method adds a new Scope component
	 */
	@FXML
	public void handleAddScope(){
		createModule("fxml/oscillo.fxml");		
	}

	/**
	 * Handle add eg. This method adds a new EG component
	 */
	@FXML
	public void handleAddEg(){
		createModule("fxml/eg.fxml");		
	}

	/**
	 * Handle add vcf lp. This method adds a new VCF lp component
	 */
	@FXML
	public void handleAddVcfLp(){
		createModule("fxml/vcf-lp.fxml");		
	}
	
	/**
	 * Handle add vcf hp. This method adds a new VCF hp component
	 */
	@FXML
	public void handleAddVcfHp(){
		createModule("fxml/vcf-hp.fxml");		
	}
	
	/**
	 * Handle add mixer. This method adds a new Mixer component
	 */
	@FXML
	public void handleAddMixer4(){
		createModule("fxml/mixer4.fxml");		
	}

	/**
	 * Handle add mixer. This method adds a new Mixer component
	 */
	@FXML
	public void handleAddMixer2(){
		createModule("fxml/mixer2.fxml");		
	}
	
	/**
	 * Handle add whiteNoise. This method adds a new WhiteNoise component
	 */
	@FXML
	public void handleAddWhiteNoise(){
		createModule("fxml/whiteNoise.fxml");		
	}

	/**
	 * Handle add player. This method adds a new Player component
	 */
	@FXML
	public void handleAddPlayer(){
		createModule("fxml/player.fxml");		
	}

	/**
	 * Handle add recorder. This method adds a new Recorder component
	 */
	@FXML
	public void handleAddRecorder(){
		createModule("fxml/recorder.fxml");		
	}
	
	/**
	 * Handle add Line In. This method adds a new Line In component
	 */
	@FXML
	public void handleAddLineIn(){
		createModule("fxml/LineIn.fxml");		
	}

	/**
	 * Handle add sequencer. This method adds a new Sequencer component
	 */
	@FXML
	public void handleAddSeq(){
		createModule("fxml/seq.fxml");		
	}
	
	/**
	 * Handle add echo. This method adds a new Echo component
	 */
	@FXML
	public void handleAddEcho(){
		createModule("fxml/echo.fxml");		
	}
	
	/**
	 * Handle add sequencer. This method adds a new Sequencer component
	 */
	@FXML
	public void handleAddKeyboard(){
		createModule("fxml/keyboard.fxml");		
	}

	/**
	 * Mouse x property.
	 *
	 * @return the double property
	 */
	public DoubleProperty mouseXProperty() {
		return mouseX;
	}

	/**
	 * Mouse y property.
	 *
	 * @return the double property
	 */
	public DoubleProperty mouseYProperty() {
		return mouseY;
	}

	/**
	 * Handle delete.
	 */
	@FXML
	public void handleDelete() {
		ctl.activateDeletionMode();
	}


	/**
	 * Handle paint.
	 */
	@FXML
	public void handlePaint() {
		ctl.activatePaintingMode();
	}

	/**
	 * Enable cable deletion mode.
	 *
	 * @param enable the enable
	 */
	public void enableCableDeletionMode(boolean enable) {
		for(Node n : contentpane.getChildren()) {
			if(n instanceof Cable) {
				n.setMouseTransparent(!enable);
			} else {
				n.setMouseTransparent(enable);
			}
		}
		if(enable) {
			contentpane.setCursor(new ImageCursor(new Image("img/delete.png")));
		}
	}

	/**
	 * Enable cable creation mode.
	 *
	 * @param enable the enable
	 */
	public void enableCableCreationMode(boolean enable) {
		if(enable) {
			contentpane.setCursor(Cursor.DISAPPEAR);
		}
	}

	/**
	 * Enable default mode.
	 *
	 * @param enable the enable
	 */
	public void enableDefaultMode(boolean enable) {
		if(enable) {
			contentpane.setCursor(Cursor.DEFAULT);
		}
	}

	/**
	 * Enable cable painting mode.
	 *
	 * @param enable the enable
	 */
	public void enableCablePaintingMode(boolean enable) {
		for(Node n : contentpane.getChildren()) {
			if(n instanceof Cable) {
				n.setMouseTransparent(!enable);
			} else {
				n.setMouseTransparent(enable);
			}
		}
		if(enable) {
			contentpane.setCursor(Cursor.HAND);
		}
	}

	/**
	 * Handle menu devmode node hierarchy_1.
	 */
	@FXML
	public void handleMenuDevmodeNodeHierarchy_1() {
		DebugJFXTools debugJFXTools = new DebugJFXTools();
		debugJFXTools.GenerateNodeHierarchy(borderpane, "synthjfx_1.dmp");
	}
	
	/**
	 * Handle menu devmode node hierarchy_2.
	 */
	@FXML
	public void handleMenuDevmodeNodeHierarchy_2() {
		DebugJFXTools debugJFXTools = new DebugJFXTools();
		debugJFXTools.GenerateNodeHierarchy(borderpane, "synthjfx_2.dmp");
	}

	/**
	 * Handle save configuration.
	 */
	@FXML
	public void  handleSaveConfiguration(){
		 FileChooser fileChooser = new FileChooser();
		  
         //Set extension filter
         FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("synthlab file (*.sl)", "*.sl");
         fileChooser.getExtensionFilters().add(extFilter);
         
         
         if ( initialDirectory != null )
         {
        	 fileChooser.setInitialDirectory(initialDirectory);
         }
         //Show save file dialog
         File file = fileChooser.showSaveDialog(stage);
         
         if(file != null){
        	Configuration configuration = new Configuration();     		
     		configuration.setModules(getModules());
     		configuration.setConnections(ControllerGlobal.getInstance().getConnectionList());
     		FileUtil.saveFile(configuration, file);
     		initialDirectory = file.getParentFile();
         }	
	}

	/**
	 * Gets the modules.
	 *
	 * @return the modules
	 */
	public List<Module> getModules() {
		List<Module> list = new ArrayList<>();
		int index=0;
		for (Supplier<Module> supplier : suppliers) {
			Module module = supplier.get();
			module.setId("Module"+index);
			list.add(module);
			index++;
		}
		return list;
	}
	/**
	 * Handle load configuration.
	 */
	private File initialDirectory=null;
	@FXML
	public void handleLoadConfiguration(){
		 FileChooser fileChooser = new FileChooser();         
         //Set extension filter
         FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("synthlab file (*.sl)", "*.sl");
         fileChooser.getExtensionFilters().add(extFilter);
          
         Map<String,ViewComponent> views = new HashMap<>();
         
         if ( initialDirectory != null )
         {
        	 fileChooser.setInitialDirectory(initialDirectory);
         }
         
         //Show save file dialog
         File file = fileChooser.showOpenDialog(stage);
         if(file != null){
        	 	// traitement        	 
        	 clearAllComponent();        	 
        	 Configuration configuration = (Configuration) FileUtil.loadFile(file, Configuration.class);
        	 Collections.sort(configuration.getModules(), new Module());
        	 // configue component
        	 configuration.getModules().forEach((module) ->{
        		 views.put(module.getId(),createModule(module.getFilename(), module));
        	 });
        	 
        	 configuration.getConnections().forEach((connection) -> {
        		 Cable cable = new Cable(Color.valueOf(connection.getColor()));
        		 ViewComponent viewIn = views.get(connection.getInputPort().getIdModule());
        		 ViewComponent viewOut = views.get(connection.getOutputPort().getIdModule());
        		 ControllerGlobal.getInstance().createConnection(cable, viewIn.getStuff(connection.getInputPort().getName()),
        				 viewOut.getStuff(connection.getOutputPort().getName()), viewIn.getPort(connection.getInputPort().getName()),
        				 viewOut.getPort(connection.getOutputPort().getName()));
        	 });
        	 
        	 initialDirectory = file.getParentFile();
        	 
         }

	}

	/**
	 * Clear all component.
	 */
	private void clearAllComponent() {
		ControllerGlobal.getInstance().resetWorkbench();
		for (Node node : splitpane.getItems()) {
			((HBox) node).getChildren().clear();
		}
	}

	/**
	 * Sets the stage.
	 *
	 * @param primaryStage the new stage
	 */
	public void setStage(Stage primaryStage) {
		this.stage=primaryStage;
	}

	/**
	 * Gets the stage.
	 *
	 * @return the stage
	 */
	public Stage getStage() {
		return stage;
	}

	/**
	 * Gets the suppliers.
	 *
	 * @return the suppliers
	 */
	public List<Supplier<Module>> getSuppliers() {
		return suppliers;
	}

	/**
	 * Sets the suppliers.
	 *
	 * @param suppliers the new suppliers
	 */
	public void setSuppliers(List<Supplier<Module>> suppliers) {
		this.suppliers = suppliers;
	}
	
	
	/**
	 * Handle start record.
	 */
	public void handleStart(){
		ControllerGlobal.getInstance().handleStartRecordSynth(getAllModulesOut());
		setLabelTimer(0);
		dateStartRecord = new Date();
		chronometreTimer.scheduleAtFixedRate(new ChronometreTimer(), 0, 1000);
		play.setDisable(true);
		stop.setDisable(false);
		record.setDisable(true);
		recordStarted = true;
	}
	
	/**
	 * Handle stop record.
	 */
	public void handleStop(){
		ControllerGlobal.getInstance().handleStopRecordSynth();	
		chronometreTimer.cancel();
		play.setDisable(true);
		stop.setDisable(true);
		record.setDisable(false);
		recordStarted = false;
	}
	
	/**
	 * Handle record.
	 */
	public void handleRecord(){	
		try {
			FileChooser fileChooser = new FileChooser();         
		     //Set extension filter
		     FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("music wave file (*.wav)", "*.wav");
		     fileChooser.getExtensionFilters().add(extFilter);
		     
		     //Show save file dialog
		     File file = fileChooser.showSaveDialog(null);
		     if(file != null){
		    	ControllerGlobal.getInstance().handleCreateRecordSynth(file); 
		    	chronometreTimer = new Timer();
		    	setLabelTimer(0);
		    	play.setDisable(false);
		 		stop.setDisable(true);
		 		record.setDisable(false);
		 		recordStarted = false;
		     }			
			
		} catch (IOException e) {
			Log.getInstance().error("Failed to handle recording", e );
		}	
	}
	
	/**
	 * Gets the all modules out.
	 *
	 * @return the all modules out
	 */
	public List<Module> getAllModulesOut(){
		List<Module> list = new ArrayList<>();
		for (Module module : getModules()) {
			if ("fxml/out.fxml".equalsIgnoreCase(module.getFilename())) {
				list.add(module);
			}
		}		
		return list;
	}
	
	/**
	 * Sets the label timer.
	 *
	 * @param diff the new label timer
	 */
	private void setLabelTimer(long diff) {
		Date date = new Date();
		  date.setTime(diff);
		  Platform.runLater(()->{
			  chronoLabel.setText(dateFormat.format(date));
		  });
	}	
	
	/**
	 * The Class ChronometreTimer.
	 */
	class ChronometreTimer extends TimerTask {
	    
    	/* (non-Javadoc)
    	 * @see java.util.TimerTask#run()
    	 */
    	public void run() {
	      long diff = (new Date().getTime() - dateStartRecord.getTime());
	      setLabelTimer(diff);	    
	  }
	}

	/**
	 * Checks if is record started.
	 *
	 * @return true, if is record started
	 */
	public boolean isRecordStarted() {
		return recordStarted;
	}

	/**
	 * Sets the record started.
	 *
	 * @param recordStarted the new record started
	 */
	public void setRecordStarted(boolean recordStarted) {
		this.recordStarted = recordStarted;
	}
	
	
}
