/*
   PHYO THANT KO, 5363901
  
   Using jdk 7
 
   Compile Instructions
   javac Chord.java
   javac Node.java
   javac NodeImp.java
   
   Run Instruction
   java Chord <datafilename>.dat
    
 */

import java.lang.reflect.Array;
import java.util.Arrays;

public class Chord {

	public static void main(String[] args) {

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
