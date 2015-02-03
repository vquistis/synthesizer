package fr.istic.groupimpl.synthesizer.out;

public class ControllerOut {

	private ModelOut model;
	
	public ControllerOut() {
		model = new ModelOut();
	}
	
	public void handleViewVolumeChange(Number newVal) {
		model.setAttenuation((double) newVal);
	}

	public void handleViewMuteChange(Boolean newVal) {
		if (newVal) {
			model.start();
		} else {
			model.stop();
		}
	}
}
