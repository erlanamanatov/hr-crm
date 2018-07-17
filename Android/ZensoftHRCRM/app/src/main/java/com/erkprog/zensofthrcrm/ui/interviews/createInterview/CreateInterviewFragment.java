package com.erkprog.zensofthrcrm.ui.interviews.createInterview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.erkprog.zensofthrcrm.CRMApplication;
import com.erkprog.zensofthrcrm.R;
import com.erkprog.zensofthrcrm.data.entity.User;
import com.erkprog.zensofthrcrm.ui.interviews.createInterview.interviewers.InterviewersFragment;
import com.erkprog.zensofthrcrm.util.DateConverter;

import java.util.ArrayList;
import java.util.Date;

public class CreateInterviewFragment extends Fragment implements CreateInterviewContract.View, View
    .OnClickListener {
  private static final String TAG = "CREATE INTRVW FRAGMENT";

  public static final String CANDIDATE_ID = "candidate id";
  public static final String CANDIDATE_LASTNAME = "candidate last name";
  public static final String CANDIDATE_FIRSTNAME = "candidate first name";
  public static final String CANDIDATE_DEPARTMENT = "candidate department";
  private static final String DATE_AND_TIME_DIALOG = "Date and time dialog";
  private static final String INTERVIEWERS_DIALOG = "add interviewers";
  public static final int REQUEST_DATE_CODE = 7;
  public static final int REQUEST_ADD_INTERVIEWERS = 1;

  private CreateInterviewContract.Presenter mPresenter;

  private TextView mCandidateName;
  private TextView mDepartmentName;
  private TextView mDate;
  private TextView mInterviewers;
  private Button mSetDateButton;
  private ImageView mAddInterviewersButton;
  private Button mCreateButton;
  private ProgressBar mProgressBar;

  private Date mInterviewDate;
  private int mCandidateId;
  private ArrayList<Integer> mInterviewersId;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initPresenter();
  }

  private void initPresenter() {
    mPresenter = new CreateInterviewPresenter(requireContext(), CRMApplication.getInstance
        (requireContext()).getApiService());
    mPresenter.bind(this);
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_create_interview, container, false);

    mInterviewDate = new Date();
    initUI(v);
    setFields();

    Log.d(TAG, "onCreateView: " + mCandidateId);

    return v;
  }

  private void initUI(View v) {
    mCandidateName = v.findViewById(R.id.crint_candidate_name);
    mDepartmentName = v.findViewById(R.id.crint_department);
    mDate = v.findViewById(R.id.crint_date);
    mInterviewers = v.findViewById(R.id.crint_interviewers);
    mSetDateButton = v.findViewById(R.id.crint_set_date_button);
    mSetDateButton.setOnClickListener(this);
    mAddInterviewersButton = v.findViewById(R.id.crint_add_image);
    mAddInterviewersButton.setOnClickListener(this);
    mCreateButton = v.findViewById(R.id.crint_create_button);
    mCreateButton.setOnClickListener(this);
    mProgressBar = v.findViewById(R.id.crint_progress_bar);
    dismissProgress();
  }

  private void setFields() {
    Bundle args = getArguments();
    StringBuilder candidateName = new StringBuilder();
    candidateName.append(args.getString(CANDIDATE_FIRSTNAME))
        .append(" ")
        .append(args.getString(CANDIDATE_LASTNAME));
    mCandidateName.setText(candidateName);
    mDepartmentName.setText(args.getString(CANDIDATE_DEPARTMENT));
    mCandidateId = args.getInt(CANDIDATE_ID);
    mDate.setText(mInterviewDate.toString());
  }

  public static CreateInterviewFragment newInstance(int candidateId, String lastName, String
      firstname, String departmentName) {
    Bundle arguments = new Bundle();
    arguments.putInt(CANDIDATE_ID, candidateId);
    arguments.putString(CANDIDATE_LASTNAME, lastName);
    arguments.putString(CANDIDATE_FIRSTNAME, firstname);
    arguments.putString(CANDIDATE_DEPARTMENT, departmentName);
    CreateInterviewFragment fragment = new CreateInterviewFragment();
    fragment.setArguments(arguments);
    return fragment;
  }

  @Override
  public void startDatePicker() {
    FragmentManager fm = getFragmentManager();
    DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(mInterviewDate);
    datePickerFragment.setTargetFragment(CreateInterviewFragment.this, REQUEST_DATE_CODE);
    datePickerFragment.show(fm, DATE_AND_TIME_DIALOG);
  }

  public void startAddInterviewers() {
    FragmentManager fm = getFragmentManager();
    InterviewersFragment interviewersFragment = InterviewersFragment.newInstance();
    interviewersFragment.setTargetFragment(CreateInterviewFragment.this, REQUEST_ADD_INTERVIEWERS);
    interviewersFragment.show(fm, INTERVIEWERS_DIALOG);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.crint_set_date_button:
        mPresenter.onSetDateButtonClick();
        break;

      case R.id.crint_add_image:
        startAddInterviewers();
        break;

      case R.id.crint_create_button:
        mPresenter.onCreateButtonClick(mCandidateId, mInterviewersId, DateConverter
            .getFormattedDate(mInterviewDate));
        break;

      default:
        break;
    }
  }

  @Override
  public void showMessage(String message) {
    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
  }

  @Override
  public boolean hasInternetConnection(Context context) {
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
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (resultCode != Activity.RESULT_OK) {
      return;
    }

    if (requestCode == REQUEST_DATE_CODE) {
      //New date and time for interview sucessfully received from DatePickerFragment
      Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
      updateInterviewDate(date);
    }

    if (requestCode == REQUEST_ADD_INTERVIEWERS) {
      ArrayList<User> users = (ArrayList<User>) data.getSerializableExtra(InterviewersFragment.USERS);

      mInterviewersId = getListId(users);

      showInterviewers(users);
    }
  }

  private ArrayList<Integer> getListId(ArrayList<User> users) {
    ArrayList<Integer> listId = new ArrayList<>();
    for (User user : users) {
      listId.add(user.getId());
    }
    return listId;
  }

  private void showInterviewers(ArrayList<User> users) {
    StringBuilder interviewers = new StringBuilder();
    for (User user : users) {
      interviewers.append(String.format("%s %s (%s)\n", user.getFirstName(), user.getLastName(),
          user.getEmail()));
      Log.d(TAG, "onActivityResult: " + user.getEmail());
    }
    mInterviewers.setText(interviewers);
  }

  private void updateInterviewDate(Date date) {
    mInterviewDate = date;
    mDate.setText(mInterviewDate.toString());
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    mPresenter.unbind();
  }
}
