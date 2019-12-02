package com.prj.server.exam;

import com.prj.entity.Exam;
import com.prj.mapper.exam.ExamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("ExamServerImpl")
public class ExamServerImpl implements ExamServer {

    @Autowired
    private ExamMapper examMapper;
    public ExamMapper getExamMapper() {
        return examMapper;
    }
    public void setExamMapper(ExamMapper examMapper) {
        this.examMapper = examMapper;
    }


    @Override
    public List<Exam> query(long mid) {
        return examMapper.query(mid);
    }

    @Override
    public int add(Exam exam) {
        return examMapper.add(exam);
    }
}
