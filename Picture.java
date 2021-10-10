import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.text.*;
import java.util.*;
import java.util.List; // resolves problem with java.awt.List and java.util.List

/**
 * A class that represents a picture.  This class inherits from 
 * SimplePicture and allows the student to add functionality to
 * the Picture class.  
 * 
 * Specifications from Barbara Ericson ericson@cc.gatech.edu
 */
public class Picture extends SimplePicture 
{
  ///////////////////// constructors //////////////////////////////////
  
  /**
   * Constructor that takes no arguments 
   */
  public Picture ()
  {
    /* not needed but use it to show students the implicit call to super()
     * child constructors always call a parent constructor 
     */
    super();  
  }
  
  /**
   * Constructor that takes a file name and creates the picture 
   * @param fileName the name of the file to create the picture from
   */
  public Picture(String fileName)
  {
    // let the parent class handle this fileName
    super(fileName);
  }
  
  /**
   * Constructor that takes the width and height
   * @param height the height of the desired picture
   * @param width the width of the desired picture
   */
  public Picture(int height, int width)
  {
    // let the parent class handle this width and height
    super(width,height);
  }
  
  /**
   * Constructor that takes a picture and creates a 
   * copy of that picture
   * @param copyPicture the picture to copy
   */
  public Picture(Picture copyPicture)
  {
    // let the parent class do the copy
    super(copyPicture);
  }
  
  /**
   * Constructor that takes a buffered image
   * @param image the buffered image to use
   */
  public Picture(BufferedImage image)
  {
    super(image);
  }
  
  ////////////////////// methods ///////////////////////////////////////
  
