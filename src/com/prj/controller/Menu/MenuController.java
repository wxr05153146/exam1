package com.prj.controller.Menu;

import com.prj.entity.ClassmenuVO;
import com.prj.entity.Menu;
import com.prj.server.menu.MenuServer;

import org.apache.commons.io.FileUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class MenuController {


    @Autowired
    @Qualifier("MenuServerImpl")
    private MenuServer menuServer;
    private File lastFile;

    public MenuServer getMenuServer() {
        return menuServer;
    }

    public void setMenuServer(MenuServer menuServer) {
        this.menuServer = menuServer;
    }


    //添加
    @ResponseBody
    @RequestMapping("/addMenu")
    public String addMenu(@RequestBody ClassmenuVO classmenu) throws Exception {
        //考试总分钟
        int sum = 0;
        //考试时间转换成分钟
        if (classmenu.getScoreTIme() != null) {
            String scoreTime = classmenu.getScoreTIme();

            //小时
            int xiaoshi = Integer.parseInt(scoreTime.substring(0, scoreTime.indexOf(":")));
            int fenzhong = Integer.parseInt(scoreTime.substring(scoreTime.indexOf(":") + 1, scoreTime.length()));

            sum = fenzhong + xiaoshi * 60;
        }

        classmenu.getMenu().setScoreTime(sum);
        //判断试题是否定时发布
        if (classmenu.getMenu().getIspublic() == 0) {
            //获取用户的时间
            String mytime = classmenu.getMytime();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Date date = dateFormat.parse(mytime);

            //启动定时任务
            //不理解，问老师
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    long mid = classmenu.getMenu().getId();
                    menuServer.FaBu(mid);

                }
            }, date);
        }

        //判断当前试题是否置顶
        if (classmenu.getMenu().getIstop() != 1) {
            classmenu.getMenu().getIstop();

        }
        int i = menuServer.addMenu(classmenu, lastFile);
        if (i > 0) {
            lastFile = null;
            return "ok";
        }

        return "error";
    }


    @ResponseBody
    @RequestMapping("/upload")
    public Map<String, Object> upload(@RequestParam("myfile") MultipartFile myfile, HttpServletRequest request) throws Exception {
        //判断用户是否选择文件
        if (!myfile.isEmpty()) {
            //删除上一次文件
            if (lastFile != null) {
                lastFile.delete();
                lastFile = null;
            }

            //获取上传的地址
            String url = request.getSession().getServletContext().getRealPath("/upload/");
            //创建文件
            File file = new File(url + System.currentTimeMillis() + myfile.getOriginalFilename());
            //复制文件
            FileUtils.copyInputStreamToFile(myfile.getInputStream(), file);

            //保留上一次文件
            lastFile = file;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        //返回json
        map.put("msg", "ok");
        map.put("code", 200);
        return map;
    }

    //修改是否置顶
    @ResponseBody
    @RequestMapping("/updateIsTop/{id}/{istop}")
    public String updateIsTop(@PathVariable long id, @PathVariable int istop) {

        //用户修改是否置顶
        if (istop == 0) {
            istop = 1;
        } else {
            istop = 0;
        }

        menuServer.updateIsTop(id, istop);

        return "ok";
    }

    //查询科目信息
    @ResponseBody
    @RequestMapping("/queryMenu")
    public Map<String, Object> queryMenu(String title) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", "0");
        map.put("data", menuServer.queryMenu(title));

        return map;
    }

    //批量删除
    @ResponseBody
    @RequestMapping("/delMenu")
    public String delMenu(Long[] ids) {
        menuServer.delMenu(ids);

        return "ok";
    }

    //加载登录
    @ResponseBody
    @RequestMapping("/JiaZai/{id}")
    public List<Menu> JiaZai(@PathVariable int id) {


        List<Menu> menuList = menuServer.JiaZai(id);


        return menuList;
    }
}
