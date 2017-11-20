package com.rilixtech.shelfview;

import android.support.annotation.DrawableRes;
import android.support.annotation.RawRes;

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

  /**
   * Add book with image from assets folder.
   * @param assetName Name of assets file. Must be the complete name with extension.
   * @param bookId id of book.
   * @param bookTitle name of book.
   * @return model of book
   */
  public static BookModel assetBookModel(String assetName, String bookId, String bookTitle) {
    return new BookModel(assetName, bookId, bookTitle, BookSource.ASSET_FOLDER);
  }

  public static BookModel drawableBookModel(String drawableName, String bookId, String bookTitle) {
    return new BookModel(drawableName, bookId, bookTitle, BookSource.DRAWABLE_NAME);
  }

  public static BookModel drawableBookModel(@DrawableRes int drawableId, String bookId, String bookTitle) {
    return new BookModel(String.valueOf(drawableId), bookId, bookTitle, BookSource.DRAWABLE_ID);
  }

  public static BookModel rawBookModel(@RawRes int rawId, String bookId, String bookTitle) {
    return new BookModel(String.valueOf(rawId), bookId, bookTitle, BookSource.RAW);
  }
}

