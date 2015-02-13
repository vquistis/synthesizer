package fr.istic.groupimpl.synthesizer.mixer;

import java.util.Collection;

import javafx.application.Platform;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.ports.UnitPort;
import com.jsyn.unitgen.UnitGenerator;

import fr.istic.groupimpl.synthesizer.component.ModelComponent;
import fr.istic.groupimpl.synthesizer.mixer.jsyn.Mixer;

public class ModelMixer extends ModelComponent {

	private Mixer mixer;
	private Thread refreshThread;
	private long refreshPeriod = 20;
	private double MaxOutputValue = 0.00;
	private double residualValue = 0.1;
	private double decreaseMaxValueIncrement = 0.01;
	
	public ModelMixer(Integer NumberOfInputPort) {
		super();
		mixer = new Mixer(NumberOfInputPort);
		
		refreshThread = new Thread(() -> {
			while (refreshThread.isAlive()) {
				try {
					Thread.sleep(refreshPeriod);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
				Platform.runLater(() -> computeOutputGaugeBar());
			}
		});
	}

	/**
	 * To start the refresh thread
	 */
	public void start() {
		refreshThread.start();
	}

	/**
	 * To stop the refresh thread
	 */
	public void stop() {
		if (refreshThread.isAlive()) {
			refreshThread.interrupt();
		}
	}
	
	/**
	 * Get the number of input port
	 * 
	 * @return Integer
	 */
	public Integer getNumberOfInputPort() {
		return mixer.getNumberOfInputPort();
	}

	
	@Override
	public UnitGenerator getUnitGenerator() {
		return mixer;
	}

	@Override
	public Collection<UnitPort> getAllPorts() {
		return mixer.getPorts();
	}
	
	/**
	 * Set an attenuation to the output signal of the index input
	 * @param index
	 * 		Input index
	 * @param dbValue
	 */
	public void setAttenuation(Integer index, double dbValue) {
		mixer.setAttenuation(index, dbValue);
	}

	/**
	 * Set Mute to the output signal of the index input
	 * @param index
	 * 		Input index
	 * @param value
	 *     true|false
	 */
	public void setMute(Integer index, boolean value) {
		mixer.setMute(index, value);
	}
	
	/**
	 * Get the jsyn output port.
	 * @return UnitOutputPort
	 */
	public UnitOutputPort getOutputPort() {
		return mixer.getOutput();
	}
	
	/**
	 * Get the jsyn input port.
	 * @param index
	 * 		Input index
	 * @return
	 */
	public UnitInputPort getInputPort(Integer index) {
		return mixer.getInput(index);
	}
	
	/**
	 * 
	 * Get the output level value
	 * 
	 */
	private void computeOutputGaugeBar() {
		double OutputValue = Math.abs(getOutputPort().get());
		if (OutputValue < residualValue) OutputValue = 0.00;
		this.setValProperty("OutputGaugeBar", OutputValue);
		computeMaxOutputGaugeBar(OutputValue);
	}
	
	/**
	 * 
	 * Get the output max level value, generate a slow down value
	 * 
	 */
	private void computeMaxOutputGaugeBar(double OutputValue) {
		if (OutputValue > MaxOutputValue) {
			MaxOutputValue = OutputValue;
			this.setValProperty("MaxOutputGaugeBar", MaxOutputValue);
		} else {
			if (MaxOutputValue > 0.00) {
				MaxOutputValue = MaxOutputValue - decreaseMaxValueIncrement;
				this.setValProperty("MaxOutputGaugeBar", MaxOutputValue);
			}
		}
	}
}
