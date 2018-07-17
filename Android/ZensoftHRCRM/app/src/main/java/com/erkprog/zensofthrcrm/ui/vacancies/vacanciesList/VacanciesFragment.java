package com.erkprog.zensofthrcrm.ui.vacancies.vacanciesList;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.erkprog.zensofthrcrm.CRMApplication;
import com.erkprog.zensofthrcrm.R;
import com.erkprog.zensofthrcrm.data.entity.Vacancy;
import com.erkprog.zensofthrcrm.ui.ItemClickListener;
import com.erkprog.zensofthrcrm.ui.interviews.interviewDetail.InterviewDetail;
import com.erkprog.zensofthrcrm.ui.vacancies.vacancyDetail.VacancyDetail;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VacanciesFragment extends Fragment implements VacanciesContract.View,
    ItemClickListener<Vacancy> {

  private VacanciesContract.Presenter mPresenter;

  @BindView(R.id.recycler_view_all_vacancies)
  RecyclerView mRecyclerView;

  @BindView(R.id.vacancies_progress_bar)
  ProgressBar mProgressBar;

  @BindView(R.id.txt_empty_vacancies_view)
  TextView noVacanciesView;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initPresenter();
  }

  private void initPresenter() {
    mPresenter = new VacanciesPresenter(this, CRMApplication.getInstance(requireContext())
        .getApiService(), CRMApplication.getInstance(requireContext()).getSQLiteHelper());
    mPresenter.bind(this);
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_vacancies_list, container, false);

    ButterKnife.bind(this, v);

    dismissProgress();

    initRecyclerView(v);
    showProgress();
    if(hasInternetConnection(v.getContext())){
      mPresenter.getVacanciesInternet();
    }
    else{
      mPresenter.getVacanciesLocal();
    }

    return v;
  }

  private void initRecyclerView(View v) {
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
    mRecyclerView.setLayoutManager(layoutManager);
  }

  @Override
  public void showVacancies(List<Vacancy> vacancies) {
    dismissProgress();

    if (vacancies.size() > 0) {
      VacanciesAdapter adapter = new VacanciesAdapter(vacancies, this);
      mRecyclerView.setAdapter(adapter);
    } else {
      noVacanciesView.setVisibility(View.VISIBLE);
    }
  }

  @Override
  public void showDetailedVacancy(int vacancyId) {
    Log.d("me", "showDetailedVacancy: ");
    Intent intent = new Intent(getActivity(), VacancyDetail.class);
    intent.putExtra("vacancy_id", vacancyId);
    startActivity(intent);
  }

  @Override
  public void showMessage(String message) {
    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
  }

  @Override
  public boolean hasInternetConnection(Context context) {
    ConnectivityManager connectivityManager
        = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    if (connectivityManager != null) {
      NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
      return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    return false;
  }

  @Override
  public void showProgress() {
    mProgressBar.setVisibility(View.VISIBLE);
  }

  @Override
  public void dismissProgress() {
    mProgressBar.setVisibility(View.GONE);
  }

  @Override
  public void onItemClick(Vacancy item) {
    mPresenter.onVacancyItemClick(item);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    mPresenter.unbind();
  }

  public static VacanciesFragment newInstance() {
    return new VacanciesFragment();
  }
}
