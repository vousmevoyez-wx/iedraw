//package com.shengyuanjun.iedraw.controller;
//
//import com.shengyuanjun.iedraw.AjaxResult;
//import com.shengyuanjun.iedraw.util.FastDfsApiOpr;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//
//@RestController
//public class FastDfsController {
//    //上传
//    @RequestMapping(value = "/fastdfs",method = RequestMethod.POST)
//    public AjaxResult upload(@RequestParam(value = "file",required = true) MultipartFile file){
//        try {
//            byte[] bytes = file.getBytes();//文件的内容
//            System.out.println(file.getName());
//            System.out.println(file.getOriginalFilename());
//            //获取文件扩展名
//            String fileName = file.getOriginalFilename();
//            String extName = fileName.substring(fileName.lastIndexOf(".")+1);
//            //直接调用工具类进行文件的上传
//            String upload = FastDfsApiOpr.upload(bytes, extName);
//            //成功
//            return AjaxResult.me().setRetObj(upload);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return AjaxResult.me().setSuccess(false).setMessage("上传失败!");
//        }
//    }
//
//    /**
//     *  删除
//     * "/group1/M00/00/01/wKh1gVxsEoGAVfdCAAppZEWKYY0457.bmp"
//     * group1
//     * M00/00/01/wKh1gVxsEoGAVfdCAAppZEWKYY0457.bmp
//     *
//     * @param filePath
//     * @return
//     */
//    @RequestMapping(value = "/fastdfs",method = RequestMethod.DELETE)
//    public AjaxResult delete(String filePath){
//        try {
//            //group1/M00/00/01/wKh1gVxsEoGAVfdCAAppZEWKYY0457.bmp
//            filePath = filePath.substring(1);
//            String groupName = filePath.substring(0,filePath.indexOf("/"));
//            System.out.println(groupName);
//            String fileName = filePath.substring(filePath.indexOf("/")+1);
//            System.out.println(fileName);
//
//            FastDfsApiOpr.delete(groupName,fileName);
//
//            return AjaxResult.me();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return AjaxResult.me().setSuccess(false).setMessage("删除失败!");
//        }
//    }
//
//}
