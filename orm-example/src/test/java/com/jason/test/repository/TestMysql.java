package com.jason.test.repository;

import com.jason.test.TestConfiguration;
import com.jason.test.constant.Sex;
import com.jason.test.pojo.mysql.Office;
import com.jason.test.pojo.mysql.User;
import com.jason.test.pojo.mysql.resource.Resource;
import com.jason.test.pojo.mysql.resource.ResourceType;
import com.jason.test.pojo.mysql.resource.UserResource;
import cool.lazy.cat.orm.base.util.CollectionUtil;
import cool.lazy.cat.orm.core.base.bo.PageResult;
import cool.lazy.cat.orm.core.base.repository.BaseRepository;
import cool.lazy.cat.orm.core.jdbc.IgnoreModel;
import cool.lazy.cat.orm.core.jdbc.Ignorer;
import cool.lazy.cat.orm.core.jdbc.OrderBy;
import cool.lazy.cat.orm.core.jdbc.param.DeleteParamImpl;
import cool.lazy.cat.orm.core.jdbc.param.SearchParam;
import cool.lazy.cat.orm.core.jdbc.param.SearchParamImpl;
import cool.lazy.cat.orm.core.jdbc.sql.condition.Condition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author: mahao
 * @date: 2021/3/7 17:25
 */
@SpringBootTest(classes = TestConfiguration.class)
public class TestMysql {

    @Test
    public void testEnvironment() {
    }

    @SpringBootTest(classes = TestConfiguration.class)
    static final class QueryApiTester {

        @Autowired
        private BaseRepository baseRepository;

        @Test
        public void testQuery() {
            Condition c1 = Condition.eq("isDeleted", 0);
            List<String> searchScope = Arrays.asList("office.parent.parent.childrenList",
                    "userDirList.userFileList.fileContentList.dir", "ftpDirList.ftpFileList.dir");
            Ignorer ig1 = Ignorer.build("office.level", "sex", "userDirList.userFileList.fileContentList.suffix");
            Ignorer ig2 = Ignorer.build(IgnoreModel.INCLUDE, Stream.concat(ig1.getFields().stream(), Stream.of("office.parent", "ftpDirList")).collect(Collectors.toSet()));
            List<User> ul1 = baseRepository.query(this.buildParam(User.class).setIgnorer(ig1).setCondition(c1));
            PageResult<User> up1 = baseRepository.queryPage(this.buildParam(User.class).setPageSize(2000).setIndex(4000)
                    .setIgnorer(ig1).setCondition(c1));
            List<User> ul3 = baseRepository.query(this.buildParam(User.class).setSearchScope(searchScope).setIgnorer(ig1)
                    .setCondition(Condition.eq("office.parent.name", "A公司")));
            List<User> ul4 = baseRepository.query(this.buildParam(User.class).setSearchScope(searchScope).setIgnorer(ig2).setCondition(c1));
            User u1 = baseRepository.querySingle(this.buildParam(User.class).setCondition(Condition.eq("id", 1).and(c1)));
            User u2 = baseRepository.queryById(User.class, 20000);
            List<User> uIds1 = baseRepository.queryByIds(User.class, Arrays.asList(1, 2, 3));
            List<User> uc1 = baseRepository.query(this.buildParam(User.class).setCondition(c1.and(Condition.neq("sex", Sex.MAN.name()))
                    .or(Condition.like("name", "王"))));
            System.out.println();
        }

        private <T> SearchParam<T> buildParam(Class<T> type) {
            return new SearchParamImpl<>(type);
        }
    }

    @SpringBootTest(classes = TestConfiguration.class)
    static final class InsertApiTester {

        @Autowired
        BaseRepository baseRepository;

