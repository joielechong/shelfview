package com.rilixtech.shelfview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.GridView;
import java.util.ArrayList;
import java.util.List;

public class ShelfView extends GridView implements AdapterView.OnItemClickListener {

  Utils utils;
  private ShelfAdapter shelfAdapter;
  private List<BookModel> bookModel = new ArrayList<>();
  private List<ShelfModel> shelfModel;
  private int numberOfTilesPerRow;
  private int shelfHeight;
  private int shelfWidth;
  private int gridViewColumnWidth = getContext().getResources().getInteger(R.integer.shelf_column_width);
  private int gridItemHeight = getContext().getResources().getInteger(R.integer.shelf_list_item);

  public ShelfView(Context context) {
    super(context);
    init(context);
  }

  public ShelfView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context);
  }

  public ShelfView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context);
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public ShelfView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init(context);
  }

  private void init(Context context) {
    this.utils = new Utils(context);
    this.shelfModel = new ArrayList<>();
    this.shelfAdapter = new ShelfAdapter(context, shelfModel);
    setAdapter(shelfAdapter);
    setOnItemClickListener(this);
    initData(bookModel);
  }

  /**
   * Populate shelf with books
   */

  //public void loadData(final ArrayList<BookModel> bookModel, int bookSource) {
  //  this.shelfAdapter.setBookSource(bookSource);
  //  new Handler().postDelayed(new Runnable() {
  //    @Override public void run() {
  //      processData(bookModel);
  //    }
  //  }, 300);
  //}

  public void loadData(final List<BookModel> bookModel) {
    //this.shelfAdapter.setBookSource(bookSource);
    new Handler().postDelayed(new Runnable() {
      @Override public void run() {
        processData(bookModel);
      }
    }, 300);
  }

  /**
   * Actual book population on the shelf
   */

  private void processData(final List<BookModel> bookModel) {
    this.bookModel.clear();
    this.bookModel.addAll(bookModel);
    this.shelfModel.clear();
    for (int i = 0; i < bookModel.size(); i++) {
      String bookCoverSource = bookModel.get(i).getBookCoverSource();
      String bookId = bookModel.get(i).getBookId();
      String bookTitle = bookModel.get(i).getBookTitle();
      BookSource bookSource = bookModel.get(i).getBookSource();
      if ((i % numberOfTilesPerRow) == 0) {
        shelfModel.add(new ShelfModel(bookCoverSource, bookId, bookTitle, true, "start", bookSource));
      } else if ((i % numberOfTilesPerRow) == (numberOfTilesPerRow - 1)) {
        shelfModel.add(new ShelfModel(bookCoverSource, bookId, bookTitle, true, "end", bookSource));
      } else {
        shelfModel.add(new ShelfModel(bookCoverSource, bookId, bookTitle, true, "", bookSource));
      }
    }

    int sizeOfModel = bookModel.size();
    int numberOfRows = sizeOfModel / numberOfTilesPerRow;
    int remainderTiles = sizeOfModel % numberOfTilesPerRow;

    if (remainderTiles > 0) {
      numberOfRows = numberOfRows + 1;
      int fillUp = numberOfTilesPerRow - remainderTiles;
      for (int i = 0; i < fillUp; i++) {
        if (i == (fillUp - 1)) {
          shelfModel.add(new ShelfModel("", "", "", false, "end", BookSource.NONE));
        } else {
          shelfModel.add(new ShelfModel("", "", "", false, "", BookSource.NONE));
        }
      }
    }

    if ((numberOfRows * gridItemHeight) < shelfHeight) {
      int remainderRowHeight = (shelfHeight - (numberOfRows * gridItemHeight)) / gridItemHeight;

      if (remainderRowHeight == 0) {
        for (int i = 0; i < numberOfTilesPerRow; i++) {
          if (i == 0) {
            shelfModel.add(new ShelfModel("", "", "", false, "start", BookSource.NONE));
          } else if (i == (numberOfTilesPerRow - 1)) {
            shelfModel.add(new ShelfModel("", "", "", false, "end", BookSource.NONE));
          } else {
            shelfModel.add(new ShelfModel("", "", "", false, "", BookSource.NONE));
          }
        }
      } else if (remainderRowHeight > 0) {
        int fillUp = numberOfTilesPerRow * (remainderRowHeight + 1);
        for (int i = 0; i < fillUp; i++) {
          if ((i % numberOfTilesPerRow) == 0) {
            shelfModel.add(new ShelfModel("", "", "", false, "start", BookSource.NONE));
          } else if ((i % numberOfTilesPerRow) == (numberOfTilesPerRow - 1)) {
            shelfModel.add(new ShelfModel("", "", "", false, "end", BookSource.NONE));
          } else {
            shelfModel.add(new ShelfModel("", "", "", false, "", BookSource.NONE));
          }
        }
      }
    }

    shelfAdapter.notifyDataSetChanged();
  }

  /**
   * Create an empty shelf, in preparation for the books
   */

  private void initData(final List<BookModel> bookModel) {
    this.bookModel.clear();
    this.bookModel.addAll(bookModel);
    this.shelfModel.clear();
    setColumnWidth(utils.dpToPixels(getResources().getInteger(R.integer.shelf_column_width)));
    setHorizontalSpacing(0);
    setVerticalSpacing(0);
    setNumColumns(AUTO_FIT);
    setVerticalScrollBarEnabled(false);
    setHorizontalScrollBarEnabled(false);

    // width & height of the shelfView will always return 0 because view is yet to be "shown" after creation;
    // Their actual values need to be captured here
    getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
      @Override public void onGlobalLayout() {

        //getViewTreeObserver().removeOnGlobalLayoutListener(this);
        if (Build.VERSION.SDK_INT < 16) {
          getViewTreeObserver().removeGlobalOnLayoutListener(this);
        } else {
          getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
        shelfWidth = utils.pixelsToDp(getWidth());
        shelfHeight = utils.pixelsToDp(getHeight());
        numberOfTilesPerRow = shelfWidth / gridViewColumnWidth;

        int sizeOfModel = bookModel.size();
        int numberOfRows = sizeOfModel / numberOfTilesPerRow;
        int remainderTiles = sizeOfModel % numberOfTilesPerRow;

        if (remainderTiles > 0) {
          numberOfRows = numberOfRows + 1;
          int fillUp = numberOfTilesPerRow - remainderTiles;
          for (int i = 0; i < fillUp; i++) {
            if (i == (fillUp - 1)) {
              shelfModel.add(new ShelfModel("", "", "", false, "end", BookSource.NONE));
            } else {
              shelfModel.add(new ShelfModel("", "", "", false, "", BookSource.NONE));
            }
          }
        }

        if ((numberOfRows * gridItemHeight) < shelfHeight) {
          int remainderRowHeight = (shelfHeight - (numberOfRows * gridItemHeight)) / gridItemHeight;

          if (remainderRowHeight == 0) {
            for (int i = 0; i < numberOfTilesPerRow; i++) {
              if (i == 0) {
                shelfModel.add(new ShelfModel("", "", "", false, "start", BookSource.NONE));
              } else if (i == (numberOfTilesPerRow - 1)) {
                shelfModel.add(new ShelfModel("", "", "", false, "end", BookSource.NONE));
              } else {
                shelfModel.add(new ShelfModel("", "", "", false, "", BookSource.NONE));
              }
            }
          } else if (remainderRowHeight > 0) {
            int fillUp = numberOfTilesPerRow * (remainderRowHeight + 1);
            for (int i = 0; i < fillUp; i++) {
              if ((i % numberOfTilesPerRow) == 0) {
                shelfModel.add(new ShelfModel("", "", "", false, "start", BookSource.NONE));
              } else if ((i % numberOfTilesPerRow) == (numberOfTilesPerRow - 1)) {
                shelfModel.add(new ShelfModel("", "", "", false, "end", BookSource.NONE));
              } else {
                shelfModel.add(new ShelfModel("", "", "", false, "", BookSource.NONE));
              }
            }
          }
        }
      }
    });

    shelfAdapter.notifyDataSetChanged();
  }

  public interface BookClickListener {

    /**
     * Callback when book is clicked from the shelf
     */
    void onBookClicked(int position, String bookId, String bookTitle);
  }

  private BookClickListener bookClickListener;

  public void setOnBookClicked(BookClickListener bookClickListener) {
    this.bookClickListener = bookClickListener;
  }

  @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    if (shelfModel.get(position).getShow()) {
      if (bookClickListener != null) {
        bookClickListener.onBookClicked(position, shelfModel.get(position).getBookId(),
            shelfModel.get(position).getBookTitle());
      }
    }
  }
}
