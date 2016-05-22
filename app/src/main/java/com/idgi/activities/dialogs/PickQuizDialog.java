package com.idgi.activities.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.idgi.R;
import com.idgi.core.IQuiz;
import com.idgi.event.ApplicationBus;
import com.idgi.event.BusEvent;
import com.idgi.event.Event;

public class PickQuizDialog extends Dialog{

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
            BusEvent event = new BusEvent(Event.QUIZ_TYPE_SELECTED, IQuiz.Type.NORMAL);
            ApplicationBus.post(event);
            dismiss();
        }
    };

    private final View.OnClickListener onTimedClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            BusEvent event = new BusEvent(Event.QUIZ_TYPE_SELECTED, IQuiz.Type.TIMED);
            ApplicationBus.post(event);
            dismiss();
        }
    };

}
