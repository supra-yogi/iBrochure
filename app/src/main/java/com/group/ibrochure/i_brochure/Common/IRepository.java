package com.group.ibrochure.i_brochure.Common;

import java.util.List;

/**
 * Created by Yogi on 02/11/2017.
 */

public interface IRepository<T extends EntityBase>{
    T GetById(int id);
    List<T> GetAll();
    int GetCount();
    void Save(T entity);
    void Delete(int id);
}
