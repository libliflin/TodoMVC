package org.vaadin.example.todomvc;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
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

        addComponent(new CssLayout() {
            {
                addStyleName("footer");

                Label todoCount = new Label("## items left");
                todoCount.addStyleName("todo-count");
                todoCount.setSizeUndefined();
                addComponent(todoCount);

                HorizontalLayout filters = new HorizontalLayout();
                filters.addStyleName("filters");
                addComponent(filters);

                NativeButton all = new NativeButton("All");
                NativeButton active = new NativeButton("Active");
                NativeButton completed = new NativeButton("Completed");
                filters.addComponents(all, active, completed);

                NativeButton clearCompleted = new NativeButton(
                        "Clear completed (#)");
                clearCompleted.addStyleName("clear-completed");
                addComponent(clearCompleted);

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

        final Label caption = new Label(captionText);
        caption.setSizeUndefined();
        row.addComponent(caption);

        NativeButton destroy = new NativeButton();
        destroy.addStyleName("destroy");
        row.addComponent(destroy);

        final TextField edit = new TextField();
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
        
        row.addLayoutClickListener(new LayoutClickListener() {
			
			@Override
			public void layoutClick(LayoutClickEvent event) {
				if (event.isDoubleClick() && caption == event.getClickedComponent()) {
					row.addStyleName("editing");
					edit.selectAll();
					edit.focus();
				}
			}
		});
        
        return row;
    }

}
