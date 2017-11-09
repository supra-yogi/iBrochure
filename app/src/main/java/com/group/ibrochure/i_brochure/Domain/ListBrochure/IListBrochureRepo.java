package com.group.ibrochure.i_brochure.Domain.ListBrochure;

import com.group.ibrochure.i_brochure.Common.IRepository;
import com.group.ibrochure.i_brochure.Domain.UserAccount.UserAccount;
import com.group.ibrochure.i_brochure.Infrastructure.ResponseCallBack;

/**
 * Created by Yogi on 02/11/2017.
 */

public interface IListBrochureRepo extends IRepository<ListBrochure> {
    void GetListBrochureByPage(ResponseCallBack responseCallBack, int page, int size);
    void GetListMyBrochureByPage(ResponseCallBack responseCallBack, UserAccount userAccount, int page, int size);
}
