package com.erkprog.zensofthrcrm.ui.requests.requestsList;

import android.content.Context;
import android.support.annotation.NonNull;

import com.erkprog.zensofthrcrm.R;
import com.erkprog.zensofthrcrm.data.entity.Request;
import com.erkprog.zensofthrcrm.data.entity.RequestsResponse;
import com.erkprog.zensofthrcrm.data.network.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestsPresenter implements RequestsContract.Presenter {

  private ApiInterface mApiService;
  private RequestsContract.View mView;
  private Context mContext;

  RequestsPresenter(Context context, ApiInterface service) {
    mContext = context;
    mApiService = service;
  }

  @Override
  public void loadData() {
    mView.showProgress();
    mApiService.getRequests().enqueue(new Callback<RequestsResponse>() {
      @Override
      public void onResponse(@NonNull Call<RequestsResponse> call, @NonNull Response<RequestsResponse> response) {
        if (isViewAttached()) {
          mView.dismissProgress();
          if (response.isSuccessful() && response.body().getRequestList() != null) {
            mView.showRequests(response.body().getRequestList());
          } else {
            mView.showMessage(mContext.getString(R.string.requests_null));
          }
        }
      }

      @Override
      public void onFailure(@NonNull Call<RequestsResponse> call, @NonNull Throwable t) {
        if (isViewAttached()) {
          mView.dismissProgress();
          mView.showMessage(t.getMessage());
        }
      }
    });
  }

  @Override
  public void onRequestItemClick(Request request) {
    mView.showProgress();
    mApiService.getDetailedRequest(request.getId()).enqueue(new Callback<Request>() {
      @Override
      public void onResponse(Call<Request> call, Response<Request> response) {
        if (isViewAttached()){
          mView.dismissProgress();
          if (response.isSuccessful()){
            Request detailedRequest = response.body();
            mView.showRequestDetails(detailedRequest);
          } else {
            mView.showMessage(mContext.getString(R.string.response_not_successful));
          }
        }
      }

      @Override
      public void onFailure(Call<Request> call, Throwable t) {
        if (isViewAttached()) {
          mView.dismissProgress();
          mView.showMessage(t.getMessage());
        }

      }
    });
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
