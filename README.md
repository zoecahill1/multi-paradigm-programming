# Multi Paradigm Programming - Shop Assignment
This repositry contains my solutions to the Shop Assignment for the module Multi Paradigm Programming in GMIT.

## Problem Description
This assignment is building on the shop program which was developed in the video series. You are tasked to add some
additional functionality:
1. The shop CSV should be modified to also hold the initial cash value for the shop.
2. Read in customer orders from a CSV file.
	- That file should include all the products they wish to buy and in what quantity.
	- It should also include their name and their budget.
3. The shop must be able to process the orders of the customer.
	- Update the cash in the shop based on money received.
	- It is important that the state of the shop be consistent.
	- You should create customer test files which cannot be completed by the shop e.g. customer want 400 loaves of bread but the shop only has 20, or the customer wants 2 cans of coke but can only afford 1.
	- Know whether or not the shop can fill an order.
	- Thrown an appropriate error.
4. Operate in a live mode, where the user can enter a product by name, specify a quantity, and pay for it.

The above described functionality should be completed in C. The second part of the assessment involves replicating the
functionality of the shop in Java. This must be done in an appropriately Object Oriented manner. You must complete a
short report, 1-3 pages, which compares the solutions acheived using the procedural approach (in C) and the object oriented
approach (in Java). The live mode, and the input files, should have exact same behaviour in both implementations.

## How to download this repository

1. On GitHub, navigate to the main page of the repository.
2. Under the repository name, click Clone or download.
3. In the Clone with HTTPs section, click to copy the clone URL for the repository.
4. Open Git Bash.
5. Change the current working directory to the location where you want the cloned directory to be made.
6. Type git clone, and then paste the URL you copied in Step 2.
7. Press Enter. Your local clone will be created.

## What each file contains
1. shopAssignment.c contains the code to run the shop functionality in the C programming language
2. stock.csv contains the variables for the shop such as the starting cash, the items in stock and the stock amounts
3. order1.csv contains the order of 1 customer. It contains their name, budget, items they wish the buy and how many of each they would like

## References
### shopAssignment.c
1. https://stackoverflow.com/questions/28654792/what-do-i-need-to-do-so-the-function-isspace-work-in-c
2. https://stackoverflow.com/questions/32717269/how-to-read-an-integer-and-a-char-with-read-function-in-c
3. https://stackoverflow.com/questions/39986168/segmentation-fault-in-c-cant-figure-out-the-reason
4. https://stackoverflow.com/questions/9628637/how-can-i-get-rid-of-n-from-string-in-c
5. https://stackoverflow.com/questions/122616/how-do-i-trim-leading-trailing-whitespace-in-a-standard-way?rq=1
6. https://ubuntuforums.org/showthread.php?t=1103327
7. https://www.javatpoint.com/c-array
8. https://www.w3resource.com/c-programming/c-for-loop.php
9. https://www.cs.uic.edu/~jbell/CourseNotes/C_Programming/Decisions.html