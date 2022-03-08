/*
 * Author: VerNANDo57 <silvenation@gmail.com>
 * date: 2022/03/08 3:07PM GMT+7
 */

package com.verNANDo57.rulebook_educational.search;

import android.animation.AnimatorInflater;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.verNANDo57.rulebook_educational.bookmarks.AppBookmarkUtils;
import com.verNANDo57.rulebook_educational.extradata.R;
import com.verNANDo57.rulebook_educational.utils.AppUtils;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class SearchHolder extends RecyclerView.ViewHolder {
    public static Integer ITEM_IS_BOOKMARKED = R.drawable.app_bookmark_added;
    public static Integer ITEM_IS_NOT_BOOKMARKED = R.drawable.app_bookmark_removed;

    // Define CardView
    public final CardView search_item_card;
    // Define CardView's ImageView
    public final ImageView search_item_imageview;
    // Define CardView's ImageView that shows up when CardView is bookmarked
    public final ImageButton search_item_imagebutton;
    // Define CardView's Title TextView
    public final TextView search_item_title;
    // Define CardView's Summary TextView
    public final TextView search_item_summary;

    public SearchHolder(@NonNull View itemView, ArrayList<SearchItemData> listdata) {
        super(itemView);
        // Initialize CardView
        search_item_card = itemView.findViewById(R.id.recycler_view_item_card);
        // Initialize CardView's ImageView
        search_item_imageview = itemView.findViewById(R.id.cardview_image);
        // Initialize CardView's ImageView that shows up when CardView is bookmarked
        search_item_imagebutton = itemView.findViewById(R.id.cardview_imagebutton);
        // Initialize CardView's Title TextView
        search_item_title = itemView.findViewById(R.id.recyclerview_item_title);
        // Initialize CardView's Summary TextView
        search_item_summary= itemView.findViewById(R.id.recyclerview_item_summary);
        // Set CardView background value to TRANSPARENT (equals 0) to make it look better
        search_item_card.setCardBackgroundColor(0);
        // Remove shadow around CardView to make it look better
        search_item_card.setCardElevation(0);
        // Set custom StateListAnimator
        search_item_card.setStateListAnimator(AnimatorInflater.loadStateListAnimator(itemView.getContext(), R.animator.btn_anim_zoom));
        // If item is already bookmarked, then change imagebutton background
        for (int i=0;i<listdata.size();i++) {
            try {
                if (AppBookmarkUtils.checkIfBookmarkExist(itemView.getContext(), listdata.get(i).getItem_key())) {
                    listdata.get(i).setItem_imageBtnId(ITEM_IS_BOOKMARKED);
                }
            } catch (IOException e) {
                Log.e(AppUtils.LOG_TAG, itemView.getContext().getString(R.string.app_error_occurred));
                e.printStackTrace();
            }
        }
        // Handle CardView onClick action
        search_item_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get context from any VIEW object instead of passing CONTEXT as an argument to SearchAdapter and keeping it as class field
                SearchReferences.performSearchItemOnClickAction(itemView.getContext(), listdata.get(getAdapterPosition()).getItem_key(), getAdapterPosition());
            }
        });
        // Handle ImageButton click action
        search_item_imagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // If bookmark exist...
                    if (AppBookmarkUtils.checkIfBookmarkExist(itemView.getContext(), listdata.get(getAdapterPosition()).getItem_key())) {
                        // Then remove bookmark
                        AppBookmarkUtils.removeBookmark(itemView.getContext(), listdata.get(getAdapterPosition()).getItem_key(), listdata.get(getAdapterPosition()).getItemTitle(), listdata.get(getAdapterPosition()).getItemDescription());
                        listdata.get(getAdapterPosition()).setItem_imageBtnId(ITEM_IS_NOT_BOOKMARKED);
                        search_item_imagebutton.setImageResource(ITEM_IS_NOT_BOOKMARKED);
                    } else {
                        // Otherwise add bookmark
                        AppBookmarkUtils.addBookmark(itemView.getContext(), listdata.get(getAdapterPosition()).getItem_key(), listdata.get(getAdapterPosition()).getItemTitle(), listdata.get(getAdapterPosition()).getItemDescription());
                        listdata.get(getAdapterPosition()).setItem_imageBtnId(ITEM_IS_BOOKMARKED);
                        search_item_imagebutton.setImageResource(ITEM_IS_BOOKMARKED);
                    }
                } catch (IOException | JSONException e) {
                    Log.e(AppUtils.LOG_TAG, itemView.getContext().getString(R.string.app_error_occurred));
                    e.printStackTrace();
                }
            }
        });
    }
}
