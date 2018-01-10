package dashbase.request;

import dashbase.request.query.Query;
import dashbase.utils.GsonObject;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Request implements GsonObject {

    /**
     * number of matching rows to fetch
     * Default = 10
     */
    @Getter
    @Setter
    private int numResults = 10;

    /**
     * list of tables to query, if empty, all tables are queried
     * Default = []
     */
    @Getter
    @Setter
    private List<String> tableNames = new ArrayList<>();

    /**
     * specifies filters on the result set, if none, all results are a match
     * Default = none
     */
    @Getter
    @Setter
    private Query query;

    /**
     * if none specified, last 15 minutes of data is queried
     * Default = last 15 minutes
     */
    @Getter
    @Setter
    private TimeFilter timeFilter;


    /**
     * columns to fetch, if empty, all columns are retrieved for each row returned
     * Default = []
     */
    @Getter
    @Setter
    private List<String> fields = new ArrayList<>();

    /**
     * whether to use approximation for aggregations
     */
    @Getter
    @Setter
    private boolean useApproximation = false;

    /**
     * any string tracking the session, a unique string will be generated if not specified
     * Default = empty str
     */
    @Getter
    @Setter
    private String ctx = "";

    /**
     * amount of time in milliseconds allocated for the query, default of 0 means MAX_LONG number of milliseconds.
     * Default = 0
     */
    @Getter
    @Setter
    private Long timeoutMillis = 0L;

    /**
     * if set to true, result highlighting is turned off
     * Default = false
     */
    @Getter
    @Setter
    private boolean disableHighlight = false;

    /**
     * a token returned by a previous query indicating a position in the result stream to start query from
     * Default = empty
     */
    @Getter
    @Setter
    private String startId;

    /**
     * a token returned by a previous query indicating a position in the result stream to end query to.
     * Default = empty
     */
    @Getter
    @Setter
    private String endId;

    /**
     * aggregation request, if not set, no aggregation is performed
     * Default = []
     */
    @Getter
    @Setter
    private Aggregations aggregations = null;
}
