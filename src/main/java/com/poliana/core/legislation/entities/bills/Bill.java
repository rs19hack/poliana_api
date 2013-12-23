package com.poliana.core.legislation.entities.bills;

import com.google.code.morphia.annotations.*;
import com.google.code.morphia.annotations.Reference;
import com.poliana.core.entities.entities.CongCommittee;

import java.util.List;

/**
 * @author David Gilmore
 * @date 11/16/13
 */
@Entity("bills")
public class Bill {

    @Id
    private String id;

    @Indexed @Property("bill_id")
    private String billId;
    @Property("vote_id")
    private String voteId;
    @Property("official_title")
    private String officialTitle;
    @Property("popular_title")
    private String popularTitle;
    @Property("short_title")
    private String shortTitle;
    @Embedded
    private List<BillTitle> titles;
    @Property("top_subject")
    private String topSubject;
    private List<String> subjects;
    @Embedded
    private BillSummary summary;
    @Reference
    private String sponsor;
    @Reference
    private List<String> cosponsors;
    @Property("introduced_ts")
    private int introducedTs;
    @Property("introduced_at")
    private String introducedAt;
    private String status;
    @Property("status_ts")
    private int statusTs;
    @Property("status_at")
    private String statusAt;
    private int congress;
    @Property("bill_type")
    private String billType;
    private int year;
    private int month;
    @Embedded
    private List<Action> actions;
    @Embedded
    private List<BillAmendmentRef> amendments;
    @Embedded
    private BillHistory history;
    @Reference
    private List<CongCommittee> committees;
    @Property("updated_at")
    private String updatedAt;
    @Property("updated_ts")
    private int updatedTs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getVoteId() {
        return voteId;
    }

    public void setVoteId(String voteId) {
        this.voteId = voteId;
    }

    public String getOfficialTitle() {
        return officialTitle;
    }

    public void setOfficialTitle(String officialTitle) {
        this.officialTitle = officialTitle;
    }

    public String getPopularTitle() {
        return popularTitle;
    }

    public void setPopularTitle(String popularTitle) {
        this.popularTitle = popularTitle;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public List<BillTitle> getTitles() {
        return titles;
    }

    public void setTitles(List<BillTitle> titles) {
        this.titles = titles;
    }

    public String getTopSubject() {
        return topSubject;
    }

    public void setTopSubject(String topSubject) {
        this.topSubject = topSubject;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

    public BillSummary getSummary() {
        return summary;
    }

    public void setSummary(BillSummary summary) {
        this.summary = summary;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public List<String> getCosponsors() {
        return cosponsors;
    }

    public void setCosponsors(List<String> cosponsors) {
        this.cosponsors = cosponsors;
    }

    public int getIntroducedTs() {
        return introducedTs;
    }

    public void setIntroducedTs(int introducedTs) {
        this.introducedTs = introducedTs;
    }

    public String getIntroducedAt() {
        return introducedAt;
    }

    public void setIntroducedAt(String introducedAt) {
        this.introducedAt = introducedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStatusTs() {
        return statusTs;
    }

    public void setStatusTs(int statusTs) {
        this.statusTs = statusTs;
    }

    public String getStatusAt() {
        return statusAt;
    }

    public void setStatusAt(String statusAt) {
        this.statusAt = statusAt;
    }

    public int getCongress() {
        return congress;
    }

    public void setCongress(int congress) {
        this.congress = congress;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public List<BillAmendmentRef> getAmendments() {
        return amendments;
    }

    public void setAmendments(List<BillAmendmentRef> amendments) {
        this.amendments = amendments;
    }

    public BillHistory getHistory() {
        return history;
    }

    public void setHistory(BillHistory history) {
        this.history = history;
    }

    public List<CongCommittee> getCommittees() {
        return committees;
    }

    public void setCommittees(List<CongCommittee> committees) {
        this.committees = committees;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getUpdatedTs() {
        return updatedTs;
    }

    public void setUpdatedTs(int updatedTs) {
        this.updatedTs = updatedTs;
    }
}