package com.dyy.p6e.germ.file.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "p6e.germ.file")
public class P6eGermFileConfig {

    public static class Setting {
        private String[] format;
        private String path;
        private Long size;
        private boolean visit;

        public String[] getFormat() {
            return format;
        }

        public void setFormat(String[] format) {
            this.format = format;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public Long getSize() {
            return size;
        }

        public void setSize(Long size) {
            this.size = size;
        }

        public boolean isVisit() {
            return visit;
        }

        public void setVisit(boolean visit) {
            this.visit = visit;
        }

        @Override
        public String toString() {
            return "{" + "\"format\":" +
                    Arrays.toString(format) +
                    ",\"path\":\"" +
                    path + '\"' +
                    ",\"size\":" +
                    size +
                    ",\"visit\":" +
                    visit +
                    '}';
        }
    }

    private String basePath;

    private Map<String, Setting> map = new HashMap<>();

    public Map<String, Setting> getMap() {
        return map;
    }

    public void setMap(Map<String, Setting> map) {
        this.map = map;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    @Override
    public String toString() {
        return "{" + "\"basePath\":\"" +
                basePath + '\"' +
                ",\"map\":" +
                map +
                '}';
    }
}
