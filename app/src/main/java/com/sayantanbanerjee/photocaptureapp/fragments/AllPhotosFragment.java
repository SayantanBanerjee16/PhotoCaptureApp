package com.sayantanbanerjee.photocaptureapp.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sayantanbanerjee.photocaptureapp.AndroidCustomAdapter;
import com.sayantanbanerjee.photocaptureapp.PicActivity;
import com.sayantanbanerjee.photocaptureapp.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class AllPhotosFragment extends Fragment {

    ListView listView;
    private View emptyview;
    ProgressBar progressBar;
    TextView loading;
    ArrayList<String> list;
    AndroidCustomAdapter customAdapter;
    private static final String IMAGE_DIRECTORY = "/PhotoCaptureApp";

    private void function(){
        loading.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        listView.setVisibility(View.INVISIBLE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_allphotos, container, false);
        emptyview = (View) view.findViewById(R.id.empty_view);
        loading = (TextView) view.findViewById(R.id.loading_view2);
        progressBar = (ProgressBar) view.findViewById(R.id.loading_view3);
        listView = (ListView) view.findViewById(R.id.listview_photos);
        return view;


    }

    @Override
    public void onResume() {
        super.onResume();
        loading.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        list = new ArrayList<String>();
        int cnt = 0;
        String path = Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY;
        File directory = new File(path);
        if (directory.exists()) {
            File[] files = directory.listFiles();
            for (int i = 0; i < files.length; i++) {
                String file_name = files[i].getName();
                String extension = file_name.substring(file_name.lastIndexOf("."));
                if(!(extension.equals(".jpg"))){
                   cnt++;
                }
            }
            if ((files.length - cnt) == 0) {
                emptyview.setVisibility(View.VISIBLE);
            } else {
                emptyview.setVisibility(View.INVISIBLE);
                for (int i = 0; i < files.length; i++) {
                    String file_name = files[i].getName();
                    String extension = file_name.substring(file_name.lastIndexOf("."));
                    if(extension.equals(".jpg")){
                        list.add(file_name);
                    }
                }
                Collections.reverse(list);
                DownloadTask task = new DownloadTask();
                task.execute();
                function();
            }
        } else {
            emptyview.setVisibility(View.VISIBLE);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String currentPhoto = list.get(i);
                String path = Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY + "/" + currentPhoto;
                File directory = new File(path);
                if(directory.exists()){
                    Intent intent = new Intent(getActivity(), PicActivity.class);
                    intent.putExtra("path",path);
                    startActivity(intent);
                }
            }
        });


    }

    private class DownloadTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... args) {
            customAdapter = new AndroidCustomAdapter(getActivity(), list);
            return "";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            loading.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            listView.setVisibility(View.INVISIBLE);
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loading.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
            listView.setVisibility(View.VISIBLE);
            listView.setAdapter(customAdapter);

        }
        
    }
}
