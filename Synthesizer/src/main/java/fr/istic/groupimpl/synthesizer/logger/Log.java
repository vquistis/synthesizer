package fr.istic.groupimpl.synthesizer.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * The Class Log
 * 
 * @author Team GroupImpl
 *
 */
public class Log {

	private Logger logManager = LogManager.getLogger();
	static private Log log = new Log();

	public static Log getInstance() {
		return log;
	}

	public void trace(String msg) {
		logManager.trace(msg);
	}

	public void debug(String msg) {
		logManager.debug(msg);
	}

	public void info(String msg) {
		logManager.info(msg);
	}

	public void warn(String msg) {
		logManager.warn(msg);
	}

	public void error(String msg) {
		logManager.error(msg);
	}

	public void fatal(String msg) {
		logManager.fatal(msg);
	}

}
