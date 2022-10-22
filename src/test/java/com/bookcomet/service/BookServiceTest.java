package com.bookcomet.service;

import com.bookcomet.converters.BookConverter;
import com.bookcomet.dto.DtoBook;
import com.bookcomet.entity.Book;
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
		Mockito.when(bookConverter.converterToEntity(Mockito.any())).thenReturn(mockBookEntity);
		Mockito.when(bookConverter.converterToDto(Mockito.any())).thenReturn(mockDtoBook);

		DtoBook newBook = bookService.save(mockDtoBook);

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
