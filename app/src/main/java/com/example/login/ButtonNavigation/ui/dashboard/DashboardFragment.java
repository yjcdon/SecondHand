package com.example.login.ButtonNavigation.ui.dashboard;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.login.MySQL.Product;
import com.example.login.TableInfo.ProductInfo;
import com.example.login.R;
import com.example.login.login.LogIn;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DashboardFragment extends Fragment {
    private ImageView imageViewUpload, imageViewDelete, imageViewRawImage;
    private Button btnUploadImage, btnPublish, btnCamera, btnAlbum, btnCancel;
    private EditText title, content, price;
    private Animation slideInAnimation;
    private FrameLayout mask;
    private LinearLayout myLinearLayout;
    private byte[] imageData;
    private static final int REQUEST_CODE_ALBUM = 0, REQUEST_IMAGES_VIDEO_PERMISSION = 1, REQUEST_CODE_CAMERA = 2, REQUEST_CAMERA_PERMISSION = 3;
    private static final int RESULT_CODE_SUCCESS = 100;
    private boolean isImageUploadSuccess = false;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        这个函数就是当我进行返回操作时,不会切换fragment,就能保存数据了
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                mask.setVisibility(View.GONE);
                mask.setAlpha(0.5f);
                imageViewRawImage.setVisibility(View.GONE);
                myLinearLayout.setVisibility(View.GONE);
            }
        });
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
        });

        mask.setOnClickListener(view16 -> {
            myLinearLayout.setVisibility(View.GONE);
            mask.setVisibility(View.GONE);
            mask.setAlpha(0.5f);
            imageViewRawImage.setVisibility(View.GONE);
        });

        btnCamera.setOnClickListener(view15 -> {
            openCamera();
        });

        btnAlbum.setOnClickListener(view14 -> {
            openAlbum();
        });

        btnCancel.setOnClickListener(view13 -> {
            myLinearLayout.setVisibility(View.GONE);
            mask.setVisibility(View.GONE);
        });

        imageViewDelete.setOnClickListener(view12 -> {
            imageViewUpload.setVisibility(View.GONE);
            imageViewDelete.setVisibility(View.GONE);
        });

        imageViewUpload.setOnClickListener(view18 -> {
            imageViewRawImage.setVisibility(View.VISIBLE);
            mask.setVisibility(View.VISIBLE);
            mask.setAlpha(1.0f);
            if (imageData != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
                imageViewRawImage.setImageBitmap(bitmap);
            }
        });

        imageViewRawImage.setOnClickListener(view19 -> {
            imageViewRawImage.setVisibility(View.GONE);
            mask.setAlpha(0.5f);
            mask.setVisibility(View.GONE);
        });

        btnPublish.setOnClickListener(view17 -> {
            ProductInfo productInfo = new ProductInfo();
            Product product = new Product();
//          先把数据都set进对象productInfo中,再用product来调用插入函数,productInfo做参数

            String titleText = title.getText().toString().trim();
            if (titleText.isEmpty()) {
                Toast.makeText(requireContext(), "请输入标题", Toast.LENGTH_SHORT).show();
                return; // 避免继续执行插入操作
            }
            productInfo.setTitle(titleText);

            String contentText = content.getText().toString();
            productInfo.setContent(contentText);

            try {
                BigDecimal priceText = new BigDecimal(price.getText().toString());
                productInfo.setPrice(priceText);
            } catch (NumberFormatException e) {
                Toast.makeText(requireContext(), "请输入有效的价格", Toast.LENGTH_SHORT).show();
                return;
            }

            if (isImageUploadSuccess && imageData != null) {
//                imageData在onActivityResult已经设置好了,所以直接set就行
                productInfo.setImage(imageData);
            } else {
                Toast.makeText(requireContext(), "请上传照片", Toast.LENGTH_SHORT).show();
                return;
            }

//            获取手机的时间并格式化
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String publishTimeText = dateFormat.format(new Date());
            productInfo.setPublishTime(publishTimeText);

            productInfo.setIsCollect(0);

            int stuNum = LogIn.getStuNumToAllAction();
            productInfo.setStuNum(stuNum);

            new Thread(() -> {
                int result = product.insertProductInfo(productInfo);
                requireActivity().runOnUiThread(() -> {
                    if (result != -1) {
                        Toast.makeText(requireContext(), "发布成功", Toast.LENGTH_SHORT).show();

//                      把之前输入的数据都清空,防止重复发布
                        title.setText("");
                        content.setText("");
                        price.setText("");
                        imageViewDelete.setVisibility(View.GONE);
                        imageViewUpload.setVisibility(View.GONE);
                        imageData = null;
                    } else {
                        Toast.makeText(requireContext(), "发布失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }).start();
        });
    }

    private void initView(View view) {
        imageViewUpload = view.findViewById(R.id.imageViewUpload);
        imageViewDelete = view.findViewById(R.id.imageViewDelete);
        imageViewRawImage = view.findViewById(R.id.imageViewRawImage);

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

    }


    private void openCamera() {
//        先申请权限,成功才能打开
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        } else {
            Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(openCameraIntent, REQUEST_CODE_CAMERA);
        }
    }

    private void openAlbum() {
//                                                                               这里的权限只需要一个就行
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_MEDIA_VIDEO, Manifest.permission.READ_MEDIA_IMAGES}, REQUEST_IMAGES_VIDEO_PERMISSION);
        } else {
            Intent openAlbumIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            openAlbumIntent.setType("image/*");
            startActivityForResult(openAlbumIntent, REQUEST_CODE_ALBUM);
        }

    }

    //    用于申请权限的回调函数
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(requireContext(), "相机权限被拒绝", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == REQUEST_IMAGES_VIDEO_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openAlbum();
            } else {
                Toast.makeText(requireContext(), "读取内存权限被拒绝", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //    回调函数,用于判断是否成功选择好了照片
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap photo = (Bitmap) extras.get("data");
                imageViewUpload.setImageBitmap(photo);
                isImageUploadSuccess = true;

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
                imageData = stream.toByteArray();
                mask.setVisibility(View.GONE);

            } else {
                isImageUploadSuccess = false;
            }

        } else if (requestCode == REQUEST_CODE_ALBUM && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            imageViewUpload.setImageURI(imageUri);
            isImageUploadSuccess = true;

            try {
                InputStream inputStream = requireActivity().getContentResolver().openInputStream(imageUri);
                imageData = getBytesFromInputStream(inputStream);
                mask.setVisibility(View.GONE);

            } catch (IOException e) {
                e.printStackTrace();
                isImageUploadSuccess = false;
            }
        } else {
            isImageUploadSuccess = false;
        }

        if (isImageUploadSuccess) {
            requireActivity().setResult(RESULT_CODE_SUCCESS, data);
            imageViewDelete.setVisibility(View.VISIBLE);
            myLinearLayout.setVisibility(View.INVISIBLE);
            imageViewUpload.setVisibility(View.VISIBLE);

        } else {
            requireActivity().setResult(RESULT_CANCELED);
        }
    }

    //    把输入流转换为字节数组
    private byte[] getBytesFromInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] data = new byte[4096];
        int n;
        while ((n = inputStream.read(data)) != -1) {
            buffer.write(data, 0, n);
        }
        buffer.flush();
        return buffer.toByteArray();
    }
}