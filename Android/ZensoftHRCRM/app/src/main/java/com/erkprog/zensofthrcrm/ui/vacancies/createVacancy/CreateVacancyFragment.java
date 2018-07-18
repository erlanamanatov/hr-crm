package com.erkprog.zensofthrcrm.ui.vacancies.createVacancy;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.erkprog.zensofthrcrm.CRMApplication;

public class CreateVacancyFragment extends Fragment implements CreateVacancyContract.View {

  private static final String TAG = "CREATE VACANCY";

  private CreateVacancyContract.Presenter mPresenter;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mPresenter = new CreateVacancyPresenter(requireContext(), CRMApplication.getInstance
        (requireContext()).getApiService());
    mPresenter.bind(this);
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return super.onCreateView(inflater, container, savedInstanceState);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mPresenter.loadData();
  }

  @Override
  public void showMessage(String message) {

  }

  @Override
  public boolean hasInternetConnection(Context context) {
    return false;
  }

  @Override
  public void showProgress() {

  }

  @Override
  public void dismissProgress() {

  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    mPresenter.unbind();
  }
}
