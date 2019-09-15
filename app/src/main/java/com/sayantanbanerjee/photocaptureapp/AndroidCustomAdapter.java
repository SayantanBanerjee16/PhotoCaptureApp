package com.sayantanbanerjee.photocaptureapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class AndroidCustomAdapter extends ArrayAdapter {

    private int MONTH_CURRENT;
    private int DAY_CURRENT;
    private int YEAR_CURRENT;
    private int HOUR_CURRENT;
    private int MINUTE_CURRENT;
    private int SECOND_CURRENT;
    private static final String IMAGE_DIRECTORY = "/PhotoCaptureApp";

    public AndroidCustomAdapter(Activity context, ArrayList<String> list) {
        super(context, 0,list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        String currentPhoto = (getItem(position)).toString();


        String day_string1 = Character.toString(currentPhoto.charAt(10)) + Character.toString(currentPhoto.charAt(11));
        String month_string1 = Character.toString(currentPhoto.charAt(8)) + Character.toString(currentPhoto.charAt(9));
        String year_string1 = Character.toString(currentPhoto.charAt(4)) + Character.toString(currentPhoto.charAt(5)) +
                Character.toString(currentPhoto.charAt(6)) + Character.toString(currentPhoto.charAt(7));

        DAY_CURRENT = Integer.parseInt(day_string1);
        MONTH_CURRENT = Integer.parseInt(month_string1);
        YEAR_CURRENT = Integer.parseInt(year_string1);

        String hour_string1 = Character.toString(currentPhoto.charAt(13)) + Character.toString(currentPhoto.charAt(14));
        HOUR_CURRENT = Integer.parseInt(hour_string1);

        String minute_string1 = Character.toString(currentPhoto.charAt(15)) + Character.toString(currentPhoto.charAt(16));
        MINUTE_CURRENT = Integer.parseInt(minute_string1);

        String second_string1 = Character.toString(currentPhoto.charAt(17)) + Character.toString(currentPhoto.charAt(18));
        SECOND_CURRENT = Integer.parseInt(second_string1);

        String minute;

        if(MINUTE_CURRENT == 0){
            minute = "00";
        }else if(MINUTE_CURRENT < 10){
            minute = "0" + MINUTE_CURRENT;
        }else {
            minute = Integer.toString(MINUTE_CURRENT);
        }

        String time,date;
        if (HOUR_CURRENT == 0) {
            time = Integer.toString(12) + " : " + minute  + " AM";
        } else if (HOUR_CURRENT < 12 && HOUR_CURRENT > 0) {
            time = Integer.toString(HOUR_CURRENT) + " : " + minute  + " AM";
        } else if (HOUR_CURRENT == 12)
        {
            time = Integer.toString(HOUR_CURRENT) + " : " + minute + " PM";
        } else
        {
            time = Integer.toString(HOUR_CURRENT - 12) + " : " + minute + " PM";
        }

        ArrayList<String> list = new ArrayList<String>();
        list.add("Jan");
        list.add("Feb");
        list.add("Mar");
        list.add("Apr");
        list.add("May");
        list.add("Jun");
        list.add("Jul");
        list.add("Aug");
        list.add("Sept");
        list.add("Oct");
        list.add("Nov");
        list.add("Dec");

        date = list.get(MONTH_CURRENT - 1) + " " + Integer.toString(DAY_CURRENT) +", " + Integer.toString(YEAR_CURRENT);


        TextView nameTextView = (TextView) listItemView.findViewById(R.id.photo_name);
        nameTextView.setText("NAME : " + currentPhoto);
        TextView dateTextView = (TextView) listItemView.findViewById(R.id.photo_date);
        dateTextView.setText("DATE : " + date);
        TextView timeTextView = (TextView) listItemView.findViewById(R.id.photo_time);
        timeTextView.setText("TIME : " + time);
        try {
            ImageView smallImage = (ImageView) listItemView.findViewById(R.id.list_item_icon);
            String path = Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY + "/" + currentPhoto;
            File directory = new File(path);
            if(directory.exists()){
                Bitmap bitmap = BitmapFactory.decodeFile(directory.getAbsolutePath());
                smallImage.setImageBitmap(bitmap);
            }
        }catch (Exception e){
            e.printStackTrace();
        }



        return listItemView;
    }
}
