package fr.istic.groupimpl.synthesizer.oscilloscope;

import javafx.beans.property.DoubleProperty;
import fr.istic.groupimpl.synthesizer.component.IControllerComponent;
import fr.istic.groupimpl.synthesizer.global.ControllerGlobal;
import fr.istic.groupimpl.synthesizer.out.ModelOut;

public class ControllerOscillo  implements IControllerComponent
{
	
	private ModelOscillo model = new ModelOscillo();
	
	public ControllerOscillo() {
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
		ControllerGlobal.getInstance().removeAllConnections(model.getAllPorts());
		ControllerGlobal.getInstance().unregisterOutUnitGenerator(model.getUnitGenerator());		
	}
	
	public double [] getbufferData()
	{
		return model.getBuffer();
	}

}