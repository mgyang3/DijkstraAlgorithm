
import java.io.*;
import java.util.*;


/*	WordNode will store individual words, while WordPair will be the various edges

 */
public class WordGraph {
	private WordNode[] wordNodes;
	private WordPair[] wordPairs;
	private String[] stringNodes, stringPairs;
	private Tokenizer list;
	private HashNode single;//used to count the single words in a file
	private HashPair pair;//used to count pairs in a file

	public WordGraph(String file) throws IOException{
		list = new Tokenizer(file);
		single = new HashNode();
		pair = new HashPair();
		for(int i=0;i<list.wordList().size();i++){
			single.put(list.wordList().get(i), 1);//adding all into the hashnode table
		}//ends for
		for(int i=0;i<list.wordList().size()-1;i++){//adding into the hashPair table
			pair.put(list.wordList().get(i)+" "+list.wordList().get(i+1), 1);
		}//ends for

		ArrayList<WordNode> temp = new ArrayList<WordNode>();
		//putting hash entries into array list, no duplicates
		for(int i=0;i<single.getLength();i++){
			if(single.getList(i)!=null){
				LinkedList<WordNode> l = single.getList(i);
				for(int j=0;j<l.size();j++){
					temp.add(l.get(j));//adding into the arraylist so it can be sorted
				}//ends for
			}//the table exists
		}//ends for
		wordNodes= new WordNode[temp.size()];
		stringNodes = new String[temp.size()];
		for(int i=0;i<temp.size();i++){
			wordNodes[i]=temp.get(i);
			stringNodes[i]=temp.get(i).getKey();
		}//ends for

		//wordPair counting
		ArrayList<WordPair> tmp = new ArrayList<WordPair>();
		//putting word pairs into array list, no duplicates
		for(int i=0;i<pair.getLength();i++){
			if(pair.getList(i)!=null){
				LinkedList<WordPair> l = pair.getList(i);
				for(int j=0;j<l.size();j++){
					tmp.add(l.get(j));//adding into the arraylist so it can be sorted
				}//ends for
			}//the table exists
		}//ends for

		wordPairs= new WordPair[tmp.size()];
		stringPairs = new String[tmp.size()];
		Collections.sort(tmp);
		for(int i=0;i<tmp.size();i++){
			wordPairs[i]=tmp.get(i);
			stringPairs[i]=tmp.get(i).getKey();
		}//ends for
		connectEdges();
	}//ends constructor

	//try to connect all the nodes with edges
	public void connectEdges(){
		for(int i=0;i<wordNodes.length;i++){
			String[] locater = this.nextWords(wordNodes[i].getKey());//locates all the words that come after a word
			for(int j=0;j<locater.length;j++){
				String s = wordNodes[i].getKey()+" "+locater[j];
				if(!wordNodes[i].getKey().equals(locater[j]))//don't let it connect back to itself
					wordNodes[i].addToAdjacent(findPair(s));//giving each word node edges that point to the following word

			}//create the edges
		}//ends for
	}
	public WordPair findPair(String w){//assumes first word is base word, and second word is following
		for(int i=0;i<wordPairs.length;i++){
			if(wordPairs[i].getKey().equals(w))
				return wordPairs[i];
		}//ends for
		return null;
	}

	public WordNode find(String w){//locates a particular node in the array holding all of them
		for(int i=0;i<wordNodes.length;i++)
			if(wordNodes[i].getKey().equals(w))
				return wordNodes[i];
		return null;
	}
	public int wordCount(String w){
		if(w.contains(" ")){
			int counter=0;
			counter=pair.get(w);
			return counter;//is a word pair
		}//ends for
		else{int counter=0;//single word
		counter=single.get(w);
		return counter;}
	}//ends wordCount

	public int inDegree(String w){//number of words that precede String w

		return prevWords(w).length;

	}
	public int outDegree(String w){//number of words that come after string w
		return nextWords(w).length;
	}
	public String[] prevWords(String w){//words coming before a specific word
		ArrayList<String> container = new ArrayList<String>();
		for(int i=0;i<wordPairs.length;i++){
			if(wordPairs[i].getSecondWord().equals(w)){
				container.add(wordPairs[i].getFirstWord());
			}//ends if
		}//ends for
		String[] temp = new String [container.size()];
		for(int i=0;i<container.size();i++)
			temp[i]=container.get(i);
		return temp;
	}
	public String[] nextWords(String w){//words coming after a specific word
		ArrayList<String> container = new ArrayList<String>();
		for(int i=0;i<wordPairs.length;i++){
			if(wordPairs[i].getFirstWord().equals(w)){
				container.add(wordPairs[i].getSecondWord());//adds the unique word
			}//ends if
		}//ends for
		String[] temp = new String [container.size()];
		for(int i=0;i<container.size();i++)
			temp[i]=container.get(i);
		return temp;
	}
	public double wordSeqCost(String[] wordSeq){
		return pathCost(wordSeq);
	}