        public List<Resource> buildResources() {
            return Arrays.asList(new Resource().setResourceName("RTX 3090").setTitle("地表最强显卡").setDesc("挖矿好手").setType(ResourceType.COLLECTION),
                                 new Resource().setResourceName("iphone13").setTitle("也就那样吧").setDesc("还是13香").setType(ResourceType.PUBLIC),
                                 new Resource().setResourceName("mac ruby whooo").setTitle("小辣椒").setDesc("巧克力味").setType(ResourceType.COLLECTION),
                                 new Resource().setResourceName("一条裤衩").setTitle("又脏又旧").setDesc("赶紧扔了吧").setType(ResourceType.PRIVATE),
                                 new Resource().setResourceName("BOM管理").setTitle("吐了吐了").setDesc("基本告别自行车了").setType(ResourceType.PROTECTED),
                                 new Resource().setResourceName("罗永浩").setTitle("天天接广告").setDesc("好歹在还钱").setType(ResourceType.PUBLIC),
                                 new Resource().setResourceName("a接w接外圈刮无情舔嫂之一刀斩").setTitle("捞的嘛").setDesc("不谈").setType(ResourceType.PUBLIC),
                                 new Resource().setResourceName("一个神秘的小盒子").setTitle("看你能不能活到100岁").setDesc("没啥意思").setType(ResourceType.COLLECTION),
                                 new Resource().setResourceName("JAVA").setTitle("yyds").setDesc("老JAVA了").setType(ResourceType.PUBLIC),
                                 new Resource().setResourceName("小红书").setTitle("总有虚假宣传").setDesc("天下乌鸦一般黑").setType(ResourceType.PUBLIC),
                                 new Resource().setResourceName("LOL").setTitle("两级反转").setDesc("这么做是对的").setType(ResourceType.PUBLIC),
                                 new Resource().setResourceName("to infinity").setTitle("如果可以").setDesc("我也想做个游戏").setType(ResourceType.SHARE),
                                 new Resource().setResourceName("前端的自我修养.txt").setTitle("不吹不黑").setDesc("废纸一堆").setType(ResourceType.PUBLIC),
                                 new Resource().setResourceName("我有一支笔").setTitle("你有一支笔").setDesc("我们大家都有一支笔").setType(ResourceType.SHARE),
                                 new Resource().setResourceName("匡威").setTitle("1970经典黑白").setDesc("质量嘎嘎地").setType(ResourceType.PUBLIC));
        }

        public List<User> buildUsers() {
            Office rootOffice = new Office().setName("root").setCode("root").setId("-1");
            /**
             * 使用自定义id
             * @see com.jason.test.component.CustomBaseRepository#isNewRecord(Object)
             */
            rootOffice.setIsNewRecord(true);
            rootOffice.setLevel(1);
            rootOffice.setPath(rootOffice.getId() + "/");
            Office mi = new Office().setCode("MI").setName("小米之家").setId("MI").setParent(rootOffice);
            // 使用自定义id
            mi.setIsNewRecord(true);
            Office lolAnchor = new Office().setCode("LOL_ANCHOR").setName("lol主播").setId("LOL_ANCHOR").setParent(rootOffice);
            lolAnchor.setIsNewRecord(true);
            rootOffice.setChildrenList(Arrays.asList(mi, lolAnchor));
            return Arrays.asList(new User().setName("小爱").setSex(Sex.WOMAN).setAge(20).setEducation("硕士").setOffice(mi),
                                 new User().setName("Lee").setSex(Sex.MAN).setAge(29).setEducation("大学本科"),
                                 new User().setName("me").setSex(Sex.MAN).setAge(23),
                                 new User().setName("jason").setSex(Sex.MAN).setAge(23),
                                 new User().setName("韩金龙").setSex(Sex.MAN).setAge(33).setEducation("未知").setOffice(lolAnchor));
        }

        @Test
        public void testInsert() {
            baseRepository.save(this.buildResources());
            List<Resource> resources = baseRepository.query(new SearchParamImpl<>(Resource.class));
            List<User> userList = this.buildUsers();
            Random random = new Random();
            for (User user : userList) {
                Set<Resource> record = new HashSet<>();
                int size = random.nextInt(resources.size() - 5);
                user.setResourceList(new ArrayList<>(size));
                for (int i = 0; i < size; i++) {
                    Resource resource = resources.get(random.nextInt(resources.size()));
                    if (record.contains(resource)) {
                        continue;
                    }
                    record.add(resource);
                    user.getResourceList().add(new UserResource().setResource(resource));
                }
            }
            List<Office> officeList = userList.stream().map(User::getOffice).filter(Objects::nonNull).collect(Collectors.toList());
            baseRepository.save(officeList.get(0).getParent(), true);
            baseRepository.save(userList);
            System.out.println();
        }
    }

    @SpringBootTest(classes = TestConfiguration.class)
    static final class UpdateApiTester {

        @Autowired
        BaseRepository baseRepository;

