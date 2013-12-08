package com.listtemplate.model.data.type;

import android.test.AndroidTestCase;
import com.listtemplate.model.data.type.TemplateRecord;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Catalin BORA
 * Date: 12/3/13
 * Time: 7:50 PM
 */
public class TemplateRecordTest extends AndroidTestCase {

    public void testTemplateRecord(){

        //create a template from SQL
        TemplateRecord SQLTemplate = new TemplateRecord(1,"SQL Template","This template was from an QSL record",2,1
                                                        ,27,"Ion Ionesco","ion@ionescu.com",true,
                                                        new Date(987654321),null);
        //test if created successfully
        assertEquals("Template id is wrong",-1,SQLTemplate.getId());
        assertEquals("Template usage increment is wrong",1,SQLTemplate.getUsageIncrement());
        assertEquals("Template name is wrong","SQL Template",SQLTemplate.getName());
        assertEquals("Template description is wrong","This template was from an QSL record",SQLTemplate.getDescription());
        assertEquals("Template rating is wrong",2,SQLTemplate.getRating());
        assertEquals("Template used personal is wrong",1,SQLTemplate.getUsedPersonal());
        assertEquals("Template used all is wrong",27,SQLTemplate.getUsedAll());
        assertEquals("Template author is wrong","Ion Ionesco",SQLTemplate.getAuthor());
        assertEquals("Template author email is wrong","ion@ionescu.com",SQLTemplate.getAuthorEmail());
        assertTrue("Template send mail enabled is wrong", SQLTemplate.isSendEmailEnabled());
        assertEquals("Template shared date is wrong", new Date(987654321), SQLTemplate.getSharedDate());
        assertNull("Template background is wrong", SQLTemplate.getBackground());
        assertEquals("Template elements number is wrong",0,SQLTemplate.getNumberOfElements());
        assertNull("Template element 1 is wrong",SQLTemplate.getElement(1));
        assertEquals("Template tags number is wrong",0,SQLTemplate.getNumberOfTags());
        assertNull("Template tag 1 is wrong",SQLTemplate.getTag(3));

        //create a template locally
        TemplateRecord localTemplate = new TemplateRecord("Template Name","bla bla bla","Scuba Bob",
                                                            "bob@scula.com",false,null);
        //test if created successfully
        assertEquals("Template id is wrong",-1,localTemplate.getId());
        assertEquals("Template usage increment is wrong",0,localTemplate.getUsageIncrement());
        assertEquals("Template name is wrong","Template Name",localTemplate.getName());
        assertEquals("Template description is wrong","bla bla bla",localTemplate.getDescription());
        assertEquals("Template rating is wrong",0,localTemplate.getRating());
        assertEquals("Template used personal is wrong",0,localTemplate.getUsedPersonal());
        assertEquals("Template used all is wrong",0,localTemplate.getUsedAll());
        assertEquals("Template author is wrong","Scuba Bob",localTemplate.getAuthor());
        assertEquals("Template author email is wrong","bob@scula.com",localTemplate.getAuthorEmail());
        assertFalse("Template send mail enabled is wrong",localTemplate.isSendEmailEnabled());
        assertNull("Template shared date is wrong",localTemplate.getSharedDate());
        assertNull("Template background is wrong",localTemplate.getBackground());
        assertEquals("Template elements number is wrong",0,localTemplate.getNumberOfElements());
        assertNull("Template element 1 is wrong",localTemplate.getElement(1));
        assertEquals("Template tags number is wrong",0,localTemplate.getNumberOfTags());
        assertNull("Template tag 1 is wrong",localTemplate.getTag(3));

        //add elements
        localTemplate.addElement("Pants");
        localTemplate.addElement("Under pants");
        localTemplate.addElement("Umbrella");
        localTemplate.addElement("Pants"); // no duplicates in the list
        //test if added correctly
        assertEquals("Template elements number is wrong",3,localTemplate.getNumberOfElements());
        assertEquals("Template element 3 is wrong", "Umbrella", localTemplate.getElement(2));

        //remove elements
        localTemplate.removeElement("Umbrella");
        //test if removed correctly
        assertEquals("Template elements number is wrong",2,localTemplate.getNumberOfElements());
        assertEquals("Template element 2 is wrong","Under pants",localTemplate.getElement(1));
        //just out of curiosity
        localTemplate.removeElement("Tangerine");

        //add tags
        localTemplate.addTag("mountain");
        localTemplate.addTag("business");
        localTemplate.addTag("vacation");
        localTemplate.addTag("business"); // no duplicates in the list
        //test if added correctly
        assertEquals("Template tags number is wrong",3,localTemplate.getNumberOfTags());
        assertEquals("Template tag 1 is wrong", "mountain", localTemplate.getTag(0));

        //change rating
        localTemplate.changeRating(3);
        //test if changed correctly
        assertEquals("Template rating is wrong",3,localTemplate.getRating());

        //share template
        localTemplate.shareThisTemplate();
        //check if shared correctly
        //share date and id must be set
        localTemplate.setId(432);
        assertEquals("Template shared date is wrong", new Date(), localTemplate.getSharedDate());
        assertEquals("Template id is wrong",432,localTemplate.getId());

        //template used
        localTemplate.templateUsed();
        //test if used correctly
        assertEquals("Template usage increment is wrong",1,localTemplate.getUsageIncrement());
        assertEquals("Template used personal is wrong",1,localTemplate.getUsedPersonal());
        assertEquals("Template used all is wrong",1,localTemplate.getUsedAll());

    }
}
