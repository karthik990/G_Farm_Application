package com.rometools.rome.feed.module;

import com.anjlab.android.iab.p020v3.Constants;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.mobiroller.constants.YoutubeConstants.YoutubeRequestParams;
import com.rometools.rome.feed.CopyFrom;
import com.rometools.rome.feed.impl.CopyFromHelper;
import com.rometools.rome.feed.impl.ObjectBean;
import com.rometools.utils.Lists;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import p043io.fabric.sdk.android.services.settings.SettingsJsonConstants;

public class DCModuleImpl extends ModuleImpl implements DCModule {
    public static final Set<String> CONVENIENCE_PROPERTIES = Collections.unmodifiableSet(IGNORE_PROPERTIES);
    private static final CopyFromHelper COPY_FROM_HELPER;
    private static final Set<String> IGNORE_PROPERTIES = new HashSet();
    private static final long serialVersionUID = 1;
    private List<String> contributors;
    private List<String> coverage;
    private List<String> creator;
    private List<Date> date;
    private List<String> description;
    private List<String> format;
    private List<String> identifier;
    private List<String> language;
    private final ObjectBean objBean = new ObjectBean(DCModule.class, this, CONVENIENCE_PROPERTIES);
    private List<String> publisher;
    private List<String> relation;
    private List<String> rights;
    private List<String> source;
    private List<DCSubject> subject;
    private List<String> title;
    private List<String> type;

    static {
        IGNORE_PROPERTIES.add("title");
        IGNORE_PROPERTIES.add("creator");
        IGNORE_PROPERTIES.add("subject");
        IGNORE_PROPERTIES.add(Constants.RESPONSE_DESCRIPTION);
        IGNORE_PROPERTIES.add("publisher");
        IGNORE_PROPERTIES.add("contributor");
        IGNORE_PROPERTIES.add(YoutubeRequestParams.req_search_order);
        IGNORE_PROPERTIES.add("type");
        IGNORE_PROPERTIES.add("format");
        IGNORE_PROPERTIES.add(SettingsJsonConstants.APP_IDENTIFIER_KEY);
        IGNORE_PROPERTIES.add(Param.SOURCE);
        IGNORE_PROPERTIES.add("language");
        IGNORE_PROPERTIES.add("relation");
        IGNORE_PROPERTIES.add("coverage");
        IGNORE_PROPERTIES.add("rights");
        HashMap hashMap = new HashMap();
        hashMap.put("titles", String.class);
        hashMap.put("creators", String.class);
        hashMap.put("subjects", DCSubject.class);
        hashMap.put("descriptions", String.class);
        hashMap.put("publishers", String.class);
        hashMap.put("contributors", String.class);
        hashMap.put("dates", Date.class);
        hashMap.put("types", String.class);
        hashMap.put("formats", String.class);
        hashMap.put("identifiers", String.class);
        hashMap.put("sources", String.class);
        hashMap.put("languages", String.class);
        hashMap.put("relations", String.class);
        hashMap.put("coverages", String.class);
        hashMap.put("rightsList", String.class);
        HashMap hashMap2 = new HashMap();
        hashMap2.put(DCSubject.class, DCSubjectImpl.class);
        COPY_FROM_HELPER = new CopyFromHelper(DCModule.class, hashMap, hashMap2);
    }

    public DCModuleImpl() {
        super(DCModule.class, DCModule.URI);
    }

    public List<String> getTitles() {
        List<String> createWhenNull = Lists.createWhenNull(this.title);
        this.title = createWhenNull;
        return createWhenNull;
    }

    public void setTitles(List<String> list) {
        this.title = list;
    }

    public String getTitle() {
        return (String) Lists.firstEntry(this.title);
    }

    public void setTitle(String str) {
        this.title = Lists.create(str);
    }

    public List<String> getCreators() {
        List<String> createWhenNull = Lists.createWhenNull(this.creator);
        this.creator = createWhenNull;
        return createWhenNull;
    }

    public void setCreators(List<String> list) {
        this.creator = list;
    }

    public String getCreator() {
        return (String) Lists.firstEntry(this.creator);
    }

    public void setCreator(String str) {
        this.creator = Lists.create(str);
    }

    public List<DCSubject> getSubjects() {
        List<DCSubject> createWhenNull = Lists.createWhenNull(this.subject);
        this.subject = createWhenNull;
        return createWhenNull;
    }

    public void setSubjects(List<DCSubject> list) {
        this.subject = list;
    }

    public DCSubject getSubject() {
        return (DCSubject) Lists.firstEntry(this.subject);
    }

    public void setSubject(DCSubject dCSubject) {
        this.subject = Lists.create(dCSubject);
    }

    public List<String> getDescriptions() {
        List<String> createWhenNull = Lists.createWhenNull(this.description);
        this.description = createWhenNull;
        return createWhenNull;
    }

    public void setDescriptions(List<String> list) {
        this.description = list;
    }

    public String getDescription() {
        return (String) Lists.firstEntry(this.description);
    }

    public void setDescription(String str) {
        this.description = Lists.create(str);
    }

