package com.idgi.android.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.common.eventbus.Subscribe;
import com.idgi.R;
import com.idgi.android.recyclerview.RecyclerViewUtility;
import com.idgi.core.Course;
import com.idgi.core.Nameable;
import com.idgi.core.NameableType;
import com.idgi.core.School;
import com.idgi.core.Subject;
import com.idgi.event.ApplicationBus;
import com.idgi.event.BusEvent;

import java.util.List;
import java.util.Locale;
import com.idgi.android.recyclerview.adapters.SelectNameableAdapter;

/*
Dialog that presents a list of Nameables.
 */
public class SelectNameableDialog extends Dialog {
	private EditText txtCreateNew;
	private SelectNameableAdapter adapter;
	private List<? extends Nameable> nameables;

	private NameableType type;

	private Nameable selectedNameable;

	public SelectNameableDialog(Context context, List<? extends Nameable> nameables, NameableType type) {
		super(context);
		this.type = type;
		this.nameables = nameables;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.selection_dialog);

		ApplicationBus.register(this);

		loadResources();
		initializeRecyclerView();
	}

	private void loadResources() {
		txtCreateNew = (EditText) findViewById(R.id.create_new_editText);
		Button btnCreateNew = (Button) findViewById(R.id.create_new_button);
		TextView txtTitle = (TextView) findViewById(R.id.title);

		//Load title
		String title = getContext().getResources().getString(R.string.dialog_select_nameable_title);
		txtTitle.setText(String.format(Locale.ENGLISH, title, getItemTypeName(type)));

		//Load hint
		String hint = getContext().getResources().getString(R.string.dialog_select_nameable_hint);
		txtCreateNew.setHint(String.format(Locale.ENGLISH, hint, getItemTypeName(type)));
		btnCreateNew.setOnClickListener(onCreateClick);

		String btnText = getContext().getResources().getString(R.string.dialog_select_nameable_create_new);
		btnCreateNew.setText(String.format(Locale.ENGLISH, btnText, getItemTypeName(type)));
	}

	private void initializeRecyclerView() {
		RecyclerView recyclerView = (RecyclerView) findViewById(R.id.param_list_recycler_view);

		adapter = new SelectNameableAdapter(getContext(), this.nameables);
		RecyclerViewUtility.connect(getContext(), recyclerView, adapter);
	}

	private final View.OnClickListener onCreateClick = new View.OnClickListener() {
		public void onClick(View view) {
			String name = txtCreateNew.getText().toString();
			setSelectedNameable(createNameable(type, name));
			dismiss();
		}
	};

	private Nameable createNameable(NameableType type, String name) {
		switch(type) {
			case SCHOOL:
				return new School(name);
			case SUBJECT:
				return new Subject(name);
			case COURSE:
				return new Course(name);
			default:
				return null;
		}
	}

	// Returns a localized name of the ItemType from resources
	private String getItemTypeName(NameableType itemType) {
		switch (itemType) {
			case SCHOOL:
				return getContext().getResources().getString(R.string.school);
			case SUBJECT:
				return getContext().getResources().getString(R.string.subject);
			case COURSE:
				return getContext().getResources().getString(R.string.course);
		}

		return null;
	}

	private void setSelectedNameable(Nameable nameable) {
		this.selectedNameable = nameable;
	}

	@Subscribe
	public void onNameableSelected(BusEvent busEvent) {
		switch(busEvent.getEvent()) {
			case NAMEABLE_SELECTED:
				setSelectedNameable((Nameable) busEvent.getData());
				dismiss();
				break;
			default:
		}
	}

	@Override
	public void dismiss() {
		if (ApplicationBus.hasListener(this))
			ApplicationBus.unregister(this);
		super.dismiss();
	}

	public Nameable getSelectedNameable() {
		return selectedNameable;
	}
}