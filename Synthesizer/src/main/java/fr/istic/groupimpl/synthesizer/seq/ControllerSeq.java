package fr.istic.groupimpl.synthesizer.seq;

import javafx.beans.property.DoubleProperty;
import fr.istic.groupimpl.synthesizer.component.ControllerComponent;
import fr.istic.groupimpl.synthesizer.component.ModelComponent;
import fr.istic.groupimpl.synthesizer.global.ControllerGlobal;

public class ControllerSeq  extends ControllerComponent
{
	static final int NB_BUTTONS=8;
	
	private final ModelSeq model = new ModelSeq(NB_BUTTONS);
	
	public ControllerSeq() {	
		
		ControllerGlobal.getInstance().registerUnitGenerator(model.getUnitGenerator());
	}
	
	/**
	 * Click listener for gate port
	 * @param xCoord Coordinate of the clicked gate port (x axis)
	 * @param yCoord Coordinate of the clicked gate port (y axis)
	 */
	public void handleViewGateClick(DoubleProperty xCoord, DoubleProperty yCoord) {
		ControllerGlobal.getInstance().handleInputClicked(model.getGatePort(), xCoord, yCoord);
		
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
		ControllerGlobal.getInstance().removeAllConnections(model.getAllPorts());
		ControllerGlobal.getInstance().unregisterUnitGenerator(model.getUnitGenerator());		
	}
	

	/**
	 * Methode appellé sur un changement de valeur d'un potentiometre
	 * @param newVal
	 * 		nouvelle valeur
	 */
	public void handleValueViewChange(int indice, Number newVal) {
		model.setValue(indice, (Double)newVal);
	}

	@Override
	public ModelComponent getModel() {
		// TODO Auto-generated method stub
		return model;
	}

}
