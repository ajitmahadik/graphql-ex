package com.mahadik.example.graphqlex.service.datafetcher;

import com.mahadik.example.graphqlex.model.Book;
import com.mahadik.example.graphqlex.repository.BookRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookDataFetcher implements DataFetcher<Book> {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book get(DataFetchingEnvironment environment) {
        String isn = environment.getArgument("id");
        return bookRepository.getOne(isn);
    }
}
