package com.p6e.germ.common.config;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eOauth2Config implements Serializable {

    private String html = "${__DATA__}";
    private Type[] types = new Type[]{
            Type.CODE
    };

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public Type[] getTypes() {
        return types;
    }

    public void setTypes(Type[] types) {
        this.types = types;
    }

    private QQ qq = new QQ();
    private WeChat weChat = new WeChat();
    private Sina sina = new Sina();

    private QrCode qrCode = new QrCode();
    private NrCode nrCode = new NrCode();

    public NrCode getNrCode() {
        return nrCode;
    }

    public void setNrCode(NrCode nrCode) {
        this.nrCode = nrCode;
    }

    public Sina getSina() {
        return sina;
    }

    public void setSina(Sina sina) {
        this.sina = sina;
    }

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

    public QrCode getQrCode() {
        return qrCode;
    }

    public void setQrCode(QrCode qrCode) {
        this.qrCode = qrCode;
    }

    public enum Type {
        /**
         * CODE 模式
         */
        CODE,
        /**
         * 客户端模式
         */
        CLIENT,
        /**
         * 简化模式
         */
        TOKEN,
        /**
         * 密码模式
         */
        PASSWORD;

        public boolean is(String t) {
            return this.name().equals(t);
        }
    }

    public static class NrCode {
        /**
         * 是否启用
         */
        private boolean enable = true;

        public boolean isEnable() {
            return enable;
        }

        public void setEnable(boolean enable) {
            this.enable = enable;
        }
    }

    public static class QrCode {
        private String url;
        /** 是否启用 */
        private boolean enable = true;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isEnable() {
            return enable;
        }

        public void setEnable(boolean enable) {
            this.enable = enable;
        }
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
        /** 是否启用 */
        private boolean enable = true;
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

        public boolean isEnable() {
            return enable;
        }

        public void setEnable(boolean enable) {
            this.enable = enable;
        }

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

    /**
     * 第一步：请求CODE
     *
     * 第三方使用网站应用授权登录前请注意已获取相应网页授权作用域（scope=snsapi_login）
     * 则可以通过在PC端打开以下链接：
     * https://open.weixin.qq.com/connect/qrconnect?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect
     * 若提示“该链接无法访问”，请检查参数是否填写错误，如redirect_uri的域名与审核时填写的授权域名不一致或scope不为snsapi_login。
     *
     * 参数说明
     *
     * 参数	            是否必须	    说明
     * appid	        是	        应用唯一标识
     * redirect_uri	    是	        请使用urlEncode对链接进行处理
     * response_type	是	        填code
     * scope	        是	        应用授权作用域，拥有多个作用域用逗号（,）分隔，网页应用目前仅填写snsapi_login
     * state	        否	        用于保持请求和回调的状态，授权请求后原样带回给第三方。
     * 该参数可用于防止csrf攻击（跨站请求伪造攻击），建议第三方带上该参数，可设置为简单的随机数加session进行校验
     *
     * 返回说明
     * 用户允许授权后，将会重定向到redirect_uri的网址上，并且带上code和state参数
     *
     * redirect_uri?code=CODE&state=STATE
     * 若用户禁止授权，则重定向后不会带上code参数，仅会带上state参数
     *
     * redirect_uri?state=STATE
     * 请求示例
     *
     * 登录一号店网站应用 https://passport.yhd.com/wechat/login.do 打开后，
     * 一号店会生成state参数，跳转到
     * https://open.weixin.qq.com/connect/qrconnect?appid=wxbdc5610cc59c1631&redirect_uri=https%3A%2F%2Fpassport.yhd.com%2Fwechat%2Fcallback.do&response_type=code&scope=snsapi_login&state=3d6be0a4035d839573b04816624a415e#wechat_redirect
     * 微信用户使用微信扫描二维码并且确认登录后，PC端会跳转到
     * https://passport.yhd.com/wechat/callback.do?code=CODE&state=3d6be0a4035d839573b04816624a415e
     * 为了满足网站更定制化的需求，我们还提供了第二种获取code的方式，支持网站将微信登录二维码内嵌到自己页面中，用户使用微信扫码授权后通过JS将code返回给网站。
     * JS微信登录主要用途：网站希望用户在网站内就能完成登录，无需跳转到微信域下登录后再返回，提升微信登录的流畅性与成功率。
     *
     * 网站内嵌二维码微信登录JS实现办法：
     *
     * 步骤1：在页面中先引入如下JS文件（支持https）：
     * http://res.wx.qq.com/connect/zh_CN/htmledition/js/wxLogin.js
     *
     * 步骤2：在需要使用微信登录的地方实例以下JS对象：
     *
     *  var obj = new WxLogin({
     *  self_redirect:true,
     *  id:"login_container",
     *  appid: "",
     *  scope: "",
     *  redirect_uri: "",
     *   state: "",
     *  style: "",
     *  href: ""
     *  });
     * 参数说明
     *
     * 参数	            是否必须	    说明
     * self_redirect	否	        true：手机点击确认登录后可以在 iframe 内跳转到 redirect_uri，false：手机点击确认登录后可以在 top window 跳转到 redirect_uri。默认为 false。
     * id	            是	        第三方页面显示二维码的容器id
     * appid	        是	        应用唯一标识，在微信开放平台提交应用审核通过后获得
     * scope	        是	        应用授权作用域，拥有多个作用域用逗号（,）分隔，网页应用目前仅填写snsapi_login即可
     * redirect_uri	    是	        重定向地址，需要进行UrlEncode
     * state	        否	        用于保持请求和回调的状态，授权请求后原样带回给第三方。该参数可用于防止csrf攻击（跨站请求伪造攻击），建议第三方带上该参数，可设置为简单的随机数加session进行校验
     * style	        否	        提供"black"、"white"可选，默认为黑色文字描述。详见文档底部FAQ
     * href	            否	        自定义样式链接，第三方可根据实际需求覆盖默认样式。详见文档底部FAQ
     *
     *
     *
     *
     * 第二步：通过code获取access_token
     * 通过code获取access_token
     * https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
     *
     * 参数说明
     *
     * 参数	        是否必须	    说明
     * appid	    是	        应用唯一标识，在微信开放平台提交应用审核通过后获得
     * secret	    是	        应用密钥AppSecret，在微信开放平台提交应用审核通过后获得
     * code	        是	        填写第一步获取的code参数
     * grant_type	是	        填authorization_code
     * 返回说明
     *
     * 正确的返回：
     *
     * {
     * "access_token":"ACCESS_TOKEN",
     * "expires_in":7200,
     * "refresh_token":"REFRESH_TOKEN",
     * "openid":"OPENID",
     * "scope":"SCOPE",
     * "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"
     * }
     *
     * 参数说明
     * 参数	            说明
     * access_token	    接口调用凭证
     * expires_in	    access_token接口调用凭证超时时间，单位（秒）
     * refresh_token	用户刷新access_token
     * openid	        授权用户唯一标识
     * scope	        用户授权的作用域，使用逗号（,）分隔
     * unionid	        当且仅当该网站应用已获得该用户的userinfo授权时，才会出现该字段。
     *
     * 错误返回样例：
     * {"errcode":40029,"errmsg":"invalid code"}
     *
     * 刷新access_token有效期
     *
     * access_token是调用授权关系接口的调用凭证，由于access_token有效期（目前为2个小时）较短，当access_token超时后，可以使用refresh_token进行刷新，access_token刷新结果有两种：
     *
     * 1. 若access_token已超时，那么进行refresh_token会获取一个新的access_token，新的超时时间；
     * 2. 若access_token未超时，那么进行refresh_token不会改变access_token，但超时时间会刷新，相当于续期access_token。
     * refresh_token拥有较长的有效期（30天），当refresh_token失效的后，需要用户重新授权。
     *
     * 请求方法
     * 获取第一步的code后，请求以下链接进行refresh_token：
     *
     * https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN
     *
     * 参数说明
     * 参数	            是否必须	    说明
     * appid	        是	        应用唯一标识
     * grant_type	    是	        填refresh_token
     * refresh_token	是	        填写通过access_token获取到的refresh_token参数
     *
     *
     * 返回说明
     * 正确的返回：
     *
     * {
     * "access_token":"ACCESS_TOKEN",
     * "expires_in":7200,
     * "refresh_token":"REFRESH_TOKEN",
     * "openid":"OPENID",
     * "scope":"SCOPE"
     * }
     *
     * 参数说明
     * 参数	            说明
     * access_token	    接口调用凭证
     * expires_in	    access_token接口调用凭证超时时间，单位（秒）
     * refresh_token	用户刷新access_token
     * openid	        授权用户唯一标识
     * scope	        用户授权的作用域，使用逗号（,）分隔
     * 错误返回样例：
     *
     * {"errcode":40030,"errmsg":"invalid refresh_token"}
     *
     * 注意：
     * 1、Appsecret 是应用接口使用密钥，泄漏后将可能导致应用数据泄漏、应用的用户数据泄漏等高风险后果；存储在客户端，极有可能被恶意窃取（如反编译获取Appsecret）；
     * 2、access_token 为用户授权第三方应用发起接口调用的凭证（相当于用户登录态），存储在客户端，可能出现恶意获取access_token 后导致的用户数据泄漏、用户微信相关接口功能被恶意发起等行为；
     * 3、refresh_token 为用户授权第三方应用的长效凭证，仅用于刷新access_token，但泄漏后相当于access_token 泄漏，风险同上。
     *
     * 建议将secret、用户数据（如access_token）放在App云端服务器，由云端中转接口调用请求。
     *
     *
     * 第三步：通过access_token调用接口
     *
     * 获取access_token后，进行接口调用，有以下前提：
     *
     * 1. access_token有效且未超时；
     * 2. 微信用户已授权给第三方应用帐号相应接口作用域（scope）。
     * 对于接口作用域（scope），能调用的接口有以下：
     *
     * 授权作用域（scope）	    接口	                    接口说明
     * snsapi_base	            /sns/oauth2/access_token	通过code换取access_token、refresh_token和已授权scope
     * snsapi_base	            /sns/oauth2/refresh_token	刷新或续期access_token使用
     * snsapi_base	            /sns/auth	                检查access_token有效性
     * snsapi_userinfo	        /sns/userinfo	            获取用户个人信息
     *
     * 其中snsapi_base属于基础接口，若应用已拥有其它scope权限，则默认拥有snsapi_base的权限。
     * 使用snsapi_base可以让移动端网页授权绕过跳转授权登录页请求用户授权的动作，直接跳转第三方网页带上授权临时票据（code），
     * 但会使得用户已授权作用域（scope）仅为snsapi_base，从而导致无法获取到需要用户授权才允许获得的数据和基础功能。
     */
    public static class WeChat {
        /** 是否启用 */
        private boolean enable = true;
        /** response_type */
        private String responseType = "code";
        /** grant_type */
        private String grantType = "authorization_code";
        /** 范围 */
        private String scope = "snsapi_login";
        /** client_id */
        private String clientId;
        /** client_secret */
        private String clientSecret;
        /** redirect_uri */
        private String redirectUri;
        /** 获取认证页面 */
        private String authUrl = "https://open.weixin.qq.com/connect/qrconnect";
        /** 获取CODE数据 */
        private String tokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token";

        public boolean isEnable() {
            return enable;
        }

        public void setEnable(boolean enable) {
            this.enable = enable;
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

        public String getScope() {
            return scope;
        }

        public void setScope(String scope) {
            this.scope = scope;
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
    }

    public static class Sina {
        /** 是否启用 */
        private boolean enable = true;
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

        public boolean isEnable() {
            return enable;
        }

        public void setEnable(boolean enable) {
            this.enable = enable;
        }

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

}
