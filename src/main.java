import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class main {
	public static void main(String[] args) throws FileNotFoundException {
		
		int press_user=0;
		double sumTime = 0;
		double minTime = 9999999;
		double maxTime = 0;
		double comparetime = 0;	
		//PATHS
				//
				File fileDir = new File("/Users/denizk7/eclipse-workspace/Homework/src/stop_words_en.txt");
				String pathsearchtxt = "/Users/denizk7/eclipse-workspace/Homework/src/search.txt";
				File pathbbc = new File("/Users/denizk7/eclipse-workspace/Homework/src/bbc");
				//
				//
		String txt = "";	
		String linesearchtxt = "";			
		File filesearch;
		int out1= 0;
		int out2 = 0;
		double out3 = 0;
		double indexingtime = 0;
		Scanner input = new Scanner(System.in);
		while(true) {
			System.out.println(" --> For Simple Summation Function (SSF) Press 1  \n --> For Polynomial Accumulation Function (PAF) Press 2");
			 out1 = input.nextInt();
			System.out.println(" --> For Linear Probing Press 1  \n --> For Double Hashing Press 2");
			out2 = input.nextInt();
			System.out.println(" --> For load factor 0.5 press 1\n --> For load factor 0.8 press 2");
			out3 = input.nextDouble();
			if(out3==1)
				out3= 0.5;
			else if (out3==2)
				out3=0.8;
			if((out1 == 1 || out1 == 2)&&(out2 == 1 || out2 == 2)&&(out3 == 0.5 || out3 == 0.8))
			break;							
		}
		
		
		String DELIMITERS = "[-+=" +

		        " " +        //space

		        "\r\n " +    //carriage return line fit

				"1234567890" + //numbers

				"’'\"" +       // apostrophe

				"(){}<>\\[\\]" + // brackets

				":" +        // colon

				"," +        // comma

				"‒–—―" +     // dashes

				"…" +        // ellipsis

				"!" +        // exclamation mark

				"." +        // full stop/period

				"«»" +       // guillemets

				"-‐" +       // hyphen

				"?" +        // question mark

				"‘’“”" +     // quotation marks

				";" +        // semicolon

				"/" +        // slash/stroke

				"⁄" +        // solidus

				"␠" +        // space?   

				"·" +        // interpunct

				"&" +        // ampersand

				"@" +        // at sign

				"*" +        // asterisk

				"\\" +       // backslash

				"•" +        // bullet

				"^" +        // caret

				"¤¢$€£¥₩₪" + // currency

				"†‡" +       // dagger

				"°" +        // degree

				"¡" +        // inverted exclamation point

				"¿" +        // inverted question mark

				"¬" +        // negation

				"#" +        // number sign (hashtag)

				"№" +        // numero sign ()

				"%‰‱" +      // percent and related signs

				"¶" +        // pilcrow

				"′" +        // prime

				"§" +        // section sign

				"~" +        // tilde/swung dash

				"¨" +        // umlaut/diaeresis

				"_" +        // underscore/understrike

				"|¦" +       // vertical/pipe/broken bar

				"⁂" +        // asterism

				"☞" +        // index/fist

				"∴" +        // therefore sign

				"‽" +        // interrobang

				"※" +          // reference mark

		        "]";
		
		


		HashedDictionary<String, String> hashTable = new HashedDictionary<>();		
		//STOP WORDS
		ArrayList <String> stopwordsArrayList = new ArrayList<>(); 		
        
        try {
            input = new Scanner(fileDir);
            while (input.hasNext()) {
            	stopwordsArrayList.add(input.next());
               
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
		//
		//INDEXING
        System.out.println(" --> Loading... ");
		
		File[] mainCategories=pathbbc.listFiles();
		long start_indexing =System.nanoTime();
			for (int i = 1; i < mainCategories.length; i++)//FOR MAC i AND j ARE EQUALS TO 1
			{
			File[] categoryFiles= mainCategories[i].listFiles();
			
				for (int j=1; j< categoryFiles.length; j++) {	//FOR MAC i AND j ARE EQUALS TO 1				
					String[] pathSplit=categoryFiles[j].toString().split("/");//FOR WINDOWS \\\\ FOR MAC/
					String value=pathSplit[pathSplit.length-2]+"  "+pathSplit[pathSplit.length-1];
			
			Scanner in=null;
			try {
			in = new Scanner (categoryFiles[j]);
			} catch (FileNotFoundException e)
			{
			
			e.printStackTrace();
			}
			in.useDelimiter(DELIMITERS) ;
			
				while(in.hasNext()){
					String next=in.next().toLowerCase();
					if (!next.equals("") && !stopwordsArrayList.contains(next))  {
						hashTable.add(next, value, out1, out2, out3);
					}
				}
			
	
				}
			}
			long end_indexing = System.nanoTime();
			indexingtime = end_indexing -start_indexing;
			//
			//IMPLEMENT SEARCH TXT INTO ARRAY

			if (new File("search. txt").exists()) {
			    filesearch = new File("search.txt");
			} else 	filesearch = new File(pathsearchtxt);
			Scanner read_search = new Scanner(filesearch, "UTF-8");
			while (read_search.hasNextLine()) {
				linesearchtxt  = read_search.nextLine();
			              
			    txt += linesearchtxt  + " ";
			}
			String words[] = txt.split(" ");
			
			read_search.close();
			//LAST OPEARTION USER
			Scanner input_from_user = new Scanner(System.in);
	
			while (true) {
				System.out.println("\n\n\nWhich operation want to compute \n\n --> For search press 1 \n\n --> For remove press 2 \n\n -->For search.txt press 3<--\n\n For quit press 4");
				press_user = input_from_user.nextInt();
				String inputt;
				if(press_user==1) {
					System.out.println("Enter your key");
					inputt = input_from_user.next();
					hashTable.getValue(inputt, out2, out1);
				}
				else if(press_user ==2) {
					System.out.println("Enter your key");
					inputt = input_from_user.next();
					hashTable.remove(inputt, out2, out1);
				}				
				else if (press_user==4 || press_user ==3) break;
				else {
					System.out.println("You entered wrong input Try again!");
				}
				
			}
			//SEARCH1000
			if(press_user==3) {
				
				for (int i = 0; i < words.length; i++) {
				   long start =System.nanoTime();
				  boolean checkKey=  hashTable.getForSearch((String)words[i], out2, out1);
				    long end = System.nanoTime();
				    sumTime += (end - start);
				    if(checkKey) {
				    	comparetime = (end - start);
				    	if (comparetime > maxTime)
						       maxTime = comparetime;
					    if (comparetime < minTime)
					       minTime = comparetime;
					    
				    }			    			          			          			            			                     
				}
				sumTime = sumTime / 1000;
				System.out.println("\t\t\t --> Total Collision: "+ hashTable.totalCollisionvalue());
				System.out.println("\t\t\t --> Indexing Time: "+indexingtime);
				System.out.println("\t\t\t --> Average Search Time : " + sumTime);
				System.out.println("\t\t\t --> Minimum Search Time :" + minTime);
				System.out.println("\t\t\t --> Maximum Search Time :" + maxTime);
			}
			
				
			
	}
	
}
