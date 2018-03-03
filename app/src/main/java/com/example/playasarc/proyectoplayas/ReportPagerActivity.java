package com.example.playasarc.proyectoplayas;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.List;
import java.util.UUID;

/**
 * Created by Ramon on 27/02/2018.
 */

public class ReportPagerActivity extends FragmentActivity {
    private static final String EXTRA_REPORT_ID =
            "com.example.playasarc.proyectoplayas.report_id";
    private ViewPager mViewPager;
    private List<BeachReport> mBeachReport;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_pager);
        UUID reportID = (UUID) getIntent()
                .getSerializableExtra(EXTRA_REPORT_ID);
        UUID id_beach = UUID.fromString(getIntent().getExtras().getString("id_beach"));
        mViewPager = (ViewPager) findViewById(R.id.activity_report_pager_view_pager);
        mBeachReport = BeachReportManager.get(this).getmBeachReports(id_beach);
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                BeachReport br = mBeachReport.get(position);
                return ReportFragment.newInstance(br.getmId());
            }
            @Override
            public int getCount() {
                return mBeachReport.size();
            }
        });

        for (int i = 0; i < mBeachReport.size(); i++) {
            if (mBeachReport.get(i).getmId().equals(reportID)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }

    public static Intent newIntent(Context packageContext, UUID reportID) {
        Intent intent = new Intent(packageContext, ReportPagerActivity.class);
        intent.putExtra(EXTRA_REPORT_ID, reportID);
        return intent;
    }
}
