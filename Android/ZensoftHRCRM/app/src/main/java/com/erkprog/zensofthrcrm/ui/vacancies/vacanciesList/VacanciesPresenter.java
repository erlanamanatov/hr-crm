package com.erkprog.zensofthrcrm.ui.vacancies.vacanciesList;

import com.erkprog.zensofthrcrm.data.db.SQLiteHelper;
import com.erkprog.zensofthrcrm.data.entity.VacanciesResponse;
import com.erkprog.zensofthrcrm.data.entity.Vacancy;
import com.erkprog.zensofthrcrm.data.network.ApiInterface;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class VacanciesPresenter implements VacanciesContract.Presenter {

  private VacanciesContract.View mView;
  private ApiInterface mService;
  private SQLiteHelper mSQLiteHelper;
  private CompositeDisposable disposable = new CompositeDisposable();

  VacanciesPresenter(ApiInterface service, SQLiteHelper
      sqliteHelper) {
    mService = service;
    mSQLiteHelper = sqliteHelper;
  }

  @Override
  public void getVacanciesInternet() {
    disposable.add(
        mService.getVacancies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<VacanciesResponse>() {
              @Override
              public void accept(VacanciesResponse vacanciesResponse) throws Exception {
                if (isViewAttached()) {
                  if (vacanciesResponse != null && vacanciesResponse.getVacancyList() != null) {
                    mSQLiteHelper.saveVacancies(vacanciesResponse.getVacancyList());
                    mView.showVacancies(vacanciesResponse.getVacancyList());
                  }
                }
              }
            }, new Consumer<Throwable>() {
              @Override
              public void accept(Throwable throwable) throws Exception {
                if (isViewAttached()) {
                  mView.showMessage(throwable.getMessage());
                  getVacanciesLocal();
                }
              }
            })
    );
  }

  @Override
  public void getVacanciesLocal() {
    List<Vacancy> vacancies = mSQLiteHelper.getVacancies();
    if (vacancies != null)
      mView.showVacancies(vacancies);
  }

  @Override
  public void onVacancyItemClick(Vacancy vacancy) {
    mView.showDetailedVacancy(vacancy.getId());
  }

  private boolean isViewAttached() {
    return mView != null;
  }

  @Override
  public void bind(VacanciesContract.View view) {
    this.mView = view;
  }

  @Override
  public void unbind() {
    this.mView = null;
  }
}
