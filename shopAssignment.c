// procedural implementation of a shop - may fit better to oop
#include <stdio.h>

// need for string tokeniser
#include <string.h>

// needed to read in file
#include <stdlib.h>

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
		printf("The cost to %s will be €%.2f\n", c.name, cost);
	}
}

// makes shop and reads in stock
struct Shop createAndStockShop()
{
	// dynamic memory allocation is NB when dealing with external files
	// instance of shop with 0 cash
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
		
		
		if (strstr(line,"cash") != NULL|strstr(line,"cash") !=NULL){
			char *n = strtok(line, ",");
			char *c = strtok(NULL, ",");
			double cash = atof(c);
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

void printShop(struct Shop s)
{
	printf("Shop has %.2f in cash\n", s.cash);
	
	// going into shop and accessing each individual product
	for (int i = 0; i < s.index; i++)
	{
		printProduct(s.stock[i].product);
		printf("The shop has %d of the above\n", s.stock[i].quantity);
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
	
// printf("The shop has %d of the product %s\n", cokeStock.quantity, cokeStock.product.name);
	
    return 0;
}