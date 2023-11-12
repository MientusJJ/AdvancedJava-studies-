
package com.company;
import java.io.*;
import java.net.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

class Book {
    private int id;
    private String title;
    private String author;
    private double price;
    private String  addedToLibrary;
    public Book(int id, String title, String author, double price,String lc) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.addedToLibrary = lc;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public double getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public String getAddedToLibrary() {
        return addedToLibrary;
    }

    public void setAddedToLibrary(String addedToLibrary) {
        this.addedToLibrary = addedToLibrary;
    }
}

public class BookstoreServer  {
    private static List<Book> books = new ArrayList<>();
    private static int bookIdCounter = 1;
    private static String readRequestBody(BufferedReader in, int contentLength) throws IOException {
        char[] buffer = new char[contentLength];
        in.read(buffer, 0, contentLength);
        return new String(buffer);
    }
    private static String formateDate(  LocalDateTime currentDateTime)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return  currentDateTime.format(formatter);

    }
    private static int cutTrash(BufferedReader in) throws IOException {
            int contentLength = 0;
            String headerLine;
            while ((headerLine = in.readLine()) != null && !headerLine.isEmpty()) {
                if (headerLine.startsWith("Content-Length:")) {
                    contentLength = Integer.parseInt(headerLine.substring(15).trim());
                }
            }

            return contentLength;
    }
    public static void main(String[] args) {
        addBook("Harry Potter, Rowling, 30.5");
        addBook("The Master and Margarita,Bukhailov, 35.32");
        addBook("Demons,Dostoyevsky, 44.22");
        try {

            ServerSocket serverSocket = new ServerSocket(8080);
            System.out.println("Server started on port 8080...");

            while (true)
            {
                Socket clientSocket = serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                String request = in.readLine();
                String response = "";
                if(request == null)
                {
                    out.println("empty");
                    continue;
                }
                if (request.startsWith("GET /books"))
                {
                    response = getAllBooks();
                }
                else if (request.startsWith("GET /book"))
                {
                    int contentLength = cutTrash(in);
                    String requestBody = readRequestBody(in, contentLength);
                    response = getBookDetails(Integer.parseInt(requestBody));
                }
                else if (request.startsWith("POST /add-book"))
                {
                    int contentLength = cutTrash(in);
                    String requestBody = readRequestBody(in, contentLength);
                    addBook(requestBody);
                    response = "Book added successfully!";
                }
                else if (request.startsWith("PUT /edit-book"))
                {
                    int contentLength = cutTrash(in);
                    String requestBody = readRequestBody(in, contentLength);
                    editBook(requestBody);
                    response = "Book updated successfully!";
                }
                else if (request.startsWith("DELETE /delete-book")) {
                    int contentLength = cutTrash(in);
                    String requestBody = readRequestBody(in, contentLength);
                    deleteBook(requestBody);
                    response = "Book deleted successfully!";
                }

                out.println("HTTP/1.1 200 OK");
                out.println("Content-Type: text/plain");
                out.println();
                out.println(response);
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getAllBooks() {
        StringBuilder result = new StringBuilder();
        for (Book book : books) {
            result.append("ID: ").append(book.getId()).append("\n");
            result.append("Title: ").append(book.getTitle()).append("\n");
            result.append("Author: ").append(book.getAuthor()).append("\n");
            result.append("\n");
        }
        return result.toString();
    }

    private static String getBookDetails(int bookId) {
        for (Book book : books) {
            if (book.getId() == bookId) {
                return "ID: " + book.getId() + "\n"
                        + "Title: " + book.getTitle() + "\n"
                        + "Author: " + book.getAuthor() + "\n"
                        + "Price: " + book.getPrice() + "\n"
                        + "Date added to library:   " + book.getAddedToLibrary() + "\n";
            }
        }
        return "Book not found.";
    }
    private static void addBook(String data) {
        String[] parts = data.split(",");
        if (parts.length == 3) {
            String title = parts[0].trim();
            String author = parts[1].trim();
            double price = Double.parseDouble(parts[2].trim());

            Book newBook = new Book(bookIdCounter++, title, author, price,formateDate(LocalDateTime.now()));
            books.add(newBook);
        }
    }

    private static void editBook(String data) {
        for (Book book : books) {
            String[] parts = data.split(",");
            if (book.getId() == Integer.parseInt(parts[0].trim())) {
                if (parts.length == 4) {
                    book.setTitle(parts[1].trim());
                    book.setAuthor(parts[2].trim());
                    book.setPrice(Double.parseDouble(parts[3].trim()));
                    book.setAddedToLibrary(formateDate(LocalDateTime.now()));
                }
            }
        }
    }

    private static void deleteBook(String name) {
        books.removeIf(book -> book.getTitle().equals( name));

    }
}