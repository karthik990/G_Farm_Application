package com.startapp.android.publish.ads.video.tracking;

import com.startapp.android.publish.ads.video.p073c.p074a.C4880b;
import com.startapp.android.publish.ads.video.p073c.p074a.C4886e;
import com.startapp.android.publish.ads.video.p073c.p074a.p075a.C4877c;
import com.startapp.common.p042c.C2362f;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/* compiled from: StartAppSDK */
public class VideoTrackingDetails implements Serializable {
    private static final long serialVersionUID = 1;
    @C2362f(mo20786b = AbsoluteTrackingLink.class)
    private AbsoluteTrackingLink[] absoluteTrackingUrls;
    @C2362f(mo20786b = ActionTrackingLink.class)
    private ActionTrackingLink[] creativeViewUrls;
    @C2362f(mo20786b = FractionTrackingLink.class)
    private FractionTrackingLink[] fractionTrackingUrls;
    @C2362f(mo20786b = ActionTrackingLink.class)
    private ActionTrackingLink[] impressionUrls;
    @C2362f(mo20786b = ActionTrackingLink.class)
    private ActionTrackingLink[] inlineErrorTrackingUrls;
    @C2362f(mo20786b = ActionTrackingLink.class)
    private ActionTrackingLink[] soundMuteUrls;
    @C2362f(mo20786b = ActionTrackingLink.class)
    private ActionTrackingLink[] soundUnmuteUrls;
    @C2362f(mo20786b = ActionTrackingLink.class)
    private ActionTrackingLink[] videoClickTrackingUrls;
    @C2362f(mo20786b = ActionTrackingLink.class)
    private ActionTrackingLink[] videoClosedUrls;
    @C2362f(mo20786b = ActionTrackingLink.class)
    private ActionTrackingLink[] videoPausedUrls;
    @C2362f(mo20786b = ActionTrackingLink.class)
    private ActionTrackingLink[] videoPostRollClosedUrls;
    @C2362f(mo20786b = ActionTrackingLink.class)
    private ActionTrackingLink[] videoPostRollImpressionUrls;
    @C2362f(mo20786b = ActionTrackingLink.class)
    private ActionTrackingLink[] videoResumedUrls;
    @C2362f(mo20786b = ActionTrackingLink.class)
    private ActionTrackingLink[] videoRewardedUrls;
    @C2362f(mo20786b = ActionTrackingLink.class)
    private ActionTrackingLink[] videoSkippedUrls;

    public VideoTrackingDetails() {
    }

    public VideoTrackingDetails(C4886e eVar) {
        if (eVar != null) {
            HashMap a = eVar.mo61752a();
            ArrayList arrayList = new ArrayList();
            addFractionUrls((List) a.get(C4880b.start), 0, arrayList);
            addFractionUrls((List) a.get(C4880b.firstQuartile), 25, arrayList);
            addFractionUrls((List) a.get(C4880b.midpoint), 50, arrayList);
            addFractionUrls((List) a.get(C4880b.thirdQuartile), 75, arrayList);
            addFractionUrls((List) a.get(C4880b.complete), 100, arrayList);
            this.fractionTrackingUrls = (FractionTrackingLink[]) arrayList.toArray(new FractionTrackingLink[arrayList.size()]);
            this.impressionUrls = urlToTrackingList(eVar.mo61756d());
            this.soundMuteUrls = trackingToTrackingList((List) a.get(C4880b.mute));
            this.soundUnmuteUrls = trackingToTrackingList((List) a.get(C4880b.unmute));
            this.videoPausedUrls = trackingToTrackingList((List) a.get(C4880b.pause));
            this.videoResumedUrls = trackingToTrackingList((List) a.get(C4880b.resume));
            this.videoSkippedUrls = trackingToTrackingList((List) a.get(C4880b.skip));
            this.videoPausedUrls = trackingToTrackingList((List) a.get(C4880b.pause));
            this.videoClosedUrls = trackingToTrackingList((List) a.get(C4880b.close));
            this.inlineErrorTrackingUrls = urlToTrackingList(eVar.mo61757e());
            this.videoClickTrackingUrls = urlToTrackingList(eVar.mo61755c().mo61741b());
            this.absoluteTrackingUrls = toAbsoluteTrackingList((List) a.get(C4880b.progress));
        }
    }

    public FractionTrackingLink[] getFractionTrackingUrls() {
        return this.fractionTrackingUrls;
    }

    public AbsoluteTrackingLink[] getAbsoluteTrackingUrls() {
        return this.absoluteTrackingUrls;
    }

    public ActionTrackingLink[] getImpressionUrls() {
        return this.impressionUrls;
    }

    public ActionTrackingLink[] getSoundUnmuteUrls() {
        return this.soundUnmuteUrls;
    }

    public ActionTrackingLink[] getCreativeViewUrls() {
        return this.creativeViewUrls;
    }

    public ActionTrackingLink[] getSoundMuteUrls() {
        return this.soundMuteUrls;
    }

    public ActionTrackingLink[] getVideoPausedUrls() {
        return this.videoPausedUrls;
    }

    public ActionTrackingLink[] getVideoResumedUrls() {
        return this.videoResumedUrls;
    }

    public ActionTrackingLink[] getVideoSkippedUrls() {
        return this.videoSkippedUrls;
    }

