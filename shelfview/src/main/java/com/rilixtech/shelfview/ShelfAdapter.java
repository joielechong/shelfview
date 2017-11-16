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

/**
 * Copyright (c) 2017 Adediji Adeyinka(tdscientist)
 * All rights reserved
 * Created on 11-Feb-2017
 */

class ShelfAdapter extends BaseAdapter {

  private Context context;
  private Utils utils;
  private List<ShelfModel> model;
  //int bookSource = 999;
  private String internalStorage;

  public ShelfAdapter(Context context, List<ShelfModel> model) {
    this.context = context;
    this.model = model;
    this.utils = new Utils(context);
    this.internalStorage = Environment.getExternalStorageDirectory().toString();
  }

  @Override public int getCount() {
    return model.size();
  }

  @Override public Object getItem(int position) {
    return model.get(position);
  }

  @Override public long getItemId(int position) {
    return 0L;
  }

  private class ViewHolder {
    ImageView shelfBackground, bookCover;
    CardView bookBackground;
    ProgressBar progressBar;
    View spine_grey, spine_white;
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {

    final ViewHolder holder;
    LayoutInflater mInflater = LayoutInflater.from(context);

    if (convertView == null) {
      convertView = mInflater.inflate(R.layout.book_shelf_grid_item, parent, false);
      holder = new ViewHolder();
      convertView.setTag(holder);
    } else {
      holder = (ViewHolder) convertView.getTag();
    }

    holder.shelfBackground = (ImageView) convertView.findViewById(R.id.shelf_background);
    holder.bookCover = (ImageView) convertView.findViewById(R.id.book_cover);
    holder.bookBackground = (CardView) convertView.findViewById(R.id.book_background);
    holder.progressBar = (ProgressBar) convertView.findViewById(R.id.progressBar);
    holder.spine_grey = convertView.findViewById(R.id.spine_grey);
    holder.spine_white = convertView.findViewById(R.id.spine_white);

    final ShelfModel model = this.model.get(position);

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
          Picasso.with(context)
              .load(new File(internalStorage + bookCover))
              .resize(utils.dpToPixels(context.getResources().getInteger(R.integer.book_width)),
                  utils.dpToPixels(context.getResources().getInteger(R.integer.book_height)))
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
          Picasso.with(context)
              .load(bookCover)
              .resize(utils.dpToPixels(context.getResources().getInteger(R.integer.book_width)),
                  utils.dpToPixels(context.getResources().getInteger(R.integer.book_height)))
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
          Picasso.with(context)
              .load("file:///android_asset/" + bookCover)
              .resize(utils.dpToPixels(context.getResources().getInteger(R.integer.book_width)),
                  utils.dpToPixels(context.getResources().getInteger(R.integer.book_height)))
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
          Picasso.with(context)
              .load(context.getResources().getIdentifier(bookCover, "drawable", context.getPackageName()))
              .resize(utils.dpToPixels(context.getResources().getInteger(R.integer.book_width)),
                  utils.dpToPixels(context.getResources().getInteger(R.integer.book_height)))
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

      //default:
      //  if (model.getShow() && !bookCover.equals("")) {
      //    Picasso.with(context)
      //        .load(model.getBookCoverSource())
      //        .resize(utils.dpToPixels(context.getResources().getInteger(R.integer.book_width)),
      //            utils.dpToPixels(context.getResources().getInteger(R.integer.book_height)))
      //        .into(holder.bookCover, new Callback() {
      //          @Override public void onSuccess() {
      //            holder.bookBackground.setVisibility(!model.getShow() ? View.GONE : View.VISIBLE);
      //            holder.progressBar.setVisibility(View.GONE);
      //            holder.spine_grey.setVisibility(View.VISIBLE);
      //            holder.spine_white.setVisibility(View.VISIBLE);
      //          }
      //
      //          @Override public void onError() {
      //
      //          }
      //        });
      //  }
    }

    return convertView;
  }

  //public void setBookSource(int bookSource) {
  //  this.bookSource = bookSource;
  //}
}