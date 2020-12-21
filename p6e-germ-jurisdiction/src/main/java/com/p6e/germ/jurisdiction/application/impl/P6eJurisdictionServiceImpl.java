package com.p6e.germ.jurisdiction.application.impl;

import com.p6e.germ.jurisdiction.application.P6eJurisdictionService;
import com.p6e.germ.jurisdiction.domain.aggregate.P6eJurisdictionGroupRelationUserAggregate;
import com.p6e.germ.jurisdiction.domain.aggregate.P6eJurisdictionRelationGroupAggregate;
import com.p6e.germ.jurisdiction.domain.entity.P6eJurisdictionEntity;
import com.p6e.germ.jurisdiction.domain.entity.P6eJurisdictionGroupEntity;
import com.p6e.germ.jurisdiction.model.P6eModel;
import com.p6e.germ.jurisdiction.model.db.P6eJurisdictionDb;
import com.p6e.germ.jurisdiction.model.db.P6eJurisdictionGroupDb;
import com.p6e.germ.jurisdiction.model.db.P6eJurisdictionGroupRelationUserDb;
import com.p6e.germ.jurisdiction.model.db.P6eJurisdictionRelationGroupDb;
import com.p6e.germ.jurisdiction.model.dto.P6eJurisdictionGroupRelationUserDto;
import com.p6e.germ.jurisdiction.model.dto.P6eJurisdictionDto;
import com.p6e.germ.jurisdiction.model.dto.P6eJurisdictionGroupDto;
import com.p6e.germ.jurisdiction.model.dto.P6eJurisdictionRelationGroupDto;
import com.p6e.germ.jurisdiction.utils.P6eCopyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author lidashuang
 * @version 1.0
 */
@Service
public class P6eJurisdictionServiceImpl implements P6eJurisdictionService {

    /** 注入日志系统 */
    private final static Logger LOGGER = LoggerFactory.getLogger(P6eJurisdictionService.class);

    @Override
    public P6eJurisdictionDto selectJurisdiction(P6eJurisdictionDto param) {
        final P6eJurisdictionDto p6eJurisdictionDto = new P6eJurisdictionDto();
        try {
            final P6eJurisdictionDb p6eJurisdictionDb =
                    P6eCopyUtil.run(param, P6eJurisdictionDb.class, new P6eJurisdictionDb());
            final P6eJurisdictionEntity p6eJurisdictionEntity = new P6eJurisdictionEntity(p6eJurisdictionDb);
            // 写入查询到的数据
            p6eJurisdictionDto.initPaging(p6eJurisdictionDb);
            p6eJurisdictionDto.setTotal(p6eJurisdictionEntity.count());
            p6eJurisdictionDto.setList(P6eCopyUtil.runList(p6eJurisdictionEntity.select(), P6eJurisdictionDto.class));
        } catch (RuntimeException e) {
            LOGGER.error(e.getMessage());
            p6eJurisdictionDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
        } catch (Exception ee) {
            LOGGER.error(ee.getMessage());
            p6eJurisdictionDto.setError(P6eModel.Error.SERVICE_EXCEPTION);
        }
        return p6eJurisdictionDto;
    }

    @Override
    public P6eJurisdictionDto selectOneJurisdiction(P6eJurisdictionDto param) {
        final P6eJurisdictionDto p6eJurisdictionDto = new P6eJurisdictionDto();
        try {
            if (param == null) {
                p6eJurisdictionDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
            } else {
                final P6eJurisdictionEntity p6eJurisdictionEntity;
                if (param.getId() != null) {
                    p6eJurisdictionEntity = new P6eJurisdictionEntity(param.getId());
                } else if (param.getCode() != null) {
                    p6eJurisdictionEntity = new P6eJurisdictionEntity(param.getCode());
                } else {
                    p6eJurisdictionDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
                    return p6eJurisdictionDto;
                }
                // 写入查询到的数据
                P6eCopyUtil.run(p6eJurisdictionEntity.get(), p6eJurisdictionDto);
            }
        } catch (RuntimeException e) {
            LOGGER.error(e.getMessage());
            p6eJurisdictionDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
        } catch (Exception ee) {
            LOGGER.error(ee.getMessage());
            p6eJurisdictionDto.setError(P6eModel.Error.SERVICE_EXCEPTION);
        }
        return p6eJurisdictionDto;
    }

