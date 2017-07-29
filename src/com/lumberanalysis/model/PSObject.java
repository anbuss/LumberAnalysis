/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lumberanalysis.model;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.*;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 *
 * @author samidoss
 */
@Entity
@Table(name = "LA_PS_DATA")
public class PSObject extends BaseObject implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "APP_ISSUE_ID", nullable = false, length = 15)
    private String providerIssueId;
    @Column(name = "APP_NAME", nullable = false, length = 30)
    private String providerName;
    
    @Column(name = "APP_SUBNAME", nullable = false, length = 30)
    private String providerSubName;
    
    @Column(name = "APP_CAT", nullable = false, length = 30)
    private String providerCategory;
    
    @Column(name = "APP_VER", nullable = true, length = 12)
    private String providerVersion;
    
    @Column(name = "MESS_TYP", nullable = false, length = 4)
    private String messageType;
    
    @Column(name = "SHORT_DESC", nullable = false, length = 100)
    private String shortDescription;
    
    @Column(name = "DESCRIPTION", nullable = true, length = 500)
    private String description;
    
    @Column(name = "CAUSE", nullable = false, length = 500)
    private String cause;
    @Column(name = "FIX", nullable = true, length = 2000)
    private String fix;
    @Column(name = "PERS_NAME", nullable = true, length = 25)
    private String contactName;
    @Column(name = "PERS_NO", nullable = true, length = 16)
    private String contactNumber;
    @Column(name = "PERS_MAIL", nullable = true, length = 25)
    private String contactEmail;
    @Column(name = "NOTES", nullable = true, length = 250)
    private String contactNotes;
    @Column(name = "ISS_FOND_TS", nullable = true)
    private Date problemDate;
    @Column(name = "ISS_FX_TS", nullable = true)
    private Date problemFixDate;
    @Column(name = "TAGS", nullable = true, length = 101)
    private String tags;

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactNotes() {
        return contactNotes;
    }

    public void setContactNotes(String contactNotes) {
        this.contactNotes = contactNotes;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    } 

    public String getFix() {
        return fix;
    }

    public void setFix(String fix) {
        this.fix = fix;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public Date getProblemDate() {
        return problemDate;
    }

    public void setProblemDate(Date problemDate) {
        this.problemDate = problemDate;
    }

    public Date getProblemFixDate() {
        return problemFixDate;
    }

    public void setProblemFixDate(Date problemFixDate) {
        this.problemFixDate = problemFixDate;
    }

    public String getProviderIssueId() {
        return providerIssueId;
    }

    public void setProviderIssueId(String providerIssueId) {
        this.providerIssueId = providerIssueId;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getProviderVersion() {
        return providerVersion;
    }

    public void setProviderVersion(String providerVersion) {
        this.providerVersion = providerVersion;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProviderCategory() {
        return providerCategory;
    }

    public void setProviderCategory(String providerCategory) {
        this.providerCategory = providerCategory;
    }

    public String getProviderSubName() {
        return providerSubName;
    }

    public void setProviderSubName(String providerSubName) {
        this.providerSubName = providerSubName;
    }

    /**
     * {@inheritDoc}
     */
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PSObject)) {
            return false;
        }

        final PSObject ps = (PSObject) o;

        return !(this.id != null ? !this.id.equals(ps.id) : ps.id != null);

    }

    /**
     * {@inheritDoc}
     */
    public int hashCode() {
        return (this.id != null ? this.id.hashCode() : 0);
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(this.id).toString();
    }

    /**
     * {@inheritDoc}
     */
    public int compareTo(Object o) {
        return (equals(o) ? 0 : -1);
    }
}
