package com.erkprog.zensofthrcrm.ui.interviews.interviewsList;

import com.erkprog.zensofthrcrm.data.db.SQLiteHelper;
import com.erkprog.zensofthrcrm.data.entity.Interview;
import com.erkprog.zensofthrcrm.data.entity.InterviewsResponse;
import com.erkprog.zensofthrcrm.data.network.ApiInterface;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import java.util.List;


public class InterviewsPresenter implements InterviewsContract.Presenter {

  private InterviewsContract.View mView;
  private ApiInterface mService;
  private SQLiteHelper mSQLiteHelper;
  private CompositeDisposable disposable = new CompositeDisposable();


  InterviewsPresenter(InterviewsContract.View view, ApiInterface service, SQLiteHelper
      sqliteHelper) {
    mView = view;
    mService = service;
    mSQLiteHelper = sqliteHelper;
  }


  @Override
  public void onInterviewItemClick(Interview interview) {
    mView.showInterviewDetailUi(interview.getId());
  }

  @Override
  public void getInterviewsInternet() {
    disposable.add(
        mService.getInterviews()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<InterviewsResponse>() {
              @Override
              public void accept(InterviewsResponse interviewsResponse) throws Exception {
                if (isViewAttached()) {
                  if (interviewsResponse != null && interviewsResponse.getInterviewList() != null) {
                    mSQLiteHelper.saveInterviews(interviewsResponse.getInterviewList());
                    mView.showInterviews(interviewsResponse.getInterviewList());
                  }
                }
              }
            }, new Consumer<Throwable>() {
              @Override
              public void accept(Throwable throwable) throws Exception {
                if (isViewAttached()) {
                  mView.showMessage(throwable.getMessage());
                  getInterviewsLocal();
                }
              }
            })
    );

  }

  @Override
  public void getInterviewsLocal() {
    List<Interview> interviews = mSQLiteHelper.getInterviews();
    if (interviews != null) {
      mView.showInterviews(interviews);
    }
  }

  @Override
  public void bind(InterviewsContract.View view) {
    mView = view;
  }

  @Override
  public void unbind() {
    mView = null;
  }

  private boolean isViewAttached() {
    return mView != null;
  }

}