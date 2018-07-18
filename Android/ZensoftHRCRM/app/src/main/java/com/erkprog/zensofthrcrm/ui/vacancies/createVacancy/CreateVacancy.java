package com.erkprog.zensofthrcrm.ui.vacancies.createVacancy;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.erkprog.zensofthrcrm.R;

public class CreateVacancy extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_create_vacancy);

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
}