    public List<String> getPublishers() {
        List<String> createWhenNull = Lists.createWhenNull(this.publisher);
        this.publisher = createWhenNull;
        return createWhenNull;
    }

    public void setPublishers(List<String> list) {
        this.publisher = list;
    }

    public String getPublisher() {
        return (String) Lists.firstEntry(this.publisher);
    }

    public void setPublisher(String str) {
        this.publisher = Lists.create(str);
    }

    public List<String> getContributors() {
        List<String> createWhenNull = Lists.createWhenNull(this.contributors);
        this.contributors = createWhenNull;
        return createWhenNull;
    }

    public void setContributors(List<String> list) {
        this.contributors = list;
    }

    public String getContributor() {
        return (String) Lists.firstEntry(this.contributors);
    }

    public void setContributor(String str) {
        this.contributors = Lists.create(str);
    }

    public List<Date> getDates() {
        List<Date> createWhenNull = Lists.createWhenNull(this.date);
        this.date = createWhenNull;
        return createWhenNull;
    }

    public void setDates(List<Date> list) {
        this.date = list;
    }

    public Date getDate() {
        return (Date) Lists.firstEntry(this.date);
    }

    public void setDate(Date date2) {
        this.date = Lists.create(date2);
    }

    public List<String> getTypes() {
        List<String> createWhenNull = Lists.createWhenNull(this.type);
        this.type = createWhenNull;
        return createWhenNull;
    }

    public void setTypes(List<String> list) {
        this.type = list;
    }

    public String getType() {
        return (String) Lists.firstEntry(this.type);
    }

    public void setType(String str) {
        this.type = Lists.create(str);
    }

    public List<String> getFormats() {
        List<String> createWhenNull = Lists.createWhenNull(this.format);
        this.format = createWhenNull;
        return createWhenNull;
    }

    public void setFormats(List<String> list) {
        this.format = list;
    }

    public String getFormat() {
        return (String) Lists.firstEntry(this.format);
    }

    public void setFormat(String str) {
        this.format = Lists.create(str);
    }

    public List<String> getIdentifiers() {
        List<String> createWhenNull = Lists.createWhenNull(this.identifier);
        this.identifier = createWhenNull;
        return createWhenNull;
    }

    public void setIdentifiers(List<String> list) {
        this.identifier = list;
    }

    public String getIdentifier() {
        return (String) Lists.firstEntry(this.identifier);
    }

    public void setIdentifier(String str) {
        this.identifier = Lists.create(str);
    }

    public List<String> getSources() {
        List<String> createWhenNull = Lists.createWhenNull(this.source);
        this.source = createWhenNull;
        return createWhenNull;
    }

    public void setSources(List<String> list) {
        this.source = list;
    }

    public String getSource() {
        return (String) Lists.firstEntry(this.source);
    }

    public void setSource(String str) {
        this.source = Lists.create(str);
    }

    public List<String> getLanguages() {
        List<String> createWhenNull = Lists.createWhenNull(this.language);
        this.language = createWhenNull;
        return createWhenNull;
    }

    public void setLanguages(List<String> list) {
        this.language = list;
    }

    public String getLanguage() {
        return (String) Lists.firstEntry(this.language);
    }

    public void setLanguage(String str) {
        this.language = Lists.create(str);
    }

    public List<String> getRelations() {
        List<String> createWhenNull = Lists.createWhenNull(this.relation);
        this.relation = createWhenNull;
        return createWhenNull;
    }

    public void setRelations(List<String> list) {
        this.relation = list;
    }

    public String getRelation() {
        return (String) Lists.firstEntry(this.relation);
    }

    public void setRelation(String str) {
        this.relation = Lists.create(str);
    }

    public List<String> getCoverages() {
        List<String> createWhenNull = Lists.createWhenNull(this.coverage);
        this.coverage = createWhenNull;
        return createWhenNull;
    }

    public void setCoverages(List<String> list) {
        this.coverage = list;
    }

    public String getCoverage() {
        return (String) Lists.firstEntry(this.coverage);
    }

    public void setCoverage(String str) {
        this.coverage = Lists.create(str);
    }

    public List<String> getRightsList() {
        List<String> createWhenNull = Lists.createWhenNull(this.rights);
        this.rights = createWhenNull;
        return createWhenNull;
    }

    public void setRightsList(List<String> list) {
        this.rights = list;
    }

    public String getRights() {
        return (String) Lists.firstEntry(this.rights);
    }

    public void setRights(String str) {
        this.rights = Lists.create(str);
    }

    public final Object clone() throws CloneNotSupportedException {
        return this.objBean.clone();
    }

    public final boolean equals(Object obj) {
        return this.objBean.equals(obj);
    }

    public final int hashCode() {
        return this.objBean.hashCode();
    }

    public final String toString() {
        return this.objBean.toString();
    }

    public final Class<DCModule> getInterface() {
        return DCModule.class;
    }

    public final void copyFrom(CopyFrom copyFrom) {
        COPY_FROM_HELPER.copy(this, copyFrom);
    }
}
