package fr.istic.groupimpl.synthesizer.global;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitGenerator;

/**
 * The Class ModelGlobalTest.
 */
public class ModelGlobalTest {

	/** The out. */
	UnitOutputPort out;
	
	/** The in. */
	UnitInputPort in;
	
	/** The unit. */
	UnitGenerator unit;
	
	/** The model. */
	ModelGlobal model;
	
	/**
	 * Setup up method.
	 */
	@Before
	public void setup() {
		out = new UnitOutputPort("output");
		in = new UnitInputPort("input");
		unit = new UnitGenerator() {
			
			@Override
			public void generate(int arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
		};
		model = new ModelGlobal();
	}
	
	/**
	 * Test connect port.
	 */
	@Test
	public void testConnect() {
		model.connectPorts(out, in);
		assertTrue(out.isConnected());
		assertTrue(in.isConnected());
		assertTrue(model.getConnectedPort(in) == out);
		assertTrue(model.getConnectedPort(out) == in);
	}
	
	/**
	 * Test disconnect port in.
	 */
	@Test
	public void testDisconnectIn() {
		model.connectPorts(out, in);
		model.disconnectInputPort(in);
		assertTrue(!out.isConnected());
		assertTrue(!in.isConnected());
	}
	
	/**
	 * Test disconnect port out.
	 */
	@Test
	public void testDisconnectOut() {
		model.connectPorts(out, in);
		model.disconnectOutputPort(out);
		assertTrue(!out.isConnected());
		assertTrue(!in.isConnected());
	}
	
	/**
	 * Test add unit.
	 */
	@Test
	public void testAddUnit() {
		model.addUnitGenerator(unit);
		assertTrue(unit.isEnabled());
	}

	/**
	 * Test add out unit.
	 */
	@Test
	public void testAddOutUnit() {
		model.addOutUnit(unit);
		assertTrue(unit.isEnabled());
	}

}
