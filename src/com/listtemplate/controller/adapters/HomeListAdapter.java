package com.listtemplate.controller.adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.listtemplate.R;
import com.listtemplate.model.AppModel;
import com.listtemplate.model.data.type.CurrentlyUsedList;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Catalin BORA on 1/24/14.
 *
 */
public class HomeListAdapter extends ArrayAdapter<CurrentlyUsedList>{

    private static List<CurrentlyUsedList> mOpenLists;
    private static LayoutInflater mInflater;
    private static Context mContext;
    private static int mCreateOption=0;

    public HomeListAdapter(Context context,List<CurrentlyUsedList> openedLists) {
        super(context, R.layout.row_home_list, openedLists);
        mOpenLists = openedLists;
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // override the method that gets the view of one row
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        if(position < mOpenLists.size()){
            // first create the normal rows
            convertView = mInflater.inflate(R.layout.row_home_list, parent, false);
            // now get the fields needed to be changed
            ImageView imageView = (ImageView) convertView.findViewById(R.id.thumbnailView);
            TextView titleView = (TextView) convertView.findViewById(R.id.listTitle);
            TextView startedView = (TextView) convertView.findViewById(R.id.listStarted);
            TextView descriptionView = (TextView) convertView.findViewById(R.id.listDescription);
            // now update the fields
            if(mOpenLists.get(position).getThumbnail() != null){
                byte[] thumbnail = mOpenLists.get(position).getThumbnail();
                imageView.setImageBitmap(BitmapFactory.decodeByteArray(thumbnail, 0, thumbnail.length));
            }
            titleView.setText(mOpenLists.get(position).getName());

            // Get a simple date format
            SimpleDateFormat mDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
            // Get the current date
            Date startDate = AppModel.getInstance().getCreationDateForList(position);
            // Set the started date to start date
            startedView.setText(mContext.getString(R.string.started_on) +" "+mDateFormat.format(startDate));
            descriptionView.setText(mOpenLists.get(position).getDescription());

        } else {
            // now create the row with the create list buttons
            convertView = mInflater.inflate(R.layout.row_home_list_create, parent, false);

            // get the 2 Relative layouts that i use as buttons
            final RelativeLayout createListLayout = (RelativeLayout)convertView.findViewById(R.id.createFromList);
            final RelativeLayout createFromTemplateLayout = (RelativeLayout)convertView.findViewById(R.id.createFromTemplate);

            // set event listeners
            //I assume this will fire before the row click event
            //It does
            createListLayout.setOnTouchListener(new RelativeLayout.OnTouchListener() {

                // create mechanism for highlighting .. non provided for items in the rows,
                // just for hole row
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    createListLayout.setBackgroundColor(mContext.getResources().getColor(R.color.aqua));
                    mCreateOption = 1;
                    // return false because i want the event to be captured again
                    return false;
                }

            });
            //I assume this will fire before the row click event
            //It does
            createFromTemplateLayout.setOnTouchListener(new RelativeLayout.OnTouchListener() {

                // create mechanism for highlighting .. non provided for items in the rows,
                // just for hole row
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    createFromTemplateLayout.setBackgroundColor(mContext.getResources().getColor(R.color.aqua));
                    mCreateOption = 2;
                    // return false because i want the event to be captured again
                    return false;
                }
            });
        }

        return convertView;
    }

    /**
     * Override the getCount method so it always returns an extra row to put the create buttons
     * @return the size of the open list array plus 1
     */
    @Override
    public int getCount(){
        return mOpenLists.size() + 1;
    }

    /**
     * Return the selected option
     * @return selected option
     */
    public int getSelectedOption() {
        return mCreateOption;
    }
}
