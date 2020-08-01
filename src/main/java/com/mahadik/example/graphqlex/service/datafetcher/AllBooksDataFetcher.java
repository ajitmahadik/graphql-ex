package com.mahadik.example.graphqlex.service.datafetcher;

import com.mahadik.example.graphqlex.model.Book;
import com.mahadik.example.graphqlex.repository.BookRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AllBooksDataFetcher implements DataFetcher<List<Book>> {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Book> get(DataFetchingEnvironment environment) {
        return bookRepository.findAll();
    }
}
