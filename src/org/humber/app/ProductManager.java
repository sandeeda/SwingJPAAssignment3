package org.humber.app;

import java.io.IOException;

import javax.swing.JFrame;

import org.humber.product.frame.MyProductFrame;

public class ProductManager {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		//Creating a JFrame object from MyProductFrame class
		JFrame frame = new MyProductFrame();
		frame.setVisible(true);	//Set visibility to true
		frame.setTitle("Product Manager");	//Set title
		frame.setDefaultCloseOperation(3);	//Set exit on close
	}

}
