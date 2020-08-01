package com.mahadik.example.graphqlex.repository;

import com.mahadik.example.graphqlex.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, String> {
}
