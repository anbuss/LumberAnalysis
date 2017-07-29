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
/**
 * CREATE  TABLE IF NOT EXISTS `loginapp`.`LA_LastBackground` (
  `id` INT NOT NULL ,
  `APP_NAME` DATETIME NULL ,
  `END_TS` DATETIME NULL ,
  `START_LINE` VARCHAR(256) NULL ,
  `END_LINE` VARCHAR(256) NULL ,
  `STATUS` VARCHAR(1) NULL ,
  PRIMARY KEY (`id`) );

 * @author samidoss
 */
@Entity
@Table(name = "LA_LastBackground")
public class LastBackground extends BaseObject implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "APP_NAME", nullable = false, length = 25)
    private String appName;
    @Column(name = "START_TS", nullable = true)
    private java.sql.Date lastExecutedStartTime;
    @Column(name = "END_TS", nullable = true)
    private java.sql.Date lastExecutedCompleatedTime;
    @Column(name = "START_LINE", nullable = true, length = 256)
    private String startedLine;
    @Column(name = "END_LINE", nullable = true, length = 256)
    private String compleateLine;
    @Column(name = "STATUS", nullable = true, length = 256)
    private Character statusInternal;
    
    public enum Status{
        Iinitialize('I'), Started('S'), Compleated('C'); 
        Character code;
        Status(char code){
            this.code = code;
        }
    }
    
    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getCompleateLine() {
        return compleateLine;
    }

    public void setCompleateLine(String compleateLine) {
        this.compleateLine = compleateLine;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getLastExecutedCompleatedTime() {
        return lastExecutedCompleatedTime;
    }

    public void setLastExecutedCompleatedTime(Date lastExecutedCompleatedTime) {
        this.lastExecutedCompleatedTime = lastExecutedCompleatedTime;
    }

    public Date getLastExecutedStartTime() {
        return lastExecutedStartTime;
    }

    public void setLastExecutedStartTime(Date lastExecutedStartTime) {
        this.lastExecutedStartTime = lastExecutedStartTime;
    }

    public String getStartedLine() {
        return startedLine;
    }

    public void setStartedLine(String startedLine) {
        this.startedLine = startedLine;
    }
/*
    public Status getStatus() {
        Status.valueOf(appName)
        return status.;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
*/
    public Character getStatusInternal() {
        return statusInternal;
    }

    public void setStatusInternal(Character statusInternal) {
        this.statusInternal = statusInternal;
    }
    
    
    
    /**
     * {@inheritDoc}
     */
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LastBackground)) {
            return false;
        }

        final LastBackground ps = (LastBackground) o;

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
