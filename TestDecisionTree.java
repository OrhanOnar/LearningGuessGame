

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import junit.framework.TestCase;

public class TestDecisionTree extends TestCase {
	
	
	public void test_addRoot() {
		DecisionTree DT = new DecisionTree();
		String root_element = "abc";
		DT.addRoot( root_element );
		assertTrue( DT.getRoot().getElement().equals( root_element ));
		assertTrue( DT.size() == 1 );
		assertFalse( DT.getRoot().hasLeft() );
		assertFalse( DT.getRoot().hasRight() );
	}
	
	public void test_insert_yes_exception() {
		DecisionTree DT = new DecisionTree();
		DT.addRoot( "xyz" );
		DT.insertYes( DT.getRoot(), "klm" );
		try {
			DT.insertYes( DT.getRoot(), "mno" ); //Should throw an exception.
		}
		catch( RuntimeException e ) {
			assertTrue( true );
			return;
		}
		assertTrue( false ); // Should not have reached here.
	}
	
	public void test_insert_yes_basic() {
		DecisionTree DT = new DecisionTree();
		DT.addRoot("xyz");
		DT.insertYes(DT.getRoot(), "abc");
		assertTrue( DT.getRoot().getElement().equals( "xyz" ) );
		assertTrue( DT.getRoot().getLeft().getElement().equals( "abc") );
		assertTrue( DT.getRoot().getLeft().getParent().getElement().equals( "xyz" ) );
		assertNull( DT.getRoot().getRight() );
		assertFalse( DT.getRoot().hasRight() );
		assertFalse( DT.getRoot().getLeft().hasLeft() );
		assertFalse( DT.getRoot().getLeft().hasRight() );
		assertTrue( DT.size() == 2 );
	}
	
	public void test_insert_no_exception() {
		DecisionTree DT = new DecisionTree();
		DT.addRoot( "xyz" );
		DT.insertNo( DT.getRoot(), "klm" );
		try {
			DT.insertNo( DT.getRoot(), "mno" ); //Should throw an exception.
		}
		catch( RuntimeException e ) {
			assertTrue( true );
			return;
		}
		assertTrue( false ); // Should not have reached here.
	}
	
	public void test_insert_no_basic() {
		DecisionTree DT = new DecisionTree();
		DT.addRoot("xyz");
		DT.insertNo(DT.getRoot(), "abc");
		assertTrue( DT.getRoot().getElement().equals( "xyz" ) );
		assertTrue( DT.getRoot().getRight().getElement().equals( "abc") );
		assertTrue( DT.getRoot().getRight().getParent().getElement().equals( "xyz" ) );
		assertNull( DT.getRoot().getLeft() );
		assertFalse( DT.getRoot().hasLeft() );
		assertFalse( DT.getRoot().getRight().hasLeft() );
		assertFalse( DT.getRoot().getRight().hasRight() );
		assertTrue( DT.size() == 2 );
	}
	
	public void test_insert() {
		DecisionTree DT = new DecisionTree();
		DT.addRoot("xyz");
		DT.insertYes(DT.getRoot(), "abc");
		DT.insertNo(DT.getRoot().getLeft(), "123");
		DT.insertYes(DT.getRoot().getLeft().getRight(), "456");
		DT.insertNo(DT.getRoot(),"fgh");
		assertTrue(DT.size() == 5);
		assertTrue(DT.getRoot().getLeft().getLeft() == null);
		assertTrue(DT.getRoot().getRight().getElement().equals("fgh"));
		assertTrue(DT.getRoot().getLeft().getRight().getElement().equals("123"));	
	}
	
	public void test_remove() {
		DecisionTree DT = new DecisionTree();
		DT.addRoot("1");
		DT.insertYes(DT.getRoot(), "2" );
		DT.insertNo(DT.getRoot(), "3");
		DT.insertYes(DT.getRoot().getLeft(), "4" );
		DT.insertNo(DT.getRoot().getLeft(), "5" );
		
		
		String removed_element = DT.remove( DT.getRoot().getLeft().getRight() );
		assertTrue( removed_element.equals( "5" ) );
		assertTrue( DT.size() == 4 );
		assertFalse( DT.getRoot().getLeft().hasRight() );
		
		String another_removed_element = DT.remove( DT.getRoot().getLeft() );
		assertTrue( another_removed_element.equals( "2" ) );
		assertTrue( DT.getRoot().getLeft().getElement().equals( "4" ) );
		assertTrue( DT.getRoot().getLeft().getParent().getElement().equals( "1" ) );
	}
	
	public void test_load() throws IOException {
		DecisionTree DT = new DecisionTree();
		boolean loaded = DT.load("DT.txt");
		assertTrue( loaded );
		assertTrue(DT.size() == 15);
		assertTrue(DT.getRoot().getElement().equals("Etcil mi?"));
		Node R = DT.getRoot();
		assertTrue(R.getLeft().getLeft().getLeft().getElement().equals("yilan"));
		assertTrue(R.getRight().getLeft().getElement().equals("zebra"));			
	}
	
	public void test_save() throws Exception {
		DecisionTree DT = new DecisionTree();
		DT.addRoot("xyz");
		DT.insertYes(DT.getRoot(), "abc");
		DT.insertNo(DT.getRoot().getLeft(), "123");
		DT.insertYes(DT.getRoot().getLeft().getRight(), "456");
		DT.insertNo(DT.getRoot(),"fgh");
		boolean done = DT.save("testDT.txt");
		assertTrue( done );
		try{
			Scanner s = new Scanner(new File("testDT.txt"));
			String line = s.nextLine();
			assertTrue(line.equals("xyz"));
			line = s.nextLine();
			line = s.nextLine();
			assertTrue(line.equals("123"));
			line = s.nextLine();
			assertTrue(line.equals("456"));
			s.close();
			File f = new File("testDT.txt");
			f.delete();
		} catch (Exception e) {
			assertTrue( false );
		}
	}
	
	public void test_guess() throws IOException {
		String q1 = AnimalPredictor.guess("DT.txt","query1.txt");
		assertTrue(q1.equals("zebra"));
		String q2 = AnimalPredictor.guess("DT.txt","query2.txt");  
		assertTrue(q2.equals("ayi"));
		String q3 = AnimalPredictor.guess("DT.txt","query3.txt");
		assertTrue(q3.equals("inek"));
		String q4 = AnimalPredictor.guess("DT2.txt", "query2.txt");
		assertTrue(q4.equals("ayi"));
		String q5 = AnimalPredictor.guess("DT2.txt", "query4.txt");
		assertTrue(q5.equals(""));
	}

	
}
