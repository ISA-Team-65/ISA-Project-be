package com.team65.isaproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.team65.isaproject.model.Course;

public interface CourseRepository extends JpaRepository<Course, Integer> {
	
	@Query("select c from Course c join fetch c.exams e where c.id =?1")
	public Course findOneWithExams(Integer courseId);

}
