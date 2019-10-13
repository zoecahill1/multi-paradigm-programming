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
	struct ProductStock shoppingList[10];
	int index;
};

void printProduct(struct Product p)
{
	printf("PRODUCT NAME: %s \nPRODUCT PRICE: %.2f\n", p.name, p.price);
	printf("-------------\n");
}

void printCustomer(struct Customer c)
{
	printf("CUSTOMER NAME: %s \nCUSTOMER BUDGET: %.2f\n", c.name, c.budget);

	printf("-------------\n");
	for(int i = 0; i < c.index; i++)
	{
		printProduct(c.shoppingList[i].product);
		printf("%s ORDERS %d OF ABOVE PRODUCT\n", c.name, c.shoppingList[i].quantity);
		double cost = c.shoppingList[i].quantity * c.shoppingList[i].product.price; 
		printf("The cost to %s will be €%.2f\n\n", c.name, cost);
	}
	
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
        // printf("Retrieved line of length %zu:\n", read);
        // printf("%s IS A LINE", line);
		
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
	
	struct Customer customer = { 0 };
	FILE * fp;
	char * line = NULL;
	size_t length = 0;
	ssize_t read;
	
	fp = fopen(csvfile, "r");
	//if (fp == NULL)
		//exit(EXIT_FAILURE);
	
	while ((read = getline(&line, &length, fp)) != -1){
		
		char *n = strtok(line, ",");
		char *b = strtok(NULL, ",");
		char *name = malloc(sizeof(char) * 25);
		char *budg = malloc(sizeof(char) * 25);
		
		//strcpy(name, n);
		//strcpy(budg, b);
		//name = stripNewline(n);
		//budg = stripNewline(b);
		
		strcpy(name, stripLine(n));
		strcpy(budg, stripLine(b));
		
		//if customer name is returned then do this
		if (strstr(name, "Name") != NULL|strstr(name, "name") != NULL){
			//printf("%s:\t%s\n", n, b);
			customer.name = budg;
		}
		
		//otherwise if budget is returned do this
		else if (strstr(name, "Budget") != NULL|strstr(name, "budget") != NULL){
			double budget = atof(budg);
			//printf("%s:\t%.2f\n", name ,budg);
			customer.budget = budget;
		}
		//otherwise add the shopping list quantity to the customer is a strctured way
		//and increment the index
		
		else {
			int n = atoi(budg);
			//printf("%s:\t%i\n", n, b);
			struct Product product = { name };
			struct ProductStock shopList = { product, n };
			customer.shoppingList[customer.index++] = shopList;
			
		}
	}
	
	return customer;
}

void printShop(struct Shop s)
{
	printf("Shop has %.2f in cash\n", s.cash);
	// addded for readablity
	printf("\n");
	
	// going into shop and accessing each individual product
	for (int i = 0; i < s.index; i++)
	{
		printProduct(s.stock[i].product);
		printf("The shop has %d of the above\n", s.stock[i].quantity);
		printf("\n");
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
	//printShop(shop);
	
	struct Customer customer = createShoppingList("order1.csv");
	printCustomer(customer);
	
// printf("The shop has %d of the product %s\n", cokeStock.quantity, cokeStock.product.name);
	
    return 0;
}