  /**
   * Method to return a string with information about this picture.
   * @return a string with information about the picture such as fileName,
   * height and width.
   */
  public String toString()
  {
    String output = "Picture, filename " + getFileName() + 
      " height " + getHeight() 
      + " width " + getWidth();
    return output;
    
  }
  public String convertToBinaryStringSTEGO(String mess) {
	  String binary="";
	  String[] binaries= new String[mess.length()];
	  for(int i=0;i<mess.length();i++){
	    String str = Integer.toBinaryString(mess.charAt(i));
	    if(8-str.length()!=0){
	      int zeros= 8-str.length();
	      String zs="";
	      for(int z=0;z<zeros;z++){
	        zs+="0";
	      }
	      binaries[i]= zs+str;
	      continue;
	    }
	    binaries[i]= str;
	  }
	  for(int i=0;i<binaries.length;i++){
	    binary+=binaries[i];
	  }
	  return binary;
	}
  //normal stego
  public void hide() {
	  Scanner scan = new Scanner(System.in);
	  System.out.println("What do you want to encrypt?");
	  String message=scan.nextLine();
	  Pixel[][] pixels = this.getPixels2D();
	 int counter=0;
	 
	 String binary=convertToBinaryStringSTEGO(message);
	  hide:
	  {
		   for(int i=0;i<pixels.length;i++) {
			   for(int z=0;z<pixels[0].length;z++) {
				   if(counter>message.length()-1) {
					   pixels[i][z].setRed(126);
					   pixels[i][z+1].setRed(126);
					   pixels[i][z+2].setRed(126);
					   break hide;
				   }
				   if(pixels[i][z].getBlue()<100&&pixels[i][z].getGreen()<100) {
					   System.out.println("Changes were applied to:"+pixels[i][z]);
					   if(!(counter+1>message.length())) {
					   pixels[i][z].setRed(Integer.parseInt(binary.substring(8*counter,(counter+1)*8),2));
					   
					   }
					   counter++;
			  }  
		  }
	  }
  }
  }
  //normal stego decrypt
  public void decrypt() {
	  Pixel[][] pixels = this.getPixels2D();
	   String message="";
	   int counter=0;
	  decrypt:
	  {
		  for(int i=0;i<pixels.length;i++) {
		  for(int z=0;z<pixels[0].length;z++) {
			  if(counter==4) {
				  break decrypt;
			  }
			  if(pixels[i][z].getRed()==126) {
				  counter++;
			  }
			  if(pixels[i][z].getBlue()<100&&pixels[i][z].getGreen()<100) {
				  message+=(char)pixels[i][z].getRed();
				  
			  }
		  }
		  }
	  }
	  System.out.println(message.substring(0, message.indexOf("~")));
  }
  public int[] replaceLestSigBitSTEGO(String bin, int[] nums) {
		char[] x = bin.toCharArray(); 
		for(int i = 0; i < x.length; i++) {
			if(i < nums.length) {
				char c=(char)nums[i]; 
				String binar=Integer.toBinaryString(c);
				binar=binar.substring(0,binar.length()-1)+x[i]; 
				nums[i]=Integer.parseInt(binar,2);
			}
		}
		return nums;
	}
//This is the leastsigbit stego part
  public void hideBetter() {
	  Scanner scan = new Scanner(System.in);
	  System.out.println("What do you want to encrypt?");
	  String message=scan.nextLine();
	  int [] bluePixels= new int[((message.length())*8)];
	  Pixel[][] pixels = this.getPixels2D();
	  int counter=0;
	  String bin=convertToBinaryStringSTEGO(message);
	  for(int i=0;i<pixels.length;i++) {
		  for(int z=0;z<pixels[0].length;z++) {
			  if((z<bluePixels.length)) {
			  bluePixels[z]=pixels[i][z].getBlue();
		  }
	  }
	  }
	 int[] newBlues= replaceLestSigBitSTEGO(bin,bluePixels);
	 replaceBlues:{
		 for(int i=0;i<pixels.length;i++) {
		  for(int z=0;z<pixels[0].length;z++) {
			  if(counter>3) {
				  break replaceBlues;
			  }
			  if(z<newBlues.length) {
			  pixels[i][z].setBlue(newBlues[z]);
			  }
			  else {
				  pixels[i][z].setBlue(126);
				  counter++;
			  }
	  }
	  }
	 }
  }
  public String StringsFromLeastSigBitsOfIntsSTEGO(int[] binar) {
	  String str="";
	  String word="";
	  for(int i=0;i<binar.length;i++){
	    String binary= Integer.toBinaryString(binar[i]);
	    str+=binary.charAt(binary.length()-1);
	  }
	  
	   for(int i=0;i<str.length()/8;i++){
	    int z = Integer.parseInt(str.substring(8*i,(i+1)*8),2);
	     word+=(char)z;
	   }
	  return word;
	}
//Decrypting the leastsigbit stego
  public void decryptHideBetter() {
	  int counter=0;
	  String decrypted="";
	  Pixel[][] pixels = this.getPixels2D();
	  ArrayList<Integer> blues=new ArrayList<Integer>();
	  decryptor:{
		  for(int i=0;i<pixels.length;i++) {
		  for(int z=0;z<pixels[0].length;z++) {
			  if(counter>3) {
				  break decryptor;
			  }
			  if(pixels[i][z].getBlue()==126) {
				  counter++;
			  }
			  blues.add(pixels[i][z].getBlue());
		  }
	  }
	blues.removeAll(Arrays.asList(126));
	  }
	  int[]bluePixels=convertIntegers(blues);
	  
	  decrypted+=StringsFromLeastSigBitsOfIntsSTEGO(bluePixels);
	  System.out.println(decrypted);
  }
  
