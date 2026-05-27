package com.example.eduhub_backend.course;

public class Course {
	private String courseCode;
	private String subjectName;
	private int credits;

	public Course() {
	}

	public Course(String courseCode, String subjectName, int credits) {
		this.courseCode = courseCode;
		this.subjectName = subjectName;
		this.credits = credits;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public int getCredits() {
		return credits;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}
}
