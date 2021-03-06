package com.poliana.core.politicianFinance.financeProfile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.poliana.core.pacFinance.entities.PacPoliticianContributionTotals;
import com.poliana.core.politicianFinance.industries.PoliticianIndustryContributionsTotals;
import org.mongodb.morphia.annotations.*;


import java.util.List;

/**
 * @author David Gilmore
 * @date 12/11/13
 */
@Entity("session_totals")
@JsonIgnoreProperties({"id"})
public class SessionTotals {

    @Id
    private String id;

    @Property("bioguide_id")
    private String bioguideId;

    @Indexed
    private int congress;
    private String chamber;

    @Property("term_start")
    private int termStart;
    private String religion;

    @Property("ideology_score")
    private double ideologyScore;

    @Property("leadership_score")
    private double leadershipScore;

    @Embedded("top_industry_contributions")
    private List<PoliticianIndustryContributionsTotals> topIndustryContributions;

    @Embedded("top_pac_contributions")
    private List<PacPoliticianContributionTotals> topPACContributions;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBioguideId(String bioguideId) {
        this.bioguideId = bioguideId;
    }

    public int getCongress() {
        return congress;
    }

    public void setCongress(int congress) {
        this.congress = congress;
    }

    public String getChamber() {
        return chamber;
    }

    public void setChamber(String chamber) {
        this.chamber = chamber;
    }

    public int getTermStart() {
        return termStart;
    }

    public void setTermStart(int termStart) {
        this.termStart = termStart;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public double getIdeologyScore() {
        return ideologyScore;
    }

    public void setIdeologyScore(double ideologyScore) {
        this.ideologyScore = ideologyScore;
    }

    public double getLeadershipScore() {
        return leadershipScore;
    }

    public void setLeadershipScore(double leadershipScore) {
        this.leadershipScore = leadershipScore;
    }

    public List<PoliticianIndustryContributionsTotals> getTopIndustryContributions() {
        return topIndustryContributions;
    }

    public void setTopIndustryContributions(List<PoliticianIndustryContributionsTotals> topIndustryContributions) {
        this.topIndustryContributions = topIndustryContributions;
    }

    public List<PacPoliticianContributionTotals> getTopPACContributions() {
        return topPACContributions;
    }

    public void setTopPACContributions(List<PacPoliticianContributionTotals> topPACContributions) {
        this.topPACContributions = topPACContributions;
    }
}
