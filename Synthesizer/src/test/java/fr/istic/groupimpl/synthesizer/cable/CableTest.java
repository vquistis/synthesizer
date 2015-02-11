package fr.istic.groupimpl.synthesizer.cable;


public class CableTest {
    

//    @Test
//    public void bindInputTest() {
//    	CopyOfCable cable = new CopyOfCable(Color.FORESTGREEN);
//    	
//    	// PropertyX de la fin du cable
//    	DoubleProperty endX = cable.endXProperty();
//    	endX.set(10);
//    	// PropertyY de la fin du cable
//    	DoubleProperty endY = cable.endYProperty();
//    	endY.set(10);
//	
//    	// controlX2Property() (effet pendouillant)
//    	DoubleProperty ctlX2 = cable.controlX2Property();
//    	// controly2Property() (effet pendouillant)
//    	DoubleProperty ctlY2 = cable.controlY2Property();
//    	
//    	// PropertyX du composant
//    	DoubleProperty x = new SimpleDoubleProperty(100) ;
//    	// PropertyY du composant
//     	DoubleProperty y = new SimpleDoubleProperty(100) ;
//     	
//     	// avant le bind
//    	assertNotEquals("Avant Bind - Property X", endX.get(), x.get());
//    	assertNotEquals("Avant Bind - Property Y", endY.get(), y.get());
//    	assertNotEquals("Avant Bind - Ctl Property X2", ctlX2.get(), x.get());
//    	assertNotEquals("Avant Bind - Ctl Property Y2", ctlY2.get(), y.get());
//    	
//     	cable.bindInput(x, y);
//    	assertEquals("Après Bind - Property X", (long) endX.get(), (long) x.get());
//    	assertEquals("Après Bind - Property Y", (long) endY.get(), (long) y.get());
//    	assertEquals("Après Bind - Ctl Property X2", (long) ctlX2.get(), (long) x.get());
//     	
//    }
//    
//    @Test
//    public void bindOutputTest() {
//    	CopyOfCable cable = new CopyOfCable(Color.FORESTGREEN);
//    	
//    	// PropertyX du début du cable
//    	DoubleProperty startX = cable.startXProperty();
//    	startX.set(10);
//    	// PropertyY du début du cable
//    	DoubleProperty startY = cable.startYProperty();
//    	startY.set(10);
//	
//    	// controlX2Property() (effet pendouillant)
//    	DoubleProperty ctlX1 = cable.controlX1Property();
//    	// controly2Property() (effet pendouillant)
//    	DoubleProperty ctlY1 = cable.controlY1Property();
//    	
//    	// PropertyX du composant
//    	DoubleProperty x = new SimpleDoubleProperty(100) ;
//    	// PropertyY du composant
//     	DoubleProperty y = new SimpleDoubleProperty(100) ;
//     	
//    	assertNotEquals("Avant Bind - Ctl Property X2",startX.get(), x.get());
//    	assertNotEquals("Avant Bind - Ctl Property X2",startY.get(), y.get());
//    	assertNotEquals("Avant Bind - Ctl Property X2", ctlX1.get(), x.get());
//    	assertNotEquals("Avant Bind - Ctl Property Y2", ctlY1.get(), y.get());
//     	
//     	cable.bindOutput(x,y);
//    	assertEquals("Après Bind - Ctl Property X1",(long) startX.get(), (long) x.get());
//    	assertEquals("Après Bind - Ctl Property X1",(long) startY.get(), (long) y.get());
//    	assertEquals("Après Bind - Ctl Property X1", (long) ctlX1.get(), (long) x.get());
//    }
    
}
