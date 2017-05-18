package com.dentaltw.criminalintent;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import java.util.Date;

/**
 * Created by Scott on 2017/5/7.
 */

public class PhotoPickerFragment extends DialogFragment {
    public static final String EXTRA_PHOTO = "com.dentaltw.android.criminalintent.photo";
    private ImageView dialogPhoto;

    public static PhotoPickerFragment newInstance(String photoPath){
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_PHOTO, photoPath);

        PhotoPickerFragment fragment = new PhotoPickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        FragmentActivity activity = getActivity();
        String filePath = (String) getArguments().getSerializable(EXTRA_PHOTO);
        Bitmap bitmap = PictureUtils.getScaledBitmap(filePath, activity);
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_photo, null);
        dialogPhoto = (ImageView) view.findViewById(R.id.dialog_photo);
        dialogPhoto.setImageBitmap(bitmap);
        return new AlertDialog.Builder(activity)
            .setView(view)
            .setTitle(R.string.photo_title)
            .create();
    }

    private void sendResult(int resultCode, Date date){
        if(getTargetFragment()==null){
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_PHOTO, date);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
