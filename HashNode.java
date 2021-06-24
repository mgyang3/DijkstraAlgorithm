import java.util.*;

public class HashNode {
	private LinkedList<WordNode>[] table;
	//might have to mod by table length
	public HashNode(){
		table = new LinkedList[20];//creating table of reasonable size
	}

	public HashNode(int x){
		table = new LinkedList[x];//creating table of specified size
	}

	public void put(String key, int value){
		int i=key.hashCode()%table.length;//should make a hashcode to be inserted
		i=Math.abs(i);//takes absolute value
		while(i>table.length){
			resize();//keeps changing length of table if hashCode is larger than length
		}
		WordNode insert=new WordNode(key,value);
		if(table[i]==null){
			table[i]=new LinkedList<WordNode>();
			table[i].add(insert);
			return;
		}//special case of nothing in array
		LinkedList<WordNode> l = table[i];
	
		for(int i1=0;i1<table[i].size();i1++){
			if(l.get(i1).getKey().equals(key)){
				table[i].get(i1).setValue(table[i].get(i1).getValue()+value);
				return;
			}//what happens if the value is already in the table

		}//increases the number
		table[i].add(insert);//what to do if key is not found in linked list
	}//inserting into any location

	public void put(String key, int value, int hashCode){
		while(hashCode%table.length>table.length)
			resize();//keeps changing length of table if hashCode is larger than length
		int i=Math.abs(hashCode%table.length);
		WordNode insert = new WordNode(key,value);
		LinkedList<WordNode> l=table[i];
		if(table[i]==null){
			table[i]=new LinkedList<WordNode>();
			table[i].add(insert);
			return;
		}//special case of nothing in array
		for(int i1=0;i1<table[i].size();i1++){
			if(l.get(i1).getKey().equals(key)){
				table[i].get(i1).setValue(table[i].get(i1).getValue()+1);
				return;
			}//what happens if the value is already in the table

		}//increases the number
		table[i].add(insert);
	}//inserting directly into location

	public void update(String key, int value){
		int finder=key.hashCode()%table.length;
		if(finder>=table.length){
			put(key,value,finder);
			return;
		}
		LinkedList<WordNode> l = table[finder];
		if(l==null){
			table[finder]=new LinkedList<WordNode>();
			table[finder].add(new WordNode(key,value));
			return;

		}//what happens if the location is empty
		for(int i=0;i<l.size();i++){
			if(l.get(i).getKey().equalsIgnoreCase(key)){
				l.get(i).setValue(value);//should only occur once
				return;
			}//ends if
		}//ends for-loop

	}//changing the value of a specific key

	public int get(String key){
		int found=0;
		int finder=key.hashCode()%table.length;
		finder=Math.abs(finder);
		if(finder>=table.length)
			return -1;//if hashcode is not in array, then returns -1
		LinkedList<WordNode> l = table[finder];
		if(l==null){
			return -1;
		}//ends if

		for(int i=0;i<l.size();i++){
			if(l.get(i).getKey().equalsIgnoreCase(key)){
				found=l.get(i).getValue();
				return found;
			}//ends if
		}//walks through an array

		return -1;
	}//returning value of a key

	public void resize(){
		LinkedList<WordNode>[] newTable= new LinkedList[2*table.length];
		for(int i=0;i<table.length;i++){
			if(table[i]!=null){
				LinkedList<WordNode> tmp = table[i];
				String s=tmp.get(0).getKey();
				int finder = s.hashCode()%newTable.length;
				newTable[finder]=tmp;
			}//ends if
			
		}//ends for
 
	}//if table is too small, double the size of table

	public int get(String key, int hashCode) {
		int found=0;
		int finder = hashCode%table.length;
		if(finder>=table.length)
			return -1;//if hashcode is not in array, then returns 0
		LinkedList<WordNode> l = table[finder];
		if(l==null)
			return -1;
		for(int i=0;i<l.size();i++){
			if(l.get(i).getKey().equalsIgnoreCase(key)){
				found=l.get(i).getValue();
				return found;
			}//ends if
		}//walks through an array

		return -1;

	}//uses a hashcode to locate
	
	public int getLength(){
		return table.length;
	}
	
	public LinkedList<WordNode>[] getTable(){
		return table;
	}
	
	public LinkedList<WordNode> getList(int x){
		return table[x];
	}
}//ends class
