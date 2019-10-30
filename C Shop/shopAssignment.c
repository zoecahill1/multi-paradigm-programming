// procedural implementation of a shop - may fit better to oop
#include <stdio.h>

// need for string tokeniser
#include <string.h>

// needed to read in file
#include <stdlib.h>

// refrenced [1]
#include <ctype.h>


// A struct is a type of data we define ourselves
struct product {
	// using a pointer
	char* name;
	double price;
};

// like a relational DB init a product that we have already defined above
struct productStock {
	struct product product;
	int quantity;
};

struct Shop {
	double cash;
	// init an array of 20 to hold stock
	struct productStock stock[10];
	// in C cant check array length so we have to keep track of it here were using index
	int index;
};

struct Customer {
	char* name;
	double budget;
	struct productStock shoppingList[10];
	int index;
};

// prints product infomation
void printproduct(struct product p)
{
	printf("NAME: \t%s \nPRICE: \t€%.2f\n", p.name, p.price);
}

// takes in customer struct
void printCustomer(struct Customer c)
{
	printf("\n\n-----------------------------------\nCUSTOMER");
	printf("\n-----------------------------------\n");
	printf("CUSTOMER NAME: \t\t%s \nCUSTOMER BUDGET: \t€%.2f\n", c.name, c.budget);
	printf("-----------------------------------\n");
	
	double total = 0;
	for(int i = 0; i < c.index; i++)
	{
		printproduct(c.shoppingList[i].product);
		printf("%s would like %d of these\n", c.name, c.shoppingList[i].quantity);
		
		double cost = c.shoppingList[i].quantity * c.shoppingList[i].product.price; 
		
		printf("The cost of this will be €%.2f\n\n", cost);
		total+=cost;
	}

	printf("TOTAL BILL: \t\t€%.2f\n", total);
	printf("REMAINING BUDGET: \t€%.2f\n\n",c.budget-total);
}

void printShop(struct Shop s)
{
	
	printf("\n\n-----------------------------------");
	printf("\nWELCOME TO THE SHOP\n-----------------------------------\n");
	printf("Shop currently holds €%.2f\n", s.cash);
	
	printf("-----------------------------------\n");
	
	// going into shop and accessing each individual product
	// [8]
	for (int i = 0; i < s.index; i++)
	{
		printproduct(s.stock[i].product);
		printf("STOCK: \t%d", s.stock[i].quantity);
		printf("\n\n");
	}
}

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
	while(isspace( (unsigned char)*textStr) ){textStr++;}
	// retruns properly foratted string
	return textStr;
} 

struct Customer createShoppingList(char *csvfile){
	
	//[6]
	struct Customer customer = { 0 };
	FILE * fp;
	char * line = NULL;
	size_t length = 0;
	ssize_t read;
	//[7]
	char *array[3];
	
	fp = fopen(csvfile, "r");
	if (fp == NULL)
			exit(EXIT_FAILURE);

	// read csv line by line
	while ((read = getline(&line, &length, fp)) != -1) {
		// reset count index
		int i=0;
		
		// read until see ,
		char *n = strtok(line, ",");
		
		// read until null occurs
		while ( n != NULL ){
			// assign memory for count
			char *token = malloc(sizeof(char) * 25);
			// copies to count after formatting
			strcpy(token, stripLine(n));
			//printf(array);
			// add all to array
			array[i++] = token; 
			//read until next null
			n = strtok(NULL, ",");
		}
		
		// if customer name is returned then do this
		// seg errror
		if (strstr(array[0], "Name") != NULL|strstr(array[0], "name") != NULL)
		{
			// add name to the customer struct
			customer.name = array[1];
		}
		
		// otherwise if budget is returned do this
		// seg error
		else if (strstr(array[0],"Budget") != NULL|strstr(array[0],"budget") != NULL)
		{
			// atof casts to float
			double budget = atof(array[1]);
			// add to customer struct
			customer.budget = budget;
		}
		
		// adds the shopping list amounts to product structs
		else
		{
			// atoi casts to int. no of items
			int items = atoi( array[2] ); 
			// atof casts to float
			double price = atof( array[1] );
			// instance of product with name from array
			struct product product = { array[0], price };
			// add product and amount to list
			struct productStock shoppingList = { product, items };
			// update list and update index
			customer.shoppingList[customer.index++] = shoppingList;
		}
	}	
	// returns once loop is finished
	return customer;
}

// makes shop and reads in stock
struct Shop createAndStockShop()
{
	// dynamic memory allocation is NB when dealing with external files
	// instance of shop with 0 cash as we will read amount from csv file
	struct Shop shop = { 0 };
	FILE * fp;
	char * line = NULL;
	
	//refrenced [2]
	size_t len = 0;
	ssize_t read;

	fp = fopen("stock.csv", "r");
	
	// error handling if empty just close
	if (fp == NULL) 
			exit(EXIT_FAILURE);

