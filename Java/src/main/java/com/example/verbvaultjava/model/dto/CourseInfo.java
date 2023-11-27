package com.example.verbvaultjava.model.dto;

import com.example.verbvaultjava.model.course.CourseSentence;
import com.example.verbvaultjava.model.course.CourseWord;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CourseInfo {
    private List<CourseWord> courseWords;
    private List<CourseSentence>courseSentences;
}
