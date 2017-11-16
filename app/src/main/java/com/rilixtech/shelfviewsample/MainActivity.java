package com.rilixtech.shelfviewsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.rilixtech.shelfview.BookModel;
import com.rilixtech.shelfview.ShelfView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ShelfView.BookClickListener {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ShelfView shelfView = (ShelfView) findViewById(R.id.shelfView);
    shelfView.setOnBookClicked(this);
    List<BookModel> model = new ArrayList<>();

    model.add(BookModel.urlBookModel(getString(R.string.url_smashing_android_ui), "1",
        getString(R.string.title_smashing_android_ui)));
    model.add(BookModel.urlBookModel(getString(R.string.url_android_app_dev_for_dummies), "2",
        getString(R.string.title_android_app_dev_for_dummies)));
    model.add(BookModel.urlBookModel(getString(R.string.url_beginning_android), "3",
        getString(R.string.title_beginning_android)));
    model.add(BookModel.urlBookModel(getString(R.string.url_professional_android_programming), "4",
        getString(R.string.title_professional_android_programming)));
    model.add(BookModel.urlBookModel(getString(R.string.url_android_programming_pshing_the_limits), "5",
        getString(R.string.title_android_programming_pshing_the_limits)));
    model.add(BookModel.urlBookModel(getString(R.string.url_100_q_and_a), "6", getString(R.string.title_100_q_and_a)));
    model.add(BookModel.urlBookModel(getString(R.string.url_android_programming_for_beginners), "7",
        getString(R.string.title_android_programming_for_beginners)));
    model.add(BookModel.urlBookModel(getString(R.string.url_android_programming), "8",
        getString(R.string.title_android_programming)));
    model.add(BookModel.drawableBookModel("cover", "9", "Smashing Magazine"));
    //model.add(BookModel.urlBookModel(getString(R.string.url_android_programming), "9", getString(R.string.title_android_programming)));
    //model.add(BookModel.urlBookModel(getString(R.string.url_android_programming), "10", getString(R.string.title_android_programming)));
    model.add(BookModel.urlBookModel(getString(R.string.url_android_programming), "11",
        getString(R.string.title_android_programming)));

    shelfView.loadData(model);
  }

  @Override public void onBookClicked(int position, String bookId, String bookTitle) {
    Toast.makeText(this, bookTitle, Toast.LENGTH_SHORT).show();
  }
}
