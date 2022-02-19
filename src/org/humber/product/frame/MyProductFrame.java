package org.humber.product.frame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.humber.entity.Product;
import org.humber.product.service.ProductDataService;

public class MyProductFrame extends JFrame {
	//Tabbed pane for displaying different tabs (add, modify, delete)
	JTabbedPane tabbedPane;
	
	
	//add product panel
	JPanel addProductPanel;
	JLabel productNameLabel;
	JTextField productNameTextField;
	
	JLabel productPriceLabel;
	JTextField productPriceTextField;
	
	JButton addProductButton;
	JButton showProductButton1;
	
	
	
	
	
	
	//modify product panel
	JPanel modifyProductPanel;
	
	JLabel modifyProductIdLabel;
	JTextField modifyProductIdTextField;
	
	JLabel modifyProductNameLabel;
	JTextField modifyProductNameTextField;
	
	JLabel modifyProductPriceLabel;
	JTextField modifyProductPriceTextField;
	
	JButton modifyProductButton;
	JButton showProductButton2;
	
	
	
	//Delete Product panel
	JPanel deleteProductPanel;
	
	JLabel deleteProductIDLabel;
	JTextField deleteProductIDTextField;
	
	JButton deleteProductButton;
	JButton showProdtButton3;
	
	
	
	
	
	
	//Action listener
	ActionListener listener;
	//Object of Product Service Class
	ProductDataService service;
	
	public MyProductFrame() {
		//Setting size
		setSize(500,180);
		
		// TODO Auto-generated constructor stub
		//Inner class for own implementation of action listener
		class ProductActionListener implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//When a product is added
				if(e.getSource() == addProductButton) {
					try {
						//Open pointer to file
						service.open("product.dat");
						//get size of records
						int numberOfProducts = service.size();
						//set static id counter to current number of records in file
						Product.setProductCounter(numberOfProducts);
						//create temporary product from the data inserted
						Product tempProduct = new Product(Double.parseDouble(productPriceTextField.getText()), productNameTextField.getText());
						
						//call service.write to add new data
						service.writeProduct(tempProduct,numberOfProducts);
						//Reset text in text fields
						productPriceTextField.setText("");
						productNameTextField.setText("");
						//explicitly click show data to display data
						showProdtButton3.doClick();
						//close file pointers
						service.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
				
				//if modify button is clicked
				if(e.getSource() == modifyProductButton) {
					try {
						//open file pointers
						service.open("product.dat");
						//read the product id to be modified
						int productIdToModify = Integer.parseInt(modifyProductIdTextField.getText()) ;
						//get index number of records if exist
						int recordNum = service.findProductPosition(productIdToModify);
						//if records exist then modify the data 
						if(recordNum>=0) {
							//create a temporary product and add the product id explicitly
							Product temProduct = new Product(productIdToModify, Double.parseDouble(modifyProductPriceTextField.getText()), modifyProductNameTextField.getText());
							//Send the seek position to write function and write the data
							service.writeProduct(temProduct, recordNum);
							//display success message
							JOptionPane.showMessageDialog(null, "Updated product "+temProduct.getProductName());
						}
						else {
							//Display not found message
							JOptionPane.showMessageDialog(null, "Product not found");
						}
						//Reset text in text fields
						modifyProductIdTextField.setText("");
						modifyProductNameTextField.setText("");
						modifyProductPriceTextField.setText("");
						//Explicitly click show data button to display data
						showProdtButton3.doClick();
						//close all pointers
						service.close();
					} catch (Exception e2) {
						// TODO: handle exception
					}
				}
				
				//if button clicked is delete button
				if(e.getSource() == deleteProductButton) {
					try {
						//open file pointers
						service.open("product.dat");
						//fetch the product id to be deleted
						int productIdToDelete = Integer.parseInt(deleteProductIDTextField.getText());
						//get the record number for the product to be deleted
						int recordNum = service.findProductPosition(productIdToDelete);
						//if a record is found
						if(recordNum>=0) {
							//call service.delete product and pass recordnum
							service.deleteProduct(recordNum);
						}
						else
							//Display error message
							JOptionPane.showMessageDialog(null, "No such product");
						//close all file pointers
						service.close();
						//Reset all text in text fields
						deleteProductIDTextField.setText("");
						//click the show data button explicitly
						showProdtButton3.doClick();

					} catch (Exception e2) {
						// TODO: handle exception
					}
				}
				
				//If a show data button is clicked
				if(e.getSource() == showProductButton1 || e.getSource() == showProdtButton3 || e.getSource() == showProductButton2) {
					try {
						//open pointers
						service.open("product.dat");
						//Fetch all products
						String products = service.getAllProducts();
						//set reult area text with fetched data
						JTextArea result = new JTextArea(20,20);
						result.setText(products);
						System.out.println(products);
						//display result in a joptionpane
						JOptionPane.showMessageDialog(null, result);
					} catch (Exception e2) {
						// TODO: handle exception
					}
				}
				
			}
		}
		//initialize attributes
		listener = new ProductActionListener();
		service = new ProductDataService();
		createAddProductPanel();  //create add panel
		createModifyProductPanel();   //create modify panel
		createDeleteProductPanel();  //create delete panel
		createLayout();	//pack the layout
	}

