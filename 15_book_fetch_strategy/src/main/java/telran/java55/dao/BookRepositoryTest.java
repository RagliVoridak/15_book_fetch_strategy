package telran.java55.dao;

import static org.junit.jupiter.api.Assertions.*;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import telran.java55.model.Book;

@DataJpaTest
//@Transactional
public class BookRepositoryTest {
	
	@Autowired
	private EntityManager em;
	
	@Autowired
	private BookRepository bookRepository;
	
	@Test
	void testAddBooks() {
		bookRepository.addBooks();
		
		Book book = em.find(Book.class, "978-0-385-1233-5");
		assertNotNull(book);
		assertEquals("The Shining", book.getTitle());
		
		assertEquals(1, book.getAuthors().size());
		assertEquals("Stephen Edwin King", book.getAuthors().iterator().next().getFullName());
	}
	
	@Test
	void testPrintBooks() {
		bookRepository.addBooks();
		bookRepository.printBooks();
		
		Book book = em.find(Book.class, "978-0-385-1233-5");
		assertNotNull(book);
		assertEquals("The Shining", book.getTitle());
		assertEquals(1, book.getAuthors().size());
	}
	
}
