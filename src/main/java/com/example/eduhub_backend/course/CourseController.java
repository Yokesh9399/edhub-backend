// package com.example.eduhub_backend.course;

// import java.util.ArrayList;
// import java.util.List;

// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;

// @RestController
// @RequestMapping("/api/courses")
// public class CourseController {
// 	private static final List<Course> courses = new ArrayList<>();

// 	static {
// 		courses.add(new Course("CS101", "Programming Fundamentals", 4));
// 		courses.add(new Course("CS102", "Data Structures", 4));
// 		courses.add(new Course("CS201", "Database Systems", 3));
// 		courses.add(new Course("CS202", "Operating Systems", 4));
// 		courses.add(new Course("CS301", "Software Engineering", 3));
// 	}

// 	@GetMapping
// 	public ResponseEntity<?> getCourses(@RequestParam(required = false) String courseCode) {
// 		if (courseCode == null || courseCode.trim().isEmpty()) {
// 			return ResponseEntity.ok(courses);
// 		}

// 		Course course = findCourseByCode(courseCode);
// 		if (course == null) {
// 			return ResponseEntity.notFound().build();
// 		}

// 		return ResponseEntity.ok(course);
// 	}

// 	@GetMapping("/{courseCode}")
// 	public ResponseEntity<Course> getCourseByPath(@PathVariable String courseCode) {
// 		Course course = findCourseByCode(courseCode);
// 		if (course == null) {
// 			return ResponseEntity.notFound().build();
// 		}
// 		return ResponseEntity.ok(course);
// 	}

// 	@PostMapping
// 	public ResponseEntity<?> createCourse(@RequestBody Course newCourse) {
// 		if (newCourse == null
// 				|| newCourse.getCourseCode() == null || newCourse.getCourseCode().trim().isEmpty()
// 				|| newCourse.getSubjectName() == null || newCourse.getSubjectName().trim().isEmpty()
// 				|| newCourse.getCredits() <= 0) {
// 			return ResponseEntity.badRequest().build();
// 		}

// 		Course existing = findCourseByCode(newCourse.getCourseCode());
// 		if (existing != null) {
// 			return ResponseEntity.badRequest().build();
// 		}

// 		courses.add(newCourse);
// 		return ResponseEntity.ok(courses); // now 6 entries after one successful POST
// 	}

// 	@PutMapping("/{courseCode}")
// 	public ResponseEntity<?> updateCourse(@PathVariable String courseCode, @RequestBody Course updatedCourse) {
// 		if (updatedCourse == null
// 				|| updatedCourse.getSubjectName() == null || updatedCourse.getSubjectName().trim().isEmpty()
// 				|| updatedCourse.getCredits() <= 0) {
// 			return ResponseEntity.badRequest().build();
// 		}

// 		Course course = findCourseByCode(courseCode);
// 		if (course == null) {
// 			return ResponseEntity.notFound().build();
// 		}

// 		course.setSubjectName(updatedCourse.getSubjectName());
// 		course.setCredits(updatedCourse.getCredits());
// 		return ResponseEntity.ok(course);
// 	}

// 	@DeleteMapping("/{courseCode}")
// 	public ResponseEntity<?> deleteCourse(@PathVariable String courseCode) {
// 		for (int i = 0; i < courses.size(); i++) {
// 			if (courses.get(i).getCourseCode().equals(courseCode)) {
// 				courses.remove(i);
// 				return ResponseEntity.ok(courses); // e.g., back to 5 after deleting 1 from 6
// 			}
// 		}
// 		return ResponseEntity.notFound().build();
// 	}

// 	static void resetToInitialStateForTests() {
// 		courses.clear();
// 		courses.add(new Course("CS101", "Programming Fundamentals", 4));
// 		courses.add(new Course("CS102", "Data Structures", 4));
// 		courses.add(new Course("CS201", "Database Systems", 3));
// 		courses.add(new Course("CS202", "Operating Systems", 4));
// 		courses.add(new Course("CS301", "Software Engineering", 3));
// 	}

// 	private static Course findCourseByCode(String courseCode) {
// 		if (courseCode == null) {
// 			return null;
// 		}
// 		for (Course course : courses) {
// 			if (courseCode.equals(course.getCourseCode())) {
// 				return course;
// 			}
// 		}
// 		return null;
// 	}
// }

package com.example.eduhub_backend.course;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/course")
public class CourseController {

    static List<Course> courseList = new ArrayList<>();

    static {
        courseList.add(new Course("CS101", "Programming Fundamentals", 4));
        courseList.add(new Course("CS102", "Data Structures", 4));
        courseList.add(new Course("CS201", "Database Systems", 3));
        courseList.add(new Course("CS202", "Operating Systems", 4));
        courseList.add(new Course("CS301", "Software Engineering", 3));
    }
     @GetMapping("/get-courses")
    public ResponseEntity<List<Course>> getCourses() {
        return ResponseEntity.ok(courseList);
}  
    @GetMapping("/get-course/{code}")
	public ResponseEntity<Course>getCourse(@PathVariable String code) {
          return courseList.stream()
				.filter(course -> course.getCourseCode().equals(code))
				.findFirst()
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
	@GetMapping("/search/get-course")
	public ResponseEntity<Course> searchCourse(@RequestParam String code) {
		return courseList.stream()
				.filter(course -> course.getCourseCode().equals(code))
				.findFirst()
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
	@PostMapping("/create")
	public ResponseEntity<Course> createCourse(@RequestBody Course newCourse) {
		courseList.add(newCourse);
		return ResponseEntity.ok(newCourse);
	}
	@PutMapping("/update/{code}")
	public ResponseEntity<Course> updateCourse(@PathVariable String code, @RequestBody Course updateCourse) {
      Course course = courseList.stream()
				.filter(c -> c.getCourseCode().equalsIgnoreCase(code))
				.findFirst()
				.orElse(null);
				
				course.setSubjectName(updateCourse.getSubjectName());
				course.setCredits(updateCourse.getCredits());
		return null;
	}
	
}