package fr.istic.groupimpl.synthesizer.cable;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.StrokeLineCap;

/**
 * 
 * Permet de relier 2 composants par un câble
 *    
 * @author Team groupImpl
 *
 */
public class Cable extends CubicCurve {

	DoubleProperty distance;
	ComputeDistance binding;

	/**
	 * Constructeur
	 */
	public Cable() {
		setStroke(Color.FORESTGREEN);
		setStrokeWidth(4);
		setStrokeLineCap(StrokeLineCap.ROUND);
		setFill(Color.CORNSILK.deriveColor(0, 1.2, 1, 0.6));
		binding = new ComputeDistance();
	}

	/**
	 * Branchement de la fin du câble au port d'entrée du premier module
	 * @param endX
	 * @param endY
	 */
	public void bindInput(DoubleProperty endX, DoubleProperty endY) {
		endXProperty().bind(endX);
		endYProperty().bind(endY);
		controlX2Property().bind(endX);
		controlY2Property().bind(endY.add(binding.get()));
	}

	/**
	 * Branchement du début du câble au port de sortie du deuxième module
	 * @param startX
	 * @param startY
	 */
	public void bindOutput(DoubleProperty startX, DoubleProperty startY) {
		startXProperty().bind(startX);
		startYProperty().bind(startY);
		controlX1Property().bind(startX);
		controlY1Property().bind(startY.add(binding.get()));
	}

	/**
	 * Classe permettant de calculer la distance entre 2 modules
	 * pour l'effet pendouillant du câble.
	 */
	private class ComputeDistance extends DoubleBinding {

		/**
		 * Constructeur
		 */
		public ComputeDistance() {
			super.bind(startXProperty(),startYProperty(),endXProperty(),endYProperty());
		}

		@Override
		protected double computeValue() {
			double xCarre = Math.pow(startXProperty().get()-endXProperty().get(), 2) ;
			double yCarre = Math.pow(startYProperty().get()-endYProperty().get(), 2) ;
			return Math.sqrt(xCarre + yCarre)*0.50;
		}
	};
}
