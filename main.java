import java.lang.reflect.Array;
import java.util.Arrays;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		

		Chord.InitChord(5);
		Chord.addPeer(7);
		Chord.addPeer(3);
		Chord.removePeer(3);
		
		
		Chord.addPeer(12);
		Chord.addPeer(3);
		Chord.addPeer(9);
		Chord.removePeer(3);
		Chord.addPeer(17);
		Chord.Insert("THIS IS A TEST");
		Chord.Insert("Markus Hagenbuchner");
		Chord.Insert("CSCI319");
		Chord.Delete("THIS IS A TEST");
		Chord.Print(12);
		Chord.removePeer(0);
		Chord.Print(7);
		Chord.Print(9);
		Chord.Print(12);
		Chord.Print(17);
		
	
		
		
		
	}

}
