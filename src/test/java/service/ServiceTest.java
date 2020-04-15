package service;


import domain.Student;
import domain.Tema;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.ValidationException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


public class ServiceTest {

    private Service target;
    private Student student;
    private Tema invalidAssignment;
    private Tema validAssignment;
    private final static String FILENAME = "testAddStudent.xml";
    private final static String STUDENT_ID = "102";
    private final static String STUDENT_NAME = "Alex";
    private final static String STUDENT_EMAIL = "a@g.ro";
    private final static Integer STUDENT_GROUP = 936;
    private final static String TEMA_FILENAME = "testAddTema.xml";
    private final static String ASSIGNMENT_ID = "276534";

    @Before
    public void setUp() {
        student = new Student(STUDENT_ID, STUDENT_NAME, STUDENT_GROUP, STUDENT_EMAIL);
        invalidAssignment = new Tema("", "b", 1, 1);
        validAssignment = new Tema(ASSIGNMENT_ID, "b", 1, 1);

        StudentXMLRepo studentXMLRepository = new StudentXMLRepo(FILENAME);
        StudentValidator studentValidator = new StudentValidator();
        TemaXMLRepo temaXMLRepository = new TemaXMLRepo(TEMA_FILENAME);
        TemaValidator temaValidator = new TemaValidator();
        target = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, null, null);
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

    @Test(expected = ValidationException.class)
    public void testAddStudentNullName(){
        student.setNume(null);
        this.target.addStudent(student);
    }

    @Test(expected = ValidationException.class)
    public void testAddStudentNullId(){
        student.setID(null);
        this.target.addStudent(student);
    }

    @Test(expected = ValidationException.class)
    public void testAddStudentNullEmail(){
        student.setEmail(null);
        this.target.addStudent(student);
    }

    @Test(expected = ValidationException.class)
    public void testAddStudentEmptyEmail(){
        student.setEmail("");
        this.target.addStudent(student);
    }

    @Test(expected = ValidationException.class)
    public void testAddStudentEmptyName(){
        student.setNume("");
        this.target.addStudent(student);
    }

    @Test(expected = ValidationException.class)
    public void testAddStudentEmptyId(){
        student.setID("");
        this.target.addStudent(student);
    }

    @Test(expected = ValidationException.class)
    public void testAddStudentNonNaturalGroup(){
        student.setGrupa(-23);
        this.target.addStudent(student);
    }

    @Test(expected = ValidationException.class)
    public void testAddAssignmentInvalid() {
        this.target.addTema(invalidAssignment);
    }

    @Test
    public void testAddAssignmentValid() {
        // when
        Tema result = this.target.addTema(validAssignment);

        // then
        assertNull(result);
    }

    @Test(expected = ValidationException.class)
    public void testAddAssignmentNull() {
      validAssignment = null;
      this.target.addTema(validAssignment);
    }

    @Test(expected = ValidationException.class)
    public void testAddAssignmentIdNull() {
      validAssignment.setID(null);
      this.target.addTema(validAssignment);
    }

    @Test(expected = ValidationException.class)
    public void testAddAssignmentIdEmpty() {
      validAssignment.setID("");
      this.target.addTema(validAssignment);
    }

    @Test(expected = ValidationException.class)
    public void testAddAssignmentDescriptionNull() {
      validAssignment.setDescriere(null);
      this.target.addTema(validAssignment);
    }

    @Test(expected = ValidationException.class)
    public void testAddAssignmentDescriptionEmpty() {
      validAssignment.setDescriere("");
      this.target.addTema(validAssignment);
    }

    @Test(expected = ValidationException.class)
    public void testAddAssignmentDeadlineNegative() {
      validAssignment.setDeadline(-1);
      this.target.addTema(validAssignment);
    }

    @Test(expected = ValidationException.class)
    public void testAddAssignmentDeadlineTooBig() {
      validAssignment.setDeadline(99);
      this.target.addTema(validAssignment);
    }

    @Test(expected = ValidationException.class)
    public void testAddAssignmentPrimireTooBig() {
      validAssignment.setPrimire(99);
      this.target.addTema(validAssignment);
    }

    @Test(expected = ValidationException.class)
    public void testAddAssignmentPrimireNegative() {
      validAssignment.setPrimire(-1);
      this.target.addTema(validAssignment);
    }

    @After
    public void TearDown() {
        this.target.deleteStudent(STUDENT_ID);
        this.target.deleteTema(ASSIGNMENT_ID);
    }
}
