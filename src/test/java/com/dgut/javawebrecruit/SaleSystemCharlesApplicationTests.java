package com.dgut.javawebrecruit;

import com.dgut.entity.AdminEntity;
import com.dgut.entity.UserEntity;
import com.dgut.mapper.AdminMapper;
import com.dgut.mapper.UserMapper;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.util.List;

//@RunWith(SpringRunner.class)
//@SpringBootTest
class SaleSystemCharlesApplicationTests {

    @Resource
    private UserMapper userMapper;

    @Resource
    private AdminMapper adminMapper;
    @Test
    public void ttt() {
        int []a = {7,2,5,9,10,36,20,12,25,16};
        this.quickSort(a, 0, a.length-1);

        for(int i:a) {
            System.out.println(i);
        }
    }


    public void quickSort(int[] num, int left, int right) {
        if(right>left) {
            int temp = num[left];
            int i = left; //从左到右进行查找时的“指针”，指示当前左位置
            int j = right; //从右到左进行查找时的“指针”，指示当前右位置
            while(j>i) {
                while(i<j && temp < num[j]) j--;
                num[i] = num[j];
                while(i<j && num[i] <= temp) i++;
                num[j] = num[i];
            }
            //将基准元素填入相应位置
            num[i] = temp;
            //此时的i即为基准元素的位置
            //对基准元素的左边子区间进行相似的快速排序
            quickSort(num,left,i-1);
            //对基准元素的右边子区间进行相似的快速排序
            quickSort(num,i+1,right);
        }
    }

    @Test
    void contextLoads() {
        System.out.println(("----- selectAll method test ------"));
        List<UserEntity> userList = userMapper.selectList(null);
//        Assert.assertEquals(1, userList.size());
        userList.forEach(System.out::println);
    }
    @Test
    public void testInsert() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("eqweqweqw");
        userEntity.setPassword("123456");
//        userEntity.setRoles("ROLE_admin123");
        int res = userMapper.insert(userEntity);
        System.out.println(res);
        System.out.println(userEntity);
    }
    @Test
    public void testInsertAdmin() {
        AdminEntity adminEntity = new AdminEntity();
        adminEntity.setName("1321312414");
        int res = adminMapper.insert(adminEntity);
        System.out.println(res);
    }
    @Test
    public void testDeleteAdmin() {
        AdminEntity adminEntity = new AdminEntity();
        int res = adminMapper.deleteById(1);
        System.out.println(res);
    }
    @Test
    public void testUpdate() {
        // mp先查询后更新
        UserEntity userEntity = userMapper.selectById("e5c960f73f76f3e645af1c07aa1c521f");
        System.out.println(userEntity);
//        userEntity.setRoles("roles");
        // 动态更新SQL
        int i = userMapper.updateById(userEntity);
        System.out.println(i);
    }

//    public String readJsonFile(String fileName) {
//        String jsonStr = "";
//        try {
//            File jsonFile = new File(fileName);
//            FileReader fileReader = new FileReader(jsonFile);
//            Reader reader = new InputStreamReader(new FileInputStream(jsonFile),"utf-8");
//            int ch = 0;
//            StringBuffer sb = new StringBuffer();
//            while ((ch = reader.read()) != -1) {
//                sb.append((char) ch);
//            }
//            fileReader.close();
//            reader.close();
//            jsonStr = sb.toString();
//            return jsonStr;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
    @Test
    public void test() {
//        String path = Objects.requireNonNull(JsonTest.class.getClassLoader().getResource("cer.json")).getPath();
//        String s = readJsonFile(path);
//        JSONObject jobj = JSON.parseObject(s);
//        System.out.println(jobj);

//        JSONArray movies = jobj.getJSONArray("RECORDS");//构建JSONArray数组
//        for (int i = 0 ; i < movies.size();i++){
//            JSONObject key = (JSONObject)movies.get(i);
//            String name = (String)key.get("name");
//            String director = (String)key.get("director");
//            String scenarist=((String)key.get("scenarist"));
//            String actors=((String)key.get("actors"));
//            String type=((String)key.get("type"));
//            String ratingNum=((String)key.get("ratingNum"));
//            String tags=((String)key.get("tags"));
//            System.out.println(name);
//            System.out.println(director);
//            System.out.println(scenarist);
//            System.out.println(actors);
//            System.out.println(type);
//            System.out.println(director);
//            System.out.println(ratingNum);
//            System.out.println(tags);
//        }
    }

}
