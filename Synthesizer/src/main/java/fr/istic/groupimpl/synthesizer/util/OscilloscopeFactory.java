package fr.istic.groupimpl.synthesizer.util;

public class OscilloscopeFactory {
	
	
	private OscilloscopeFactory() {
	}

	static public OscilloscopeFactory getFactoryInstance() {
		return new OscilloscopeFactory();
	}

	public Oscilloscope getOscilloscope() {
		return new Oscilloscope(this);
	}

	
	
	private double height;
	private double width;
	private Oscilloscope.GetBuffer cmdGetBuffer;
	private long refreshPeriod;

	public long getRefreshPeriod() {
		return refreshPeriod;
	}
	public void setRefreshPeriod(long refreshPeriod) {
		this.refreshPeriod = refreshPeriod;
	}
	public Oscilloscope.GetBuffer getCmdGetBuffer() {
		return cmdGetBuffer;
	}
	public void setCmdGetBuffer(Oscilloscope.GetBuffer cmdGetBuffer) {
		this.cmdGetBuffer = cmdGetBuffer;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public double getWidth() {
		return width;
	}
	public void setWidth(double width) {
		this.width = width;
	}
	

}
