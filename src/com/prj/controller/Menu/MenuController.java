package com.prj.controller.Menu;

import com.prj.entity.ClassmenuVO;
import com.prj.server.menu.MenuServer;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    @ResponseBody
    @RequestMapping("/addMenu")
    public String addMenu(@RequestBody ClassmenuVO classmenu)throws Exception{
       int i= menuServer.addMenu(classmenu,lastFile);
        //判断当前试题是否置顶

        if (classmenu.getMenu().getIstop()!=1){
            classmenu.getMenu().getIstop();

        }
        int i1=menuServer.addMenu(classmenu,lastFile);
        if(i>0){
            lastFile=null;
           return "ok";
       }

       return "error";
    }



    @ResponseBody
    @RequestMapping("/upload")
    public Map<String,Object> upload(@RequestParam("myfile") MultipartFile myfile, HttpServletRequest request) throws Exception {

        if(!myfile.isEmpty()){
            if(lastFile!=null){
                lastFile.delete();
                lastFile=null;
            }

            String url=request.getSession().getServletContext().getRealPath("/upload/");
            File file=new File(url+System.currentTimeMillis()+myfile.getOriginalFilename());
            FileUtils.copyInputStreamToFile(myfile.getInputStream(),file);

            lastFile=file;
        }
        Map<String,Object> map = new HashMap<String,Object>();
        //返回json
        map.put("msg","ok");
        map.put("code",200);
        return map;
    }
    //修改是否置顶
    @ResponseBody
    @RequestMapping("/updateIsTop/{id}/{istop}")
    public String updateIsTop(@PathVariable long id,@PathVariable int istop){

        //用户修改是否置顶
        if(istop==0){
            istop=1;
        }else {
            istop=0;
        }

        menuServer.updateIsTop(id,istop);

        return "ok";
    }

    //查询科目信息
    @ResponseBody
    @RequestMapping("/queryMenu")
    public Map<String,Object> queryMenu(String title){

        Map<String,Object> map=new HashMap<String,Object>();
        map.put("code","0");
        map.put("data",menuServer.queryMenu(title));

        return map;
    }
   // @RequestMapping("/delMenu")
    //public String delMenu(@RequestBody List ids){

    //}


}
