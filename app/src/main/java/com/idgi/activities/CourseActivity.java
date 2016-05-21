package com.idgi.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.idgi.application.Application;
import com.idgi.activities.dialogs.PickQuizDialog;
import com.idgi.core.IQuiz;
import com.idgi.core.Lesson;
import com.idgi.core.Nameable;
import com.idgi.core.TimedQuiz;
import com.idgi.event.BusEvent;
import com.idgi.event.Event;
import com.idgi.event.NameableSelectionBus;
import com.idgi.fragments.CourseInfoFragment;
import com.idgi.fragments.CourseLessonListFragment;
import com.idgi.fragments.CourseQuizListFragment;
import com.idgi.R;
import com.idgi.activities.extras.DrawerActivity;
import com.idgi.fragments.CourseUserListFragment;
import com.idgi.session.SessionData;

import java.util.ArrayList;
import java.util.List;

public class CourseActivity extends DrawerActivity implements NameableSelectionBus.Listener{

    private ViewPager viewPager;
    private ViewPagerAdapter pagerAdapter;
    private IQuiz selectedQuiz;

    private final EventBus bus = Application.getEventBus();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);


		String courseName = SessionData.getCurrentCourse().getName();
        initializeWithTitle(courseName);
        setupViewPager();
    }

    private void setupViewPager() {
        viewPager = (ViewPager) findViewById(R.id.course_pager);

        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

		CourseLessonListFragment lessonListFragment = new CourseLessonListFragment();
        lessonListFragment.addListener(this);
        CourseQuizListFragment quizListFragment = new CourseQuizListFragment();
        quizListFragment.addListener(this);

        pagerAdapter.addFragment(lessonListFragment, "Lessons");
        pagerAdapter.addFragment(quizListFragment, "Quiz");
        pagerAdapter.addFragment(new CourseInfoFragment(), "Info");
        pagerAdapter.addFragment(new CourseUserListFragment(), "Users");

        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(pageChangeListener);
        //refreshViewPager();

        TabLayout tabLayout = (TabLayout) findViewById(R.id.course_tab_layout);
		if (tabLayout != null)
        	tabLayout.setupWithViewPager(viewPager);
    }

	// TODO remove? Not used as of writing this (17/05/2016)
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

    static class ViewPagerAdapter extends FragmentPagerAdapter {
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
                    getResources().getString(R.string.course_no_users)
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

    //On nameable selected
	@Override
	public void onNameableSelected(Nameable nameable) {
        if(nameable instanceof Lesson) {
            Lesson lesson = (Lesson) nameable;
            SessionData.setCurrentLesson(lesson);
            startActivity(new Intent(this, LessonActivity.class));
        } else if(nameable instanceof IQuiz){
            selectedQuiz = (IQuiz) nameable;
            SessionData.setCurrentQuiz(selectedQuiz);

            Dialog dialog = new PickQuizDialog(this);

            //Subscribe for quiz-type-events
            bus.register(this);

            dialog.show();
            dialog.getWindow().setGravity(Gravity.CENTER);
        }
	}

    @Subscribe
    public void onQuizTypeSelected(BusEvent busEvent) {
        if(busEvent.getEvent() == Event.QUIZ_SELECTED) {
            IQuiz.Type quizType = (IQuiz.Type) busEvent.getData();

            if(quizType == IQuiz.Type.TIMED) {
                SessionData.setCurrentQuiz(TimedQuiz.create(selectedQuiz));
            } else if(quizType == IQuiz.Type.NORMAL) {
                SessionData.setCurrentQuiz(selectedQuiz);
            }
        }

        bus.unregister(this);
        startActivity(new Intent(this, QuizActivity.class));

    }
}
