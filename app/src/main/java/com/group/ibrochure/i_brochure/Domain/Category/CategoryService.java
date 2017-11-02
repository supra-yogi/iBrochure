package com.group.ibrochure.i_brochure.Domain.Category;

import com.group.ibrochure.i_brochure.Common.DomainServiceBase;
import com.group.ibrochure.i_brochure.Common.IRepository;

/**
 * Created by Yogi on 02/11/2017.
 */

public class CategoryService extends DomainServiceBase<Category> {
    public CategoryService(IRepository repository) {
        super(repository);
    }

    @Override
    public Category CreateNew() {
        return new Category();
    }
}
