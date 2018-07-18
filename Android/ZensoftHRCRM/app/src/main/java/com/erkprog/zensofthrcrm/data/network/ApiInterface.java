package com.erkprog.zensofthrcrm.data.network;

import com.erkprog.zensofthrcrm.data.entity.Candidate;
import com.erkprog.zensofthrcrm.data.entity.CandidatesResponse;
import com.erkprog.zensofthrcrm.data.entity.Interview;
import com.erkprog.zensofthrcrm.data.entity.InterviewRequest;
import com.erkprog.zensofthrcrm.data.entity.InterviewsResponse;
import com.erkprog.zensofthrcrm.data.entity.Request;
import com.erkprog.zensofthrcrm.data.entity.RequestsResponse;
import com.erkprog.zensofthrcrm.data.entity.UsersResponse;
import com.erkprog.zensofthrcrm.data.entity.VacanciesResponse;
import com.erkprog.zensofthrcrm.data.entity.Vacancy;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

public interface ApiInterface {

  @GET("candidates?size=100")
  Single<CandidatesResponse> getCandidates();

  @GET("candidates/{id}")
  Call<Candidate> getDetailedCandidate(@Path("id") int id);

  @GET("interviews/{id}")
  Call<Interview> getDetailedInterview(@Path("id") int id);

  @GET("vacancies/{id}")
  Call<Vacancy> getDetailedVacancy(@Path("id") int id);

  @GET("interviews?size=100")
  Single<InterviewsResponse> getInterviews();

  @PATCH("candidates/{id}")
  Completable updateCandidates(@Path("id") int id, @Header("Content-Type") String content_type,
                               @Body Candidate candidate);

  @GET("vacancies?size=100")
  Single<VacanciesResponse> getVacancies();

  @GET("requests/{id}")
  Call<Request> getDetailedRequest(@Path("id") int id);

  @GET("users?size=100")
  Call<UsersResponse> getUsers();

  //create new interview
  @POST("interviews")
  Call<Interview> postInterview(@Header("Content-Type") String content_type,
                                @Body InterviewRequest request);

  @GET("requests?size=100")
  Single<RequestsResponse> getRequests();
}
