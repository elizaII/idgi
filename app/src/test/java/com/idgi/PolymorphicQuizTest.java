package com.idgi;

import com.idgi.core.Answer;
import com.idgi.core.IQuiz;
import com.idgi.core.Question;
import com.idgi.core.Quiz;
import com.idgi.core.TimedQuiz;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

//import org.codehaus.jackson.JsonProcessingException;
//import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

public class PolymorphicQuizTest {

    Question question1;
    Question question2;

   /* public PolymorphicQuizTest() {
        question1 = new Question("What makes the world go round?");
        Answer q1a1 = new Answer("Monet");
        Answer q1a2 = new Answer("Money");
        q1a2.setCorrect(true);

        question1.addAnswers(q1a1, q1a2);

        question2 = new Question("How inapproriate are you?", "Be honest");
        Answer q2a1 = new Answer("1");
        Answer q2a2 = new Answer("100");
        q2a2.setSelected(true);
        Answer q2a3 = new Answer("69");
        q2a3.setCorrect(true);

        question2.addAnswers(q2a1, q2a2, q2a3);
    }

    @Test
    public void correctNormalQuizType()  throws JsonProcessingException,
            IOException {
        IQuiz normalQuiz = new Quiz();

        normalQuiz.addQuestions(Arrays.asList(question1,question2));

        String result = new ObjectMapper().writeValueAsString(normalQuiz);

        System.out.println(result);

        assertThat(result, containsString("type"));
        assertThat(result, containsString("quiz"));
    }

    @Test
    public void deserializeNormalQuizType() throws JsonProcessingException,
            IOException {
        IQuiz normalQuiz = new Quiz();

        normalQuiz.addQuestions(Arrays.asList(question1,question2));

        String json = "{\"type\":\"quiz\",\"questions\":[{\"text\":" +
                "\"What makes the world go round?\",\"hint\":\"There are no hints for this question.\"," +
                "\"answers\":[{\"text\":\"Monet\",\"correct\":false,\"selected\":false}," +
                "{\"text\":\"Money\",\"correct\":true,\"selected\":false}]," +
                "\"correctlyAnswered\":false,\"answerAmount\":2,\"initiallyExpanded\":false," +
                "\"correctAnswerAmount\":1}," +
                "{\"text\":\"How inapproriate are you?\",\"hint\":\"Be honest\",\"answers\":" +
                "[{\"text\":\"1\",\"correct\":false,\"selected\":false}," +
                "{\"text\":\"100\",\"correct\":false,\"selected\":true}," +
                "{\"text\":\"69\",\"correct\":true,\"selected\":false}]," +
                "\"correctlyAnswered\":false,\"answerAmount\":3,\"initiallyExpanded\"" +
                ":false,\"correctAnswerAmount\":1}],\"id\":\"1158a5a0-3d4c-4bbf-b904-3c372af2d40f\"," +
                "\"lastQuestion\":false,\"finished\":false,\"correctAnswerAmount\":0,\"pointsEarned\":0}";

        Quiz quiz = new ObjectMapper().reader()
                    .withType(Quiz.class)
                    .readValue(json);

        assertEquals("1158a5a0-3d4c-4bbf-b904-3c372af2d40f", quiz.getID());
    }

    @Test
    public void correctTimedQuizType() throws JsonProcessingException,
            IOException {
        IQuiz timedQuiz = new TimedQuiz(new Quiz());

        timedQuiz.addQuestions(Arrays.asList(question1, question2));

        String result = new ObjectMapper().writeValueAsString(timedQuiz);

        System.out.println(result);

        assertThat(result, containsString("type"));
        assertThat(result, containsString("timedQuiz"));
    }*/
}