    @Override
    public P6eJurisdictionDto createJurisdiction(P6eJurisdictionDto param) {
        final P6eJurisdictionDto p6eJurisdictionDto = new P6eJurisdictionDto();
        try {
            if (param == null
                    || param.getCode() == null
                    || param.getName() == null
                    || param.getContent() == null
                    || param.getOperate() == null) {
                p6eJurisdictionDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
            } else {
                // 新增数据
                param.setId(null);
                final P6eJurisdictionEntity p6eJurisdictionEntity =
                        new P6eJurisdictionEntity(P6eCopyUtil.run(param, P6eJurisdictionDb.class));
                P6eCopyUtil.run(p6eJurisdictionEntity.create(), p6eJurisdictionDto);
            }
        } catch (RuntimeException e) {
            LOGGER.error(e.getMessage());
            p6eJurisdictionDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
        } catch (Exception ee) {
            LOGGER.error(ee.getMessage());
            p6eJurisdictionDto.setError(P6eModel.Error.SERVICE_EXCEPTION);
        }
        return p6eJurisdictionDto;
    }

    @Override
    public P6eJurisdictionDto deleteJurisdiction(P6eJurisdictionDto param) {
        final P6eJurisdictionDto p6eJurisdictionDto = new P6eJurisdictionDto();
        try {
            if (param == null) {
                p6eJurisdictionDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
            } else {
                final P6eJurisdictionEntity p6eJurisdictionEntity;
                if (param.getId() != null) {
                    // 删除数据
                    p6eJurisdictionEntity = new P6eJurisdictionEntity(param.getId());
                    P6eCopyUtil.run(p6eJurisdictionEntity.delete(), p6eJurisdictionDto);
                } else if (param.getCode() != null) {
                    // 删除数据
                    p6eJurisdictionEntity = new P6eJurisdictionEntity(param.getCode());
                    P6eCopyUtil.run(p6eJurisdictionEntity.delete(), p6eJurisdictionDto);
                } else {
                    p6eJurisdictionDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
                    return p6eJurisdictionDto;
                }
            }
        } catch (RuntimeException e) {
            LOGGER.error(e.getMessage());
            p6eJurisdictionDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
        } catch (Exception ee) {
            LOGGER.error(ee.getMessage());
            p6eJurisdictionDto.setError(P6eModel.Error.SERVICE_EXCEPTION);
        }
        return p6eJurisdictionDto;
    }

    @Override
    public P6eJurisdictionDto updateJurisdiction(P6eJurisdictionDto param) {
        final P6eJurisdictionDto p6eJurisdictionDto = new P6eJurisdictionDto();
        try {
            if (param == null || param.getId() == null) {
                p6eJurisdictionDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
            } else {
                if (param.getId() != null) {
                    // 更新数据
                    final P6eJurisdictionEntity p6eJurisdictionEntity =
                            new P6eJurisdictionEntity(P6eCopyUtil.run(param, P6eJurisdictionDb.class));
                    P6eCopyUtil.run(p6eJurisdictionEntity.update(), p6eJurisdictionDto);
                } else {
                    p6eJurisdictionDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
                    return p6eJurisdictionDto;
                }
            }
        } catch (RuntimeException e) {
            LOGGER.error(e.getMessage());
            p6eJurisdictionDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
        } catch (Exception ee) {
            LOGGER.error(ee.getMessage());
            p6eJurisdictionDto.setError(P6eModel.Error.SERVICE_EXCEPTION);
        }
        return p6eJurisdictionDto;
    }








