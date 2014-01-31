package com.listtemplate.controller.adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.listtemplate.R;

/**
 * Created with IntelliJ IDEA.
 * User: Catalin BORA
 * Date: 1/17/14
 * Time: 12:52 AM
 *
 * Adapter class for the drawer menu.
 */

public class MenuAdapter extends ArrayAdapter<String> {

    private final String[] mTitles;
    private final String[] mDescriptions;
    private final TypedArray mIconNames;
    private final LayoutInflater mInflated;

    // create a constructor for my menu adapter
    public MenuAdapter(Context context, String[] titles, String[] descriptions, TypedArray iconNames ) {
        super(context, R.layout.row_menu, titles);
        mTitles = titles;
        mDescriptions = descriptions;
        mIconNames = iconNames;
        mInflated = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // override the method that gets the view of one row
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        // use the covertView if it is provided
         if(convertView == null){
             // inflate the layout XML for row
             convertView = mInflated.inflate(R.layout.row_menu, parent, false);
         }

        // add the elements to the menu row
        TextView titleView = null;
        if (convertView != null) {
            titleView = (TextView) convertView.findViewById(R.id.menuTitle);
        }
        if (titleView != null) {
            titleView.setText(mTitles[position]);
        }

        // these resources are not dynamic so all this tests have no sens
        TextView descriptionView = null;
        if (convertView != null) {
            descriptionView = (TextView) convertView.findViewById(R.id.menuDescription);
        }
        if(mDescriptions[position] != null) {
            if (descriptionView != null) {
                descriptionView.setText(mDescriptions[position]);
            }
        }

        ImageView imageView = null;
        if (convertView != null) {
            imageView = (ImageView) convertView.findViewById(R.id.menuIcon);
        }
        if(mIconNames.getResourceId(position,-1) != -1){
            if (imageView != null) {
                imageView.setImageResource(mIconNames.getResourceId(position,-1));
            }
        }

        return convertView;
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public String getItem(int position) {
        return mTitles[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}