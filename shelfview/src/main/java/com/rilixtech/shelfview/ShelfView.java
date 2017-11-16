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

  private ShelfAdapter mShelfAdapter;
  private List<BookModel> mBookModels;
  private List<ShelfModel> mShelfModels;
  private int mNumberOfTilesPerRow;
  private int mShelfHeight;
  private int mShelfWidth;
  private int mGridViewColumnWidth;
  private int mGridItemHeight;
  private BookClickListener mBookClickListener;

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
    mGridViewColumnWidth = getContext().getResources().getInteger(R.integer.shelf_column_width);
    mGridItemHeight = getContext().getResources().getInteger(R.integer.shelf_list_item);
    mShelfModels = new ArrayList<>();
    mBookModels = new ArrayList<>();
    mShelfAdapter = new ShelfAdapter(context, mShelfModels);
    setAdapter(mShelfAdapter);
    setOnItemClickListener(this);
    initData(mBookModels);
  }

  /**
   * Populate shelf with books
   */
  public void loadData(final List<BookModel> bookModel) {
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
    mBookModels.clear();
    mBookModels.addAll(bookModel);
    mShelfModels.clear();
    for (int i = 0; i < bookModel.size(); i++) {
      String bookCoverSource = bookModel.get(i).getBookCoverSource();
      String bookId = bookModel.get(i).getBookId();
      String bookTitle = bookModel.get(i).getBookTitle();
      BookSource bookSource = bookModel.get(i).getBookSource();
      if ((i % mNumberOfTilesPerRow) == 0) {
        mShelfModels.add(new ShelfModel(bookCoverSource, bookId, bookTitle, true, ShelfType.START, bookSource));
      } else if ((i % mNumberOfTilesPerRow) == (mNumberOfTilesPerRow - 1)) {
        mShelfModels.add(new ShelfModel(bookCoverSource, bookId, bookTitle, true, ShelfType.END, bookSource));
      } else {
        mShelfModels.add(new ShelfModel(bookCoverSource, bookId, bookTitle, true, ShelfType.NONE, bookSource));
      }
    }

    int sizeOfModel = bookModel.size();
    int numberOfRows = sizeOfModel / mNumberOfTilesPerRow;
    int remainderTiles = sizeOfModel % mNumberOfTilesPerRow;

    if (remainderTiles > 0) {
      numberOfRows = numberOfRows + 1;
      int fillUp = mNumberOfTilesPerRow - remainderTiles;
      for (int i = 0; i < fillUp; i++) {
        if (i == (fillUp - 1)) {
          mShelfModels.add(new ShelfModel("", "", "", false, ShelfType.END, BookSource.NONE));
        } else {
          mShelfModels.add(new ShelfModel("", "", "", false, ShelfType.NONE, BookSource.NONE));
        }
      }
    }

    if ((numberOfRows * mGridItemHeight) < mShelfHeight) {
      int remainderRowHeight = (mShelfHeight - (numberOfRows * mGridItemHeight)) / mGridItemHeight;

      if (remainderRowHeight == 0) {
        for (int i = 0; i < mNumberOfTilesPerRow; i++) {
          if (i == 0) {
            mShelfModels.add(new ShelfModel("", "", "", false, ShelfType.START, BookSource.NONE));
          } else if (i == (mNumberOfTilesPerRow - 1)) {
            mShelfModels.add(new ShelfModel("", "", "", false, ShelfType.END, BookSource.NONE));
          } else {
            mShelfModels.add(new ShelfModel("", "", "", false, ShelfType.NONE, BookSource.NONE));
          }
        }
      } else if (remainderRowHeight > 0) {
        int fillUp = mNumberOfTilesPerRow * (remainderRowHeight + 1);
        for (int i = 0; i < fillUp; i++) {
          if ((i % mNumberOfTilesPerRow) == 0) {
            mShelfModels.add(new ShelfModel("", "", "", false, ShelfType.START, BookSource.NONE));
          } else if ((i % mNumberOfTilesPerRow) == (mNumberOfTilesPerRow - 1)) {
            mShelfModels.add(new ShelfModel("", "", "", false, ShelfType.END, BookSource.NONE));
          } else {
            mShelfModels.add(new ShelfModel("", "", "", false, ShelfType.NONE, BookSource.NONE));
          }
        }
      }
    }

    mShelfAdapter.notifyDataSetChanged();
  }

  /**
   * Create an empty shelf, in preparation for the books
   */
  private void initData(final List<BookModel> bookModel) {
    setColumnWidth(Utils.dpToPixels(getContext(), getResources().getInteger(R.integer.shelf_column_width)));
    setHorizontalSpacing(0);
    setVerticalSpacing(0);
    setNumColumns(AUTO_FIT);
    setVerticalScrollBarEnabled(false);
    setHorizontalScrollBarEnabled(false);

    // width & height of the shelfView will always return 0 because view is yet to be "shown" after creation;
    // Their actual values need to be captured here
    getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
      @Override public void onGlobalLayout() {

        if (Build.VERSION.SDK_INT < 16) {
          getViewTreeObserver().removeGlobalOnLayoutListener(this);
        } else {
          getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
        mShelfWidth = Utils.pixelsToDp(getContext(), getWidth());
        mShelfHeight = Utils.pixelsToDp(getContext(), getHeight());
        mNumberOfTilesPerRow = mShelfWidth / mGridViewColumnWidth;

        int sizeOfModel = bookModel.size();
        int numberOfRows = sizeOfModel / mNumberOfTilesPerRow;
        int remainderTiles = sizeOfModel % mNumberOfTilesPerRow;

        if (remainderTiles > 0) {
          numberOfRows = numberOfRows + 1;
          int fillUp = mNumberOfTilesPerRow - remainderTiles;
          for (int i = 0; i < fillUp; i++) {
            if (i == (fillUp - 1)) {
              mShelfModels.add(new ShelfModel("", "", "", false, ShelfType.END, BookSource.NONE));
            } else {
              mShelfModels.add(new ShelfModel("", "", "", false, ShelfType.NONE, BookSource.NONE));
            }
          }
        }

        if ((numberOfRows * mGridItemHeight) < mShelfHeight) {
          int remainderRowHeight = (mShelfHeight - (numberOfRows * mGridItemHeight)) / mGridItemHeight;

          if (remainderRowHeight == 0) {
            for (int i = 0; i < mNumberOfTilesPerRow; i++) {
              if (i == 0) {
                mShelfModels.add(new ShelfModel("", "", "", false, ShelfType.START, BookSource.NONE));
              } else if (i == (mNumberOfTilesPerRow - 1)) {
                mShelfModels.add(new ShelfModel("", "", "", false, ShelfType.END, BookSource.NONE));
              } else {
                mShelfModels.add(new ShelfModel("", "", "", false, ShelfType.NONE, BookSource.NONE));
              }
            }
          } else if (remainderRowHeight > 0) {
            int fillUp = mNumberOfTilesPerRow * (remainderRowHeight + 1);
            for (int i = 0; i < fillUp; i++) {
              if ((i % mNumberOfTilesPerRow) == 0) {
                mShelfModels.add(new ShelfModel("", "", "", false, ShelfType.START, BookSource.NONE));
              } else if ((i % mNumberOfTilesPerRow) == (mNumberOfTilesPerRow - 1)) {
                mShelfModels.add(new ShelfModel("", "", "", false, ShelfType.END, BookSource.NONE));
              } else {
                mShelfModels.add(new ShelfModel("", "", "", false, ShelfType.NONE, BookSource.NONE));
              }
            }
          }
        }
      }
    });

    mShelfAdapter.notifyDataSetChanged();
  }

  public interface BookClickListener {

    /**
     * Callback when book is clicked from the shelf
     */
    void onBookClicked(int position, String bookId, String bookTitle);
  }

  public void setOnBookClicked(BookClickListener bookClickListener) {
    this.mBookClickListener = bookClickListener;
  }

  @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    if (mShelfModels.get(position).getShow()) {
      if (mBookClickListener != null) {
        mBookClickListener.onBookClicked(position, mShelfModels.get(position).getBookId(),
            mShelfModels.get(position).getBookTitle());
      }
    }
  }
}
