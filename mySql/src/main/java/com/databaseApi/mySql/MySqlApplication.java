package com.databaseApi.mySql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class MySqlApplication {

	private static final Logger log = LoggerFactory.getLogger(MySqlApplication.class);

	public static void main(String[] args) {

		SpringApplication.run(MySqlApplication.class, args);

	}

	@Bean // This tells Spring to manage this CommandLineRunner
	public CommandLineRunner demo(AuthorRepository authorRepository, BookRepository bookRepository) {
		return (args) -> {
			log.info("Let's try some CRUD operations!");

			// --- CREATE ---
			log.info("Creating some authors and books...");
			Author author1 = new Author("J.K. Rowling", "UK");
			Author author2 = new Author("J.R.R. Tolkien", "UK");

			// Save authors first, so they get an ID
			authorRepository.save(author1);
			authorRepository.save(author2);
			log.info("Authors saved: {}, {}", author1, author2);

			Book book1 = new Book("Harry Potter and the Philosopher's Stone", "978-0747532699", author1);
			Book book2 = new Book("The Hobbit", "978-0547928227", author2);
			Book book3 = new Book("Harry Potter and the Chamber of Secrets", "978-0439064873", author1);

			// Using the helper method in Author to establish bidirectional relationship
			// author1.addBook(book1); // This would also save book1 if cascade is set up fully.
			// author1.addBook(book3);
			// author2.addBook(book2);
			// authorRepository.save(author1); // Re-save author to persist book relationships if cascade is from Author to Book
			// authorRepository.save(author2);
			// For simplicity with ManyToOne on Book being the owner:
			bookRepository.save(book1);
			bookRepository.save(book2);
			bookRepository.save(book3);

			log.info("Books saved!");

			// --- READ ---
			log.info("Reading all books...");
			List<Book> allBooks = bookRepository.findAll();
			for (Book book : allBooks) {
				log.info("Found book: {}", book.toString());
			}

			log.info("Finding a book by ID (e.g., ID 1)...");
			Optional<Book> foundBookOptional = bookRepository.findById(1L); // ID is Long, so 1L
			if (foundBookOptional.isPresent()) {
				Book foundBook = foundBookOptional.get();
				log.info("Found book by ID 1: {} by {}", foundBook.getTitle(), foundBook.getAuthor().getName());
			} else {
				log.info("Book with ID 1 not found.");
			}

			// --- UPDATE ---
			log.info("Updating a book...");
			// Let's say we want to update the ISBN of "The Hobbit"
			// First, we need to find it. Let's assume we know its ID (e.g., book2.getId())
			Optional<Book> bookToUpdateOptional = bookRepository.findById(book2.getId());
			if (bookToUpdateOptional.isPresent()) {
				Book bookToUpdate = bookToUpdateOptional.get();
				log.info("Book to update: {} with ISBN {}", bookToUpdate.getTitle(), bookToUpdate.getIsbn());
				bookToUpdate.setIsbn("NEW-ISBN-12345");
				bookRepository.save(bookToUpdate); // Save method is also used for updates!
				log.info("Book updated. New ISBN: {}", bookToUpdate.getIsbn());
			}

			// --- DELETE ---
			log.info("Deleting a book...");
			// Let's delete the first Harry Potter book (book1)
			// We need its ID.
			Long book1Id = book1.getId(); // Assuming book1 has been saved and has an ID
			if (book1Id != null) {
				bookRepository.deleteById(book1Id);
				log.info("Deleted book with ID: {}", book1Id);

				// Verify it's deleted
				Optional<Book> deletedBookOptional = bookRepository.findById(book1Id);
				if (deletedBookOptional.isPresent()) {
					log.error("Book was NOT deleted successfully!");
				} else {
					log.info("Book successfully deleted. No book found with ID {}", book1Id);
				}
			} else {
				log.warn("Cannot delete book1 as its ID is null (was it saved correctly?).");
			}

			log.info("Remaining books:");
			bookRepository.findAll().forEach(book -> log.info("{}", book));

			Optional<Author> author1Db = authorRepository.findById(1L);
			System.out.println(author1Db.get());

			log.info("CRUD Operations demo finished!");
		};
	}

}