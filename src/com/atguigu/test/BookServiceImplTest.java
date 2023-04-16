package com.atguigu.test;

import com.atguigu.pojo.Book;
import com.atguigu.pojo.Page;
import com.atguigu.service.BookService;
import com.atguigu.service.impl.BookServiceImpl;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

public class BookServiceImplTest {

    BookService bookService = new BookServiceImpl();

    @Test
    public void addBook() {
        bookService.addBook(new Book(null,"abccba","lzx",new BigDecimal(100000),100,0,null));
    }

    @Test
    public void deleteBookById() {
        bookService.deleteBookById(21);
    }

    @Test
    public void updateBook() {
        bookService.updateBook(new Book(22,"abc123","lzx",new BigDecimal(100000),100,0,null));
    }

    @Test
    public void queryBookById() {
        Book book = bookService.queryBookById(22);
        System.out.println(book);
    }

    @Test
    public void queryBooks() {
        List<Book> books = bookService.queryBooks();
        books.forEach(System.out::println);
    }

    @Test
    public void page(){
        Page<Book> page = bookService.page(1, Page.PAGE_SIZE);
        System.out.println(page);

    }
}