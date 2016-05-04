package com.idgi;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.idgi.core.Lesson;
import com.idgi.services.Database;
import com.idgi.util.AppCompatActivityWithDrawer;
import com.idgi.util.LessonListAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CourseActivity extends AppCompatActivityWithDrawer {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private RecyclerView recycler;
    private RecyclerView.Adapter recyclerAdapter;
    private RecyclerView.LayoutManager recyclerManager;

    private Database database = Database.getInstance();

    //should be list of Lessons, when working
    private ArrayList<Lesson> lessons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.course_pager);

        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new CourseLessonListFragment(), "Lessons");
        pagerAdapter.addFragment(new CourseQuizListFragment(), "Quiz");
        pagerAdapter.addFragment(new CourseInfoFragment(), "Info");

        viewPager.setAdapter(pagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.course_tab_layout);
        tabLayout.setupWithViewPager(viewPager);


/*        Bundle bundle = getIntent().getExtras();
       String s = bundle.getString("courseName"); */

        initializeDrawer();
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentNameList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentNameList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentNameList.get(position);
        }
    }
}
