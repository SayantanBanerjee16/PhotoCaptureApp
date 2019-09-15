package com.sayantanbanerjee.photocaptureapp.fragments;

import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sayantanbanerjee.photocaptureapp.AndroidCustomAdapter;
import com.sayantanbanerjee.photocaptureapp.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class AllPhotosFragment extends Fragment {

    ListView listView;
    private static final String IMAGE_DIRECTORY = "/PhotoCaptureApp";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_allphotos,container,false);
        listView = (ListView) view.findViewById(R.id.listview_photos);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ArrayList<String> list = new ArrayList<String>();
        String path = Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY;

        File directory = new File(path);
        File[] files = directory.listFiles();

        for (int i = 0; i < files.length; i++)
        {
            String file_name = files[i].getName();
            list.add(file_name);
        }
        Collections.reverse(list);
        AndroidCustomAdapter customAdapter = new AndroidCustomAdapter(getActivity(), list);
        listView.setAdapter(customAdapter);
    }
}
