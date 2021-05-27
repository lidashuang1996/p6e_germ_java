package com.p6e.germ.oauth2.domain.entity;

import com.p6e.germ.common.config.P6eConfig;
import com.p6e.germ.common.config.P6eOauth2Config;
import com.p6e.germ.common.utils.P6eGeneratorUtil;
import com.p6e.germ.common.utils.P6eHttpUtil;
import com.p6e.germ.common.utils.P6eJsonUtil;
import com.p6e.germ.common.utils.P6eSpringUtil;
import com.p6e.germ.oauth2.infrastructure.cache.IP6eCacheOtherState;
import com.p6e.germ.oauth2.infrastructure.cache.P6eCache;

import java.net.URLEncoder;
import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
public final class P6eOtherSinaLoginEntity {

    private static String AUTH_URL;
    private static String TOKEN_URL;
    private static String INFO_URL;

    private static final String TYPE = "QQ";
    private static final IP6eCacheOtherState OTHER_STATE = P6eCache.otherState;

    static {
        try {
            final P6eConfig config = P6eSpringUtil.getBean(P6eConfig.class);
            final P6eOauth2Config.QQ qqConfig = config.getOauth2().getQq();
            AUTH_URL = qqConfig.getAuthUrl()
                    + "?response_type=" + URLEncoder.encode(qqConfig.getResponseType(), "UTF-8")
                    + "&client_id=" + URLEncoder.encode(qqConfig.getClientId(), "UTF-8")
                    + "&redirect_uri=" + URLEncoder.encode(qqConfig.getRedirectUri(), "UTF-8")
                    + "&scope=" + URLEncoder.encode(qqConfig.getScope(), "UTF-8");
            TOKEN_URL = qqConfig.getTokenUrl()
                    + "?grant_type=" + URLEncoder.encode(qqConfig.getGrantType(), "UTF-8")
                    + "&client_id=" + URLEncoder.encode(qqConfig.getClientId(), "UTF-8")
                    + "&client_secret=" + URLEncoder.encode(qqConfig.getClientSecret(), "UTF-8")
                    + "&redirect_uri=" + URLEncoder.encode(qqConfig.getRedirectUri(), "UTF-8")
                    + "&fmt=json";
            INFO_URL = qqConfig.getInfoUrl() + "?fmt=json";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    private static String getTokenData(String code) {
        try {
            final String content = P6eHttpUtil.doGet(TOKEN_URL + "&code=" + code);
            if (content != null && !content.isEmpty()) {
                final Map<String, String> map = P6eJsonUtil.fromJsonToMap(content, String.class, String.class);
                if (map != null && map.size() > 0) {
                    return map.get("access_token");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Map<String, String> getInfoData(String accessToken) {
        try {
            final String content = P6eHttpUtil.doGet(INFO_URL + "&access_token=" + accessToken);
            if (content != null && !content.isEmpty()) {
                final Map<String, String> map = P6eJsonUtil.fromJsonToMap(content, String.class, String.class);
                if (map != null && map.size() > 0) {
                    return map;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    private final String mark;
    public static P6eOtherSinaLoginEntity create(String mark) {
        return new P6eOtherSinaLoginEntity(mark);
    }

    public P6eOtherSinaLoginEntity(String mark) {
        this.mark = mark;
    }

    public String getAuthUrl() {
        return getAuthUrl(null);
    }

    public String getAuthUrl(String display) {
        try {
            final String state = P6eGeneratorUtil.uuid();
            OTHER_STATE.set(TYPE, state, mark);
            return AUTH_URL + "&state=" + state
                    + (display == null ? "" : "&display=" + URLEncoder.encode(display, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
