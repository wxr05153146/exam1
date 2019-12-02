package com.prj.server.exam;

import com.prj.entity.Exam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExamServer {

    public List<Exam> query(@Param("mid") long mid);

    //添加试题信息
    public int add(Exam exam);

}
