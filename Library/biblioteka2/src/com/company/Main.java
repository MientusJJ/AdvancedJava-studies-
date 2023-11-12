package com.company;

public class Main {

    public static void main(String[] args) {
        HttpServer server = new HttpServer();
        BookManager bm = new BookManager();
        BooksServlet booksServlet = new BooksServlet(bm);
        AddBookServlet addBookServlet = new AddBookServlet(bm);
        booksServlet.getBookManager().addBook("Hobbit", "J.R.R. Tolkien", "Fantasy");
        booksServlet.getBookManager().addBook("Dune", "Frank Herbert", "Science Fiction");
        booksServlet.getBookManager().addBook("1984", "George Orwell", "Dystopian");
        server.addServlet("/books", booksServlet);
        server.addServlet("/addbook",addBookServlet);
        server.listen(8080);
    }
}
