package com.raywenderlich.alltherages;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by shashvat on 13/06/16.
 */
public class RageComicDetailsFragment extends Fragment {

    private static final String TAG = "ComicDetailsFragment";
    private static final String ARGUMENT_IMAGE_RES_ID = "imageResId";
    private static final String ARGUMENT_NAME = "name";
    private static final String ARGUMENT_DESCRIPTION = "description";
    private static final String ARGUMENT_URL = "url";

    public static RageComicDetailsFragment newInstance(int imageResId, String name, String description, String url) {
        final Bundle args = new Bundle();
        args.putInt(ARGUMENT_IMAGE_RES_ID, imageResId);
        args.putString(ARGUMENT_NAME, name);
        args.putString(ARGUMENT_DESCRIPTION, description);
        args.putString(ARGUMENT_URL, url);
        final RageComicDetailsFragment fragment = new RageComicDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public RageComicDetailsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rage_comic_details, container, false);
        Log.d("TAG", "onCreateView called");
        final ImageView imageView = (ImageView) view.findViewById(R.id.comic_image);
        final TextView nameTextView = (TextView) view.findViewById(R.id.name);
        final TextView descriptionTextView = (TextView) view.findViewById(R.id.description);

        Bundle args = getArguments();
        imageView.setImageResource(args.getInt(ARGUMENT_IMAGE_RES_ID));
        nameTextView.setText(args.getString(ARGUMENT_NAME));
        final String text = String.format(getString(R.string.description_format), args.getString(ARGUMENT_DESCRIPTION), args.getString(ARGUMENT_URL));
        Log.d(TAG, "onCreateView: finalText" + text);
        Log.d(TAG, "onCreateView: initText" + args.getInt(ARGUMENT_DESCRIPTION));
        descriptionTextView.setText(text);
        return view;
    }

}
