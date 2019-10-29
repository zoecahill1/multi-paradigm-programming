// procedural implementation of a shop - may fit better to oop
#include <stdio.h>

// need for string tokeniser
#include <string.h>

// needed to read in file
#include <stdlib.h>

// refrenced [1]
#include <ctype.h>


// A struct is a type of data we define ourselves
struct Product {
	// using a pointer
	char* name;
	double price;
};

// like a relational DB init a product that we have already defined above
struct ProductStock {
	struct Product product;
	int quantity;
};

struct Shop {
	double cash;
	// init an array of 20 to hold stock
	struct ProductStock stock[10];
	// in C cant check array length so we have to keep track of it here were using index
	int index;
};

struct Customer {
	char* name;
	double budget;
	struct ProductStock shoppingList[10];
	int index;
};

// looks for characters that will effect formatting
// refrenced [4]
const char * stripLine(char *textStr){
	// looking for \n characters 
	if (textStr[strlen(textStr)-1] == '\n'){
		// changes to \0 which has no format effect
		textStr[strlen(textStr)-1] = '\0';
	}
	
	// looking for \r characters
	if (textStr[strlen(textStr)-1] == '\r'){
		// if true changes to \0
		textStr[strlen(textStr)-1] = '\0';
		
	}
	// refrenced [5]
	while(isspace( (unsigned char)*textStr ) ) textStr++;
	// retruns properly foratted string
	return textStr;
	
}

// makes shop and reads in stock
struct Shop createAndStockShop()
{
	// dynamic memory allocation is NB when dealing with external files
	// instance of shop with 0 cash as we will read amount from csv file
	//struct Shop shop = { 0 };
    FILE * fp;
    char * line = NULL;
	//refrenced [2]
    size_t len = 0;
    ssize_t read;

    fp = fopen("stock.csv", "r");
	// error handling if empty just close
    if (fp == NULL)
        exit(EXIT_FAILURE);
	
	getline(&line, &len, fp);
	double cashInShop = atof(line);
	struct Shop shop = { cashInShop };

	// &len and fp - read to end of file
    while ((read = getline(&line, &len, fp)) != -1) {
		
		// checking that cash has a value
		//  refrenced [3]
		if (strstr(line,"cash") != NULL|strstr(line,"Cash") != NULL){
			
			//getting name and amount
			char *n = strtok(line, ",");
			char *c = strtok(NULL, ",");
			// atof converts string to float to get rid of /n
			double cash = atof(c);
			// specifying the value to shop instance init above
			shop.cash = cash;
		}
		
		else {
			// getting name, price and quantity - "," is specifying the break
			char *n = strtok(line, ",");
			char *p = strtok(NULL, ",");
			char *q = strtok(NULL, ",");
		
			// a carriage return is implict /n at the end of the csv row affects quantity
			// atoi converts string to int
			// atof converts strinf to float
			int quantity = atoi(q);
			double price = atof(p);
		
			// allocates memory atoi and atof did this for q and p
			char *name = malloc(sizeof(char) * 50);
			// need seperate variable for this otherwise its overwritten
			strcpy(name, n);
		
			// reads in file line by line then adds to product variable
			// then add both to shop
			struct Product product = { name, price };
			struct ProductStock stockItem = { product, quantity };
			// shop at index[0] is incremented and also updated to the amount of stockItem
			shop.stock[shop.index++] = stockItem;
			// printf("NAME OF PRODUCT %s PRICE %.2f QUANTITY %d\n", name, price, quantity);
		}
    }
	
	return shop;
}

struct Customer createShoppingList(char *csvfile){
	
	//[6]
	struct Customer customer = { 0 };
	FILE * fp;
	char * line = NULL;
	size_t length = 0;
	ssize_t read;
	//[7]
	char *trackers[3];
	
	fp = fopen(csvfile, "r");
	if (fp == NULL)
		exit(EXIT_FAILURE);
	
