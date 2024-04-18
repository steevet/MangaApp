package com.example.mangareaderapp;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class MangaCover implements Serializable {
    private HashMap<String, Object> data;
    private Map<String, String> attributes;
    private List<Map<String, String>> relationships;
    private byte[] cover = null;
    private byte[] cover256 = null;
    private byte[] cover512 = null;

    public MangaCover(HashMap<String, Object> data) {
        this.data = data;
        this.attributes = (Map<String, String>) data.get("attributes");
        this.relationships = (List<Map<String, String>>) data.get("relationships");
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

    public String getMangaId() {
        for (Map<String, String> relationship : relationships) {

            if (((String)relationship.get("type")).equals("manga")) {
                return (String)relationship.get("id");
            }
        }
        return (String) "NOTFOUND";
    }

    public String getDescription() {
        return (String) attributes.get("description");
    }

    public String getLocale() {
        return (String) attributes.get("locale");
    }

    public String getFileName() {
        return this.getFileName(0);
    }

    public String getFileName(int width) {
        if (width == 0) {
            return (String) attributes.get("fileName");
        } else if (width == 512) {
            return (String) attributes.get("fileName") + ".512.jpg";
        } else {
            return (String) attributes.get("fileName") + ".256.jpg";
        }
    }

    public void setCoverBytes(byte[] coverBytes, int width) {
        if (width == 0) {
            this.cover = coverBytes;
        } else if (width == 512) {
            this.cover512 = coverBytes;
        } else {
            this.cover256 = coverBytes;
        }
    }

    public byte[] getCoverBytes() {
        return this.getCoverBytes(0);
    }

    public byte[] getCoverBytes(int width) {
        if (width == 0) {
            return this.cover;
        } else if (width == 512) {
            return this.cover512;
        } else {
            return this.cover256;
        }
    }
    @Override
    public String toString() {
        return String.format("Cover [id=%s, mangaId=%s, locale=%s, description=%s, filename=%s, data=%s]",
                this.getId(), this.getMangaId(), this.getLocale(), this.getDescription(),
                this.getFileName(), ""/*this.data*/);
    }
}