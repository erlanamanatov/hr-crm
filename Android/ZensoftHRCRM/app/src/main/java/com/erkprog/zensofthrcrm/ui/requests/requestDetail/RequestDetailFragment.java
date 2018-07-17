package com.erkprog.zensofthrcrm.ui.requests.requestDetail;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.erkprog.zensofthrcrm.R;
import com.erkprog.zensofthrcrm.data.entity.Request;
import com.erkprog.zensofthrcrm.data.entity.Requirement;

import java.util.List;

public class RequestDetailFragment extends DialogFragment {
  private static final String ARG_REQUEST = "argument request";
  private Request mRequest;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mRequest = (Request) getArguments().getSerializable(ARG_REQUEST);
    }
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    View v = LayoutInflater.from(getContext()).inflate(R.layout.fragment_request_detail, null);

    TextView name = v.findViewById(R.id.req_detail_name);
    TextView department = v.findViewById(R.id.req_detail_department);
    TextView created_by = v.findViewById(R.id.req_detail_created_by);
    TextView count = v.findViewById(R.id.req_detail_count);
    TextView status = v.findViewById(R.id.req_detail_status);
    TextView requirements = v.findViewById(R.id.req_detail_requirements);
    TextView created = v.findViewById(R.id.req_detail_created);
    TextView modified = v.findViewById(R.id.req_detail_modified);

    if (mRequest != null) {
      if (mRequest.getPosition() != null) {
        name.setText(mRequest.getPosition().getName());

        if (mRequest.getPosition().getDepartment() != null) {
          department.setText(mRequest.getPosition().getDepartment().getName());
        }
      }

      if (mRequest.getCreatedBy() != null) {
        created_by.setText(String.format("%s %s\n%s", mRequest.getCreatedBy().getFirstName(),
            mRequest.getCreatedBy().getLastName(), mRequest.getCreatedBy().getEmail()));
      }

      count.setText(String.valueOf(mRequest.getCount()));
      status.setText(mRequest.getStatus());
      requirements.setText(getDisplayRequirements(mRequest.getRequirementList()));
      created.setText(mRequest.getCreated());
      modified.setText(mRequest.getModified());
    }

    final AlertDialog dialog = new AlertDialog.Builder(getActivity())
        .setView(v)
        .setPositiveButton(R.string.create_vacancy, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {

          }
        })
        .create();

    dialog.setOnShowListener(new DialogInterface.OnShowListener() {
      @Override
      public void onShow(DialogInterface arg0) {
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getContext().getResources()
            .getColor(R.color.colorAccent));
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(14);
      }
    });

    return dialog;
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

  public static RequestDetailFragment newInstance(Request request) {
    Bundle args = new Bundle();
    args.putSerializable(ARG_REQUEST, request);
    RequestDetailFragment fragment = new RequestDetailFragment();
    fragment.setArguments(args);
    return fragment;
  }
}
