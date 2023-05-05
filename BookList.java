package booklist;

import java.io.*;
import java.util.ArrayList;

public class BookList {
	// private inner Node class
	private class Node {
		private Book b;
		private Node next;

		public Node() {
			b = null;
			next = null;
		}

		public Node(Book b, Node next) {
			this.b = b;
			this.next = next;
		}

	}

	// BookList class attribute
	private Node head;

	// constructors
	public BookList() {
		head = null;
	}

	// methods
	public void addToStart(Book b) {
		head = new Node(b, head);
	}

	public void addToEnd(Book b) {
		if (head == null) {
			addToStart(b);
		} else {
			// traverse book list until the current next item (or before last item of the list) is null,
			// and link a new node after that item, pointing to the book b
			Node current = head;
			while (current.next != null) {
				current = current.next;
			}
			current.next = new Node(b, null);
		}
	}

	public void storeRecordsByYear(int yr) {
		// method traverses book list and if the current the year of the book found in the current node
		// is equal to the year passed in as the parameter, the yearExists variable is true and the
		// current book is added to a temporary book ArrayList. The ArrayList is later used to write
		// to the year.txt file (created only if yearExists == true)
		String fileName = Integer.toString(yr) + ".txt";
		Boolean yearExists = false;
		ArrayList<Book> books = new ArrayList<>();
		Node current = head;

		try {
			while (current != null) {
				if (current.b.getYear() == yr) {
					yearExists = true;
					books.add(current.b);
				}
				current = current.next;
			}

			if (yearExists) {
				BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
				for (Book b : books) {
					writer.write(b.toString());
					writer.newLine();
				}
				writer.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// inserts Book b before the record which contains the isbn that is passed in (if it exists).
	public boolean insertBefore(long isbn, Book b) {
		Node previous = null;
		Node current = head;
		Boolean isbnExists = false;
		
		if (Long.parseLong(head.b.getIsbn()) == isbn) {
			isbnExists = true;
			addToStart(b);
		}
		else {
			while (current != null) {
				if (Long.parseLong(current.b.getIsbn()) == isbn) {
					isbnExists = true;
					previous.next = new Node(b, current);
				}
				previous = current;
				current = current.next;
			}
		}

		return isbnExists;
	}
	
	// inserts Book b between 2 consecutive records with attributes isbn1, and isbn2, respectively (if they exist).
	public boolean insertBetween(long isbn1, long isbn2, Book b) {
		Node current = head;
		Boolean isbnsExists = false;

		while (current != null) {
			// only adds node if the current's isbn == isbn1, AND the next isbn == isbn2
			if ((Long.parseLong(current.b.getIsbn()) == isbn1) && (Long.parseLong(current.next.b.getIsbn()) == isbn2)) {
				isbnsExists = true;
				current.next = new Node(b, current.next);
			}
			current = current.next;
		}
		return isbnsExists;
	}

	public boolean delConsecutiveRepeatedRecords() {
		Node previous = null;
		Node current = head;
		Boolean delRecords = false;

		while (current.next != null) {
			previous = current;
			current = current.next;

			// if the previous record is equal to the current record, method returns true to
			// indicate that consecutive records have been deleted. If two consecutive equal records
			// have been found, the method is called recursively in the case that there are more than
			// 2 equal consecutive records.

			if (previous.b.equals(current.b)) {
				delRecords = true;
				previous.next = current.next;
				delConsecutiveRepeatedRecords();
			}
		}
		return delRecords;
	}

	public void displayContent() {
		Node temp = head;

		while (temp != null) {
			System.out.println(temp.b.toString() + " ==>");
			temp = temp.next;
		}
		System.out.println("===> head");
	}
	
	// method returns a booklist that contains exclusively records from the specified author.
	public BookList extractAuthList(String aut) {
		Node header = head;
		BookList bL = new BookList();
		//extract all entries with same authors and adds it to the booklist
		while (header.next != null) {
			if (header.b.getAuthors().equals(aut)) {
				bL.addToEnd(header.b);
			}
			header = header.next;
		}

		return bL;
	}
	
	// methods swaps 2 records; one with isbn1, and the other with isbn2 (if they exist).
	// it swaps their position within the linked list.
	public boolean swap(long isbn1, long isbn2) {		
		Node current1 = null;
		Node current2 = null;
		Node next1 = null;
		Node next2 = null;

		boolean swap = false;
		Node header = head;

		//loop to find entries with corresponding isbn and storing the information into variable of the current entry and the next one
		while (header.next != null) {
			if (Long.parseLong(header.next.b.getIsbn()) == isbn1) {
				current1 = header;
				next1 = header.next;
			}
			if (Long.parseLong(header.next.b.getIsbn()) == isbn2) {
				current2 = header;
				next2 = header.next;
			}
			header = header.next;		
			
		}
		
		
		if ((current1 == null) || (current2 == null))
			return false;

		
		if (next1 != null && next2 != null) {
			swap = true;
			
			//for when the two entries are next to each other
			if (current2 == next1) {
				current1.next = new Node(next2.b, next1);
				next1.next = next2.next;
			} else if (current1 == next2) {
				current2.next = new Node(next1.b, next2);
				next2.next = next1.next;
			//for the rest
			} else {
				current2.next = new Node(next1.b,next2.next);
				current1.next = new Node(next2.b,next1.next);
			}
			
		}
		return swap;
	}
	
	public void commit() {
		Node header = head;
		String fileName = "Update_Books.txt";
		try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
				
				//write each entry to the file
				while(header.next !=null) {
					writer.write(header.b + "\n");
					header = header.next;
				}
				writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		
	}
}
