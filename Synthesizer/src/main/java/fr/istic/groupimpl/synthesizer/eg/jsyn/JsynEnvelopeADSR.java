package fr.istic.groupimpl.synthesizer.eg.jsyn;

import com.jsyn.engine.SynthesisEngine;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.Circuit;
import com.jsyn.unitgen.UnitGate;
import com.jsyn.unitgen.UnitSource;

/**
 * The class JsynEnvelopeADSR
 * 
 * Four stage envelope similar to an ADSR. The envelope is triggered when the input goes above THRESHOLD. The
 * envelope is released when the input goes below THRESHOLD. The THRESHOLD is currently 0.01 but may
 * change so it would be best to use an input signal that went from 0 to 1. Mathematically an
 * exponential Release will never reach 0.0. But when it reaches -96 dB the DAHDSR just sets its
 * output to 0.0 and stops.
 * 
 * @author Team groupImpl
 * 
 */
public class JsynEnvelopeADSR extends UnitGate implements UnitSource {
    
    /** The Constant MIN_DURATION. */
    private static final double MIN_DURATION = (1.0 / 100000.0);

    /**
     * Time in seconds for the rising stage of the envelope to go from 0.0 to 1.0. The attack is a
     * linear ramp.
     */
    public UnitInputPort attack;
    /**
     * Time in seconds for the falling stage to go from 0 dB to -90 dB. The decay stage will stop at
     * the sustain level. But we calculate the time to fall to -90 dB so that the decay
     * <em>rate</em> will be unaffected by the sustain level.
     */
    public UnitInputPort decay;
    /**
     * Level for the sustain stage. The envelope will hold here until the input goes to zero or
     * less. This should be set between 0.0 and 1.0.
     */
    public UnitInputPort sustain;
    /**
     * Time in seconds to go from 0 dB to -90 dB. This stage is triggered when the input goes to
     * zero or less. The release stage will start from the sustain level. But we calculate the time
     * to fall from full amplitude so that the release <em>rate</em> will be unaffected by the
     * sustain level.
     */
    public UnitInputPort release;
    
    /** The amplitude. */
    public UnitInputPort amplitude;

    /**
     * The Enum State.
     */
    enum State {
        
        /** The idle. */
        IDLE, 
 /** The attacking. */
 ATTACKING, 
 /** The decaying. */
 DECAYING, 
 /** The sustaining. */
 SUSTAINING, 
 /** The releasing. */
 RELEASING
    }

    /** The state. */
    private State state = State.IDLE;
    
    /** The scaler. */
    private double scaler = 1.0;
    
    /** The level. */
    private double level;
    
    /** The increment. */
    private double increment;

    /**
     * Instantiates a new jsyn envelope adsr.
     */
    public JsynEnvelopeADSR() {
        super();
        addPort(attack = new UnitInputPort("Attack", 0.1));
        attack.setup(0.01, 0.1, 8.0);
        addPort(decay = new UnitInputPort("Decay", 0.2));
        decay.setup(0.01, 0.2, 8.0);
        addPort(sustain = new UnitInputPort("Sustain", 0.5));
        sustain.setup(0.0, 0.5, 1.0);
        addPort(release = new UnitInputPort("Release", 0.3));
        release.setup(0.01, 0.3, 8.0);
        addPort(amplitude = new UnitInputPort("Amplitude", 1.0));
    }

    /* (non-Javadoc)
     * @see com.jsyn.unitgen.UnitGenerator#generate(int, int)
     */
    @Override
    public void generate(int start, int limit) {
        double[] sustains = sustain.getValues();
        double[] amplitudes = amplitude.getValues();
        double[] outputs = output.getValues();

        for (int i = start; i < limit;) {
            boolean triggered = input.checkGate(i);
            switch (state) {
                case IDLE:
                    for (; i < limit; i++) {
                        outputs[i] = level * amplitudes[i];
                        if (triggered) {
                            startAttack(i);
                            break;
                        }
                    }
                    break;

                case ATTACKING:
                    for (; i < limit; i++) {
                        // Increment first so we can render fast attacks.
                        level += increment;
                        if (level >= 1.0) {
                            level = 1.0;
                            outputs[i] = level * amplitudes[i];
                            startDecay(i);
                            break;
                        } else {
                            outputs[i] = level * amplitudes[i];
                            if (input.isOff()) {
                                startRelease(i);
                                break;
                            }
                        }
                    }
                    break;

                case DECAYING:
                    for (; i < limit; i++) {
                        level -= increment;
                        outputs[i] = level * amplitudes[i];
                        if (triggered) {
                            startAttack(i);
                            break;
                        } else if (level < sustains[i]) {
                            level = sustains[i];
                            startSustain(i);
                            break;
                        } else if (level < SynthesisEngine.DB96) {
                            input.checkAutoDisable();
                            startIdle();
                            break;
                        } else if (input.isOff()) {
                            startRelease(i);
                            break;
                        }
                    }
                    break;

                case SUSTAINING:
                    for (; i < limit; i++) {
                        level = sustains[i];
                        outputs[i] = level * amplitudes[i];
                        if (triggered) {
                            startAttack(i);
                            break;
                        } else if (input.isOff()) {
                            startRelease(i);
                            break;
                        }
                    }
                    break;

                case RELEASING:
                    for (; i < limit; i++) {
                        outputs[i] = level * amplitudes[i];
                        level *= scaler; // exponential decay
                        if (triggered) {
                            startAttack(i);
                            break;
                        } else if (level < SynthesisEngine.DB96) {
                            input.checkAutoDisable();
                            startIdle();
                            break;
                        }
                    }
                    break;
            }
        }
    }

    /**
     * Start idle.
     */
    private void startIdle() {
        state = State.IDLE;
        level = 0.0;
    }

    /**
     * Start attack.
     *
     * @param i the i
     */
    private void startAttack(int i) {
        double[] attacks = attack.getValues();
        double duration = attacks[i];
        if (duration < MIN_DURATION) {
            level = 1.0;
            startSustain(i);
        } else {
            increment = getFramePeriod() / duration;
            state = State.ATTACKING;
        }
    }

    /**
     * Start decay.
     *
     * @param i the i
     */
    private void startDecay(int i) {
        double[] decays = decay.getValues();
        double duration = decays[i];
        if (duration < MIN_DURATION) {
            level = 1.0;
            startSustain(i);
        } else {
            increment = getFramePeriod() / duration;
            state = State.DECAYING;
        }
    }

    /**
     * Start sustain.
     *
     * @param i the i
     */
    private void startSustain(int i) {
        state = State.SUSTAINING;
    }

    /**
     * Start release.
     *
     * @param i the i
     */
    private void startRelease(int i) {
        double[] releases = release.getValues();
        double duration = releases[i];
        if (duration < MIN_DURATION) {
            duration = MIN_DURATION;
        }
        scaler = getSynthesisEngine().convertTimeToExponentialScaler(duration);
        state = State.RELEASING;
    }

    /**
     * Export.
     *
     * @param circuit the circuit
     * @param prefix the prefix
     */
    public void export(Circuit circuit, String prefix) {
        circuit.addPort(attack, prefix + attack.getName());
        circuit.addPort(decay, prefix + decay.getName());
        circuit.addPort(sustain, prefix + sustain.getName());
        circuit.addPort(release, prefix + release.getName());
    }

    /* (non-Javadoc)
     * @see com.jsyn.unitgen.UnitGate#getOutput()
     */
    @Override
    public UnitOutputPort getOutput() {
        return output;
    }

}
