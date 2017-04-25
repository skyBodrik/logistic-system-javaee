package logistic.web.repositories;

import logistic.web.dao.UserDao;
import logistic.web.dao.iUserDao;
import logistic.web.models.User;

import javax.ejb.EJB;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;
import java.security.MessageDigest;

/**
 * Created by bodrik on 23.11.16.
 */
public class UsersRepository {
    private static UsersRepository instance;

    private UserDao dao = new UserDao();

    public static UsersRepository getInstance() {
        if (instance == null) {
            instance = new UsersRepository();
        }

        return instance;
    }

    private static String md5Convert(String str) {
        String outStr = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());

            byte byteData[] = md.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            outStr = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.print(e.getMessage());
        }
        return outStr;
    }

    public List<User> getAll(int type) {
        List<User> list;
        if (type == 0) {
            list = this.dao.getAll();
        } else {
            list = this.dao.getAll().stream().filter(p -> p.getType() == type).collect(Collectors.toList());
        }
        return list;
    }

    public User getById(int id) {
        return this.dao.getById(id); //UserMapper.getById(id);
    }

    public User getByEmailAndPassword(String email, String password) {
        String passwordHash = md5Convert(password);
        return this.dao.getAll().stream().filter(u -> u.getEmail().equals(email) && u.getPassword().equals(passwordHash)).findFirst().orElse(null);
        //return UserMapper.getAll().stream().filter(u -> u.getEmail().equals(email) && u.getPassword().equals(passwordHash)).findFirst().orElse(null);
    }

    public User createClient(String name, String email, String password, String phone) {
        User user = new User(name, email, this.md5Convert(password), phone, User.TYPE_CLIENT);
        this.dao.add(user);
        return user;
    }

    public User createCarrier(String name, String email, String password, String phone) {
        User user = new User(name, email, this.md5Convert(password), phone, User.TYPE_CARRIER);
        this.dao.add(user);
        return user;
    }

    public User createOperator(String name, String email, String password, String phone) {
        User user = new User(name, email, this.md5Convert(password), phone, User.TYPE_OPERATOR);
        this.dao.add(user);
        return user;
    }

    public User updateUser(int userId, String name, String email, String phone, double weight, double length, double width, double height) {
        User user = this.dao.getById(userId);
        user.setName(name);
        user.setEmail(email);
        user.setPhone(phone);
        user.setMaxWeight(weight);
        user.setLength(length);
        user.setWidth(width);
        user.setHeight(height);
        this.dao.update(user);
        return user;
    }
}
