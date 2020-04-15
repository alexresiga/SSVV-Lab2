package service;

import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


public class BigBangTest {

    private Service target;
    private Student student;
    private Tema validAssignment;
    private Nota nota;
    private final static String FILENAME = "testAddStudent.xml";
    private final static String STUDENT_ID = "102";
    private final static String STUDENT_NAME = "Alex";
    private final static String STUDENT_EMAIL = "a@g.ro";
    private final static Integer STUDENT_GROUP = 936;
    private final static String TEMA_FILENAME = "testAddTema.xml";
    private final static String NOTA_FILENAME = "testAddNota.xml";
    private final static String ASSIGNMENT_ID = "276534";
    private final static String NOTA_ID = "276534";
    private final static String FEEDBACK = "se putea mai bine";
    private final static double NOTA_NOTA = 7.9876798678987;


    @Before
    public void setUp() {
        student = new Student(STUDENT_ID, STUDENT_NAME, STUDENT_GROUP, STUDENT_EMAIL);
        validAssignment = new Tema(ASSIGNMENT_ID, "b", 1, 5);
        nota = new Nota(NOTA_ID, "1", "1", NOTA_NOTA, LocalDate.now());

        StudentXMLRepo studentXMLRepository = new StudentXMLRepo(FILENAME);
        StudentValidator studentValidator = new StudentValidator();
        TemaXMLRepo temaXMLRepository = new TemaXMLRepo(TEMA_FILENAME);
        TemaValidator temaValidator = new TemaValidator();
        NotaXMLRepo notaXMLRepo = new NotaXMLRepo(NOTA_FILENAME);
        NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
        target = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepo, notaValidator);
    }

    @Test
    public void testAddStudent() {
        Student result = this.target.addStudent(student);
        assertNull(result);
    }

    @Test
    public void testAddAssignmentValid() {
        // when
        Tema result = this.target.addTema(validAssignment);

        // then
        assertNull(result);
    }

    @Test
    public void testAddGrade() {
        // when
        double result = this.target.addNota(nota, FEEDBACK);

        // then
        assertEquals(result, NOTA_NOTA, 1e-20);
    }

    @Test
    public void testBigBang() {
        // when
        Student resultStudent = this.target.addStudent(student);
        Tema resultTema = this.target.addTema(validAssignment);
        double resultNota = this.target.addNota(nota, FEEDBACK);

        // then
        assertNull(resultStudent);
        assertNull(resultTema);
        assertEquals(resultNota, NOTA_NOTA, 1e-20);
    }


    @After
    public void TearDown() {
        this.target.deleteStudent(STUDENT_ID);
        this.target.deleteTema(ASSIGNMENT_ID);
        this.target.deleteNota(NOTA_ID);
    }
}
