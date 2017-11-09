package com.group.ibrochure.i_brochure.Domain.UserAccount;

import com.group.ibrochure.i_brochure.Common.IRepository;
import com.group.ibrochure.i_brochure.Infrastructure.ResponseCallBack;

/**
 * Created by Yogi on 02/11/2017.
 */

public interface IUserAccountRepo extends IRepository<UserAccount> {
    void Login(ResponseCallBack responseCallBack, String userOrEmail, String password);
    void GetByUsername(ResponseCallBack responseCallBack, String username);
}
