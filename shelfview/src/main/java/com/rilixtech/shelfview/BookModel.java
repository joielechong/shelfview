package com.rilixtech.shelfview;

public class BookModel {
  private String bookCoverSource;
  private String bookId;
  private String bookTitle;
  private BookSource bookSource;

  private BookModel(String bookCoverSource, String bookId, String bookTitle, BookSource bookSource) {
    this.bookCoverSource = bookCoverSource;
    this.bookId = bookId;
    this.bookTitle = bookTitle;
    this.bookSource = bookSource;
  }

  public String getBookCoverSource() {
    return bookCoverSource;
  }

  public void setBookCoverSource(String bookCoverSource) {
    this.bookCoverSource = bookCoverSource;
  }

  public String getBookId() {
    return bookId;
  }

  public void setBookId(String bookId) {
    this.bookId = bookId;
  }

  public String getBookTitle() {
    return bookTitle;
  }

  public void setBookTitle(String bookTitle) {
    this.bookTitle = bookTitle;
  }

  public BookSource getBookSource() {
    return bookSource;
  }

  public void setBookSource(BookSource bookSource) {
    this.bookSource = bookSource;
  }

  public static BookModel fileBookModel(String bookCoverSource, String bookId, String bookTitle) {
    return new BookModel(bookCoverSource, bookId, bookTitle, BookSource.FILE);
  }

  public static BookModel urlBookModel(String bookCoverSource, String bookId, String bookTitle) {
    return new BookModel(bookCoverSource, bookId, bookTitle, BookSource.URL);
  }

  public static BookModel assetBookModel(String bookCoverSource, String bookId, String bookTitle) {
    return new BookModel(bookCoverSource, bookId, bookTitle, BookSource.ASSET_FOLDER);
  }

  public static BookModel drawableBookModel(String bookCoverSource, String bookId, String bookTitle) {
    return new BookModel(bookCoverSource, bookId, bookTitle, BookSource.DRAWABLE_FOLDER);
  }
}

