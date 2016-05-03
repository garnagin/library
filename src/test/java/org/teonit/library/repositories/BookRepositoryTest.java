package org.teonit.library.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.teonit.library.config.TestApplicationConfig;
import org.teonit.library.domain.Book;
import org.teonit.library.domain.Language;
import org.teonit.library.domain.Organization;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestApplicationConfig.class })
@Transactional
public class BookRepositoryTest {

	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private LanguageRepository languageRepository;
	@Autowired
	private OrganizationRepository organizationRepository;

	@Before
	@Rollback(false)
	public void setUp() {
		Language en = new Language("en", "English");
		Language ua = new Language("uk", "Ukrainian");
		languageRepository.save(Arrays.asList(en, ua));

		Organization dummyPub = new Organization("Dummy Publishing");
		Organization happyPub = new Organization("Happy Publishing");
		organizationRepository.save(Arrays.asList(dummyPub, happyPub));

		Book book = new Book("Romeo and Juliet");
		book.setLanguage(en);
		book.setPublisher(happyPub);
		bookRepository.save(book);

		book = new Book("Giuseppe Verdi");
		book.setLanguage(en);
		book.setPublisher(happyPub);
		bookRepository.save(book);

		book = new Book("Dummy Books");
		book.setLanguage(en);
		book.setPublisher(dummyPub);
		bookRepository.save(book);

		book = new Book("Кобзар");
		book.setLanguage(ua);
		book.setPublisher(happyPub);
		bookRepository.save(book);
	}

	@Test
	public void testCount() {
		long count = bookRepository.count();

		assertEquals(4, count);
	}

	@Test
	public void testFindAll() {
		Iterable<Book> books = bookRepository.findAll();
		int bookCount = 0;
		for (Book book : books) {
			assertNotNull(book.getId());
			assertNotNull(book.getName());
			assertNotNull(book.getLanguage());
			assertNotNull(book.getPublisher());

			bookCount++;
		}
		;
		assertEquals(4, bookCount);
	}
/*
	@Test
	public void testFindByName() {
		Iterable<Book> books = bookRepository.findByNameContaining("Verdi");
		assertNotNull(books);
		int bookCount = 0;
		for (Book book : books) {
			assertNotNull(book.getId());
			assertEquals("Giuseppe Verdi", book.getName());
			assertEquals("English", book.getInLanguage().getName());
			assertEquals("Happy Publishing", book.getPublisher().getName());

			bookCount++;
		}
		;
		assertEquals(1, bookCount);
	}
*/
}
