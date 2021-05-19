package com.p6e.germ.jurisdiction.application.impl;

import com.p6e.germ.common.utils.P6eCopyUtil;
import com.p6e.germ.jurisdiction.application.P6eJurisdictionService;
import com.p6e.germ.jurisdiction.domain.aggregate.P6eJurisdictionListDataAggregate;
import com.p6e.germ.jurisdiction.domain.entity.P6eJurisdictionEntity;
import com.p6e.germ.jurisdiction.domain.entity.P6eJurisdictionGroupEntity;
import com.p6e.germ.jurisdiction.infrastructure.error.P6eDataNoNullRuntimeException;
import com.p6e.germ.jurisdiction.infrastructure.error.P6eDataNullRuntimeException;
import com.p6e.germ.jurisdiction.infrastructure.error.P6eRelationDataException;
import com.p6e.germ.jurisdiction.infrastructure.error.P6eSqlRuntimeException;
import com.p6e.germ.jurisdiction.model.*;
import com.p6e.germ.jurisdiction.model.db.P6eJurisdictionDb;
import com.p6e.germ.jurisdiction.model.db.P6eJurisdictionGroupDb;
import org.springframework.stereotype.Service;

/**
 * @author lidashuang
 * @version 1.0
 */
@Service
public class P6eJurisdictionServiceImpl implements P6eJurisdictionService {

    @Override
    public P6eJurisdictionListModel.DtoResult selectListJurisdictionData(P6eJurisdictionListModel.DtoParam param) {
        final P6eJurisdictionListModel.DtoResult result = new P6eJurisdictionListModel.DtoResult();
        try {
            final P6eJurisdictionListDataAggregate aggregate =
                    P6eJurisdictionListDataAggregate.select(P6eCopyUtil.run(param, P6eJurisdictionDb.class));
            // 写入数据
            P6eCopyUtil.run(aggregate, result);
        } catch (Exception e) {
            result.setError(P6eErrorModel.SERVICE_EXCEPTION).setErrorContent(e.getMessage());
        }
        return result;
    }

    @Override
    public P6eJurisdictionModel.DtoResult selectOneJurisdictionData(P6eJurisdictionModel.DtoParam param) {
        final P6eJurisdictionModel.DtoResult result = new P6eJurisdictionModel.DtoResult();
        try {
            try {
                P6eCopyUtil.run(P6eJurisdictionEntity.select(
                        P6eCopyUtil.run(param, P6eJurisdictionDb.class)).getData(), result);
            } catch (P6eDataNullRuntimeException e) {
                result.setError(P6eErrorModel.RESOURCES_NO_EXIST).setErrorContent(e.getMessage());
            }
        } catch (Exception e) {
            result.setError(P6eErrorModel.SERVICE_EXCEPTION).setErrorContent(e.getMessage());
        }
        return result;
    }

    @Override
    public P6eJurisdictionModel.DtoResult createOneJurisdictionData(P6eJurisdictionModel.DtoParam param) {
        final P6eJurisdictionModel.DtoResult result = new P6eJurisdictionModel.DtoResult();
        try {
            try {
                P6eCopyUtil.run(P6eJurisdictionEntity.create(
                        P6eCopyUtil.run(param, P6eJurisdictionDb.class)).getData(), result);
            } catch (P6eSqlRuntimeException e) {
                result.setError(P6eErrorModel.SQL_EXCEPTION).setErrorContent(e.getMessage());
            } catch (P6eDataNoNullRuntimeException e) {
                result.setError(P6eErrorModel.RESOURCES_EXIST).setErrorContent(e.getMessage());
            }
        } catch (Exception e) {
            result.setError(P6eErrorModel.SERVICE_EXCEPTION).setErrorContent(e.getMessage());
        }
        return result;
    }

    @Override
    public P6eJurisdictionModel.DtoResult deleteOneJurisdictionData(P6eJurisdictionModel.DtoParam param) {
        final P6eJurisdictionModel.DtoResult result = new P6eJurisdictionModel.DtoResult();
        try {
            try {
                P6eCopyUtil.run(P6eJurisdictionEntity.select(
                        P6eCopyUtil.run(param, P6eJurisdictionDb.class)).delete().getData(), result);
            } catch (P6eSqlRuntimeException e) {
                result.setError(P6eErrorModel.SQL_EXCEPTION).setErrorContent(e.getMessage());
            } catch (P6eDataNullRuntimeException e) {
                result.setError(P6eErrorModel.RESOURCES_NO_EXIST).setErrorContent(e.getMessage());
            } catch (P6eRelationDataException e) {
                result.setError(P6eErrorModel.RELATION_DATA_EXCEPTION).setErrorContent(e.getMessage());
            }
        } catch (Exception e) {
            result.setError(P6eErrorModel.SERVICE_EXCEPTION).setErrorContent(e.getMessage());
        }
        return result;
    }

