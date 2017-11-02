package com.group.ibrochure.i_brochure.Common;

import java.util.List;

/**
 * Created by Yogi on 02/11/2017.
 */

public abstract class DomainServiceBase<T extends EntityBase> {
    protected IRepository repository;

    public DomainServiceBase(IRepository repository) {
        this.repository = repository;
    }

    public void Save(T Entity) {
        repository.Save(Entity);
    }

    public void Delete(int Id) {
        repository.Delete(Id);
    }

    public List<T> GetAll() {
        return repository.GetAll();
    }

    public T GetById(int Id) {
        return (T) repository.GetById(Id);
    }

    public int GetCount() {
        return repository.GetCount();
    }

    public abstract T CreateNew();
}
