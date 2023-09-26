package com.example.LibraryBoot;

import com.example.LibraryBoot.models.Book;
import com.example.LibraryBoot.models.Person;
import com.example.LibraryBoot.repositories.BookRepository;
import com.example.LibraryBoot.repositories.PersonRepository;
import com.example.LibraryBoot.services.BookService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    public void testFindAll() {
        List<Book> books = new ArrayList<>();
        books.add(new Book("Title1", "Author1", 2000));
        books.add(new Book("Title2", "Author2", 2005));
        books.add(new Book("Title3", "Author3", 2010));

        Mockito.when(bookRepository.findAll()).thenReturn(books);

        List<Book> result = bookService.findAll(null, null, null);
        Assert.assertEquals(books, result);
    }

    @Test
    public void testFindById() {
        Book book = new Book("Title1", "Author1", 2000);
        Mockito.when(bookRepository.findById(1)).thenReturn(Optional.of(book));

        Book result = bookService.findById(1);
        Assert.assertEquals(book, result);
    }

    @Test
    public void testFindStartingWith() {
        List<Book> books = new ArrayList<>();
        books.add(new Book("Title1", "Author1", 2000));
        books.add(new Book("Title2", "Author2", 2005));

        Mockito.when(bookRepository.findBooksByTitleStartingWith("T")).thenReturn(books);

        List<Book> result = bookService.findStartingWith("T");
        Assert.assertEquals(books, result);
    }

    @Test
    public void testSave() {
        Book book = new Book("Title1", "Author1", 2000);
        bookService.save(book);

        Mockito.verify(bookRepository).save(book);
    }

    @Test
    public void testUpdate() {
        Book book = new Book("Title1", "Author1", 2000);
        Book bookToUpdate = new Book("Title2", "Author2", 2005);

        Mockito.when(bookRepository.findById(1)).thenReturn(Optional.of(book));
        bookService.update(bookToUpdate, 1);

        Mockito.verify(bookRepository).save(book);
    }

    @Test
    public void testDelete() {
        Book book = new Book("Title1", "Author1", 2000);
        Mockito.when(bookRepository.findById(1)).thenReturn(Optional.of(book));

        bookService.delete(1);

        Mockito.verify(bookRepository).delete(book);
    }

    @Test
    public void testSet() {
        Book book = new Book("Title1", "Author1", 2000);
        Person person = new Person("John", 1924);
        Mockito.when(bookRepository.findById(1)).thenReturn(Optional.of(book));
        Mockito.when(personRepository.findById(1)).thenReturn(Optional.of(person));

        bookService.set(1, 1);

        Mockito.verify(bookRepository).save(book);
    }

    @Test
    public void testUnset() {
        Book book = new Book("Title1", "Author1", 2000);
        book.setPerson(new Person("John", 2005));
        Mockito.when(bookRepository.findById(1)).thenReturn(Optional.of(book));

        bookService.unset(1);

        Mockito.verify(bookRepository).save(book);
    }
}
