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
import com.erkprog.zensofthrcrm.R;

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
    View v = inflater.inflate(R.layout.fragment_create_vacancy, container, false);

    return v;
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

  public static CreateVacancyFragment newInstance() {
    return new CreateVacancyFragment();
  }

  @Override
  public void onStart() {
    super.onStart();
    getActivity().setTitle(getString(R.string.create_vacancy));
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    mPresenter.unbind();
  }
}
