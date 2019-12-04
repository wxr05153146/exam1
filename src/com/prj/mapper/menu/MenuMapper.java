package com.prj.mapper.menu;

import com.prj.entity.Classmenu;
import com.prj.entity.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuMapper {

    //查询考试科目
    public List<Menu> queryMenu(@Param("title") String title);

    //添加科目
    public int addMenu(Menu menu);

    //添加科目表与班级表中间表
    public int addMenuClasses(Classmenu classesmenu);

    //修改置顶
    public int updateIsTop(@Param("id") long id, @Param("istop") int istop);

    //批量删除
    public int delMenu(@Param("ids") Long[] ids);
    //定时发布
    public int FaBu(@Param("mid")long mid);
}
