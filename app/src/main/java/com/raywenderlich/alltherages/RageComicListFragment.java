/*
 * Copyright (c) 2015 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.raywenderlich.alltherages;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RageComicListFragment extends Fragment {
    private final String TAG = "RageComicListFragment";
    private ArrayList<Integer> mImageResIds;
    private ArrayList<String> mNames;
    private ArrayList<String> mDescriptions;
    private ArrayList<String> mUrls;
    private OnRageComicSelected mListener;
    private RageComicAdapter rageComicAdapter;

    public interface OnRageComicSelected {
        void onRageComicSelected(int imageResId, String name, String description, String url);
    }
    public static RageComicListFragment newInstance() {
        return new RageComicListFragment();
    }

    public RageComicListFragment() {
        // Required empty public constructor
        Log.d(TAG, "RageComicAdapter() constructor called");
    }

    public int getCountOfElements() {
        return mImageResIds.size();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnRageComicSelected) {
            mListener = (OnRageComicSelected) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement OnRageComicSelected.");
        }
        Log.d(TAG, "onAttach called");
        // Get rage face names and descriptions.
        final Resources resources = context.getResources();
//        mNames = resources.getStringArray(R.array.names);

        String[] arrayOfStringNames = resources.getStringArray(R.array.names);
        mNames = new ArrayList<>(Arrays.asList(arrayOfStringNames));


        String[] arrayOfDescription = resources.getStringArray(R.array.descriptions);
        mDescriptions = new ArrayList<>(Arrays.asList(arrayOfDescription));

        String[] arrayOfUrls = resources.getStringArray(R.array.urls);
        mUrls = new ArrayList<>(Arrays.asList(arrayOfUrls));

        // Get rage face images.
        final TypedArray typedArray = resources.obtainTypedArray(R.array.images);
        final int imageCount = mNames.size();

        mImageResIds = new ArrayList<>();
        for (int i = 0; i < imageCount; i++) {
            mImageResIds.add(typedArray.getResourceId(i,0));
        }
//        mImageResIds = new int[imageCount];
//        for (int i = 0; i < imageCount; i++) {
//            mImageResIds.add(); = typedArray.getResourceId(i, 0);
////            Log.d(TAG, "onAttach: " + mImageResIds[i]);
//        }
        typedArray.recycle();
    }

    public void removeRageComic() {
//            Remove the last element from the ArrayList
//        check if the array is empty
        if(mNames.size() == 0) {
            Toast.makeText(getActivity(), "NO ELEMENT TO REMOVE",
                    Toast.LENGTH_SHORT).show();
        } else {
            mNames.remove(mNames.size()-1);
            mImageResIds.remove(mImageResIds.size()-1);
            mDescriptions.remove(mDescriptions.size()-1);
            mUrls.remove(mUrls.size()-1);
            rageComicAdapter.notifyItemRemoved(mUrls.size());
            rageComicAdapter.notifyItemRangeChanged(mUrls.size(), mUrls.size() + 1);
        }
    }

    public void addRageComic() {
//        Just duplicate the last element
        Log.d(TAG, "addRageComic: Count is" + mNames.size());
        for (int i = 0; i < mNames.size(); i++) {
            Log.d(TAG, "addRageComic: " + mNames.get(i));
        }

        mNames.add(mNames.get(mNames.size()-1));
        mDescriptions.add(mDescriptions.get(mDescriptions.size()-1));
        mUrls.add(mUrls.get(mUrls.size()-1));
        mImageResIds.add(mImageResIds.get(mImageResIds.size()-1));
//        Now  update the recyclerView
        rageComicAdapter.notifyItemInserted(getCountOfElements() - 1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_rage_comic_list, container, false);
        Log.d(TAG, "onCreateView called");
        final Activity activity = getActivity();
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(activity, 2));
        rageComicAdapter = new RageComicAdapter(activity);
        recyclerView.setAdapter(rageComicAdapter);
        return view;
    }

    class RageComicAdapter extends RecyclerView.Adapter<ViewHolder> {

        private LayoutInflater mLayoutInflater;

        public RageComicAdapter(Context context) {
            Log.d(TAG, "RageComicAdapter(context) constructor called");
            mLayoutInflater = LayoutInflater.from(context);

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            return new ViewHolder(mLayoutInflater
                    .inflate(R.layout.recycler_item_rage_comic, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            final int imageResId = mImageResIds.get(position);
            final String name = mNames.get(position);
            final String description = mDescriptions.get(position);
            final String url = mUrls.get(position);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick called for a ViewHolder object");
                    Log.d(TAG, view.toString());
                    mListener.onRageComicSelected(imageResId, name, description, url);
                }
            });
            viewHolder.setData(imageResId, name);
        }

        @Override
        public int getItemCount() {
            return mNames.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        // Views
        private ImageView mImageView;
        private TextView mNameTextView;

        private ViewHolder(View itemView) {
            super(itemView);

            // Get references to image and name.
            mImageView = (ImageView) itemView.findViewById(R.id.comic_image);
            mNameTextView = (TextView) itemView.findViewById(R.id.name);
        }

        private void setData(int imageResId, String name) {
            mImageView.setImageResource(imageResId);
            mNameTextView.setText(name);
        }
    }
}
