package com.poliana.core.bills.entities;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Property;

/**
 * @author David Gilmore
 * @date 11/22/13
 */
@Embedded
public class BillHistory {

    private boolean active;
    @Property("active_ts")
    private long activeTs;
    @Property("active_at")
    private String activeAt;
    @Property("awaiting_signature")
    private boolean awaitingSignature;
    @Property("awaiting_signature_since")
    private long awaitingSignatureSince;
    private boolean enacted;
    @Property("enacted_ts")
    private long enactedTs;
    @Property("enacted_at")
    private String enactedAt;
    @Property("house_passage_result")
    private String housePassageResult;
    @Property("house_passage_result_ts")
    private long housePassageResultTs;
    @Property("house_passage_result_at")
    private String housePassageResultAt;
    @Property("senate_passage_result")
    private String senatePassageResult;
    @Property("senate_passage_result_ts")
    private long senatePassageResultTs;
    @Property("senate_passage_result_at")
    private String senatePassageResultAt;
    @Property("house_override_result")
    private String houseOverrideResult;
    @Property("senate_override_result")
    private String senateOverrideResult;
    private boolean vetoed;
    @Property("vetoed_ts")
    private long vetoedTs;
    @Property("vetoed_at")
    private String vetoedAt;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public long getActiveTs() {
        return activeTs;
    }

    public void setActiveTs(long activeTs) {
        this.activeTs = activeTs;
    }

    public String getActiveAt() {
        return activeAt;
    }

    public void setActiveAt(String activeAt) {
        this.activeAt = activeAt;
    }

    public boolean isAwaitingSignature() {
        return awaitingSignature;
    }

    public void setAwaitingSignature(boolean awaitingSignature) {
        this.awaitingSignature = awaitingSignature;
    }

    public long getAwaitingSignatureSince() {
        return awaitingSignatureSince;
    }

    public void setAwaitingSignatureSince(long awaitingSignatureSince) {
        this.awaitingSignatureSince = awaitingSignatureSince;
    }

    public boolean isEnacted() {
        return enacted;
    }

    public void setEnacted(boolean enacted) {
        this.enacted = enacted;
    }

    public long getEnactedTs() {
        return enactedTs;
    }

    public void setEnactedTs(long enactedTs) {
        this.enactedTs = enactedTs;
    }

    public String getEnactedAt() {
        return enactedAt;
    }

    public void setEnactedAt(String enactedAt) {
        this.enactedAt = enactedAt;
    }

    public String getHousePassageResult() {
        return housePassageResult;
    }

    public void setHousePassageResult(String housePassageResult) {
        this.housePassageResult = housePassageResult;
    }

    public long getHousePassageResultTs() {
        return housePassageResultTs;
    }

    public void setHousePassageResultTs(long housePassageResultTs) {
        this.housePassageResultTs = housePassageResultTs;
    }

    public String getHousePassageResultAt() {
        return housePassageResultAt;
    }

    public void setHousePassageResultAt(String housePassageResultAt) {
        this.housePassageResultAt = housePassageResultAt;
    }

    public String getSenatePassageResult() {
        return senatePassageResult;
    }

    public void setSenatePassageResult(String senatePassageResult) {
        this.senatePassageResult = senatePassageResult;
    }

    public long getSenatePassageResultTs() {
        return senatePassageResultTs;
    }

    public void setSenatePassageResultTs(long senatePassageResultTs) {
        this.senatePassageResultTs = senatePassageResultTs;
    }

    public String getSenatePassageResultAt() {
        return senatePassageResultAt;
    }

    public void setSenatePassageResultAt(String senatePassageResultAt) {
        this.senatePassageResultAt = senatePassageResultAt;
    }

    public String getHouseOverrideResult() {
        return houseOverrideResult;
    }

    public void setHouseOverrideResult(String houseOverrideResult) {
        this.houseOverrideResult = houseOverrideResult;
    }

    public String getSenateOverrideResult() {
        return senateOverrideResult;
    }

    public void setSenateOverrideResult(String senateOverrideResult) {
        this.senateOverrideResult = senateOverrideResult;
    }

    public boolean isVetoed() {
        return vetoed;
    }

    public void setVetoed(boolean vetoed) {
        this.vetoed = vetoed;
    }

    public long getVetoedTs() {
        return vetoedTs;
    }

    public void setVetoedTs(long vetoedTs) {
        this.vetoedTs = vetoedTs;
    }

    public String getVetoedAt() {
        return vetoedAt;
    }

    public void setVetoedAt(String vetoedAt) {
        this.vetoedAt = vetoedAt;
    }
}