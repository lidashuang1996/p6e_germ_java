package com.p6e.germ.oauth2.config;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eOauth2Config implements Serializable {

    private QQ qq = new QQ();
    private WeChat weChat = new WeChat();

    public QQ getQq() {
        return qq;
    }

    public void setQq(QQ qq) {
        this.qq = qq;
    }

    public WeChat getWeChat() {
        return weChat;
    }

    public void setWeChat(WeChat weChat) {
        this.weChat = weChat;
    }

    /**
     * Step1：获取Authorization Code
     *
     * 请求地址：
     * PC网站：https://graph.qq.com/oauth2.0/authorize
     *
     * 请求方法：
     * GET
     *
     * 请求参数：
     * 请求参数请包含如下内容：
     *
     * 参数	是否必须	含义
     * response_type	必须	        授权类型，此值固定为“code”。
     * client_id	    必须	        申请QQ登录成功后，分配给应用的appid。
     * redirect_uri	    必须	        成功授权后的回调地址，必须是注册appid时填写的主域名下的地址，建议设置为网站首页或网站的用户中心。注意需要将url进行URLEncode。
     * state	        必须	        client端的状态值。用于第三方应用防止CSRF攻击，成功授权后回调时会原样带回。请务必严格按照流程检查用户与state参数状态的绑定。
     * scope	        可选	        请求用户授权时向用户显示的可进行授权的列表。
     *
     * 可填写的值是API文档中列出的接口，以及一些动作型的授权（目前仅有：do_like），如果要填写多个接口名称，请用逗号隔开。
     * 例如：scope=get_user_info,list_album,upload_pic,do_like
     * 不传则默认请求对接口get_user_info进行授权。
     * 建议控制授权项的数量，只传入必要的接口名称，因为授权项越多，用户越可能拒绝进行任何授权。
     *
     * display	        可选	        仅PC网站接入时使用。
     *
     * 用于展示的样式。不传则默认展示为PC下的样式。
     * 如果传入“mobile”，则展示为mobile端下的样式。
     *
     *
     * 返回说明：
     * 1. 如果用户成功登录并授权，则会跳转到指定的回调地址，并在redirect_uri地址后带上Authorization Code和原始的state值。如：
     * PC网站：http://graph.qq.com/demo/index.jsp?code=9A5F************************06AF&state=test
     * 注意：此code会在10分钟内过期。
     *
     * 2. 如果用户在登录授权过程中取消登录流程，对于PC网站，登录页面直接关闭；对于WAP网站，同样跳转回指定的回调地址，并在redirect_uri地址后带上usercancel参数和原始的state值，其中usercancel值为非零，如：
     * http://open.z.qq.com/demo/index.jsp?usercancel=1&state=test
     *
     * 错误码说明：
     * 接口调用有错误时，会返回code和msg字段，以url参数对的形式返回，value部分会进行url编码（UTF-8）。
     * PC网站接入时，错误码详细信息请参见：100000-100031：PC网站接入时的公共返回码。
     *
     *
     *
     * Step2：通过Authorization Code获取Access Token
     *
     * 请求地址：
     * PC网站：https://graph.qq.com/oauth2.0/token
     *
     * 请求方法：
     * GET
     *
     * 请求参数：
     * 请求参数请包含如下内容：
     *
     * 参数	            是否必须	    含义
     * grant_type	    必须	        授权类型，在本步骤中，此值为“authorization_code”。
     * client_id	    必须	        申请QQ登录成功后，分配给网站的appid。
     * client_secret	必须	        申请QQ登录成功后，分配给网站的appkey。
     * code	            必须	        上一步返回的authorization code。
     * 如果用户成功登录并授权，则会跳转到指定的回调地址，并在URL中带上Authorization Code。
     * 例如，回调地址为www.qq.com/my.php，则跳转到：
     * http://www.qq.com/my.php?code=520DD95263C1CFEA087******
     * 注意此code会在10分钟内过期。
     * redirect_uri	    必须	        与上面一步中传入的redirect_uri保持一致。
     * fmt	            可选	        因历史原因，默认是x-www-form-urlencoded格式，如果填写json，则返回json格式
     * 返回说明：
     *
     * 如果成功返回，即可在返回包中获取到Access Token。 如(不指定fmt时）：
     *
     * access_token=FE04************************CCE2&expires_in=7776000&refresh_token=88E4************************BE14
     *
     * 参数说明	        描述
     * access_token	    授权令牌，Access_Token。
     * expires_in	    该access token的有效期，单位为秒。
     * refresh_token	在授权自动续期步骤中，获取新的Access_Token时需要提供的参数。
     * 注：refresh_token仅一次有效
     *
     * 错误码说明：
     * 接口调用有错误时，会返回code和msg字段，以url参数对的形式返回，value部分会进行url编码（UTF-8）。
     * PC网站接入时，错误码详细信息请参见：100000-100031：PC网站接入时的公共返回码。
     *
     *
     *
     * Step3：（可选）权限自动续期，获取Access Token
     *
     * Access_Token的有效期默认是3个月，过期后需要用户重新授权才能获得新的Access_Token。本步骤可以实现授权自动续期，避免要求用户再次授权的操作，提升用户体验。
     *
     * 请求地址：
     * PC网站：https://graph.qq.com/oauth2.0/token
     *
     * 请求方法：
     * GET
     *
     * 请求参数：
     * 请求参数请包含如下内容：
     *
     * 参数	            是否必须	    含义
     * grant_type	    必须	        授权类型，在本步骤中，此值为“refresh_token”。
     * client_id	    必须	        申请QQ登录成功后，分配给网站的appid。
     * client_secret	必须	        申请QQ登录成功后，分配给网站的appkey。
     * refresh_token	必须	        首次：使用在Step2中获取到的最新的refresh_token。
     * 后续：使用刷新后返回的最新refresh_token
     *
     * fmt	            可选	        因历史原因，默认是x-www-form-urlencoded格式，如果填写json，则返回json格式
     * 返回说明：
     * 如果成功返回，即可在返回包中获取到Access Token。 如(不指定fmt时）：
     * access_token=FE04************************CCE2&expires_in=7776000&refresh_token=88E4************************BE14。
     *
     * 参数说明	        描述
     * access_token	    授权令牌，Access_Token。
     * expires_in	    该access token的有效期，单位为秒。
     * refresh_token	在授权自动续期步骤中，获取新的Access_Token时需要提供的参数。每次生成最新的refresh_token，且仅一次有效
     *
     * 错误码说明：
     * 接口调用有错误时，会返回code和msg字段，以url参数对的形式返回，value部分会进行url编码（UTF-8）。
     * PC网站接入时，错误码详细信息请参见：100000-100031：PC网站接入时的公共返回码。
     *
     *
     *
     * 文档来源：https://wiki.connect.qq.com/%E4%BD%BF%E7%94%A8authorization_code%E8%8E%B7%E5%8F%96access_token
     *
     *
     * Step4：获取用户的信息
     *
     * 请求地址：
     * PC网站：https://graph.qq.com/oauth2.0/me
     *
     * 请求方法
     * GET
     *
     * 请求参数
     * 请求参数请包含如下内容：
     *
     * 参数	        是否必须	    含义
     * access_token	必须	        在Step1中获取到的access token。
     * fmt	        可选	        因历史原因，默认是jsonpb格式，如果填写json，则返回json格式
     *
     * 返回说明
     * PC网站接入时，获取到用户OpenID，返回包如下（如果fmt参数未指定)：
     *
     * callback( {"client_id":"YOUR_APPID","openid":"YOUR_OPENID"} );
     * openid是此网站上唯一对应用户身份的标识，网站可将此ID进行存储便于用户下次登录时辨识其身份，或将其与用户在网站上的原有账号进行绑定。
     *
     * 错误码说明：
     * 接口调用有错误时，会返回code和msg字段，以url参数对的形式返回，value部分会进行url编码（UTF-8）。
     * PC网站接入时，错误码详细信息请参见：100000-100031：PC网站接入时的公共返回码。
     */
    public static class QQ {
        /** response_type */
        private String responseType = "code";
        /** grant_type */
        private String grantType = "authorization_code";
        /** 范围 */
        private String scope = "get_user_info";
        /** client_id */
        private String clientId;
        /** client_secret */
        private String clientSecret;
        /** redirect_uri */
        private String redirectUri;
        /** 获取认证页面 */
        private String authUrl = "https://graph.qq.com/oauth2.0/authorize";
        /** 获取CODE数据 */
        private String tokenUrl = "https://graph.qq.com/oauth2.0/token";
        /** 获取用户信息 */
        private String infoUrl = "https://graph.qq.com/oauth2.0/me";

        public String getScope() {
            return scope;
        }

        public void setScope(String scope) {
            this.scope = scope;
        }

        public String getResponseType() {
            return responseType;
        }

        public void setResponseType(String responseType) {
            this.responseType = responseType;
        }

        public String getGrantType() {
            return grantType;
        }

        public void setGrantType(String grantType) {
            this.grantType = grantType;
        }

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getClientSecret() {
            return clientSecret;
        }

        public void setClientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
        }

        public String getRedirectUri() {
            return redirectUri;
        }

        public void setRedirectUri(String redirectUri) {
            this.redirectUri = redirectUri;
        }

        public String getAuthUrl() {
            return authUrl;
        }

        public void setAuthUrl(String authUrl) {
            this.authUrl = authUrl;
        }

        public String getTokenUrl() {
            return tokenUrl;
        }

        public void setTokenUrl(String tokenUrl) {
            this.tokenUrl = tokenUrl;
        }

        public String getInfoUrl() {
            return infoUrl;
        }

        public void setInfoUrl(String infoUrl) {
            this.infoUrl = infoUrl;
        }
    }

    public static class WeChat {

    }


}
