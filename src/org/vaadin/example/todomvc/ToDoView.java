package org.vaadin.example.todomvc;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class ToDoView extends VerticalLayout implements View {

    public ToDoView() {

        Label title = new Label("todos");
        title.addStyleName("title");
        addComponent(title);

        TextField newTodo = new TextField();
        newTodo.addStyleName("new-todo");
        newTodo.setInputPrompt("What needs to be done?");
        addComponent(newTodo);

        addComponent(new CssLayout() {
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

    }

    @Override
    public void enter(ViewChangeEvent event) {
        // TODO Auto-generated method stub

    }

    CssLayout getNewTodoRow(String captionText, boolean done, boolean editing) {
        CssLayout row = new CssLayout();
        row.addStyleName("todo-row");
        if (done) {
            row.addStyleName("completed");
        }
        if (editing) {
            row.addStyleName("editing");
        }

        CheckBox toggle = new CheckBox(null, done);
        row.addComponent(toggle);

        Label caption = new Label(captionText);
        caption.setSizeUndefined();
        row.addComponent(caption);

        NativeButton destroy = new NativeButton();
        destroy.addStyleName("destroy");
        row.addComponent(destroy);

        TextField edit = new TextField();
        edit.setValue(captionText);
        row.addComponent(edit);

        return row;
    }

}