        @Test
        public void testUpdate1() {
            SearchParam<User> param = new SearchParamImpl<>(User.class).setCondition(Condition.notNull("resourceList.id"));
            List<User> users = baseRepository.query(param);
            for (User user : users) {
                if (CollectionUtil.isNotEmpty(user.getResourceList())) {
                    for (UserResource userResource : user.getResourceList()) {
                        userResource.getResource().setDesc(userResource.getResource().getDesc() + ".");
//                        userResource.getResource().setDesc(userResource.getResource().getDesc().replaceAll("\\.", ""));
                    }
                }
            }
            baseRepository.update(users);
            users = baseRepository.query(param);
            System.out.println();
        }

        @Test
        public void testUpdate() {
            List<User> userList = baseRepository.query(User.class);
            List<Office> officeList = baseRepository.query(new SearchParamImpl<>(Office.class).setCondition(Condition.gt("level", 2)));
            Random random = new Random();
            for (User user : userList) {
                int indexOf = random.nextInt(officeList.size());
                Office office = officeList.get(indexOf);
                user.setOffice(office);
            }
            baseRepository.save(userList, true);
        }
    }

    @SpringBootTest(classes = TestConfiguration.class)
    static final class DeleteApiTester {

        @Autowired
        BaseRepository baseRepository;

        @Test
        public void testDelete() {
            baseRepository.delete(baseRepository.queryById(User.class, 20002));
            baseRepository.delete(new DeleteParamImpl(User.class, Condition.eq("age", 57)));
            baseRepository.deleteById(User.class, 5000);
            baseRepository.deleteByIds(User.class, Arrays.asList(3000, 7500));
            baseRepository.deleteByIds(User.class, Collections.singletonList(8972));
            baseRepository.deleteByCondition(User.class, Condition.like("name", "孙"));
            baseRepository.deleteByCondition(Resource.class, null);
            System.out.println();
        }

        @Test
        public void testDelete2() {
            baseRepository.delete(baseRepository.queryByIds(User.class, Arrays.asList(1, 2, 3)));
            System.out.println();
        }

        @Test
        public void testLogicDelete() {
            baseRepository.logicDelete(baseRepository.query(new SearchParamImpl<>(User.class).setCondition(Condition.in("id", Arrays.asList(1, 2)))));
            baseRepository.logicDeleteByIds(User.class, Arrays.asList(7777, 8888, 9999));
            baseRepository.logicDeleteByIds(User.class, Collections.singletonList(6666));
            baseRepository.logicDeleteById(User.class, "4567");
            baseRepository.logicDeleteByCondition(User.class, Condition.isNull("phoneNum"));
            System.out.println();
        }

        @Test
        public void testLogicDelete2() {
            baseRepository.logicDelete(baseRepository.query(User.class));
            System.out.println();
        }
    }

    @Autowired
    BaseRepository baseRepository;

    @Test
    public void testCondition() {
        SearchParam<User> param = new SearchParamImpl<>(User.class).setPageSize(2000).setOrderBy(OrderBy.buildOrderBy(true, "age"));
        List<User> u1 = baseRepository.query(param.setCondition(Condition.eq("age", 18).and(Condition.riLike("name", "张"))));
        List<User> u2 = baseRepository.query(param.setCondition(Condition.neq("office.parent.name", "B公司")
                .and(Condition.in("education", Arrays.asList("大学本科", "硕士研究生", "博士研究生"))).and(Condition.lte("age", 35))));
        List<User> u3 = baseRepository.query(param.setCondition(Condition.like("office.parent.name", "A").and(Condition.leLike("name", "林"))));
        List<User> u4 = baseRepository.query(param.setCondition(Condition.leLike("phoneNum", "0378")));
        List<User> u5 = baseRepository.query(param.setCondition(Condition.riLike("phoneNum", "139").and(Condition.eq("sex", Sex.WOMAN.name()))));
        List<User> u6 = baseRepository.query(param.setCondition(Condition.notLike("office.name", "B").and(Condition.notLeLike("name", "昊"))));
        List<User> u7 = baseRepository.query(param.setCondition(Condition.notLeLike("phoneNum", "8250").and(Condition.notLike("name", "张"))));
        List<User> u8 = baseRepository.query(param.setCondition(Condition.notRiLike("phoneNum", "139").and(Condition.notRiLike("name", "吴"))));
        List<User> u9 = baseRepository.query(param.setCondition(Condition.isNull("phoneNum").and(Condition.notNull("age"))));
        List<User> u10 = baseRepository.query(param.setCondition(Condition.notNull("phoneNum").and(Condition.in("office.name", Arrays.asList("A_开发部门", "B_开发部门")))));
        List<User> u11 = baseRepository.query(param.setCondition(Condition.in("age", Arrays.asList(18, 26, 33)).and(Condition.eq("sex", Sex.WOMAN.name()))));
        List<User> u12 = baseRepository.query(param.setCondition(Condition.notIn("education", Arrays.asList("中专", "高中以下")).and(Condition.lte("age", 30))));
        List<User> u13 = baseRepository.query(param.setCondition(Condition.gt("age", 10).and(Condition.eq("sex", Sex.MAN.name()))));
        List<User> u14 = baseRepository.query(param.setCondition(Condition.lt("age", 20).and(Condition.eq("office.name", "B_开发部门"))));
        List<User> u15 = baseRepository.query(param.setCondition(Condition.gte("createDate", LocalDate.now().minusDays(1)).and(Condition.notNull("sex"))));
        List<User> u16 = baseRepository.query(param.setCondition(Condition.lte("createDate", LocalDate.now())
                .or(Condition.isNull("createDate").and(Condition.riLike("name", "张")))));
        System.out.println();
    }

