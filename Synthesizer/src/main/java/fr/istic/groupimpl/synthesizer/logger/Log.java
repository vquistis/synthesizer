package fr.istic.groupimpl.synthesizer.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The Class Log.
 *
 * @author Team GroupImpl
 */
public class Log {

	/** The log manager. */
	private Logger logManager = LogManager.getLogger();
	
	/** The log. */
	static private Log log = new Log();

	/**
	 * Gets the single instance of Log.
	 *
	 * @return single instance of Log
	 */
	public static Log getInstance() {
		return log;
	}

	/**
	 * Trace.
	 *
	 * @param msg the msg
	 */
	public void trace(String msg) {
		logManager.trace(msg);
	}

	/**
	 * Debug.
	 *
	 * @param msg the msg
	 */
	public void debug(String msg) {
		logManager.debug(msg);
	}

	/**
	 * Info.
	 *
	 * @param msg the msg
	 */
	public void info(String msg) {
		logManager.info(msg);
	}

	/**
	 * Warn.
	 *
	 * @param msg the msg
	 */
	public void warn(String msg) {
		logManager.warn(msg);
	}

	/**
	 * Error.
	 *
	 * @param msg the msg
	 */
	public void error(String msg) {
		logManager.error(msg);
	}

	/**
	 * Fatal.
	 *
	 * @param msg the msg
	 */
	public void fatal(String msg) {
		logManager.fatal(msg);
	}
	
	/**
	 * Trace.
	 *
	 * @param msg the msg
	 * @param e the e
	 */
	public void trace(String msg, Exception e) {
		logManager.trace(msg, e);
	}

	/**
	 * Debug.
	 *
	 * @param msg the msg
	 * @param e the e
	 */
	public void debug(String msg, Exception e) {
		logManager.debug(msg, e);
	}

	/**
	 * Info.
	 *
	 * @param msg the msg
	 * @param e the e
	 */
	public void info(String msg, Exception e) {
		logManager.info(msg, e);
	}

	/**
	 * Warn.
	 *
	 * @param msg the msg
	 * @param e the e
	 */
	public void warn(String msg, Exception e) {
		logManager.warn(msg, e);
	}

	/**
	 * Error.
	 *
	 * @param msg the msg
	 * @param e the e
	 */
	public void error(String msg, Exception e) {
		logManager.error(msg, e);
	}

	/**
	 * error Fatal.
	 *
	 * @param msg the msg
	 * @param e the e
	 */
	public void fatal(String msg, Exception e) {
		logManager.fatal(msg, e);
	}
}
