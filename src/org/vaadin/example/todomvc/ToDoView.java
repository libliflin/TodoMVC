package org.vaadin.example.todomvc;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class ToDoView extends VerticalLayout implements View {

    Link routingAll;

    Link routingActive;

    Link routingCompleted;

    TextField taskText;

    Label remainingTasksCount;

    Label remainingTasksLabel;

    CssLayout mainSection;

    CssLayout todoStatsContainer;

    Label clearTasksCount;

    Button clearCompleted;

    CheckBox toggleAll;

    public ToDoView() {

        Label title = new Label("todos");
        title.addStyleName("title");
        addComponent(title);

        TextField newTodo = new TextField();
        newTodo.addStyleName("new-todo");
        addComponent(newTodo);

    }

    public String getTaskText() {
        return taskText.getValue();
    }

    public void clearTaskText() {
        taskText.setValue("");
    }

    public void setTaskStatistics(int totalTasks, int completedTasks) {
        int remainingTasks = totalTasks - completedTasks;

        mainSection.setVisible(totalTasks == 0);
        todoStatsContainer.setVisible(totalTasks == 0);
        clearCompleted.setVisible(completedTasks == 0);

        remainingTasksCount.setValue(Integer.toString(remainingTasks));
        remainingTasksLabel
                .setValue(remainingTasks > 1 || remainingTasks == 0 ? "items"
                        : "item");
        clearTasksCount.setValue(Integer.toString(completedTasks));

        toggleAll.setValue(totalTasks == completedTasks);
    }

    @Override
    public void enter(ViewChangeEvent event) {
        // TODO Auto-generated method stub

    }

}