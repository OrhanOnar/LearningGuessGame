package tobb.etu.decisionTree;

public class Node {
	private Node left,right,parent;
	private String element;

	public Node(){
		left = null;
		right = null;
		parent = null;
		element = "";
	}
	public Node(String s) {
		left = null;
		right = null;
		parent = null;
		element = s;
	}
	public void setElement(String el) {
		element = el;
	}
	public String getElement() {
		return element;
	}
	public Node getLeft() {
		return left;
	}
	public void setLeft(Node l) {
		left = l;
	}
	public Node getRight() {
		return right;		
	}
	public void setRight(Node r) {
		right = r;
	}
	public Node getParent() {
		return parent;
	}
	public void setParent(Node p) {
		parent = p;
	}
	public boolean hasLeft() {
		return left != null;
	}
	public boolean hasRight() {
		return right != null;
	}	
	public boolean isRoot() {
		return parent == null;
	}
	public boolean isExternal() {
		return !(hasRight() || hasLeft());
	}
}
