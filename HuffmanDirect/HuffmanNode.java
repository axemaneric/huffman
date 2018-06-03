// Eric Fan
// 143AU Dilraj Devgun
// Assignment 8: Huffman

// HuffmanNode stores a character's integer value and its frequency as individual nodes that 
// will used in a binary tree. Implements the Comparable interface.
public class HuffmanNode implements Comparable<HuffmanNode> {
	
	public int value; // the unique integer value to represent the character
	public int count; // the frequency of the character
	public HuffmanNode left; // node to the left of a binary tree
	public HuffmanNode right; // node to the right of a binary tree
	
	// constructor for leaf node
	// @param {integer} value - the integer value of a character
	// @param {integer} count - the frequency of character
	public HuffmanNode(int value, int count) {
		this(value, count, null, null);
	}
	

	// @param {integer} value - the integer value of a character, if value = -1: represents
	// a branch node that holds no real integer value
	// @param {integer} count - in a leaf node: the frequency of the character, in a branch node: represents
	// combined frequency of all child nodes
	// @param {HuffmanNode} left - the node to the left
	// @param {HuffmanNode} right - the node to the right 
	public HuffmanNode(int value, int count, HuffmanNode left, HuffmanNode right) {
		this.value = value;
		this.count = count;
		this.left = left;
		this.right = right;
	}
	
	// compares this node with another node
	// @param {HuffmanNode} other - The other HuffmanNode being compared to
	// @returns {integer} - a negative integer, zero, or a positive integer corresponding to less
	// than, equal to, or greater than the compared node.
	public int compareTo(HuffmanNode other) {
		return count - other.count;
	}
	
}
