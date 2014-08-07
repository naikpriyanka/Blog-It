package ramani;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import ramani.DBHandler;
import ramani.Post;
import ramani.PostNotFoundException;

public final class DBHandler {/*
	 * Database connection related constants
	 * 
	 */
	
	/* Uncomment these constants for MySQL Database
	 * 
	 * private final String DB_DRIVER_CLASS	= "com.mysql.jdbc.Driver";
	 * private final String DB_URL				= "jdbc:mysql://localhost:3306/blog_db_user";
	 * 
	 * */
	
	/* Uncomment these constant for Oracle Database */
	private final String DB_DRIVER_CLASS	= "oracle.jdbc.OracleDriver";
	private final String DB_URL				= "jdbc:oracle:thin:@localhost:1521:xe";
	
	/* User name and password constants */
	private final String DB_USER			= "twitterproject"; // DATABASE USER 
	private final String DB_PASS			= "branch"; //PASSWORD
	
	/*
	 * Using a logger is expected in the production code.
	 * 
	 * It's a bad practice to use System.out.println as a 
	 * debugging aid for web application development.
	 * 
	 */
	//private static final Logger logger		= Logger.getLogger(DBHandler.class.getSimpleName());
	
	/*
	 * Queries kept in the string constants.
	 * It's a bad practice to use String / number literals in the code. 
	 */
	//private final static String SELECT_ALL_POSTS_SQL= "SELECT postId, post, postDate, author FROM post ORDER BY postDate DESC";
	private final static String UPDATE_NUM_POSTS = "UPDATE profile set numberofposts = ? where username = ?";
	private final static String SELECT_POST_NUM = "SELECT numberofposts from profile where username = ?";
	private final static String SELECT_ONE_POST_SQL	= "SELECT postId, post, postDate, author FROM post WHERE postId = ?";
	private final static String INSERT_POST_SQL		= "INSERT INTO post ( postId, post, postdate, author ) VALUES ( ? ,?, sysdate, ?)";
	private final static String SELECT_USER = "SELECT * FROM login where username = ? and password = ?";
	private final static String INSERT_USER = " INSERT INTO profile values( ? , ? , ? , ? , ? , ? , ?)";
	private final static String INSERT_USER_LOGIN = "INSERT INTO login values(? , ?)";
	private final static String SELECT_FRIEND = "SELECT * from TEMP";
	//private final static String SELECT_SUGGEST = "SELECT * from login where username != TEMPO";
	private final static String CREATE_USER_TABLE= "CREATE TABLE TEMPUSER(friend varchar2(50) not null,primary key (friend))";
	private final static String SUGGEST_FRIENDS = "select username from login where username != 'TEMP' MINUS select * from TEMP";
	private final static String INSERT_FRIEND = "INSERT INTO TEMPUSER values(?)";
	private final static String SELECT_POST = "SELECT postId,post,postDate,author from post where author = ?";
	/*
	 * Once initialized this connection object will be
	 * used throughout the entire application.
	 * 
	 * Database connections can be costly to make and close upon every database query.
	 * 
	 */
	private static Connection connection;
	
	/*
	 * Initializes the connection object as soon as the class is loaded in the JVM's memory. 
	 */
	static 
	{
		if (connection==null) 
		{
			new DBHandler();
		}
	}
	
	/*
	 * A private constructor prevents object creation using new operator.
	 * 
	 * A static block in this class will create single object of the connection,
	 * preventing this application to make a new connection to database for each database query.
	 * 
	 * This is called Singleton design pattern.
	 */
	private DBHandler() 
	{
		
		try 
		{
			Class.forName(DB_DRIVER_CLASS);
		}
		catch (ClassNotFoundException e) 
		{
			System.out.println("MySQL driver could not be loaded");
		}
		
		try 
		{
			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
		}
		catch (SQLException e) 
		{
			System.out.println("Database connection could not be made. Is this database up?\nPerhapse database URL, username or password changed.");
		}
		
	}
	
	public static User getUser(String username,String password) throws SQLException, UserNotFoundException {
		
		User user = null;
		
		if ( connection != null ) 
		{
			PreparedStatement statement	= connection.prepareStatement(SELECT_USER);
			
			
			statement.setString(1, username);
			statement.setString(2, password);
			ResultSet result = statement.executeQuery();
			while (result.next()) 
			{
				user = new User(result.getString(1),result.getString(2));
			} 
		} 
		else 
		{
			System.out.println("Connection is closed");
		}
		
		return user;
	}


	public static void insertPost(Post post, String UNS) throws SQLException {
		
		if ( connection != null ) {
			
			PreparedStatement statement0 = connection.prepareStatement(SELECT_POST_NUM);
			statement0.setString(1, UNS);
			ResultSet result = statement0.executeQuery();
			Integer resNum = 0;
			while(result.next()){
				resNum = result.getInt(1);
			}
			resNum++;
			String res = String.valueOf(resNum);
			String postID = UNS.concat(res);
						
			PreparedStatement statement = connection.prepareStatement(INSERT_POST_SQL);
			statement.setString(1, postID);
			statement.setString(2, post.getBody());
			statement.setString(3, UNS);
			statement.executeUpdate();
			
			PreparedStatement statement1 = connection.prepareStatement(UPDATE_NUM_POSTS);
			statement1.setInt(1, resNum);
			statement1.setString(2, UNS);
			statement1.executeUpdate();
			System.out.println("New post added");
		} 
		else 
		{
			throw(new SQLException("Connection closed"));
		}
	}

