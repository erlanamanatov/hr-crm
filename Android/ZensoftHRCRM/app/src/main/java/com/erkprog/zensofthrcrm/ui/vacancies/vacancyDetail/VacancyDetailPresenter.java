package com.erkprog.zensofthrcrm.ui.vacancies.vacancyDetail;

import android.util.Log;

import com.erkprog.zensofthrcrm.data.db.SQLiteHelper;
import com.erkprog.zensofthrcrm.data.entity.Vacancy;
import com.erkprog.zensofthrcrm.data.network.ApiInterface;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VacancyDetailPresenter implements VacancyDetailContract.Presenter {


  private VacancyDetailContract.View mView;
  private ApiInterface mService;
  private SQLiteHelper mSQLiteHelper;

  VacancyDetailPresenter(ApiInterface service, SQLiteHelper
      sqLiteHelper) {
    mService = service;
    mSQLiteHelper = sqLiteHelper;
  }

  @Override
  public void getVacancyInternet(final int vacancyId) {

    mService.getDetailedVacancy(vacancyId).enqueue(new Callback<Vacancy>() {
      @Override
      public void onResponse(Call<Vacancy> call, Response<Vacancy> response) {
        if (isViewAttached()) {
          if (response.isSuccessful() && response.body() != null) {
            mSQLiteHelper.saveVacancies(new ArrayList<Vacancy>(Arrays.asList(response.body())));
            mView.showVacancyDetails(response.body());
          }
        }
      }

      @Override
      public void onFailure(Call<Vacancy> call, Throwable t) {
        if (isViewAttached()) {
          mView.showMessage(t.getMessage());
          getVacancyLocal(vacancyId);
        }
      }
    });

  }

  @Override
  public void getVacancyLocal(int vacancyId) {
    Vacancy vacancy = mSQLiteHelper.getVacancy(String.valueOf(vacancyId));
    mView.showVacancyDetails(vacancy);
  }


  @Override
  public void bind(VacancyDetailContract.View view) {
    mView = view;
  }

  private boolean isViewAttached() {
    return mView != null;
  }

  @Override
  public void unbind() {
    mView = null;
  }


}
