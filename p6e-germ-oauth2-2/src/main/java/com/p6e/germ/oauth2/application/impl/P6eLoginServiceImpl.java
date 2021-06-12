package com.p6e.germ.oauth2.application.impl;

import com.p6e.germ.common.config.P6eConfig;
import com.p6e.germ.common.config.P6eOauth2Config;
import com.p6e.germ.common.utils.*;
import com.p6e.germ.oauth2.Utils;
import com.p6e.germ.oauth2.application.P6eLoginService;
import com.p6e.germ.oauth2.domain.entity.*;
import com.p6e.germ.oauth2.domain.keyvalue.P6eMarkKeyValue;
import com.p6e.germ.oauth2.domain.keyvalue.P6eTokenKeyValue;
import com.p6e.germ.oauth2.domain.keyvalue.P6eUserKeyValue;
import com.p6e.germ.oauth2.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录服务
 * @author lidashuang
 * @version 1.0
 */
public class P6eLoginServiceImpl implements P6eLoginService {

    /** 注入日志系统 */
    private final static Logger LOGGER = LoggerFactory.getLogger(P6eLoginService.class);

    /** 注入配置文件数据 */
    private final P6eOauth2Config config = P6eSpringUtil.getBean(P6eConfig.class).getOauth2();

    @Override
    public P6eSecretVoucherModel.DtoResult secretVoucher(P6eSecretVoucherModel.DtoParam param) {
        // 创建凭证对象
        final P6eSecretVoucherModel.DtoResult result = new P6eSecretVoucherModel.DtoResult();
        try {
            try {
                // 验证是否过期
                P6eMarkEntity.get(param.getMark()).refresh();
            } catch (Exception e) {
                result.setError(P6eResultModel.Error.PAGE_EXPIRED);
                return result;
            }
            // 创建凭证并缓存
            final P6eSecretVoucherEntity secretVoucher = P6eSecretVoucherEntity.create().cache();
            // 写入数据
            result.setVoucher(secretVoucher.getKey());
            result.setPublicKey(secretVoucher.getPublicSecretKey());
        } catch (RuntimeException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            result.setError(P6eResultModel.Error.PARAMETER_EXCEPTION);
        } catch (Exception ee) {
            LOGGER.error(ee.getMessage());
            result.setError(P6eResultModel.Error.SERVICE_EXCEPTION);
        }
        return result;
    }

    @Override
    public P6eApModel.VerificationDtoResult verification(P6eApModel.VerificationDtoParam param) {
        // 创建登录信息返回对象
        final P6eApModel.VerificationDtoResult result = new P6eApModel.VerificationDtoResult();
        try {
            // 查询 mark 信息
            final P6eMarkEntity mark = P6eMarkEntity.get(param.getMark());
            // 读取 mark 信息内容
            final P6eMarkKeyValue.Content content = mark.getData();
            // 查询用户信息并重制模型
            final P6eTokenEntity userToken =
                    P6eTokenEntity.get(new P6eTokenKeyValue.AccessToken(param.getAccessToken())).refresh();
        } catch (RuntimeException e) {
            LOGGER.error(e.getMessage());
            result.setError(P6eResultModel.Error.PARAMETER_EXCEPTION);
        } catch (Exception ee) {
            LOGGER.error(ee.getMessage());
            result.setError(P6eResultModel.Error.SERVICE_EXCEPTION);
        }
        return result;
    }

    @Override
    public P6eApModel.DtoResult accountPassword(P6eApModel.DtoParam param) {
        final P6eApModel.DtoResult result = new P6eApModel.DtoResult();
        try {
            // 获取标记信息
            final P6eMarkEntity mark = P6eMarkEntity.get(param.getMark());
            // 获取凭证信息
            final P6eSecretVoucherEntity secretVoucher = P6eSecretVoucherEntity.get(param.getVoucher());
            // 获取用户信息
            final P6eUserEntity user;
            try {
                // 出现异常就是账号密码错误
                user = P6eUserEntity.get(new P6eUserKeyValue.Account(param.getAccount()));
            } catch (Exception e){
                result.setError(P6eResultModel.Error.ACCOUNT_OR_PASSWORD);
                return result;
            }
            // 解密验证账号密码
            if (user.defaultVerification(secretVoucher.execute(param.getPassword()))) {
                try {
                    // 读取数据
                    final P6eMarkKeyValue.Content mContent = mark.getData();
                    final P6eUserKeyValue.Content uContent = user.getData();
                    // 缓存数据
                    final P6eTokenEntity token = P6eTokenEntity.create(
                            new P6eTokenKeyValue.CodeParam(P6eJsonUtil.toJson(mContent)),
                            new P6eTokenKeyValue.DataParam(uContent.getId().toString(), uContent.toMap())
                    ).cache();
                    // 写入返回数据
                    result.setCode(token.getKey());
                    P6eCopyUtil.run(mContent, result);
                    P6eCopyUtil.run(token.getContent(), result);
                } catch (Exception e) {
                    throw new Exception(e);
                } finally {
                    mark.clean();
                    secretVoucher.clean();
                }
            } else {
                result.setError(P6eResultModel.Error.ACCOUNT_OR_PASSWORD);
            }
        } catch (Exception ee) {
            LOGGER.error(ee.getMessage());
            result.setError(P6eResultModel.Error.PARAMETER_EXCEPTION);
        }
        return result;
    }






