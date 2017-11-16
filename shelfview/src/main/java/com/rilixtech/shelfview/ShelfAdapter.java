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
  private List<ShelfModel> mShelfModels;
  private String internalStorage;

  private int mTargetWidth;
  private int mTargetHeight;

  ShelfAdapter(Context context, List<ShelfModel> shelfModels) {
    this.mContext = context;
    this.mShelfModels = shelfModels;
    this.internalStorage = Environment.getExternalStorageDirectory().toString();

    mTargetWidth = Utils.dpToPixels(context, context.getResources().getInteger(R.integer.book_width));
    mTargetHeight = Utils.dpToPixels(context, context.getResources().getInteger(R.integer.book_height));
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
    ImageView imvShelfBackground;
    ImageView imvBookCover;
    CardView cvBookBackground;
    ProgressBar pgbLoad;
    View vSpineGrey, vSpineWhite;
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {
    final ViewHolder holder;

    // Create views
    if (convertView == null) {
      LayoutInflater mInflater = LayoutInflater.from(mContext);
      convertView = mInflater.inflate(R.layout.book_shelf_grid_item, parent, false);
      holder = new ViewHolder();
      holder.imvShelfBackground = convertView.findViewById(R.id.shelf_background);
      holder.imvBookCover = convertView.findViewById(R.id.book_cover_imv);
      holder.cvBookBackground = convertView.findViewById(R.id.book_background_cv);
      holder.pgbLoad = convertView.findViewById(R.id.load_pgb);
      holder.vSpineGrey = convertView.findViewById(R.id.spine_grey_view);
      holder.vSpineWhite = convertView.findViewById(R.id.spine_white_view);
      convertView.setTag(holder);
    }
    // Recycler view
    else {
      holder = (ViewHolder) convertView.getTag();
    }

    final ShelfModel model = this.mShelfModels.get(position);

    holder.pgbLoad.setVisibility(!model.getShow() ? View.GONE : View.VISIBLE);

    switch (model.getType()) {
      case START:
        holder.imvShelfBackground.setImageResource(R.drawable.grid_item_background_left);
        break;
      case END:
        holder.imvShelfBackground.setImageResource(R.drawable.grid_item_background_right);
        break;
      default:
        holder.imvShelfBackground.setImageResource(R.drawable.grid_item_background_center);
        break;
    }

    loadImageWithPicasso(mContext, model, holder);
    return convertView;
  }

  private void loadImageWithPicasso(Context context, final ShelfModel model, final ViewHolder holder) {
    String bookCover = model.getBookCoverSource().trim();
    if (model.getShow() && !bookCover.equals("")) {

      Callback callback = new Callback() {
        @Override public void onSuccess() {
          holder.cvBookBackground.setVisibility(!model.getShow() ? View.GONE : View.VISIBLE);
          holder.pgbLoad.setVisibility(View.GONE);
          holder.vSpineGrey.setVisibility(View.VISIBLE);
          holder.vSpineWhite.setVisibility(View.VISIBLE);
        }

        @Override public void onError() {

        }
      };

      switch (model.getBookSource()) {
        case FILE:
          Picasso.with(context)
              .load(new File(internalStorage + bookCover))
              .resize(mTargetWidth, mTargetHeight)
              .into(holder.imvBookCover, callback);
          break;
        case URL:
          Picasso.with(context).load(bookCover).resize(mTargetWidth, mTargetHeight).into(holder.imvBookCover, callback);
          break;
        case ASSET_FOLDER:
          Picasso.with(context)
              .load("file:///android_asset/" + bookCover)
              .resize(mTargetWidth, mTargetHeight)
              .into(holder.imvBookCover, callback);
          break;
        case DRAWABLE_NAME:
          Picasso.with(context)
              .load(context.getResources().getIdentifier(bookCover, "drawable", context.getPackageName()))
              .resize(mTargetWidth, mTargetHeight)
              .into(holder.imvBookCover, callback);
          break;
        case NONE:
          Picasso.with(context)
              .load(model.getBookCoverSource())
              .resize(mTargetWidth, mTargetHeight)
              .into(holder.imvBookCover, callback);
          break;
      }
    } else {
      holder.cvBookBackground.setVisibility(View.GONE);
      holder.pgbLoad.setVisibility(View.GONE);
      holder.vSpineGrey.setVisibility(View.VISIBLE);
      holder.vSpineWhite.setVisibility(View.VISIBLE);
    }
  }

}