    @Override
    public P6eJurisdictionGroupDto selectJurisdictionGroup(P6eJurisdictionGroupDto param) {
        final P6eJurisdictionGroupDto p6eJurisdictionGroupDto = new P6eJurisdictionGroupDto();
        try {
            // 查询数据
            final P6eJurisdictionGroupDb p6eJurisdictionGroupDb =
                    P6eCopyUtil.run(param, P6eJurisdictionGroupDb.class, new P6eJurisdictionGroupDb());
            final P6eJurisdictionGroupEntity p6eJurisdictionGroupEntity = new P6eJurisdictionGroupEntity(p6eJurisdictionGroupDb);
            // 写入数据
            p6eJurisdictionGroupDto.initPaging(p6eJurisdictionGroupDb);
            p6eJurisdictionGroupDto.setTotal(p6eJurisdictionGroupEntity.count());
            p6eJurisdictionGroupDto.setList(P6eCopyUtil.runList(p6eJurisdictionGroupEntity.select(), P6eJurisdictionGroupDto.class));
        }catch (RuntimeException e) {
            LOGGER.error(e.getMessage());
            p6eJurisdictionGroupDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
        } catch (Exception ee) {
            LOGGER.error(ee.getMessage());
            p6eJurisdictionGroupDto.setError(P6eModel.Error.SERVICE_EXCEPTION);
        }
        return p6eJurisdictionGroupDto;
    }

    @Override
    public P6eJurisdictionGroupDto selectOneJurisdictionGroup(P6eJurisdictionGroupDto param) {
        final P6eJurisdictionGroupDto p6eJurisdictionGroupDto = new P6eJurisdictionGroupDto();
        try {
            if (param == null || param.getId() == null) {
                p6eJurisdictionGroupDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
            } else {
                // 查询数据
                final P6eJurisdictionGroupEntity p6eJurisdictionGroupEntity =
                        new P6eJurisdictionGroupEntity(new P6eJurisdictionGroupDb().setId(param.getId()));
                P6eCopyUtil.run(p6eJurisdictionGroupEntity.get(), p6eJurisdictionGroupDto);
            }
        }catch (RuntimeException e) {
            LOGGER.error(e.getMessage());
            p6eJurisdictionGroupDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
        } catch (Exception ee) {
            LOGGER.error(ee.getMessage());
            p6eJurisdictionGroupDto.setError(P6eModel.Error.SERVICE_EXCEPTION);
        }
        return p6eJurisdictionGroupDto;
    }

    @Override
    public P6eJurisdictionGroupDto createJurisdictionGroup(P6eJurisdictionGroupDto param) {
        final P6eJurisdictionGroupDto p6eJurisdictionGroupDto = new P6eJurisdictionGroupDto();
        try {
            if (param == null
                    || param.getName() == null
                    || param.getWeight() == null
                    || param.getOperate() == null) {
                p6eJurisdictionGroupDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
            } else {
                // 创建数据
                param.setId(null);
                final P6eJurisdictionGroupEntity p6eJurisdictionGroupEntity =
                        new P6eJurisdictionGroupEntity(P6eCopyUtil.run(param, P6eJurisdictionGroupDb.class));
                P6eCopyUtil.run(p6eJurisdictionGroupEntity.create(), p6eJurisdictionGroupDto);
            }
        }catch (RuntimeException e) {
            LOGGER.error(e.getMessage());
            p6eJurisdictionGroupDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
        } catch (Exception ee) {
            LOGGER.error(ee.getMessage());
            p6eJurisdictionGroupDto.setError(P6eModel.Error.SERVICE_EXCEPTION);
        }
        return p6eJurisdictionGroupDto;
    }