	//Initialize components in delete panel and add components to the panel
	//add action listeners
	private void createDeleteProductPanel() {
		// TODO Auto-generated method stub
		deleteProductPanel = new JPanel();
		deleteProductPanel.setLayout(new GridLayout(2,2));
		deleteProductPanel.setBorder(new TitledBorder(new EtchedBorder(),"Delete product"));
		
		deleteProductIDLabel = new JLabel("Product ID: ");
		deleteProductIDTextField = new JTextField(3);
		
		deleteProductButton = new JButton("Delete Product");
		deleteProductButton.addActionListener(listener);
		
		showProdtButton3 = new JButton("Show Products");
		showProdtButton3.addActionListener(listener);
		
		deleteProductPanel.add(deleteProductIDLabel);
		deleteProductPanel.add(deleteProductIDTextField);
		deleteProductPanel.add(deleteProductButton);
		deleteProductPanel.add(showProdtButton3);
		
	}

	//Initialize components in modify panel and add components to the panel
	//add action listeners
	private void createModifyProductPanel() {
		// TODO Auto-generated method stub
		modifyProductPanel = new JPanel();
		modifyProductPanel.setBorder(new TitledBorder(new EtchedBorder(),"Modify Product"));
		modifyProductPanel.setLayout(new GridLayout(4,2));
		
		modifyProductIdLabel = new JLabel("Product ID:");
		modifyProductIdTextField = new JTextField(3);
		
		modifyProductNameLabel = new JLabel("Product Name");
		modifyProductNameTextField = new JTextField(15);
		
		modifyProductPriceLabel = new JLabel("Product price");
		modifyProductPriceTextField = new JTextField(3);
		
		modifyProductButton = new JButton("Modify Product");
		modifyProductButton.addActionListener(listener);
		
		showProductButton2 = new JButton("Show Products");
		showProductButton2.addActionListener(listener);
		
		modifyProductPanel.add(modifyProductIdLabel);
		modifyProductPanel.add(modifyProductIdTextField);
		modifyProductPanel.add(modifyProductNameLabel);
		modifyProductPanel.add(modifyProductNameTextField);
		modifyProductPanel.add(modifyProductPriceLabel);
		modifyProductPanel.add(modifyProductPriceTextField);
		modifyProductPanel.add(modifyProductButton);
		modifyProductPanel.add(showProductButton2);
		
	}

	//pack the panel into panes and add to frames
	private void createLayout() {
		// TODO Auto-generated method stub
		
		tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Add Product", null, addProductPanel, "Add Product");
		tabbedPane.addTab("Modify Product", null, modifyProductPanel, "Modify Product");
		tabbedPane.addTab("Delete Product", null, deleteProductPanel, "Delete Product");
		
		
		add(tabbedPane, BorderLayout.CENTER);


	}

	//Initialize components in add panel and add components to the panel
	//add action listeners
	private void createAddProductPanel() {
		// TODO Auto-generated method stub
		addProductPanel = new JPanel();
		addProductPanel.setBorder(new TitledBorder(new EtchedBorder(),"Add Product"));
		addProductPanel.setLayout(new GridLayout(3,2));
		
		productNameLabel = new JLabel("Product name");
		productNameTextField = new JTextField(15);
		
		productPriceLabel = new JLabel("Product Price");
		productPriceTextField = new JTextField(3);
		
		addProductButton = new JButton("Add");
		addProductButton.addActionListener(listener);
		
		showProductButton1 = new JButton("Show Products");
		showProductButton1.addActionListener(listener);
		
		addProductPanel.add(productNameLabel);
		addProductPanel.add(productNameTextField);
		
		addProductPanel.add(productPriceLabel);
		addProductPanel.add(productPriceTextField);
		
		addProductPanel.add(addProductButton);
		addProductPanel.add(showProductButton1);
		
		
	}
	
	
	
	
	
	
}
