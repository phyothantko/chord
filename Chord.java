// PHYO THANT KO, 5363901
// javac <filename>.java
// Using jdk 7

import java.lang.reflect.Array;
import java.util.Arrays;

public class Chord {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//NodeImp.readfile("C:\\Users\\Phyo\\workspace\\Chord\\src\\myFile.txt");
		if (args.length == 1)
		{
			NodeImp.readfile(args[0]);
		}
		else
		{
			System.out.println("One arugment is allowed");
		}	
		
	}

}
