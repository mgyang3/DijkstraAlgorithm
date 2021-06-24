import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class WordPairTest {

	@Test
	public void testConstructor1(){
		WordPair w = new WordPair("alice","thought", 5);
		assertEquals("Created a word pair with a count of 5",5,w.getValue());
		assertEquals("Created a word pair(edge) between alice and thought","alice thought",w.getKey());
	}
	@Test
	public void testConstructor2(){
		WordPair w = new WordPair("alice thought", 5);
		assertEquals("Created a word pair with a count of 5",5,w.getValue());
		assertEquals("Created a word pair(edge) between alice and thought","alice thought",w.getKey());
	}
	@Test
	public void testConstructor3(){
		WordPair w = new WordPair("alice","thought");
		assertEquals("Created a word pair with a count of 0",0,w.getValue());
		assertEquals("Created a word pair(edge) between alice and thought","alice thought",w.getKey());
	}
}
