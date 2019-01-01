package com.example.oalam.smartwater;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oways on 17-Dec-17.
 */

public class NotificationsAdapter extends ArrayAdapter<Notifications> {


    List<Notifications> wordlist;
    private Context context;
    int resource;

    public NotificationsAdapter(Context context, int resource, ArrayList<Notifications> wordlist) {
        super(context, 0, wordlist);
        this.context = context;
        this.resource = resource;
        this.wordlist = wordlist;


        // mColorResourseID=ColorResourceID;
    }

    // private static LayoutInflater inflater=null;
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Notifications my_words = (Notifications) getItem(position);

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(resource, null, false);

        TextView miwok_textView = (TextView) view.findViewById(R.id.textViewTitle);
        miwok_textView.setText(my_words.getmNotificationTitle());

        TextView default_textView = (TextView) view.findViewById(R.id.textViewText);
        default_textView.setText(my_words.getmNotificationText());

        TextView time_textView = (TextView) view.findViewById(R.id.textViewTime);
        time_textView.setText(my_words.getmNotificationTime());

//        ImageView imageView=(ImageView)listItemView.findViewById(R.id.imageView);
//        if(my_words.hasImage()){
//            imageView.setImageResource(my_words.getmImageResourceID());
//            imageView.setVisibility(View.VISIBLE);
//
//        }
//        else{
//
//            imageView.setVisibility(View.GONE);
//        }

//        View textContainer=view.findViewById(R.id.container);
//        int color= ContextCompat.getColor(getContext(),mColorResourseID);
//        textContainer.setBackgroundColor(color);

        return view;
    }
}

