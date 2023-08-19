package com.example.login.ButtonNavigation.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.login.R;

public class DashboardFragment extends Fragment {
    private ImageView imageViewUpload, imageViewDelete;
    private Button btnUploadImage, btnPublish, btnCamera, btnAlbum, btnCancel;
    private EditText title, content, price;
    private Animation slideInAnimation;
    private FrameLayout mask;
    private LinearLayout myLinearLayout;
    private ConstraintLayout db_myConstrainLayout;

    private static final int REQUEST_CODE_GALLERY = 0, RESULT_CODE_SUCCESS = 1, REQUEST_CODE_CAMERA = 2, REQUEST_CAMERA_PERMISSION = 3;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

        btnUploadImage.setOnClickListener(view1 -> {
            myLinearLayout.startAnimation(slideInAnimation);
            myLinearLayout.setVisibility(View.VISIBLE);
            mask.setVisibility(View.VISIBLE);

            disableInputFields();
        });

        db_myConstrainLayout.setOnClickListener(view12 -> {
            myLinearLayout.setVisibility(View.GONE);
            mask.setVisibility(View.GONE);

            enableInputFields();
        });

        btnCancel.setOnClickListener(view13 -> {
            myLinearLayout.setVisibility(View.GONE);
            mask.setVisibility(View.GONE);
            enableInputFields();
        });

        btnAlbum.setOnClickListener(view14 -> openAlbum());

    }

    private void initView(View view) {
        imageViewUpload = view.findViewById(R.id.imageViewUpload);
        imageViewDelete = view.findViewById(R.id.imageViewDelete);

        btnUploadImage = view.findViewById(R.id.btnUploadImage);
        btnPublish = view.findViewById(R.id.btnPublish);
        btnCamera = view.findViewById(R.id.btnCamera);
        btnAlbum = view.findViewById(R.id.btnAlbum);
        btnCancel = view.findViewById(R.id.btnCancel);

        title = view.findViewById(R.id.title);
        content = view.findViewById(R.id.content);
        price = view.findViewById(R.id.price);

        slideInAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in_bottom);

        mask = view.findViewById(R.id.mask);

        myLinearLayout = view.findViewById(R.id.myLinearLayout);

        db_myConstrainLayout = view.findViewById(R.id.db_myConstraintLayout);
    }

    private void disableInputFields() {
        title.setFocusable(false);
        content.setFocusable(false);
        price.setFocusable(false);
    }

    private void enableInputFields() {
        title.setFocusableInTouchMode(true);
        content.setFocusableInTouchMode(true);
        price.setFocusableInTouchMode(true);
    }

    private void openAlbum() {
        Intent openAlbumIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        openAlbumIntent.setType("image/*");
        startActivityForResult(openAlbumIntent, REQUEST_CODE_GALLERY);
    }
}