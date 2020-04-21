package MscInvisibleDemo;

/**
 * @ClassName book
 * @Author 吴俊淇
 * @Date 2020/4/15 16:55
 * @Version 1.0
 **/
public class book {
    String name;
    String remark;
   static StringBuffer price = new StringBuffer();
    public book() {
        aaa();
    }
    public  void  aaa(){
        name="wjq";
        remark="beizhu";
        price.append("123");
//        System.out.println(price.toString());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public static StringBuffer getPrice() {
        return price;
    }

    public static void setPrice(StringBuffer price) {
        book.price = price;
    }
}
