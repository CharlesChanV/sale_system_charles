//package com.dgut.utils;
//
//import lombok.extern.slf4j.Slf4j;
//
//import javax.imageio.ImageIO;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
///**
// * @author Charles
// * @version 1.0
// * @date 2020/6/16
// */
//@Slf4j
//public class ImageUtils {
//
//    public static List<String> createImage(String[] strArr, Font font,String imageFilePath,
//                                   int width, int image_height, int every_line, int line_height,Color fontColor) throws Exception {
//
//        FontMetrics fm = sun.font.FontDesignMetrics.getMetrics(font);
//        int stringWidth = fm.charWidth('字');// 标点符号也算一个字
//        int line_string_num = width % stringWidth == 0 ? (width / stringWidth) : (width / stringWidth) + 1;
//        log.debug("每行=" + line_string_num);
//
//        List<String> listStr = new ArrayList<String>();
//        List<String> newList = new ArrayList<String>();
//        for (int h = 0; h < strArr.length; h++) {
//            listStr.add(strArr[h]);
//        }
//        for (int j = 0; j < listStr.size(); j++) {
//            if( listStr.get(j).length() > line_string_num){
//                newList.add(listStr.get(j).substring(0,line_string_num));
//                listStr.add(j+1,listStr.get(j).substring(line_string_num));
//                listStr.set(j,listStr.get(j).substring(0,line_string_num));
//            }else{
//                newList.add(listStr.get(j));
//            }
//        }
//
//        int a = newList.size();
//        int b = every_line;
//        int imgNum = a % b == 0 ? (a / b) : (a / b) + 1;
//
//        List<String> filePaths = new ArrayList<>();
//        for (int m = 0; m < imgNum; m++) {
//            String filePath = imageFilePath + UUID.randomUUID().toString();
//
//            File outFile = new File(filePath);
//            // 创建图片
//            BufferedImage image = new BufferedImage(width, image_height,
//                    BufferedImage.TYPE_INT_BGR);
//            Graphics g = image.getGraphics();
//            g.setClip(0, 0, width, image_height);
//            g.setColor(Color.white); // 背景色白色
//            g.fillRect(0, 0, width, image_height);
//            g.setColor(fontColor);//  字体颜色黑色
//            g.setFont(font);// 设置画笔字体
//            // 每张多少行，当到最后一张时判断是否填充满
//            for (int i = 0; i < every_line; i++) {
//                int index = i + m * every_line;
//                if (newList.size() - 1 >= index) {
//                    log.debug("每行实际=" + newList.get(index).length());
//                    g.drawString(newList.get(index), 0, line_height * (i + 1));
//                }
//            }
//            g.dispose();
//            ImageIO.write(image, "jpg", outFile);// 输出png图片
//            filePaths.add(filePath);
//        }
//        return filePaths;
//    }
//}
