package com.sayantanbanerjee.photocaptureapp.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.sayantanbanerjee.photocaptureapp.MainActivity;
import com.sayantanbanerjee.photocaptureapp.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;
import static android.media.MediaRecorder.VideoSource.CAMERA;
import static android.os.Environment.getExternalStoragePublicDirectory;

public class HomePageFragment extends Fragment {

    ImageView imageView;
    TextView textView;
    TextView textDisp;
    private static final String IMAGE_DIRECTORY = "/PhotoCaptureApp";
    private String pictureImagePath = "";

    public int VAL = 0;
    SharedPreferences sharedPreferences;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Button button = (Button) view.findViewById(R.id.captureButton);
        imageView = view.findViewById(R.id.photoPlaceholder);
        textView = view.findViewById(R.id.footer);
        textDisp = view.findViewById(R.id.disp);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = timeStamp + ".jpg";
                File storageDir = Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES);
                pictureImagePath = storageDir.getAbsolutePath() + "/" + imageFileName;
                File file = new File(pictureImagePath);
                Uri outputFileUri = FileProvider.getUriForFile(getActivity(), "com.sayantanbanerjee.photocaptureapp.fileprovider", file);
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                cameraIntent.putExtra("return-data", true);
                startActivityForResult(cameraIntent, 1);
            }
        });
        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                File imgFile = new File(pictureImagePath);
                Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                try {
                    ExifInterface exif = new ExifInterface(imgFile.getPath());
                    int orientation = exif.getAttributeInt(
                            ExifInterface.TAG_ORIENTATION,
                            ExifInterface.ORIENTATION_NORMAL);

                    int angle = 0;

                    if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                        angle = 90;
                    } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
                        angle = 180;
                    } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                        angle = 270;
                    }

                    Matrix mat = new Matrix();
                    mat.postRotate(angle);
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 2;

                    Bitmap bmp = BitmapFactory.decodeStream(new FileInputStream(imgFile),
                            null, options);
                    bitmap = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(),
                            bmp.getHeight(), mat, true);
                    ByteArrayOutputStream outstudentstreamOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100,
                            outstudentstreamOutputStream);
                    imageView.setImageBitmap(bitmap);

                } catch (IOException e) {
                    Log.w("TAG", "-- Error in setting image");
                } catch (OutOfMemoryError oom) {
                    Log.w("TAG", "-- OOM Error in setting image");
                }
                if (imgFile.exists()) {

                    imageView.setImageBitmap(bitmap);
                    saveImage(bitmap);
                }
            }
        }
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }
        // Calendar.getInstance().getTimeInMillis()

        try {
            int val = sharedPreferences.getInt("firstTime", 0);
            sharedPreferences.edit().putInt("firstTime", val + 1).apply();
            File f = new File(wallpaperDirectory, "IMG" + val + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(getActivity(),
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::---&gt;" + f.getAbsolutePath());
            textView.setText("No of PICTURES total captured by this App are: " + Integer.toString(val - 1));
            textDisp.setVisibility(View.INVISIBLE);
            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        textView.setAlpha(0);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(getActivity(), "Permission denied...", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStop() {
        super.onStop();
        textDisp.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStart() {
        super.onStart();
        textView.setAlpha(0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ((ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) || (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) || (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)) {
                String[] permission = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                ActivityCompat.requestPermissions(getActivity(), permission, 1);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        sharedPreferences = getActivity().getSharedPreferences("com.sayantanbanerjee.photocaptureapp", Context.MODE_PRIVATE);
        int firstTime = sharedPreferences.getInt("firstTime", 0);
        int firstTime2 = firstTime;
        if (firstTime2 == 0) {
            sharedPreferences.edit().putInt("firstTime", 1).apply();
        }

        textView.setAlpha(1);
        if (firstTime2 == 0) {
            textView.setText("No of PICTURES total captured by this App are: " + 0);
        } else {
            textView.setText("No of PICTURES total captured by this App are: " + Integer.toString(firstTime2 - 1));
        }


    }
}