	public double pathCost(String[] words){
		if(words.length<=1)
			return 0;//cost could not be calculated
		int N=0;//will hold number of words in source text. found by adding up all the counts of each wordNode
		for(int i=0;i<wordNodes.length;i++){
			N=N+wordNodes[i].getValue();
		}//ends for
		double x=0;
		x=Math.log(N/wordCount(words[0]));
		for(int i=0;i<words.length-1;i++){
			x=x+Math.log((wordCount(words[i]))/(wordCount(words[i]+" "+words[i+1])));//adding all the values
		}//ends for
		//should try to minimize x to find highest probability 
		return x;
	}
	/*
	 * you want to return the start word and the endword
	 * First find shortest path, and see if endWord is at the 
	 * if you dont reach it within N moves, you just return 
	 */
	public String generatePhrase(String startWord, String endWord, int N){
		PriorityQueue<WordNode> visit = new PriorityQueue<WordNode>();

		String complete ="";
		String source=startWord.toLowerCase();
		endWord=endWord.toLowerCase();
		WordNode first = find(source);
		if(first==null)
			return "Word is not in document";
		first.setDistance(0);
		visit.add(first);
		//find(source).setParent(new WordNode("THE FIRST PARENT"));
		//visit.add(find(source));//adds first node to be inserted
		WordNode last=new WordNode();
		mainLoop:
			while(!visit.isEmpty()){//source will change in this loop

				WordNode walk=visit.poll();
				source=walk.getKey();
				ArrayList<WordPair> neighbors = walk.getEdge();//grabs neighbor of source word

				for(int i=0;i<neighbors.size();i++){
					WordNode w = find(neighbors.get(i).getSecondWord());
					double cost = pathCost(new String[]{source,w.getKey()});
					double newDist = cost+walk.getDistance();
					if(newDist <w.getDistance()){
						visit.remove(w);
						w.setDistance(newDist);
						w.setParent(walk);
						w.setDepth(walk.getDepth()+1);
						visit.add(w);
					}//ends if

				}//ends for

			}//ends while
		last=find(endWord);//begin backwords search
		if(last.getDepth()>N){
			return "";

		}
		if(last.getValue()==0){//last did not change, so didn't locate within N levels
			return "";
		}

		LinkedList<String> reverser = new LinkedList<String>();
		last=find(endWord);//begin backwords search
		//System.out.println("Testing:"+find("and").getParent().getKey());
		while(last!=null){//adding in reverse
			String s=last.getKey();
			//System.out.println(s);
			reverser.addFirst(s);//adds into first position
			//System.out.println(last.getParent().getKey());
			last=last.getParent();//moves to the next
		}//ends while		

		for(int i=0;i<reverser.size();i++){
			complete=complete+ " "+reverser.get(i);
		}//ends if

		return complete.trim();
	}

	public String generatePhrase(String startWord, String endWord, int N, int r){
	String complete=generatePhrase(startWord,endWord, N);
		complete=complete.trim();
		//checking for repeats
		String[] words = complete.split(" ");//splits it along the spaces
		int[] repeats= new int[words.length];
		for(int i=0;i<words.length-1;i++){
			if(words[i].equals(words[i+1]))
				repeats[i]++;
		}//ends for
		complete="";
		for(int i=0;i<words.length;i++){
			if(repeats[i]>r){
				words[i]="";
			}//ends if
			else{
				complete=complete+" "+words[i];
			}
		}//ends for
		return complete.trim();
	}
	
	public static void main(String[] args) throws IOException {
		WordGraph wg = new WordGraph("Alice in Wonderland.txt");
		File output = new File("main.txt");
		PrintWriter printer = new PrintWriter(output);
		printer.write("Stats for Alice in Wonderland"+System.getProperty("line.separator"));
		printer.write("The wordCount of alice:"+wg.wordCount("alice")+System.getProperty("line.separator"));
		printer.write("Number of words that come before alice:"+wg.inDegree("alice") +System.getProperty("line.separator"));	
		printer.write("The words that come before alice:"+Arrays.toString(wg.prevWords("alice"))+ System.getProperty("line.separator"));
		printer.write("Number of words that come after alice:"+wg.outDegree("alice") +System.getProperty("line.separator"));	
		printer.write("The words that come after alice:"+Arrays.toString(wg.nextWords("alice"))+ System.getProperty("line.separator"));
		printer.write("Generating a phrase no longer than four words:" + wg.generatePhrase("table","little",4)+System.getProperty("line.separator"));

		wg = new WordGraph("Alice in Wonderland.txt");
		printer.write("Extra Credit|Generating phrase with a limited number of repeats:" + wg.generatePhrase("come","finish",4,1)+System.getProperty("line.separator"));
		
		printer.close();

	}//ends main


}//ends class
