package com.erkprog.zensofthrcrm.ui.vacancies.vacancyDetail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.erkprog.zensofthrcrm.R;

public class VacancyDetail extends AppCompatActivity {

  private static final String EXTRA_VACANCY_ID = "vacancy_id";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_vacancy_detail);

    Bundle args = new Bundle();

    if (getIntent().getExtras() != null) {
      args.putInt(EXTRA_VACANCY_ID, getIntent().getExtras().getInt(EXTRA_VACANCY_ID));
    }

    FragmentManager fm = getSupportFragmentManager();
    Fragment fragment = fm.findFragmentById(R.id.vacancy_detail_container);

    if (fragment == null) {
      fragment = new VacancyDetailFragment();
      fragment.setArguments(args);
      fm.beginTransaction()
          .add(R.id.vacancy_detail_container, fragment)
          .commit();
    }

    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
  }


  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        onBackPressed();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }
}
