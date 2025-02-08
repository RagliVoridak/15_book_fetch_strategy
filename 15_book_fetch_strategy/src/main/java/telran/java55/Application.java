package telran.java55;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import telran.java55.dao.BookRepository;

@SpringBootApplication
public class Application implements  CommandLineRunner {

	@Autowired
	BookRepository bookRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		bookRepository.addBooks();
		bookRepository.printBooks();
		
	}

}
