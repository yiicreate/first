package com.test.first.controller;

import com.test.first.entity.User;
import com.test.first.service.imp.UserServiceImp;
import com.test.first.util.ExcelUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author: lh
 * @version: 2020/12/17
 * @description:
 */

@RestController
@RequestMapping("/guest/")
public class ExeclController  {

    @Autowired
    private UserServiceImp userServiceImp;

    @PostMapping("import")
    public List<List<Object>> importFile(@RequestParam("file") MultipartFile file) throws IOException {
        if(file.isEmpty()){
            return null;
        }
        InputStream in = file.getInputStream();
        return  ExcelUtil.importExecl(in,file.getOriginalFilename());
    }


    @GetMapping("export")
    public void ecportFile(HttpServletResponse response, String ids) throws IOException {
        List<User> users = userServiceImp.findAllLists();

        Integer a[] = {14,15,20,20,15};
        String n[] = {"序号","ID","姓名","账户","时间"};

        List<Map<String,Object>> header = new ArrayList<>();
        for (int i = 0; i<n.length; i++){
            Map<String,Object> map = new HashMap<>();
            map.put("val",n[i]);
            map.put("len",a[i]);
            header.add(map);
        }

        List<String[]> list = new ArrayList<>();
        Integer j = 1;
        for (User val:users) {
            String[] str = {
                    String.valueOf(j),
                    String.valueOf(val.getId()),
                    val.getName(),
                    val.getUserName(),
                    val.getCreateTime()
            };
            j++;
            list.add(str);
        }
        Workbook wb = ExcelUtil.createWorkBook(header,list,null,null);
        FileOutputStream fos = new FileOutputStream("E:/java_example/first/src/main/resources/static/测试.xls");
        wb.write(fos);
        fos.close();
    }

    @GetMapping("user")
    public List<User> userList(){
        return userServiceImp.findAllLists();
    }
}
