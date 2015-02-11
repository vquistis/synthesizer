package fr.istic.groupimpl.synthesizer.global;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitGenerator;

public class ModelGlobalTest {

	UnitOutputPort out;
	UnitInputPort in;
	UnitGenerator unit;
	ModelGlobal model;
	
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
	
	@Test
	public void testConnect() {
		model.connectPorts(out, in);
		assertTrue(out.isConnected());
		assertTrue(in.isConnected());
		assertTrue(model.getConnectedPort(in) == out);
		assertTrue(model.getConnectedPort(out) == in);
	}
	
	@Test
	public void testDisconnectIn() {
		model.connectPorts(out, in);
		model.disconnectInputPort(in);
		assertTrue(!out.isConnected());
		assertTrue(!in.isConnected());
	}
	
	@Test
	public void testDisconnectOut() {
		model.connectPorts(out, in);
		model.disconnectOutputPort(out);
		assertTrue(!out.isConnected());
		assertTrue(!in.isConnected());
	}
	
	@Test
	public void testAddUnit() {
		model.addUnitGenerator(unit);
		assertTrue(unit.isEnabled());
	}

	@Test
	public void testAddOutUnit() {
		model.addOutUnit(unit);
		assertTrue(unit.isEnabled());
	}

}
