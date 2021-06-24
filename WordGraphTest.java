import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class WordGraphTest {

	@Test
	public void testLargeFileConstructor() throws IOException{
		WordGraph wg = new WordGraph("Alice in Wonderland.txt");
		assertTrue("creates a new word graph",true);
	}
	@Test
	public void testFileConstructor() throws IOException{
		WordGraph wg= new WordGraph("test.txt");
		assertTrue("Creates a word graph from a small file", true);
	}

	@Test
	public void testConnectEdges() throws IOException{

		WordGraph wg = new WordGraph("test.txt");
		WordNode w = wg.find("maid");
		assertEquals("Should only have one edge", 1, w.getEdge().size());
		assertEquals("Checks maid only has will in the edge","will",w.getEdge().get(0).getSecondWord());

	}
	@Test 
	public void testWordCount() throws IOException{
		WordGraph wg = new WordGraph("test.txt");
		assertEquals("correctly takes into account a word that isn't in the document", -1, wg.wordCount("boogala"));
		assertEquals("Correctly counts the number of times a word appears in the source",
				2,wg.wordCount("they"));
	}
	@Test
	public void testInDegreeandPrevWords()throws IOException{
		WordGraph wg = new WordGraph("test.txt");
		int x = wg.inDegree("days");
		int y= wg.inDegree("a");
		String[] j = wg.prevWords("days");
		String[] k= wg.prevWords("a");

		assertEquals("Correctly finds number of times there are unique words in front of a specific word", 1, x);
		assertEquals("Correctly finds number of times there are unique words in front of a specific word", 2, y);
		assertEquals("Correctly find number of unique words in front of a specific word", 1, j.length);
		assertEquals("Correctly find number of unique words in front of a specific word", 2, k.length);
	}
	@Test 
	public void testOutWordsandNextWords() throws IOException{
		WordGraph wg = new WordGraph("test.txt");
		int x = wg.outDegree("days");
		int y= wg.outDegree("a");
		String[] j = wg.nextWords("days");
		String[] k= wg.nextWords("a");

		assertEquals("Correctly finds number of times there are unique words following of a specific word", 1, x);
		assertEquals("Correctly finds number of times there are unique words following of a specific word", 2, y);
		assertEquals("Correctly find number of unique words after a specific word", 1, j.length);
		assertEquals("Correctly find number of unique words after a specific word", 2, k.length);

	}
	@Test
	public void testWordSeqCount() throws IOException{
		WordGraph wg= new WordGraph("Alice in Wonderland.txt");// a portion of alice in wonderland 
		double d= wg.wordSeqCost(new String[]{"alice","think","it","so"});
		double x = wg.wordSeqCost(new String[]{"until"});
		assertTrue("Returns a value that is larger than 0, meaning this sequence could be found in the text", d>0);
	}
	@Test
	public void testGeneratePhrase() throws IOException{
		WordGraph wg=new WordGraph("test2.txt");
		String s=wg.generatePhrase("cow", "jump",3);
		String[] l=s.split(" ");
		assertTrue("Length of the string is less or equal than the given N value",l.length<=3);
		
		wg=new WordGraph("test2.txt");
		String x = wg.generatePhrase("The","something", 5);
		l=x.split(" ");
		assertTrue("Length of the string is less or equal than the given N value",l.length<=5);
		
		wg=new WordGraph("test2.txt");
		x = wg.generatePhrase("The","over", 5,1);
		l=x.split(" ");
		assertTrue("Length of the string is less or equal than the given N value",l.length<=5);
		
		wg=new WordGraph("test2.txt");
		x=wg.generatePhrase("the","rabid",4);
		assertEquals("Length of the string will be 0 since rabid is too far away from the",x,"");
		
		double z = wg.wordSeqCost(new String[]{"alice","was","nothing","so"});
		double f= wg.wordSeqCost(new String[]{"alice","so"});
		assertEquals("A specific phrase has a lower cost than another phrase",z>f ,true);
	}

}