    @Test
    public void test1() {
        List<User> userList = baseRepository.query(new SearchParamImpl<>(User.class).setCondition(Condition.gte("createDate", LocalDate.now().minusDays(1))));
        System.out.println(userList.size());
    }

    @Test
    public void batchInsert() {
        Office root = baseRepository.querySingle(new SearchParamImpl<>(Office.class).setCondition(Condition.eq("level", "1")));
        // 创建1000个office
        int officeCount = 1000;
        List<Office> officeList = new ArrayList<>(officeCount);
        for (int i = 0; i < officeCount; i++) {
            String code = String.format("%04d", i + 1);
            officeList.add(new Office().setParent(root).setCode(code).setName(code + "部门"));
        }
        baseRepository.save(officeList);
        int idOffset = 1000000000;
        // 分5批 一共创建一百万个user
        int batchCount = 5;
        int batchSize = 200000;
        List<User> container = new ArrayList<>(batchSize);
        int seed = officeList.size();
        Random random = new Random();
        for (int i = 0; i < batchCount; i++) {
            for (int j = 0; j < batchSize; j++) {
                // 随机姓名、性别、电话号码、学历、年龄
                String chineseName = RandomValue.getChineseName();
                Sex sex = RandomValue.name_sex;
                String tel = RandomValue.getTel();
                String education = RandomValue.getEducation();
                int age = RandomValue.getNum(0, 115);
                User user = new User().setName(chineseName).setPhoneNum(tel).setEducation(education).setAge(age).setSex(sex)
                        // 随机部门
                        .setDepartment(officeList.get(random.nextInt(seed)).getId());
                // 为了提高效率 不查询序列
                user.setId(String.valueOf(idOffset ++));
                user.setIsNewRecord(true);
                container.add(user);
            }
            long start = System.currentTimeMillis();
            baseRepository.save(container);
            System.out.println("第"+ (i + 1) +"批耗时:" + (System.currentTimeMillis() - start));
            container.clear();
        }
        System.out.println();
    }

