package fr.istic.groupimpl.synthesizer.util;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

import javafx.scene.Node;
import javafx.scene.Parent;

public class DebugJFXTools {

	public void GenerateNodeHierarchy(Node node, String fileName) {
		FileWriter writer = null;
		try{
		     writer = new FileWriter(fileName, false);
			 dump(node, 0, writer);
		}catch(IOException ex){
		    ex.printStackTrace();
		}finally{
		  if(writer != null){
		     try {
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  }
		}
	}
	
	private static void dump(Node node, int depth, FileWriter writer) throws IOException {
		DecimalFormat df = new DecimalFormat("0.00");
		String descString = "";
		String indent = "";
		String stringToWrite = "";
	    for (int i = 0; i < depth; i++) indent=indent + "  ";;
	
		if (node.getId()!=null) { descString = node.getId() + ":";};
		descString = descString + node.getClass().getSimpleName();
		descString = descString + " [X=" + df.format(node.getLayoutBounds().getMinX()) + " Y=" + df.format(node.getLayoutBounds().getMaxY());
		descString = descString + " W=" + df.format(node.getLayoutBounds().getWidth()) + " H=" + df.format(node.getLayoutBounds().getHeight()) + "]";
		descString = descString + " BoundsInParent:" + node.getBoundsInParent();
		descString = descString + " BoundsInLocal:" + node.getBoundsInLocal();
		descString = descString + " LayoutBounds:" + node.getLayoutBounds();
		descString = descString + " " + node;
		stringToWrite = indent + descString;
		if (writer==null) {
			System.out.println(stringToWrite);
		} else {
			stringToWrite = stringToWrite + System.getProperty("line.separator");
		    writer.write(stringToWrite,0,stringToWrite.length());
		}
	
	    if (node instanceof Parent) {
	        for (Node childNode : ((Parent) node).getChildrenUnmodifiable()) {
	            dump(childNode, depth + 1, writer);
	        }
	    }
	}
}
