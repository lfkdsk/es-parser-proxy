package dashbase.request;

import dashbase.utils.GsonObject;
import lombok.Getter;
import lombok.Setter;

/**
 * TimeFilter
 * startTimeInSec	number	0	begin time range in time since epoch in seconds, if 0, start from beginning
 * endTimeInSec	number	0	end time range in time since epoch in seconds, if 0, ends time is now
 */
public class TimeFilter implements GsonObject {
    /**
     * begin time range in time since epoch in seconds, if 0, start from beginning
     */
    @Setter
    @Getter
    private long startTimeInSec = 0;

    /**
     * end time range in time since epoch in seconds, if 0, ends time is now
     */
    @Setter
    @Getter
    private long endTimeInSec = 0;
}
