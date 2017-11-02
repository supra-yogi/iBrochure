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

    public String GenerateCode() {
        return "C" + GetCount();
    }

    @Override
    public Category CreateNew() {
        Category ent = new Category();
        ent.setCode(GenerateCode());

        return ent;
    }
}
