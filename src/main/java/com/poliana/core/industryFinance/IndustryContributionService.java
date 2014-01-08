package com.poliana.core.industryFinance;

import com.poliana.core.industryFinance.entities.*;
import com.poliana.core.time.CongressTimestamps;
import com.poliana.core.time.CongressYears;
import com.poliana.core.time.TimeService;
import com.poliana.core.industries.IndustryRepo;
import com.poliana.core.industries.Industry;
import com.poliana.core.legislators.Legislator;
import com.poliana.core.legislators.LegislatorService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author David Gilmore
 * @date 11/15/13
 */
@Service
public class IndustryContributionService {

    private IndustryRepo industryRepo;
    private LegislatorService legislatorService;
    private IndustryContributionRepo industryContributionRepo;
    private TimeService timeService;

    private static final Logger logger = Logger.getLogger(IndustryContributionService.class);


    /**
     * Get a map of BioguideId->Total sum of industry contributions to all legislators in a given chamber during the given
     * congressional cycles
     * @param chamber
     * @param congress
     * @return
     */
    public IndustryChamberTotals getIndustryTotalsByChamber(String id, String chamber, int congress) {

        //Check MongoDB for a cached industry chamber total document
        IndustryChamberTotals chamberTotals =
                industryContributionRepo.getIndustryChamberTotalsMongo(id, chamber, congress);

        //If it exists, return it
        if (chamberTotals != null)
            return chamberTotals;

        //If not we'll use Impala to get it
        if (id.length() == 3)
            chamberTotals = industryContributionRepo.getIndustryChamberTotals(id, chamber, congress);
        if (id.length() == 5)
            chamberTotals = industryContributionRepo.getIndustryChamberTotalsByCategory(id, chamber, congress);

        if (chamberTotals != null)
            industryContributionRepo.saveIndustryChamberTotals(chamberTotals);

        return chamberTotals;
    }

    /**
     * Get a map of BioguideId->Total sum of industry contributions to all legislators in a given chamber during the given
     * congressional cycles
     * @param chamber
     * @param congress
     * @return
     */
    public IndustryChamberTotals getCategoryTotalsByChamber(String categoryId, String chamber, int congress) {

        //Check MongoDB for a cached industry chamber total document
        IndustryChamberTotals chamberTotals =
                industryContributionRepo.getCategoryChamberTotalsMongo(categoryId, chamber, congress);

        //If it exists, return it
        if (chamberTotals != null)
            return chamberTotals;

        chamberTotals = industryContributionRepo.getIndustryCategoryChamberTotals(categoryId, chamber, congress);

        if (chamberTotals != null) {
            chamberTotals.setIndustryId(null);
            chamberTotals.setCategoryId(categoryId);
            industryContributionRepo.saveIndustryChamberTotals(chamberTotals);
        }

        return chamberTotals;
    }

    /**
     * Get a list of industry to politician contributions for a given congressional cycle
     * @param bioguideId
     * @param congress
     * @return
     */
    public List<IndToPolContrTotals> getIndustryTotals(String bioguideId, int congress) {

        List<IndToPolContrTotals> totalsList = industryContributionRepo.getIndustryToPoliticianContributions(bioguideId, congress);

        if (totalsList.size() > 0)
            return totalsList;

        //If MongoDB didn't return, fall back to Impala.
        totalsList = industryContributionRepo.getIndustryToPoliticianContributions(bioguideId, congress);

        //If Impala had something to return, save it to MongoDB
        if (totalsList.size() > 0)
            industryContributionRepo.saveIndustryToPoliticianContributions(totalsList);

        return totalsList;
    }

    /**
     * Get a HashMap of Cycle->Industry contribution lists for all congressional cycles a politician has been apart of
     * @param bioguideId
     * @return
     */
    public HashMap<Integer, List<IndToPolContrTotals>> getIndustryTotalsAllTime(String bioguideId) {

        //Query MongoDB for industry to politician objects
        Iterator<IndToPolContrTotals> totalsIterator = industryContributionRepo.getIndustryToPoliticianContributions(bioguideId);

        HashMap<Integer, List<IndToPolContrTotals>> totalsHashMap = new HashMap<>(30);

        //Add industry totals to the HashMap. Check the size, if it's zero, fall back to Impala.
        while (totalsIterator.hasNext()) {
            IndToPolContrTotals industryTotals = totalsIterator.next();

            //If the hashmap already has a list of industry totals for the object's cycle
            if (totalsHashMap.containsKey(industryTotals.getCycle()))
                totalsHashMap.get(industryTotals.getCycle()).add(industryTotals);
                //If the hashmap doesn't contain a list of industry totals, make it
            else {
                List<IndToPolContrTotals> totalsList = new LinkedList<>();
                totalsList.add(industryTotals);
                totalsHashMap.put(industryTotals.getCycle(), totalsList);
            }
        }

        //A size greater than 0 means that MongoDB had the sums cached
        if (totalsHashMap.size() > 0)
            return totalsHashMap;

        //Fall back to Impala if MongoDB did not have the sums cached
        totalsHashMap = industryContributionRepo.getAllIndustryContributionsPerCongress(bioguideId);

        //Cache sums to MongoDB

        //Get an iterator for the values in the hash map
        Iterator it = totalsHashMap.entrySet().iterator();
        Map.Entry pairs;

        //Iterate through all entry pairs in the map and update the TermTotalsMap with the values.
        while (it.hasNext()) {
            pairs = (Map.Entry) it.next();

            if (industryContributionRepo.countIndustryToPoliticianContributions(bioguideId, (Integer) pairs.getKey()) <= 0)
                industryContributionRepo.saveIndustryToPoliticianContributions((List<IndToPolContrTotals>)pairs.getValue());
        }

        return totalsHashMap;
    }

