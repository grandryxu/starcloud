package com.xywg.admin.core.excel;

public class Excel2Module {

//    @Test
//    public void excel2Object() throws Exception {
//
//        String path = "F://aa.xls";
//
//        System.out.println("读取全部：");
//
//        List<String> data = new ArrayList<>();
//        List<List<String>> students = ExcelUtils.getInstance().readExcel2List(path, 6, 22, 0);
//        JsonArray parentJsonArray = new JsonArray();
//
//        for (List<String> stu : students) {
//            JsonArray jsonArray = new JsonArray();
//           for (String item : stu){
//
//               if(item.length()>1&&" ".equals(item.substring(0,1))){
//                   item = "　　"+item.trim();
//               }
//               jsonArray.add(item);
//           }
//           if(isBlank(jsonArray)) continue;
//           parentJsonArray.add(jsonArray);
//        }
//        String result = parentJsonArray.toString();
//        System.out.println(result);
//
//        int row = 1;
//        for (JsonElement item2: parentJsonArray.getAsJsonArray()){
//            int colmun = 65;
//            row++;
//             for(JsonElement item:item2.getAsJsonArray()){
//                 String result2 = row+"-"+(char)colmun + item;
//                 System.out.println(result2);
//                 colmun++;
//            }
//        }
//
//
//
//        //System.out.println("读取指定行数：");
//        //students = ExcelUtils.getInstance().readExcel2Objects(path, Student1.class, 0, 3, 0);
//        //for (Student1 stu : students) {
//        //    System.out.println(stu);
//        //}
//    }
//
//    private boolean isBlank(JsonArray jsonArray){
//        for (JsonElement item : jsonArray){
//             if(!"".equals(item.getAsString())){
//                 return false;
//             }
//        }
//        return true;
//    }


    /**
     *
     *
     */
    //@Test
    //public void readElevatorExcel() throws Exception{
    //    String path = "F://a.xls";
    //
    //    System.out.println("读取全部：");
    //    List<ElevatorEntity> students = ExcelUtils.getInstance().readExcel2Objects(path, ElevatorEntity.class);
    //    for (ElevatorEntity stu : students) {
    //        System.out.println(stu);
    //    }
    //
    //    System.out.println("读取指定行数：");
    //    students = ExcelUtils.getInstance().readExcel2Objects(path, ElevatorEntity.class, 0, 3, 0);
    //    for (ElevatorEntity stu : students) {
    //        System.out.println(stu);
    //    }
    //}

    //
    //
    //@Test
    //public void excel2Object2() throws Exception {
    //
    //    String path = "/Users/cloume/Excel4J/src/test/resource/students_02.xlsx";
    //
    //    // 不基于注解,将Excel内容读至List<List<String>>对象内
    //    List<List<String>> lists = ExcelUtils.getInstance().readExcel2List(path, 1, 3, 0);
    //    System.out.println("读取Excel至String数组：");
    //    for (List<String> list : lists) {
    //        System.out.println(list);
    //    }
    //    // 基于注解,将Excel内容读至List<Student2>对象内
    //    List<Student2> students = ExcelUtils.getInstance().readExcel2Objects(path, Student2.class, 0);
    //    System.out.println("读取Excel至对象数组(支持类型转换)：");
    //    for (Student2 st : students) {
    //        System.out.println(st);
    //    }
    //}
}
