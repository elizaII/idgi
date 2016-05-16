package com.idgi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idgi.core.StudentUser;
import com.idgi.core.TeacherUser;
import com.idgi.core.User;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.containsString;

import org.junit.Test;

import java.io.IOException;

public class StudentUserTest {

    @Test
    public void correctStudentUserType() throws JsonProcessingException,
            IOException{
        User student = new StudentUser("Tuyen");
        student.setAge(19);
        student.setEmail("kim_tuyen_ngo@hotmail.com");
        student.setPhoneNumber("0000007070");

        String result = new ObjectMapper().writeValueAsString(student);

        System.out.println(result);

        assertThat(result, containsString("type"));
        assertThat(result, containsString("student"));
    }

    @Test
    public void deserializeStudentUser() throws JsonProcessingException,
            IOException {
        User student = new StudentUser("Tuyen");
        student.setAge(19);

        String json = "{\"type\":\"student\",\"name\":\"Tuyen\"," +
                "\"email\":\"kim_tuyen_ngo@hotmail.com\"," +
                "\"phoneNumber\":\"0000007070\",\"age\":19,\"myCourses\":[]}";

        StudentUser studentUser = new ObjectMapper().reader()
                .withType(StudentUser.class)
                .readValue(json);

        assertEquals(19, studentUser.getAge());
    }

    @Test
    public void correctTeacherUserType() throws JsonProcessingException,
            IOException {
        User teacher = new TeacherUser("Niklas Grip");

        teacher.setEmail("nigr@mikaelelias.se");

        String result = new ObjectMapper().writeValueAsString(teacher);

        System.out.println(result);

        assertThat(result, containsString("type"));
        assertThat(result, containsString("teacher"));
    }

    @Test
    public void deserializeTeacherUser() throws JsonProcessingException,
            IOException {
        User teacher = new TeacherUser("Niklas Grip");

        teacher.setEmail("nigr@mikaelelias.se");

        String json = "{\"type\":\"teacher\",\"name\":\"Niklas Grip\"," +
                "\"email\":\"nigr@mikaelelias.se\",\"phoneNumber\":null," +
                "\"age\":0,\"myCourses\":[]}";

        TeacherUser teacherUser = new ObjectMapper().reader()
                                                    .withType(TeacherUser.class)
                                                    .readValue(json);

        assertEquals("Niklas Grip", teacherUser.getName());
    }
}
