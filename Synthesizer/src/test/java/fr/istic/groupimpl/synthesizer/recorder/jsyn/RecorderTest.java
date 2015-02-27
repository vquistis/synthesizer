package fr.istic.groupimpl.synthesizer.recorder.jsyn;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jsyn.engine.SynthesisEngine;

import fr.istic.groupimpl.synthesizer.util.SignalUtil;

public class RecorderTest {

	private String sampleFileTest = "src/main/resources/sound/junit_RecorderTest.wav";
	
    /** The synthesis engine. */
    SynthesisEngine synthesisEngine;
	
	@Before
	public void init() {
		synthesisEngine = new SynthesisEngine();
		
		File sampleFile = new File( sampleFileTest );
		if (sampleFile.exists()) {
	    	sampleFile.setWritable(true);
			sampleFile.delete();
		}
	}

	@After
	public void after() {
		synthesisEngine.stop();
	}
	
	@Test
    public void testRecorder() throws InterruptedException, IOException {
        Recorder recorder = new Recorder();
        synthesisEngine.add(recorder);
        synthesisEngine.start();

		File sampleFile = new File( sampleFileTest );
		recorder.prepareFile(sampleFile);
		
		recorder.startRecording();
		recorder.getInput().set(1);
        synthesisEngine.sleepFor(0.2);
        recorder.stopRecording();
        synthesisEngine.sleepFor(0.2);
        
        if (!sampleFile.exists()) {
        	fail("Sample file does not exist !");
        }
        
        if (!(sampleFile.getTotalSpace()>0)) {
        	fail("Sample file is empty !");
        }
        
        sampleFile.delete();
    }
	
	@Test
    public void testRecorder_WithoutSampleFile() throws InterruptedException, IOException {
        Recorder recorder = new Recorder();
        synthesisEngine.add(recorder);
        synthesisEngine.start();
		
		recorder.startRecording();
        synthesisEngine.sleepFor(0.2);
        recorder.stopRecording();
        synthesisEngine.sleepFor(0.2);
     }

	@Test (expected=FileNotFoundException.class)
    public void testRecorder_WrongPathFile() throws InterruptedException, IOException {
        Recorder recorder = new Recorder();
        synthesisEngine.add(recorder);
        synthesisEngine.start();

		File sampleFile = new File( "error" + sampleFileTest );
		
		recorder.prepareFile(sampleFile);
     }
	
	@Test (expected=FileNotFoundException.class)
    public void testRecorder_LockFile() throws InterruptedException, IOException {
        Recorder recorder = new Recorder();
        synthesisEngine.add(recorder);
        synthesisEngine.start();

		File sampleFile = new File( sampleFileTest );
		
		recorder.prepareFile(sampleFile);
		recorder.startRecording();
		recorder.getInput().set(1);

        synthesisEngine.sleepFor(0.2);
        
		sampleFile.setReadOnly(); // to generate an error before closing
		recorder.stopRecording();
	    synthesisEngine.sleepFor(0.2);
	    recorder=null;
	    
        assertTrue("Sample file exist", sampleFile.exists());
        assertEquals("File is empty", 0, sampleFile.length());
     }
}