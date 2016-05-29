package com.idgi.android.recyclerview.viewHolder;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.idgi.core.NameableType;

/*
Factory for creating ViewHolders that can be used to visually present an Object in the GUI.
 */
public class ViewHolderFactory {

	public static NameableViewHolder createNameableViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
		switch (NameableType.fromInteger(viewType)) {
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
			case USER: case STUDENT: case TEACHER:
				return UserViewHolder.create(inflater, parent);
			default:
				return null;
		}
	}
}