    public ActionTrackingLink[] getVideoClosedUrls() {
        return this.videoClosedUrls;
    }

    public ActionTrackingLink[] getVideoPostRollImpressionUrls() {
        return this.videoPostRollImpressionUrls;
    }

    public ActionTrackingLink[] getVideoPostRollClosedUrls() {
        return this.videoPostRollClosedUrls;
    }

    public ActionTrackingLink[] getVideoRewardedUrls() {
        return this.videoRewardedUrls;
    }

    public ActionTrackingLink[] getVideoClickTrackingUrls() {
        return this.videoClickTrackingUrls;
    }

    public ActionTrackingLink[] getInlineErrorTrackingUrls() {
        return this.inlineErrorTrackingUrls;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("VideoTrackingDetails [fractionTrackingUrls=");
        sb.append(printTrackingLinks(this.fractionTrackingUrls));
        sb.append(", absoluteTrackingUrls=");
        sb.append(printTrackingLinks(this.absoluteTrackingUrls));
        sb.append(", impressionUrls=");
        sb.append(printTrackingLinks(this.impressionUrls));
        sb.append(", creativeViewUrls=");
        sb.append(printTrackingLinks(this.creativeViewUrls));
        sb.append(", soundMuteUrls=");
        sb.append(printTrackingLinks(this.soundMuteUrls));
        sb.append(", soundUnmuteUrls=");
        sb.append(printTrackingLinks(this.soundUnmuteUrls));
        sb.append(", videoPausedUrls=");
        sb.append(printTrackingLinks(this.videoPausedUrls));
        sb.append(", videoResumedUrls=");
        sb.append(printTrackingLinks(this.videoResumedUrls));
        sb.append(", videoSkippedUrls=");
        sb.append(printTrackingLinks(this.videoSkippedUrls));
        sb.append(", videoClosedUrls=");
        sb.append(printTrackingLinks(this.videoClosedUrls));
        sb.append(", videoPostRollImpressionUrls=");
        sb.append(printTrackingLinks(this.videoPostRollImpressionUrls));
        sb.append(", videoPostRollClosedUrls=");
        sb.append(printTrackingLinks(this.videoPostRollClosedUrls));
        sb.append(", videoRewardedUrls=");
        sb.append(printTrackingLinks(this.videoRewardedUrls));
        sb.append(", videoClickTrackingUrls=");
        sb.append(printTrackingLinks(this.videoClickTrackingUrls));
        sb.append(", inlineErrorTrackingUrls=");
        sb.append(printTrackingLinks(this.inlineErrorTrackingUrls));
        sb.append("]");
        return sb.toString();
    }

    private String printTrackingLinks(VideoTrackingLink[] videoTrackingLinkArr) {
        return videoTrackingLinkArr != null ? Arrays.toString(videoTrackingLinkArr) : "";
    }

    private static void addFractionUrls(List<C4877c> list, int i, List<FractionTrackingLink> list2) {
        if (list != null) {
            for (C4877c cVar : list) {
                FractionTrackingLink fractionTrackingLink = new FractionTrackingLink();
                fractionTrackingLink.setTrackingUrl(cVar.mo61734a());
                fractionTrackingLink.setFraction(i);
                fractionTrackingLink.setAppendReplayParameter(true);
                fractionTrackingLink.setReplayParameter("");
                list2.add(fractionTrackingLink);
            }
        }
    }

    private static ActionTrackingLink[] trackingToTrackingList(List<C4877c> list) {
        if (list == null) {
            return new ActionTrackingLink[0];
        }
        ArrayList arrayList = new ArrayList(list.size());
        for (C4877c cVar : list) {
            ActionTrackingLink actionTrackingLink = new ActionTrackingLink();
            actionTrackingLink.setTrackingUrl(cVar.mo61734a());
            arrayList.add(actionTrackingLink);
        }
        return (ActionTrackingLink[]) arrayList.toArray(new ActionTrackingLink[arrayList.size()]);
    }

    private static ActionTrackingLink[] urlToTrackingList(List<String> list) {
        if (list == null) {
            return new ActionTrackingLink[0];
        }
        ArrayList arrayList = new ArrayList(list.size());
        for (String str : list) {
            ActionTrackingLink actionTrackingLink = new ActionTrackingLink();
            actionTrackingLink.setTrackingUrl(str);
            arrayList.add(actionTrackingLink);
        }
        return (ActionTrackingLink[]) arrayList.toArray(new ActionTrackingLink[arrayList.size()]);
    }

    private AbsoluteTrackingLink[] toAbsoluteTrackingList(List<C4877c> list) {
        if (list == null) {
            return new AbsoluteTrackingLink[0];
        }
        ArrayList arrayList = new ArrayList(list.size());
        for (C4877c cVar : list) {
            AbsoluteTrackingLink absoluteTrackingLink = new AbsoluteTrackingLink();
            absoluteTrackingLink.setTrackingUrl(cVar.mo61734a());
            if (cVar.mo61735b() != -1) {
                absoluteTrackingLink.setVideoOffsetMillis(cVar.mo61735b());
            }
            arrayList.add(absoluteTrackingLink);
        }
        return (AbsoluteTrackingLink[]) arrayList.toArray(new AbsoluteTrackingLink[arrayList.size()]);
    }
}
