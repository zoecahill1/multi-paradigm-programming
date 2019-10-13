// procedural implementation of a shop - may fit better to oop
#include <stdio.h>

// need for string tokeniser
#include <string.h>

// needed to read in file
#include <stdlib.h>

// https://stackoverflow.com/questions/28654792/what-do-i-need-to-do-so-the-function-isspace-work-in-c
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
	struct ProductStock stock[20];
	// in C cant check array length so we have to keep track of it here were using index
	int index;
};

struct Customer {
	char* name;
	double budget;
	//memory
	struct ProductStock shoppingList[10];
	int index;
};

void printProduct(struct Product p)
{
	printf("PRODUCT NAME: %s \nPRODUCT PRICE: %.2f\n", p.name, p.price);
	//printf("-------------\n");
}

void printCustomer(struct Customer c)
{
	printf("\n\n-------------------\nCUSTOMER\n---------------------\n");
	printf("CUSTOMER NAME: %s \nCUSTOMER BUDGET: %.2f\n", c.name, c.budget);

	printf("-------------\n");
	
	double total = 0;
	for(int i = 0; i < c.index; i++)
	{
		printProduct(c.shoppingList[i].product);
		printf("%s ORDERS %d OF ABOVE PRODUCT\n", c.name, c.shoppingList[i].quantity);
		double cost = c.shoppingList[i].quantity * c.shoppingList[i].product.price; 
		printf("The cost to %s will be €%.2f\n\n", c.name, cost);
		
		total+=cost;
	}
	
	printf("TOTAL: \t\t%.2f\n", total);
	printf("REMAINING: \t%.2f\n", c.budget-total);
	
}

// makes shop and reads in stock
struct Shop createAndStockShop()
{
	// dynamic memory allocation is NB when dealing with external files
	// instance of shop with 0 cash as we will read amount from csv file
	struct Shop shop = { 0 };
    FILE * fp;
    char * line = NULL;
    size_t len = 0;
    ssize_t read;

    fp = fopen("stock.csv", "r");
	// error handling if empty just close
    if (fp == NULL)
        exit(EXIT_FAILURE);

	// &len and fp - read to end of file
    while ((read = getline(&line, &len, fp)) != -1) {
		
		// checking that cash has a value
		if (strstr(line,"cash") != NULL){
			
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
			shop.stock[shop.index++] = stockItem;
			// printf("NAME OF PRODUCT %s PRICE %.2f QUANTITY %d\n", name, price, quantity);
		}
    }
	
	return shop;
}

// looks for characters that will effect formatting
// refrenced https://stackoverflow.com/questions/9628637/how-can-i-get-rid-of-n-from-string-in-c
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
	// refrenced https://stackoverflow.com/questions/122616/how-do-i-trim-leading-trailing-whitespace-in-a-standard-way?rq=1
	while(isspace( (unsigned char)*textStr ) ) textStr++;
	// retruns properly foratted string
	return textStr;
	
}

struct Customer createShoppingList(char *csvfile){
	
	//https://ubuntuforums.org/showthread.php?t=1103327
	struct Customer customer = { 0 };
	FILE * fp;
	char * line = NULL;
	size_t length = 0;
	ssize_t read;
	//https://www.javatpoint.com/c-array
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
			// add all to array
			trackers[i++]=tracker;
			//read until next null
			n = strtok(NULL, ",");
		}
		
		// if customer name is returned then do this
		if (strstr(trackers[0], "name") != NULL){
			// add name to the customer struct
			customer.name = trackers[1];
			
		}
		
		// otherwise if budget is returned do this
		else if (strstr(trackers[0], "budget") != NULL){
			
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

void printShop(struct Shop s)
{
	printf("\n\n-----------------\nSHOP\n-------------------\n");
	printf("Shop has %.2f in cash\n", s.cash);
	
	printf("\n-------------------\n");
	
	// going into shop and accessing each individual product
	for (int i = 0; i < s.index; i++)
	{
		printProduct(s.stock[i].product);
		printf("The shop has %d of the above\n", s.stock[i].quantity);
		printf("\n\n");
	}
}

int main(void) 
{
	// struct Customer dominic = { "Dominic", 100.0 };
	//
	// struct Product coke = { "Can Coke", 1.10 };
	// struct Product bread = { "Bread", 0.7 };
	// // printProduct(coke);
	//
	// struct ProductStock cokeStock = { coke, 20 };
	// struct ProductStock breadStock = { bread, 2 };
	//
	// everytime this list is called it increments the index which tracks the array length for us
	// dominic.shoppingList[dominic.index++] = cokeStock;
	// dominic.shoppingList[dominic.index++] = breadStock;
	//
	// printCustomer(dominic);
	
	struct Shop shop = createAndStockShop();
	printShop(shop);
	
	struct Customer customer = createShoppingList("order1.csv");
	printCustomer(customer);
	
// printf("The shop has %d of the product %s\n", cokeStock.quantity, cokeStock.product.name);
	
    return 0;
}