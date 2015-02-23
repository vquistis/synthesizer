package fr.istic.groupimpl.synthesizer.echo;

import java.util.Collection;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.ports.UnitPort;
import com.jsyn.unitgen.UnitGenerator;

import fr.istic.groupimpl.synthesizer.component.ModelComponent;
import fr.istic.groupimpl.synthesizer.echo.jsyn.JsynEchoCircuit;
import fr.istic.groupimpl.synthesizer.oscilloscope.jsyn.JsynOscilloCircuit;

/**
 * 
 * Model of echo module
 * 
 * @author Team GroupImpl
 *
 */
public class ModelEcho extends ModelComponent {

	private JsynEchoCircuit circuit;
	
	/**
	 * Constructor
	 * @param period
	 */
	public ModelEcho( double period ) {
		super();

		circuit = new JsynEchoCircuit(period);
	}

	/**
	 * set period value in seconds
	 * @param period
	 * 	the period for the echoes
	 */
	public void setPeriodValue( double period )
	{
		circuit.setPeriodValue( period );
	}
	
	/**
	 * set the attenuation value in decibel
	 * @param attenuation
	 */
	public void setAttenuationValue( double attenuation )
	{
		circuit.setAttenuationValue( attenuation );
	}
	
	@Override
	public UnitGenerator getUnitGenerator() {
		return circuit;
	}

	/**
	 * Get the jsyn input port.
	 * @return UnitInputPort
	 */
	public UnitInputPort getInputPort() {
		return circuit.getInput();
	}

	/**
	 * Get the jsyn output port.
	 * @return UnitOutputPort
	 */
	public UnitOutputPort getOutputPort() {
		return circuit.getOutput();
	}

	@Override
	public Collection<UnitPort> getAllPorts() {
		return circuit.getPorts();
	}

}
