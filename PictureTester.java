/**
 * This class contains class (static) methods
 * that will help you test the Picture class 
 * methods.  Uncomment the methods and the code
 * in the main to test.
 * 
 * @author Barbara Ericson 
 */
public class PictureTester
{
  /** Method to test zeroBlue */
  public static void testZeroBlue()
  {
    Picture beach = new Picture("beach.jpg");
    beach.explore();
    beach.zeroBlue();
    beach.explore();
  }
  public static void testNegate() {
	  Picture beach = new Picture("beach.jpg");
	    beach.explore();
	    beach.negate();
	    beach.explore();
  }
  /** Method to test mirrorVertical */
  public static void testMirrorVertical()
  {
    Picture caterpillar = new Picture("caterpillar.jpg");
    caterpillar.explore();
    caterpillar.mirrorVertical();
    caterpillar.explore();
  }
  
  /** Method to test mirrorTemple */
  public static void testMirrorTemple()
  {
    Picture temple = new Picture("temple.jpg");
    temple.explore();
    temple.mirrorTemple();
    temple.explore();
  }
  
  /** Method to test the collage method */
  public static void testCollage()
  {
    Picture canvas = new Picture("640x480.jpg");
    canvas.createCollage();
    canvas.explore();
  }
 // public stat
  /** Method to test edgeDetection */
  public static void testEdgeDetection()
  {
    Picture swan = new Picture("seagull.jpg");
    swan.edgeDetection(10);
    swan.explore();
  }
  
  /** Main method for testing.  Every class can have a main
    * method in Java */
  public static void main(String[] args)
  {
    
    //testHide(); 
	//testHideBetter();   
  }

private static void testKeepOnlyBlue() {
	Picture seagull= new Picture("temple.jpg");
	seagull.explore();
	seagull.keepOnlyBlue();
	seagull.explore();
	
}
private static void testHideBetter() {
	Picture seagull= new Picture("temple.jpg");
	seagull.explore();
	seagull.hideBetter();
	seagull.explore();
	seagull.decryptHideBetter();
	}
private static void testHide() {
	Picture seagull= new Picture("temple.jpg");
	seagull.explore();
	seagull.hide();
	seagull.explore();
	seagull.decrypt();
}
private static void testEdgeDetection2() {
	Picture seagull= new Picture("temple.jpg");
	seagull.explore();
	seagull.edgeDetection2(10);
	seagull.explore();
	
}
private static void testMirrorGull() {
	Picture seagull= new Picture("seagull.jpg");
	seagull.explore();
	seagull.mirrorGull();
	seagull.explore();
	
}
private static void testMirrorArms() {
	Picture snowman= new Picture("snowman.jpg");
	snowman.explore();
	snowman.mirrorArms();
	snowman.explore();
	
}
private static void testMirrorHorizontal() {
	Picture motorcycle = new Picture("redMotorcycle.jpg");
    motorcycle.explore();
    motorcycle.mirrorHorizontal();
    motorcycle.explore();
	
}
private static void testMirrorDiagonal() {
	Picture motorcycle = new Picture("redMotorcycle.jpg");
    motorcycle.explore();
    motorcycle.mirrorDiagonal();
    motorcycle.explore();
	
}
private static void testMirrorHorizontalBotToTop() {
	Picture motorcycle = new Picture("redMotorcycle.jpg");
    motorcycle.explore();
    motorcycle.mirrorHorizontalBotToTop();
    motorcycle.explore();
	
}
private static void testMirrorVerticalRightToLeft() {
	Picture motorcycle = new Picture("redMotorcycle.jpg");
    motorcycle.explore();
    motorcycle.mirrorVerticalRighttoLeft();
    motorcycle.explore();
	
}
private static void testGrayscale() {
	 Picture beach = new Picture("beach.jpg");
	    beach.explore();
	    beach.grayscale();
	    beach.explore();
	
}
public static void testFixUnderwater() {
	Picture water = new Picture("water.jpg");
	water.explore();
	water.fixUndewater();
	water.explore();
}
public static void testMyCollage()
{
  Picture canvas = new Picture("640x480.jpg");
  canvas.myCollage();
  canvas.explore();
}
}