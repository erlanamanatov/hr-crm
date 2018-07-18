package com.erkprog.zensofthrcrm.ui.vacancies.createVacancy;

import android.content.Context;

import com.erkprog.zensofthrcrm.data.network.ApiInterface;

public class CreateVacancyPresenter implements CreateVacancyContract.Presenter {

  private Context mContext;
  private ApiInterface mApiService;
  private CreateVacancyContract.View mView;

  public CreateVacancyPresenter(Context context, ApiInterface service) {
    mContext = context;
    mApiService = service;
  }

  private boolean isViewAttached(){
    return mView != null;
  }

  @Override
  public void bind(CreateVacancyContract.View view) {
    mView = view;
  }

  @Override
  public void unbind() {
    mView = null;
  }

  @Override
  public void loadData() {

  }
}
