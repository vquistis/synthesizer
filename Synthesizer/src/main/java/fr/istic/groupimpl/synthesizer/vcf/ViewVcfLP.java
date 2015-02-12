package fr.istic.groupimpl.synthesizer.vcf;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import fr.istic.groupimpl.synthesizer.component.ControllerComponent;
import fr.istic.groupimpl.synthesizer.util.Potentiometre;
import fr.istic.groupimpl.synthesizer.util.PotentiometreFactory;

public class ViewVcfLP extends ViewVcf implements Initializable {

	private ControllerVcf controller;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Creation du controller
		controller = new ControllerVcf(ModelVcf.Type.LP24);

		// Creation des potentiometres
		PotentiometreFactory knobFact = PotentiometreFactory.getFactoryInstance();
		knobFact.setRayon(32);

		knobFact.setMinValue(10);
		knobFact.setMaxValue(22000);
		knobFact.setValueDef(10);
		knobFact.setNbSpins(3);
		Potentiometre knobCutoff = knobFact.getPotentiometre();
		
		knobFact.setMinValue(0);
		knobFact.setMaxValue(10);
		knobFact.setValueDef(1);
		knobFact.setNbSpins(1);
		Potentiometre knobResonance = knobFact.getPotentiometre();
		
		super.configurate("VCF - LP24", controller, knobCutoff, knobResonance);
	}

	@Override
	protected ControllerComponent getController() {
		return controller;
	}

	@Override
	public String getFilename() {
		return "fxml/vcf-lp.fxml";
	}
}