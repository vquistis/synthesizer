package fr.istic.groupimpl.synthesizer.oscilloscope;

import fr.istic.groupimpl.synthesizer.component.ControllerComponent;
import fr.istic.groupimpl.synthesizer.component.ModelComponent;
import fr.istic.groupimpl.synthesizer.global.ControllerGlobal;
import fr.istic.groupimpl.synthesizer.util.Oscilloscope;

public class ControllerOscillo  extends ControllerComponent
{
	
	private ModelOscillo model = new ModelOscillo(Oscilloscope.SIZE_BUFFER_READ);
	private Oscilloscope scope;
	
	public ControllerOscillo(Oscilloscope scope) {
		this.scope = scope;
		ControllerGlobal.getInstance().registerOutUnitGenerator(model.getUnitGenerator());
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

	@Override
	public ModelComponent getModel() {
		// TODO Auto-generated method stub
		return model;
	}

}
