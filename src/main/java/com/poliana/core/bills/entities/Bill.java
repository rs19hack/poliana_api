package com.poliana.core.bills.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

import java.util.List;

/**
 * @author David Gilmore
 * @date 11/22/13
 */
@Entity("bills_govtrack")
@JsonIgnoreProperties({"id"})
public class Bill {

    @Id
    private String id;

    @Embedded
    private List<Action> actions;

    @Embedded
    private List<BillAmendmentRef> amendments;

    @Property("bill_id")
    private String billId;

    @Property("bill_type")
    private String billType;
    private List<Committee> committees;
    private Integer congress;
    private Sponsor sponsor;
    private List<Sponsor> cosponsors;

    @Property("enacted_as")
    private String enactedAs;

    @Embedded
    private BillHistory history;

    @Property("introduced_at")
    private String introducedAt;

    private Integer number;

    @Property("official_title")
    private String officialTitle;

    @Property("popular_title")
    private String popularTitle;

    @Property("short_title")
    private String shortTitle;

    @Embedded("related_bills")
    private List<BillsRelated> billsRelated;

    private String status;

    @Property("status_at")
    private String statusAt;

    private List<String> subjects;

    @Property("subjects_top_term")
    private String subjectsTopTerm;

    @Embedded
    private BillSummary summary;

    @Embedded
    private List<BillTitle> titles;

    @Property("updated_at")
    private String updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public List<Committee> getCommittees() {
        return committees;
    }

    public void setCommittees(List<Committee> committees) {
        this.committees = committees;
    }

    public Integer getCongress() {
        return congress;
    }

    public void setCongress(Integer congress) {
        this.congress = congress;
    }

    public Sponsor getSponsor() {
        return sponsor;
    }

    public void setSponsor(Sponsor sponsor) {
        this.sponsor = sponsor;
    }

    public List<Sponsor> getCosponsors() {
        return cosponsors;
    }

    public void setCosponsors(List<Sponsor> cosponsors) {
        this.cosponsors = cosponsors;
    }

    public String getEnactedAs() {
        return enactedAs;
    }

    public void setEnactedAs(String enactedAs) {
        this.enactedAs = enactedAs;
    }

    public BillHistory getHistory() {
        return history;
    }

    public void setHistory(BillHistory history) {
        this.history = history;
    }

    public String getIntroducedAt() {
        return introducedAt;
    }

    public void setIntroducedAt(String introducedAt) {
        this.introducedAt = introducedAt;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
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

    public List<BillsRelated> getBillsRelated() {
        return billsRelated;
    }

    public void setBillsRelated(List<BillsRelated> billsRelated) {
        this.billsRelated = billsRelated;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusAt() {
        return statusAt;
    }

    public void setStatusAt(String statusAt) {
        this.statusAt = statusAt;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

    public String getSubjectsTopTerm() {
        return subjectsTopTerm;
    }

    public void setSubjectsTopTerm(String subjectsTopTerm) {
        this.subjectsTopTerm = subjectsTopTerm;
    }

    public BillSummary getSummary() {
        return summary;
    }

    public void setSummary(BillSummary summary) {
        this.summary = summary;
    }

    public List<BillTitle> getTitles() {
        return titles;
    }

    public void setTitles(List<BillTitle> titles) {
        this.titles = titles;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
