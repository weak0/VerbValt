package com.example.verbvaultjava.service;

import com.example.verbvaultjava.model.course.Course;
import com.example.verbvaultjava.model.course.CourseSentence;
import com.example.verbvaultjava.model.course.CourseWord;
import com.example.verbvaultjava.repository.CourseRepository;
import com.example.verbvaultjava.repository.CourseSentenceRepository;
import com.example.verbvaultjava.repository.CourseWordRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DataLoadingService {
    private final CourseRepository courseRepository;
    private final CourseSentenceRepository courseSentenceRepository;
    private final CourseWordRepository courseWordRepository;

    @Transactional
    public void loadWords(String path, String courseLevel) {

            List<String[]> lines = loadFromCSV(path);
            Course courseFromDb = getCourse(courseLevel);

            for (String[] row : lines) {
                if (!courseWordRepository.existsByForeignWord(row[0])){
                    CourseWord courseWord = new CourseWord();
                    courseWord.setForeignWord(row[0]);
                    courseWord.setTranslation(row[1]);
                    courseWord.setCourse(courseFromDb);
                    CourseWord save = courseWordRepository.save(courseWord);
                    courseFromDb.getCourseWords().add(save);
                }


            }
            courseRepository.save(courseFromDb);


    }


    @Transactional
    public void loadSentences(String path, String courseLevel) {

            List<String[]> lines = loadFromCSV(path);
            Course courseFromDb = getCourse(courseLevel);

            for (String[] row : lines) {
               if (!courseSentenceRepository.existsByForeignSentence(row[0])){
                   CourseSentence courseSentence = new CourseSentence();
                   courseSentence.setForeignSentence(row[0]);
                   courseSentence.setTranslation(row[1]);
                   courseSentence.setCourse(courseFromDb);
                   CourseSentence save = courseSentenceRepository.save(courseSentence);
                   courseFromDb.getCourseSentences().add(save);
               }
            }
            courseRepository.save(courseFromDb);


    }

    private Course getCourse(String courseLevel) {
        if (courseRepository.findByCourseLevelIgnoreCase(courseLevel).isEmpty()) {
            Course course1 = new Course();
            course1.setCourseLevel(courseLevel);
            courseRepository.save(course1);
        }
        return courseRepository.findByCourseLevelIgnoreCase(courseLevel)
                .orElseThrow(() -> new RuntimeException("not found"));
    }

    private List<String[]> loadFromCSV(String path) {
        List<String[]> rows = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                rows.add(data);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return rows;
    }

}
