package com.erkprog.zensofthrcrm.ui.requests.requestsList;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.erkprog.zensofthrcrm.CRMApplication;
import com.erkprog.zensofthrcrm.R;
import com.erkprog.zensofthrcrm.data.entity.Request;
import com.erkprog.zensofthrcrm.ui.ItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RequestsFragment extends Fragment implements RequestsContract.View,
    ItemClickListener<Request> {
  private static final String TAG = "REQUESTS FRAGMENT";

  private RequestsContract.Presenter mPresenter;
  private RequestsAdapter mAdapter;

  @BindView(R.id.recycler_view_all_requests)
  RecyclerView mRecyclerView;
  @BindView(R.id.txt_empty_requests_view)
  TextView noRequestsView;
  @BindView(R.id.requests_progress_bar)
  ProgressBar mProgressBar;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initPresenter();
  }

  private void initPresenter() {
    mPresenter = new RequestsPresenter(
        CRMApplication.getInstance(requireContext()).getApiService(), CRMApplication.getInstance
        (requireContext()).getSQLiteHelper());
    mPresenter.bind(this);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_requests_list, container, false);

    ButterKnife.bind(this, v);

    dismissProgress();
    initRecyclerView(v);
    return v;
  }

  private void initRecyclerView(View v) {
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
    mRecyclerView.setLayoutManager(layoutManager);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    showProgress();
    if (hasInternetConnection(view.getContext())) {
      mPresenter.getRequestsInternet();
    } else {
      mPresenter.getRequestsLocal();
    }
  }

  public static RequestsFragment newInstance() {
    return new RequestsFragment();
  }

  @Override
  public void showRequests(List<Request> requests) {
    dismissProgress();
    if (requests.size() > 0) {
      noRequestsView.setVisibility(View.GONE);
      mAdapter = new RequestsAdapter(requests, this);
      mRecyclerView.setAdapter(mAdapter);
    } else
      noRequestsView.setVisibility(View.VISIBLE);
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
  public void onItemClick(Request item) {
    mPresenter.onRequestItemClick(item);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    mPresenter.unbind();
  }
}
