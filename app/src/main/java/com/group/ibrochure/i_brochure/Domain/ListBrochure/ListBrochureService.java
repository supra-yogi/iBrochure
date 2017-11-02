package com.group.ibrochure.i_brochure.Domain.ListBrochure;

import com.group.ibrochure.i_brochure.Common.DomainServiceBase;
import com.group.ibrochure.i_brochure.Common.IRepository;

/**
 * Created by Yogi on 02/11/2017.
 */

public class ListBrochureService extends DomainServiceBase<ListBrochure> {
    public ListBrochureService(IRepository repository) {
        super(repository);
    }

    @Override
    public ListBrochure CreateNew() {
        return new ListBrochure();
    }
}
