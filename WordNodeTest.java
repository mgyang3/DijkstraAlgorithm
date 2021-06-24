import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class WordNodeTest {

	@Test
	public void testConstructor1(){
		WordNode x= new WordNode("alice", 4);
		assertEquals("Created a node with count of 4",4,x.getValue());
		assertEquals("Created a node with a key of alice","alice",x.getKey());
	}
	public void testConstructor2(){
		WordNode x= new WordNode("alice");
		assertEquals("Created a node with count of 0",0,x.getValue());
		assertEquals("Created a node with a key of alice","alice",x.getKey());
	}
	public void testConstructor3(){
		WordNode x= new WordNode();
		assertEquals("Created a node with count of 0",0,x.getValue());
		assertEquals("Created a node with a key of ''","",x.getKey());
	}
	
	
}//ends class
