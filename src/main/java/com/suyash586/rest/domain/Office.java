package com.suyash586.rest.domain;

import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Office {

    @Id
    @NotNull
    @Size(max = 64)
    @Column(name = "id", nullable = false, updatable = false)
    private String id;

    @NotNull
    @Size(max = 64)
    @Column(name = "location", nullable = false)
    private String location;

    
    // Let's say ranging from 1400 to -1200 representing UTC+14:00 to UTC -12:00
    @NotNull
    @Column(name="timeDifference")
    private Integer timeDifference;
    
    @NotNull
    @Column(name="startTime")
    private Time startTime;
    
    @NotNull
    @Column(name="endTime")
    private Time endTime;
    

    Office() {
    }

    public Office(String id, String location, Integer timeDifference, Time startTime, Time endTime) {
        super();
        this.id = id;
        this.location = location;
        this.timeDifference = timeDifference;
        this.startTime = startTime;
        this.endTime = endTime;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getTimeDifference() {
        return timeDifference;
    }

    public void setTimeDifference(Integer timeDifference) {
        this.timeDifference = timeDifference;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

}
