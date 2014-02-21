package com.listtemplate.controller.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.listtemplate.R;
import com.listtemplate.model.AppModel;

import java.util.List;

/**
 *
 * Created by Catalin BORA on 2/21/14.
 */
public class CreateListAdapter extends ArrayAdapter<String> {

    private List<String> mElementsList;
    private static LayoutInflater mInflater;
    private static Context mContext;

    public CreateListAdapter(Context context, List<String> elements) {
        super(context, R.layout.row_create_list, elements);

        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mElementsList = elements;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        if(convertView == null){
            convertView = mInflater.inflate(R.layout.row_create_list, parent, false);
        }

        // Get the label
        TextView elementLabel = (TextView) convertView.findViewById(R.id.textView);
        // Set the label
        elementLabel.setText(mElementsList.get(position));

        // Get the imageView
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);

        //TODO: remove element

        return convertView;
    }

}
