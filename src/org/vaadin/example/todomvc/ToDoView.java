package org.vaadin.example.todomvc;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class ToDoView extends VerticalLayout {
    private TextField newTodo;
    private CssLayout main;

    private boolean newTodoFocused;

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

        newTodo.addFocusListener(new FocusListener() {

            @Override
            public void focus(FocusEvent event) {
                newTodoFocused = true;
            }
        });
        newTodo.addBlurListener(new BlurListener() {

            @Override
            public void blur(BlurEvent event) {
                newTodoFocused = false;
            }
        });
        addShortcutListener(new ShortcutListener(null, KeyCode.ENTER, null) {

            @Override
            public void handleAction(Object sender, Object target) {
                if (newTodoFocused) {
                    main.addComponent(getNewTodoRow(newTodo.getValue(), false,
                            false));
                    newTodo.setValue("");
                } else {
                    getUI().focus();
                }
            }
        });

        addComponent(new CssLayout() {
            {
                addStyleName("footer");

                Label todoCount = new Label("<b>##</b> items left",
                        ContentMode.HTML);
                todoCount.addStyleName("todo-count");
                todoCount.setSizeUndefined();
                addComponent(todoCount);

                CssLayout filters = new CssLayout();
                filters.addStyleName("filters");
                addComponent(filters);

                NativeButton all = new NativeButton("All");
                all.addStyleName("selected");
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
                if (event.isDoubleClick()
                        && caption == event.getClickedComponent()) {
                    row.addStyleName("editing");
                    edit.selectAll();
                    edit.focus();
                }
            }
        });

        edit.addBlurListener(new BlurListener() {

            @Override
            public void blur(BlurEvent event) {
                row.removeStyleName("editing");
                caption.setValue(edit.getValue());
            }
        });

        destroy.addClickListener(new ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                main.removeComponent(row);
            }
        });

        return row;
    }

}
