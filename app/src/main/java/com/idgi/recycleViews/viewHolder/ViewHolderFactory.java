package com.idgi.recycleViews.viewHolder;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.idgi.core.Nameable;

public class ViewHolderFactory {

	public static NameableViewHolder createNameableViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
		switch (Nameable.Type.fromInteger(viewType)) {
			case SCHOOL:
				return SchoolViewHolder.create(inflater, parent);
			case SUBJECT:
				return SubjectViewHolder.create(inflater, parent);
			case COURSE:
				return CourseViewHolder.create(inflater, parent);
			case LESSON:
				return LessonViewHolder.create(inflater, parent);
			case QUIZ:
				return QuizViewHolder.create(inflater, parent);
			case HAT:
				return HatViewHolder.create(inflater, parent);
			default:
				return null;
		}
	}
}
