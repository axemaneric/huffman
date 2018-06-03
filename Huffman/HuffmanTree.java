// Eric Fan
// 143AU Dilraj Devgun
// Assignment 8: Huffman

import java.util.*;
import java.io.*;

// HuffmanTree is used for compression of text files using Huffman Code, therefore involving the 
// creation of a binary tree. Using the tree it can create a code file from a text file, use it to
// encode the text file, and finally decode the text file. Can read or write data to file. The 
// encoded text file is compressed. 
//
// The code file follows a Standard Format: Every two lines represent information for one leaf
// node (one character) of the Huffman tree. First line is the integer value of one character.
// Second line is the Huffman Code for the character. 
//
// A Huffman Code is defined by 0 bits and 1 bits. No exceptions. Follows pre-order traversal
// left = 0, right = 1. Applies through entire class. Look up Huffman coding.

public class HuffmanTree {

	HuffmanNode overallRoot; // stores the root of the tree

	// constructor to create tree from original text file's data
	// @param {integer array} count - array of frequencies of characters
	// eg. for an integer count[i], i represents the integer value of a character, whereas count[i]
	// represents the number of occurrences (strictly this format)
	public HuffmanTree(int[] count) {
		Queue<HuffmanNode> nodeList = new PriorityQueue<HuffmanNode>();
		for (int i = 0; i < count.length; i++) {
			if (count[i] > 0) {
				nodeList.add(new HuffmanNode(i, count[i]));
			}
		}
		nodeList.add(new HuffmanNode(count.length, 1)); // eof
		buildTree(nodeList);
		overallRoot = nodeList.remove();
	}

	// builds a Huffman Tree from nodeList and stores the overall root as the only element in nodeList
	// @param {Queue} nodeList - should be a PriorityQueue containing HuffmanNodes of each character
	private void buildTree(Queue<HuffmanNode> nodeList) {
		if (nodeList.size() > 1) {
			HuffmanNode firstNode = nodeList.remove();
			HuffmanNode secondNode = nodeList.remove();
			HuffmanNode combine = new HuffmanNode(-1, firstNode.count + secondNode.count, firstNode, secondNode);
			nodeList.add(combine);
			buildTree(nodeList);
		}
	}

	// prints out the current tree to a code file in Standard Format
	// @param {PrintSteam} output - linked to legal file open for writing
	public void write(PrintStream output) {
		write("", overallRoot, output);
	}

	// helper method for write
	// @param {String} path - holds the path (Huffman code) to reach a leaf node 
	// @param {HuffmanNode} root - the current root to be processed
	// @param {PrintSteam} output - linked to legal file open for writing
	private void write(String path, HuffmanNode root, PrintStream output) {
		if (root != null) {
			if (root.value != -1) {
				output.println(root.value);
				output.println(path);
			} else {
				write(path + "0", root.left, output);
				write(path + "1", root.right, output);
			}
		}
	}
	
	// constructor to create tree from code file's data
	// @param {Scanner} input - data read from a legal code file following Standard Format
	public HuffmanTree(Scanner input) {
		while (input.hasNext()) {
			int value = Integer.parseInt(input.nextLine());
			String huffmanCode = input.nextLine();
			overallRoot = update(overallRoot, value, huffmanCode, 0);
		}
	}

	// helper method for constructor using code file data
	// @param {HuffmanNode} root - the root of tree to process
	// @param {HuffmanNode} value - the integer value of a character
	// @param {String} path - the Huffman Code that also represents its weighting/location in the tree
	// @param {integer} index - the current bit of the Huffman Code (path) to be processed
	// @returns {HuffmanNode} - the overall root of the tree
	private HuffmanNode update(HuffmanNode root, int value, String path, int index) {
		if (root == null) { // reach empty node
			if (index != path.length()) { // path is not finished
				if (path.charAt(index) == '0') { // generate branches following path
					return new HuffmanNode(-1, -1, update(root, value, path, index + 1), null);
				} else {
					return new HuffmanNode(-1, -1, null, update(root, value, path, index + 1));
				}
			} else { // path finished so create the leaf node
				return new HuffmanNode(value, -1);
			}
		} else { // traverse tree
			if (path.charAt(index) == '0') {
				root.left = update(root.left, value, path, index + 1);
			} else {
				root.right = update(root.right, value, path, index + 1);
			}
		}
		return root;
	}

	// decodes an encoded compressed file with code file and prints original text to new file
	// @param {BitInputStream} input - used to read the bits in a valid encoded file with a valid eof
	// @param {PrintStream} output - linked to a legal file open for writing
	// @param {integer} eof - the end of file character value to identify that the end of
	// file is reached and no further bits of encoded file should be read
	public void decode(BitInputStream input, PrintStream output, int eof) {
		int currentBit;
		HuffmanNode runner = overallRoot;
		while (runner.value != eof) {
			if (runner.value != -1) {
				output.write(runner.value);
				runner = overallRoot;
			}
			currentBit = input.readBit();
			if (currentBit == 0) {
				runner = runner.left;
			} else if (currentBit == 1) {
				runner = runner.right;
			}
		}
	}



}
