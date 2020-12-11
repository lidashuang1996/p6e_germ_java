package com.p6e.germ.oauth2.application.impl;

import com.p6e.germ.oauth2.application.P6eLoginService;
import com.p6e.germ.oauth2.config.P6eConfig;
import com.p6e.germ.oauth2.config.P6eOauth2Config;
import com.p6e.germ.oauth2.domain.entity.*;
import com.p6e.germ.oauth2.domain.keyvalue.P6eCodeKeyValue;
import com.p6e.germ.oauth2.domain.keyvalue.P6eMarkKeyValue;
import com.p6e.germ.oauth2.infrastructure.utils.*;
import com.p6e.germ.oauth2.model.P6eModel;
import com.p6e.germ.oauth2.model.db.P6eOauth2LogDb;
import com.p6e.germ.oauth2.model.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eLoginServiceImpl implements P6eLoginService {

    /** 注入日志系统 */
    private final static Logger LOGGER = LoggerFactory.getLogger(P6eLoginService.class);

    /** 注入配置文件数据 */
    private final P6eConfig p6eConfig = P6eSpringUtil.getBean(P6eConfig.class);

    @Override
    public P6eLoginDto verification(P6eVerificationLoginDto param) {
        // 创建登录信息返回对象
        final P6eLoginDto p6eLoginDto = new P6eLoginDto();
        try {
            // 校验参数
            if (param == null || param.getMark() == null || param.getAccessToken() == null) {
                p6eLoginDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
            } else {
                // 查询 mark 信息
                final P6eMarkEntity p6eMarkEntity = new P6eMarkEntity(param.getMark());
                // 读取 mark 信息内容
                final P6eMarkKeyValue p6eMarkKeyValue = p6eMarkEntity.getP6eMarkKeyValue();
                // 查询用户信息并重制模型
                final P6eTokenEntity p6eTokenEntity =
                        new P6eTokenEntity(param.getAccessToken(), P6eTokenEntity.ACCESS_TOKEN).resetModel();
                // 写入客户端信息
                P6eCopyUtil.run(p6eMarkKeyValue, p6eLoginDto);
                // 写入用户认证信息
                P6eCopyUtil.run(p6eTokenEntity.getModel(), p6eLoginDto);
                // 写入返回数据 CODE
                p6eLoginDto.setCode(p6eTokenEntity.getKey());
                // CID / UID
                int cid = p6eMarkKeyValue.getId();
                int uid = Integer.parseInt(p6eTokenEntity.getValue().get("id"));
                // 简化模式修改过期时间
                final long simpleDateTime = 120;
                final String simpleType = "TOKEN";
                final String type = p6eMarkKeyValue.getResponseType();
                if (simpleType.equals(type.toUpperCase())) {
                    // 如果为简化模式对返回的数据进行一下修改
                    p6eTokenEntity.delRefreshToken();
                    p6eTokenEntity.setAccessTokenExpirationTime(simpleDateTime);
                    p6eLoginDto.setRefreshToken(null);
                    p6eLoginDto.setExpiresIn(simpleDateTime);
                    // 写入日志数据
                    new P6eLogEntity(new P6eOauth2LogDb()
                            .setCid(cid)
                            .setUid(uid)
                            .setType("UID_TO_CID_VERIFICATION_TO_TOKEN_TYPE")
                    ).create();
                } else {
                    // 写入日志数据
                    new P6eLogEntity(new P6eOauth2LogDb()
                            .setCid(cid)
                            .setUid(uid)
                            .setType("UID_TO_CID_VERIFICATION_TO_CODE_TYPE")
                    ).create();
                }
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            p6eLoginDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
        } catch (Exception ee) {
            LOGGER.error(ee.getMessage());
            p6eLoginDto.setError(P6eModel.Error.SERVICE_EXCEPTION);
        }
        return p6eLoginDto;
    }

    @Override
    public P6eVoucherDto defaultVoucher() {
        // 创建凭证对象
        final P6eVoucherDto p6eVoucherDto = new P6eVoucherDto();
        try {
            // 创建凭证并缓存
            final P6eVoucherEntity p6eVoucherEntity = new P6eVoucherEntity().create().cache();
            // 写入数据
            p6eVoucherDto.setVoucher(p6eVoucherEntity.getVoucher());
            p6eVoucherDto.setPublicKey(p6eVoucherEntity.getPublicSecretKey());
        } catch (RuntimeException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            p6eVoucherDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
        } catch (Exception ee) {
            LOGGER.error(ee.getMessage());
            p6eVoucherDto.setError(P6eModel.Error.SERVICE_EXCEPTION);
        }
        return p6eVoucherDto;
    }

    @Override
    public P6eLoginDto defaultLogin(P6eDefaultLoginDto param) {
        // 创建登录信息返回对象
        final P6eLoginDto p6eLoginDto = new P6eLoginDto();
        try {
            // 校验参数
            if (param == null
                    || param.getAccount() == null
                    || param.getPassword() == null
                    || param.getMark() == null
                    || param.getVoucher() == null) {
                p6eLoginDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
            } else {
                // 获取标记信息
                final P6eMarkEntity p6eMarkEntity = new P6eMarkEntity(param.getMark());
                // 获取凭证信息
                final P6eVoucherEntity p6eVoucherEntity = new P6eVoucherEntity(param.getVoucher()).get();
                // 获取用户信息
                final P6eUserEntity p6eUserEntity;
                try {
                    // 出现异常就是账号密码错误
                    p6eUserEntity = new P6eUserEntity(new P6eUserEntity.Account(param.getAccount()));
                } catch (Exception e){
                    p6eLoginDto.setError(P6eModel.Error.ACCOUNT_OR_PASSWORD);
                    return p6eLoginDto;
                }
                // 解密验证账号密码
                if (p6eUserEntity.defaultVerification(p6eVoucherEntity.execute(param.getPassword()))) {
                    try {
                        // 创建用户认证信息并缓存
                        final P6eTokenEntity p6eTokenEntity = p6eUserEntity.createTokenCache().cache();
                        // 读取 MARK 信息
                        final P6eMarkKeyValue p6eMarkKeyValue = p6eMarkEntity.getP6eMarkKeyValue();
                        // 写入返回数据 CODE
                        p6eLoginDto.setCode(p6eTokenEntity.getKey());
                        // 写入客户端信息
                        P6eCopyUtil.run(p6eMarkKeyValue, p6eLoginDto);
                        // 写入用户认证信息
                        P6eCopyUtil.run(p6eTokenEntity.getModel(), p6eLoginDto);
                        // CID / UID
                        int cid = p6eMarkKeyValue.getId();
                        int uid = Integer.parseInt(p6eTokenEntity.getValue().get("id"));
                        // 简化模式修改过期时间
                        final long simpleDateTime = 120;
                        final String simpleType = "TOKEN";
                        final String type = p6eMarkEntity.getP6eMarkKeyValue().getResponseType();
                        if (simpleType.equals(type.toUpperCase())) {
                            // 如果为简化模式对返回的数据进行一下修改
                            p6eTokenEntity.delRefreshToken();
                            p6eTokenEntity.setAccessTokenExpirationTime(simpleDateTime);
                            p6eLoginDto.setRefreshToken(null);
                            p6eLoginDto.setExpiresIn(simpleDateTime);
                            // 写入日志数据
                            new P6eLogEntity(new P6eOauth2LogDb()
                                    .setCid(cid)
                                    .setUid(uid)
                                    .setType("UID_TO_CID_LOGIN_TO_TOKEN_TYPE")
                            ).create();
                        } else {
                            // 写入日志数据
                            new P6eLogEntity(new P6eOauth2LogDb()
                                    .setCid(cid)
                                    .setUid(uid)
                                    .setType("UID_TO_CID_LOGIN_TO_CODE_TYPE")
                            ).create();
                        }
                    } catch (Exception e) {
                        throw new Exception(e);
                    } finally {
                        p6eMarkEntity.clean();
                        p6eVoucherEntity.clean();
                    }
                } else {
                    p6eLoginDto.setError(P6eModel.Error.ACCOUNT_OR_PASSWORD);
                }
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            p6eLoginDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
        } catch (Exception ee) {
            LOGGER.error(ee.getMessage());
            p6eLoginDto.setError(P6eModel.Error.SERVICE_EXCEPTION);
        }
        return p6eLoginDto;
    }

    @Override
    public P6eUrlLoginDto qqInfo(String mark, String display) {
        final P6eUrlLoginDto p6eUrlLoginDto = new P6eUrlLoginDto();
        try {
            if (mark == null) {
                p6eUrlLoginDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
            } else {
                // 判断 mark 是否存在
                new P6eMarkEntity(mark);
                // 读取配置信息
                final P6eOauth2Config.QQ qq = p6eConfig.getOauth2().getQq();
                final P6eStateEntity p6eStateEntity =
                        new P6eStateEntity(P6eGeneratorUtil.uuid(), mark, P6eStateEntity.QQ_TYPE).cache();
                // 拼接 QQ 授权的 URL
                String url = qq.getAuthUrl()
                        + "?response_type=" + dataFormatting(qq.getResponseType())
                        + "&client_id=" + dataFormatting(qq.getClientId())
                        + "&redirect_uri=" + dataFormatting(qq.getRedirectUri())
                        + "&state=" + dataFormatting(p6eStateEntity.getState())
                        + "&scope=" + dataFormatting(qq.getScope());
                // 写入display参数
                if (display != null) {
                    url += "&display=" + dataFormatting(display);
                }
                // 写入数据
                p6eUrlLoginDto.setUrl(url);
            }
        } catch (RuntimeException e) {
            LOGGER.error(e.getMessage());
            p6eUrlLoginDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
        } catch (Exception ee) {
            LOGGER.error(ee.getMessage());
            p6eUrlLoginDto.setError(P6eModel.Error.SERVICE_EXCEPTION);
        }
        return p6eUrlLoginDto;
    }

    @Override
    public P6eLoginDto qqLogin(P6eQqLoginDto param) {
        final P6eLoginDto p6eLoginDto = new P6eLoginDto();
        try {
            if (param == null || param.getCode() == null || param.getState() == null) {
                p6eLoginDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
            } else {
                // 读取配置信息
                final P6eOauth2Config.QQ qq = p6eConfig.getOauth2().getQq();
                // 获取用户在 QQ 的 ACCESS TOKEN
                final String tokenUrl = qq.getTokenUrl()
                        + "?grant_type=" + dataFormatting(qq.getGrantType())
                        + "&client_id=" + dataFormatting(qq.getClientId())
                        + "&client_secret=" + dataFormatting(qq.getClientSecret())
                        + "&code=" + dataFormatting(param.getCode())
                        + "&redirect_uri=" + dataFormatting(qq.getRedirectUri())
                        + "&fmt=json";
                // 发送请求
                final String tokenContent = P6eHttpUtil.doGet(tokenUrl);
                if (tokenContent != null) {
                    final String tokenError = "error";
                    final String tokenAccessToken = "access_token";
                    final Map<String, String> tokenMap =
                            P6eJsonUtil.fromJsonToMap(tokenContent, String.class, String.class);
                    if (tokenMap != null && tokenMap.get(tokenError) == null) {
                        final String infoOpenId = "openid";
                        // 获取用户的信息
                        final String infoUrl = qq.getInfoUrl()
                                + "?access_token=" + tokenMap.get(tokenAccessToken)
                                + "&fmt=json";
                        // 发送请求
                        final String infoContent = P6eHttpUtil.doGet(infoUrl);
                        final Map<String, String> infoMap =
                                P6eJsonUtil.fromJsonToMap(infoContent, String.class, String.class);
                        if (infoMap != null && infoMap.get(infoOpenId) != null) {
                            // QQ 认证回调后执行
                            P6eMarkEntity p6eMarkEntity = null;
                            P6eStateEntity p6eStateEntity = null;
                            try {
                                // 读取 mark 信息
                                p6eStateEntity = new P6eStateEntity(param.getState(), P6eStateEntity.QQ_TYPE);
                                // 读取客户端信息
                                p6eMarkEntity = new P6eMarkEntity(p6eStateEntity.getMark());
                                // 创建缓存
                                final P6eTokenEntity p6eTokenEntity = new P6eUserEntity(
                                        new P6eUserEntity.Qq(infoMap.get(infoOpenId))).createTokenCache();
                                // 写入返回数据 CODE
                                p6eLoginDto.setCode(p6eTokenEntity.getKey());
                                // 写入用户认证信息
                                P6eCopyUtil.run(p6eTokenEntity.getModel(), p6eLoginDto);
                                // 写入客户端信息
                                P6eCopyUtil.run(p6eMarkEntity.getP6eMarkKeyValue(), p6eLoginDto);
                                // 简化模式修改过期时间
                                final long simpleDateTime = 120;
                                final String simpleType = "TOKEN";
                                final String type = p6eMarkEntity.getP6eMarkKeyValue().getResponseType();
                                // CID / UID
                                int cid = p6eMarkEntity.getP6eMarkKeyValue().getId();
                                int uid = Integer.parseInt(p6eTokenEntity.getValue().get("id"));
                                if (simpleType.equals(type.toUpperCase())) {
                                    // 如果为简化模式对返回的数据进行一下修改
                                    p6eTokenEntity.delRefreshToken();
                                    p6eTokenEntity.setAccessTokenExpirationTime(simpleDateTime);
                                    p6eLoginDto.setRefreshToken(null);
                                    p6eLoginDto.setExpiresIn(simpleDateTime);
                                    // 写入日志数据
                                    new P6eLogEntity(new P6eOauth2LogDb()
                                            .setCid(cid)
                                            .setUid(uid)
                                            .setType("UID_TO_CID_QQ_LOGIN_TO_TOKEN_TYPE")
                                    ).create();
                                } else {
                                    // 写入日志数据
                                    new P6eLogEntity(new P6eOauth2LogDb()
                                            .setCid(cid)
                                            .setUid(uid)
                                            .setType("UID_TO_CID_QQ_LOGIN_TO_CODE_TYPE")
                                    ).create();
                                }
                            } catch (Exception e) {
                                throw new Exception(e);
                            } finally {
                                if (p6eMarkEntity != null) {
                                    p6eMarkEntity.clean();
                                }
                                if (p6eStateEntity != null) {
                                    p6eStateEntity.clean();
                                }
                            }
                        } else {
                            p6eLoginDto.setError(P6eModel.Error.SERVICE_EXCEPTION);
                        }
                    } else {
                        p6eLoginDto.setError(P6eModel.Error.SERVICE_EXCEPTION);
                    }
                } else {
                    p6eLoginDto.setError(P6eModel.Error.SERVICE_EXCEPTION);
                }
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            p6eLoginDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
        } catch (Exception ee) {
            LOGGER.error(ee.getMessage());
            p6eLoginDto.setError(P6eModel.Error.SERVICE_EXCEPTION);
        }
        return p6eLoginDto;
    }

    @Override
    public P6eUrlLoginDto weChatInfo(String mark, String display) {
        // 同 QQ 登录
        // 微信授权直接 GG
        return null;
    }

    @Override
    public P6eLoginDto weChatLogin(P6eWeChatLoginDto param) {
        // 同 QQ 登陆
        // 微信授权直接 GG
        return null;
    }

    @Override
    public P6eGenerateCodeLoginDto generateCode(String mark) {
        final P6eGenerateCodeLoginDto p6eGenerateCodeLoginDto = new P6eGenerateCodeLoginDto().setMark(mark);
        try {
            if (mark == null) {
                p6eGenerateCodeLoginDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
            } else {
                // 写入 mark 缓存
                final String key = new P6eCodeEntity().create(mark).cache().getKey();
                // 写入 CODE 数据
                p6eGenerateCodeLoginDto.setCode(key);
            }
        }catch (RuntimeException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            p6eGenerateCodeLoginDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
        } catch (Exception ee) {
            LOGGER.error(ee.getMessage());
            p6eGenerateCodeLoginDto.setError(P6eModel.Error.SERVICE_EXCEPTION);
        }
        return p6eGenerateCodeLoginDto;
    }

    @Override
    public P6eLoginDto codeLogin(P6eCodeLoginDto param) {
        final P6eLoginDto p6eLoginDto = new P6eLoginDto();
        try {
            if (param == null || param.getCode() == null || param.getMark() == null || param.getAccessToken() == null) {
                p6eLoginDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
            } else {
                // 读取 MARK 数据
                final String mark = param.getMark();
                final P6eCodeEntity p6eCodeEntity = new P6eCodeEntity(param.getCode()).get();
                // 验证 MARK
                if (p6eCodeEntity.verificationMark(mark)) {
                    // 查询 mark 信息
                    final P6eMarkEntity p6eMarkEntity = new P6eMarkEntity(mark);
                    // 读取 mark 信息内容
                    final P6eMarkKeyValue p6eMarkKeyValue = p6eMarkEntity.getP6eMarkKeyValue();
                    // 查询用户信息并重制模型
                    final P6eTokenEntity p6eTokenEntity =
                            new P6eTokenEntity(param.getAccessToken(), P6eTokenEntity.ACCESS_TOKEN).resetModel();
                    // 写入客户端信息
                    P6eCopyUtil.run(p6eMarkKeyValue, p6eLoginDto);
                    // 写入返回数据 CODE
                    p6eLoginDto.setCode(p6eTokenEntity.getKey());
                    // 写入用户认证信息
                    P6eCopyUtil.run(p6eTokenEntity.getModel(), p6eLoginDto);
                    // CID / UID
                    int cid = p6eMarkKeyValue.getId();
                    int uid = Integer.parseInt(p6eTokenEntity.getValue().get("id"));
                    // 简化模式修改过期时间
                    final long simpleDateTime = 120;
                    final String simpleType = "TOKEN";
                    final String type = p6eMarkKeyValue.getResponseType();
                    if (simpleType.equals(type.toUpperCase())) {
                        // 如果为简化模式对返回的数据进行一下修改
                        p6eTokenEntity.delRefreshToken();
                        p6eTokenEntity.setAccessTokenExpirationTime(simpleDateTime);
                        p6eLoginDto.setRefreshToken(null);
                        p6eLoginDto.setExpiresIn(simpleDateTime);
                        // 写入日志数据
                        new P6eLogEntity(new P6eOauth2LogDb()
                                .setCid(cid)
                                .setUid(uid)
                                .setType("UID_TO_CID_CODE_LOGIN_TO_TOKEN_TYPE")
                        ).create();
                    } else {
                        // 写入日志数据
                        new P6eLogEntity(new P6eOauth2LogDb()
                                .setCid(cid)
                                .setUid(uid)
                                .setType("UID_TO_CID_CODE_LOGIN_TO_CODE_TYPE")
                        ).create();
                    }
                    // 如果不是采用轮训的机制，而是推送的机制，这里可以直接推送数据
                } else {
                    p6eLoginDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
                }
            }
        }catch (RuntimeException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            p6eLoginDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
        } catch (Exception ee) {
            LOGGER.error(ee.getMessage());
            p6eLoginDto.setError(P6eModel.Error.SERVICE_EXCEPTION);
        }
        return p6eLoginDto;
    }

    @Override
    public P6eLoginDto getCodeLogin(String code) {
        final P6eLoginDto p6eLoginDto = new P6eLoginDto();
        try {
            if (code == null) {
                p6eLoginDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
            } else {
                final P6eCodeEntity p6eCodeEntity = new P6eCodeEntity(code).get();
                final P6eCodeKeyValue p6eCodeKeyValue = p6eCodeEntity.getP6eCodeKeyValue();
                if (p6eCodeKeyValue.getMark() == null) {
                    // 写入用户的数据
                    P6eCopyUtil.run(p6eCodeKeyValue, p6eLoginDto);
                    // 删除缓存
                    p6eCodeEntity.clean();
                }
            }
        }catch (RuntimeException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            p6eLoginDto.setError(P6eModel.Error.EXPIRATION_EXCEPTION);
        } catch (Exception ee) {
            LOGGER.error(ee.getMessage());
            p6eLoginDto.setError(P6eModel.Error.SERVICE_EXCEPTION);
        }
        return p6eLoginDto;
    }

    /**
     * 格式化数据
     * @param content 数据内容
     * @return 格式化后的数据内容
     */
    private static String dataFormatting(String content) {
        if (content == null || "".equals(content)) {
            throw new NullPointerException("content cannot be empty.");
        } else {
            try {
                return URLEncoder.encode(content, "UTF-8");
            } catch (Exception e) {
                throw new RuntimeException("content encode error!");
            }
        }
    }

}