    /**
     *
     * @param bioguideId
     * @param congress
     * @return
     */
    public List<IndustryPoliticianContributions> legislatorReceivedIndustryTotals(String bioguideId, int congress) {

        CongressTimestamps yearTimestamps = timeService.congressTimestamps(congress);
        return legislatorReceivedIndustryTotals(bioguideId, yearTimestamps.getBegin(), yearTimestamps.getEnd(), 0);
    }

    /**
     *
     * @param bioguideId
     * @param beginTimestamp
     * @param endTimestamp
     * @param limit
     * @return
     */
    public List<IndustryPoliticianContributions> legislatorReceivedIndustryTotals(String bioguideId,
                                                                                  long beginTimestamp, long endTimestamp, int limit) {

        return industryContributionRepo.legislatorReceivedIndustryTotals(bioguideId, beginTimestamp, endTimestamp, limit);
    }

    /**
     * Get an IndTimeRangeTotals object
     * @param industryId
     * @param congress
     * @param numSeries
     * @return
     */
    public IndTimeRangeTotals getIndustryTimeRangeTotals(String industryId, int congress, int numSeries) {

        CongressYears years = timeService.congressToYears(congress);

        List<IndToPolContrTotals> contributionTotals =
                industryContributionRepo.industryContrTotals(industryId, years.getYearOne(), years.getYearTwo());


        IndTimeRangeTotals timeRangeTotals = new IndTimeRangeTotals();

        timeRangeTotals.setCongress(congress);
        Industry industry = industryRepo.industry(industryId);
        timeRangeTotals.setIndustry(industry.getId());

        HashMap<String,Recipient> recipients = new HashMap<>(500);
        HashMap<String,Recipient> stateAverages = new HashMap<>(60);

        for (IndToPolContrTotals contributions : contributionTotals) {
            updatePartyCounts(contributions, timeRangeTotals);
            updateStateCounts(contributions, stateAverages, numSeries);
            updateRecipientMap(contributions, recipients, numSeries);
        }


        timeRangeTotals.setStates(stateAverages);
        timeRangeTotals.setTopRecipients(getTopRecipients(recipients, 5));
        timeRangeTotals.setBottomRecipients(getBottomRecipients(recipients, 5));

        return timeRangeTotals;
    }

    /**
     * Functional helper method that updates a map of Bioguide->Recipients from a list of industry contributions
     * @param contribution
     * @param recipients
     * @param numSeries
     */
    private void updateRecipientMap(IndToPolContrTotals contribution, HashMap<String, Recipient> recipients, int numSeries) {

        String bioguideId = contribution.getBioguideId();
        Legislator legislator = getLegislator(contribution);

        if (recipients.containsKey(bioguideId)) {
            Recipient recipient = recipients.get(bioguideId);
            recipient.setCount(recipient.getCount() + contribution.getContributionsCount());
            recipient.setSum(recipient.getSum() + contribution.getContributionSum());
            recipient.setSeriesAverage(recipient.getSum()/numSeries);
        }
        else {
            Recipient recipient = new Recipient();

            int contributionCount = contribution.getContributionsCount();
            int contributionSum = contribution.getContributionSum();

            recipient.setState(legislator.getTermState());
            recipient.setCount(contributionCount);
            recipient.setSum(contributionSum);
            recipient.setBioguideId(bioguideId);
            recipient.setSeriesAverage(contributionSum / numSeries);
            recipient.setParty(legislator.getParty());
            recipient.setFirstName(legislator.getFirstName());
            recipient.setLastName(legislator.getLastName());
            recipients.put(bioguideId, recipient);
        }
    }