  /** Method to set the blue to 0 */
  public void zeroBlue()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        pixelObj.setBlue(0);
      }
    }
  }
  //A helper method I used
  public int[] convertIntegers(ArrayList<Integer> nums)
  {
      int[] prim = new int[nums.size()];
      for (int i=0; i < prim.length; i++)
      {
          prim[i] = nums.get(i).intValue();
      }
      return prim;
  }
  
  public void keepOnlyBlue() {
	  
	    Pixel[][] pixels = this.getPixels2D();
	   
	  for(int i=0;i<pixels.length;i++) {
		  for(int z=0;z<pixels[0].length;z++) {
			  pixels[i][z].setRed(0);
			  pixels[i][z].setGreen(0);
		  }
	  }
  }
  public void negate() {
	  Pixel[][] pixels = this.getPixels2D();
	  for(int i=0;i<pixels.length;i++) {
		  for(int z=0;z<pixels[0].length;z++) {
			  pixels[i][z].setRed(255-pixels[i][z].getRed());
			  pixels[i][z].setBlue(255-pixels[i][z].getBlue());
			  pixels[i][z].setGreen(255-pixels[i][z].getGreen());
		  }
	  }
  }
  public void grayscale() {
	  Pixel[][] pixels = this.getPixels2D();
	  int average=0;
	  for(int i=0;i<pixels.length;i++) {
		  for(int z=0;z<pixels[0].length;z++) {
			  average=(pixels[i][z].getRed()+pixels[i][z].getGreen()+pixels[i][z].getBlue())/3;
			  pixels[i][z].setRed(average);
			  pixels[i][z].setBlue(average);
			  pixels[i][z].setGreen(average);
		  }
	  }
  }
  /** Method that mirrors the picture around a 
    * vertical mirror in the center of the picture
    * from left to right */
  public void mirrorVertical()
  {
    Pixel[][] pixels = this.getPixels2D();
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int width = pixels[0].length;
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0; col < width / 2; col++)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][width - 1 - col];
        rightPixel.setColor(leftPixel.getColor());
      }
    } 
  }
  public void mirrorVerticalRighttoLeft() {
	  Pixel[][] pixels = this.getPixels2D();
	    Pixel leftPixel = null;
	    Pixel rightPixel = null;
	    int width = pixels[0].length;
	    for (int row = 0; row < pixels.length; row++)
	    {
	      for (int col = 0; col < width / 2; col++)
	      {
	    	leftPixel = pixels[row][col];
	        rightPixel = pixels[row][width - 1 - col];
	        leftPixel.setColor(rightPixel.getColor());
	      }
	    } 
  }
  public void mirrorHorizontal() {
	  Pixel[][] pixels = this.getPixels2D();
	    Pixel top = null;
	    Pixel bot = null;
	    int height = pixels.length;
	    for (int row = 0; row < height/2; row++)
	    {
	      for (int col = 0; col < pixels[0].length; col++)
	      {
	    	top = pixels[row][col];
	        bot = pixels[height - 1 - row][col];
	        bot.setColor(top.getColor());
	      }
	    } 
  }
  public void mirrorHorizontalBotToTop() {
	  Pixel[][] pixels = this.getPixels2D();
	    Pixel top = null;
	    Pixel bot = null;
	    int height = pixels.length;
	    for (int row = 0; row < height/2; row++)
	    {
	      for (int col = 0; col < pixels[0].length; col++)
	      {
	    	top = pixels[row][col];
	        bot = pixels[height - 1 - row][col];
	        top.setColor(bot.getColor());
	      }
	    } 
  }
  public void mirrorDiagonal() {
	  Pixel[][] pixels = this.getPixels2D();
	  Pixel topRight = null;
	  Pixel botLeft = null;
	  int lengthForLoop;
	  if(pixels.length<pixels[0].length) {
		  lengthForLoop=pixels.length;
	  }
	  else {
		  lengthForLoop=pixels[0].length;
	  }
	  for (int row = 0; row < lengthForLoop; row++)
	    {
	      for (int col = 0; col < lengthForLoop; col++)
	      {
	    	topRight = pixels[row][col];
	        botLeft = pixels[col][row];
	        botLeft.setColor(topRight.getColor());
	      }
	    } 
  }
  /** Mirror just part of a picture of a temple */
  public void mirrorArms()
  {
    int mirrorPoint = 188;
    Pixel top = null;
    Pixel bot = null;
   
    Pixel[][] pixels = this.getPixels2D();
    
    // loop through the rows
    for (int row = 160; row < 188; row++)
    {
    	
      // loop from 13 to just before the mirror point
      for (int col = 107; col < 169; col++)
      {
        
        top = pixels[row][col];      
        bot = pixels[mirrorPoint - row + mirrorPoint]
        								[col]; 
       bot.setColor(top.getColor());
       
      }
     
    }
    int secondArmPoint=198;
    Pixel secondTop = null;
    Pixel secondBot = null;
    for (int row = 172; row < 198; row++)
    {
    	
      // loop from 13 to just before the mirror point
      for (int col = 239; col < 293; col++)
      {
        
    	  secondTop = pixels[row][col];      
    	  secondBot = pixels[mirrorPoint - row + secondArmPoint]
        								[col]; 
       secondBot.setColor(secondTop.getColor());
       
      }
     
    }
  }
  public void mirrorTemple()
  {
    int mirrorPoint = 276;
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int count = 0;
    Pixel[][] pixels = this.getPixels2D();
    
    // loop through the rows
    for (int row = 27; row < 97; row++)
    {
    	
      // loop from 13 to just before the mirror point
      for (int col = 13; col < mirrorPoint; col++)
      {
        
        leftPixel = pixels[row][col];      
        rightPixel = pixels[row]                       
                         [mirrorPoint - col + mirrorPoint];
        rightPixel.setColor(leftPixel.getColor());
        count++;
      }
      System.out.println(count);
    }
  }
  public void mirrorGull()
  {
    int mirrorPoint = 350;
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int count = 0;
    Pixel[][] pixels = this.getPixels2D();
    
    // loop through the rows
    for (int row = 233; row < 319; row++)
    {
    	
      // loop from 13 to just before the mirror point
      for (int col = 237; col < mirrorPoint; col++)
      {
        
        leftPixel = pixels[row][col];      
        rightPixel = pixels[row]                       
                         [mirrorPoint - col + mirrorPoint];
        rightPixel.setColor(leftPixel.getColor());
        count++;
      }
      System.out.println(count);
    }
  }
  /** copy from the passed fromPic to the
    * specified startRow and startCol in the
    * current picture
    * @param fromPic the picture to copy from
    * @param startRow the start row to copy to
    * @param startCol the start col to copy to
    */
  public void copy(Picture fromPic, 
                 int startRow, int startCol)
  {
    Pixel fromPixel = null;
    Pixel toPixel = null;
    Pixel[][] toPixels = this.getPixels2D();
    Pixel[][] fromPixels = fromPic.getPixels2D();
    for (int fromRow = 0, toRow = startRow; 
         fromRow < fromPixels.length &&
         toRow < toPixels.length; 
         fromRow++, toRow++)
    {
      for (int fromCol = 0, toCol = startCol; 
           fromCol < fromPixels[0].length &&
           toCol < toPixels[0].length;  
           fromCol++, toCol++)
      {
        fromPixel = fromPixels[fromRow][fromCol];
        toPixel = toPixels[toRow][toCol];
        toPixel.setColor(fromPixel.getColor());
      }
    }   
  }
  public void copyTwo(Picture fromPic, 
		          int startRow, int endRow, int startCol, int endCol)
		{
		Pixel fromPixel = null;
		Pixel toPixel = null;
		Pixel[][] toPixels = this.getPixels2D();
		Pixel[][] fromPixels = fromPic.getPixels2D();
		for (int fromRow = startRow, toRow = endRow; 
		  fromRow < fromPixels.length &&
		  toRow < endRow; 
		  fromRow++, toRow++)
		{
		for (int fromCol = startCol, toCol = endCol; 
		    fromCol < fromPixels[0].length &&
		    toCol < endCol;  
		    fromCol++, toCol++)
		{
		 fromPixel = fromPixels[fromRow][fromCol];
		 toPixel = toPixels[toRow][toCol];
		 toPixel.setColor(fromPixel.getColor());
		}
		}   
		}

  /** Method to create a collage of several pictures */
  public void createCollage()
  {
	  
    Picture flower1 = new Picture("flower1.jpg");
    Picture flower2 = new Picture("flower2.jpg");
    this.copy(flower1,0,0);
    this.copy(flower2,100,0);
    this.copy(flower1,200,0);
    Picture flowerNoBlue = new Picture(flower2);
    flowerNoBlue.zeroBlue();
    this.copy(flowerNoBlue,300,0);
    this.copy(flower1,400,0);
    this.copy(flower2,500,0);
    this.mirrorVertical();
    this.write("collage.jpg");
  }
  public void myCollage() {
	  Picture robot = new Picture("robot.jpg");
	  Pixel[][] pixels = robot.getPixels2D();
	    Picture robotmirrored = new Picture(robot);
	    Picture robotGray= new Picture(robot);
	    Picture robotNoBlue= new Picture(robot);
	    robotGray.grayscale();
	    robotNoBlue.zeroBlue();
	    robotmirrored.mirrorDiagonal();
	    this.copy(robot, 0, 0);
	    this.copy(robotGray,100,0);
	    this.copy(robotNoBlue,200,0);
	    this.copy(robotmirrored,300,0);
	    this.copy(robotNoBlue, 400, 0);
	    this.mirrorVertical();
	    this.write("myCollage.jpg");
	  
  }
  
  
  /** Method to show large changes in color 
    * @param edgeDist the distance for finding edges
    */
  public void edgeDetection(int edgeDist)
  {
	Pixel topPixel=null;
	Pixel botPixel=null;
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    Pixel[][] pixels = this.getPixels2D();
    Color rightColor = null;
    Color botColor=null;
    for (int row = 0; row < pixels.length-1; row++)
    {
      for (int col = 0; 
           col < pixels[0].length-1; col++)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][col+1];
        rightColor = rightPixel.getColor();
        topPixel=pixels[row][col];
        botPixel=pixels[row+1][col];
        botColor=botPixel.getColor();
        if ((leftPixel.colorDistance(rightColor) > 
            edgeDist)||(topPixel.colorDistance(botColor)>edgeDist)){
          leftPixel.setColor(Color.BLACK);
          topPixel.setColor(Color.BLACK);
            }
        else 
          leftPixel.setColor(Color.WHITE);
      }
      
    }
    
  }
  
