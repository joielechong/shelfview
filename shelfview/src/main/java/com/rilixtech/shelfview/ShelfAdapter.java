package com.rilixtech.shelfview;

import android.content.Context;
import android.os.Environment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

class ShelfAdapter extends BaseAdapter {

  private Context mContext;
  private Utils utils;
  private List<ShelfModel> mShelfModels;
  private String internalStorage;

  ShelfAdapter(Context context, List<ShelfModel> shelfModels) {
    this.mContext = context;
    this.mShelfModels = shelfModels;
    this.utils = new Utils(context);
    this.internalStorage = Environment.getExternalStorageDirectory().toString();
  }

  @Override public int getCount() {
    return mShelfModels.size();
  }

  @Override public Object getItem(int position) {
    return mShelfModels.get(position);
  }

  @Override public long getItemId(int position) {
    return 0L;
  }

  private static class ViewHolder {
    ImageView shelfBackground, bookCover;
    CardView bookBackground;
    ProgressBar progressBar;
    View spine_grey, spine_white;
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {
    final ViewHolder holder;

    // Create views
    if (convertView == null) {
      LayoutInflater mInflater = LayoutInflater.from(mContext);
      convertView = mInflater.inflate(R.layout.book_shelf_grid_item, parent, false);
      holder = new ViewHolder();
      holder.shelfBackground = (ImageView) convertView.findViewById(R.id.shelf_background);
      holder.bookCover = (ImageView) convertView.findViewById(R.id.book_cover);
      holder.bookBackground = (CardView) convertView.findViewById(R.id.book_background);
      holder.progressBar = (ProgressBar) convertView.findViewById(R.id.progressBar);
      holder.spine_grey = convertView.findViewById(R.id.spine_grey);
      holder.spine_white = convertView.findViewById(R.id.spine_white);
      convertView.setTag(holder);
    }
    // Recycler view
    else {
      holder = (ViewHolder) convertView.getTag();
    }

    final ShelfModel model = this.mShelfModels.get(position);

    holder.progressBar.setVisibility(!model.getShow() ? View.GONE : View.VISIBLE);

    switch (model.getType()) {
      case "start":
        holder.shelfBackground.setImageResource(R.drawable.grid_item_background_left);
        break;
      case "end":
        holder.shelfBackground.setImageResource(R.drawable.grid_item_background_right);
        break;
      default:
        holder.shelfBackground.setImageResource(R.drawable.grid_item_background_center);
        break;
    }

    String bookCover = model.getBookCoverSource().trim();

    //FILE, URL, ASSET_FOLDER, DRAWABLE_FOLDER, NONE
    switch (model.getBookSource()) {
      case FILE:
        if (model.getShow() && !bookCover.equals("")) {
          Picasso.with(mContext)
              .load(new File(internalStorage + bookCover))
              .resize(utils.dpToPixels(mContext.getResources().getInteger(R.integer.book_width)),
                  utils.dpToPixels(mContext.getResources().getInteger(R.integer.book_height)))
              .into(holder.bookCover, new Callback() {
                @Override public void onSuccess() {
                  holder.bookBackground.setVisibility(!model.getShow() ? View.GONE : View.VISIBLE);
                  holder.progressBar.setVisibility(View.GONE);
                  holder.spine_grey.setVisibility(View.VISIBLE);
                  holder.spine_white.setVisibility(View.VISIBLE);
                }

                @Override public void onError() {

                }
              });
        }
        break;
      case URL:
        if (model.getShow() && !bookCover.equals("")) {
          Picasso.with(mContext)
              .load(bookCover)
              .resize(utils.dpToPixels(mContext.getResources().getInteger(R.integer.book_width)),
                  utils.dpToPixels(mContext.getResources().getInteger(R.integer.book_height)))
              .into(holder.bookCover, new Callback() {
                @Override public void onSuccess() {
                  holder.bookBackground.setVisibility(!model.getShow() ? View.GONE : View.VISIBLE);
                  holder.progressBar.setVisibility(View.GONE);
                  holder.spine_grey.setVisibility(View.VISIBLE);
                  holder.spine_white.setVisibility(View.VISIBLE);
                }

                @Override public void onError() {

                }
              });
        }
        break;
      case ASSET_FOLDER:
        if (model.getShow() && !bookCover.equals("")) {
          Picasso.with(mContext)
              .load("file:///android_asset/" + bookCover)
              .resize(utils.dpToPixels(mContext.getResources().getInteger(R.integer.book_width)),
                  utils.dpToPixels(mContext.getResources().getInteger(R.integer.book_height)))
              .into(holder.bookCover, new Callback() {
                @Override public void onSuccess() {
                  holder.bookBackground.setVisibility(!model.getShow() ? View.GONE : View.VISIBLE);
                  holder.progressBar.setVisibility(View.GONE);
                  holder.spine_grey.setVisibility(View.VISIBLE);
                  holder.spine_white.setVisibility(View.VISIBLE);
                }

                @Override public void onError() {

                }
              });
        }
        break;
      case DRAWABLE_FOLDER:
        if (model.getShow() && !bookCover.equals("")) {
          Picasso.with(mContext)
              .load(mContext.getResources().getIdentifier(bookCover, "drawable", mContext.getPackageName()))
              .resize(utils.dpToPixels(mContext.getResources().getInteger(R.integer.book_width)),
                  utils.dpToPixels(mContext.getResources().getInteger(R.integer.book_height)))
              .into(holder.bookCover, new Callback() {
                @Override public void onSuccess() {
                  holder.bookBackground.setVisibility(!model.getShow() ? View.GONE : View.VISIBLE);
                  holder.progressBar.setVisibility(View.GONE);
                  holder.spine_grey.setVisibility(View.VISIBLE);
                  holder.spine_white.setVisibility(View.VISIBLE);
                }

                @Override public void onError() {

                }
              });
        }
        break;

      default:
        if (model.getShow() && !bookCover.equals("")) {
          Picasso.with(mContext)
              .load(model.getBookCoverSource())
              .resize(utils.dpToPixels(mContext.getResources().getInteger(R.integer.book_width)),
                  utils.dpToPixels(mContext.getResources().getInteger(R.integer.book_height)))
              .into(holder.bookCover, new Callback() {
                @Override public void onSuccess() {
                  holder.bookBackground.setVisibility(!model.getShow() ? View.GONE : View.VISIBLE);
                  holder.progressBar.setVisibility(View.GONE);
                  holder.spine_grey.setVisibility(View.VISIBLE);
                  holder.spine_white.setVisibility(View.VISIBLE);
                }

                @Override public void onError() {

                }
              });
        } else {
          holder.bookBackground.setVisibility(View.GONE);
        }
    }

    return convertView;
  }

}