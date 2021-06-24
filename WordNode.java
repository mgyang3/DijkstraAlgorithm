import java.util.ArrayList;


public class WordNode implements Comparable<WordNode>{
	private String word;
	private int count,numberofVisits;
	private ArrayList<WordPair> adjacent;
	private boolean finalized;
	private double distance=Double.MAX_VALUE;
	private WordNode parent;
	private int depth=0;

	public WordNode(String s, int x){
		word=s;
		count=x;
		adjacent=new ArrayList<WordPair>();
		finalized=false;
		numberofVisits=0;
		parent=null;
	}
	public WordNode(){
		word="";
		count=0;
		adjacent = new ArrayList<WordPair>();
		finalized=false;
		numberofVisits=0;
		parent=null;
	}
	public WordNode(String s){
		word=s;
		count=0;
		adjacent = new ArrayList<WordPair>();
		finalized=false;
		numberofVisits=0;
		parent=null;
	}
	public void setVisits(int x){
		numberofVisits=x;
	}
	public int getVisits(){
		return numberofVisits;
	}
	public void setDistance(double x){
		distance=x;	
	}
	public double getDistance(){
		return distance;
	}
	public int getDepth(){
		return depth;
	}
	public void setDepth(int x){
		depth=x;
	}
	public String getKey(){
		return word;
	}
	public void setParent(WordNode x){
		parent=x;
	}
	public WordNode getParent(){
		return parent;
	}

	public int getValue(){
		return count;
	}

	public void setValue(int x){
		count=x;
	}
	public void setAdjacent(ArrayList<WordPair> x){
		adjacent=x;
	}
	public ArrayList<WordPair> getEdge(){
		return adjacent;
	}
	public void setVisit(boolean b){
		finalized=b;
	}
	public boolean getVisit(){
		return finalized;
	}
	public void addToAdjacent(WordPair x){
		adjacent.add(x);
	}

	public boolean finalized(){
		return finalized;
	}
	public void setFinalized(boolean f){
		finalized=f;
	}
	@Override
	public int compareTo(WordNode o) {
		return 	Double.compare(distance,o.getDistance());
	}


}//ends class
