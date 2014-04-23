package org.vaadin.example.todomvc;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class ToDoView extends VerticalLayout implements View {
	private TextField newTodo;
    private CssLayout main;

	public ToDoView() {
        Label title = new Label("todos");
        title.addStyleName("title");
        addComponent(title);

        newTodo = new TextField();
        newTodo.addStyleName("new-todo");
        newTodo.setInputPrompt("What needs to be done?");
        addComponent(newTodo);

        addComponent(main = new CssLayout() {
            {
                addStyleName("main");
                CheckBox toggleAll = new CheckBox("Mark all as complete");
                toggleAll.addStyleName("toggle-all");
                addComponent(toggleAll);

                addComponent(getNewTodoRow("This item is done", true, false));
                addComponent(getNewTodoRow(
                        "This one is still not done, and has a very long caption which should span a few lines",
                        false, false));
                addComponent(getNewTodoRow(
                        "This one is not done and is being edited", false, true));
            }
        });
        
        newTodo.addShortcutListener(new ShortcutListener(null, KeyCode.ENTER, null) {
			
			@Override
			public void handleAction(Object sender, Object target) {
				main.addComponent(getNewTodoRow(newTodo.getValue(), false, false));
			}
		});

    }

    @Override
    public void enter(ViewChangeEvent event) {
        // TODO Auto-generated method stub

    }

    CssLayout getNewTodoRow(String captionText, boolean done, boolean editing) {
        final CssLayout row = new CssLayout();
        row.addStyleName("todo-row");
        if (done) {
            row.addStyleName("completed");
        }
        if (editing) {
            row.addStyleName("editing");
        }

        final CheckBox completed = new CheckBox(null, done);
        row.addComponent(completed);

        Label caption = new Label(captionText);
        caption.setSizeUndefined();
        row.addComponent(caption);

        NativeButton destroy = new NativeButton();
        destroy.addStyleName("destroy");
        row.addComponent(destroy);

        TextField edit = new TextField();
        edit.setValue(captionText);
        row.addComponent(edit);

        completed.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				if (completed.getValue().booleanValue()) {
					row.addStyleName("completed");
				} else {
					row.removeStyleName("completed");
				}
			}
		});
        
        return row;
    }

}