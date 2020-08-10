package com.rometools.rome.feed.rss;

import com.rometools.rome.feed.WireFeed;
import com.rometools.rome.feed.module.Module;
import com.rometools.rome.feed.module.impl.ModuleUtils;
import com.rometools.utils.Dates;
import com.rometools.utils.Lists;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Channel extends WireFeed {
    private static final Set<String> DAYS;
    public static final String FRIDAY = "friday";
    public static final String MONDAY = "monday";
    public static final String SATURDAY = "saturday";
    public static final String SUNDAY = "sunday";
    public static final String THURSDAY = "thursday";
    public static final String TUESDAY = "tuesday";
    public static final String WEDNESDAY = "wednesday";
    private static final long serialVersionUID = 1;
    private List<Category> categories;
    private Cloud cloud;
    private String copyright;
    private String description;
    private String docs;
    private String generator;
    private Image image;
    private List<Item> items;
    private String language;
    private Date lastBuildDate;
    private String link;
    private String managingEditor;
    private List<Module> modules;
    private Date pubDate;
    private String rating;
    private List<String> skipDays;
    private List<Integer> skipHours;
    private TextInput textInput;
    private String title;
    private int ttl = -1;
    private String uri;
    private String webMaster;

    static {
        HashSet hashSet = new HashSet();
        hashSet.add(SUNDAY);
        hashSet.add(MONDAY);
        hashSet.add(TUESDAY);
        hashSet.add(WEDNESDAY);
        hashSet.add(THURSDAY);
        hashSet.add(FRIDAY);
        hashSet.add(SATURDAY);
        DAYS = Collections.unmodifiableSet(hashSet);
    }

    public Channel() {
    }

    public Channel(String str) {
        super(str);
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String str) {
        this.link = str;
    }

    public String getUri() {
        return this.uri;
    }

    public void setUri(String str) {
        this.uri = str;
    }

    public Image getImage() {
        return this.image;
    }

    public void setImage(Image image2) {
        this.image = image2;
    }

    public List<Item> getItems() {
        List<Item> createWhenNull = Lists.createWhenNull(this.items);
        this.items = createWhenNull;
        return createWhenNull;
    }

    public void setItems(List<Item> list) {
        this.items = list;
    }

    public TextInput getTextInput() {
        return this.textInput;
    }

    public void setTextInput(TextInput textInput2) {
        this.textInput = textInput2;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String str) {
        this.language = str;
    }

    public String getRating() {
        return this.rating;
    }

    public void setRating(String str) {
        this.rating = str;
    }

    public String getCopyright() {
        return this.copyright;
    }

    public void setCopyright(String str) {
        this.copyright = str;
    }

    public Date getPubDate() {
        return Dates.copy(this.pubDate);
    }

    public void setPubDate(Date date) {
        this.pubDate = Dates.copy(date);
    }

    public Date getLastBuildDate() {
        return Dates.copy(this.lastBuildDate);
    }

    public void setLastBuildDate(Date date) {
        this.lastBuildDate = Dates.copy(date);
    }

    public String getDocs() {
        return this.docs;
    }

    public void setDocs(String str) {
        this.docs = str;
    }

    public String getManagingEditor() {
        return this.managingEditor;
    }

    public void setManagingEditor(String str) {
        this.managingEditor = str;
    }

    public String getWebMaster() {
        return this.webMaster;
    }

    public void setWebMaster(String str) {
        this.webMaster = str;
    }

    public List<Integer> getSkipHours() {
        return Lists.createWhenNull(this.skipHours);
    }

    public void setSkipHours(List<Integer> list) {
        if (list != null) {
            int i = 0;
            while (i < list.size()) {
                Integer num = (Integer) list.get(i);
                if (num != null) {
                    int intValue = num.intValue();
                    if (intValue < 0 || intValue > 24) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Invalid hour [");
                        sb.append(intValue);
                        sb.append("]");
                        throw new IllegalArgumentException(sb.toString());
                    }
                    i++;
                } else {
                    throw new IllegalArgumentException("Invalid hour [null]");
                }
            }
        }
        this.skipHours = list;
    }

    public List<String> getSkipDays() {
        return Lists.createWhenNull(this.skipDays);
    }

    public void setSkipDays(List<String> list) {
        if (list != null) {
            int i = 0;
            while (i < list.size()) {
                String str = (String) list.get(i);
                if (str != null) {
                    String lowerCase = str.toLowerCase();
                    if (DAYS.contains(lowerCase)) {
                        list.set(i, lowerCase);
                        i++;
                    } else {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Invalid day [");
                        sb.append(lowerCase);
                        sb.append("]");
                        throw new IllegalArgumentException(sb.toString());
                    }
                } else {
                    throw new IllegalArgumentException("Invalid day [null]");
                }
            }
        }
        this.skipDays = list;
    }

    public Cloud getCloud() {
        return this.cloud;
    }

    public void setCloud(Cloud cloud2) {
        this.cloud = cloud2;
    }

    public List<Category> getCategories() {
        List<Category> createWhenNull = Lists.createWhenNull(this.categories);
        this.categories = createWhenNull;
        return createWhenNull;
    }

    public void setCategories(List<Category> list) {
        this.categories = list;
    }

    public String getGenerator() {
        return this.generator;
    }

    public void setGenerator(String str) {
        this.generator = str;
    }

    public int getTtl() {
        return this.ttl;
    }

    public void setTtl(int i) {
        this.ttl = i;
    }

    public List<Module> getModules() {
        List<Module> createWhenNull = Lists.createWhenNull(this.modules);
        this.modules = createWhenNull;
        return createWhenNull;
    }

    public void setModules(List<Module> list) {
        this.modules = list;
    }

    public Module getModule(String str) {
        return ModuleUtils.getModule(this.modules, str);
    }
}
