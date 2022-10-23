package com.bookcomet.service;

import com.bookcomet.converters.BookConverter;
import com.bookcomet.dto.DtoBook;
import com.bookcomet.entity.Book;
import com.bookcomet.entity.BookInventory;
import com.bookcomet.exceptions.BusinessException;
import com.bookcomet.repository.BookInventoryRepository;
import com.bookcomet.repository.BookRepository;
import com.bookcomet.service.impl.BookServiceImp;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
public class BookServiceTest {

	BookService bookService;

	@MockBean
	private BookConverter bookConverter;

	@MockBean
	BookRepository bookRepostiroy;

	@MockBean
	private  BookInventoryRepository bookInventoryRepository;

	private final  static  Long idBook = 123L;

	@BeforeEach
	public void setUp() {
		this.bookService = new BookServiceImp(bookRepostiroy,bookConverter, bookInventoryRepository );
	}

	@Test
	@DisplayName("should save a book successfully")
	public void savedBookTest() {

		Book mockBookEntity = createNewValidBook();
		mockBookEntity.setId(idBook);
		DtoBook mockDtoBook = createNewValidDtoBook();
		mockDtoBook.setId(idBook);

		Mockito.when(bookRepostiroy.save(Mockito.any())).thenReturn(mockBookEntity);
		Mockito.when(bookRepostiroy.existsByNameAndAuthor(Mockito.anyString(),Mockito.anyString())).thenReturn(false);
		Mockito.when(bookConverter.converterToEntity(Mockito.any())).thenReturn(mockBookEntity);
		Mockito.when(bookConverter.converterToDto(Mockito.any())).thenReturn(mockDtoBook);

		DtoBook newBook = bookService.save(mockDtoBook);

		assertThat(newBook.getId()).isNotNull();
		assertThat(newBook.getId()).isEqualTo(mockDtoBook.getId());
		assertThat(newBook.getName()).isEqualTo(mockDtoBook.getName());
		assertThat(newBook.getAuthor()).isEqualTo(mockDtoBook.getAuthor());
	}


	@Test
	@DisplayName("should update a book successfully")
	public void shouldUpdateBookSuccessfulyTest() {

		DtoBook book = createNewValidDtoBook();
		book.setId(idBook);

		Book mockBookEntity = createNewValidBook();
		mockBookEntity.setId(idBook);

		DtoBook mockDtoBook = DtoBook.builder().name("Clean code").id(idBook).author("Eduardo").build();

		Mockito.when(bookRepostiroy.save(Mockito.any())).thenReturn(mockBookEntity);
		Mockito.when(bookRepostiroy.findById(Mockito.any())).thenReturn(Optional.of(mockBookEntity));
		Mockito.when(bookConverter.converterToEntity(Mockito.any())).thenReturn(mockBookEntity);
		Mockito.when(bookConverter.converterToDto(Mockito.any())).thenReturn(mockDtoBook);

		DtoBook newBook = bookService.update(mockDtoBook);

		assertThat(newBook.getId()).isNotNull();
		assertThat(newBook.getId()).isEqualTo(mockDtoBook.getId());
		assertThat(newBook.getName()).isEqualTo(mockDtoBook.getName());
		assertThat(newBook.getAuthor()).isEqualTo(mockDtoBook.getAuthor());
	}

	@Test
	@DisplayName("should fail to save existing book")
	public void shouldFailToSaveExistingBook() {
		DtoBook mockDtoBook = createNewValidDtoBook();

		Mockito.when(bookRepostiroy.existsByNameAndAuthor(Mockito.anyString(),Mockito.anyString())).thenReturn(true);

		Throwable exception = Assertions.catchThrowable(() -> bookService.save(mockDtoBook));

		assertThat(exception).isInstanceOf(BusinessException.class).hasMessage("The book is already registered");
	}

	@Test
	@DisplayName("should fail to update existing book")
	public void shouldFailToUpdateExistingBook() {
		DtoBook mockDtoBook = createNewValidDtoBook();

		Mockito.when(bookRepostiroy.findById(idBook)).thenReturn(Optional.empty());

		Throwable exception = Assertions.catchThrowable(() -> bookService.update(mockDtoBook));

		assertThat(exception).isInstanceOf(BusinessException.class).hasMessage("Book ID is required for change.");
	}

	@Test
	@DisplayName("should fail to update no-existent book")
	public void shouldFailToUpdateNoExistentBook() {
		DtoBook mockDtoBook = createNewValidDtoBook(); mockDtoBook.setId(idBook);

		Mockito.when(bookRepostiroy.findById(idBook)).thenReturn(Optional.empty());

		Throwable exception = Assertions.catchThrowable(() -> bookService.update(mockDtoBook));

		assertThat(exception).isInstanceOf(BusinessException.class).hasMessage("There is no book with the given ID");
	}

	@Test
	@DisplayName("should delete a book successfully")
	public void shouldDeleteBookSuccessfully() {
		Book mockBook = createNewValidBook();

		Mockito.when(bookRepostiroy.findById(idBook)).thenReturn(Optional.of(mockBook));
		Mockito.when(bookInventoryRepository.findByBook(mockBook)).thenReturn(null);

		bookService.deleteById(idBook);

		Mockito.verify(bookRepostiroy, Mockito.times(1)).deleteById(idBook);
	}

	@Test
	@DisplayName("should fail to delete  no-existent book")
	public void shouldFailToDeleteNoExistentBook() {
		Mockito.when(bookRepostiroy.findById(idBook)).thenReturn(Optional.empty());
		Throwable exception = Assertions.catchThrowable(() -> bookService.deleteById(idBook));
		assertThat(exception).isInstanceOf(IllegalArgumentException.class).hasMessage("There is no book with the given ID");
	}

	@Test
	@DisplayName("should fail to delete  book with a posive inventory")
	public void shouldFailToDeleteBookWithPosiveInventory() {
		Book book = createNewValidBook(); book.setId(idBook);

		BookInventory mockInventory = BookInventory.builder()
				.book(book)
				.id(123L)
				.quantity(55L)
				.build();

		Mockito.when(bookRepostiroy.findById(idBook)).thenReturn(Optional.of(book));
		Mockito.when(bookInventoryRepository.findByBook(book)).thenReturn(mockInventory);


		Throwable exception = Assertions.catchThrowable(() -> bookService.deleteById(idBook));
		assertThat(exception).isInstanceOf(BusinessException.class).hasMessage("Cannot delete book because it has positive inventory.");
	}


	private DtoBook createNewValidDtoBook() {
		return DtoBook.builder()
				.name("Clean Architecture")
				.author("Robert")
				.publisher("Pearson")
				.yearOfPublication(2008).build();
	}

	private Book createNewValidBook() {
		return Book.builder()
				.id(idBook)
				.name("Clean Architecture")
				.author("Robert")
				.publisher("Pearson")
				.yearOfPublication(2008).build();
	}
}