	// read csv line by line
	while ((read = getline(&line, &length, fp)) != -1){
		// reset count index
		int i=0; 
		
		// read until see ,
		char *n = strtok(line, ",");
		
		// read until null occurs
		while (n != NULL){
			// assign memory for count
			char *tracker = malloc(sizeof(char) * 25);
			// copies to count after formatting
			strcpy(tracker, stripLine(n));
			//printf(tracker);
			// add all to array
			trackers[i++]=tracker;
			//read until next null
			n = strtok(NULL, ",");
		}
		
		// if customer name is returned then do this
		// seg errror
		if (strstr(trackers[0],"Name") != NULL|strstr(trackers[0],"name") != NULL){
			// add name to the customer struct
			customer.name = trackers[1];
			
		}
		
		// otherwise if budget is returned do this
		// seg error
		else if (strstr(trackers[0],"Budget") != NULL|strstr(trackers[0],"budget") != NULL){
			
			// atof casts to float
			double budget = atof(trackers[1]);
			// add to customer struct
			customer.budget = budget;
		}
		
		// adds the shopping list amounts to product structs
		else{
			// atoi casts to int. no of items
			int n = atoi(trackers[2]);
			// atof casts to float
			double cost = atof(trackers[1]);
			
			// instance of product with name from array
			struct Product product = {trackers[0], cost};
			// add product and amount to list
			struct ProductStock shopList = {product, n};
			// update list and update index
			customer.shoppingList[customer.index++] = shopList;
			
			
		}
	}
	
	return customer;
}

void printProduct(struct Product p)
{
	printf("NAME: %s \nPRICE: €%.2f\n", p.name, p.price);
}

void printCustomer(struct Customer c)
{
	printf("\n\n-----------------------------\nCUSTOMER");
	printf("\n-----------------------------\n");
	printf("CUSTOMER NAME: %s \nCUSTOMER BUDGET: €%.2f\n", c.name, c.budget);
	printf("-----------------------------\n");
	
	double total = 0;
	for(int i = 0; i < c.index; i++)
	{
		printProduct(c.shoppingList[i].product);
		printf("%s would like %d of these\n", c.name, c.shoppingList[i].quantity);
		double cost = c.shoppingList[i].quantity * c.shoppingList[i].product.price; 
		printf("The cost of this will be €%.2f\n\n", c.name, cost);
		
		total+=cost;
	}
	
	printf("TOTAL BILL: \t\t€%.2f\n", total);
	printf("REMAINING BUDGET: \t€%.2f\n", c.budget-total);
	
}

void printShop(struct Shop s)
{
	printf("\n\n-----------------------------");
	printf("\nSHOP\n-----------------------------\n");
	printf("Shop currently holds €%.2f\n", s.cash);
	
	printf("-----------------------------\n");
	
	// going into shop and accessing each individual product
	// [8]
	for (int i = 0; i < s.index; i++)
	{
		printProduct(s.stock[i].product);
		printf("STOCK: %d", s.stock[i].quantity);
		printf("\n\n");
	}
}

void checkOrder(struct Shop s, struct Customer c){
	//prints out list and stock 
	// caused segmentation error if not using print in main
	//printf("\n-------------\nORDER\n--------------");
	//printf("\n--------------\nSHOPPING LIST\n------------\n");
	//for (int i=0; i<c.index; i++){
		//printf("%3i. %s\n", i+1, s.stock[i].product.name);
	//}
	//printf("\n-------------\nSTOCK\n-----------------\n");
	//for (int i=0; i<c.index; i++){
		//printf("%3i. %s\n", i+1, s.stock[i].product.name);
	//}
	
	s.cash;
	s.index;
	c.name;
	c.budget;
	c.index;
	s.stock[0].quantity;
	c.shoppingList[0].quantity;
	s.stock[0].product.name;
	c.shoppingList[0].product.name;
	s.stock[0].product.price;
	c.shoppingList[0].product.price;
	
	

	
	for (int i=0;i<c.index;i++){
		// ref [9]
		short stockCheck=0;
		char *list = malloc(sizeof(char) * 25);
		strcpy(list, c.shoppingList[i].product.name);
		
		for (int j=0;j<s.index;j++){
		
			char *shop = malloc(sizeof(char) * 25);
			strcpy(shop, s.stock[j].product.name);
		
			//if to check if shop has item or not
			// if it is then
			if (strstr(list, shop) != NULL){ 
				//bool value
				stockCheck=1;
				printf("We have %s\n", shop);
			
				//then check stock level
			
				int orderAmt = c.shoppingList[i].quantity;
				int shopAmt = s.stock[j].quantity;
				//means stock to fill the order is available 
				if (orderAmt<shopAmt){
					printf("%s is in stock\n\n", shop);
				}
				//otherwise we don't have the stock to fill order
				else{
					// test bread
					printf("Sorry but %s is out of stock\n\n", shop);
				}
				// loop ends meaning we are at end of list
			}
			// test apples
			if (j == s.index-1 & !stockCheck){
					printf("Sorry we don't have any %s in this shop\n", list);
			}
			
		}
	}
	return;
}



int main(void) 
{	
	struct Shop shop = createAndStockShop();
	//printShop(shop);
	
	struct Customer customer = createShoppingList("order1.csv");
	//printCustomer(customer);
	
	checkOrder(shop, customer);
    return 0;
}