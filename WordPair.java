

//should be directed
public class WordPair implements Comparable<WordPair>{
	String baseWord, word2, wholeWord;
	int count;
	double cost;

	public WordPair(String s1, String s2, int x){
		baseWord=s1;
		word2=s2;
		count=x;
		wholeWord = s1+ " "+s2;
		cost=0;
	}
	public WordPair(String s, int x){
		wholeWord=s;
		count=x;
		String[] u = s.split(" ");
		if(u.length>2)
			System.out.println("More than two words in this string");
		if(u.length==2){
			baseWord=u[0];
			word2=u[1];
		}//ends if
		cost=0;
	}
	public WordPair(String s1, String s2){
		baseWord=s1;
		word2=s2;
		count=0;
		wholeWord = s1+ " "+s2;
		cost=0;
	}	
	public WordPair(String s){
		wholeWord=s;
		count=0;
		String[] u = s.split(" ");
		if(u.length>2)
			System.out.println("More than two words in this string");
		if(u.length==2){
			baseWord=u[0];
			word2=u[1];
		}//ends if
		cost=0;
	}
	public void setCost(double h){
		cost=h;
	}
	public double getCost(){
		return cost;
	}
	public String getKey(){
		return wholeWord;
	}
	public String getFirstWord(){
		return baseWord;
	}
	public String getSecondWord(){
		return word2;
	}
	public int getValue(){
		return count;
	}

	public void setValue(int s){
		count=s;
	}
	@Override
	public int compareTo(WordPair q) {
		WordPair x= q;
		if(count>x.getValue())
			return -1;//this hash entry has a larger value
		else if(count<x.getValue())
			return 1;//this hash entry has a smaller value
		else if(x.getKey().compareTo(wholeWord)>0)
			return -1;//values are equal but this wordNode is alphabetically higher
		else return 1;
	}
}//ends class
