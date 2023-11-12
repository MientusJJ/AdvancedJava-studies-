package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class BookManager {
    private Map<Integer, Book> books = new HashMap<>();
    private AtomicInteger idGenerator = new AtomicInteger();

    public int addBook(String title, String author, String genre) {
        int id = idGenerator.incrementAndGet();
        books.put(id, new Book(id, title, author, genre));
        return id;
    }
    public int addBook(Book book)
    {
        int id = idGenerator.incrementAndGet();
        book.setId(id);
        books.put(id,book);
        return id;
    }
    public ArrayList<Book> getAllBooks() {
        return new ArrayList<>(books.values());
    }

    public Book getBook(int id) {
        return books.get(id);
    }

}