	// &len and fp - read to end of file
	while ((read = getline(&line, &len, fp)) != -1) {
		// checking that cash has a value
		//  refrenced [3]		
		if (strstr(line, "cash") != NULL|strstr(line, "Cash") != NULL){
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
			struct product product = { name, price };
			struct productStock stockItem = { product, quantity };
			// shop at index[0] is incremented and also updated to the amount of stockItem
			shop.stock[shop.index++] = stockItem;
		}
	
	}
	
	return shop;
}

struct Shop checkOrder(struct Shop s, struct Customer c){

	// totalBill will track the bill amount of the products we are are to fill from shoppinglist
	double totalBill = 0;
	
	printf("\n\n-----------------------------------");
	printf("\nORDER SUMMARY\n-----------------------------------\n");
	
	// for loop goes through shopping list
	for (int i=0; i<c.index; i++){
		// ref [9]
		short stockCheck = 0;
		char *list = malloc(sizeof(char) * 25);
		strcpy(list, c.shoppingList[i].product.name);
		
		// this loop looks at the shop stock
		for (int j=0; j < s.index; j++){
			
			char *shop = malloc(sizeof(char) * 25);
			strcpy(shop, s.stock[j].product.name);
			
			// if to check if shop has item or not
			// if it is then:
			if (strstr(list, shop) != NULL){
				
				stockCheck=1;
				printf("This shop has: "); 
				
				// then check stock level
				// getting stock levels and prices
				int orderAmt = c.shoppingList[i].quantity;
				int shopAmt = s.stock[j].quantity;
				double price = s.stock[j].product.price;
				
				// means stock to fill the order is available 
				if (orderAmt<shopAmt){
					printf("%i %s for €%.2f\n\n",orderAmt, shop, price);
					// take away shopping list amount from shop stock
					s.stock[j].quantity-=orderAmt;
					// add total bill to shops cash to increase it
					s.cash += (s.stock[j].product.price * orderAmt);
					// add to total bill which keeps track of the order
					totalBill += (orderAmt * price);
				}
				
				//otherwise we don't have the stock to fill order
				else {
					// test bread
					// will summarise the order and tell the customer how much of the item we are missing
					printf("%s but stock is low and we only have %i of the %i %s that you wanted\n", shop, shopAmt, orderAmt, shop);
					printf("We will add what we have to your order: %i %s for €%.2f (missing %i %s)\n\n", shopAmt, shop, price, orderAmt-shopAmt, shop);
					// take away stock that we do have
					s.stock[j].quantity -= shopAmt;
					s.cash += (s.stock[j].product.price * shopAmt);
					totalBill += (shopAmt * price);
				}
			}
			// test apples
			// loop ends meaning we are at end of list
			if (j == s.index-1 & !stockCheck) {
				printf("Sorry we don't have any %s in this shop\n\n", list);
			}
		}
	}
	// gives total bill and remaining budget of customer from csv
	printf("\nYour total bill is €%4.2f\n", totalBill);
	printf("You will have €%4.2f left in your budget\n\n", c.budget - totalBill);
	// returns shop with altered amounts
	return s;
}

void updateShop(struct Shop s,char * filename){
	
	// opens the stock.csv with the new cash balance
	FILE * fp;
	// open w for write
	fp=fopen(filename,"w+");
	// first line Cash
	fprintf(fp,"Cash, %.2f\n", s.cash);
	
	for (int i=0; i<s.index; i++){
		// updating the stock
		fprintf(fp, "%s, %.2f, %i\n",s.stock[i].product.name,s.stock[i].product.price,s.stock[i].quantity);
		
	}
	// close file when finished
	fclose(fp);
	
	return;
}

// gets user input for choice
int input()
{
	int number;
	scanf("%d", &number);
	return (number);
}

int main(void) 
{
	// init instance of shop and customer order from csv
	struct Shop shop = createAndStockShop();
	struct Customer customer = createShoppingList("order1.csv");
	int choice;
	
	// refrenced [10]
	do {
		// menu options
		printf("\n*****************THE SHOP*****************\n");
		printf("\n1. View the Shop");
		printf("\n2. View the current customer's details and shopping list");
		printf("\n3. Check stock against current customers shopping list");
		printf("\n4. Process order");
		printf("\n5. To exit program\n");
		printf("\nPlease enter your choice: ");
		choice = input();

		switch (choice)
		{
			case 1:
					printShop(shop);
					break;
			case 2:
					printCustomer(customer);
					break;
			case 3:
					shop = checkOrder(shop,customer);
					break;
			case 4:
					shop = checkOrder(shop,customer);
					// updateShop will keep the shops state consistent
					updateShop(shop,"stock.csv");
					printf("\n\n*****************UPDATED SHOP*****************\n");
					printShop(shop);
					break;
			case 5:
					return 0;
			default:	
					printf("\nInvalid option, please try again!\n");
					break;
		}
	}
	
	while (choice != 5);
	
	return 0;
}