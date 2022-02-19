package org.humber.entity;
//Creating entity product
public class Product {

	//Product counter variable for generating unique ID
	static int productCounter=0;
	//attributes
	double price;
	int productId;
	String productName;
	
	//Constructor for adding new product by generating ID automatically
	public Product(double price, String productName) {
		super();
		productCounter++;
		this.price = price;
		this.productId = productCounter;
		this.productName = productName;
	}
	
	
	//Constructor to use while modifying data or deleting
	public Product(int productId, double price, String productName) {
		//super();
		this.price = price;
		this.productId = productId;
		this.productName = productName;
	}


	//Getters and setters
	public static int getProductCounter() {
		return productCounter;
	}


	public static void setProductCounter(int productCounter) {
		Product.productCounter = productCounter;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public int getProductId() {
		return productId;
	}


	public void setProductId(int productId) {
		this.productId = productId;
	}


	public String getProductName() {
		return productName;
	}


	public void setProductName(String productName) {
		this.productName = productName;
	}


	//overriding to string
	@Override
	public String toString() {
		return "Product [price=" + price + ", productId=" + productId + ", productName=" + productName + "]";
	}
	
	
	
}
