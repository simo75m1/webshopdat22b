package dk.kea.webshopdat22b.repository;

import dk.kea.webshopdat22b.model.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepository
{
    //private final static String DB_URL = "jdbc:mysql://localhost:3306/webshopdat22b";
    //private final static String UID = "root";
    //private final String PWD = "DBKode12345";

    //database-properties injectes med Value fra application.properties
    @Value("${spring.datasource.url}")
    private String DB_URL;
    @Value("${spring.datasource.username}")
    private String UID;
    @Value("${spring.datasource.password}")
    private String PWD;
    //test

        public List<Product> getAll()
        {
            List<Product> productList = new ArrayList<>();
            try
            {
                Connection connection = DriverManager.getConnection(DB_URL, UID, PWD);
                Statement statement = connection.createStatement();
                final String SQL_Query = "SELECT * FROM products";
                ResultSet resultSet = statement.executeQuery(SQL_Query);
                while(resultSet.next())
                {
                    int id = resultSet.getInt(1);
                    String name = resultSet.getString(2);
                    double price = resultSet.getDouble(3);
                    Product product = new Product(id,name,price);
                    productList.add(product);
                    System.out.println(product);
                }
            }
            catch(SQLException e)
            {
                System.out.println("Could not query database");
                e.printStackTrace();
            }
            return productList;
        }

        public Product findProductById(int id)
        {
            //SQL-statement
            final String FIND_QUERY = "SELECT * FROM products WHERE id = ?";
            Product product = new Product();
            product.setId(id);
            try
            {
                //db connection
                Connection connection = DriverManager.getConnection(DB_URL, UID, PWD);

                //prepared statement
                PreparedStatement preparedStatement = connection.prepareStatement(FIND_QUERY);

                //set parameters
                preparedStatement.setInt(1, id);

                //execute statement
                ResultSet resultset = preparedStatement.executeQuery();

                //Få product ud af resultset
                resultset.next();
                String name = resultset.getString(2);
                double price = resultset.getDouble(3);
                product.setName(name);
                product.setPrice(price);
            }
            catch(SQLException e)
            {
                System.out.println("Database could not find search");
                e.printStackTrace();
            }

            return product;
        }

        public void addProduct(Product product)
        {
            try
            {
                //connect to db
                Connection connection = DriverManager.getConnection(DB_URL, UID, PWD);
                final String CREATE_QUERY = "INSERT INTO products(name, price) VALUES(?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(CREATE_QUERY);

                //set attributer i prepared statement
                preparedStatement.setString(1, product.getName());
                preparedStatement.setDouble(2, product.getPrice());

                //execute statement
                preparedStatement.executeUpdate();

                System.out.println(product+" added to database");
            }
            catch(SQLException e)
            {
                System.out.println("Could not add to database");
                e.printStackTrace();
            }


        }

        public void updateProduct(Product product)
        {
            //SQL statement
            final String UPDATE_QUERY = "UPDATE products SET name= ?, price= ? WHERE id= ?";
            try
            {
                //connect db
                Connection connection = DriverManager.getConnection(DB_URL, UID, PWD);
                //preparedstatement
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY);
                //set parameters
                String name = product.getName();
                double price = product.getPrice();
                int id = product.getId();

                preparedStatement.setString(1, name);
                preparedStatement.setDouble(2, price);
                preparedStatement.setInt(3, id);

                //execute statement
                preparedStatement.executeUpdate();

            }
            catch(SQLException e)
            {
                System.out.println("Could not update database");
                e.printStackTrace();
            }
        }

        public void deleteById(int id)
        {
            //SQL-query
            final String DELETE_QUERY = "DELETE FROM products WHERE id=?";

            try
            {
                //connecte til DB
                Connection connection = DriverManager.getConnection(DB_URL, UID, PWD);

                //create statement
                PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY);

                //Set parameter
                preparedStatement.setInt(1, id);

                //Execute statement
                preparedStatement.executeUpdate();
            }
            catch(SQLException e)
            {
                System.out.println("Could not delete from database");
                e.printStackTrace();

            }
        }


















}