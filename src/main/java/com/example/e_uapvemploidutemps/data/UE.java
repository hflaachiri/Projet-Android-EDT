package com.example.e_uapvemploidutemps.data;

import java.util.Date;

public class UE {

    private String LOCATION = "DEFAULT";
    private String UID = "DEFAULT";
    private String SUMMARRY = "DEFAULT";
    private String DESCRIPTION = "DEFAULT";
    private String CATEGORIES = "DEFAULT";
    private Date DTSTAMP;
    private Date DTSTART;
    private Date LAST_MODIFIED;
    private Date DTEND;

    public UE(String LOCATION, String UID, Date DTSTAMP, String SUMMARRY, Date DTSTART, String DESCRIPTION, String CATEGORIES, Date LAST_MODIFIED, Date DTEND) {
        this.LOCATION = LOCATION;
        this.UID = UID;
        this.DTSTAMP = DTSTAMP;
        this.SUMMARRY = SUMMARRY;
        this.DTSTART = DTSTART;
        this.DESCRIPTION = DESCRIPTION;
        this.CATEGORIES = CATEGORIES;
        this.LAST_MODIFIED = LAST_MODIFIED;
        this.DTEND = DTEND;
    }

    public String getLOCATION() {
        return LOCATION;
    }

    public void setLOCATION(String LOCATION) {
        this.LOCATION = LOCATION;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getSUMMARRY() {
        return SUMMARRY;
    }

    public void setSUMMARRY(String SUMMARRY) {
        this.SUMMARRY = SUMMARRY;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getCATEGORIES() {
        return CATEGORIES;
    }

    public void setCATEGORIES(String CATEGORIES) {
        this.CATEGORIES = CATEGORIES;
    }

    public Date getDTSTAMP() {
        return DTSTAMP;
    }

    public void setDTSTAMP(Date DTSTAMP) {
        this.DTSTAMP = DTSTAMP;
    }

    public Date getDTSTART() {
        return DTSTART;
    }

    public void setDTSTART(Date DTSTART) {
        this.DTSTART = DTSTART;
    }

    public Date getLAST_MODIFIED() {
        return LAST_MODIFIED;
    }

    public void setLAST_MODIFIED(Date LAST_MODIFIED) {
        this.LAST_MODIFIED = LAST_MODIFIED;
    }

    public Date getDTEND() {
        return DTEND;
    }

    public void setDTEND(Date DTEND) {
        this.DTEND = DTEND;
    }



}
