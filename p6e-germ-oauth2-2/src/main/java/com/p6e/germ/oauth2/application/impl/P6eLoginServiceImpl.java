package com.p6e.germ.oauth2.application.impl;

import com.p6e.germ.common.config.P6eConfig;
import com.p6e.germ.common.config.P6eOauth2Config;
import com.p6e.germ.common.utils.*;
import com.p6e.germ.oauth2.application.P6eLoginService;
import com.p6e.germ.oauth2.domain.entity.*;
import com.p6e.germ.oauth2.domain.keyvalue.P6eMarkKeyValue;
import com.p6e.germ.oauth2.model.*;
import com.p6e.germ.oauth2.model.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
                P6eMarkEntity.get(param.getMark());
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
    public P6eLoginModel.VerificationDtoResult verification(P6eLoginModel.VerificationDtoParam param) {
        // 创建登录信息返回对象
        final P6eLoginModel.VerificationDtoResult result = new P6eLoginModel.VerificationDtoResult();
        try {
            // 查询 mark 信息
            final P6eMarkEntity mark = P6eMarkEntity.get(param.getMark());
            // 读取 mark 信息内容
            final P6eMarkKeyValue.Content content = mark.getData();
            // 查询用户信息并重制模型
            final P6eUserTokenEntity userToken =
                    new P6eUserTokenEntity(param.getAccessToken(), P6eUserTokenEntity.ACCESS_TOKEN).resetModel();
            result(content, userToken, result);
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
    public P6eLoginModel.AccountPasswordDtoResult accountPassword(P6eLoginModel.AccountPasswordDtoParam param) {
        // 创建登录信息返回对象
        final P6eLoginModel.AccountPasswordDtoResult result = new P6eLoginModel.AccountPasswordDtoResult();
        try {
            // 获取标记信息
            final P6eMarkEntity p6eMarkEntity = P6eMarkEntity.get(param.getMark());
            // 获取凭证信息
            final P6eSecretVoucherEntity p6eVoucherEntity = P6eSecretVoucherEntity.get(param.getVoucher());
            // 获取用户信息
            final P6eUserEntity p6eUserEntity;
            try {
                // 出现异常就是账号密码错误
                p6eUserEntity = new P6eUserEntity(new P6eUserEntity.Account(param.getAccount()));
            } catch (Exception e){
                result.setError(P6eResultModel.Error.ACCOUNT_OR_PASSWORD);
                return result;
            }
            // 解密验证账号密码
            if (p6eUserEntity.defaultVerification(p6eVoucherEntity.execute(param.getPassword()))) {
                try {
                    // 创建用户认证信息并缓存
                    final P6eUserTokenEntity userToken = p6eUserEntity.createTokenCache().cache();
                    // 读取 MARK 信息
                    final P6eMarkKeyValue.Content content = p6eMarkEntity.getData();
                    result(content, userToken, result);
                } catch (Exception e) {
                    throw new Exception(e);
                } finally {
                    p6eMarkEntity.clean();
                    p6eVoucherEntity.clean();
                }
            } else {
                result.setError(P6eResultModel.Error.ACCOUNT_OR_PASSWORD);
            }
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

    /**
     *
     */
    private void result(P6eMarkKeyValue.Content content, P6eUserTokenEntity userToken, P6eLoginModel.DtoResult result) {
        // 写入客户端信息
        P6eCopyUtil.run(content, result);
        // 写入用户认证信息
        P6eCopyUtil.run(userToken.getModel(), result);
        // 写入返回数据 CODE
        result.setCode(userToken.getKey());
        // CID / UID
        int cid = content.getId();
        int uid = Integer.parseInt(userToken.getValue().get("id"));
        // 简化模式修改过期时间
        final long simpleDateTime = 120;
        final String simpleType = "TOKEN";
        final String type = content.getResponseType();
        if (simpleType.equalsIgnoreCase(type)) {
            // 如果为简化模式对返回的数据进行一下修改
            userToken.delRefreshToken();
            userToken.setAccessTokenExpirationTime(simpleDateTime);
            result.setRefreshToken(null);
            result.setExpiresIn(simpleDateTime);
        }
    }

    @Override
    public String smsInfo(P6eSmsInfoDto param) {
        return null;
    }

    @Override
    public P6eLoginDto smsCodeLogin(P6eSmsCodeLoginDto param) {
        return null;
    }

    @Override
    public String emailInfo(P6eEmailInfoDto param) {
        return null;
    }

    @Override
    public P6eLoginDto emailCodeLogin(P6eEmailCodeLoginDto param) {
        return null;
    }

    @Override
    public P6eOtherLoginModel.DtoResult qqInfo(P6eOtherLoginModel.DtoParam param) {
        final P6eOtherLoginModel.DtoResult result = new P6eOtherLoginModel.DtoResult();
        try {
            if (config.getQq().isEnable()) {
                try {
                    P6eMarkEntity.get(param.getMark());
                } catch (Exception e) {
                    result.setError(P6eResultModel.Error.PAGE_EXPIRED);
                    return result;
                }
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
        if (config.getQq().isEnable()) {

        } else {
            result.setError(P6eResultModel.Error.SERVICE_NOT_ENABLE);
        }
        return result;
    }

    @Override
    public P6eOtherLoginModel.DtoResult weChatInfo(P6eOtherLoginModel.DtoParam param) {
        final P6eOtherLoginModel.DtoResult result = new P6eOtherLoginModel.DtoResult();
        if (config.getWeChat().isEnable()) {
            try {
                P6eMarkEntity.get(param.getMark());
            } catch (Exception e) {
                result.setError(P6eResultModel.Error.PAGE_EXPIRED);
                return result;
            }
            result.setContent(P6eOtherWeChatLoginEntity.create(param.getMark()).getAuthUrl());
        } else {
            result.setError(P6eResultModel.Error.SERVICE_NOT_ENABLE);
        }
        return result;
    }

    @Override
    public P6eLoginDto weChatLogin(P6eWeChatLoginDto param) {
        final P6eOtherLoginModel.DtoResult result = new P6eOtherLoginModel.DtoResult();
        if (config.getWeChat().isEnable()) {

        } else {
            result.setError(P6eResultModel.Error.SERVICE_NOT_ENABLE);
        }
        return null;
    }

    @Override
    public P6eOtherLoginModel.DtoResult sinaInfo(P6eOtherLoginModel.DtoParam param) {
        final P6eOtherLoginModel.DtoResult result = new P6eOtherLoginModel.DtoResult();
        try {
            if (config.getSina().isEnable()) {
                try {
                    P6eMarkEntity.get(param.getMark());
                } catch (Exception e) {
                    result.setError(P6eResultModel.Error.PAGE_EXPIRED);
                    return result;
                }
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

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            result.setError(P6eResultModel.Error.SERVICE_EXCEPTION);
        }
        return result;
    }

    @Override
    public P6eQrCodeModel.DtoResult qrCode(P6eQrCodeModel.DtoParam param) {
        final P6eQrCodeModel.DtoResult result = new P6eQrCodeModel.DtoResult();
        try {
            boolean bool = true;
            try {
                // 验证是否过期
                P6eMarkEntity.get(param.getMark());
            } catch (Exception e) {
                bool = false;
                result.setError(P6eResultModel.Error.PAGE_EXPIRED);
            }
            if (bool) {
                result.setContent("http://lidashuang.com/auth/?code=" + P6eQrCodeEntity.create().cache().getKey());
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            result.setError(P6eResultModel.Error.SERVICE_EXCEPTION);
        }
        return result;
    }

    @Override
    public P6eNrCodeModel.DtoResult nrCode(P6eNrCodeModel.DtoParam param) {
        final P6eNrCodeModel.DtoResult result = new P6eNrCodeModel.DtoResult();
        try {
            try {
                // 验证是否过期
                P6eMarkEntity.get(param.getMark());
            } catch (Exception e) {
                result.setError(P6eResultModel.Error.PAGE_EXPIRED);
                return result;
            }
            // 创建且推送且缓存
            final String ip = param.getIp();
            final String account = param.getAccount();
            final P6eNrCodeEntity nrCode = P6eNrCodeEntity.create(account, ip).push().cache();
            result.setAccount(account);
            result.setContent(nrCode.getKey());
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
            try {
                // 验证是否过期
                P6eMarkEntity.get(param.getMark());
            } catch (Exception e) {
                result.setError(P6eResultModel.Error.PAGE_EXPIRED);
                return result;
            }
            try {
                final String account = param.getAccount();
                final String codeKey = param.getCodeKey();
                final String codeContent = param.getCodeContent();
                if (P6eNrCodeEntity.get(codeKey).verification(account, codeContent)) {
                    // 登录操作
                }
            } catch (Exception e) {
                result.setError(P6eResultModel.Error.VERIFICATION_CODE_EXCEPTION);
                return result;
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            result.setError(P6eResultModel.Error.SERVICE_EXCEPTION);
        }
        return result;
    }

    @Override
    public P6eLoginDto getCodeLogin(String code) {
        return null;
    }


}
