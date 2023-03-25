package com.hotel.booking.service;


public interface BookServiceImpl {

    BookData createBook(BookDTO bookDTO);

    List<BookData> getAllBookData();

    BookData updateRecordById(BookDTO bookDTO, int bookId);



    BookData deleteBookRecord(int bookId);

    BookData getBookModelById(int bookId);

    List<BookData> sortedListOfBooksInAscendingOrder();

    List<BookData> sortedListOfBooksInDescendingOrder();


    int getTotalBooksCount();

    List<BookData> searchByName(String name);
}