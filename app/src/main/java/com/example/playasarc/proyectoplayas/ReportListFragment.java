package com.example.playasarc.proyectoplayas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

/**
 * Created by Ramon on 27/02/2018.
 */

public class ReportListFragment extends Fragment {
    private RecyclerView mReportRecyclerView;
    private ReportAdapter mAdapter;
    private UUID id_beach;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report_list, container, false);
        mReportRecyclerView = (RecyclerView) view
                .findViewById(R.id.report_recycler_view);
        mReportRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        String idAux = getArguments().getString("id_beach",null);
        id_beach = UUID.fromString(idAux);
        setHasOptionsMenu(true);
        updateUI();
        return view;
    }

    private void updateUI() {
        BeachReportManager beachReportManager = BeachReportManager.get(getActivity());
        List<BeachReport> beachReport = beachReportManager.getmBeachReports(id_beach);
        mAdapter = new ReportAdapter(beachReport);
        mReportRecyclerView.setAdapter(mAdapter);
    }

    private class ReportHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private BeachReport mBeachReport;

        public ReportHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView)
                    itemView.findViewById(R.id.list_item_report_title_text_view);
            mDateTextView = (TextView)
                    itemView.findViewById(R.id.list_item_report_date_text_view);
           
        }
        public void bindBeachReport(BeachReport beachReport) {
            mBeachReport = beachReport;
            mTitleTextView.setText((int) mBeachReport.getmTemperature()+" ºC - "+mBeachReport.getmColorFlag());
            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy, HH:mm");
            String dateCorrect = formatter.format(mBeachReport.getmDate());
            mDateTextView.setText(dateCorrect);
        }

        @Override
        public void onClick(View v) {
            Intent intent = ReportPagerActivity.newIntent(getActivity(), mBeachReport.getmId());
            intent.putExtra("id_beach",String.valueOf(mBeachReport.getmIdBeach()));
            startActivity(intent);
        }
    }

    private class ReportAdapter extends RecyclerView.Adapter<ReportHolder> {
        private List<BeachReport> mBeachReports;
        public ReportAdapter(List<BeachReport> beachReports) {
            mBeachReports = beachReports;
        }
        @Override
        public ReportHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(R.layout.list_item_report, parent, false);
            return new ReportHolder(view);
        }
        @Override
        public void onBindViewHolder(ReportHolder holder, int position) {
            BeachReport br = mBeachReports.get(position);
            holder.bindBeachReport(br);
        }
        @Override
        public int getItemCount() {
            return mBeachReports.size();
        }
    }
}
