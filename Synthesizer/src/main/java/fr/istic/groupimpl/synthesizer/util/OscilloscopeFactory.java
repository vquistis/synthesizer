package fr.istic.groupimpl.synthesizer.util;

public class OscilloscopeFactory {
	
	
	private OscilloscopeFactory() {
	}

	/**
	 * get an oscilloscope factory method
	 * @return
	 * 	an oscilloscope factory method
	 */
	static public OscilloscopeFactory getFactoryInstance() {
		return new OscilloscopeFactory();
	}

	/**
	 * get an oscilloscope instance
	 * @return
	 * an oscilloscope instance
	 */
	public Oscilloscope getOscilloscope() {
		return new Oscilloscope(this);
	}

	
	
	private double height;
	private double width;
	private Oscilloscope.GetBuffer cmdGetBuffer;
	private long refreshPeriod=1000;

	/**
	 * get refresh period
	 * @return
	 * 	refresh period
	 */
	public long getRefreshPeriod() {
		return refreshPeriod;
	}
	/**
	 * set the refresh period
	 * @param refreshPeriod
	 * 	the refresh period
	 */
	public void setRefreshPeriod(long refreshPeriod) {
		this.refreshPeriod = refreshPeriod;
	}
	/**
	 * get the get buffer command
	 * @return
	 * 	the  get buffer command
	 */
	public Oscilloscope.GetBuffer getCmdGetBuffer() {
		return cmdGetBuffer;
	}
	
	/**
	 * set the get buffer command
	 * @param cmdGetBuffer
	 *  the get buffer command
	 */
	public void setCmdGetBuffer(Oscilloscope.GetBuffer cmdGetBuffer) {
		this.cmdGetBuffer = cmdGetBuffer;
	}
	
	/**
	 * get the height
	 * @return
	 * 		the height
	 */
	public double getHeight() {
		return height;
	}
	/**
	 * set the height
	 * @param height
	 * 	the height
	 */
	public void setHeight(double height) {
		this.height = height;
	}
	/**
	 * get the width
	 * @return
	 * 		the width
	 */
	public double getWidth() {
		return width;
	}
	/**
	 * set the width
	 * @param width
	 * 		the width
	 */
	public void setWidth(double width) {
		this.width = width;
	}
	

}
