package com.idgi.android.activities.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.idgi.R;
import com.idgi.android.recycleViews.adapters.SelectNameableAdapter;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Locale;

public class SelectNameableDialog extends Dialog implements PropertyChangeListener {
    private EditText txtCreateNew;
    private SelectNameableAdapter adapter;
    private List<String> itemNames;

	private String itemTypeName;

	private String selectedItemText = "";

    public SelectNameableDialog(Context context, String itemTypeName, List<String> itemNames) {
        super(context);
		this.itemTypeName = itemTypeName;
        this.itemNames = itemNames;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.selection_dialog);

		loadResources();
		initializeRecyclerView();
    }

	private void loadResources() {
		txtCreateNew = (EditText) findViewById(R.id.create_new_editText);
		Button btnCreateNew = (Button) findViewById(R.id.create_new_button);
		TextView txtTitle = (TextView) findViewById(R.id.title);

		//Load title
		String title = getContext().getResources().getString(R.string.dialog_select_nameable_title);
		txtTitle.setText(String.format(Locale.ENGLISH, title, itemTypeName));

		//Load hint
		String hint = getContext().getResources().getString(R.string.dialog_select_nameable_hint);
		txtCreateNew.setHint(String.format(Locale.ENGLISH, hint, itemTypeName));
		btnCreateNew.setOnClickListener(onCreateClick);
	}

	private void initializeRecyclerView() {
		RecyclerView recyclerView = (RecyclerView) findViewById(R.id.param_list_recycler_view);
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

		adapter = new SelectNameableAdapter(getContext(), this.itemNames);
		adapter.addPropertyChangeListener(this);
		recyclerView.setAdapter(adapter);
	}

    /* Triggers when you click an element in the list, like a School or Subject. */
    private final View.OnClickListener onCreateClick = new View.OnClickListener() {
        public void onClick(View view) {
            String itemText = txtCreateNew.getText().toString();
			setSelectedItemText(itemText);
			dismiss();
        }
    };

	private void setSelectedItemText(String itemName) {
		this.selectedItemText = itemName;
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		switch(event.getPropertyName()) {
			case "listItemSelected":
				String itemText = (String) event.getNewValue();
				setSelectedItemText(itemText);
				dismiss();
				break;
		}
	}

	public String getSelectedItemText() {
		return selectedItemText;
	}
}