    @Override
    public P6eJurisdictionGroupDto deleteJurisdictionGroup(P6eJurisdictionGroupDto param) {
        final P6eJurisdictionGroupDto p6eJurisdictionGroupDto = new P6eJurisdictionGroupDto();
        try {
            if (param == null || param.getId() == null) {
                p6eJurisdictionGroupDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
            } else {
                // 删除数据
                final P6eJurisdictionGroupEntity p6eJurisdictionGroupEntity =
                        new P6eJurisdictionGroupEntity(new P6eJurisdictionGroupDb().setId(param.getId()));
                P6eCopyUtil.run(p6eJurisdictionGroupEntity.delete(), p6eJurisdictionGroupDto);
            }
        }catch (RuntimeException e) {
            LOGGER.error(e.getMessage());
            p6eJurisdictionGroupDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
        } catch (Exception ee) {
            LOGGER.error(ee.getMessage());
            p6eJurisdictionGroupDto.setError(P6eModel.Error.SERVICE_EXCEPTION);
        }
        return p6eJurisdictionGroupDto;
    }

    @Override
    public P6eJurisdictionGroupDto updateJurisdictionGroup(P6eJurisdictionGroupDto param) {
        final P6eJurisdictionGroupDto p6eJurisdictionGroupDto = new P6eJurisdictionGroupDto();
        try {
            if (param == null || param.getId() == null) {
                p6eJurisdictionGroupDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
            } else {
                // 修改数据
                final P6eJurisdictionGroupDb p6eJurisdictionGroupDb = P6eCopyUtil.run(param, P6eJurisdictionGroupDb.class);
                final P6eJurisdictionGroupEntity p6eJurisdictionGroupEntity = new P6eJurisdictionGroupEntity(p6eJurisdictionGroupDb);
                P6eCopyUtil.run(p6eJurisdictionGroupEntity.update(), p6eJurisdictionGroupDto);
            }
        }catch (RuntimeException e) {
            LOGGER.error(e.getMessage());
            p6eJurisdictionGroupDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
        } catch (Exception ee) {
            LOGGER.error(ee.getMessage());
            p6eJurisdictionGroupDto.setError(P6eModel.Error.SERVICE_EXCEPTION);
        }
        return p6eJurisdictionGroupDto;
    }

    @Override
    public P6eJurisdictionRelationGroupDto selectJurisdictionRelationGroup(P6eJurisdictionRelationGroupDto param) {
        final P6eJurisdictionRelationGroupDto p6eJurisdictionRelationGroupDto = new P6eJurisdictionRelationGroupDto();
        try {
            // 查询数据
            final P6eJurisdictionRelationGroupDb p6eJurisdictionRelationGroupDb =
                    P6eCopyUtil.run(param, P6eJurisdictionRelationGroupDb.class, new P6eJurisdictionRelationGroupDb());
            final P6eJurisdictionRelationGroupAggregate p6eJurisdictionRelationGroupAggregate =
                    new P6eJurisdictionRelationGroupAggregate(p6eJurisdictionRelationGroupDb);
            // 写入查询的数据
            p6eJurisdictionRelationGroupDto.initPaging(p6eJurisdictionRelationGroupDb);
            p6eJurisdictionRelationGroupDto.setTotal(p6eJurisdictionRelationGroupAggregate.count());
            p6eJurisdictionRelationGroupDto.setList(P6eCopyUtil.runList(
                    p6eJurisdictionRelationGroupAggregate.select(), P6eJurisdictionRelationGroupDto.class));
        }catch (RuntimeException e) {
            LOGGER.error(e.getMessage());
            p6eJurisdictionRelationGroupDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
        } catch (Exception ee) {
            LOGGER.error(ee.getMessage());
            p6eJurisdictionRelationGroupDto.setError(P6eModel.Error.SERVICE_EXCEPTION);
        }
        return p6eJurisdictionRelationGroupDto;
    }

    @Override
    public P6eJurisdictionRelationGroupDto selectOneJurisdictionRelationGroup(P6eJurisdictionRelationGroupDto param) {
        final P6eJurisdictionRelationGroupDto p6eJurisdictionRelationGroupDto = new P6eJurisdictionRelationGroupDto();
        try {
            if (param == null || param.getGid() == null || param.getJurisdictionId() == null) {
                p6eJurisdictionRelationGroupDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
            } else {
                P6eCopyUtil.run(new P6eJurisdictionRelationGroupAggregate(
                        new P6eJurisdictionRelationGroupDb().setGid(param.getGid())
                                .setJurisdictionId(param.getJurisdictionId())).get(), p6eJurisdictionRelationGroupDto);
            }
        }catch (RuntimeException e) {
            LOGGER.error(e.getMessage());
            p6eJurisdictionRelationGroupDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
        } catch (Exception ee) {
            LOGGER.error(ee.getMessage());
            p6eJurisdictionRelationGroupDto.setError(P6eModel.Error.SERVICE_EXCEPTION);
        }
        return p6eJurisdictionRelationGroupDto;
    }

