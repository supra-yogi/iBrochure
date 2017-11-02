package com.group.ibrochure.i_brochure.Domain.UserAccount;

import com.group.ibrochure.i_brochure.Common.DomainServiceBase;
import com.group.ibrochure.i_brochure.Common.IRepository;

/**
 * Created by Yogi on 02/11/2017.
 */

public class UserAccountService extends DomainServiceBase<UserAccount> {
    public UserAccountService(IRepository repository) {
        super(repository);
    }

    @Override
    public UserAccount CreateNew() {
        return new UserAccount();
    }
}
