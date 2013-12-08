package com.listtemplate.model;

import android.test.AndroidTestCase;
import com.listtemplate.model.exceptions.AlreadyPresentException;

/**
 * Created with IntelliJ IDEA.
 * User: Catalin BORA
 * Date: 12/4/13
 * Time: 10:46 PM
 */
public class AppModelTest extends AndroidTestCase {

    public void testSingleton(){
        //get the instance
        AppModel singleton = AppModel.getInstance();
        //see it's not null
        assertNotNull("Singleton is null",singleton);
        //see it's the same instance
        assertEquals("Singleton error", singleton, AppModel.getInstance());
        //clear all
        AppModel.getInstance().clearAll();
    }

    public void testCreateSimpleList(){
        // create a list with name,description
        AppModel.getInstance().createList("Test List","This is a test list.",null);
        // see it's empty
        assertEquals("List name is wrong","Test List",AppModel.getInstance().getNameOfTheCurrentList());
        assertEquals("List name is wrong","This is a test list.",AppModel.getInstance().getDescriptionOfTheCurrentList());
        assertEquals("List size is wrong",0,AppModel.getInstance().getNumberOfElementsOfTheCurrentList());
        // add elements to it
        AppModel.getInstance().addElementToTheCurrentList("Pants");
        AppModel.getInstance().addElementToTheCurrentList("Socks");
        AppModel.getInstance().addElementToTheCurrentList("Shirt");
        AppModel.getInstance().addElementToTheCurrentList("Sun screen");
        AppModel.getInstance().addElementToTheCurrentList("Backpack");
        AppModel.getInstance().addElementToTheCurrentList("Pants");
        // see it's correct
        assertEquals("List size is wrong", 5, AppModel.getInstance().getNumberOfElementsOfTheCurrentList());
        assertEquals("Element 3 is wrong","Shirt",AppModel.getInstance().getElementOfTheCurrentList(2));
        assertEquals("Selected state of element \"Shirt\" is wrong",false,AppModel.getInstance().isElementSelected("Shirt"));
        // remove elements
        AppModel.getInstance().removeElementFromTheCurrentList("Socks");
        // see it's correct
        assertEquals("List size is wrong",4,AppModel.getInstance().getNumberOfElementsOfTheCurrentList());
        assertEquals("Element 3 is wrong","Shirt",AppModel.getInstance().getElementOfTheCurrentList(1));
        assertEquals("Selected state of element \"Shirt\" is wrong",false,AppModel.getInstance().isElementSelected("Shirt"));
        // use it / save it to database
        AppModel.getInstance().useList();
        // the second call should have no effect
        AppModel.getInstance().useList();
        // clear all singleton fields
        AppModel.getInstance().clearAll();
        assertEquals("Number of lists is wrong",0, AppModel.getInstance().getNumberOfLists());
        // load database
        AppModel.getInstance().loadLists();
        // see list is loaded correctly
        assertEquals("Number of lists is wrong",1, AppModel.getInstance().getNumberOfLists());
        // open the list first
        AppModel.getInstance().openList(0);
        // erase list and remove from database if present
        AppModel.getInstance().discardList();
        // see if removed correctly
        assertEquals("Number of lists is wrong",0, AppModel.getInstance().getNumberOfLists());
        // delete all
        AppModel.getInstance().deleteAll();
    }
}
