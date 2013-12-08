package com.listtemplate.model.data.type;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Catalin BORA
 * Date: 12/3/13
 * Time: 7:48 PM
 */
public class TemplateRecord {
    //fields needed for the server sync
    private int mId = -1;
    private int mUsageIncrement = 0;

    //General fields of a template
    private final String mName;
    private final String mDescription;
    private int mRating;
    private int mUsedPersonal;
    private int mUsedAll;
    private final String mAuthor;
    private final String mAuthorEmail;
    private final boolean mSendEmail;
    private Date mSharedDate;
    private final ArrayList<String> mElements = new ArrayList<String>();
    private final ArrayList<String> mTags = new ArrayList<String>();
    private final Bitmap mBackground;

    /**
     * Constructor used to load data from the database or the server response
     */
    public TemplateRecord(int usageIncrement,String name, String description, int rating,int usedPersonal, int usedAll,
                          String author, String authorEmail, boolean sendEmail, Date sharedDate, Bitmap background) {

        mUsageIncrement = usageIncrement;
        mName = name;
        mDescription = description;
        mRating = rating;
        mUsedPersonal = usedPersonal;
        mUsedAll = usedAll;
        mAuthor = author;
        mAuthorEmail = authorEmail;
        mSendEmail = sendEmail;
        mSharedDate = sharedDate;
        mBackground = background;
    }

    /**
     * Constructor used to create a template locally
     */

    public TemplateRecord(String name, String description, String author, String authorEmail,
                          boolean sendEmail, Bitmap background) {
        this(0,name,description,0,0,0,author,authorEmail,sendEmail,null,background);
    }

    public int getId() {
        return mId;
    }

    public int getUsageIncrement() {
        return mUsageIncrement;
    }

    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public int getRating() {
        return mRating;
    }

    public int getUsedPersonal() {
        return mUsedPersonal;
    }

    public int getUsedAll() {
        return mUsedAll;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getAuthorEmail() {
        return mAuthorEmail;
    }

    public boolean isSendEmailEnabled() {
        return mSendEmail;
    }

    public Date getSharedDate() {
        return mSharedDate;
    }

    public Bitmap getBackground() {
        return mBackground;
    }

    public int getNumberOfElements(){
        return mElements.size();
    }

    public String getElement(int index){
        if (index >= 0 && index < mElements.size()){
            return mElements.get(index);
        } else {
            return null;
        }
    }

    public int getNumberOfTags(){
        return mTags.size();
    }

    public String getTag(int index){
        if (index >= 0 && index < mTags.size()){
            return mTags.get(index);
        } else {
            return null;
        }
    }

    public void setId(int newId) {
        mId = newId;
    }

    /**
     * Add an element in the elements list. If it already exists just ignore it.
     * @param name the name of the element
     */
    public void addElement(String name) {
        for (String mElement : mElements) {
            if (mElement.equalsIgnoreCase(name.trim())) {
                return;
            }
        }
        mElements.add(name);
    }

    public void removeElement(String name){
        mElements.remove(name);
    }

    /**
     * DUPLICATE CODE
     * Add a tag in the tag list. If it already exists just ignore it.
     * @param name the name of the tag
     */
    public void addTag(String name) {
        for (String mTag : mTags) {
            if (mTag.equalsIgnoreCase(name.trim())) {
                return;
            }
        }
        mTags.add(name);
    }

    public void changeRating(int rating) {
        if(rating > -1) {
            mRating = rating;
        }
    }

    public void shareThisTemplate() {
        mSharedDate = new Date();
    }

    public void templateUsed() {
        mUsageIncrement++;
        mUsedAll++;
        mUsedPersonal++;
    }

}
