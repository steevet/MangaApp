package com.example.mangareaderapp;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MangaChapter implements Serializable {
    private HashMap<String, Object> data;
    private Map<String, String> attributes;
    private List<Map<String, String>> relationships;
    private LinkedHashMap<String, byte[]> pages = new LinkedHashMap<String, byte[]>();
    private String hash;
    private String scanlationGroup;

    public MangaChapter(HashMap<String, Object> data){
        this.data = data;
        this.attributes = (Map<String, String>) data.get("attributes");
        this.relationships = (List<Map<String, String>>) data.get("relationships");
    }

    public void setPageBytes(String page, byte[] bytes) {
        this.pages.put(page, bytes);
    }

    public byte[] getPageBytes(String page){
        return this.pages.get(page);
    }

    public String getId() {
        return (String) data.get("id");
    }

    public String getType() {
        return (String) data.get("type");
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public void addPage(String page){
        pages.put(page, null);
    }

    public LinkedHashMap<String, byte[]> getPages(){
        return pages;
    }

    public void setPages(LinkedHashMap<String, byte[]> pages){
        this.pages = pages;
    }

    public String getHash(){
        return this.hash;
    }

    public void setHash(String hash){
        this.hash = hash;
    }

    public String getMangaId() {
        for(Map<String, String> relationship : relationships) {
            if (((String) relationship.get("type")).equals("manga")){
                return (String) relationship.get("id");
            }
        }
        return (String) "NOTFOUND";
    }

    public String getChapterId() {
        return (String) data.get("id");
    }

    public String getVolume(){
        return (String) attributes.get("volume");
    }

    public String getChapterNumber(){
        return (String) attributes.get("chapter");
    }

    public String getTranslatedLanguage(){
        return (String) attributes.get("translatedLanguage");
    }

    public String getScanlationGroup(){
        MangaDex mangaDex = new MangaDex();
        scanlationGroup = "";
        for(Map<String, String> relationship : relationships) {
            if (((String) relationship.get("type")).equals("scanlation_group")){
                scanlationGroup = relationship.get("id");
            }
        }

        if(MangaDex.getGroupsCache().containsKey(scanlationGroup)){
            return MangaDex.getGroupsCache().get(scanlationGroup);
        }
        String translatedGroup = mangaDex.translateIdtoString(scanlationGroup, "group");
        MangaDex.getGroupsCache().put(scanlationGroup, translatedGroup);
        return translatedGroup;
    }

    public String getExternalUrl(){
        return (String) attributes.get("externalUrl");
    }

    @Override
    public String toString() {
        return String.format("Chapter [id=%s, Volume=%s, Chapter=%s, Language=%s]", getChapterId(),
                getVolume(), getChapterNumber(), getTranslatedLanguage());
    }
}
