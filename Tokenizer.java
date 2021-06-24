import java.io.*;
import java.util.*;

public class Tokenizer {
	private ArrayList<String> list;
	private BufferedReader reader;

	public Tokenizer(String s) throws IOException{
		list = new ArrayList<String>();
		BufferedReader reader = new BufferedReader(new FileReader(s));
		String line;
		while((line=reader.readLine())!=null){
			if(line.equals("\n")){
				line=reader.readLine();
				System.out.println(line+"|hi!");
			}//end if, hopefully skips spaces
			line=line.trim().toLowerCase();//removes extra space and changes to lowercase

			String[] split=line.trim().split("\\s+");
			for(int i=0;i<split.length;i++){
				split[i]=split[i].replaceAll("\\W", "").replaceAll("_", "").trim();//should take out all punctuation
			}//runs through each word in a line
			for(int i=0;i<split.length;i++){
				if(!split[i].equals(""))
					list.add(split[i]);//adds fixed word to the list
			}//ends for

		}//still has a line to read
		reader.close();
	}//constructor that specifies file
	public Tokenizer(String[] s){
		list = new ArrayList<String>();
		for(int i=0;i<s.length;i++){
			String[] x=s[i].trim().split("\\s+");//splits at the spaces
			for(int j=0;j<x.length;j++){
				x[j]=x[j].trim().toLowerCase().replaceAll("\\W", "");
				list.add(x[j]);
			}//ends for loop that splits up words inside each array
		}//will go through each array
	}//constructor gives an array of strings

	public ArrayList<String> wordList(){
		return list;
	}//returns word list created by constructors

}//ends class




