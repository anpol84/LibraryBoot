package com.example.LibraryBoot;

import com.example.LibraryBoot.models.Book;
import com.example.LibraryBoot.models.Person;
import com.example.LibraryBoot.repositories.BookRepository;
import com.example.LibraryBoot.repositories.PersonRepository;
import com.example.LibraryBoot.services.PersonService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private BookRepository bookRepository;

    private PersonService personService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        personService = new PersonService(personRepository, bookRepository);
    }

    @Test
    public void testFindAll() {
        List<Person> personList = new ArrayList<>();
        personList.add(new Person("John", 1990));
        personList.add(new Person("Jane", 1985));

        when(personRepository.findAll()).thenReturn(personList);

        List<Person> resultList = personService.findAll();

        assertThat(resultList.size(), is(2));
        assertThat(resultList.get(0).getName(), is("John"));
        assertThat(resultList.get(1).getName(), is("Jane"));

        verify(personRepository, times(1)).findAll();
    }

    @Test
    public void testFindById() {
        Person person = new Person("John", 1990);
        person.setId(1);

        when(personRepository.findById(1)).thenReturn(java.util.Optional.of(person));

        Person result = personService.findById(1);

        assertThat(result.getName(), is("John"));
        assertThat(result.getYearOfBirth(), is(1990));

        verify(personRepository, times(1)).findById(1);
    }

    @Test
    public void testSetLater() {
        Person person = new Person("John", 1990);
        Book book1 = new Book("Title1", "Author1", 1000);
        Book book2 = new Book("Title2", "Author2", 1999);
        List<Book> bookList = new ArrayList<>();
        bookList.add(book1);
        bookList.add(book2);
        person.setBooks(bookList);

        when(personRepository.findById(1)).thenReturn(java.util.Optional.of(person));

        Person result = personService.setLater(1);


        assertFalse(result.getBooks().get(1).isLater());

        verify(personRepository, times(1)).findById(1);
    }

    @Test
    public void testSave() {
        Person person = new Person("John", 1990);

        personService.save(person);

        verify(personRepository, times(1)).save(person);
    }

    @Test
    public void testUpdate() {
        Person person = new Person("Jane", 1985);
        Person person1 = new Person("John", 1990);
        person1.setId(1);

        when(personRepository.findById(1)).thenReturn(java.util.Optional.of(person1));

        personService.update(person, 1);

        assertThat(person1.getName(), is("Jane"));
        assertThat(person1.getYearOfBirth(), is(1985));

        verify(personRepository, times(1)).findById(1);
        verify(personRepository, times(1)).save(person1);
    }

    @Test
    public void testDelete() {
        Person person = new Person("John", 1990);
        person.setId(1);

        when(personRepository.findById(1)).thenReturn(java.util.Optional.of(person));

        personService.delete(1);

        verify(personRepository, times(1)).delete(person);
    }

    @Test
    public void testFindByName() {
        Person person = new Person("John", 1990);

        when(personRepository.findByName("John")).thenReturn(person);

        Person result = personService.findByName("John");

        assertThat(result.getName(), is("John"));
        assertThat(result.getYearOfBirth(), is(1990));

        verify(personRepository, times(1)).findByName("John");
    }
}