package fr.istic.groupimpl.synthesizer.seq;

import javafx.beans.property.DoubleProperty;
import fr.istic.groupimpl.synthesizer.component.IControllerComponent;
import fr.istic.groupimpl.synthesizer.global.ControllerGlobal;
import fr.istic.groupimpl.synthesizer.util.Oscilloscope;

public class ControllerSeq  implements IControllerComponent
{
	static final int NB_BUTTONS = 8;
	
	private ModelSeq model = new ModelSeq(NB_BUTTONS);
	private Oscilloscope scope;
	
	public ControllerSeq(Oscilloscope scope) {
		this.scope = scope;
		ControllerGlobal.getInstance().registerOutUnitGenerator(model.getUnitGenerator());
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
		scope.stop(); // pour arreter le thread
		ControllerGlobal.getInstance().removeAllConnections(model.getAllPorts());
		ControllerGlobal.getInstance().unregisterOutUnitGenerator(model.getUnitGenerator());		
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
