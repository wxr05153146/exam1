package com.prj.server.menu;

import com.prj.entity.*;
import org.apache.ibatis.annotations.Param;

import java.io.File;
import java.util.List;


public interface MenuServer {

    //查询考试科目
    public List<Menu> queryMenu(@Param("title") String title, User user);

    //添加科目
    public int addMenu(ClassmenuVO classmenu, File file)throws Exception;

    //添加科目表与班级表中间表
    public int addMenuClasses(Classmenu classesmenu);

    //修改置顶
    public int updateIsTop(@Param("id") long id, @Param("istop") int istop);

    //批量删除
    public int delMenu(Long[] ids);

    //定时发布
    public int FaBu(long mid);

    //加载登录
    public List<Menu>JiaZai(int id);

    //交卷
    public int insertResult(ResultVo resultVo);


}
