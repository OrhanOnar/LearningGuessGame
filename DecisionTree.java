

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Stack;

import javax.swing.tree.TreeNode;

public class DecisionTree {

	//Root of the tree
	private Node Root;
	public Node temp=null;
	//number of nodes in decision tree
	private int DTsize;
	
	public int size() {
		return DTsize;		
	}
	
	public Node getRoot() {
		return Root;		
	}
	
	//create and return a new node r storing element e and
	//make r the root of the tree.
	public Node addRoot(String s) {
		
		Node r=new Node();
			
			r.setElement(s);
			r.setLeft(null);
			r.setRight(null);
	        Root=r;
		DTsize=1;
		temp=Root;

		return r;
	}
	
    //create and return a new node w storing element el and 
    //make w the left (yes) child of v. Make sure to increment the size.
    //make sure to update both child's parent link and parent's child link.
    //throws a RuntimeException if the node has already a left child.
	public Node insertYes(Node v, String el) throws RuntimeException {
		
		Node w=new Node();

if(v.hasLeft()) {
	throw new RuntimeException("Has left already");
}
else {
	
	w.setElement(el);
v.setLeft(w);

w.setParent(v);


DTsize++;

}
		
		return w;
	}

    //create and return a new node w storing element el and 
    //make w the right (no) child of v. Make sure to increment the size.
    //make sure to update both child's parent link and parent's child link.
    //throws a RuntimeException if the node has already a right child.
	public Node insertNo(Node v, String el) {
		Node w=new Node();

		if(v.hasRight()) {
			throw new RuntimeException("Has Right already");
		}
		w.setElement(el);
		
		v.setRight(w);

		w.setParent(v);

		DTsize++;

		return w;
	}
	
    //remove node v, replace it with its child, if any, and return 
    //the element stored at v. make sure to decrement the size.
    //make sure to update both child's parent link and parent's child link.
    //throws a RuntimeException if v has two children
	public String remove(Node v) throws RuntimeException {
		
		if (v.hasLeft() && v.hasRight())
		 throw new RuntimeException("v has two children");
		
		 Node child = (v.getLeft( ) != null ? v.getLeft( ) : v.getRight( ) );
		 if (child != null)
	child.setParent(v.getParent( ));
		 if (v == Root)
		 Root = child; 
		 else {
			 Node parent = v.getParent( );
		 if (v == parent.getLeft( ))
		 parent.setLeft(child);
		 else
		 parent.setRight(child);
		 }
		 		DTsize--;
			 String temp = v.getElement( );
			 v.setElement(null); 
			 v.setLeft(null);
			 v.setRight(null);
			 v.setParent(v); 
			 
			 return temp;
	}

    //write to file in PREORDER traversal order
    //Handle file exceptions as follows: If an exception is thrown, return false.
    //Otherwise, return true.	
	public boolean save(String filename) throws Exception {
		
		
		PrintWriter writer = new PrintWriter(filename, "UTF-8");

		

		try {
			
		      if(getRoot() == null) {

		             throw new Exception("Root yok");		      }
		      
		      
		         Stack<Node> stack = new Stack<Node>();
		         
		         stack.push(getRoot());
		  
		         while(!stack.empty()){
		          
		             Node n = stack.pop();
		             
		             
		             	writer.println(n.getElement());
		  
		           
		             	
		             	
		             if(n.getRight() != null){
		                 stack.push(n.getRight());
		             }
		             if(n.getLeft() != null){
		                 stack.push(n.getLeft());
		             }
		  
		         }
		} catch (IOException ex) {
		    return false;		} finally {
		   try {writer.close();} catch (Exception ex) {/*ignore*/}
		}

	
		


		return true;
	}
	
	
    //load the DT from file that contains the output of PREORDER traversal
    //Handle file exceptions as follows: If an exception is thrown, return false.
    //Otherwise, return true.
    //You can distinguish a leaf node from an internal node as follows: internal nodes always end with
    //a question mark.	
	public boolean load(String filename) throws IOException {

		if(!filename.contains(".txt")) {
			return false;
		}
		
		try {
		 File file = new File(filename);
		  BufferedReader br = new BufferedReader(new FileReader(file));
		
		
		  String st="";
		  
		  while ((st = br.readLine()) != null) {
			  
			  

			  if(DTsize==0)  {

			 addRoot(st);
			 continue;
			  }
			  
			
			  	while(temp.hasLeft() && temp.hasRight()) {
					  temp=temp.getParent();

			  	}
			  
			  
			  

		
			  
			String  ct=""+st.charAt(st.length()-1);
			  if(ct.equals("?")){
				  
				  

				  if(!temp.hasLeft()) {

					insertYes(temp, st);
						temp=temp.getLeft();
				  }
				  
				  else if(!temp.hasRight()) {
					insertNo(temp, st);
					temp=temp.getRight();
					

					  
				  }
				  
			  }
			  
			  else {
				  
				  if(!temp.hasLeft()) {
				  insertYes(temp,st);
				  
				  }
				  
				  else {

				  insertNo(temp, st);
				  
				  }
			  }
		  }
		  }
catch(Exception a) {
}
		return true;
	

		  
	}
	
	
	
	
	
	
}

