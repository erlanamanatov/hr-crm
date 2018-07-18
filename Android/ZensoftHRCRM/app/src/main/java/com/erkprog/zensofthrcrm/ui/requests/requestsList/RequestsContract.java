package com.erkprog.zensofthrcrm.ui.requests.requestsList;

import com.erkprog.zensofthrcrm.data.entity.Request;
import com.erkprog.zensofthrcrm.ui.BaseView;
import com.erkprog.zensofthrcrm.ui.ILifecycle;

import java.util.List;

public interface RequestsContract {

  interface View extends BaseView {

    void showRequests(List<Request> requests);

    void showRequestDetails(Request request);
  }

  interface Presenter extends ILifecycle<View> {


    void getRequestsInternet();

    void getRequestsLocal();

    void onRequestItemClick(Request request);
  }

}

