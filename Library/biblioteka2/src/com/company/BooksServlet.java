package com.company;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
public class BooksServlet extends HttpServlet {

    private BookManager bookManager;
    public BooksServlet(BookManager bookManager) {
        this.bookManager = bookManager;
    }

    public BookManager getBookManager() {
        return bookManager;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String bookId = request.getParameter("id");

        if (bookId == null) {
            ArrayList<Book> allBooks = bookManager.getAllBooks();
            PrintWriter writer = response.getWriter();
            writer.println("Books");
            for (Book book : allBooks) {
                writer.println("ID: " + book.getId() +" Title: " + book.getTitle());
            }
        } else {
            Book book = bookManager.getBook(Integer.parseInt(bookId));
            if (book != null) {
                PrintWriter writer = response.getWriter();
                writer.println("ID: " + book.getId());
                writer.println("Title: " + book.getTitle());
                writer.println("Author: " + book.getAuthor());
                writer.println("Genre: " + book.getGenre());
            } else {
                response.setStatus(404);
                response.getWriter().println("Book not found");
            }
        }
    }


}
