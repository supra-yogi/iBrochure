package com.group.ibrochure.i_brochure.Domain.Customer;

import com.group.ibrochure.i_brochure.Common.DomainServiceBase;
import com.group.ibrochure.i_brochure.Common.IRepository;

/**
 * Created by Yogi on 02/11/2017.
 */

public class CustomerService extends DomainServiceBase<Customer> {
    public CustomerService(IRepository repository) {
        super(repository);
    }

    @Override
    public Customer CreateNew() {
        return new Customer();
    }
}
