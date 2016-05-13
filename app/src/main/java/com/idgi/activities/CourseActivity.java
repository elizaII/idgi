package com.idgi.activities;

import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.idgi.fragments.CourseInfoFragment;
import com.idgi.fragments.CourseLessonListFragment;
import com.idgi.fragments.CourseQuizListFragment;
import com.idgi.R;
import com.idgi.activities.extras.DrawerActivity;
import com.idgi.session.SessionData;

import java.util.ArrayList;
import java.util.List;

public class CourseActivity extends DrawerActivity {

    private enum Section {
        LESSON, QUIZ, INFO;

        public static Section fromInteger(int x) {
            switch (x) {
                case 0:
                    return LESSON;
                case 1:
                    return QUIZ;
                case 2:
                    return INFO;
                default:
                    return INFO;
            }
        }
    }

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView emptyViewText;

    private RecyclerView recycler;
    private RecyclerView.Adapter recyclerAdapter;
    private RecyclerView.LayoutManager recyclerManager;

    private ViewPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        String courseName = SessionData.getCurrentCourse().getName();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(courseName);

        initializeDrawer();

        setupViewPager();
    }

    private void setupViewPager() {
        viewPager = (ViewPager) findViewById(R.id.course_pager);

        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new CourseLessonListFragment(), "Lessons");
        pagerAdapter.addFragment(new CourseQuizListFragment(), "Quiz");
        pagerAdapter.addFragment(new CourseInfoFragment(), "Info");

        viewPager.setAdapter(pagerAdapter);

        viewPager.addOnPageChangeListener(pageChangeListener);
        //refreshViewPager();

        tabLayout = (TabLayout) findViewById(R.id.course_tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    /* Makes sure the appropriate values (like empty-list text message) are used */
    private void refreshViewPager() {
        //We do it this way since there's no refresh() equivalent method.
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                View view = pagerAdapter.getItem(1).getView();
                if (view != null) {
                    viewPager.setCurrentItem(1);
                    viewPager.setCurrentItem(0);
                }
            }
        }, 50);

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

    private final ViewPager.OnPageChangeListener pageChangeListener =  new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {

            String messages[] = {
                    getResources().getString(R.string.course_no_lessons),
                    getResources().getString(R.string.course_no_quizzes),
                    getResources().getString(R.string.course_no_info),
            };

            String text = messages[position];
            View view = pagerAdapter.getItem(position).getView();

            updateEmptyTextInView(view, text);

        }
    };

    /* Updates the text that is shown if a list is empty */
    private void updateEmptyTextInView(View view, String text) {

        TextView textView = (TextView) view.findViewById(R.id.lesson_list_empty_view_text);
        textView.setText(text);
    }
}
