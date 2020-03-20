package service;


import domain.Student;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import repository.StudentFileRepository;
import repository.StudentXMLRepo;
import validation.StudentValidator;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import static org.junit.Assert.*;


public class ServiceTest {

    private Service target;
    private Student student;
    private final static String FILENAME = "testAddStudent.xml";
    private final static String STUDENT_ID = "102";
    private final static String STUDENT_NAME = "Alex";
    private final static String STUDENT_EMAIL = "a@g.ro";
    private final static Integer STUDENT_GROUP = 936;

    @Before
    public void setUp() throws FileNotFoundException {
        student = new Student(STUDENT_ID, STUDENT_NAME, STUDENT_GROUP, STUDENT_EMAIL);

        StudentXMLRepo studentXMLRepository = new StudentXMLRepo(FILENAME);
        StudentValidator studentValidator = new StudentValidator();
        target = new Service(studentXMLRepository, studentValidator, null, null, null, null);
    }

    @Test
    public void testAddStudent() {
        Student result = this.target.addStudent(student);
        assertNull(result);
    }

    @Test
    public void testAddSameStudentTwice() {
        Student result1 = this.target.addStudent(student);
        Student result2 = this.target.addStudent(student);
        assertNull(result1);
        assertNotNull(result2);
    }

    @After
    public void TearDown() {
        this.target.deleteStudent(STUDENT_ID);
    }
}
