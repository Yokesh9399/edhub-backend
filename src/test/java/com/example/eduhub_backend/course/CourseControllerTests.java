package com.example.eduhub_backend.course;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class CourseControllerTests {
	private MockMvc mockMvc;

	@BeforeEach
	void resetCourses() {
		CourseController.resetToInitialStateForTests();
		this.mockMvc = MockMvcBuilders.standaloneSetup(new CourseController()).build();
	}

	@Test
	void getAllCourses_returns5Entries() throws Exception {
		mockMvc.perform(get("/api/courses"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(5)));
	}

	@Test
	void getCourseByPathVariable_returnsCourse() throws Exception {
		mockMvc.perform(get("/api/courses/CS101"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.courseCode").value("CS101"))
				.andExpect(jsonPath("$.subjectName").value("Programming Fundamentals"))
				.andExpect(jsonPath("$.credits").value(4));
	}

	@Test
	void getCourseByRequestParam_returnsCourse() throws Exception {
		mockMvc.perform(get("/api/courses").param("courseCode", "CS102"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.courseCode").value("CS102"))
				.andExpect(jsonPath("$.subjectName").value("Data Structures"))
				.andExpect(jsonPath("$.credits").value(4));
	}

	@Test
	void postNewCourse_makesListSize6() throws Exception {
		mockMvc.perform(post("/api/courses")
						.contentType(APPLICATION_JSON)
						.content("{" +
								"\"courseCode\":\"CS401\"," +
								"\"subjectName\":\"Cloud Computing\"," +
								"\"credits\":3" +
							"}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(6)));

		mockMvc.perform(get("/api/courses"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(6)));
	}

	@Test
	void putUpdatesCourse_updatesOneEntry() throws Exception {
		mockMvc.perform(put("/api/courses/CS201")
						.contentType(APPLICATION_JSON)
						.content("{" +
								"\"courseCode\":\"CS201\"," +
								"\"subjectName\":\"Database Management Systems\"," +
								"\"credits\":4" +
							"}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.courseCode").value("CS201"))
				.andExpect(jsonPath("$.subjectName").value("Database Management Systems"))
				.andExpect(jsonPath("$.credits").value(4));

		mockMvc.perform(get("/api/courses/CS201"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.subjectName").value("Database Management Systems"))
				.andExpect(jsonPath("$.credits").value(4));
	}

	@Test
	void deleteCourse_afterCreatingOne_bringsListBackTo5() throws Exception {
		mockMvc.perform(post("/api/courses")
						.contentType(APPLICATION_JSON)
						.content("{" +
								"\"courseCode\":\"CS402\"," +
								"\"subjectName\":\"Machine Learning\"," +
								"\"credits\":3" +
							"}"))
				.andExpect(status().isOk());

		mockMvc.perform(get("/api/courses"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(6)));

		mockMvc.perform(delete("/api/courses/CS402"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(5)));

		mockMvc.perform(get("/api/courses"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(5)));
	}
}
