package com.group.ibrochure.i_brochure.Infrastructure;

import android.content.Context;

import com.group.ibrochure.i_brochure.Domain.ListBrochure.IListBrochureRepo;
import com.group.ibrochure.i_brochure.Domain.ListBrochure.ListBrochure;
import com.group.ibrochure.i_brochure.Domain.UserAccount.IUserAccountRepo;
import com.group.ibrochure.i_brochure.Domain.UserAccount.UserAccount;

/**
 * Created by Yogi on 06/11/2017.
 */

public class ListBrochureAPI extends BaseAPI<ListBrochure> implements IListBrochureRepo {

    public ListBrochureAPI(Context context) {
        super(context);
    }

    @Override
    public void Save(ResponseCallBack responseCallBack, ListBrochure entity) {

    }

    @Override
    public ListBrochure CreateNew() {
        return new ListBrochure();
    }

    @Override
    public String GetUrl() {
        return URLs.URL_LISTBROCHURE;
    }
}
