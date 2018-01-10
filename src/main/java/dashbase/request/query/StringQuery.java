package dashbase.request.query;


import lombok.Getter;
import lombok.Setter;

public class StringQuery extends Query {
    @Getter
    @Setter
    private String queryStr;
}
