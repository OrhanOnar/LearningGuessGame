package tobb.etu.decisionTree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class AnimalPredictor {
	
	//use this as the decision tree (DT) in animal prediction game
	private static DecisionTree DT = new DecisionTree();

	//traverse the decision tree to predict the animal.
	//update decision tree if the guess is wrong.
	public static void guess_and_update(DecisionTree DT) {
		Node cur = DT.getRoot();
		Scanner s = new Scanner(System.in);
		do {
			if (cur.isExternal()) {
					System.out.println("Bence tuttugunuz hayvan " + cur.getElement());
					System.out.println("Tahminim dogru mu?");
					String ans = s.nextLine();
					if (ans.equals("evet")) {
						System.out.println("Oley!");
						return;
					}
				else { //have to update DT
					System.out.println("Ne tutmustunuz?");
					String h = s.nextLine();
					System.out.println("Bana " + h + "'yi " + cur.getElement() + "'den ayirmami saglayacak bir evet/hayir sorusu soyler misiniz?");
					System.out.println("(Evet cevabi " + h + " icin olmalidir.)");
					String q = s.nextLine();
					DT.insertYes(cur,h);
					DT.insertNo(cur,cur.getElement());
					cur.setElement(q);
					System.out.println(h + " 'yi ogrendim.");
					return;
				}
			}
			else {
				System.out.println(cur.getElement());
				String ans = s.nextLine();
				if (ans.equals("evet"))
					cur = cur.getLeft();
				else
					cur = cur.getRight();				
			}
			
		} while (true);		
	}
	
	//simulates the animal prediction game by reading the interactions from a file
	//returns the final prediction corresponding to the decision tree and query file.
	//if there is any mismatch between DT and query file return empty string.
	//DTFile : Decision Tree File
	//queryFile : list of interactions to reach to a decision.
	public static String guess(String DTFile, String queryFile) throws IOException {
		
		
		DT.load(DTFile);
		
		
		Node root=DT.getRoot();    
		
		 File file = new File(queryFile);
		  BufferedReader br = new BufferedReader(new FileReader(file));
		  
		  
		  
int sayac=0;
ArrayList<String> query=new ArrayList();         // Arraylist attim
int i=0;
		  String st="";
		  
		  while ((st = br.readLine()) != null) {
			  
	
				  sayac++;
				  
				  
				  query.add(st);
				  
			  }
 
		br.close();
		
					while(i<sayac+1) {
						
						if(root.isExternal()){
							
							return root.getElement();
						
						}
						
					
						
		if( root.getElement().equals(query.get(i))){
		

					if(query.get(i+1).equals("hayir")) {
						root=root.getRight();
						i=i+2;
					}
					
					else    {
						root=root.getLeft();
						i=i+2;
						
					}
					
					
		}
		else {
			
			return "";
		}
		
		}
		  
		  
		  
		  
		  
		  
		  
		  
		return "";
	}

	//the main method
	public static void main(String[] args) throws Exception {
		Scanner s = new Scanner(System.in);

		
		
		System.out.println("Yuklemek istediginiz bir dosya var mi?");
		String ans = s.nextLine();
		if (ans.equals("evet")) {
			System.out.println("Dosya ismi :");
			if (!DT.load(s.nextLine()))
				System.out.println("Dosya yuklenemedi");
		}

		if (DT.size() == 0) {
			System.out.println("Su anda hic hayvan bilmiyorum.");
			System.out.println("Bana bir hayvan ismi soyler misiniz?");
			String h1 = s.nextLine();
			System.out.println("Bir tane daha..");
			String h2 = s.nextLine();
			System.out.println("Bana  " + h1 + "'yi " + h2 + "'den ayırmami saglayacak bir evet/hayir sorusu soyler misiniz?");
			System.out.println("(Evet cevabi " + h1 + " için olmalidir.)");
			String s1 = s.nextLine();
			Node R = DT.addRoot(s1);
			DT.insertYes(R, h1);
			DT.insertNo(R, h2);
		}
		String c ="";
		while (!c.equals("hayir")) {
			System.out.println("Aklinizdan bir hayvan tutun, ben onu tahmin etmeye calisacagim.");
			System.out.println("Hazir olunca enter'a basin lutfen.");
			s.nextLine();
			guess_and_update(DT);
			System.out.println("Bir daha oynamak ister misiniz?");
			c = s.nextLine();		
		}
		
		System.out.println("Ogrenilen karar agacini kaydetmek ister misiniz?");
		ans = s.nextLine();
		if (ans.equals("evet")) {
			System.out.println("Dosya ismi :");
			DT.save(s.nextLine());
		}			
	}
}
