package com.mahadik.example.graphqlex.service;

import com.mahadik.example.graphqlex.model.Book;
import com.mahadik.example.graphqlex.repository.BookRepository;
import com.mahadik.example.graphqlex.service.datafetcher.AllBooksDataFetcher;
import com.mahadik.example.graphqlex.service.datafetcher.BookDataFetcher;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

@Service
public class GraphQLService {

    private GraphQL graphQL;

    @Value("classpath:books.graphql")
    private Resource resource;

    @Autowired
    private AllBooksDataFetcher allBooksDataFetcher;

    @Autowired
    private BookDataFetcher bookDataFetcher;

    @Autowired
    private BookRepository bookRepository;

    @PostConstruct
    private void load() throws IOException {
        loadDataHSQL();
        File resourceFile = resource.getFile();
        TypeDefinitionRegistry definitionRegistry = new SchemaParser().parse(resourceFile);
        RuntimeWiring wiring = buildRuntimeWiring();
        GraphQLSchema graphQLSchema = new SchemaGenerator().makeExecutableSchema(definitionRegistry, wiring);
        graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }

    private void loadDataHSQL() {
        Stream.of(
            new Book("100", "Clean Code", "Kindle", new String[]{"Ajit"}, "10 Jun"),
            new Book("101", "Microservices", "SBN", new String[]{"Hemant"}, "4 Aug"),
            new Book("102", "Kafka", "Orielly", new String[]{"Srey"}, "20 Jan")
        ).forEach(
                bookRepository::save
        );
    }

    private RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type("Query", typeWiring -> typeWiring
                        .dataFetcher("allBooks", allBooksDataFetcher)
                        .dataFetcher("book", bookDataFetcher))
                .build();
    }

    public GraphQL getGraphQL() {
        return graphQL;
    }
}