    @Override
    public P6eJurisdictionRelationGroupDto createJurisdictionRelationGroup(P6eJurisdictionRelationGroupDto param) {
        final P6eJurisdictionRelationGroupDto p6eJurisdictionRelationGroupDto = new P6eJurisdictionRelationGroupDto();
        try {
            if (param == null
                    || param.getGid() == null
                    || param.getJurisdictionId() == null
                    || param.getJurisdictionParam() == null
                    || param.getOperate() == null) {
                p6eJurisdictionRelationGroupDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
            } else {
                // 添加数据
                P6eCopyUtil.run(new P6eJurisdictionRelationGroupAggregate(
                        P6eCopyUtil.run(param, P6eJurisdictionRelationGroupDb.class)), p6eJurisdictionRelationGroupDto);
            }
        }catch (RuntimeException e) {
            LOGGER.error(e.getMessage());
            p6eJurisdictionRelationGroupDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
        } catch (Exception ee) {
            LOGGER.error(ee.getMessage());
            p6eJurisdictionRelationGroupDto.setError(P6eModel.Error.SERVICE_EXCEPTION);
        }
        return p6eJurisdictionRelationGroupDto;
    }

    @Override
    public P6eJurisdictionRelationGroupDto deleteJurisdictionRelationGroup(P6eJurisdictionRelationGroupDto param) {
        final P6eJurisdictionRelationGroupDto p6eJurisdictionRelationGroupDto = new P6eJurisdictionRelationGroupDto();
        try {
            if (param == null || param.getGid() == null || param.getJurisdictionId() == null) {
                p6eJurisdictionRelationGroupDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
            } else {
                P6eCopyUtil.run(new P6eJurisdictionRelationGroupAggregate(
                        new P6eJurisdictionRelationGroupDb().setGid(param.getGid())
                                .setJurisdictionId(param.getJurisdictionId())).delete(), p6eJurisdictionRelationGroupDto);
            }
        }catch (RuntimeException e) {
            LOGGER.error(e.getMessage());
            p6eJurisdictionRelationGroupDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
        } catch (Exception ee) {
            LOGGER.error(ee.getMessage());
            p6eJurisdictionRelationGroupDto.setError(P6eModel.Error.SERVICE_EXCEPTION);
        }
        return p6eJurisdictionRelationGroupDto;
    }

    @Override
    public P6eJurisdictionGroupRelationUserDto selectJurisdictionGroupRelationUser(P6eJurisdictionGroupRelationUserDto param) {
        final P6eJurisdictionGroupRelationUserDto p6eJurisdictionGroupRelationUserDto = new P6eJurisdictionGroupRelationUserDto();
        try {
            final P6eJurisdictionGroupRelationUserDb p6eJurisdictionGroupRelationUserDb =
                    P6eCopyUtil.run(param, P6eJurisdictionGroupRelationUserDb.class, new P6eJurisdictionGroupRelationUserDb());
            final P6eJurisdictionGroupRelationUserAggregate p6eJurisdictionGroupRelationUserAggregate =
                    new P6eJurisdictionGroupRelationUserAggregate(p6eJurisdictionGroupRelationUserDb);
            // 写入数据的操作
            p6eJurisdictionGroupRelationUserDto.initPaging(p6eJurisdictionGroupRelationUserDb);
            p6eJurisdictionGroupRelationUserDto.setTotal(p6eJurisdictionGroupRelationUserAggregate.count());
            p6eJurisdictionGroupRelationUserDto.setList(P6eCopyUtil.runList(
                    p6eJurisdictionGroupRelationUserAggregate.select(), P6eJurisdictionGroupRelationUserDto.class));
        }catch (RuntimeException e) {
            LOGGER.error(e.getMessage());
            p6eJurisdictionGroupRelationUserDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
        } catch (Exception ee) {
            LOGGER.error(ee.getMessage());
            p6eJurisdictionGroupRelationUserDto.setError(P6eModel.Error.SERVICE_EXCEPTION);
        }
        return p6eJurisdictionGroupRelationUserDto;
    }