//my version that iterates in a different way and compares top to bottom
  public void edgeDetection2(int edgeDist) {
	
	    Pixel[][] pixels = this.getPixels2D();
	  
	    for(int c=0;c<pixels[0].length;c++) {
	    	for(int r=0;r<pixels.length-1;r++) {
	    		if(pixels[r][c].colorDistance(pixels[r+1][c].getColor())>edgeDist) {
	    			pixels[r][c].setColor(Color.BLACK);
	    			
	    		}
	    		else {
	    			pixels[r][c].setColor(Color.WHITE);
	    		}
	    	}
	    }
  }
  public void fixUndewater() {
	  Pixel[][] pixels = this.getPixels2D();
	  for(int r=0;r<pixels.length-275;r++) {
	    	for(int c=0;c<pixels[0].length-1;c++) {
	    		if(pixels[r][c].getRed()<20&&pixels[r][c].getBlue()<205&&pixels[r][c].getGreen()>150) {
	    			pixels[r][c].setRed(pixels[r][c].getRed()+200);
	    		}
	    	}
	  }
	  for(int r=80;r<150;r++) {
		  for(int c=365;c<465;c++) {
			  if(pixels[r][c].getRed()<30&&pixels[r][c].getBlue()<205&&pixels[r][c].getGreen()>150) {
	    			pixels[r][c].setRed(pixels[r][c].getRed()+200);
	    		}
	    	}
		  }
	  for(int r=227;r<275;r++) {
		  for(int c=528;c<542;c++) {
			  if(pixels[r][c].getRed()<26&&pixels[r][c].getBlue()<152&&pixels[r][c].getGreen()<159) {
	    			pixels[r][c].setRed(pixels[r][c].getRed()+200);
	    		}
	    	}
		  }
	  for(int r=209;r<236;r++) {
		  for(int c=480;c<528;c++) {
			  if(pixels[r][c].getRed()<26&&(pixels[r][c].getBlue()<153&&pixels[r][c].getBlue()>148)&&(pixels[r][c].getGreen()<165&&pixels[r][c].getGreen()>153)) {
	    			pixels[r][c].setRed(pixels[r][c].getRed()+200);
	    		}
	    	}
		  }
	  
  }
  
  /* Main method for testing - each class in Java can have a main 
   * method 
   */
  public static void main(String[] args) 
  {
    Picture beach = new Picture("beach.jpg");
    beach.explore();
    beach.keepOnlyBlue();
    beach.explore();
  }
  
} // this } is the end of class Picture, put all new methods before this
