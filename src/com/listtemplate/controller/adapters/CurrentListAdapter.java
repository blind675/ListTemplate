package com.listtemplate.controller.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.listtemplate.R;
import com.listtemplate.model.AppModel;

/**
 * Created by blind675 on 1/31/14.
 *
 */
public class CurrentListAdapter extends ArrayAdapter<String> {

    private static LayoutInflater mInflater;

    public CurrentListAdapter(Context context, int resource) {
        super(context, resource);

        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // Override the method that gets the view of one row
    // This comment is self explanatory.
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        if(convertView == null){
            convertView = mInflater.inflate(R.layout.row_current_list, parent, false);
        }

        // Get the name of the element
        String elementName = AppModel.getInstance().getElementOfTheCurrentList(position);
        // Get the text view
        final TextView textView = (TextView) convertView.findViewById(R.id.checkBox);
        // Set the text view
        textView.setText(elementName);
        // Get the image view
        final ImageView imageView = (ImageView) convertView.findViewById(R.id.imageCheck);
        // Set the strike-through if needed and change picture
        if(AppModel.getInstance().isElementSelected(elementName)){
            // Set the strike-through
            textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            // Set the image
            imageView.setImageResource(R.drawable.ic_accept);
        }

        return convertView;
    }

    // Need to overwrite this because i don't send any array so he doesn't actually know the list size
    @Override
    public int getCount() {
        return AppModel.getInstance().getNumberOfElementsOfTheCurrentList();
    }
}
