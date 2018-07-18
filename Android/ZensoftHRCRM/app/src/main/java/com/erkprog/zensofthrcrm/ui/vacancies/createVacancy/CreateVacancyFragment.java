package com.erkprog.zensofthrcrm.ui.vacancies.createVacancy;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.erkprog.zensofthrcrm.CRMApplication;
import com.erkprog.zensofthrcrm.R;
import com.erkprog.zensofthrcrm.data.entity.Request;
import com.erkprog.zensofthrcrm.data.entity.Requirement;
import com.erkprog.zensofthrcrm.data.entity.User;

import java.util.List;

public class CreateVacancyFragment extends Fragment implements CreateVacancyContract.View {

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
}
