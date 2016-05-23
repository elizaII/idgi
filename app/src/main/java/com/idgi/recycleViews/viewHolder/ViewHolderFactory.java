package com.idgi.recycleViews.viewHolder;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.idgi.core.Nameable;
import com.idgi.event.NameableSelectionBus;

public class ViewHolderFactory {

	public static NameableViewHolder createNameableViewHolder(LayoutInflater inflater, ViewGroup parent, NameableSelectionBus bus, int viewType) {
		switch (Nameable.Type.fromInteger(viewType)) {
			case SCHOOL:
				return SchoolViewHolder.create(inflater, parent, bus);
			case SUBJECT:
				return SubjectViewHolder.create(inflater, parent, bus);
			case COURSE:
				return CourseViewHolder.create(inflater, parent, bus);
			case LESSON:
				return LessonViewHolder.create(inflater, parent, bus);
			case QUIZ:
				return QuizViewHolder.create(inflater, parent, bus);
			case HAT:
				return HatViewHolder.create(inflater, parent, bus);
			default:
				return null;
		}
	}
}
