package com.rebook.book.controller;

import com.rebook.book.controller.request.BookCreateRequest;
import com.rebook.book.controller.request.BookUpdateRequest;
import com.rebook.book.controller.response.BookResponse;
import com.rebook.book.service.BookService;
import com.rebook.book.service.command.BookUpdateCommand;
import com.rebook.book.service.dto.BookDto;
import com.rebook.common.schema.ListResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/books")
@RestController
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookResponse> saveBook(
            @RequestBody @Valid final BookCreateRequest bookCreateRequest
    ) {
        BookResponse book = BookResponse.from(
                bookService.save(bookCreateRequest.toCommand())
        );

        return ResponseEntity.created(URI.create("/api/v1/books/" + book.getId())).body(book);
    }

    @GetMapping
    public ResponseEntity<ListResponse<BookResponse>> getBooks() {
        List<BookDto> books = bookService.getBooks();
        List<BookResponse> bookResponses = books.stream()
                .map(BookResponse::from)
                .toList();
        ListResponse<BookResponse> response = new ListResponse<>(bookResponses);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookResponse> getBook(@PathVariable Long bookId) {
        BookResponse book = BookResponse.from(bookService.getBook(bookId));

        return ResponseEntity.ok()
                .body(book);
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<Void> updateBook(
            @PathVariable Long bookId,
            @RequestBody BookUpdateRequest bookUpdateRequest
    ) {
        bookService.updateBook(bookId, BookUpdateCommand.from(bookUpdateRequest));
        return ResponseEntity.ok()
                .build();
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.ok()
                .build();
    }
}
