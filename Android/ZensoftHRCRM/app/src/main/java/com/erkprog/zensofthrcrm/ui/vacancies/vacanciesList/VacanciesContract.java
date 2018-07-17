package com.erkprog.zensofthrcrm.ui.vacancies.vacanciesList;

import com.erkprog.zensofthrcrm.data.entity.Vacancy;
import com.erkprog.zensofthrcrm.ui.BaseView;
import com.erkprog.zensofthrcrm.ui.ILifecycle;

import java.util.List;

public interface VacanciesContract extends BaseView{

  interface View extends BaseView {

    void showVacancies(List<Vacancy> vacancies);

    void showDetailedVacancy(int vacancyId);

  }

  interface Presenter extends ILifecycle<View> {

    void getVacanciesInternet();

    void getVacanciesLocal();

    void onVacancyItemClick(Vacancy vacancy);
  }

}
