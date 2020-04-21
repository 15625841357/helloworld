package MscInvisibleDemo; /**
 * @ClassName DebugLog
 * @Author 吴俊淇
 * @Date 2020/4/14 22:04
 * @Version 1.0
 **/
import java.text.SimpleDateFormat;

public class DebugLog {

    public static void Log(String tag,String log)
    {
        if(true)
            System.out.println(log);
    }

    public static void Log(String log)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date=dateFormat.format(new java.util.Date());
        if(true)
            System.out.println("<" + date + ">" + log);
    }

    public static boolean isEmpty(String string){
        if(string == null)
        {
            return true;
        }
        if(string.isEmpty())
        {
            return true;
        }
        return false;
    }
}