	public static void insertUser(User user) throws SQLException {
		
		if ( connection != null ) 
		{
		
			PreparedStatement statement = connection.prepareStatement(INSERT_USER);
			statement.setString(1, user.getFullname());
			statement.setString(2, user.getGender());
			statement.setDate(4, user.getDoB());
			statement.setString(3, user.getPlace());
			statement.setString(5, user.getUserName());
			statement.setString(6, user.getPassword());
			statement.setInt(7, 0);
			statement.executeUpdate();
			
		
			PreparedStatement statement1 = connection.prepareStatement(INSERT_USER_LOGIN);
			statement1.setString(1, user.getUserName());
			statement1.setString(2, user.getPassword());
			
			statement1.executeUpdate();
			
			System.out.println("New user added");
		} 
		else 
		{
			throw(new SQLException("Connection closed"));
		}
	}

	
	public static List<Post> getAllPosts(String username) throws SQLException {
		
		ArrayList<Post> allPosts	= null;
		if ( connection != null ) 
		{
			allPosts = new ArrayList<Post>();
			
			
			String replace=SELECT_FRIEND.replaceAll("TEMP",username);
			PreparedStatement statement	= connection.prepareStatement(SELECT_POST);
			PreparedStatement statement1	= connection.prepareStatement(replace);
			ResultSet result1			= statement1.executeQuery();
			
			while (result1.next()) 
			{
				
				statement.setString(1,result1.getString(1));
				ResultSet result			= statement.executeQuery();
				while(result.next()){
				Post currentPost = new Post(result.getString(2));
				
				currentPost.setPostId(result.getString(1));
				currentPost.setCreatedOn(result.getDate(3));
				currentPost.setAuthor(result.getString(4));
				allPosts.add(currentPost);
				}
			}
			statement.setString(1,username);
			ResultSet result			= statement.executeQuery();
			while(result.next())
			{
				Post currentPost = new Post(result.getString(2));
				currentPost.setPostId(result.getString(1));
				currentPost.setCreatedOn(result.getDate(3));
				currentPost.setAuthor(result.getString(4));
				allPosts.add(currentPost);
			}
		}
		return allPosts;
	}

	public static Post getPost(int id) throws SQLException, PostNotFoundException {
		
		Post post = null;
		
		if ( connection != null )
		{
			
			PreparedStatement statement	= connection.prepareStatement(SELECT_ONE_POST_SQL);
			statement.setInt(1, id);
			ResultSet result 			= statement.executeQuery();
	
			if (result.next()) 
			{
				
				post = new Post(result.getString(2));
				
				post.setPostId(result.getString(1));
				post.setCreatedOn(result.getDate(3));
				
			}
			else 
			{
				throw (new PostNotFoundException("Post with id " + id + " not found"));
			}
		} 
		else 
		{
			System.out.println("Connection is closed");
		}
		
		return post;
	}

public static List<Friend> getAllfriends(String username) throws SQLException {
	
	ArrayList<Friend> allfriends	= null;
	
	if ( connection != null ) 
	{
		
		allfriends = new ArrayList<Friend>();
		String replace=SELECT_FRIEND.replaceAll("TEMP",username);
		
		PreparedStatement statement	= connection.prepareStatement(replace);
		ResultSet result			= statement.executeQuery();
		
	
		while (result.next()) 
		{
			
			Friend currentFriend = new Friend(result.getString(1));
			
			currentFriend.setfriendName(result.getString(1));
			
			
			allfriends.add(currentFriend);
		}

		if (allfriends.size()>0) 
		{
			System.out.println("All friends returned ...");
		}
		else
		{
			System.out.println("No friends found!");
		}
	}
	
	return allfriends;
}

	public static void CreateUserTable(String username) throws SQLException {
		if ( connection != null )
		{
			Statement statement1 = null;
			statement1=connection.createStatement();
			String replace=	CREATE_USER_TABLE.replaceAll("TEMPUSER", username);
			statement1.executeUpdate(replace);
		}
		else 
		{
			throw(new SQLException("Connection closed"));
		}
		
	}

	public static List<Friend> SuggestFriends(String username) throws SQLException {
		ArrayList<Friend> allfriends	= null;
		if ( connection != null ) 
		{
		
			allfriends = new ArrayList<Friend>();
			String replace=SUGGEST_FRIENDS.replaceAll("TEMP",username);
			PreparedStatement statement	= connection.prepareStatement(replace);
			ResultSet result			= statement.executeQuery();
		
			while (result.next()) 
			{
			
				Friend currentFriend = new Friend(result.getString(1));
			
				currentFriend.setfriendName(result.getString(1));
			

				allfriends.add(currentFriend);
			}

			if (allfriends.size()>0) 
			{
				System.out.println("All friends returned ...");
			}
			else
			{
				System.out.println("No friends found!");
			}
		
		}
		return allfriends;

}
	
	public static void addfriend(String username, String friend) throws SQLException {
	
	if ( connection != null ) 
	{
	
		String replace = INSERT_FRIEND.replaceAll("TEMPUSER", username);
		PreparedStatement statement = connection.prepareStatement(replace);
		statement.setString(1, friend);
		statement.executeUpdate();
	
	} 
	else 
	{
		throw(new SQLException("Connection closed"));
	}
}
}