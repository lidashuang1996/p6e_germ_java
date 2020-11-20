package com.p6e.germ.oauth2.application;

import com.p6e.germ.oauth2.context.controller.support.model.P6eAuthModelParam;
import com.p6e.germ.oauth2.context.controller.support.model.P6eAuthModelResult;
import com.p6e.germ.oauth2.context.controller.support.model.P6eModelConfig;
import com.p6e.germ.oauth2.context.controller.support.model.P6eTokenModelParam;
import com.p6e.germ.oauth2.domain.aggregate.P6eCodeModeAggregate;
import com.p6e.germ.oauth2.domain.keyvalue.P6eAuthKeyValue;
import com.p6e.germ.oauth2.domain.keyvalue.P6eCodeModeKeyValueParam;
import com.p6e.germ.oauth2.domain.keyvalue.P6eCodeAuthKeyValue;
import com.p6e.germ.oauth2.infrastructure.exception.ParamException;
import com.p6e.germ.oauth2.infrastructure.utils.CopyUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
@Service
public class P6eAuthService {

    /**
     * 执行 code 模式
     * @param param 请求参数
     * @return 处理的结果
     */
    public P6eAuthModelResult codeMode(P6eAuthModelParam param) {
        final P6eAuthModelResult p6eAuthModelResult = new P6eAuthModelResult();
        try {
            final P6eCodeAuthKeyValue p6eCodeModeKeyValue = P6eCodeModeAggregate.create().generate(
                    new P6eAuthKeyValue(
                            param.getClient_id(),
                            param.getScope(),
                            param.getState(),
                            param.getResponse_type(),
                            param.getRedirect_uri()
                    ));
            CopyUtil.run(p6eCodeModeKeyValue, p6eAuthModelResult);
        } catch (ParamException | NullPointerException e) {
            e.printStackTrace();
            p6eAuthModelResult.setError(P6eModelConfig.ERROR_OAUTH2_PARAM_EXCEPTION);
        }
        return p6eAuthModelResult;
    }

    public Map<String, String> codeModeExecute(P6eTokenModelParam param) {
        try {
            return P6eCodeModeAggregate.create().execute(
                    param.getCode(),
                    param.getRedirect_uri(),
                    param.getClient_id(),
                    param.getClient_secret()
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
