package com.erkprog.zensofthrcrm.ui.requests.requestsList;

import android.content.Context;
import android.support.annotation.NonNull;

import com.erkprog.zensofthrcrm.R;
import com.erkprog.zensofthrcrm.data.db.SQLiteHelper;
import com.erkprog.zensofthrcrm.data.entity.Request;
import com.erkprog.zensofthrcrm.data.entity.RequestsResponse;
import com.erkprog.zensofthrcrm.data.network.ApiInterface;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestsPresenter implements RequestsContract.Presenter {

  private RequestsContract.View mView;
  private ApiInterface mService;
  private SQLiteHelper mSQLiteHelper;
  private CompositeDisposable disposable = new CompositeDisposable();

  RequestsPresenter(RequestsContract.View view, ApiInterface service, SQLiteHelper
      sqliteHelper) {
    mView = view;
    mService = service;
    mSQLiteHelper = sqliteHelper;
  }

  @Override
  public void getRequestsInternet() {
    disposable.add(
        mService.getRequests()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<RequestsResponse>() {
              @Override
              public void accept(RequestsResponse requestsResponse) throws Exception {
                if (isViewAttached()) {
                  if (requestsResponse != null && requestsResponse.getRequestList() != null) {
                    mSQLiteHelper.saveRequests(requestsResponse.getRequestList());
                    mView.showRequests(requestsResponse.getRequestList());
                  }
                }
              }
            }, new Consumer<Throwable>() {
              @Override
              public void accept(Throwable throwable) throws Exception {
                if (isViewAttached()) {
                  mView.showMessage(throwable.getMessage());
                  getRequestsLocal();
                }
              }
            })
    );
  }


  @Override
  public void getRequestsLocal() {
    List<Request> requests = mSQLiteHelper.getRequests();
    if (requests != null) {
      mView.showRequests(requests);
    }
  }



  @Override
  public void onRequestItemClick(Request request) {
    //TODO: implement mView.createVacancy() here
  }

  private boolean isViewAttached() {
    return mView != null;
  }

  @Override
  public void bind(RequestsContract.View view) {
    this.mView = view;
  }

  @Override
  public void unbind() {
    this.mView = null;
  }
}
