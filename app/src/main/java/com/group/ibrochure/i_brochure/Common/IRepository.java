package com.group.ibrochure.i_brochure.Common;

import com.group.ibrochure.i_brochure.Infrastructure.ResponseCallBack;

import java.util.List;

/**
 * Created by Yogi on 02/11/2017.
 */

public interface IRepository<T extends EntityBase>{
    void GetById(final ResponseCallBack responseCallBack, int id);
    void GetAll(final ResponseCallBack responseCallBack);
    void Save(final ResponseCallBack responseCallBack, T entity);
    void Delete(final ResponseCallBack responseCallBack, int id);
    T CreateNew();
}
