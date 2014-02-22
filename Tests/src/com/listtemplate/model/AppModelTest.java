package com.listtemplate.model;

import android.test.AndroidTestCase;
import com.listtemplate.model.data.type.TemplateRecord;

import java.util.Date;

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
        AppModel.getInstance().createList("Test List","This is a test list.", new Date(),null);
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
        AppModel.getInstance().saveList(getContext());
        // the second call should have no effect
        AppModel.getInstance().saveList(getContext());
        // clear all singleton fields
        AppModel.getInstance().clearAll();
        assertEquals("Number of lists is wrong",0, AppModel.getInstance().getNumberOfLists());
        // load database
        AppModel.getInstance().loadLists(getContext());
        // see list is loaded correctly
        assertEquals("Number of lists is wrong",1, AppModel.getInstance().getNumberOfLists());
        // open the list first
        AppModel.getInstance().openList(0);
        // see it's correct
        assertEquals("List size is wrong",4,AppModel.getInstance().getNumberOfElementsOfTheCurrentList());
        assertEquals("Element 3 is wrong","Shirt",AppModel.getInstance().getElementOfTheCurrentList(1));
        assertEquals("Selected state of element \"Shirt\" is wrong",false,AppModel.getInstance().isElementSelected("Shirt"));
        // erase list and remove from database if present
        AppModel.getInstance().discardList(getContext());
        // see if removed correctly
        assertEquals("Number of lists is wrong",0, AppModel.getInstance().getNumberOfLists());
        // delete all
        AppModel.getInstance().deleteAll();
    }

    public void testCreateShareTemplate(){
        // create new template
        TemplateRecord templateRecord = new TemplateRecord("Test Template","Bla bla Test","Ion","ion@smt.com",false,null);
        templateRecord.addElement("Pants");
        templateRecord.addElement("Shirt");
        templateRecord.addElement("Pants");
        templateRecord.addElement("Sun screen");
        // save template do DB
        AppModel.getInstance().saveTemplate(templateRecord);
        // test if ok
        assertEquals("Not added",1,AppModel.getInstance().getNumberOfTemplates());
        AppModel.getInstance().saveTemplate(templateRecord);
        // test if not added twice
        assertEquals("Added twice",1,AppModel.getInstance().getNumberOfTemplates());
        // clear singleton
        AppModel.getInstance().clearAll();
        // test if empty
        assertEquals("Not empty",0,AppModel.getInstance().getNumberOfTemplates());
        // load template from DB
        AppModel.getInstance().loadTemplates(getContext());
        // test if ok
        assertEquals("Not loaded",1,AppModel.getInstance().getNumberOfTemplates());
        // delete all
        AppModel.getInstance().deleteAll();
    }

    public void testCreateFromTemplate(){
        // create new template
        TemplateRecord templateRecord = new TemplateRecord("Test Template","Bla bla Test","Ion","ion@smt.com",false,null);
        templateRecord.addElement("Pants");
        templateRecord.addElement("Shirt");
        templateRecord.addElement("Pants");
        templateRecord.addElement("Sun screen");
        // save template
        AppModel.getInstance().saveTemplate(templateRecord);
        // test if added ok
        assertEquals("Not added",1,AppModel.getInstance().getNumberOfTemplates());
        // create new list from template
        AppModel.getInstance().createListFromTemplate(0);
        // test if ok
        assertEquals("List size is wrong", 3, AppModel.getInstance().getNumberOfElementsOfTheCurrentList());
        assertEquals("Element 1 is wrong","Pants",AppModel.getInstance().getElementOfTheCurrentList(0));
        assertEquals("Selected state of element \"Shirt\" is wrong",false,AppModel.getInstance().isElementSelected("Shirt"));
        // add element
        AppModel.getInstance().addElementToTheCurrentList("Socks");
        // test if added ok
        assertEquals("List size is wrong", 4, AppModel.getInstance().getNumberOfElementsOfTheCurrentList());
        assertEquals("Element 4 is wrong","Socks",AppModel.getInstance().getElementOfTheCurrentList(3));
        // remove element
        AppModel.getInstance().removeElementFromTheCurrentList("Shirt");
        // test if ok
        assertEquals("List size is wrong", 3, AppModel.getInstance().getNumberOfElementsOfTheCurrentList());
        assertEquals("Element 2 is wrong","Sun screen",AppModel.getInstance().getElementOfTheCurrentList(1));
        // delete all
        AppModel.getInstance().deleteAll();
    }

    public void testUseList(){
        // create a list with name,description
        AppModel.getInstance().createList("Test List","This is a test list.", new Date(),null);
        // add elements to it
        AppModel.getInstance().addElementToTheCurrentList("Pants");
        AppModel.getInstance().addElementToTheCurrentList("Socks");
        AppModel.getInstance().addElementToTheCurrentList("Shirt");
        // check the selected state of an element
        assertEquals("Selected state of element \"Shirt\" is wrong",false,AppModel.getInstance().isElementSelected("Shirt"));
        // change the state
        AppModel.getInstance().toggleSelectedForElement("Shirt");
        // check again
        assertEquals("Selected state of element \"Shirt\" is wrong",true,AppModel.getInstance().isElementSelected("Shirt"));
        // change the state
        AppModel.getInstance().toggleSelectedForElement("Shirt");
        // check again
        assertEquals("Selected state of element \"Shirt\" is wrong",false,AppModel.getInstance().isElementSelected("Shirt"));
        // delete all
        AppModel.getInstance().deleteAll();
    }
}
