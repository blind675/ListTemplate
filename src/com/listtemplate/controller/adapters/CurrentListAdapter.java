package com.listtemplate.controller.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import com.listtemplate.R;
import com.listtemplate.model.AppModel;

/**
 * Created by blind675 on 1/31/14.
 *
 */
public class CurrentListAdapter extends ArrayAdapter<String> {

    private static LayoutInflater mInflater;
    private static Context mContext;

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
        // Get the checkbox
        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);
        // Set the checkbox
        checkBox.setText(elementName);
        checkBox.setChecked(AppModel.getInstance().isElementSelected(elementName));

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkBox = (CheckBox) view;
                AppModel.getInstance().toggleSelectedForElement((String) checkBox.getText());
            }
        });

        return convertView;
    }

    // Need to overwrite this because i don't send any array so he doesn't actually know the list size
    @Override
    public int getCount() {
        return AppModel.getInstance().getNumberOfElementsOfTheCurrentList();
    }
}
