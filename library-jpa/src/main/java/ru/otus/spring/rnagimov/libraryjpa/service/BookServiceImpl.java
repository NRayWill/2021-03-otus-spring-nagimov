package ru.otus.spring.rnagimov.libraryjpa.service;

import org.apache.logging.log4j.util.Strings;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.rnagimov.libraryjpa.dto.BookDto;
import ru.otus.spring.rnagimov.libraryjpa.exception.NoElementWithSuchIdException;
import ru.otus.spring.rnagimov.libraryjpa.mapper.CommonMapper;
import ru.otus.spring.rnagimov.libraryjpa.model.Author;
import ru.otus.spring.rnagimov.libraryjpa.model.Book;
import ru.otus.spring.rnagimov.libraryjpa.model.Genre;
import ru.otus.spring.rnagimov.libraryjpa.repository.AuthorRepository;
import ru.otus.spring.rnagimov.libraryjpa.repository.BookRepository;
import ru.otus.spring.rnagimov.libraryjpa.repository.GenreRepository;
import ru.otus.spring.rnagimov.libraryjpa.util.ConvertUtils;

import java.util.List;
import java.util.Optional;

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
    public long createBook(String title, long authorId, long genreId) throws NoElementWithSuchIdException {
        Author author = authorRepository.findById(authorId).orElseThrow(() -> new NoElementWithSuchIdException("Author doesn't exist"));
        Genre genre = genreRepository.findById(genreId).orElseThrow(() -> new NoElementWithSuchIdException("Genre doesn't exist"));
        Book book = new Book(null, title, author, genre);
        return bookRepository.save(book).getId();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> getAll() {
        return ConvertUtils.convertEntityListToDtoList(bookRepository.findAll(), BookDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public BookDto getById(long id) throws NoElementWithSuchIdException {
        Book book = bookRepository.findById(id).orElseThrow(() -> new NoElementWithSuchIdException("Book doesn't exist"));
        return bookMapper.toDto(book, BookDto.class);
    }

    @Override
    @Transactional
    public void updateBook(long id, String title, Long authorId, Long genreId) throws NoElementWithSuchIdException {
        Book book = bookRepository.getById(id);
        if (Strings.isNotEmpty(title)) {
            book.setTitle(title);
        }
        if (authorId != null){
            Author author = authorRepository.findById(authorId).orElseThrow(() -> new NoElementWithSuchIdException("Author doesn't exist"));
            book.setAuthor(author);
        }
        if (genreId != null) {
            Genre genre = genreRepository.findById(genreId).orElseThrow(() -> new NoElementWithSuchIdException("Genre doesn't exist"));
            book.setGenre(genre);
        }
        bookRepository.save(book);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }
}
