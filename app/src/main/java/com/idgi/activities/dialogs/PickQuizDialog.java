package com.idgi.activities.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.google.common.eventbus.EventBus;
import com.idgi.Application;
import com.idgi.R;
import com.idgi.core.IQuiz;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class PickQuizDialog extends Dialog{

    private final EventBus bus = Application.getEventBus();
    private Button normalQuizBtn, timedQuizBtn;

    public PickQuizDialog(Context context) {
        super(context);
    }

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pick_quiz_dialog);

        normalQuizBtn = (Button) findViewById(R.id.pick_quiz_dialog_btn_normal);
        timedQuizBtn = (Button) findViewById(R.id.pick_quiz_dialog_btn_timed);

        normalQuizBtn.setOnClickListener(onNormalClick);
        timedQuizBtn.setOnClickListener(onTimedClick);
    }

    private final View.OnClickListener onNormalClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            bus.post(IQuiz.Type.NORMAL);
            dismiss();
        }
    };

    private final View.OnClickListener onTimedClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            bus.post(IQuiz.Type.TIMED);
            dismiss();
        }
    };

}
