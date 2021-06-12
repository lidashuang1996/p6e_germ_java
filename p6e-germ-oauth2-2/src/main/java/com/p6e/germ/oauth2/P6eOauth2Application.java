package com.p6e.germ.oauth2;

import com.p6e.germ.oauth2.context.controller.support.P6eBaseController;
import com.p6e.germ.oauth2.domain.entity.P6eUserEntity;
import com.p6e.germ.oauth2.starter.P6eEnableOauth2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 认证的服务入口
 * @author lidashuang
 * @version 1.0
 */
@P6eEnableOauth2
@SpringBootApplication
public class P6eOauth2Application {
    public static void main(String[] args) {
        P6eBaseController.INDEX_HTML = ""
                + "<!DOCTYPE html><html lang=\"\"><head><meta charset=\"utf-8\"><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><meta name=\"viewport\" content=\"width=device-width,initial-scale=1\"><!--[if IE]><link rel=\"icon\" href=\"favicon.ico\"><![endif]--><title>p6e_auth_vue</title><script>// const data = '$x{__DATA__}';\n" +
                "      const data = '${__DATA__}';\n" +
                "      window['P6E_OAUTH2_DATA'] = JSON.parse(data);</script><link href=\"css/app.b586740f.css\" rel=\"preload\" as=\"style\"><link href=\"css/chunk-vendors.32108b03.css\" rel=\"preload\" as=\"style\"><link href=\"js/app.658cc536.js\" rel=\"preload\" as=\"script\"><link href=\"js/chunk-vendors.47e6b58f.js\" rel=\"preload\" as=\"script\"><link href=\"css/chunk-vendors.32108b03.css\" rel=\"stylesheet\"><link href=\"css/app.b586740f.css\" rel=\"stylesheet\"><link rel=\"icon\" type=\"image/png\" sizes=\"32x32\" href=\"img/icons/favicon-32x32.png\"><link rel=\"icon\" type=\"image/png\" sizes=\"16x16\" href=\"img/icons/favicon-16x16.png\"><link rel=\"manifest\" href=\"manifest.json\"><meta name=\"theme-color\" content=\"#4DBA87\"><meta name=\"apple-mobile-web-app-capable\" content=\"no\"><meta name=\"apple-mobile-web-app-status-bar-style\" content=\"default\"><meta name=\"apple-mobile-web-app-title\" content=\"p6e_auth_vue\"><link rel=\"apple-touch-icon\" href=\"img/icons/apple-touch-icon-152x152.png\"><link rel=\"mask-icon\" href=\"img/icons/safari-pinned-tab.svg\" color=\"#4DBA87\"><meta name=\"msapplication-TileImage\" content=\"img/icons/msapplication-icon-144x144.png\"><meta name=\"msapplication-TileColor\" content=\"#000000\"></head><body><noscript><strong>We're sorry but p6e_auth_vue doesn't work properly without JavaScript enabled. Please enable it to continue.</strong></noscript><div id=\"app\"></div><script src=\"js/chunk-vendors.47e6b58f.js\"></script><script src=\"js/app.658cc536.js\"></script></body></html>" +
                "";
        SpringApplication.run(P6eOauth2Application.class, args);
        System.out.println(P6eUserEntity.encryption(""));
    }
}
