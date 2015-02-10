package fr.istic.groupimpl.synthesizer.oscilloscope;

import javafx.beans.property.DoubleProperty;
import fr.istic.groupimpl.synthesizer.component.IControllerComponent;
import fr.istic.groupimpl.synthesizer.global.ControllerGlobal;
import fr.istic.groupimpl.synthesizer.out.ModelOut;
import fr.istic.groupimpl.synthesizer.util.Oscilloscope;

public class ControllerOscillo  implements IControllerComponent
{
	
	private ModelOscillo model = new ModelOscillo(Oscilloscope.SIZE_BUFFER_READ);
	private Oscilloscope scope;
	
	public ControllerOscillo(Oscilloscope scope) {
		this.scope = scope;
		ControllerGlobal.getInstance().registerOutUnitGenerator(model.getUnitGenerator());
	}
	


	@Override
	public void handleViewInputClick(String portName, DoubleProperty xCoord,
			DoubleProperty yCoord) {
		ControllerGlobal.getInstance().handleInputClicked(model.getInputPort(portName), xCoord, yCoord);
		
	}

	@Override
	public void handleViewOutputClick(String portName, DoubleProperty xCoord,
			DoubleProperty yCoord) {
		ControllerGlobal.getInstance().handleOutputClicked(model.getOutputPort(portName), xCoord, yCoord);
		
	}

	@Override
	public void handleViewClose() {
		scope.stop(); // pour arreter le thread
		ControllerGlobal.getInstance().removeAllConnections(model.getAllPorts());
		ControllerGlobal.getInstance().unregisterOutUnitGenerator(model.getUnitGenerator());		
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
