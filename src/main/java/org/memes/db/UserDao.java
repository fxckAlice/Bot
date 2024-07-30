package org.memes.db;

import org.memes.context.DBAppContext;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;


public class UserDao {
    private final DataSource dataSource = DBAppContext.getDataSource();
    private static UserDao dao;
    public static UserDao getDao(){
        if (dao == null){
            dao = new UserDao();
        }
        return dao;
    }
    private UserDao(){}
    public User getUserById(Long id){
        try(Connection connection = dataSource.getConnection()){
            String byIdQuery = "SELECT * FROM users WHERE id = ?";
            try(PreparedStatement statement = connection.prepareStatement(byIdQuery)){
                statement.setString(1, id.toString());
                try(ResultSet resultSet = statement.executeQuery()){
                    if(resultSet.next()){
                        return new User(resultSet.getLong("id"), resultSet.getString("nickname"));
                    }
                }
            }
        }
        catch (SQLException e){
            System.out.println("DB GET was failed!");
            e.printStackTrace();
        }
        return null;
    }
    public User getUserByNickname(String nickname){
        try(Connection connection = dataSource.getConnection()){
            String byNickQuery = "SELECT * FROM users WHERE nickname = ?";
            try(PreparedStatement statement = connection.prepareStatement(byNickQuery)){
                statement.setString(1, nickname);
                try(ResultSet resultSet = statement.executeQuery()){
                    if(resultSet.next()){
                        return new User(resultSet.getLong("id"), resultSet.getString("nickname"));
                    }
                }
            }
        }
        catch (SQLException e){
            System.out.println("DB GET was failed!");
            e.printStackTrace();
        }
        return null;
    }
    public void createNewUser(User newUser){
        try(Connection connection = dataSource.getConnection()){
            String createQuery = "INSERT INTO users (id, started, ifNicknameRequired, ifListenerRequired, ifRmListenerRequired, ifStreamOpened) VALUES (?, ?, ?, ?, ?, ?)";
            try(PreparedStatement statement = connection.prepareStatement(createQuery)){
                statement.setString(1, newUser.getChatID().toString());
                statement.setString(2, newUser.isStarted().toString());
                statement.setString(3, newUser.isIfNicknameRequired().toString());
                statement.setString(4, newUser.isIfListenerRequired().toString());
                statement.setString(5, newUser.isIfRmListenerRequired().toString());
                statement.setString(6, newUser.isIfStreamOpened().toString());

                statement.executeUpdate();
            }
        }
        catch (SQLException e){
            System.out.println("DB POST was failed!");
            e.printStackTrace();
        }
    }

    public Boolean ifExistByChatId(Long chatId){
        try(Connection connection = dataSource.getConnection()){
            String ifUniqueQuery = "SELECT EXISTS(SELECT 1 FROM users WHERE id = ?)";
            try(PreparedStatement statement = connection.prepareStatement(ifUniqueQuery)){
                statement.setString(1, chatId.toString());
                try(ResultSet result = statement.executeQuery()){
                    if(result.next()){
                        return result.getBoolean(1);
                    }
                    else return false;
                }
            }
        }
        catch (SQLException e){
            System.out.println("DB GET was failed!");
            e.printStackTrace();
            return false;
        }
    }

    public Boolean ifUniqueByNickname(String nick){
        try(Connection connection = dataSource.getConnection()){
            String ifUniqueQuery = "SELECT EXISTS(SELECT 1 FROM users WHERE nickname = ?)";
            try(PreparedStatement statement = connection.prepareStatement(ifUniqueQuery)){
                statement.setString(1, nick);
                try(ResultSet result = statement.executeQuery()){
                    if(result.next()){
                        return !result.getBoolean(1);
                    }
                    else return false;
                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    public ArrayList<Long> getListenersListById(Long id){
        try (Connection connection = dataSource.getConnection()) {
            String getArrQuery = "SELECT * FROM usersChats WHERE userId = ?";
            try(PreparedStatement statement = connection.prepareStatement(getArrQuery)){
                statement.setString(1, id.toString());
                try(ResultSet result = statement.executeQuery()){
                    ArrayList<Long> ans = new ArrayList<>();
                    while(result.next()){
                        ans.add(result.getLong("chatId"));
                    }
                    return ans;
                }
            }
        }
        catch (SQLException e){
            System.out.println("DB GET was failed!");
            return null;
        }
    }
    public void updateInfoFromUsers(User user){
        try (Connection connection = dataSource.getConnection()){
            String updateQuery = "UPDATE users SET nickname = ?, started = ?, ifNicknameRequired = ?, ifListenerRequired = ?, ifRmListenerRequired = ?, ifStreamOpened = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(updateQuery)){
                statement.setString(1, user.getNick());
                statement.setBoolean(2, user.isStarted());
                statement.setBoolean(3, user.isIfNicknameRequired());
                statement.setBoolean(4, user.isIfListenerRequired());
                statement.setBoolean(5, user.isIfRmListenerRequired());
                statement.setBoolean(6, user.isIfStreamOpened());
                statement.setLong(7, user.getChatID());
                statement.execute();
            }
        }
        catch (SQLException e){
            System.out.println("DB UPDATE was failed!");
            e.printStackTrace();
        }
    }
    public void deleteListenerByChatId(Long userId, Long chatId){
        try (Connection connection = dataSource.getConnection()){
            String deleteQuery = "DELETE FROM usersChats WHERE userId = ? AND chatId = ?";
            try (PreparedStatement statement = connection.prepareStatement(deleteQuery)){
                statement.setLong(1, userId);
                statement.setLong(2, chatId);
                statement.execute();
            }
        } catch (SQLException e){
            System.out.println("DB DELETE was failed!");
            e.printStackTrace();
        }
    }
    public void createListener(Long userId, Long chatId){
        try (Connection connection = dataSource.getConnection()){
            String createQuery = "INSERT INTO userChats (id, userId, chatId) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(createQuery)){
                statement.setString(1, UUID.randomUUID().toString());
                statement.setLong(2, userId);
                statement.setLong(3, chatId);
                statement.execute();
            }
        } catch (SQLException e){
            System.out.println("DB CREATE was failed!");
            e.printStackTrace();
        }
    }
    public ArrayList<Long> getAllListenersById(Long userId){
        try (Connection connection = dataSource.getConnection()){
            String getQuery = "SELECT * FROM usersChats WHERE userId = ?";
            try (PreparedStatement statement = connection.prepareStatement(getQuery)){
                statement.setLong(1, userId);
                try (ResultSet result = statement.executeQuery()){
                    ArrayList<Long> ans = new ArrayList<>();
                    while (result.next()){
                        ans.add(result.getLong("chatId"));
                    }
                    return ans;
                }
            }
        } catch (SQLException e){
            System.out.println("DB GET was failed");
            e.printStackTrace();
            return null;
        }
    }
}

