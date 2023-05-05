package booklist;

public class Book {
	private String title;
	private String authors;
	private double price;
	private String isbn;
	private String genre;
	private int year;

	public Book() {}

	public Book(String title, String author, double price, String isbn, String genre, int year) {
		this.title = title;
		this.authors = author;
		this.price = price;
		this.isbn = isbn;
		this.genre = genre;
		this.year = year;
	}
	
	public Book(String[] s) {
		this.title = s[0];
		this.authors = s[1];
		this.price = Double.parseDouble(s[2]);
		this.isbn = s[3];
		this.genre = s[4];
		this.year = Integer.parseInt(s[5]);	
	}

	public void setTitle(String t) {this.title = t;}
	public void setAuthors(String a) {this.authors = a;}
	public void setPrice(double p) {this.price = p;}
	public void setIsbn(String i) {this.isbn = i;}
	public void setGenre(String g) {this.genre = g;}
	public void setYear(int y) {this.year = y;}
	
	public String getTitle() {return title;}
	public String getAuthors() {return authors;}
	public double getPrice() {return price;}
	public String getIsbn() {return isbn;}
	public String getGenre() {return genre;}
	public int getYear() {return year;}
	
	@Override
	public String toString() {
		return this.title + ", " + this.authors + ", " + this.price + ", "
				+ this.isbn + ", " + this.genre + ", " + this.year;
	}

	public boolean equals(Book b) {
		return (this.authors.equals(b.authors) && this.title.equals(b.title) && this.price == b.price 
				&& this.isbn.equals(b.isbn) && this.genre.equals(b.genre) && this.year == b.year);
	}
}
