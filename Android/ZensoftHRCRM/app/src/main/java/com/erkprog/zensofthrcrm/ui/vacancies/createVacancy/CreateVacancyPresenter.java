package com.erkprog.zensofthrcrm.ui.vacancies.createVacancy;

import android.content.Context;

import com.erkprog.zensofthrcrm.R;
import com.erkprog.zensofthrcrm.data.entity.Vacancy;
import com.erkprog.zensofthrcrm.data.entity.VacancyRequest;
import com.erkprog.zensofthrcrm.data.network.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateVacancyPresenter implements CreateVacancyContract.Presenter {

  private Context mContext;
  private ApiInterface mApiService;
  private CreateVacancyContract.View mView;

  public CreateVacancyPresenter(Context context, ApiInterface service) {
    mContext = context;
    mApiService = service;
  }

  private boolean isViewAttached() {
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
  public void onCreateButtonClick(VacancyRequest vacancyRequest) {
    mView.showProgress();
    mApiService.postVacancy("application/json", vacancyRequest).enqueue(new Callback<Vacancy>() {
      @Override
      public void onResponse(Call<Vacancy> call, Response<Vacancy> response) {
        if (isViewAttached()) {
          mView.dismissProgress();
          if (response.isSuccessful()) {
            mView.showMessage(mContext.getString(R.string.vacancy_created));
          } else {
            mView.showMessage(mContext.getString(R.string.response_not_successful));
          }
        }
      }

      @Override
      public void onFailure(Call<Vacancy> call, Throwable t) {
        if (isViewAttached()) {
          mView.dismissProgress();
          mView.showMessage(t.getMessage());
        }
      }
    });

  }
}
