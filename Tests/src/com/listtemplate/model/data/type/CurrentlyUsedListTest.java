package com.listtemplate.model.data.type;

import android.test.AndroidTestCase;
import com.listtemplate.model.data.type.CurrentlyUsedList;

/**
 * Created with IntelliJ IDEA.
 * User: Catalin Bora
 * Date: 12/3/13
 * Time: 3:51 PM
 */
public class CurrentlyUsedListTest extends AndroidTestCase {

    public void testCurrentlyUsedList(){

        //create a new empty list
        CurrentlyUsedList list = new CurrentlyUsedList("Test List","This is a test list.",null,null);

        //test if list created successfully
        assertEquals("Name of list is wrong.","Test List", list.getName());
        assertEquals("Description of list is wrong.","This is a test list.", list.getDescription());
        assertNull("The is a background image.", list.getBackground());
        assertNull("There is a thumbnail image.",list.getThumbnail());
        assertEquals("The size of the element list s not 0",0, list.getNumberOfElements());
        assertNull("There is a first elements.",list.getElement(0));

        //add elements
        list.addElement("Pants",false);
        list.addElement("Shirt",false);
        list.addElement("Sun screen",true);

        //test if elements were added correctly
        assertEquals("The number of elements is wrong",3,list.getNumberOfElements());
        assertEquals("The element on position 2 is wrong","Shirt",list.getElement(1));
        assertEquals("The state of the element on position 3 is wrong",true,list.isItemSelected(list.getElement(2)));

        //remove an element
        list.removeElement("Pants");

        //test if removed correctly
        assertEquals("The number of elements is wrong",2,list.getNumberOfElements());
        assertEquals("The element on position 2 is wrong","Sun screen",list.getElement(1));

        //set the selected of an element
        boolean selected = true;
        list.setElementSelected("Shirt",selected);
        //test if selected set correctly
        assertEquals("Selected not set correctly",selected,list.isItemSelected("Shirt"));
        //change that elements selected
        list.toggleElementSelected("Shirt");
        //test if done correctly
        assertEquals("Selected not toggled correctly",!selected,list.isItemSelected("Shirt"));
    }

}
