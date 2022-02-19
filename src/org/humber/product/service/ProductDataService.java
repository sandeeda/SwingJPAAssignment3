package org.humber.product.service;

import java.io.IOException;
import java.io.RandomAccessFile;

import org.humber.entity.Product;

public class ProductDataService {

	//Create a randomfileaccess object
	private RandomAccessFile file;

	//Declare bytes for integer data
	public static final int INT_SIZE = 4;

	//declare bytes for double data
	public static final int DOUBLE_SIZE = 8;

	
	//declare bytes for total memory for one record
	public static final int PRODUCT_RECORD_SIZE = INT_SIZE + DOUBLE_SIZE + 50;

	
	//initilize file with null on default construction
	public ProductDataService() {
		// TODO Auto-generated constructor stub
		file = null;
	}

	//open a pointer to a file, if it is already pointing, point it to null and then repoint to the file passed
	public void open(String string) throws IOException {
		// TODO Auto-generated method stub
		if (file != null)
			file.close();
		file = new RandomAccessFile(string, "rw");

	}

	//Close the file by pointing to null;
	public void close() throws IOException {
		if (file != null)
			file.close();
		file = null;
	}

	//return length of records by dividing total size by size required for a record
	public int size() throws IOException {
		// TODO Auto-generated method stub
		return (int) (file.length() / PRODUCT_RECORD_SIZE);
	}

	
	//Write to the file in the record at the position which is passed to the function
	public void writeProduct(Product tempProduct, int numberOfProducts) throws IOException {
		// TODO Auto-generated method stub
		//go to the index by multiplying index with size for one record
		file.seek(numberOfProducts * PRODUCT_RECORD_SIZE);
		//write id  by getting id from product object
		file.writeInt(tempProduct.getProductId());
		//write price  by getting price from product object
		file.writeDouble(tempProduct.getPrice());
		String modifiedProductName = tempProduct.getProductName();

		// Write the name to the file by parsing each character one by one. Add additional white spaces to fill the extra blocks of spaces free
		int lengthOfModiefiedProductName = modifiedProductName.length();
		//here bytes allocalted for name is 50 , hence 25 char data , hence looping until 25
		for (int i = 1; i <= (25 - lengthOfModiefiedProductName); i++) {
			//adding white spaces
			modifiedProductName += " ";
		}
		//write the modified string of size 50 bytes
		file.writeChars(modifiedProductName);
	}

	
	//find product position by productID
	public int findProductPosition(int productIdToModify) throws IOException {
		// TODO Auto-generated method stub
		//loop through entire records and read the frst integer to match with id
		for (int i = 0; i < this.size(); i++) {
			file.seek(i * PRODUCT_RECORD_SIZE);
			if (file.readInt() == productIdToModify)
				//if found return its index
				return i;
		}
		//if not found return -1
		return -1;
	}

	//Delete product for the id requested
	public void deleteProduct(int recordNum) throws IOException {
		// TODO Auto-generated method stub
		//go to the index for that record and set id to -1. Setting to -1 will keep the data but not fetch the data while displaying
		file.seek(recordNum * PRODUCT_RECORD_SIZE);
		file.writeInt(-1);
	}

	
	//Loop through all data and construct display result. Ignore the records with id = -1
	public String getAllProducts() throws IOException {
		// TODO Auto-generated method stub
		//create result header
		String allProducts = "Product ID\t\t\tProduct Name\t\t\tProduct Price\n";
	
		for(int i=0; i<this.size(); i++) {
			file.seek(i*PRODUCT_RECORD_SIZE);
			String productName = "";
			int prodId = file.readInt();
			double prodPrice = file.readDouble();
			//System.out.println(file.readDouble());
			for(int j = 0;j<25;j++) {
				productName+=file.readChar();
			}

			//add record to result only if the id is non negative
			if(prodId!=-1)
			allProducts = allProducts+prodId+"\t\t\t"+productName+"\t\t"+prodPrice+"\n";
		}
		//return string for output
		return allProducts;
	}

}
