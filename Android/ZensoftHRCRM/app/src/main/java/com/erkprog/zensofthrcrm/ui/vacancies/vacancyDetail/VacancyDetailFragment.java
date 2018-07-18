package com.erkprog.zensofthrcrm.ui.vacancies.vacancyDetail;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.erkprog.zensofthrcrm.CRMApplication;
import com.erkprog.zensofthrcrm.R;
import com.erkprog.zensofthrcrm.data.entity.Vacancy;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VacancyDetailFragment extends Fragment implements VacancyDetailContract.View,View.OnClickListener {

  private static final String EXTRA_VACANCY_ID = "vacancy_id";

  private VacancyDetailContract.Presenter mPresenter;

  @BindView(R.id.v_name)
  TextView mName;

  @BindView(R.id.v_title)
  TextView mTitle;

  @BindView(R.id.v_city)
  TextView mCity;

  @BindView(R.id.v_address)
  TextView mAddress;

  @BindView(R.id.v_working_hours)
  TextView mWorkingHours;

  @BindView(R.id.v_salary_min)
  TextView mMinSalary;

  @BindView(R.id.v_salary_max)
  TextView mMaxSalary;

  @BindView(R.id.v_comments)
  TextView mComment;

  @BindView(R.id.v_created)
  TextView mCreated;

  @BindView(R.id.v_last_published)
  TextView mLastPublished;

  @BindView(R.id.v_work_conditions_layout)
  LinearLayout mLayout;

  @BindView(R.id.v_publish)
  Button mPublish;

  @BindView(R.id.v_edit)
  Button mEdit;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mPresenter = new VacancyDetailPresenter(CRMApplication.getInstance(requireContext())
        .getApiService(), CRMApplication.getInstance(requireContext()).getSQLiteHelper());
    mPresenter.bind(this);
  }


  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_vacancy_detail, container, false);

    ButterKnife.bind(this, v);

    if (getArguments() != null)
      if (hasInternetConnection(v.getContext())) {
        mPresenter.getVacancyInternet(getArguments().getInt(EXTRA_VACANCY_ID));
      } else {
        mPresenter.getVacancyLocal(getArguments().getInt(EXTRA_VACANCY_ID));
      }

    return v;
  }


  @Override
  public void onDestroy() {
    super.onDestroy();
    mPresenter.unbind();
  }


  @Override
  public void showMessage(String message) {

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

  }

  @Override
  public void dismissProgress() {

  }

  @Override
  public void showVacancyDetails(Vacancy vacancy) {
    if (vacancy.getWorkConditions() != null) {
      LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams
          .WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
      llp.setMargins(20,8,0,0);
      TextView workLabel = new TextView(getActivity());
      workLabel.setText(R.string.work_conditions_label);
      workLabel.setLayoutParams(llp);
      mLayout.addView(workLabel);

      for(final String workingCondition : vacancy.getWorkConditions()){
        TextView workText = new TextView(getActivity());
        workText.setText(String.format("-%s", workingCondition.substring(0, 1).toUpperCase() + workingCondition
            .substring
            (1)));
        workText.setTextColor(getResources().getColor(R.color.colorBlack));
        workText.setLayoutParams(llp);
        mLayout.addView(workText);
      }
    }


    mName.setText(vacancy.getName());
    mTitle.setText(vacancy.getTitle());
    mCity.setText(vacancy.getCity());
    mAddress.setText(vacancy.getAddress());
    mWorkingHours.setText(vacancy.getWorkingHours());
    mMinSalary.setText(String.valueOf(vacancy.getSalaryMin()));
    mMaxSalary.setText(String.valueOf(vacancy.getSalaryMax()));
    mComment.setText(vacancy.getComments());
    mCreated.setText(vacancy.getCreated());
    mLastPublished.setText(vacancy.getLastPublished());

    mPublish.setOnClickListener(this);
    mEdit.setOnClickListener(this);


  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.v_publish:
        break;
      case R.id.v_edit:
        break;
      default:
        break;
    }
  }
}

