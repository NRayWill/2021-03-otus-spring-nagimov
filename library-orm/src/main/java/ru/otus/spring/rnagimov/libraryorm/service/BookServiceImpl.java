package ru.otus.spring.rnagimov.libraryorm.service;

import org.apache.logging.log4j.util.Strings;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.rnagimov.libraryorm.dto.BookDto;
import ru.otus.spring.rnagimov.libraryorm.mapper.CommonMapper;
import ru.otus.spring.rnagimov.libraryorm.model.Author;
import ru.otus.spring.rnagimov.libraryorm.model.Book;
import ru.otus.spring.rnagimov.libraryorm.model.Genre;
import ru.otus.spring.rnagimov.libraryorm.repository.AuthorRepository;
import ru.otus.spring.rnagimov.libraryorm.repository.BookRepository;
import ru.otus.spring.rnagimov.libraryorm.repository.GenreRepository;
import ru.otus.spring.rnagimov.libraryorm.util.ConvertUtils;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    private final CommonMapper<Book, BookDto> bookMapper;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.bookMapper = new CommonMapper<>(modelMapper);
    }

    @Override
    @Transactional
    public long createBook(String title, long authorId, long genreId) {
        Author author = authorRepository.getById(authorId);
        Genre genre = genreRepository.getById(genreId);
        Book book = new Book(null, title, author, genre);
        return bookRepository.insert(book).getId();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> getAll() {
        return ConvertUtils.convertEntityListToDtoList(bookRepository.getAll(), BookDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public BookDto getById(long id) {
        return bookMapper.toDto(bookRepository.getById(id), BookDto.class);
    }

    @Override
    @Transactional
    public void updateBook(long id, String title, Long authorId, Long genreId) {
        Book book = bookRepository.getById(id);
        if (Strings.isNotEmpty(title)) {
            book.setTitle(title);
        }
        if (authorId != null){
            Author author = authorRepository.getById(authorId);
            book.setAuthor(author);
        }
        if (genreId != null) {
            Genre genre = genreRepository.getById(genreId);
            book.setGenre(genre);
        }
        bookRepository.update(book);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }
}
