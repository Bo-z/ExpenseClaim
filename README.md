# ExpenseClaim
=======
C301 Assignment 1
=======

Demo video: http://youtu.be/wwLbGj72xjY 

note: if you have problem when you run app in eclipse, please delete the /bin/res/crunch folder.

I design and implement a simple, attractive, and easy-to-use travel expense tracking application. For each travel claim (for which expenses are to be reimbursed), the application should record expense items.

An expense item has a date, category (e.g., air fare, ground transport, vehicle rental, fuel, parking, registration, accommodation, meal), textual description, amount spent, and unit of currency (e.g., CAD, USD, EUR, GBP, etc.).

A travel claim has a date range and a textual description (e.g., destination and reason for travel).


Travel expense record and track is the software that can track all kinds of travel, it allows different use of currency in one travel, it allows you to submit your travel, approve your travel or return it. 

This app supports:

    add/edit/delete individual expense items
    add/edit/delete individual claims
    email a selected claim and its constituent expense items
    denote a claim as submitted (no further edits allowed to it)
    denote a submitted claim as returned (further edits allowed to it)
    denote a submitted claim as approved (no further edits allowed to it)
    list all the claims, indicating status (i.e., in progress, submitted, returned, approved), ordered by start date
    show total currency amounts for a claim when it is listed
    list the expense items for a claim
    show total currency amounts for the expenses of a claim

A claim may involve expenses of mixed currencies, so the total should indicate the amount of each currency.

The application must be persistent; exiting the app should not lose data.

The application should assist the user in proper and consistent data entry.

>>>>>>> 8229cea00e40e9827e91ba14e985a3c32e1b965f
