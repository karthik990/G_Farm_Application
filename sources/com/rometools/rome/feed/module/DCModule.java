package com.rometools.rome.feed.module;

import java.util.Date;
import java.util.List;

public interface DCModule extends Module {
    public static final String URI = "http://purl.org/dc/elements/1.1/";

    String getContributor();

    List<String> getContributors();

    String getCoverage();

    List<String> getCoverages();

    String getCreator();

    List<String> getCreators();

    Date getDate();

    List<Date> getDates();

    String getDescription();

    List<String> getDescriptions();

    String getFormat();

    List<String> getFormats();

    String getIdentifier();

    List<String> getIdentifiers();

    String getLanguage();

    List<String> getLanguages();

    String getPublisher();

    List<String> getPublishers();

    String getRelation();

    List<String> getRelations();

    String getRights();

    List<String> getRightsList();

    String getSource();

    List<String> getSources();

    DCSubject getSubject();

    List<DCSubject> getSubjects();

    String getTitle();

    List<String> getTitles();

    String getType();

    List<String> getTypes();

    void setContributor(String str);

    void setContributors(List<String> list);

    void setCoverage(String str);

    void setCoverages(List<String> list);

    void setCreator(String str);

    void setCreators(List<String> list);

    void setDate(Date date);

    void setDates(List<Date> list);

    void setDescription(String str);

    void setDescriptions(List<String> list);

    void setFormat(String str);

    void setFormats(List<String> list);

    void setIdentifier(String str);

    void setIdentifiers(List<String> list);

    void setLanguage(String str);

    void setLanguages(List<String> list);

    void setPublisher(String str);

    void setPublishers(List<String> list);

    void setRelation(String str);

    void setRelations(List<String> list);

    void setRights(String str);

    void setRightsList(List<String> list);

    void setSource(String str);

    void setSources(List<String> list);

    void setSubject(DCSubject dCSubject);

    void setSubjects(List<DCSubject> list);

    void setTitle(String str);

    void setTitles(List<String> list);

    void setType(String str);

    void setTypes(List<String> list);
}
