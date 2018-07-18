package com.erkprog.zensofthrcrm.ui.vacancies.createVacancy;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.erkprog.zensofthrcrm.CRMApplication;
import com.erkprog.zensofthrcrm.R;
import com.erkprog.zensofthrcrm.data.entity.Request;
import com.erkprog.zensofthrcrm.data.entity.Requirement;
import com.erkprog.zensofthrcrm.data.entity.User;
import com.erkprog.zensofthrcrm.data.entity.VacancyRequest;

import java.util.ArrayList;
import java.util.List;

public class CreateVacancyFragment extends Fragment implements CreateVacancyContract.View, View
    .OnClickListener {

  private static final String TAG = "CREATE VACANCY";
  private static final String ARG_REQUEST = "ARG REQUEST";
  private Request mRequest;

  private TextView requirements;
  private EditText title;
  private TextView name;
  private EditText city;
  private EditText address;
  private EditText workConditions;
  private EditText responsibilities;
  private EditText comments;
  private EditText minSalary;
  private EditText maxSalary;
  private TextView createdBy;
  private TextView created;
  private Button buttonCreate;
  private ProgressBar mProgressBar;

  private CreateVacancyContract.Presenter mPresenter;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mPresenter = new CreateVacancyPresenter(requireContext(), CRMApplication.getInstance
        (requireContext()).getApiService());
    mPresenter.bind(this);

    if (getArguments() != null) {
      mRequest = (Request) getArguments().getSerializable(ARG_REQUEST);
      Log.d(TAG, "onCreate: getArguments: dep " + mRequest.getPosition().getDepartment().getName());
    }
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_create_vacancy, container, false);
    initUi(v);
    fillFields();

    getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    return v;
  }

  private void initUi(View v) {
    title = v.findViewById(R.id.cr_vac_title);
    name = v.findViewById(R.id.cr_vac_name);
    city = v.findViewById(R.id.cr_vac_city);
    address = v.findViewById(R.id.cr_vac_address);
    requirements = v.findViewById(R.id.cr_vac_requirements);
    workConditions = v.findViewById(R.id.cr_vac_work_conditions);
    responsibilities = v.findViewById(R.id.cr_vac_responsibilities);
    comments = v.findViewById(R.id.cr_vac_comments);
    minSalary = v.findViewById(R.id.cr_vac_min_salary);
    maxSalary = v.findViewById(R.id.cr_vac_max_salary);
    createdBy = v.findViewById(R.id.cr_vac_created_by);
    created = v.findViewById(R.id.cr_vac_created);
    buttonCreate = v.findViewById(R.id.cr_vac_create);
    buttonCreate.setOnClickListener(this);
    mProgressBar = v.findViewById(R.id.cr_vac_progress_bar);
//    Drawable progressDrawable = mProgressBar.getProgressDrawable().mutate();
//    progressDrawable.setColorFilter(Color.YELLOW, android.graphics.PorterDuff.Mode.SRC_IN);
//    mProgressBar.setProgressDrawable(progressDrawable);
    mProgressBar.setVisibility(View.GONE);
  }

  private void fillFields() {
    if (mRequest != null) {
      if (mRequest.getPosition() != null) {
        name.setText(mRequest.getPosition().getName());
      }

      requirements.setText(getDisplayRequirements(mRequest.getRequirementList()));

      if (mRequest.getCreatedBy() != null) {
        User user = mRequest.getCreatedBy();
        createdBy.setText(String.format(("%s %s\n%s"),
            user.getFirstName(), user.getFirstName(), user.getEmail()));
      }

      created.setText(mRequest.getCreated());
    }
  }


  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }

  @Override
  public void showMessage(String message) {
    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

  }

  @Override
  public boolean hasInternetConnection(Context context) {
    return false;
  }

  @Override
  public void showProgress() {
    buttonCreate.setEnabled(false);
    mProgressBar.setVisibility(View.VISIBLE);
  }

  @Override
  public void dismissProgress() {
    buttonCreate.setEnabled(true);
    mProgressBar.setVisibility(View.GONE);
  }

  public static CreateVacancyFragment newInstance(Request request) {
    Bundle args = new Bundle();
    args.putSerializable(ARG_REQUEST, request);
    CreateVacancyFragment fragment = new CreateVacancyFragment();
    fragment.setArguments(args);
    return fragment;
  }



  private String getDisplayRequirements(List<Requirement> list) {
    if (list == null) {
      return "";
    }

    StringBuilder builder = new StringBuilder();

    for (Requirement requirement : list) {
      builder.append(requirement.getName()).append("\n");
    }

    return builder.toString();
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

  @Override
  public void onClick(View v) {

    switch (v.getId()) {
      case R.id.cr_vac_create:
        if (mRequest != null) {
          List<String> wConditions = new ArrayList<>();
          wConditions.add(workConditions.getText().toString());

          int minSalaryInt;
          if (minSalary.getText().toString().isEmpty()) {
            minSalaryInt = 0;
          } else {
            minSalaryInt = Integer.parseInt(minSalary.getText().toString());
          }

          int maxSalaryInt;
          if (maxSalary.getText().toString().isEmpty()) {
            maxSalaryInt = 0;
          } else {
            maxSalaryInt = Integer.parseInt(minSalary.getText().toString());
          }

          VacancyRequest vacancyRequest = new VacancyRequest.Builder()
              .setRequestId(mRequest.getId())
              .setCreatedBy(mRequest.getCreatedBy().getId())
              .setTitle(title.getText().toString())
              .setCity(city.getText().toString())
              .setAddress(address.getText().toString())
              .setWorkingConditions(wConditions)
              .setResponsibilities(responsibilities.getText().toString())
              .setSalaryMin(minSalaryInt)
              .setSalaryMax(maxSalaryInt)
              .setComments(comments.getText().toString())
              .build();

          Log.d(TAG, "onClick: " + vacancyRequest.toString());
          mPresenter.onCreateButtonClick(vacancyRequest);
        }
        break;

      default:
        break;
    }
  }
}