    /**
     * Functional helper method that increments sums and counts according to party
     * @param contribution
     * @param totals
     */
    private void updatePartyCounts(IndToPolContrTotals contribution, IndTimeRangeTotals totals) {

        switch(contribution.getParty()) {
            case "Republican":
                totals.setRepublicanCount(totals.getRepublicanCount() + contribution.getContributionsCount());
                totals.setRepublicanSum(totals.getRepublicanSum() + contribution.getContributionSum());
                break;
            case "Democrat":
                totals.setDemocratCount(totals.getDemocratCount() + contribution.getContributionsCount());
                totals.setDemocratSum(totals.getDemocratSum() + contribution.getContributionSum());
                break;
            case "Independent":
                totals.setIndependentCount(totals.getIndependentCount() + contribution.getContributionsCount());
                totals.setIndependentSum(totals.getIndependentSum() + contribution.getContributionSum());
                break;
        }
    }

    /**
     * Functional helper method that increments sums and counts according to legislator state
     * @param contribution
     * @param stateAverages
     * @param numSeries
     */
    private void updateStateCounts(IndToPolContrTotals contribution, HashMap<String, Recipient> stateAverages, int numSeries) {

        Legislator legislator = getLegislator(contribution);
        String state = legislator.getTermState();

        if (stateAverages.containsKey(state)) {
            Recipient recipient = stateAverages.get(state);
            recipient.setCount(recipient.getCount() + contribution.getContributionsCount());
            recipient.setSum(recipient.getSum() + contribution.getContributionSum());
            recipient.setSeriesAverage(recipient.getSum()/numSeries);
        }
        else {
            Recipient recipient = new Recipient();
            recipient.setState(legislator.getTermState());

            int contributionsCount = contribution.getContributionsCount();
            int contributionsSum = contribution.getContributionSum();

            recipient.setCount(contributionsCount);
            recipient.setSum(contributionsSum);
            recipient.setSeriesAverage(contributionsSum/numSeries);
            stateAverages.put(legislator.getTermState(), recipient);
        }
    }

    /**
     * Get a legislator object from a contribution object.
     * @param contribution
     * @return
     */
    private Legislator getLegislator(IndToPolContrTotals contribution) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(contribution.getYear(),contribution.getMonth(),2);

        long timestamp = calendar.getTimeInMillis()/1000;

        return legislatorService.getLegislatorByIdTimestamp(contribution.getBioguideId(), (int) timestamp);
    }

    /**
     * Get a list of the top recipients given a map of recipient objects.
     * @param recipients
     * @param limit
     * @return
     */
    private List<Recipient> getTopRecipients(HashMap<String, Recipient> recipients, int limit) {
        List<Recipient> recipientList = new ArrayList<>(recipients.values());

        Collections.sort(recipientList, new Comparator<Recipient>() {

            public int compare(Recipient r1, Recipient r2) {
                return r2.getSum() - r1.getSum();
            }
        });

        if (recipientList.size() > limit)
            return recipientList.subList(0,limit);
        else
            return recipientList;
    }

    /**
     * Get a list of the least recipients given a map of recipient objects.
     * @param recipients
     * @param limit
     * @return
     */
    private List<Recipient> getBottomRecipients(HashMap<String, Recipient> recipients, int limit) {

        List<Recipient> recipientList = new ArrayList<>(recipients.values());

        Collections.sort(recipientList, new Comparator<Recipient>() {

            public int compare(Recipient r1, Recipient r2) {
                return r1.getSum() - r2.getSum();
            }
        });

        int recipientsCount = recipientList.size();
        int index = 0;
        int firstPositive = 0;

        Iterator<Recipient> recipientIterator = recipientList.iterator();

        while (recipientIterator.hasNext() && firstPositive < recipientsCount - limit) {
            Recipient recipient = recipientIterator.next();
            if (recipient.getSum() > 0) {
                firstPositive = index;
                break;
            }
            index++;
        }

        if (recipientsCount > limit + firstPositive)
            return recipientList.subList(firstPositive,limit+firstPositive);
        else if (recipientsCount <= limit)
            return recipientList;
        else {
            int offset = (firstPositive - recipientsCount) + limit;
            return recipientList.subList(firstPositive-offset,firstPositive-offset);
        }
    }

    @Autowired
    public void setIndustryRepo(IndustryRepo industryRepo) {
        this.industryRepo = industryRepo;
    }

    @Autowired
    public void setLegislatorService(LegislatorService legislatorService) {
        this.legislatorService = legislatorService;
    }

    @Autowired
    public void setIndustryContributionRepo(IndustryContributionRepo industryContributionRepo) {
        this.industryContributionRepo = industryContributionRepo;
    }

    @Autowired
    public void setTimeService(TimeService timeService) {
        this.timeService = timeService;
    }
}