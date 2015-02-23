package fr.istic.groupimpl.synthesizer.cable;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.StrokeLineCap;
import fr.istic.groupimpl.synthesizer.logger.Log;
/**
 * 
 * Connects two components by a cable
 *    
 * @author Team groupImpl
 *
 */
public class Cable extends Path {

	/** The binding. */
	private ComputeDistance binding;
	
	/** The inner shadow. */
	private InnerShadow innerShadow;
	
	/** The drop shadow. */
	private DropShadow dropShadow;
	
	/** The curve. */
	private CubicCurveTo curve;
	
	/** The move. */
	private MoveTo move;

	/**
	 * Constructor.
	 *
	 * @param color the color
	 */
	public Cable(Color color) {
		setMouseTransparent(true);
		setStroke(color);
		setStrokeWidth(10);
		setStrokeLineCap(StrokeLineCap.ROUND);
		curve = new CubicCurveTo();
		move = new MoveTo();
		binding = new ComputeDistance();
		getElements().addAll(move, curve);
		
		innerShadow = new InnerShadow(BlurType.GAUSSIAN,
				Color.BLACK, 10, 0.25, 0, 0);
		dropShadow = new DropShadow(BlurType.GAUSSIAN,
				Color.ANTIQUEWHITE, 5, 0.5, 0, 0);
		dropShadow.setInput(innerShadow);
		setEffect(innerShadow);
		
		hoverProperty().addListener((obs,oldVal,newVal) -> {
			Log.getInstance().debug("CABLE HOVERED");
			if(newVal) {
				setEffect(dropShadow);
			} else {
				setEffect(innerShadow);
			}
		});
	}

	/**
	 * Connecting the end of the first module input port cable.
	 *
	 * @param endX the end x
	 * @param endY the end y
	 */
	public void bindInput(DoubleProperty endX, DoubleProperty endY) {
		curve.xProperty().bind(endX);
		curve.yProperty().bind(endY);
		curve.controlX2Property().bind(endX);
		curve.controlY2Property().bind(endY.add(binding));
	}

	/**
	 * Cable connection to the beginning of the second module output port.
	 *
	 * @param startX the start x
	 * @param startY the start y
	 */
	public void bindOutput(DoubleProperty startX, DoubleProperty startY) {
		move.xProperty().bind(startX);
		move.yProperty().bind(startY);
		curve.controlX1Property().bind(startX);
		curve.controlY1Property().bind(startY.add(binding));
	}

	/**
	 * Class for calculating the distance between two modules 
	 * for the dangling end of the cable.
	 */
	private class ComputeDistance extends DoubleBinding {

		/**
		 * Constructor.
		 */
		public ComputeDistance() {
			super.bind(move.xProperty(),move.yProperty(),curve.xProperty(),curve.yProperty());
		}

		/* (non-Javadoc)
		 * @see javafx.beans.binding.DoubleBinding#computeValue()
		 */
		@Override
		protected double computeValue() {
			double xCarre = Math.pow(move.xProperty().get()-curve.xProperty().get(), 2) ;
			double yCarre = Math.pow(move.yProperty().get()-curve.yProperty().get(), 2) ;
			return Math.sqrt(xCarre + yCarre)*0.50;
		}
	};
}

