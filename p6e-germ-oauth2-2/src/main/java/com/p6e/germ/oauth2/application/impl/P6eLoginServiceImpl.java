package com.p6e.germ.oauth2.application.impl;

import com.p6e.germ.common.config.P6eConfig;
import com.p6e.germ.common.utils.*;
import com.p6e.germ.oauth2.application.P6eLoginService;
import com.p6e.germ.oauth2.domain.entity.*;
import com.p6e.germ.oauth2.domain.keyvalue.P6eMarkKeyValue;
import com.p6e.germ.oauth2.model.*;
import com.p6e.germ.oauth2.model.db.P6eOauth2LogDb;
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
    private final P6eConfig p6eConfig = P6eSpringUtil.getBean(P6eConfig.class);

    @Override
    public P6eSecretVoucherModel.DtoResult secretVoucher() {
        // 创建凭证对象
        final P6eSecretVoucherModel.DtoResult result = new P6eSecretVoucherModel.DtoResult();
        try {
            // 创建凭证并缓存
            final P6eSecretVoucherEntity p6eVoucherEntity = new P6eSecretVoucherEntity().create().cache();
            // 写入数据
            result.setVoucher(p6eVoucherEntity.getVoucher());
            result.setPublicKey(p6eVoucherEntity.getPublicSecretKey());
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
    public P6eVerificationLoginModel.DtoResult verification(P6eVerificationLoginModel.DtoParam param) {
        // 创建登录信息返回对象
        final P6eVerificationLoginModel.DtoResult result = new P6eVerificationLoginModel.DtoResult();
        try {
            // 查询 mark 信息
            final P6eMarkEntity p6eMarkEntity = P6eMarkEntity.get(param.getMark());
            // 读取 mark 信息内容
            final P6eMarkKeyValue p6eMarkKeyValue = p6eMarkEntity.getData();
            // 查询用户信息并重制模型
            final P6eUserTokenEntity p6eTokenEntity =
                    new P6eUserTokenEntity(param.getAccessToken(), P6eUserTokenEntity.ACCESS_TOKEN).resetModel();
            // 写入客户端信息
            P6eCopyUtil.run(p6eMarkKeyValue, result);
            // 写入用户认证信息
            P6eCopyUtil.run(p6eTokenEntity.getModel(), result);
            // 写入返回数据 CODE
            result.setCode(p6eTokenEntity.getKey());
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
                result.setRefreshToken(null);
                result.setExpiresIn(simpleDateTime);
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
    public P6eLoginModel.AccountPasswordDtoResult accountPassword(P6eLoginModel.AccountPasswordDtoParam param) {
        // 创建登录信息返回对象
        final P6eLoginModel.AccountPasswordDtoResult result = new P6eLoginModel.AccountPasswordDtoResult();
        try {
            // 获取标记信息
            final P6eMarkEntity p6eMarkEntity = P6eMarkEntity.get(param.getMark());
            // 获取凭证信息
            final P6eSecretVoucherEntity p6eVoucherEntity = new P6eSecretVoucherEntity(param.getVoucher()).get();
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
                    final P6eUserTokenEntity p6eTokenEntity = p6eUserEntity.createTokenCache().cache();
                    // 读取 MARK 信息
                    final P6eMarkKeyValue p6eMarkKeyValue = p6eMarkEntity.getData();
                    // 写入返回数据 CODE
                    result.setCode(p6eTokenEntity.getKey());
                    // 写入客户端信息
                    P6eCopyUtil.run(p6eMarkKeyValue, result);
                    // 写入用户认证信息
                    P6eCopyUtil.run(p6eTokenEntity.getModel(), result);
                    // CID / UID
                    int cid = p6eMarkKeyValue.getId();
                    int uid = Integer.parseInt(p6eTokenEntity.getValue().get("id"));
                    // 简化模式修改过期时间
                    final long simpleDateTime = 120;
                    final String simpleType = "TOKEN";
                    final String type = p6eMarkEntity.getData().getResponseType();
                    if (simpleType.equals(type.toUpperCase())) {
                        // 如果为简化模式对返回的数据进行一下修改
                        p6eTokenEntity.delRefreshToken();
                        p6eTokenEntity.setAccessTokenExpirationTime(simpleDateTime);
                        result.setRefreshToken(null);
                        result.setExpiresIn(simpleDateTime);
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
    public P6eUrlLoginDto qqInfo(String mark, String display) {
        return null;
    }

    @Override
    public P6eLoginDto qqLogin(P6eQqLoginDto param) {
        return null;
    }

    @Override
    public P6eUrlLoginDto weChatInfo(String mark) {
        return null;
    }

    @Override
    public P6eLoginDto weChatLogin(P6eWeChatLoginDto param) {
        return null;
    }

    @Override
    public P6eQrCodeModel.DtoResult qrCode(P6eQrCodeModel.DtoParam param) {
        final P6eQrCodeModel.DtoResult result = new P6eQrCodeModel.DtoResult();
        try {
            boolean bool = true;
            try {
                P6eMarkEntity.get(param.getMark());
            } catch (Exception e) {
                bool = false;
                result.setError(P6eResultModel.Error.RESOURCES_NO_EXIST);
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
    public P6eLoginDto codeLogin(P6eCodeLoginDto param) {
        return null;
    }

    @Override
    public P6eLoginDto getCodeLogin(String code) {
        return null;
    }


}
