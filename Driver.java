package booklist;
import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Driver {
	public static void main(String args[]) {
		// part 1 - read incorrect records from books.txt and place into an ArrayList	
		ArrayList<Book> arrLst = new ArrayList<>();
		boolean errorsExist = false;
		
		// part 2 - store correct records into a BookList
		BookList bkLst = new BookList();
		
		try {
			Scanner reader = new Scanner(new FileInputStream("Books.txt"));
			while (reader.hasNextLine()) {
				String line = reader.nextLine();
				String[] currentRecord = line.split(",");
				Book currentBook = new Book(currentRecord);
				
				
				if (Integer.parseInt(currentRecord[5]) >= 2024) {
					errorsExist = true;
					arrLst.add(currentBook);
				}
				
				// add correct books to the end of the circular book list
				else {
					bkLst.addToEnd(currentBook);
				}
			}
			
			if (errorsExist) {
				System.out.println("YearError File Created");
				BufferedWriter writer = new BufferedWriter(new FileWriter("YearErr.txt"));
				for (Book b : arrLst) {
					writer.write(b.toString());
					writer.newLine();
				}
				writer.close();
			}
		}
		catch (IOException e) {e.printStackTrace();}			

		System.out.println("Here are the contents of the list");
		System.out.println("=================================");
		bkLst.displayContent();
		
		// menu and selection
		int choice = 0;
		boolean hasCommited = false;
		Scanner key = new Scanner(System.in);
		while (choice != 8 || !hasCommited) {
			System.out.println("Tell me what you want to do? Let's chat since this is trending now! Here are the options: ");
			System.out.println("\t1) Give me a year # and I would extract all records of that year and store them in a file for that year;");
			System.out.println("\t2) Ask me to delete all consecutive repeated records;");
			System.out.println("\t3) Give me an author name and I will create a new list with the records of this author and display them;");
			System.out.println("\t4) Give me an ISBN number and a book object, and I will insert Node with the book before the record with this ISBN;");
			System.out.println("\t5) Give me 2 ISBN numbers and a book object, and I will insert a Node between them, if I find them;");
			System.out.println("\t6) Give me 2 ISBN numbers and I will swap them in the list for rearrangement of records, of course if they exist;");
			System.out.println("\t7) Tell me to COMMIT! Your command is my wish. I will commit your list to a file called Updated_Books;");
			System.out.println("\t8) Tell me to STOP TALKING. Remember, if you do not commit, I will not!");
			System.out.print("Enter your selection: ");
			choice = key.nextInt();
			
			switch (choice) {
			case 1:
				System.out.print("Please enter the year you wish to extract and store records from: ");
				int year = key.nextInt();
				bkLst.storeRecordsByYear(year);
				System.out.println("Successfully extracted and stored records into the file "+year+".txt");
				bkLst.displayContent();
				break;
			case 2:
				bkLst.delConsecutiveRepeatedRecords();
				System.out.println("Here are the contents of the list after removing consecutive duplicates");
				System.out.println("=======================================================================");
				bkLst.displayContent();
				break;
			case 3:
				System.out.print("Please enter the name of the author to create an extracted list: ");
				String newAuthor = key.next();
				newAuthor = key.next();
				bkLst.extractAuthList(newAuthor).displayContent();
				break;
			case 4:
				System.out.print("Enter the ISBN number: ");
				long isbn = key.nextLong();
				System.out.println("Enter the info for a new book object (title, author(s), price, isbn, genre, and year): ");
				String title = key.next();
				String author = key.next();
				double price = key.nextDouble();
				String newIsbn = key.next();
				String genre = key.next();
				int newYear = key.nextInt();
				Book b = new Book(title, author, price, newIsbn, genre, newYear);
				bkLst.insertBefore(isbn, b);
				System.out.println("Here are the contents of the list after inserting the new book object before the book with the corresponding isbn");
				System.out.println("=================================================================================================================");
				bkLst.displayContent();
				break;
			case 5:
				System.out.print("Enter the 1st ISBN number: ");
				long isbn1 = key.nextLong();
				System.out.print(" Enter the 2nd ISBN number: ");
				long isbn2 = key.nextLong();
				System.out.println("Enter the info for a new book object (title, author(s), price, isbn, genre, and year): ");
				title = key.next();
				author = key.next();
				price = key.nextDouble();
				newIsbn = key.next();
				genre = key.next();
				newYear = key.nextInt();
				b = new Book(title, author, price, newIsbn, genre, newYear);
				bkLst.insertBetween(isbn1, isbn2, b);
				System.out.println("Here are the contents of the list after inserting the new book object between the 2 books with the corresponding isbns");
				System.out.println("======================================================================================================================");
				bkLst.displayContent();
				break;
			case 6:
				System.out.print("Enter the 1st ISBN number: ");
				isbn1 = key.nextLong();
				System.out.print(" Enter the 2nd ISBN number: ");
				isbn2 = key.nextLong();
				bkLst.swap(isbn1, isbn2);
				bkLst.displayContent();
				break;
			case 7:
				bkLst.commit();
				hasCommited = true;
				System.out.println("Your wish is my command!");
				break;
			case 8:
				if (hasCommited) {
					System.out.print("I will now stop talking");
					System.exit(0);
				}
				else {
					System.out.println("I have not commited yet!");
					break;
				}
			}
		}
		key.close();
	}
}