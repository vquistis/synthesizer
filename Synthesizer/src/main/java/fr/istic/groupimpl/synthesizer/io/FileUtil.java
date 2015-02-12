package fr.istic.groupimpl.synthesizer.io;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import fr.istic.groupimpl.synthesizer.logger.Log;

/**
 * The Class xmlio.
 */
public class FileUtil {	
	
	/**
	 * Load file.
	 *
	 * @param file the file
	 * @param clazz the clazz
	 * @return the object
	 */
	public static Object loadFile(File file,Class<?> clazz){
		final Log log = Log.getInstance();
		try{
			JAXBContext context = JAXBContext.newInstance(clazz);			
			Unmarshaller um = context.createUnmarshaller();
			Object obj=  um.unmarshal(new FileReader(file));
			log.info("Le fichier "+file+" a été chargé");
			return obj;
		}catch(Exception e){
			log.error("Erreur de chargement ");
		}
		return null;	
	}
	
	/**
	 * Save file.
	 *
	 * @param obj the obj
	 * @param file the file
	 */
	public static void saveFile(Object obj,File file){
		final Log log = Log.getInstance();
		try{
			JAXBContext context = JAXBContext.newInstance(obj.getClass());
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//			m.marshal(obj, System.out);
			Writer w = null;
			w = new FileWriter(file);
			m.marshal(obj, w);
			w.close();
			log.info("le fichier "+file+" a été enregistré");
			
		}catch(Exception e){
			log.info("Erreur d'enregistrement ");
		}
	}

}
