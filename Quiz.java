package com.CS1102;
public class Quiz {
    public static void main(String[] args) {
        Question question = new TrueFalseQuestion("The Lion is the king of the jungle?", "TRUE");
        question.check();
        question = new TrueFalseQuestion("Is Maha Vajiralongkorn the current king of Thailand?", "TRUE");
                question.check();
        question = new TrueFalseQuestion("The first name of Kramer in Seinfeld is Cosmo?", "TRUE");
                question.check();
        question = new TrueFalseQuestion("The Slogan “Where's the Beef”, is for the famous fast-food joint Burger King?", "FALSE");
                question.check();
        question = new MultipleChoiceQuestion(
                "The french national football team are known as what?", "Francier", "Le francy", "Le Bleu", "Franball", "all of the above", "C");
                question.check();
        question = new MultipleChoiceQuestion
                ("How many blue stripes are there on the U.S. flag?", "7", "8",
                        "13", "0", "31", "D");
        question.check();
        question = new MultipleChoiceQuestion
                ("Which of the following was NOT a Canadian prime minister?", "Kim Campbell", "John N. Turner", "Robert Borden", "Roland Michener", "Wilfrid Laurier", "D");
                        question.check();
        question = new MultipleChoiceQuestion
                ("What is the colour of Donald Duck’s bowtie?", "Red", "Yellow",
                        "Blue", "White", "None of the above", "A");
        question.check();
        MultipleChoiceQuestion.showResults();
    }
}