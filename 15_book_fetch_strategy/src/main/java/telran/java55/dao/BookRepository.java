package telran.java55.dao;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceContextType;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import telran.java55.model.Author;
import telran.java55.model.Book;

@Repository
public class BookRepository {
	
	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	EntityManager em;
	
	@Transactional
	public void addBooks() {
        
		TypedQuery<Author> queryKing = em.createQuery(
		        "SELECT a FROM Author a WHERE a.fullName = :name", Author.class);
		    queryKing.setParameter("name", "Stephen Edwin King");

		    Author stephenEdwinKing;
		    List<Author> authors = queryKing.getResultList();
		    
		    if (authors.isEmpty()) {
		        stephenEdwinKing = Author.builder().fullName("Stephen Edwin King").build();
		        em.persist(stephenEdwinKing);
		    } else {
		        stephenEdwinKing = authors.get(0);
		    }

		    Book theShining = Book.builder()
		        .isbn("978-0-385-1233-5")
		        .author(stephenEdwinKing)
		        .title("The Shining")
		        .build();
		    em.persist(theShining);

		    TypedQuery<Author> queryIlf = em.createQuery(
		        "SELECT a FROM Author a WHERE a.fullName = :name", Author.class);
		    queryIlf.setParameter("name", "Ilya Ilf");
		    
		    TypedQuery<Author> queryPetrov = em.createQuery(
		        "SELECT a FROM Author a WHERE a.fullName = :name", Author.class);
		    queryPetrov.setParameter("name", "Yevgeny Petrov");

		    Author ilf = queryIlf.getResultList().stream().findFirst()
		                    .orElseGet(() -> {
		                        Author newIlf = Author.builder().fullName("Ilya Ilf").build();
		                        em.persist(newIlf);
		                        return newIlf;
		                    });

		    Author petrov = queryPetrov.getResultList().stream().findFirst()
		                    .orElseGet(() -> {
		                        Author newPetrov = Author.builder().fullName("Yevgeny Petrov").build();
		                        em.persist(newPetrov);
		                        return newPetrov;
		                    });

		    Book chairs12 = Book.builder()
		        .isbn("978-028873883")
		        .authors(Set.of(ilf, petrov))
		        .title("The Twelve Chairs")
		        .build();
		    em.persist(chairs12);
    	}
	
	
	public void printBooks() {
//		Book book = em.find(Book.class, "978-0-385-1233-5");
		
		TypedQuery<Book> query = em.createQuery("select b from Book b left join fetch b.authors a where b.isbn=?1", Book.class);
		query.setParameter(1, "978-0-385-1233-5");
		Book book = query.getSingleResult();
		System.out.println(book.getTitle());
		Set<Author> authors = book.getAuthors();
		authors.forEach(a -> System.out.println(a.getFullName()));
	}
}
