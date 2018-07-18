package com.erkprog.zensofthrcrm.ui.candidates.candidatesList;

import android.content.Context;

import com.erkprog.zensofthrcrm.R;
import com.erkprog.zensofthrcrm.data.db.SQLiteHelper;
import com.erkprog.zensofthrcrm.data.entity.Candidate;
import com.erkprog.zensofthrcrm.data.entity.CandidatesResponse;
import com.erkprog.zensofthrcrm.data.network.ApiInterface;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CandidatesPresenter implements CandidatesContract.Presenter {

  private CandidatesContract.View mView;
  private ApiInterface mService;
  private SQLiteHelper mSQLiteHelper;
  private CompositeDisposable disposable = new CompositeDisposable();

  CandidatesPresenter(ApiInterface service, SQLiteHelper
      sqliteHelper) {
    mService = service;
    mSQLiteHelper = sqliteHelper;
  }

  private boolean isViewAttached() {
    return mView != null;
  }

  @Override
  public void getCandidatesInternet() {
    disposable.add(
        mService.getCandidates()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<CandidatesResponse>() {
              @Override
              public void accept(CandidatesResponse candidatesResponse) throws Exception {
                if (isViewAttached()) {
                  if (candidatesResponse != null && candidatesResponse.getCandidateList() != null) {
                    mSQLiteHelper.saveCandidates(candidatesResponse.getCandidateList());
                    mView.showCandidates(candidatesResponse.getCandidateList());
                  }
                }
              }
            }, new Consumer<Throwable>() {
              @Override
              public void accept(Throwable throwable) throws Exception {
                if (isViewAttached()) {
                  mView.showMessage(throwable.getMessage());
                  getCandidatesLocal();
                }
              }
            })
    );
  }

  @Override
  public void getCandidatesLocal() {
    List<Candidate> candidates = mSQLiteHelper.getCandidates();
    if (candidates != null) {
      mView.showCandidates(candidates);
    }
  }

  @Override
  public void onCandidateItemClick(Candidate candidate) {
    mView.showCandidateDetailUi(candidate.getId());
  }

  @Override

  public void bind(CandidatesContract.View view) {
    mView = view;
  }

  @Override
  public void unbind() {
    mView = null;
  }
}
