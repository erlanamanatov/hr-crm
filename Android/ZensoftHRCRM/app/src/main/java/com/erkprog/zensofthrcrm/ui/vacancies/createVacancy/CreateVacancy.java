package com.erkprog.zensofthrcrm.ui.vacancies.createVacancy;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.erkprog.zensofthrcrm.R;
import com.erkprog.zensofthrcrm.data.entity.Request;

public class CreateVacancy extends AppCompatActivity {

  private static final String TAG = "CREATE VACANCY";
  private static final String EXTRA_REQUEST = "EXTRA REQUEST";
  private static final String EXTRA_VACANCY = "EXTRA VACANCY";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_create_vacancy);

    if (getIntent() != null) {
      Request request = (Request) getIntent().getSerializableExtra(EXTRA_REQUEST);
      Log.d(TAG, "onCreate: " + request.getPosition().getName());
    }

    FragmentManager fm = getSupportFragmentManager();
    Fragment fragment = fm.findFragmentById(R.id.create_vacancy_fragment_container);

    if (fragment == null) {
      fragment = CreateVacancyFragment.newInstance();
//      fragment.setArguments(args);
      fm.beginTransaction()
          .add(R.id.create_vacancy_fragment_container, fragment)
          .commit();
    }
  }

  public static Intent newIntent(Context context, Request request) {
    Intent intent = new Intent(context, CreateVacancy.class);
    intent.putExtra(EXTRA_REQUEST, request);
    return intent;
  }
}
