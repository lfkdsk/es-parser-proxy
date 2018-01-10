package dashbase.request.query;

import dashbase.utils.GsonObject;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class Query implements GsonObject {

//    @Getter
//    @Setter
//    private String queryStr;

    @Setter
    @Getter
    private String queryType;

//    @Setter
//    @Getter
//    private String col;
//
//    @Getter
//    @Setter
//    private boolean maxInclusive;
//
//    @Setter
//    @Getter
//    private boolean minInclusive;
//
//    @Getter
//    @Setter
//    private String min;
//
//    @Getter
//    @Setter
//    private List<Query> subQueries;
}
