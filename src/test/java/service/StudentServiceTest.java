package service;

import com.example.school.enums.Gender;
import com.example.school.model.Student.Student;
import com.example.school.model.Student.StudentContactDetails;
import com.example.school.repo.StudentRepository;
import com.example.school.service.Student.StudentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StudentServiceTest {

    StudentRepository studentRepository= Mockito.mock(StudentRepository.class);

    @Test
    public void updateStudentInfo_ShouldSetNewValues(){
        StudentService studentService =new StudentService();
        StudentContactDetails studentContactDetails = new StudentContactDetails();
        StudentContactDetails studentContactDetailsUpdated = new StudentContactDetails();
        studentContactDetails.setEmail("brehuescusergiu@gmail");
        studentContactDetails.setFirstName("Brehuscu");
        studentContactDetails.setLastName("Sergiu");
        Student student = new Student(666, 26, LocalDate.of(1994, Month.AUGUST, 20), Gender.MALE, null, studentContactDetails, null, null);
        studentContactDetailsUpdated.setEmail("gmail");
        studentContactDetailsUpdated.setFirstName("S");
        studentContactDetailsUpdated.setLastName("B");
        Student updated = new Student(888, 27, LocalDate.of(1994, Month.AUGUST, 25), Gender.MALE, null, studentContactDetailsUpdated, null, null);

        studentService.updateStudentInfo(updated,student);

        assertTrue(student.getDateOfBirth().equals(updated.getDateOfBirth()));
        assertEquals(27,student.getAge());
        assertEquals("B",studentContactDetails.getLastName());
        assertEquals("gmail", studentContactDetails.getEmail());
        assertEquals("S",studentContactDetails.getFirstName());
        assertThat(student.getGender()).isEqualTo(Gender.MALE);


    }
//
//    @Test
//    public void findStudentById_should_return_student(){
//        StudentService studentService = new StudentService();
//        Student student =new Student();
//        StudentContactDetails studentContactDetails = new StudentContactDetails();
//        studentContactDetails.setLastName("Brehuescu");
//        studentContactDetails.setFirstName("Sergiu");
//        studentContactDetails.setEmail("brehuescusergiu@gmail.com");
//        studentContactDetails.setPhoneNumber("0755458656");
//        studentService.setStudentRepository(studentRepository);
//
//        student.setId(6);
//        student.setStudentContactDetails(studentContactDetails);
//        student.setAge(27);
//
//        when(studentRepository.findById(6)).thenReturn(Optional.of(student));
//
//
//        Student studentC = studentService.findStudentById(7);
//
//
//        assertThat(studentC.getAge().equals(27));
//        assertThat(studentC.getStudentContactDetails()).isEqualTo(studentContactDetails);
//
//
//
//    }


}