    @Override
    public P6eJurisdictionGroupRelationUserDto selectOneJurisdictionGroupRelationUser(P6eJurisdictionGroupRelationUserDto param) {
        final P6eJurisdictionGroupRelationUserDto p6eJurisdictionGroupRelationUserDto = new P6eJurisdictionGroupRelationUserDto();
        try {
            if (param == null || param.getGid() == null || param.getUid() == null) {
                p6eJurisdictionGroupRelationUserDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
            } else {
                P6eCopyUtil.run(new P6eJurisdictionGroupRelationUserAggregate(
                        param.getUid(), param.getGid()).get(), P6eJurisdictionGroupRelationUserDto.class);
            }
        }catch (RuntimeException e) {
            LOGGER.error(e.getMessage());
            p6eJurisdictionGroupRelationUserDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
        } catch (Exception ee) {
            LOGGER.error(ee.getMessage());
            p6eJurisdictionGroupRelationUserDto.setError(P6eModel.Error.SERVICE_EXCEPTION);
        }
        return p6eJurisdictionGroupRelationUserDto;
    }

    @Override
    public P6eJurisdictionGroupRelationUserDto createJurisdictionGroupRelationUser(P6eJurisdictionGroupRelationUserDto param) {
        final P6eJurisdictionGroupRelationUserDto p6eJurisdictionGroupRelationUserDto = new P6eJurisdictionGroupRelationUserDto();
        try {
            if (param == null || param.getGid() == null || param.getUid() == null || param.getOperate() == null) {
                p6eJurisdictionGroupRelationUserDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
            } else {
                P6eCopyUtil.run(new P6eJurisdictionGroupRelationUserAggregate(
                        P6eCopyUtil.run(param, P6eJurisdictionGroupRelationUserDb.class)).create(), P6eJurisdictionGroupRelationUserDto.class);
            }
        }catch (RuntimeException e) {
            LOGGER.error(e.getMessage());
            p6eJurisdictionGroupRelationUserDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
        } catch (Exception ee) {
            LOGGER.error(ee.getMessage());
            p6eJurisdictionGroupRelationUserDto.setError(P6eModel.Error.SERVICE_EXCEPTION);
        }
        return p6eJurisdictionGroupRelationUserDto;
    }

    @Override
    public P6eJurisdictionGroupRelationUserDto deleteJurisdictionGroupRelationUser(P6eJurisdictionGroupRelationUserDto param) {
        final P6eJurisdictionGroupRelationUserDto p6eJurisdictionGroupRelationUserDto = new P6eJurisdictionGroupRelationUserDto();
        try {
            if (param == null || param.getGid() == null || param.getUid() == null) {
                p6eJurisdictionGroupRelationUserDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
            } else {
                P6eCopyUtil.run(new P6eJurisdictionGroupRelationUserAggregate(
                        P6eCopyUtil.run(param, P6eJurisdictionGroupRelationUserDb.class)).delete(), P6eJurisdictionGroupRelationUserDto.class);
            }
        }catch (RuntimeException e) {
            LOGGER.error(e.getMessage());
            p6eJurisdictionGroupRelationUserDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
        } catch (Exception ee) {
            LOGGER.error(ee.getMessage());
            p6eJurisdictionGroupRelationUserDto.setError(P6eModel.Error.SERVICE_EXCEPTION);
        }
        return p6eJurisdictionGroupRelationUserDto;
    }

}
