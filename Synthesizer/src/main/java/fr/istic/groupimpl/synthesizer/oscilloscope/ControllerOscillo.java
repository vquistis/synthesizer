package fr.istic.groupimpl.synthesizer.oscilloscope;

import javafx.beans.property.DoubleProperty;
import fr.istic.groupimpl.synthesizer.component.IControllerComponent;
import fr.istic.groupimpl.synthesizer.global.ControllerGlobal;
import fr.istic.groupimpl.synthesizer.util.Oscilloscope;

public class ControllerOscillo  implements IControllerComponent
{
	
	private ModelOscillo model = new ModelOscillo(Oscilloscope.SIZE_BUFFER_READ);
	private Oscilloscope scope;
	
	public ControllerOscillo(Oscilloscope scope) {
		this.scope = scope;
		ControllerGlobal.getInstance().registerUnitGenerator(model.getUnitGenerator());
	}
	
	/**
	 * Click listener for input port
	 * @param xCoord Coordinate of the clicked input port (x axis)
	 * @param yCoord Coordinate of the clicked input port (y axis)
	 */
	public void handleViewInputClick(DoubleProperty xCoord, DoubleProperty yCoord) {
		ControllerGlobal.getInstance().handleInputClicked(model.getInputPort(), xCoord, yCoord);
		
	}

	/**
	 * Click listener for output port
	 * @param xCoord Coordinate of the clicked output port (x axis)
	 * @param yCoord Coordinate of the clicked output port (y axis)
	 */
	public void handleViewOutputClick(DoubleProperty xCoord, DoubleProperty yCoord) {
		ControllerGlobal.getInstance().handleOutputClicked(model.getOutputPort(), xCoord, yCoord);
		
	}

	@Override
	public void handleViewClose() {
		scope.stop(); // pour arreter le thread
		ControllerGlobal.getInstance().removeAllConnections(model.getAllPorts());
		ControllerGlobal.getInstance().unregisterUnitGenerator(model.getUnitGenerator());		
	}
	
	/**
	 * méthode qui transmet les données venant du model
	 * @return 
	 * 	Le buffer de données
	 */
	public double [] getbufferData()
	{
		return model.getBuffer();
	}


	/**
	 * Methode appellé sur un changement de valeur 
	 * @param newVal
	 * 		nouvelle valeur
	 */
	public void handleRefreshPeriodViewChange(Number newVal) {
		scope.setRefreshPeriod((Double)newVal);
	}

}
