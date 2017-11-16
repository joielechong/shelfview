package com.rilixtech.shelfview;

class ShelfModel {

  private String bookCoverSource;
  private String bookId;
  private String bookTitle;
  private Boolean show;
  private String type;
  private BookSource bookSource;

  ShelfModel(String bookCoverSource, String bookId, String bookTitle, Boolean show, String type, BookSource bookSource) {
    this.bookCoverSource = bookCoverSource;
    this.bookId = bookId;
    this.bookTitle = bookTitle;
    this.show = show;
    this.type = type;
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

  public Boolean getShow() {
    return show;
  }

  public void setShow(Boolean show) {
    this.show = show;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public BookSource getBookSource() {
    return bookSource;
  }

  public void setBookSource(BookSource bookSource) {
    this.bookSource = bookSource;
  }
}

