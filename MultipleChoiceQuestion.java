package com.CS1102;
import javax.swing.*;
import java.awt.*;
public class MultipleChoiceQuestion extends Question {
    MultipleChoiceQuestion(String query, String a, String b, String c, String d,
                           String e, String answer) {
        super(query);
        addChoice("A", a);
        addChoice("B", b);
        addChoice("C", c);
        addChoice("D", d);
        addChoice("E", e);
        initQuestionDialog();
        correctAnswer = answer.toUpperCase();
    }
    void addChoice(String name, String label){
        JPanel choice = new JPanel(new BorderLayout());
        JButton button = new JButton(name);
        button.addActionListener(question);
        choice.add(button, BorderLayout.WEST);
        choice.add(new Label(label+" ",JLabel.LEFT), BorderLayout.CENTER);
        question.add(choice);
    }
}