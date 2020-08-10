package com.mobiroller.models.youtube;

import com.google.api.client.util.DateTime;
import java.io.Serializable;
import java.math.BigInteger;

public class ChannelDetailModel implements Serializable {
    public String channelDescription;
    public String channelId;
    public String channelImageUrl;
    public DateTime channelJoinDate;
    public String channelName;
    public String channelSubscriberCount;
    public BigInteger channelTotalViewCount;
}