    public static class RandomValue {
        private static final String firstName = "赵钱孙李周吴郑王冯陈褚卫蒋沈韩杨朱秦尤许何吕施张孔曹严华金魏陶姜戚谢邹喻柏水窦章云苏潘葛奚范彭郎鲁韦昌马苗凤花方俞任袁柳酆鲍史唐费廉岑薛雷贺倪汤滕殷罗毕郝邬安常乐于时傅皮卞齐康伍余元卜顾孟平黄和穆萧尹姚邵湛汪祁毛禹狄米贝明臧计伏成戴谈宋茅庞熊纪舒屈项祝董梁杜阮蓝闵席季麻强贾路娄危江童颜郭梅盛林刁钟徐邱骆高夏蔡田樊胡凌霍虞万支柯咎管卢莫经房裘缪干解应宗宣丁贲邓郁单杭洪包诸左石崔吉钮龚程嵇邢滑裴陆荣翁荀羊於惠甄魏加封芮羿储靳汲邴糜松井段富巫乌焦巴弓牧隗山谷车侯宓蓬全郗班仰秋仲伊宫宁仇栾暴甘钭厉戎祖武符刘姜詹束龙叶幸司韶郜黎蓟薄印宿白怀蒲台从鄂索咸籍赖卓蔺屠蒙池乔阴郁胥能苍双闻莘党翟谭贡劳逄姬申扶堵冉宰郦雍却璩桑桂濮牛寿通边扈燕冀郏浦尚农温别庄晏柴瞿阎充慕连茹习宦艾鱼容向古易慎戈廖庚终暨居衡步都耿满弘匡国文寇广禄阙东殴殳沃利蔚越夔隆师巩厍聂晁勾敖融冷訾辛阚那简饶空曾毋沙乜养鞠须丰巢关蒯相查后江红游竺权逯盖益桓公万俟司马上官欧阳夏侯诸葛闻人东方赫连皇甫尉迟公羊澹台公冶宗政濮阳淳于仲孙太叔申屠公孙乐正轩辕令狐钟离闾丘长孙慕容鲜于宇文司徒司空亓官司寇仉督子车颛孙端木巫马公西漆雕乐正壤驷公良拓拔夹谷宰父谷粱晋楚阎法汝鄢涂钦段干百里东郭南门呼延归海羊舌微生岳帅缑亢况后有琴梁丘左丘东门西门商牟佘佴伯赏南宫墨哈谯笪年爱阳佟第五言福百家姓续";
        private static final String girl = "秀娟英华慧巧美娜静淑惠珠翠雅芝玉萍红娥玲芬芳燕彩春菊兰凤洁梅琳素云莲真环雪荣爱妹霞香月莺媛艳瑞凡佳嘉琼勤珍贞莉桂娣叶璧璐娅琦晶妍茜秋珊莎锦黛青倩婷姣婉娴瑾颖露瑶怡婵雁蓓纨仪荷丹蓉眉君琴蕊薇菁梦岚苑婕馨瑗琰韵融园艺咏卿聪澜纯毓悦昭冰爽琬茗羽希宁欣飘育滢馥筠柔竹霭凝晓欢霄枫芸菲寒伊亚宜可姬舒影荔枝思丽 ";
        private static final String boy = "伟刚勇毅俊峰强军平保东文辉力明永健世广志义兴良海山仁波宁贵福生龙元全国胜学祥才发武新利清飞彬富顺信子杰涛昌成康星光天达安岩中茂进林有坚和彪博诚先敬震振壮会思群豪心邦承乐绍功松善厚庆磊民友裕河哲江超浩亮政谦亨奇固之轮翰朗伯宏言若鸣朋斌梁栋维启克伦翔旭鹏泽晨辰士以建家致树炎德行时泰盛雄琛钧冠策腾楠榕风航弘";

        public static int getNum(int start, int end) {
            return (int) (Math.random() * (end - start +1) + start);
        }
        /**
         * 返回手机号码
         */
        private static final String[] telFirst = "134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153,176,189,172".split(",");
        private static String getTel() {
            int index = getNum(0, telFirst.length -1);
            String first = telFirst[index];
            String second = String.valueOf(getNum(1,888) +10000).substring(1);
            String third = String.valueOf(getNum(1,9100) +10000).substring(1);
            return first + second + third;
        }
        /**
         * 返回中文姓名
         */
        private static Sex name_sex = Sex.WOMAN;
        private static String getChineseName() {
            int index = getNum(0, firstName.length() -1);
            String first = firstName.substring(index, index +1);
            int sex = getNum(0,1);
            String str = boy;
            int length = boy.length();
            if(sex == 0){
                str = girl;
                length = girl.length();
                name_sex = Sex.WOMAN;
            } else {
                name_sex = Sex.MAN;
            }
            index = getNum(0, length -1);
            String second = str.substring(index, index +1);
            int hasThird = getNum(0, 1);
            String third = "";
            if(hasThird == 1){
                index = getNum(0, length -1);
                third = str.substring(index, index +1);
            }
            return first + second + third;
        }

        private static final String[] educations = new String[]{"大学本科", "大学专科", "中专", "高中以下", "硕士研究生", "博士研究生"};
        public static String getEducation() {
            return educations[getNum(0, educations.length -1)];
        }
    }
}