    @Override
    public P6eJurisdictionModel.DtoResult updateOneJurisdictionData(P6eJurisdictionModel.DtoParam param) {
        final P6eJurisdictionModel.DtoResult result = new P6eJurisdictionModel.DtoResult();
        try {
            if (param == null || param.getId() == null || param.getOperate() == null) {
                result.setError(P6eErrorModel.PARAMETER_EXCEPTION);
            } else {
                final P6eJurisdictionDb paramDb = P6eCopyUtil.run(param, P6eJurisdictionDb.class);
                try {
                    // 执行操作
                    final P6eJurisdictionDb resultDb = P6eJurisdictionEntity.select(paramDb).update(paramDb).getData();
                    // 写入数据
                    P6eCopyUtil.run(resultDb, result);
                } catch (P6eSqlRuntimeException e) {
                    result.setError(P6eErrorModel.SQL_EXCEPTION).setErrorContent(e.getMessage());
                } catch (P6eDataNullRuntimeException e) {
                    result.setError(P6eErrorModel.RESOURCES_NO_EXIST).setErrorContent(e.getMessage());
                } catch (P6eDataNoNullRuntimeException e) {
                    result.setError(P6eErrorModel.RESOURCES_EXIST).setErrorContent(e.getMessage());
                }
            }
        } catch (Exception e) {
            result.setError(P6eErrorModel.SERVICE_EXCEPTION).setErrorContent(e.getMessage());
        }
        return result;
    }

    @Override
    public P6eJurisdictionGroupModel.DtoResult selectJurisdictionGroupData(P6eJurisdictionGroupModel.DtoParam param) {
        return null;
    }

    @Override
    public P6eJurisdictionGroupModel.DtoResult selectOneJurisdictionGroupData(P6eJurisdictionGroupModel.DtoParam param) {
        final P6eJurisdictionGroupModel.DtoResult result = new P6eJurisdictionGroupModel.DtoResult();
        try {

        } catch (Exception e) {
            result.setError(P6eErrorModel.SERVICE_EXCEPTION).setErrorContent(e.getMessage());
        }
        return result;
    }

    @Override
    public P6eJurisdictionGroupModel.DtoResult createJurisdictionGroupData(P6eJurisdictionGroupModel.DtoParam param) {
        final P6eJurisdictionGroupModel.DtoResult result = new P6eJurisdictionGroupModel.DtoResult();
        try {

        } catch (Exception e) {
            result.setError(P6eErrorModel.SERVICE_EXCEPTION).setErrorContent(e.getMessage());
        }
        return result;
    }

    @Override
    public P6eJurisdictionGroupModel.DtoResult deleteJurisdictionGroupData(P6eJurisdictionGroupModel.DtoParam param) {
        final P6eJurisdictionGroupModel.DtoResult result = new P6eJurisdictionGroupModel.DtoResult();
        try {

        } catch (Exception e) {
            result.setError(P6eErrorModel.SERVICE_EXCEPTION).setErrorContent(e.getMessage());
        }
        return result;
    }

    @Override
    public P6eJurisdictionGroupModel.DtoResult updateJurisdictionGroupData(P6eJurisdictionGroupModel.DtoParam param) {
        final P6eJurisdictionGroupModel.DtoResult result = new P6eJurisdictionGroupModel.DtoResult();
        try {
            if (param == null || param.getId() == null || param.getOperate() == null) {
                result.setError(P6eErrorModel.PARAMETER_EXCEPTION);
            } else {
                final P6eJurisdictionGroupDb paramDb = P6eCopyUtil.run(param, P6eJurisdictionGroupDb.class);
                try {
                    // 执行操作
                    final P6eJurisdictionGroupDb resultDb = P6eJurisdictionGroupEntity.select(paramDb).update(paramDb).getData();
                    // 写入数据
                    P6eCopyUtil.run(resultDb, result);
                } catch (P6eSqlRuntimeException e) {
                    result.setError(P6eErrorModel.SQL_EXCEPTION).setErrorContent(e.getMessage());
                } catch (P6eDataNullRuntimeException e) {
                    result.setError(P6eErrorModel.RESOURCES_NO_EXIST).setErrorContent(e.getMessage());
                } catch (P6eDataNoNullRuntimeException e) {
                    result.setError(P6eErrorModel.RESOURCES_EXIST).setErrorContent(e.getMessage());
                }
            }
        } catch (Exception e) {
            result.setError(P6eErrorModel.SERVICE_EXCEPTION).setErrorContent(e.getMessage());
        }
        return result;
    }

    @Override
    public P6eJurisdictionRelationGroupModel.DtoResult selectJurisdictionRelationGroup(P6eJurisdictionRelationGroupModel.DtoParam param) {
        return null;
    }

    @Override
    public P6eJurisdictionRelationGroupModel.DtoResult selectOneJurisdictionRelationGroup(P6eJurisdictionRelationGroupModel.DtoParam param) {
        return null;
    }

    @Override
    public P6eJurisdictionRelationGroupModel.DtoResult createJurisdictionRelationGroup(P6eJurisdictionRelationGroupModel.DtoParam param) {
        return null;
    }

    @Override
    public P6eJurisdictionRelationGroupModel.DtoResult deleteJurisdictionRelationGroup(P6eJurisdictionRelationGroupModel.DtoParam param) {
        return null;
    }

}