    @Override
    public P6eNrCodeModel.DtoResult nrCode(P6eNrCodeModel.DtoParam param) {
        final P6eNrCodeModel.DtoResult result = new P6eNrCodeModel.DtoResult();
        try {
            // 判断其他登录是否启动
            if (config.getNrCode().isEnable()) {
                try {
                    // 验证参数是否存在且刷新过期时间
                    P6eMarkEntity.get(param.getMark()).refresh();
                } catch (Exception e) {
                    result.setError(P6eResultModel.Error.PAGE_EXPIRED);
                    return result;
                }
                // 读取参数
                final String ip = param.getIp();
                final String account = param.getAccount();
                // 验证账号存不存在
                try {
                    P6eUserEntity.get(new P6eUserKeyValue.Account(account));
                } catch (Exception e) {
                    result.setError(P6eResultModel.Error.ACCOUNT_NOT_EXIST);
                    return result;
                }
                // 创建且推送消息并缓存
                final P6eNrCodeEntity nrCode = P6eNrCodeEntity.create(account, ip).push().cache();
                // 写入返回的数据
                result.setAccount(account);
                result.setContent(nrCode.getKey());
            } else {
                result.setError(P6eResultModel.Error.SERVICE_NOT_ENABLE);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            result.setError(P6eResultModel.Error.SERVICE_EXCEPTION);
        }
        return result;
    }

    @Override
    public P6eNrCodeModel.DtoResult nrCodeLogin(P6eNrCodeModel.DtoParam param) {
        final P6eNrCodeModel.DtoResult result = new P6eNrCodeModel.DtoResult();
        try {
            // 判断其他登录是否启动
            if (config.getNrCode().isEnable()) {
                // 读取参数
                final String account = param.getAccount();
                final String codeKey = param.getCodeKey();
                final String codeContent = param.getCodeContent();
                // 验证验证码是否正确
                final P6eNrCodeEntity nrCode = P6eNrCodeEntity.get(codeKey);
                if (nrCode.verification(account, codeContent)) {
                    // 读取缓存的信息并删除缓存
                    final P6eMarkKeyValue.Content mContent;
                    try {
                        mContent = P6eMarkEntity.get(param.getMark()).clean().getData();
                    } catch (Exception e) {
                        result.setError(P6eResultModel.Error.OTHER_LOGIN_STATE_NOT_EXIST);
                        return result;
                    } finally {
                        // 删除验证码
                        nrCode.clean();
                    }
                    try {
                        // 创建用户对象
                        final P6eUserKeyValue.Content uContent = P6eUserEntity.get(new P6eUserKeyValue.Account(account)).getData();
                        // 创建用户认证信息并缓存
                        final P6eTokenEntity token = P6eTokenEntity.create(
                                new P6eTokenKeyValue.CodeParam(P6eJsonUtil.toJson(mContent)),
                                new P6eTokenKeyValue.DataParam(uContent.getId().toString(), uContent.toMap())
                        ).cache();
                        // 写入返回数据
                        result.setCode(token.getKey());
                        P6eCopyUtil.run(mContent, result);
                        P6eCopyUtil.run(token.getContent(), result);
                    } catch (Exception e) {
                        result.setError(P6eResultModel.Error.ACCOUNT_NOT_EXIST);
                        return result;
                    }
                } else {
                    result.setError(P6eResultModel.Error.VERIFICATION_CODE_EXCEPTION);
                }
            } else {
                result.setError(P6eResultModel.Error.SERVICE_NOT_ENABLE);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            result.setError(P6eResultModel.Error.VERIFICATION_CODE_EXCEPTION);
        }
        return result;
    }




    @Override
    public P6eQrCodeModel.DtoResult qrCode(P6eQrCodeModel.DtoParam param) {
        final P6eQrCodeModel.DtoResult result = new P6eQrCodeModel.DtoResult();
        try {
            // 判断其他登录是否启动
            if (config.getQrCode().isEnable()) {
                final String mark = param.getMark();
                try {
                    // 验证参数是否存在且刷新过期时间
                    P6eMarkEntity.get(mark).refresh();
                } catch (Exception e) {
                    result.setError(P6eResultModel.Error.PAGE_EXPIRED);
                    return result;
                }
                // 写入二维码的数据
                result.setContent(Utils.variableFormatting(config.getQrCode().getUrl(), new String[]{
                        "code", (P6eQrCodeEntity.create(mark).cache().getKey()), "mark", mark }));
            } else {
                result.setError(P6eResultModel.Error.SERVICE_NOT_ENABLE);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            result.setError(P6eResultModel.Error.SERVICE_EXCEPTION);
        }
        return result;
    }

    @Override
    public P6eQrCodeModel.DtoResult qrCodeData(P6eQrCodeModel.DtoParam param) {
        final P6eQrCodeModel.DtoResult result = new P6eQrCodeModel.DtoResult();
        try {
            // 判断其他登录是否启动
            if (config.getQrCode().isEnable()) {
                try {
                    // 验证参数是否存在
                    P6eMarkEntity.get(param.getMark());
                } catch (Exception e) {
                    result.setError(P6eResultModel.Error.PAGE_EXPIRED);
                    return result;
                }
                try {
                    // 写入读取的缓存数据内容
                    final P6eQrCodeEntity qrCode = P6eQrCodeEntity.get(param.getCode());
                    if (qrCode.verificationMark(param.getMark())) {
                        try {
                            final String value = qrCode.getValue();
                            if (value != null) {
                                final P6eQrCodeModel.DtoResult r = P6eJsonUtil.fromJson(value, P6eQrCodeModel.DtoResult.class);
                                P6eCopyUtil.run(r, result);
                            }
                        } finally {
                            qrCode.clean();
                        }
                    } else {
                        result.setError(P6eResultModel.Error.PARAMETER_EXCEPTION);
                    }
                } catch (Exception e) {
                    result.setError(P6eResultModel.Error.RESOURCES_NO_EXIST);
                    return result;
                }
            } else {
                result.setError(P6eResultModel.Error.SERVICE_NOT_ENABLE);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            result.setError(P6eResultModel.Error.SERVICE_EXCEPTION);
        }
        return result;
    }

    @Override
    public P6eQrCodeModel.DtoResult qrCodeLogin(P6eQrCodeModel.DtoParam param) {
        final P6eQrCodeModel.DtoResult result = new P6eQrCodeModel.DtoResult();
        try {
            // 判断其他登录是否启动
            if (config.getQrCode().isEnable()) {
                final P6eMarkKeyValue.Content mContent;
                try {
                    // 读取缓存的信息并删除缓存
                    mContent = P6eMarkEntity.get(param.getMark()).clean().getData();
                } catch (Exception e) {
                    result.setError(P6eResultModel.Error.PAGE_EXPIRED);
                    return result;
                }
                try {
                    // 读取来源信息
                    final String accessToken = param.getAccessToken();
                    final Map<String, String> uMap = P6eTokenEntity.get(new P6eTokenKeyValue.AccessToken(accessToken)).getData();
                    final String id = uMap.get("id");
                    // 创建用户认证信息并缓存
                    final P6eTokenEntity token = P6eTokenEntity.create(
                            new P6eTokenKeyValue.CodeParam(P6eJsonUtil.toJson(mContent)),
                            new P6eTokenKeyValue.DataParam(id, uMap)
                    ).cache();
                    result.setCode(token.getKey());
                    P6eCopyUtil.run(mContent, result);
                    P6eCopyUtil.run(token.getContent(), result);
                    P6eQrCodeEntity.get(param.getCode()).setValue(P6eJsonUtil.toJson(result));
                } catch (Exception e) {
                    result.setError(P6eResultModel.Error.RESOURCES_NO_EXIST);
                    return result;
                }
            } else {
                result.setError(P6eResultModel.Error.SERVICE_NOT_ENABLE);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            result.setError(P6eResultModel.Error.SERVICE_EXCEPTION);
        }
        return result;
    }




    @Override
    public P6eOtherLoginModel.DtoResult qqInfo(P6eOtherLoginModel.DtoParam param) {
        final P6eOtherLoginModel.DtoResult result = new P6eOtherLoginModel.DtoResult();
        try {
            // 判断其他登录是否启动
            if (config.getQq().isEnable()) {
                try {
                    // 验证参数是否存在且刷新过期时间
                    P6eMarkEntity.get(param.getMark()).refresh();
                } catch (Exception e) {
                    result.setError(P6eResultModel.Error.PAGE_EXPIRED);
                    return result;
                }
                // 创建其它登录获取认证地址并返回写入
                result.setContent(P6eOtherQqLoginEntity.create(param.getMark()).getAuthUrl());
            } else {
                result.setError(P6eResultModel.Error.SERVICE_NOT_ENABLE);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            result.setError(P6eResultModel.Error.SERVICE_EXCEPTION);
        }
        return result;
    }

    @Override
    public P6eOtherLoginModel.DtoResult qqLogin(P6eOtherLoginModel.DtoParam param) {
        final P6eOtherLoginModel.DtoResult result = new P6eOtherLoginModel.DtoResult();
        try {
            // 判断其他登录是否启动
            if (config.getQq().isEnable()) {
                // 判断回调参数是否合法
                final String m = P6eOtherQqLoginEntity.getMark(param.getState());
                if (m == null) {
                    result.setError(P6eResultModel.Error.OTHER_LOGIN_STATE_NOT_EXIST);
                } else {
                    // 读取缓存的信息并删除缓存
                    final P6eMarkKeyValue.Content mContent;
                    try {
                        mContent = P6eMarkEntity.get(m).clean().getData();
                    } catch (Exception e) {
                        result.setError(P6eResultModel.Error.PAGE_EXPIRED);
                        return result;
                    }
                    // 获取用户的信息
                    final Map<String, String> u = P6eOtherQqLoginEntity.getUser(param.getCode());
                    // 创建用户对象
                    final P6eUserKeyValue.Content uContent =
                            P6eUserEntity.get(new P6eUserKeyValue.Qq(u.get("openid"))).getData();
                    // 创建用户认证信息并缓存
                    final P6eTokenEntity token = P6eTokenEntity.create(
                                    new P6eTokenKeyValue.CodeParam(P6eJsonUtil.toJson(mContent)),
                                    new P6eTokenKeyValue.DataParam(uContent.getId().toString(), uContent.toMap())
                            ).cache();
                    // 写入需要返回的信息
                    result.setCode(token.getKey());
                    P6eCopyUtil.run(mContent, result);
                    P6eCopyUtil.run(token.getContent(), result);
                }
            } else {
                result.setError(P6eResultModel.Error.SERVICE_NOT_ENABLE);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            result.setError(P6eResultModel.Error.SERVICE_EXCEPTION);
        }
        return result;
    }

    @Override
    public P6eOtherLoginModel.DtoResult weChatInfo(P6eOtherLoginModel.DtoParam param) {
        final P6eOtherLoginModel.DtoResult result = new P6eOtherLoginModel.DtoResult();
        try {
            // 判断其他登录是否启动
            if (config.getWeChat().isEnable()) {
                try {
                    // 验证参数是否存在且刷新过期时间
                    P6eMarkEntity.get(param.getMark()).refresh();
                } catch (Exception e) {
                    result.setError(P6eResultModel.Error.PAGE_EXPIRED);
                    return result;
                }
                // 创建其它登录获取认证地址并返回写入
                result.setContent(P6eOtherWeChatLoginEntity.create(param.getMark()).getAuthUrl());
            } else {
                result.setError(P6eResultModel.Error.SERVICE_NOT_ENABLE);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            result.setError(P6eResultModel.Error.SERVICE_EXCEPTION);
        }
        return result;
    }

    @Override
    public P6eOtherLoginModel.DtoResult weChatLogin(P6eOtherLoginModel.DtoParam param) {
        final P6eOtherLoginModel.DtoResult result = new P6eOtherLoginModel.DtoResult();
        try {
            // 判断其他登录是否启动
            if (config.getWeChat().isEnable()) {
                // 判断回调参数是否合法
                final String m = P6eOtherWeChatLoginEntity.getMark(param.getState());
                if (m == null) {
                    result.setError(P6eResultModel.Error.OTHER_LOGIN_STATE_NOT_EXIST);
                } else {
                    // 读取缓存的信息并删除缓存
                    final P6eMarkKeyValue.Content mContent;
                    try {
                        mContent = P6eMarkEntity.get(m).clean().getData();
                    } catch (Exception e) {
                        result.setError(P6eResultModel.Error.OTHER_LOGIN_STATE_NOT_EXIST);
                        return result;
                    }
                    // 获取用户的信息
                    final Map<String, String> u = P6eOtherWeChatLoginEntity.getUser(param.getCode());
                    // 创建用户对象
                    final P6eUserKeyValue.Content uContent =
                            P6eUserEntity.get(new P6eUserKeyValue.WeChat(u.get("openid"))).getData();
                    // 创建用户认证信息并缓存
                    final P6eTokenEntity token = P6eTokenEntity.create(
                            new P6eTokenKeyValue.CodeParam(P6eJsonUtil.toJson(mContent)),
                            new P6eTokenKeyValue.DataParam(uContent.getId().toString(), uContent.toMap())
                    ).cache();
                    // 写入需要返回的信息
                    result.setCode(token.getKey());
                    P6eCopyUtil.run(mContent, result);
                    P6eCopyUtil.run(token.getContent(), result);
                }
            } else {
                result.setError(P6eResultModel.Error.SERVICE_NOT_ENABLE);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            result.setError(P6eResultModel.Error.SERVICE_EXCEPTION);
        }
        return result;
    }

    @Override
    public P6eOtherLoginModel.DtoResult sinaInfo(P6eOtherLoginModel.DtoParam param) {
        final P6eOtherLoginModel.DtoResult result = new P6eOtherLoginModel.DtoResult();
        try {
            // 判断其他登录是否启动
            if (config.getSina().isEnable()) {
                try {
                    // 验证参数是否存在且刷新过期时间
                    P6eMarkEntity.get(param.getMark()).refresh();
                } catch (Exception e) {
                    result.setError(P6eResultModel.Error.PAGE_EXPIRED);
                    return result;
                }
                // 创建其它登录获取认证地址并返回写入
                result.setContent(P6eOtherSinaLoginEntity.create(param.getMark()).getAuthUrl());
            } else {
                result.setError(P6eResultModel.Error.SERVICE_NOT_ENABLE);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            result.setError(P6eResultModel.Error.SERVICE_EXCEPTION);
        }
        return result;
    }

    @Override
    public P6eOtherLoginModel.DtoResult sinaLogin(P6eOtherLoginModel.DtoParam param) {
        final P6eOtherLoginModel.DtoResult result = new P6eOtherLoginModel.DtoResult();
        try {
            // 判断其他登录是否启动
            if (config.getSina().isEnable()) {
                // 判断回调参数是否合法
                final String m = P6eOtherSinaLoginEntity.getMark(param.getState());
                if (m == null) {
                    result.setError(P6eResultModel.Error.OTHER_LOGIN_STATE_NOT_EXIST);
                } else {
                    // 读取缓存的信息并删除缓存
                    final P6eMarkKeyValue.Content mContent;
                    try {
                        mContent = P6eMarkEntity.get(m).clean().getData();
                    } catch (Exception e) {
                        result.setError(P6eResultModel.Error.OTHER_LOGIN_STATE_NOT_EXIST);
                        return result;
                    }
                    // 获取用户的信息
                    final Map<String, String> u = P6eOtherSinaLoginEntity.getUser(param.getCode());
                    // 创建用户对象
                    final P6eUserKeyValue.Content uContent =
                            P6eUserEntity.get(new P6eUserKeyValue.Sina(u.get("openid"))).getData();
                    // 创建用户认证信息并缓存
                    final P6eTokenEntity token = P6eTokenEntity.create(
                            new P6eTokenKeyValue.CodeParam(P6eJsonUtil.toJson(mContent)),
                            new P6eTokenKeyValue.DataParam(uContent.getId().toString(), uContent.toMap())
                    ).cache();
                    // 写入需要返回的信息
                    result.setCode(token.getKey());
                    P6eCopyUtil.run(mContent, result);
                    P6eCopyUtil.run(token.getContent(), result);
                }
            } else {
                result.setError(P6eResultModel.Error.SERVICE_NOT_ENABLE);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            result.setError(P6eResultModel.Error.SERVICE_EXCEPTION);
        }
        return result;
    }

}
