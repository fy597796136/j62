import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

public class Demo {
    public static void main(String[] args) {
        //根据ini文件创建工厂类，工厂类可以产生安全管理器
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");

        //产生安全管理器
        SecurityManager securityManager = factory.getInstance();

        //将安全管理器设置到当前的运行环境中
        SecurityUtils.setSecurityManager(securityManager);

        //创建主体，通过主体才能进行认证操作
        Subject subject = SecurityUtils.getSubject();

        //产生令牌token,封装用户名，密码，准备认证
        UsernamePasswordToken token = new UsernamePasswordToken("jack","123");

        try {
            //用户登录
            subject.login(token);

            //判断，当前用户是否已经登录
            if (subject.isAuthenticated()){
                System.out.println("用户已登录");
            }

            System.out.println("----------准备退出---------");

            subject.logout();//退出登录

            if (subject.isAuthenticated()){
                System.out.println("用户已登录");
            }else{
                System.out.println("用户已退出");
            }
        } catch (UnknownAccountException e) {
            System.out.println("用户不存在...");
        } catch(IncorrectCredentialsException ex){
            System.out.println("密码错误...");
        }


    }
}
