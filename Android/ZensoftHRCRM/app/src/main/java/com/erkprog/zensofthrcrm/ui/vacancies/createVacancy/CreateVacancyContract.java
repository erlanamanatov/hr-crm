package com.erkprog.zensofthrcrm.ui.vacancies.createVacancy;

import com.erkprog.zensofthrcrm.data.entity.VacancyRequest;
import com.erkprog.zensofthrcrm.ui.BaseView;
import com.erkprog.zensofthrcrm.ui.ILifecycle;

public interface CreateVacancyContract {

  interface View extends BaseView {

  }

  interface Presenter extends ILifecycle<View> {

    void onCreateButtonClick(VacancyRequest request);
  }
}
