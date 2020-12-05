package com.p6e.germ.oauth2.application.impl;

import com.p6e.germ.oauth2.application.P6eLoginService;
import com.p6e.germ.oauth2.domain.entity.*;
import com.p6e.germ.oauth2.domain.keyvalue.P6eMarkKeyValue;
import com.p6e.germ.oauth2.infrastructure.utils.P6eCopyUtil;
import com.p6e.germ.oauth2.model.P6eModel;
import com.p6e.germ.oauth2.model.dto.P6eDefaultLoginDto;
import com.p6e.germ.oauth2.model.dto.P6eLoginDto;
import com.p6e.germ.oauth2.model.dto.P6eVerificationLoginDto;
import com.p6e.germ.oauth2.model.dto.P6eVoucherDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eLoginServiceImpl implements P6eLoginService {

    /** 注入日志系统 */
    private final static Logger LOGGER = LoggerFactory.getLogger(P6eLoginService.class);

    @Override
    public P6eVoucherDto defVoucher() {
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
                // 写入返回数据 CODE
                p6eLoginDto.setCode(p6eTokenEntity.getKey());
                // 写入客户端信息
                P6eCopyUtil.run(p6eMarkKeyValue, p6eLoginDto);
                // 写入用户认证信息
                P6eCopyUtil.run(p6eTokenEntity.getModel(), p6eLoginDto);
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
                }
                // 删除缓存的数据
                p6eMarkEntity.clean();
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
    public P6eLoginDto defaultLogin(P6eDefaultLoginDto login) {
        // 创建登录信息返回对象
        final P6eLoginDto p6eLoginDto = new P6eLoginDto();
        try {
            // 校验参数
            if (login == null
                    || login.getAccount() == null
                    || login.getPassword() == null
                    || login.getMark() == null
                    || login.getVoucher() == null) {
                p6eLoginDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
            } else {
                // 获取标记信息
                final P6eMarkEntity p6eMarkEntity = new P6eMarkEntity(login.getMark());
                // 获取凭证信息
                final P6eVoucherEntity p6eVoucherEntity = new P6eVoucherEntity(login.getVoucher()).get();
                // 获取用户信息
                final P6eUserEntity p6eUserEntity;
                try {
                    // 出现异常就是账号密码错误
                    p6eUserEntity = new P6eUserEntity(login.getAccount());
                } catch (Exception e){
                    p6eLoginDto.setError(P6eModel.Error.ACCOUNT_OR_PASSWORD);
                    return p6eLoginDto;
                }
                // 解密验证账号密码
                if (p6eUserEntity.defaultVerification(p6eVoucherEntity.execute(login.getPassword()))) {
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
    public P6eLoginDto qqLogin() {
        return null;
    }

    @Override
    public P6eLoginDto weChatLogin() {
        return null;
    }

    @Override
    public P6eLoginDto scanCodeLogin() {
        return null;
    }
}